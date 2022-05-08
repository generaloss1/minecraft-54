package engine54.graphics.texture;

public class TextureRegion{


    private double u1,v1,u2,v2,regionWidth,regionHeight;
    private Texture texture;


    public TextureRegion(TextureRegion textureRegion){
        setTextureRegion(textureRegion);
    }

    public TextureRegion(Texture texture,int x,int y,int width,int height){
        setTextureRegion(texture,x,y,width,height);
    }

    public TextureRegion(Texture texture,double u1,double v1,double u2,double v2){
        setTextureRegion(texture,u1,v1,u2,v2);
    }

    public TextureRegion(Texture texture){
        setTextureRegion(texture,0d,0d,1d,1d);
    }

    public void setTextureRegion(TextureRegion textureRegion){
        texture=textureRegion.texture;
        regionWidth=textureRegion.regionWidth;
        regionHeight=textureRegion.regionHeight;
        u1=textureRegion.u1;
        v1=textureRegion.v1;
        u2=textureRegion.u2;
        v2=textureRegion.v2;
    }

    public void setTextureRegion(Texture texture,int x,int y,int width,int height){
        setTexture(texture);
        setRegion(x,y,width,height);
    }

    public void setTextureRegion(Texture texture,double u1,double v1,double u2,double v2){
        setTexture(texture);
        setRegion(u1,v1,u2,v2);
    }

    public void setRegion(int x,int y,int width,int height){
        setRegion(
                (double)(x)/texture.getWidth(),
                (double)(y)/texture.getHeight(),
                (double)(x+width)/texture.getWidth(),
                (double)(y+height)/texture.getHeight()
        );
    }

    public void setRegion(double u1,double v1,double u2,double v2){
        int texWidth=texture.getWidth();
        int texHeight=texture.getHeight();
        regionWidth=Math.round(Math.abs(u2-u1)*texWidth);
        regionHeight=Math.round(Math.abs(v2-v1)*texHeight);

        this.u1=u1;
        this.v1=v1;
        this.u2=u2;
        this.v2=v2;
    }

    public void setTexture(Texture texture){
        this.texture=texture;
    }

    public Texture getTexture(){
        return texture;
    }

    public float getU(){
        return (float)u1;
    }
    public float getV(){
        return (float)v1;
    }

    public float getU2(){
        return (float)u2;
    }
    public float getV2(){
        return (float)v2;
    }

    public float getRegionWidth(){
        return (float)regionWidth;
    }
    public float getRegionHeight(){
        return (float)regionHeight;
    }

    public TextureRegion clone(){
        return new TextureRegion(this);
    }

}
