package minecraft54.main.client.world;

import minecraft54.main.util.Direction;

import java.util.ArrayList;
import java.util.List;

public class Block{

    private final short id;
    private final List<BlockData> blockDataList;

    public Block(int id){
        this.id=(short)id;
        blockDataList=new ArrayList<>();
    }

    public void addData(int data,BlockData blockData){
        blockDataList.add(data,blockData);
    }

    public BlockData getBlockData(short data){
        if(data>=blockDataList.size())
            return null;
        return blockDataList.get(data);
    }

    public BlockData getBlockData(){
        return blockDataList.get(0);
    }

    public short getId(){
        return id;
    }






    public static class BlockSide{

        public static final int VERTEX_SIZE=3+3+4+1+1;

        public int render_layer;
        private final float[] mesh;
        public Direction direction;
        public final List<Short> interruptNeighbors;

        public BlockSide(Direction direction,int blockId,int render_layer,float[] vertices,float[] textures,float[] uvs,float[] color){
            interruptNeighbors=new ArrayList<>();
            this.direction=direction;
            this.render_layer=render_layer;
            int vertexCount=vertices.length/3;
            mesh=new float[vertexCount*VERTEX_SIZE];

            float shadow=(direction==Direction.WEST || direction==Direction.EAST)?0.8f:(direction==Direction.NORTH || direction==Direction.SOUTH)?0.575f:direction==Direction.DOWN?0.45f:1f;

            for(int v=0; v<vertexCount; v++){
                mesh[v*VERTEX_SIZE   ]=vertices[v*3  ];
                mesh[v*VERTEX_SIZE+1 ]=vertices[v*3+1];
                mesh[v*VERTEX_SIZE+2 ]=vertices[v*3+2];

                mesh[v*VERTEX_SIZE+3 ]=uvs[v*2  ];
                mesh[v*VERTEX_SIZE+4 ]=uvs[v*2+1];
                mesh[v*VERTEX_SIZE+5 ]=textures[v/6];

                mesh[v*VERTEX_SIZE+6 ]=color[v/6*4]*shadow;
                mesh[v*VERTEX_SIZE+7 ]=color[v/6*4+1]*shadow;
                mesh[v*VERTEX_SIZE+8 ]=color[v/6*4+2]*shadow;
                mesh[v*VERTEX_SIZE+9 ]=color[v/6*4+3];

                mesh[v*VERTEX_SIZE+10]=blockId;

                mesh[v*VERTEX_SIZE+11]=1;
            }
        }

        public BlockSide addInterruptNeighborBlocksIds(short... ids){
            for(short id:ids)
                interruptNeighbors.add(id);
            return this;
        }

        public float[] getMesh(){
            return mesh;
        }

    }



}