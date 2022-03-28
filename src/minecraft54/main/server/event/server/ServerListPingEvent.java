package minecraft54.main.server.event.server;

import minecraft54.main.server.event.Event;

public class ServerListPingEvent extends Event{

    private final String clientUuid;
    private String motd;
    private int playersCount,maxPlayersCount;

    public ServerListPingEvent(String clientUuid,String motd,int playersCount,int maxPlayersCount){
         this.clientUuid=clientUuid;
         this.motd=motd;
         this.playersCount=playersCount;
         this.maxPlayersCount=maxPlayersCount;
    }

    public void setMotd(String motd){
        this.motd=motd;
    }
    public void setPlayersCount(int playersCount){
        this.playersCount=playersCount;
    }
    public void setMaxPlayersCount(int maxPlayersCount){
        this.maxPlayersCount=maxPlayersCount;
    }

    public String getClientUuid(){
        return clientUuid;
    }
    public String getMotd(){
        return motd;
    }
    public int getPlayersCount(){
        return playersCount;
    }
    public int getMaxPlayersCount(){
        return maxPlayersCount;
    }

}
