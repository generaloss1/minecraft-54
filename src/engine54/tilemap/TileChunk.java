package engine54.tilemap;

import engine54.graphics.*;
import engine54.graphics.camera.CenteredOrthographicCamera;
import engine54.maths.Maths;

public class TileChunk{

    private final TileMapLayer parent;
    private final int x,y;
    private final int[] tiles,colors;

    public TileChunk(TileMapLayer parent,int x,int y){
        this.parent=parent;
        this.x=x;
        this.y=y;
        tiles=new int[parent.getChunkWidth()*parent.getChunkHeight()];
        colors=new int[parent.getChunkWidth()*parent.getChunkHeight()];

        for(int i=0; i<parent.getWidth(); i++)
            for(int j=0; j<parent.getWidth(); j++)
                setColor(0xffffffff,i,j);
    }

    public void render(SpriteBatch batch,CenteredOrthographicCamera cam,float x,float y){
        float tileSize=parent.getParent().getTileSize(cam);
        int chunkWidth=parent.getChunkWidth();
        int chunkHeight=parent.getChunkHeight();
        float offsetX=parent.getOffsetX();
        float offsetY=parent.getOffsetY();

        int iEnd=  Maths.round(Math.min(chunkWidth ,Maths.ceil( (cam.x()+cam.getWidth() /2/tileSize/cam.getScale()-x)/offsetX)-(this.x*parent.getChunkWidth() )));
        int jEnd=  Maths.round(Math.min(chunkHeight,Maths.ceil( (cam.y()+cam.getHeight()/2/tileSize/cam.getScale()-y)/offsetY)-(this.y*parent.getChunkHeight())));
        int iBegin=Maths.round(Math.max(0,          Maths.floor((cam.x()-cam.getWidth() /2/tileSize/cam.getScale()-x)/offsetX)-(this.x*parent.getChunkWidth() )));
        int jBegin=Maths.round(Math.max(0,          Maths.floor((cam.y()-cam.getHeight()/2/tileSize/cam.getScale()-y)/offsetY)-(this.y*parent.getChunkHeight())));

        for(int i=iBegin; i<iEnd; i++)
            for(int j=jBegin; j<jEnd; j++){
                int idData=tiles[j*chunkWidth+i];
                if(idData==0)
                    continue;
                int id=idData%Short.MAX_VALUE;
                int data=(idData-id)/Short.MAX_VALUE;
                Tile tile=parent.getParent().getTileSet().getTile(id,data);
                if(tile==null)
                    continue;

                batch.getColor().set(colors[i*parent.getChunkWidth()+j]);

                batch.draw(tile.getTextureRegion(),
                        offsetX*tileSize*(this.x*chunkWidth +i+tile.getRelativeX()+x),
                        offsetY*tileSize*(this.y*chunkHeight+j+tile.getRelativeY()+y),
                        offsetX*tileSize*tile.getRelativeWidth()                     ,
                        offsetY*tileSize*tile.getRelativeHeight()
                );
            }

        batch.setColor(1,1,1,1);
    }

    public void setColor(int color,int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return;
        colors[y*parent.getChunkWidth()+x]=color;
    }

    public int getColor(int x,int y){
        return colors[y*parent.getChunkWidth()+x];
    }

    public void setTile(int id,int data,int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return;
        tiles[y*parent.getChunkWidth()+x]=data*Short.MAX_VALUE+id;
    }

    public void setTileID(int id,int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return;
        int idData=tiles[y*parent.getChunkWidth()+x];
        int data=(idData-id)/Short.MAX_VALUE;
        tiles[y*parent.getChunkWidth()+x]=data*Short.MAX_VALUE+id;
    }

    public void setTileData(int data,int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return;
        int id=tiles[y*parent.getChunkWidth()+x]%Short.MAX_VALUE;
        tiles[y*parent.getChunkWidth()+x]=data*Short.MAX_VALUE+id;
    }

    public int getTileID(int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return -1;
        return tiles[y*parent.getChunkWidth()+x]%Short.MAX_VALUE;
    }

    public int getTileData(int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return -1;
        int idData=tiles[y*parent.getChunkWidth()+x];
        int id=idData%Short.MAX_VALUE;
        return (idData-id)/Short.MAX_VALUE;
    }

    public Tile getTile(int x,int y){
        if(x<0 || y<0 || x>=parent.getChunkWidth() || y>=parent.getChunkHeight())
            return null;
        int idData=tiles[y*parent.getChunkWidth()+x];
        int id=idData%Short.MAX_VALUE;
        int data=(idData-id)/Short.MAX_VALUE;
        return parent.getParent().getTileSet().getTile(id,data);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
