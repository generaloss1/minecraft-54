package minecraft54.main.server.entity;

import minecraft54.main.util.Location;
import minecraft54.main.server.world.BlockData;

import java.util.UUID;

public interface Player extends HumanEntity{

    String getName();
    String getDisplayName();
    void setDisplayName(String displayName);
    String getPlayerListName();
    void setPlayerListName(String listName);

    UUID getClientUuid();

    void kickPlayer(String reason);

    void chat(String var1);

    boolean performCommand(String command);
    void updateCommands();

    void saveData();
    void loadData();

    Location getBedSpawnLocation();
    void setBedSpawnLocation(Location location);
    void setBedSpawnLocation(Location location,boolean force);

    boolean sendChunkChange(Location location,byte[] data);
    boolean sendBlockChange(Location location,BlockData block);
    
    void setPlayerTime(long time,boolean relative);
    long getPlayerTime();
    void resetPlayerTime();

    void hidePlayer(Player player);
    void showPlayer(Player player);
    boolean canSee(Player player);

    boolean isOnGround();

    boolean isSneaking();
    void setSneaking(boolean sneaking);
    boolean isSprinting();
    void setSprinting(boolean sprinting);

    boolean getAllowFlight();
    void setAllowFlight(boolean flight);
    boolean isFlying();
    void setFlying(boolean flying);

    void setFlySpeed(float speed);
    float getFlySpeed();
    void setWalkSpeed(float speed);
    float getWalkSpeed();

    int getClientViewDistance();
    
    void updateInventory();

}
