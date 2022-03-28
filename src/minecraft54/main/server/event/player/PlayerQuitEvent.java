package minecraft54.main.server.event.player;

import minecraft54.main.server.entity.Player;
import minecraft54.main.server.event.Event;

public class PlayerQuitEvent extends Event{

    private final Player player;
    private String message;

    public PlayerQuitEvent(Player player,String message){
        this.player=player;
        this.message=message;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
    public Player getPlayer(){
        return player;
    }

}
