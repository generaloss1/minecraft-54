package minecraft54.engine.net;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer{


    private ServerSocket socket;
    private final List<Client> clients=new ArrayList<>();
    private final List<TCPServerListener> listeners=new ArrayList<>();

    private int port,maxClientCount;
    private boolean waiting;


    public TCPServer(int maxClientCount){
        this.maxClientCount=maxClientCount;
    }

    public TCPServer(){
        maxClientCount=-1;
    }


    public void open(int port){
        this.port=port;
        try{
            socket=new ServerSocket(port);
        }catch(IOException e){
            for(TCPServerListener l:listeners)
                l.error(this,e);
        }
        waitNextConnection();
    }

    public void close(){
        try{
            for(int i=0; i<clients.size(); i++)
                clients.get(i).disconnect("Server closed.");
            waiting=false;
            socket.close();
        }catch(IOException e){
            for(TCPServerListener l:listeners)
                l.error(this,e);
        }
    }


    public void addListener(TCPServerListener l){
        listeners.add(l);
    }


    public void broadcast(Packet packet){
        for(int i=0; i<clients.size(); i++)
            clients.get(i).send(packet);
    }

    public void disconnectClient(String uuid,String msg){
        Client client=clients.get(getClientIndex(uuid));
        client.disconnect(msg);
    }

    public int getClientIndex(String uuid){
        for(int i=0; i<clients.size(); i++){
            String cuuid=clients.get(i).uuid.toString();
            if(cuuid.equals(uuid))
                return i;
        }
        return -1;
    }

    public Client getClient(String uuid){
        int i=getClientIndex(uuid);
        if(i>-1)
            return clients.get(i);
        return null;
    }


    private void waitNextConnection(){
        if(!waiting && (clients.size()<maxClientCount || maxClientCount==-1)){
            new Client(this);
            waiting=true;
        }
    }


    public void setMaxClientCount(int maxClientCount){
        this.maxClientCount=maxClientCount;
    }

    public int getPort(){
        return port;
    }

    public int getMaxClientCount(){
        return maxClientCount;
    }

    public List<Client> getClients(){
        return clients;
    }

    public ServerSocket getSocket(){
        return socket;
    }



    public static class Client{


        private Socket socket;
        private ObjectOutputStream out;
        private final TCPServer server;
        private final UUID uuid;
        private Thread thread;
        private boolean connected;


        public Client(TCPServer server){
            uuid=UUID.randomUUID();
            this.server=server;
            thread=new Thread(()->{
                try{
                    socket=server.socket.accept();
                    out=new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream input=new ObjectInputStream(socket.getInputStream());

                    server.clients.add(this);
                    connected=true;
                    server.waiting=false;
                    server.waitNextConnection();

                    for(TCPServerListener l:server.listeners)
                        l.newConnection(server,this);

                    while(!thread.isInterrupted()){
                        Packet packet=(Packet)input.readObject();
                        for(TCPServerListener l:server.listeners)
                            l.receive(server,this,packet);
                    }
                }catch(Exception e){
                    disconnect();
                }
            });
            thread.start();
        }

        private void disconnect(){
            if(connected){
                connected=false;
                for(TCPServerListener l:server.listeners)
                    l.disconnected(server,this);
                int i=server.getClientIndex(uuid.toString());
                if(i>-1)
                    server.clients.remove(i);
                server.waitNextConnection();
            }
        }

        public void disconnect(String msg){
            if(connected){
                send(new DisconnectPacket(msg));
                connected=false;
                for(TCPServerListener l:server.listeners)
                    l.disconnected(server,this);
                int i=server.getClientIndex(uuid.toString());
                if(i>-1)
                    server.clients.remove(i);
                server.waitNextConnection();
            }
        }

        public void send(Packet packet){
            if(out!=null){
                try{
                    out.writeObject(packet);
                    out.flush();
                    out.reset();
                }catch(SocketException ignored){
                }catch(Exception e){
                    for(TCPServerListener l:server.listeners)
                        l.error(server,e);
                }
            }
        }


        public TCPServer getServer(){
            return server;
        }

        public ObjectOutputStream getOutputStream(){
            return out;
        }

        public boolean isConnected(){
            return connected;
        }

        public UUID getUuid(){
            return uuid;
        }

        public Socket getSocket(){
            return socket;
        }


    }



}