package minecraft54.main.server.world;

import minecraft54.main.server.entity.Entity;
import minecraft54.main.server.entity.Player;
import minecraft54.main.util.Location;

import java.io.File;
import java.util.List;

public interface World{

    Chunk getChunkAt(int chunkX,int chunkZ);
    Block getBlockAt(int worldX,int worldY,int worldZ);

    void loadChunk(int chunkX,int chunkZ);
    void unloadChunk(int chunkX,int chunkZ);
    boolean isChunkLoaded(int chunkX,int chunkZ);
    Chunk[] getLoadedChunks();
    void save();

    Location getSpawnLocation();

    List<Player> getPlayers();
    List<Entity> getEntities();

    String getName();

    File getWorldFolder();

    ChunkGenerator getGenerator();
    void generate(int chunkX,int chunkZ);
    boolean isChunkGenerated(int chunkX,int chunkZ);
    long getSeed();

    long getTime();
    void setTime(long time);

}
