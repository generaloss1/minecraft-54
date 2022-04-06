package minecraft54.main.client.world;

import java.io.*;
import java.util.Objects;

public class Chunk{


    public static final byte SECTION_COUNT=16;
    public static final byte WIDTH_X=16;
    public static final short HEIGHT=ChunkSection.HEIGHT*SECTION_COUNT;
    public static final byte WIDTH_Z=16;


    public final World world;
    public final int x,z;
    public boolean generated,generation,init,loaded;
    public ChunkSection[] sections;


    public Chunk(World world,int x,int z){
        super();

        this.world=world;
        this.x=x;
        this.z=z;

        sections=new ChunkSection[SECTION_COUNT];
        for(int i=0; i<sections.length; i++)
            sections[i]=new ChunkSection(this,i);
    }


    public void init(){
        if(init)
            return;
        init=true;

        for(ChunkSection section: sections)
            section.init();
    }

    public void changed(boolean changed){
        for(ChunkSection section: sections)
            section.changed=changed;
    }

    public void build(){
        for(ChunkSection section: sections)
            if(section.notAirBlockCount!=0 && section.changed)
                section.buildOld();
    }

    public void update(){
        if(init)
            for(ChunkSection section: sections)
                section.update();
    }



    public void setBlock(BlockData block,int lx,int ly,int lz,boolean updateNeighbors){
        int index=ly/ChunkSection.HEIGHT;
        if(index<0 || index>=SECTION_COUNT)
            return;
        sections[index].setBlock(block,(short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz,updateNeighbors);
    }

    public void setBlock(Block block,int lx,int ly,int lz,boolean updateNeighbors){
        int index=ly/ChunkSection.HEIGHT;
        if(index<0 || index>=SECTION_COUNT)
            return;
        sections[index].setBlock(block,(short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz,updateNeighbors);
    }

    public void setBlockIdData(short id,short data,int lx,int ly,int lz,boolean updateNeighbors){
        int index=ly/ChunkSection.HEIGHT;
        if(index<0 || index>=SECTION_COUNT)
            return;
        sections[index].setIdData(id,data,(short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz,updateNeighbors);
    }

    public void setBlockIdData(int idData,int lx,int ly,int lz,boolean updateNeighbors){
        int index=ly/ChunkSection.HEIGHT;
        if(index<0 || index>=SECTION_COUNT)
            return;
        sections[index].setIdData(idData,(short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz,updateNeighbors);
    }

    public void setBlockId(short id,int lx,int ly,int lz,boolean updateNeighbors){
        int index=ly/ChunkSection.HEIGHT;
        if(index<0 || index>=SECTION_COUNT)
            return;
        sections[index].setId(id,(short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz,updateNeighbors);
    }

    public void setBlockData(short data,int lx,int ly,int lz,boolean updateNeighbors){
        int index=ly/ChunkSection.HEIGHT;
        if(index<0 || index>=SECTION_COUNT)
            return;
        sections[index].setData(data,(short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz,updateNeighbors);
    }


    public BlockData getBlock(int lx,int ly,int lz){
        int index=ly/ChunkSection.HEIGHT;
        return index<0||index>=SECTION_COUNT ?null: sections[index].getBlock((short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz);
    }

    public int getBlockIdData(int lx,int ly,int lz){
        int index=ly/ChunkSection.HEIGHT;
        return index<0||index>=SECTION_COUNT ?-1: sections[index].getIdData((short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz);
    }

    public short getBlockId(int lx,int ly,int lz){
        int index=ly/ChunkSection.HEIGHT;
        return index<0||index>=SECTION_COUNT ?-1: sections[index].getId((short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz);
    }

    public short getBlockData(int lx,int ly,int lz){
        int index=ly/ChunkSection.HEIGHT;
        return index<0||index>=SECTION_COUNT ?-1: sections[index].getData((short)lx,(short)(ly-ChunkSection.HEIGHT*index),(short)lz);
    }



    public boolean load(){
        try{
            File chunkFile=new File(world.worldPath+"/chunks/"+x+":"+z);
            if(chunkFile.exists() && chunkFile.canRead()){
                ObjectInputStream in=new ObjectInputStream(new FileInputStream(chunkFile));

                generated=in.readBoolean();
                for(ChunkSection section:sections){
                    section.notAirBlockCount=in.readShort();
                    for(int i=0; i<section.blocks.length; i++)
                        section.blocks[i]=in.readInt();
                }

                in.close();
                changed(true);

                loaded=true;

                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void save(){
        try{
            File chunkFile=new File(world.worldPath+"/chunks/"+x+":"+z);
            chunkFile.createNewFile();
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(chunkFile));
            out.writeBoolean(generated);
            for(ChunkSection section: sections){
                out.writeShort(section.notAirBlockCount);
                for(int idData: section.blocks)
                    out.writeInt(idData);
            }
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void dispose(){
        for(ChunkSection section: sections)
            section.dispose();
    }



    @Override
    public boolean equals(Object o){
        if(o==null || getClass()!=o.getClass())
            return false;
        Chunk chunk=(Chunk)o;
        return x==chunk.x && z==chunk.z && generated==chunk.generated && init==chunk.init;
    }



}
