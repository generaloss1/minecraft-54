package minecraft54.engine.graphics;

public class TextureRegion{


    private Texture texture;
    private float x,y,width,height;
    private float u1,v1,u2,v2;


    public TextureRegion(TextureRegion textureRegion){
        setTextureRegion(textureRegion);
    }

    public TextureRegion(Texture texture,int x,int y,int width,int height){
        setTextureRegion(texture,x,y,width,height);
    }

    public TextureRegion(Texture texture,float x,float y,float width,float height){
        setTextureRegion(texture,x,y,width,height);
    }

    public TextureRegion(Texture texture){
        setTextureRegion(texture,0f,0f,1f,1f);
    }

    public void setTextureRegion(TextureRegion textureRegion){
        texture=textureRegion.texture;
        x=textureRegion.x;
        y=textureRegion.y;
        width=textureRegion.width;
        height=textureRegion.height;
        u1=textureRegion.u1;
        v1=textureRegion.v1;
        u2=textureRegion.u2;
        v2=textureRegion.v2;
    }

    public void setTextureRegion(Texture texture,int x,int y,int width,int height){
        this.texture=texture;

        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        u1=(float)x/texture.getWidth();
        v1=(float)y/texture.getHeight();
        u2=(float)(width+x)/texture.getWidth();
        v2=(float)(height+y)/texture.getHeight();
    }

    public void setTextureRegion(Texture texture,float x,float y,float width,float height){
        this.texture=texture;

        this.x=x*texture.getWidth();
        this.y=y*texture.getHeight();
        this.width=width*texture.getWidth();
        this.height=height*texture.getHeight();
        u1=x;
        v1=y;
        u2=width+x;
        v2=height+y;
    }

    public void setRegion(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        u1=(float)x/texture.getWidth();
        v1=(float)y/texture.getHeight();
        u2=(float)(width+x)/texture.getWidth();
        v2=(float)(height+y)/texture.getHeight();
    }

    public void setRegion(float x,float y,float width,float height){
        this.x=x*texture.getWidth();
        this.y=y*texture.getHeight();
        this.width=width*texture.getWidth();
        this.height=height*texture.getHeight();
        u1=x;
        v1=y;
        u2=width+x;
        v2=height+y;
    }

    public void setTexture(Texture texture){
        this.texture=texture;
    }

    public float getU(){
        return u1;
    }
    public float getV(){
        return v1;
    }

    public float getU2(){
        return u2;
    }
    public float getV2(){
        return v2;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public Texture getTexture(){
        return texture;
    }

    public TextureRegion clone(){
        return new TextureRegion(this);
    }


}
