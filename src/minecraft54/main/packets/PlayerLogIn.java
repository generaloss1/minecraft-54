package minecraft54.main.packets;

import engine54.net.Packet;

public class PlayerLogIn implements Packet{

    public String n;

    public PlayerLogIn(String playerName){
        n=playerName;
    }

}
