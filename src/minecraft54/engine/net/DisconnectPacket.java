package minecraft54.engine.net;

import java.io.Serializable;

public class DisconnectPacket implements Packet{

    public String a;

    public DisconnectPacket(String msg){
        this.a=msg;
    }

}
