package minecraft54.engine.graphics;

import minecraft54.engine.utils.Color;

public class Sprite{


    private TextureRegion textureRegion;
    private float x,y,width,height,rotation,originX,originY;
    private Color color;


    public Sprite(Texture texture,float width,float height){
        this.textureRegion=new TextureRegion(texture);
        this.width=width;
        this.height=height;
        this.color=new Color();
    }

    public Sprite(TextureRegion textureRegion,float width,float height){
        this.textureRegion=textureRegion;
        this.width=width;
        this.height=height;
        this.color=new Color();
    }


    public void setColor(Color color){
        this.color.set(color);
    }

    public void setColor(float r,float g,float b,float a){
        this.color.set(r,g,b,a);
    }

    public void setTextureRegion(TextureRegion textureRegion){
        this.textureRegion=textureRegion;
    }

    public void setTexture(Texture texture){
        this.textureRegion=new TextureRegion(texture);
    }

    public void setSize(float width,float height){
        this.width=width;
        this.height=height;
    }

    public void setPos(float x,float y){
        this.x=x;
        this.y=y;
    }

    public void translate(float x,float y){
        this.x+=x;
        this.y+=y;
    }

    public void setRotation(float rotation){
        this.rotation=rotation;
    }

    public void rotate(float angle){
        this.rotation+=angle;
    }


    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public TextureRegion getTextureRegion(){
        return textureRegion;
    }

    public Texture getTexture(){
        return textureRegion.getTexture();
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getRotation(){
        return rotation;
    }

    public Color getColor(){
        return color;
    }

}
