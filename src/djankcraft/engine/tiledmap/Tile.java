package djankcraft.engine.tiledmap;

import java.util.HashMap;

public class Tile{

	public int id;
	public HashMap<Integer,TileData> datas=new HashMap<>();

	public Tile(int tid){
		id=tid;
	}
	
}
