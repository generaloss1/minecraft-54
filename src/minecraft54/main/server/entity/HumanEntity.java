package minecraft54.main.server.entity;

import minecraft54.main.util.GameMode;
import minecraft54.main.server.item.Inventory;
import minecraft54.main.server.item.ItemStack;
import minecraft54.main.server.item.PlayerInventory;
import minecraft54.main.util.Location;

public interface HumanEntity{

    PlayerInventory getInventory();

    Inventory getEnderChest();

    //MainHand getMainHand();
    //boolean setWindowProperty(Property var1, int var2);

    //InventoryView getOpenInventory();

    //InventoryView openInventory(Inventory var1);
    //InventoryView openWorkbench(Location var1,boolean var2);
    //InventoryView openEnchanting(Location var1, boolean var2);
    //InventoryView openMerchant(Villager var1, boolean var2);
    //InventoryView openMerchant(Merchant var1, boolean var2);

    //void openInventory(InventoryView var1);
    void closeInventory();

    ItemStack getItemInHand();
    void setItemInHand(ItemStack var1);

    ItemStack getItemOnCursor();
    void setItemOnCursor(ItemStack var1);

    //boolean hasCooldown(Material var1);
    //int getCooldown(Material var1);
    //void setCooldown(Material var1, int var2);

    int getSleepTicks();
    boolean sleep(Location var1, boolean var2);
    void wakeup(boolean var1);

    Location getBedLocation();

    GameMode getGameMode();
    void setGameMode(GameMode var1);

    boolean isBlocking();

    boolean isHandRaised();

    int getExpToLevel();

    float getAttackCooldown();

    //boolean discoverRecipe(NamespacedKey var1);
    //int discoverRecipes(Collection<NamespacedKey> var1);

    //boolean undiscoverRecipe(NamespacedKey var1);
    //int undiscoverRecipes(Collection<NamespacedKey> var1);

    //boolean hasDiscoveredRecipe(NamespacedKey var1);

    //Set<NamespacedKey> getDiscoveredRecipes();

    Entity getShoulderEntityLeft();
    void setShoulderEntityLeft(Entity var1);

    Entity getShoulderEntityRight();
    void setShoulderEntityRight(Entity var1);

    boolean dropItem(boolean var1);

}
