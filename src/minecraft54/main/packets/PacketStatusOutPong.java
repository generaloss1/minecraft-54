package minecraft54.main.packets;

import minecraft54.engine.net.Packet;

public class PacketStatusOutPong implements Packet{

    public String m; // motd
    public int a,b; // count, maxCount

    public PacketStatusOutPong(String m,int a,int b){
        this.m=m;
        this.a=a;
        this.b=b;
    }

}
