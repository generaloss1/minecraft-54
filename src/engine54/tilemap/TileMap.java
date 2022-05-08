package engine54.tilemap;

import engine54.E54;
import engine54.graphics.SpriteBatch;
import engine54.graphics.camera.Camera;
import engine54.graphics.camera.CenteredOrthographicCamera;
import engine54.maths.vectors.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class TileMap{

    private TileSet tileSet;
    private final List<TileMapLayer> layers;
    private final int chunksNumX,chunksNumY,chunkWidth,chunkHeight;
    private float offsetX,offsetY,tilesNum;

    public TileMap(int chunkWidth,int chunkHeight,int chunksNumX,int chunksNumY,float offsetX,float offsetY,float tilesNum){
        this.chunkWidth=chunkWidth;
        this.chunkHeight=chunkHeight;
        this.chunksNumX=chunksNumX;
        this.chunksNumY=chunksNumY;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        this.tilesNum=tilesNum;
        layers=new ArrayList<>();
    }

    public void render(SpriteBatch batch,CenteredOrthographicCamera cam,float x,float y){
        for(TileMapLayer layer: layers)
            layer.render(batch,cam,x,y);
    }

    public Vector2f getAllocatedTile(CenteredOrthographicCamera cam){
        int h=E54.getWinHeight();
        float winPixel=tilesNum/cam.getScale()/h;
        int tx=E54.mouse().getX();
        int ty=h-E54.mouse().getY();
        float x=(tx-E54.getWinWidth()/2f)*winPixel-0.5f;
        float y=(ty-h                /2f)*winPixel-0.5f;
        return new Vector2f(x,y);
    }

    public int createLayer(){
        layers.add(new TileMapLayer(this,chunkWidth,chunkHeight,chunksNumX,chunksNumY,offsetX,offsetY));
        return layers.size()-1;
    }

    public TileMapLayer layer(int z){
        return layers.get(z);
    }

    public void setOffset(float x,float y){
        offsetX=x;
        offsetY=y;
        for(TileMapLayer layer: layers)
            layer.setOffset(x,y);
    }

    public float getTilesNum(){
        return tilesNum;
    }

    public float getTileSize(Camera cam){
        return cam.getHeight()/tilesNum;
    }

    public float getOffsetX(){
        return offsetX;
    }

    public float getOffsetY(){
        return offsetY;
    }

    public TileSet getTileSet(){
        return tileSet;
    }

    public void setTileSet(TileSet tileSet){
        this.tileSet=tileSet;
    }

    public int getChunksNumX(){
        return chunksNumX;
    }

    public int getChunksNumY(){
        return chunksNumY;
    }

}
