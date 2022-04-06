package minecraft54.main.client.world;

import minecraft54.main.Minecraft54;

public class ChunkSectionData{


    public static final byte WIDTH_X=16;
    public static final byte HEIGHT=16;
    public static final byte WIDTH_Z=16;


    public final Chunk chunk;
    public final int y;
    final int[] blocks;
    short notAirBlockCount;
    boolean changed;


    public ChunkSectionData(Chunk chunk,int y){
        this.chunk=chunk;
        this.y=y;
        blocks=new int[WIDTH_X*HEIGHT*WIDTH_Z];
    }


    void setIdData(short id,short data,short lx,short ly,short lz,boolean updateNeighbors){
        try{
            int key=lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz;
            int oldIdData=blocks[key];

            int idData=data*Short.MAX_VALUE + id;
            if(idData==oldIdData)
                return;
            blocks[key]=idData;

            if(oldIdData==0 && id!=0)
                notAirBlockCount++;
            else if(oldIdData!=0 && id==0)
                notAirBlockCount--;

            changed=true;

            if(updateNeighbors){
                Chunk xChunk=lx==0?
                        chunk.world.chunkProvider.getChunk(chunk.x-1,chunk.z)
                        :
                        lx==WIDTH_X-1?
                                chunk.world.chunkProvider.getChunk(chunk.x+1,chunk.z)
                                :
                                null;
                if(xChunk!=null)
                    xChunk.sections[y].changed=true;

                if(ly==0)
                    chunk.sections[y-1].changed=true;
                else if(ly==HEIGHT-1)
                    chunk.sections[y+1].changed=true;

                Chunk zChunk=lz==0?
                        chunk.world.chunkProvider.getChunk(chunk.x,chunk.z-1)
                        :
                        lz==WIDTH_Z-1?
                                chunk.world.chunkProvider.getChunk(chunk.x,chunk.z+1)
                                :
                                null;
                if(zChunk!=null)
                    zChunk.sections[y].changed=true;
            }
        }catch(Exception ignored){}
    }

    void setIdData(int idData,short lx,short ly,short lz,boolean updateNeighbors){
        try{
            int key=lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz;
            int oldIdData=blocks[key];

            if(idData==oldIdData)
                return;
            blocks[key]=idData;

            if(oldIdData==0)
                notAirBlockCount++;
            else if(idData==0)
                notAirBlockCount--;

            changed=true;

            if(updateNeighbors){
                Chunk xChunk=lx==0?
                        chunk.world.chunkProvider.getChunk(chunk.x-1,chunk.z)
                        :
                        lx==WIDTH_X-1?
                                chunk.world.chunkProvider.getChunk(chunk.x+1,chunk.z)
                                :
                                null;
                if(xChunk!=null)
                    xChunk.sections[y].changed=true;

                if(ly==0)
                    chunk.sections[y-1].changed=true;
                else if(ly==HEIGHT-1)
                    chunk.sections[y+1].changed=true;

                Chunk zChunk=lz==0?
                        chunk.world.chunkProvider.getChunk(chunk.x,chunk.z-1)
                        :
                        lz==WIDTH_Z-1?
                                chunk.world.chunkProvider.getChunk(chunk.x,chunk.z+1)
                                :
                                null;
                if(zChunk!=null)
                    zChunk.sections[y].changed=true;
            }
        }catch(Exception ignored){}
    }

    void setId(short id,short lx,short ly,short lz,boolean updateNeighbors){
        try{
            int key=lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz;
            int blockId=blocks[key]%Short.MAX_VALUE;
            int blockData=(blocks[key]-blockId)/Short.MAX_VALUE;

            blocks[key]=blockData*Short.MAX_VALUE + id;

            if(blockId==0 && id!=0)
                notAirBlockCount++;
            else if(blockId!=0 && id==0)
                notAirBlockCount--;

            changed=true;

            if(updateNeighbors){
                Chunk xChunk=lx==0?
                        chunk.world.chunkProvider.getChunk(chunk.x-1,chunk.z)
                        :
                        lx==WIDTH_X-1?
                                chunk.world.chunkProvider.getChunk(chunk.x+1,chunk.z)
                                :
                                null;
                if(xChunk!=null)
                    xChunk.sections[y].changed=true;

                if(ly==0)
                    chunk.sections[y-1].changed=true;
                else if(ly==HEIGHT-1)
                    chunk.sections[y+1].changed=true;

                Chunk zChunk=lz==0?
                        chunk.world.chunkProvider.getChunk(chunk.x,chunk.z-1)
                        :
                        lz==WIDTH_Z-1?
                                chunk.world.chunkProvider.getChunk(chunk.x,chunk.z+1)
                                :
                                null;
                if(zChunk!=null)
                    zChunk.sections[y].changed=true;
            }
        }catch(Exception ignored){}
    }

    void setData(short data,short lx,short ly,short lz,boolean updateNeighbors){
        try{
            int key=lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz;
            int blockId=blocks[key]%Short.MAX_VALUE;
            blocks[key]=data*Short.MAX_VALUE + blockId;

            changed=true;

            if(updateNeighbors){
                Chunk xChunk=lx==0?
                        chunk.world.chunkProvider.getChunk(chunk.x-1,chunk.z)
                        :
                        lx==WIDTH_X-1?
                                chunk.world.chunkProvider.getChunk(chunk.x+1,chunk.z)
                                :
                                null;
                if(xChunk!=null)
                    xChunk.sections[y].changed=true;

                if(ly==0)
                    chunk.sections[y-1].changed=true;
                else if(ly==HEIGHT-1)
                    chunk.sections[y+1].changed=true;

                Chunk zChunk=lz==0?
                        chunk.world.chunkProvider.getChunk(chunk.x,chunk.z-1)
                        :
                        lz==WIDTH_Z-1?
                                chunk.world.chunkProvider.getChunk(chunk.x,chunk.z+1)
                                :
                                null;
                if(zChunk!=null)
                    zChunk.sections[y].changed=true;
            }
        }catch(Exception ignored){}
    }


    int getIdData(short lx,short ly,short lz){
        if(lx>-1 && ly>-1 && lz>-1 && lx<WIDTH_X && ly<HEIGHT && lz<WIDTH_Z)
            return blocks[lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz];
        return -1;
    }

    short getId(short lx,short ly,short lz){
        if(lx>-1 && ly>-1 && lz>-1 && lx<WIDTH_X && ly<HEIGHT && lz<WIDTH_Z)
            return (short)(blocks[lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz]%Short.MAX_VALUE);
        return -1;
    }

    short getData(short lx,short ly,short lz){
        if(lx>-1 && ly>-1 && lz>-1 && lx<WIDTH_X && ly<HEIGHT && lz<WIDTH_Z){
            int idData=blocks[lx*WIDTH_Z*HEIGHT + ly*WIDTH_Z + lz];
            return (short)((idData-idData%Short.MAX_VALUE)/Short.MAX_VALUE);
        }
        return -1;
    }


    void setBlock(Block block,short lx,short ly,short lz,boolean updateNeighbors){
        if(block!=null)
            setIdData(block.getId(),(short)0,lx,ly,lz,updateNeighbors);
    }

    void setBlock(BlockData block,short lx,short ly,short lz,boolean updateNeighbors){
        if(block!=null)
            setIdData(block.getParentBlockId(),block.getData(),lx,ly,lz,updateNeighbors);
    }

    BlockData getBlock(short lx,short ly,short lz){
        int idData=getIdData(lx,ly,lz);
        if(idData==0)
            return Minecraft54.AIR.getBlockData();
        short id=(short)(idData%Short.MAX_VALUE);
        Block block=BlockManager.getBlockFromId(id);
        if(block==null)
            return null;
        return block.getBlockData((short)((idData-id)/Short.MAX_VALUE));
    }


    public int[] getBlocks(){
        return blocks;
    }

    public boolean isEmpty(){
        return notAirBlockCount==0;
    }


}
