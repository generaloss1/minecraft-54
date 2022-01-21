package djankcraft.minecraft54;

public class BlockTextureModel{


    public final float[][] textures;


    public BlockTextureModel(){
        textures=new float[Chunk.SIDES][];
    }


    public BlockTextureModel setSide(int side,float[] texture){
        textures[side]=texture;
        return this;
    }


    public static float[] quad(int textureID){
        return new float[]{1,1,textureID,0,1,textureID,0,0,textureID,0,0,textureID,1,0,textureID,1,1,textureID};
    }

    public static float[] quad(int textureID,float u1,float v1,float u2,float v2){
        return new float[]{u2,v2,textureID,u1,v2,textureID,u1,v1,textureID,u1,v1,textureID,u2,v1,textureID,u2,v2,textureID};
    }


}
