package engine54.net;

public interface TCPServerListener{

    void receive(TCPServer thisServer,TCPServer.Client sender,Packet packet);
    void newConnection(TCPServer thisServer,TCPServer.Client client);
    void disconnected(TCPServer thisServer,TCPServer.Client client);
    void error(TCPServer thisServer,Exception e);

}
