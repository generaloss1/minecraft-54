package minecraft54.main.packets;

import minecraft54.engine.net.Packet;

public class PlayerLogIn implements Packet{

    public String n;

    public PlayerLogIn(String playerName){
        n=playerName;
    }

}
