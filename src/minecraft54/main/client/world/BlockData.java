package minecraft54.main.client.world;

import minecraft54.main.client.audio.SoundPack;

public class BlockData{

    private final boolean solid,transparent,translucent;
    private final short id,data;
    public Block.BlockSide[] sides;
    public SoundPack sounds;

    public BlockData(int id,int data,boolean solid,boolean transparent,boolean translucent){
        this.id=(short)id;
        this.data=(short)data;
        this.solid=solid;
        this.transparent=transparent;
        this.translucent=translucent;
        sides=new Block.BlockSide[7];
    }

    public boolean equalsIdData(BlockData blockData){
        if(blockData==null)
            return false;
        return id==blockData.id && data==blockData.data;
    }

    public short getParentBlockId(){
        return id;
    }

    public short getData(){
        return data;
    }

    public boolean isSolid(){ // для AO
        return solid;
    }

    public boolean isTransparent(){ // хотя бы одна сторона содержит прозрачный пиксель
        return transparent;
    }

    public boolean isTranslucent(){ // хотя бы одна сторона содержит полупрозрачный пиксель
        return translucent;
    }

    public void setSounds(SoundPack sounds){
        this.sounds=sounds;
    }

    public SoundPack getSounds(){
        return sounds;
    }

}