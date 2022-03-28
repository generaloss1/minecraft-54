package minecraft54.engine.net;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPClient{


	private Socket socket;
	private ObjectOutputStream out;
	private final List<TCPClientListener> listeners=new ArrayList<>();
	private Thread thread;

	private String ip;
	private int port;
	private boolean connected;


	public void connect(String ip,int port){
		this.ip=ip;
		this.port=port;

		try{
			socket=new Socket(ip,port);

			final ObjectInputStream input=new ObjectInputStream(socket.getInputStream());
			out=new ObjectOutputStream(socket.getOutputStream());

			connected=true;
			for(TCPClientListener l:listeners)
				l.connected(this);

			thread=new Thread(()->{
				while(!Thread.currentThread().isInterrupted()){
					try{
						Packet packet=(Packet)input.readObject();
						if(packet.getClass().getSimpleName().equals("DisconnectPacket"))
							disconnect(((DisconnectPacket)packet).a);
						for(TCPClientListener l:listeners)
							l.receive(this,packet);
					}catch(Exception e){
						disconnect(e.toString());
					}
				}
			});
			thread.start();

		}catch(Exception e){
			for(TCPClientListener l:listeners)
				l.error(this,e);
			disconnect(e.toString());
		}
	}


	public void addListener(TCPClientListener l){
		listeners.add(l);
	}

	public void removeListener(TCPClientListener l){
		listeners.remove(l);
	}


	public void disconnect(String msg){
		try{
			out.close();
			socket.close();
			connected=false;
			for(TCPClientListener l:listeners)
				l.disconnected(this,msg);
			thread.stop();
		}catch(Exception e){
			for(TCPClientListener l:listeners)
				l.error(this,e);
		}
	}


	public void send(Packet packet){
		if(out!=null){
			try{
				out.writeObject(packet);
				out.flush();
				out.reset();
			}catch(Exception e){
				for(TCPClientListener l:listeners)
					l.error(this,e);
			}
		}
	}


	public void send(String message){
		try{
			PrintWriter out=new PrintWriter(socket.getOutputStream());
			out.write(message);
			out.flush();
		}catch(Exception e){
			for(TCPClientListener l:listeners)
				l.error(this,e);
		}
	}


	public String getIp(){
		return ip;
	}

	public int getPort(){
		return port;
	}

	public boolean isConnected(){
		return connected;
	}


}
