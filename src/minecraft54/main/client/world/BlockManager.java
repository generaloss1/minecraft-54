package minecraft54.main.client.world;

import minecraft54.main.util.Direction;

import java.util.HashMap;

public class BlockManager{

    public static final HashMap<Short,Block> blockFromId=new HashMap<>();

    public static void addBlocks(Block... blocks){
        for(Block block: blocks)
            blockFromId.put(block.getId(),block);
    }

    public static Block getBlockFromId(short id){
        return blockFromId.get(id);
    }


    public static BlockData blockDataSolid(int id,int data,int t){
        BlockData result=new BlockData(id,data,true,false,false);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(int id,int data,int tSide,int tUD){
        BlockData result=new BlockData(id,data,true,false,false);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{tUD},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{tUD},quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(int id,int data,int tSide,int tU,int tD){
        BlockData result=new BlockData(id,data,true,false,false);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{tU}   ,quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{tD}   ,quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(int id,int data,int tN,int tS,int tE,int tW,int tU,int tD){
        BlockData result=new BlockData(id,data,true,false,false);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{tN},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{tS},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{tE},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{tW},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{tU},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{tD},quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(boolean transparent,boolean translucent,int id,int data,int t){
        BlockData result=new BlockData(id,data,true,transparent,translucent);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{t},quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(boolean transparent,boolean translucent,int id,int data,int tSide,int tUD){
        BlockData result=new BlockData(id,data,true,transparent,translucent);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{tUD}  ,quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{tUD}  ,quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(boolean transparent,boolean translucent,int id,int data,int tSide,int tU,int tD){
        BlockData result=new BlockData(id,data,true,transparent,translucent);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{tSide},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{tU}   ,quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{tD}   ,quad_uvs(),new float[]{1,1,1,1});
        return result;
    }

    public static BlockData blockDataSolid(boolean transparent,boolean translucent,int id,int data,int tN,int tS,int tE,int tW,int tU,int tD){
        BlockData result=new BlockData(id,data,true,transparent,translucent);
        result.sides[Direction.NORTH.ordinal()]=new Block.BlockSide(Direction.NORTH,id,0,quad_vertices(1,1,1,1,0,1,1,0,0,1,1,0),new float[]{tN},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.SOUTH.ordinal()]=new Block.BlockSide(Direction.SOUTH,id,0,quad_vertices(0,1,0,0,0,0,0,0,1,0,1,1),new float[]{tS},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.EAST.ordinal() ]=new Block.BlockSide(Direction.EAST ,id,0,quad_vertices(0,1,1,0,0,1,1,0,1,1,1,1),new float[]{tE},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.WEST.ordinal() ]=new Block.BlockSide(Direction.WEST ,id,0,quad_vertices(1,1,0,1,0,0,0,0,0,0,1,0),new float[]{tW},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.UP.ordinal()   ]=new Block.BlockSide(Direction.UP   ,id,0,quad_vertices(1,1,1,1,1,0,0,1,0,0,1,1),new float[]{tU},quad_uvs(),new float[]{1,1,1,1});
        result.sides[Direction.DOWN.ordinal() ]=new Block.BlockSide(Direction.DOWN ,id,0,quad_vertices(1,0,0,1,0,1,0,0,1,0,0,0),new float[]{tD},quad_uvs(),new float[]{1,1,1,1});
        return result;
    }


    public static float[] quad_vertices(float x1,float y1,float z1,float x2,float y2,float z2,float x3,float y3,float z3,float x4,float y4,float z4){
        return new float[]{x3,y3,z3, x2,y2,z2, x1,y1,z1, x1,y1,z1, x4,y4,z4, x3,y3,z3};
        //return new float[]{x1,y1,z1, x2,y2,z2, x3,y3,z3, x3,y3,z3, x4,y4,z4, x1,y1,z1,};
    }

    public static float[] quad_uvs(){
        return new float[]{1,1, 0,1, 0,0, 0,0, 1,0, 1,1};
        //return new float[]{0,0, 0,1, 1,1, 1,1, 1,0, 0,0,};
    }

    public static float[] quad_uvs(float u1,float v1,float u2,float v2){
        return new float[]{u2,v2, u1,v2, u1,v1, u1,v1, u2,v1, u2,v2};
        //return new float[]{u2,v2, u1,v2, u1,v1, u1,v1, u2,v1, u2,v2};
    }

}
