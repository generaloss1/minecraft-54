package minecraft54.minecraft54;

import minecraft54.engine.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;

public class Block{


    public int id;
    public int[] render_layer;
    public BlockModel model;
    public BlockTextureModel textures;
    public float[][] color;
    public int[][] showFor;

    public static HashMap<Integer,Block> fromId=new HashMap<>();


    public Block(int id,BlockModel model,BlockTextureModel textures){
        this.id=id;
        this.render_layer=new int[]{1,1,1,1,1,1,1};
        this.model=model;
        this.textures=textures;
        showFor=new int[7][0];
        color=new float[7][4];
        Arrays.fill(color[0],1);
        Arrays.fill(color[1],1);
        Arrays.fill(color[2],1);
        Arrays.fill(color[3],1);
        Arrays.fill(color[4],1);
        Arrays.fill(color[5],1);
        Arrays.fill(color[6],1);

        fromId.put(id,this);
    }


    public void setColor(int side,float r,float g,float b,float a){
        color[side][0]=r;
        color[side][1]=g;
        color[side][2]=b;
        color[side][3]=a;
    }

    public void setColor(float r,float g,float b,float a){
        color[0][0]=r; color[0][1]=g; color[0][2]=b; color[0][3]=a;
        color[1][0]=r; color[1][1]=g; color[1][2]=b; color[1][3]=a;
        color[2][0]=r; color[2][1]=g; color[2][2]=b; color[2][3]=a;
        color[3][0]=r; color[3][1]=g; color[3][2]=b; color[3][3]=a;
        color[4][0]=r; color[4][1]=g; color[4][2]=b; color[4][3]=a;
        color[5][0]=r; color[5][1]=g; color[5][2]=b; color[5][3]=a;
        color[6][0]=r; color[6][1]=g; color[6][2]=b; color[6][3]=a;
    }

    public void setShowFor(byte side,int... id){
        showFor[side]=id;
    }

    public void setShowFor(int... id){
        showFor[0]=id;
        showFor[1]=id;
        showFor[2]=id;
        showFor[3]=id;
        showFor[4]=id;
        showFor[5]=id;
        showFor[6]=id;
    }



    public static int ALL=Integer.MAX_VALUE;

    public static final Block AIR,GRASS_BLOCK,DIRT,STONE,SAND,OAK_LOG,OAK_LEAVES,BIRCH_LOG,WATER,STILL_WATER,GLASS,GRASS,COBBLESTONE,PLANKS,CHEST,OAK_STAIRS;
    static{
        float gcr=0.4f;float lcr=0.2f;
        float gcg=0.9f;float lcg=0.9f;
        float gcb=0.3f;float lcb=0.1f;

        float wymax=1.5f/16f;

        int id=0;
        AIR=new Block(id,null,null);

        id++;
        GRASS_BLOCK=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(0)) // px
                .setSide(1,BlockTextureModel.quad(0)) // mx
                .setSide(2,BlockTextureModel.quad(1)) // py
                .setSide(3,BlockTextureModel.quad(2)) // my
                .setSide(4,BlockTextureModel.quad(0)) // pz
                .setSide(5,BlockTextureModel.quad(0)) // mz
        );
        GRASS_BLOCK.setColor(2,0.45f*gcr,0.8f*gcg,0.45f*gcb,1);

        id++;
        DIRT=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(2)) // px
                .setSide(1,BlockTextureModel.quad(2)) // mx
                .setSide(2,BlockTextureModel.quad(2)) // py
                .setSide(3,BlockTextureModel.quad(2)) // my
                .setSide(4,BlockTextureModel.quad(2)) // pz
                .setSide(5,BlockTextureModel.quad(2)) // mz
        );

        id++;
        STONE=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(3)) // px
                .setSide(1,BlockTextureModel.quad(3)) // mx
                .setSide(2,BlockTextureModel.quad(3)) // py
                .setSide(3,BlockTextureModel.quad(3)) // my
                .setSide(4,BlockTextureModel.quad(3)) // pz
                .setSide(5,BlockTextureModel.quad(3)) // mz
        );

        id++;
        COBBLESTONE=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(12)) // px
                .setSide(1,BlockTextureModel.quad(12)) // mx
                .setSide(2,BlockTextureModel.quad(12)) // py
                .setSide(3,BlockTextureModel.quad(12)) // my
                .setSide(4,BlockTextureModel.quad(12)) // pz
                .setSide(5,BlockTextureModel.quad(12)) // mz
        );

        id++;
        SAND=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(4)) // px
                .setSide(1,BlockTextureModel.quad(4)) // mx
                .setSide(2,BlockTextureModel.quad(4)) // py
                .setSide(3,BlockTextureModel.quad(4)) // my
                .setSide(4,BlockTextureModel.quad(4)) // pz
                .setSide(5,BlockTextureModel.quad(4)) // mz
        );

        id++;
        OAK_LOG=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(5)) // px
                .setSide(1,BlockTextureModel.quad(5)) // mx
                .setSide(2,BlockTextureModel.quad(6)) // py
                .setSide(3,BlockTextureModel.quad(6)) // my
                .setSide(4,BlockTextureModel.quad(5)) // pz
                .setSide(5,BlockTextureModel.quad(5)) // mz
        );

        id++;
        OAK_LEAVES=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(7)) // px
                .setSide(1,BlockTextureModel.quad(7)) // mx
                .setSide(2,BlockTextureModel.quad(7)) // py
                .setSide(3,BlockTextureModel.quad(7)) // my
                .setSide(4,BlockTextureModel.quad(7)) // pz
                .setSide(5,BlockTextureModel.quad(7)) // mz
        );
        OAK_LEAVES.setShowFor(ALL,id);
        OAK_LEAVES.setColor(0.45f*lcr,0.8f*lcg,0.45f*lcb,1);

        id++;
        BIRCH_LOG=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(8)) // px
                .setSide(1,BlockTextureModel.quad(8)) // mx
                .setSide(2,BlockTextureModel.quad(9)) // py
                .setSide(3,BlockTextureModel.quad(9)) // my
                .setSide(4,BlockTextureModel.quad(8)) // pz
                .setSide(5,BlockTextureModel.quad(8)) // mz
        );

        id++;
        WATER=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(13)) // px
                .setSide(1,BlockTextureModel.quad(13)) // mx
                .setSide(2,BlockTextureModel.quad(13)) // py
                .setSide(3,BlockTextureModel.quad(13)) // my
                .setSide(4,BlockTextureModel.quad(13)) // pz
                .setSide(5,BlockTextureModel.quad(13)) // mz
        );
        WATER.setShowFor(ALL,-(id+1));
        WATER.render_layer=new int[]{4,4,3,3,4,4,3};
        //WATER.setColor(0.3f,0.85f,1,1);
        //WATER.setColor(0,0.5f,0.7f,0.9f);
        //WATER.setColor(1,1,1,0.85f);
        WATER.setColor(0,0.4f,0.9f,1);

        id++;
        STILL_WATER=new Block(id,BlockModel.still_water,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(13,0,0,1,1-wymax)) // px
                .setSide(1,BlockTextureModel.quad(13,0,0,1,1-wymax)) // mx
                .setSide(6,BlockTextureModel.quad(13)) // py
                .setSide(3,BlockTextureModel.quad(13)) // my
                .setSide(4,BlockTextureModel.quad(13,0,0,1,1-wymax)) // pz
                .setSide(5,BlockTextureModel.quad(13,0,0,1,1-wymax)) // mz
        );
        STILL_WATER.setShowFor(ALL,-(id-1));
        STILL_WATER.render_layer=new int[]{4,4,3,3,4,4,3};
        //WATER.setColor(0.3f,0.85f,1,1);
        //STILL_WATER.setColor(0,0.5f,0.7f,0.9f);
        //STILL_WATER.setColor(1,1,1,0.85f);
        STILL_WATER.setColor(0,0.4f,0.9f,1);

        id++;
        GLASS=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(10)) // px
                .setSide(1,BlockTextureModel.quad(10)) // mx
                .setSide(2,BlockTextureModel.quad(10)) // py
                .setSide(3,BlockTextureModel.quad(10)) // my
                .setSide(4,BlockTextureModel.quad(10)) // pz
                .setSide(5,BlockTextureModel.quad(10)) // mz
        );
        GLASS.setShowFor(ALL,WATER.id);
        //GLASS.render_layer=new int[]{4,4,4,4,4,4,4};

        id++;
        GRASS=new Block(id,BlockModel.grass,new BlockTextureModel()
            .setSide(6,Utils.add(
                BlockTextureModel.quad(11),BlockTextureModel.quad(11)
            ))
        );
        GRASS.setShowFor(ALL);
        GRASS.render_layer=new int[]{2,2,2,2,2,2,2};
        GRASS.setColor(6,0.45f*gcr,0.8f*gcg,0.45f*gcb,1);

        id++;
        PLANKS=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(14)) // px
                .setSide(1,BlockTextureModel.quad(14)) // mx
                .setSide(2,BlockTextureModel.quad(14)) // py
                .setSide(3,BlockTextureModel.quad(14)) // my
                .setSide(4,BlockTextureModel.quad(14)) // pz
                .setSide(5,BlockTextureModel.quad(14)) // mz
        );

        id++;
        CHEST=new Block(id,BlockModel.cube,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(15)) // px
                .setSide(1,BlockTextureModel.quad(18)) // mx
                .setSide(2,BlockTextureModel.quad(17)) // py
                .setSide(3,BlockTextureModel.quad(17)) // my
                .setSide(4,BlockTextureModel.quad(16)) // pz
                .setSide(5,BlockTextureModel.quad(16)) // mz
        );

        id++;
        OAK_STAIRS=new Block(id,BlockModel.stairs,new BlockTextureModel()
                .setSide(0,BlockTextureModel.quad(14,0,0.5f,1,1)) // px
                .setSide(1,BlockTextureModel.quad(14)) // mx
                .setSide(2,BlockTextureModel.quad(14,0.5f,0,1,1)) // py
                .setSide(3,BlockTextureModel.quad(14)) // my
                .setSide(4,Utils.add(
                        BlockTextureModel.quad(14,0,0,0.5f,1),BlockTextureModel.quad(14,0.5f,0.5f,1,1)
                )) // pz
                .setSide(5,Utils.add(
                        BlockTextureModel.quad(14,0.5f,0,1,1),BlockTextureModel.quad(14,0,0.5f,0.5f,1)
                )) // mz
                .setSide(6,Utils.add(
                        BlockTextureModel.quad(14,0,0,0.5f,1),BlockTextureModel.quad(14,0,0,1,0.5f)
                ))
        );
        OAK_STAIRS.setColor(6,0.85f,0.85f,0.85f,1);
        OAK_STAIRS.setShowFor(ALL,id);
        OAK_STAIRS.setShowFor((byte)1,id);
        OAK_STAIRS.setShowFor((byte)3,id);

    }



}
