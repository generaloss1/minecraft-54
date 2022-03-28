package minecraft54.main.server.item;

public interface PlayerInventory extends Inventory{

    ItemStack[] getArmorContents();
    ItemStack[] getExtraContents();
    void setArmorContents(ItemStack[] var1);
    void setExtraContents(ItemStack[] var1);

    ItemStack getHelmet();
    ItemStack getChestplate();
    ItemStack getLeggings();
    ItemStack getBoots();
    void setHelmet(ItemStack var1);
    void setChestplate(ItemStack var1);
    void setLeggings(ItemStack var1);
    void setBoots(ItemStack var1);

    void setItem(int var1,ItemStack var2);
    //void setItem(EquipmentSlot var1,ItemStack var2);
    //ItemStack getItem(EquipmentSlot var1);

    ItemStack getItemInMainHand();
    void setItemInMainHand(ItemStack var1);
    ItemStack getItemInOffHand();
    void setItemInOffHand(ItemStack var1);
    ItemStack getItemInHand();
    void setItemInHand(ItemStack var1);

    int getHeldItemSlot();
    void setHeldItemSlot(int var1);

}