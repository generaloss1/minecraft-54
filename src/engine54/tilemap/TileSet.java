package engine54.tilemap;

import java.util.ArrayList;
import java.util.List;

public class TileSet{

    private final List<List<Tile>> tiles=new ArrayList<>();

    public TileSet(){
        tiles.add(null); // 0 - air
    }

    public void defineTile(Tile tile){
        int id=tile.getID();
        int data=tile.getData();
        if(id>tiles.size() || id<1){
            System.err.println("Unexpected tile ID: "+id);
            return;
        }
        if(id==tiles.size())
            tiles.add(new ArrayList<>());

        List<Tile> datas=tiles.get(id);
        if(data>datas.size()){
            System.err.println("Unexpected tile data: "+data);
            return;
        }
        datas.add(tile);
    }

    public Tile getTile(int id,int data){
        if(id==0 || id>=tiles.size())
            return null;
        List<Tile> datas=tiles.get(id);
        if(datas==null)
            return null;
        return datas.get(data);
    }

}
