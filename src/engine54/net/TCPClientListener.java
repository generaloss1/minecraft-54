package engine54.net;

public interface TCPClientListener{

    void receive(TCPClient thisClient,Packet packet);
    void connected(TCPClient thisClient);
    void disconnected(TCPClient thisClient,String reason);
    void error(TCPClient thisClient,Exception e);

}
