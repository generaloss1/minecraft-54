package minecraft54.main.server.item;

public interface Inventory{

    int getSize();

    ItemStack getItem(int var1);
    void setItem(int var1,ItemStack var2);

    void addItem(ItemStack... var1);
    void removeItem(ItemStack... var1);

    ItemStack[] getContents();
    void setContents(ItemStack[] var1);

    //boolean contains(Material var1);
    //boolean contains(ItemStack var1);
    //boolean contains(Material var1, int var2);
    //boolean contains(ItemStack var1, int var2);

    boolean isEmpty();

    //void remove(Material var1);
    void remove(ItemStack var1);

    void clear(int var1);
    void clear();

    //InventoryHolder getHolder();

    //Location getLocation();

}