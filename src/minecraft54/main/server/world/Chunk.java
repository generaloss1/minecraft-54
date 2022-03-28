package minecraft54.main.server.world;

import minecraft54.main.server.entity.Entity;

public interface Chunk{

    int getX();
    int getZ();

    World getWorld();

    Block getBlock(int localX,int localY,int localZ);

    Entity[] getEntities();

    boolean load();
    boolean unload();
    boolean isLoaded();

}
