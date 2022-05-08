package minecraft54.main.server;

import engine54.net.Packet;
import engine54.net.TCPServer;
import engine54.net.TCPServerListener;
import engine54.util.Sync;
import engine54.util.Timer;
import minecraft54.main.server.entity.*;
import minecraft54.main.server.event.player.PlayerJoinEvent;
import minecraft54.main.server.event.player.PlayerQuitEvent;
import minecraft54.main.server.world.*;
import minecraft54.main.packets.PlayerLogIn;
import minecraft54.main.server.event.Event;
import minecraft54.main.server.event.EventHandler;
import minecraft54.main.server.event.EventListener;
import minecraft54.main.server.event.server.ServerListPingEvent;
import minecraft54.main.packets.PacketStatusOutPong;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Server{


    private final TCPServer server;
    private final Thread tickThread;
    private final List<World> worlds;
    private final List<EventListener> eventListeners;
    private final List<Event> eventStack;
    private final List<Player> onlinePlayers;
    private float tps;
    private final Timer time;
    private String motd;
    private int maxPlayers;


    public Server(){
        worlds=new ArrayList<>();
        server=new TCPServer();
        eventListeners=new ArrayList<>();
        eventStack=new ArrayList<>();
        onlinePlayers=new ArrayList<>();

        server.addListener(new TCPServerListener(){
            public void receive(TCPServer thisServer,TCPServer.Client sender,Packet packet){
                switch(packet.getClass().getSimpleName()){
                    case "PacketStatusInPing" -> {
                        callListeners(new ServerListPingEvent(sender.getUuid().toString(),motd,onlinePlayers.size(),maxPlayers));
                    }
                    case "PlayerLogIn" -> {
                        PlayerLogIn p=(PlayerLogIn)packet;
                        if(onlinePlayers.size()==maxPlayers || getPlayer(p.n)!=null)
                            break;

                        Player player=new ServerPlayer(p.n,sender.getUuid());
                        callListeners(new PlayerJoinEvent(player,p.n+" joined the game"));
                    }
                }
            }
            public void newConnection(TCPServer thisServer,TCPServer.Client client){
                //System.out.println("New Connection: "+client.getUuid());
            }
            public void disconnected(TCPServer thisServer,TCPServer.Client client){
                //System.out.println("Disconnected: "+client.getUuid());
                Player player=getPlayer(client.getUuid());
                if(player!=null)
                    callListeners(new PlayerQuitEvent(player,player.getName()+" leave the game"));
            }
            public void error(TCPServer thisServer,Exception e){
                e.printStackTrace();
            }
        });

        tickThread=new Thread(()->{
            final Sync tpsSync=new Sync(20);
            long prevTime=System.nanoTime();
            while(!Thread.interrupted()){
                tps=-1000000000f/(prevTime-(prevTime=System.nanoTime()));
                tick();
                tpsSync.sync();
            }
        });

        time=new Timer();


    }


    private void tick(){
        for(int i=0; i<eventStack.size(); i++){
            Event event=eventStack.get(i);

            switch(event.getName()){
                case "ServerListPingEvent" -> {
                    ServerListPingEvent e=(ServerListPingEvent)event;
                    TCPServer.Client client=server.getClient(e.getClientUuid());
                    if(client!=null)
                        client.send(new PacketStatusOutPong(e.getMotd(),e.getPlayersCount(),e.getMaxPlayersCount()));
                }
                case "PlayerJoinEvent" -> {
                    PlayerJoinEvent e=(PlayerJoinEvent)event;
                    onlinePlayers.add(e.getPlayer());

                    System.out.println(e.getMessage());
                }
                case "PlayerQuitEvent" -> {
                    PlayerQuitEvent e=(PlayerQuitEvent)event;
                    Player player=e.getPlayer();
                    onlinePlayers.remove(player);

                    System.out.println(e.getMessage());
                }
            }

            eventStack.remove(event);
        }
    }


    public void registerEvents(EventListener listener){
        eventListeners.add(listener);
    }


    public void callListeners(Event event){
        for(EventListener listener: eventListeners)
            for(Method method:listener.getClass().getDeclaredMethods())
                if( method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes()[0].getSimpleName().equals(event.getClass().getSimpleName()) )
                    try{
                        method.invoke(listener,event);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
        if(!event.isCancelled())
            eventStack.add(event);
    }




    public World getWorld(String name){
        for(World world: worlds)
            if(world.getName().equals(name))
                return world;
        return null;
    }

    public Player getPlayer(String name){
        for(Player player: onlinePlayers)
            if(player.getName().equals(name))
                return player;
        return null;
    }

    public Player getPlayer(UUID clientUuid){
        for(Player player: onlinePlayers)
            if(player.getClientUuid().equals(clientUuid))
                return player;
        return null;
    }

    public List<Player> getOnlinePlayers(){
        return onlinePlayers;
    }


    public void open(){
        server.open(20540);
        tickThread.start();
    }

    public void close(){
        server.close();
        tickThread.interrupt();
    }


    public void setMotd(String motd){
        this.motd=motd;
    }
    public void setMaxPlayers(int maxPlayers){
        this.maxPlayers=maxPlayers;
    }

    public String getMotd(){
        return motd;
    }
    public int getOnline(){
        return onlinePlayers.size();
    }
    public int getMaxPlayers(){
        return maxPlayers;
    }

    public int getPort(){
        return server.getPort();
    }

    public float getTps(){
        return tps;
    }

}