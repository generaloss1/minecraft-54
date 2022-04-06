package minecraft54.main.client.world;

import minecraft54.engine.util.FastArrayList;

public class ChunkProvider{


    public final FastArrayList<Chunk> loadedChunks;
    public World world;


    public ChunkProvider(World world){
        this.world=world;
        loadedChunks=new FastArrayList<>();
    }


    public boolean loadChunk(int chunkX,int chunkZ){
        Chunk chunk=getChunk(chunkX,chunkZ);
        if(!chunk.loaded)
            return chunk.load();
        return true;
    }

    public void unloadChunk(int chunkX,int chunkZ){
        for(int i=0; i<loadedChunks.size(); i++){
            Chunk chunk=loadedChunks.get(i);
            if(chunk!=null && chunk.x==chunkX && chunk.z==chunkZ){
                chunk.save();
                chunk.dispose();
                loadedChunks.remove(chunk);
                break;
            }
        }
    }

    public Chunk getChunk(int chunkX,int chunkZ){
        for(int i=0; i<loadedChunks.size(); i++){
            Chunk chunk=loadedChunks.get(i);
            if(chunk!=null && chunk.x==chunkX && chunk.z==chunkZ)
                return chunk;
        }
        Chunk chunk=new Chunk(world,chunkX,chunkZ);
        loadedChunks.add(chunk);
        return chunk;
    }

    public void unloadChunks(){
        for(int i=0; i<loadedChunks.size(); i++){
            Chunk chunk=loadedChunks.get(i);
            if(chunk==null)
                continue;
            chunk.save();
            chunk.dispose();
            loadedChunks.remove(chunk);
        }
    }

}
