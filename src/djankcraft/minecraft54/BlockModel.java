package djankcraft.minecraft54;

import djankcraft.engine.utils.Utils;

public class BlockModel{


    public static final BlockModel cube,grass,still_water,stairs;
    static{
        cube=new BlockModel();
        cube.setSide(0,quad(
                1,1,1,1,0,1,1,0,0,1,1,0
        )); // +x
        cube.setSide(1,quad(
                0,1,0,0,0,0,0,0,1,0,1,1
        )); // -x
        cube.setSide(2,quad(
                1,1,1,1,1,0,0,1,0,0,1,1
        )); // +y
        cube.setSide(3,quad(
                1,0,0,1,0,1,0,0,1,0,0,0
        )); // -y
        cube.setSide(4,quad(
                0,1,1,0,0,1,1,0,1,1,1,1
        )); // +z
        cube.setSide(5,quad(
                1,1,0,1,0,0,0,0,0,0,1,0
        )); // -z


        float min=0;//3/16f;
        float max=1-min;
        grass=new BlockModel();
        grass.setSide(6,
            Utils.add(
                quad(max,max-min,max,max,0,max,min,0,min,min,max-min,min),
                quad(min,max-min,max,min,0,max,max,0,min,max,max-min,min)
            )
        );

        float wymax=1-1.5f/16f;
        still_water=new BlockModel();
        still_water.setSide(0,quad(
                1,wymax,1,1,0,1,1,0,0,1,wymax,0
        )); // +x
        still_water.setSide(1,quad(
                0,wymax,0,0,0,0,0,0,1,0,wymax,1
        )); // -x
        still_water.setSide(6,quad(
                1,wymax,1,1,wymax,0,0,wymax,0,0,wymax,1
        )); // +y
        still_water.setSide(3,quad(
                1,0,0,1,0,1,0,0,1,0,0,0
        )); // -y
        still_water.setSide(4,quad(
                0,wymax,1,0,0,1,1,0,1,1,wymax,1
        )); // +z
        still_water.setSide(5,quad(
                1,wymax,0,1,0,0,0,0,0,0,wymax,0
        )); // -z

        stairs=new BlockModel();
        stairs.setSide(0,quad(
                1,0.5f,1,1,0,1,1,0,0,1,0.5f,0
        )); // +x
        stairs.setSide(1,quad(
                0,1,0,0,0,0,0,0,1,0,1,1
        )); // -x a
        stairs.setSide(2,quad(
                0.5f,1,1,0.5f,1,0,0,1,0,0,1,1
        )); // +y
        stairs.setSide(3,quad(
                1,0,0,1,0,1,0,0,1,0,0,0
        )); // -y a
        stairs.setSide(4,Utils.add(
                quad(0,1,1,0,0,1,0.5f,0,1,0.5f,1,1),quad(0.5f,0.5f,1,0.5f,0,1,1,0,1,1,0.5f,1)
        )); // +z
        stairs.setSide(5,Utils.add(
                quad(0.5f,1,0,0.5f,0,0,0,0,0,0,1,0),quad(1,0.5f,0,1,0,0,0.5f,0,0,0.5f,0.5f,0)
        )); // -z
        stairs.setSide(6,
            Utils.add(
                quad(1,0.5f,1,1,0.5f,0,0.5f,0.5f,0,0.5f,0.5f,1),quad(0.5f,1,1,0.5f,0.5f,1,0.5f,0.5f,0,0.5f,1,0)
            )
        );
    }


    private final float[][] vertices;


    public BlockModel(){
        vertices=new float[Chunk.SIDES][];
    }


    public void setSide(int side,float[] sideVertices){
        vertices[side]=sideVertices;
    }

    public float[] getSide(int side){
        return vertices[side];
    }


    public static float[] quad(float x1,float y1,float z1,float x2,float y2,float z2,float x3,float y3,float z3,float x4,float y4,float z4){
        return new float[]{x3,y3,z3,x2,y2,z2,x1,y1,z1,x1,y1,z1,x4,y4,z4,x3,y3,z3};
    }


}