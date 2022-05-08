package engine54.tilemap;

import engine54.graphics.texture.Texture;
import engine54.graphics.texture.TextureRegion;

public class Tile{

    private TextureRegion textureRegion;
    private float relativeX,relativeY,relativeWidth,relativeHeight;
    private final int id,data;

    public Tile(int id,int data,TextureRegion textureRegion,double relativeX,double relativeY,double relativeWidth,double relativeHeight){
        this.id=id;
        this.data=data;
        setTextureRegion(textureRegion);
        setRelativePos(relativeX,relativeY);
        setRelativeSize(relativeWidth,relativeHeight);
    }

    public Tile(int id,int data,Texture texture,double relativeX,double relativeY,double relativeWidth,double relativeHeight){
        this.id=id;
        this.data=data;
        setTextureRegion(texture);
        setRelativePos(relativeX,relativeY);
        setRelativeSize(relativeWidth,relativeHeight);
    }

    public Tile(int id,int data,TextureRegion textureRegion){
        this.id=id;
        this.data=data;
        setTextureRegion(textureRegion);
        setRelativeSize(1,1);
    }

    public Tile(int id,int data,Texture texture){
        this.id=id;
        this.data=data;
        setTextureRegion(texture);
        setRelativeSize(1,1);
    }

    public void setTextureRegion(TextureRegion textureRegion){
        this.textureRegion=textureRegion;
    }

    public void setTextureRegion(Texture texture){
        if(textureRegion==null)
            textureRegion=new TextureRegion(texture);
        else
            textureRegion.setTextureRegion(texture,0d,0,1,1);
    }

    public TextureRegion getTextureRegion(){
        return textureRegion;
    }

    public void setRelativePos(double x,double y){
        relativeX=(float)x;
        relativeY=(float)y;
    }

    public void setRelativeSize(double width,double height){
        relativeWidth=(float)width;
        relativeHeight=(float)height;
    }

    public float getRelativeX(){
        return relativeX;
    }

    public float getRelativeY(){
        return relativeY;
    }

    public float getRelativeWidth(){
        return relativeWidth;
    }

    public float getRelativeHeight(){
        return relativeHeight;
    }

    public int getID(){
        return id;
    }

    public int getData(){
        return data;
    }

}
