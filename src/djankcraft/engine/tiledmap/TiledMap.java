package djankcraft.engine.tiledmap;

import djankcraft.engine.graphics.OrthographicCamera;
import djankcraft.engine.graphics.SpriteBatch;

import java.io.*;
import java.util.HashMap;

public class TiledMap implements Serializable{


    public HashMap<Integer,TMLayer> layers;


    public TiledMap(){
        layers=new HashMap<>();
    }

    public void draw(TileManager tileManager,SpriteBatch sb,OrthographicCamera cam){
        for(TMLayer l: layers.values()){
            l.draw(tileManager,sb,cam);
        }
    }

    public void draw(TileManager tileManager,SpriteBatch sb,OrthographicCamera cam,float x,float y){
        for(TMLayer l: layers.values()){
            l.draw(tileManager,sb,cam,x,y);
        }
    }

    public void save(String path){
        try{
            FileOutputStream fout=new FileOutputStream(path);
            ObjectOutputStream oos=new ObjectOutputStream(fout);
            oos.writeObject(layers);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void load(String path){
        try{
            FileInputStream fin=new FileInputStream(path);
            ObjectInputStream oos=new ObjectInputStream(fin);
            layers=(HashMap<Integer,TMLayer>)oos.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean inBounds(int layer_id,int x,int y){
        return layers.get(layer_id).inBounds(x,y);
    }

    public void addLayer(int layer_id,int w,int h,int tw,int th){
        layers.put(layer_id,new TMLayer(layer_id,w,h,tw,th));
    }

    public TMLayer layer(int layer_id){
        return layers.get(layer_id);
    }

    public void showLayer(int layer_id,boolean ls){
        layers.get(layer_id).show(ls);
    }

    public boolean isLayerShow(int layer_id){
        return layers.get(layer_id).isShow();
    }

    public void setView(int layer_id,int v){
        layers.get(layer_id).setView(v);
    }


    public void setTile(int layer_id,int x,int y,int id,int data){
        layers.get(layer_id).setTile(x,y,id,data);
    }

    public void setTileId(int layer_id,int x,int y,int id){
        layers.get(layer_id).setTileId(x,y,id);
    }

    public void setTileData(int layer_id,int x,int y,int data){
        layers.get(layer_id).setTileData(x,y,data);
    }

    public int getTileId(int layer_id,int x,int y){
        return layers.get(layer_id).getTileId(x,y);
    }

    public int getTileData(int layer_id,int x,int y){
        return layers.get(layer_id).getTileData(x,y);
    }


    public void fill(int layer_id,int x1,int y1,int x2,int y2,int id,int data){
        layers.get(layer_id).fill(x1,y1,x2,y2,id,data);
    }

    public void fillId(int layer_id,int x1,int y1,int x2,int y2,int id){
        layers.get(layer_id).fillId(x1,y1,x2,y2,id);
    }

    public void fillData(int layer_id,int x1,int y1,int x2,int y2,int data){
        layers.get(layer_id).fillData(x1,y1,x2,y2,data);
    }

}
