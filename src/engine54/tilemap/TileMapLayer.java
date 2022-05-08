package engine54.tilemap;

import engine54.graphics.SpriteBatch;
import engine54.graphics.camera.CenteredOrthographicCamera;

public class TileMapLayer{

    private final TileMap parent;
    private final int chunksNumX, chunksNumY;
    private final TileChunk[] chunks;
    private float offsetX,offsetY;
    private final int chunkWidth,chunkHeight;

    public TileMapLayer(TileMap parent,int chunkWidth,int chunkHeight,int chunksNumX,int chunksNumY,float offsetX,float offsetY){
        this.parent=parent;
        this.chunkWidth=chunkWidth;
        this.chunkHeight=chunkHeight;
        this.chunksNumX=chunksNumX;
        this.chunksNumY=chunksNumY;
        this.offsetX=offsetX;
        this.offsetY=offsetY;
        chunks=new TileChunk[chunksNumX*chunksNumY];
        for(int x=0; x<chunksNumX; x++)
            for(int y=0; y<chunksNumY; y++)
                chunks[y*chunksNumX+x]=new TileChunk(this,x,y);
    }

    public void render(SpriteBatch batch,CenteredOrthographicCamera cam,float x,float y){
        for(TileChunk chunk: chunks)
            chunk.render(batch,cam,x,y);
    }

    public void setColor(int color,int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return;
        chunks[chunkY*chunksNumX+chunkX].setColor(color,x%chunkWidth,y%chunkHeight);
    }

    public int getColor(int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return 0xffffffff;
        return chunks[chunkY*chunksNumX+chunkX].getColor(x%chunkWidth,y%chunkHeight);
    }

    public void setTile(int id,int data,int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return;
        chunks[chunkY*chunksNumX+chunkX].setTile(id,data,x%chunkWidth,y%chunkHeight);
    }

    public void setTileID(int id,int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return;
        chunks[chunkY*chunksNumX+chunkX].setTileID(id,x%chunkWidth,y%chunkHeight);
    }

    public void setTileData(int data,int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return;
        chunks[chunkY*chunksNumX+chunkX].setTileData(data,x%chunkWidth,y%chunkHeight);
    }

    public Tile getTile(int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return null;
        return chunks[chunkY*chunksNumX+chunkX].getTile(x%chunkWidth,y%chunkHeight);
    }

    public int getTileID(int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return -1;
        return chunks[chunkY*chunksNumX+chunkX].getTileID(x%chunkWidth,y%chunkHeight);
    }

    public int getTileData(int x,int y){
        int chunkX=x/chunkWidth;
        int chunkY=y/chunkHeight;
        if(chunkX<0 || chunkY<0 || chunkX>=chunksNumX || chunkY>=chunksNumY)
            return -1;
        return chunks[chunkY*chunksNumX+chunkX].getTileData(x%chunkWidth,y%chunkHeight);
    }

    public void setOffset(float x,float y){
        offsetX=x;
        offsetY=y;
    }

    public float getOffsetX(){
        return offsetX;
    }

    public float getOffsetY(){
        return offsetY;
    }

    public TileChunk getChunk(int x,int y){
        return chunks[y*chunksNumX+x];
    }

    public TileMap getParent(){
        return parent;
    }

    public int getChunkWidth(){
        return chunkWidth;
    }

    public int getChunkHeight(){
        return chunkHeight;
    }

    public int getChunksNumX(){
        return chunksNumX;
    }

    public int getChunksNumY(){
        return chunksNumY;
    }

    public int getWidth(){
        return chunkWidth*chunksNumX;
    }

    public int getHeight(){
        return chunkHeight*chunksNumY;
    }

}
