package minecraft54.main.server.entity;

import minecraft54.main.util.GameMode;
import minecraft54.main.server.item.Inventory;
import minecraft54.main.server.item.ItemStack;
import minecraft54.main.server.item.PlayerInventory;
import minecraft54.main.server.world.BlockData;
import minecraft54.main.util.Location;

import java.util.UUID;

public class ServerPlayer implements Player{

    private final String name;
    private String displayName,listName;
    private UUID clientUuid;

    public ServerPlayer(String name,UUID clientUuid){
        this.name=name;
        displayName=name;
        listName=name;
        this.clientUuid=clientUuid;
    }

    public String getName(){
        return name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName=displayName;
    }

    public String getPlayerListName(){
        return listName;
    }

    public void setPlayerListName(String listName){
        this.listName=listName;
    }

    public UUID getClientUuid(){
        return clientUuid;
    }

    public void kickPlayer(String reason){

    }

    public void chat(String var1){

    }

    public boolean performCommand(String command){
        return false;
    }

    public void updateCommands(){

    }

    public void saveData(){

    }

    public void loadData(){

    }

    public Location getBedSpawnLocation(){
        return null;
    }

    public void setBedSpawnLocation(Location location){

    }

    public void setBedSpawnLocation(Location location,boolean force){

    }

    public boolean sendChunkChange(Location location,byte[] data){
        return false;
    }

    public boolean sendBlockChange(Location location,BlockData block){
        return false;
    }

    public void setPlayerTime(long time,boolean relative){

    }

    public long getPlayerTime(){
        return 0;
    }

    public void resetPlayerTime(){

    }

    public void hidePlayer(Player player){

    }

    public void showPlayer(Player player){

    }

    public boolean canSee(Player player){
        return false;
    }

    public boolean isOnGround(){
        return false;
    }

    public boolean isSneaking(){
        return false;
    }

    public void setSneaking(boolean sneaking){

    }

    public boolean isSprinting(){
        return false;
    }

    public void setSprinting(boolean sprinting){

    }

    public boolean getAllowFlight(){
        return false;
    }

    public void setAllowFlight(boolean flight){

    }

    public boolean isFlying(){
        return false;
    }

    public void setFlying(boolean flying){

    }

    public void setFlySpeed(float speed){

    }

    public float getFlySpeed(){
        return 0;
    }

    public void setWalkSpeed(float speed){

    }

    public float getWalkSpeed(){
        return 0;
    }

    public int getClientViewDistance(){
        return 0;
    }

    public void updateInventory(){

    }

    public PlayerInventory getInventory(){
        return null;
    }

    public Inventory getEnderChest(){
        return null;
    }

    public void closeInventory(){

    }

    public ItemStack getItemInHand(){
        return null;
    }

    public void setItemInHand(ItemStack var1){

    }

    public ItemStack getItemOnCursor(){
        return null;
    }

    public void setItemOnCursor(ItemStack var1){

    }

    public int getSleepTicks(){
        return 0;
    }

    public boolean sleep(Location var1,boolean var2){
        return false;
    }

    public void wakeup(boolean var1){

    }

    public Location getBedLocation(){
        return null;
    }

    public GameMode getGameMode(){
        return null;
    }

    public void setGameMode(GameMode var1){

    }

    public boolean isBlocking(){
        return false;
    }

    public boolean isHandRaised(){
        return false;
    }

    public int getExpToLevel(){
        return 0;
    }

    public float getAttackCooldown(){
        return 0;
    }

    public Entity getShoulderEntityLeft(){
        return null;
    }

    public void setShoulderEntityLeft(Entity var1){

    }

    public Entity getShoulderEntityRight(){
        return null;
    }

    public void setShoulderEntityRight(Entity var1){

    }

    public boolean dropItem(boolean var1){
        return false;
    }

}