package minecraft54.engine.tiledmap;

import minecraft54.engine.graphics.Texture;
import minecraft54.engine.graphics.TextureRegion;
import minecraft54.engine.utils.Assets;

import java.util.HashMap;

public class TileManager{

	public HashMap<Integer,Tile> tiles=new HashMap<>();

	public boolean defineTile(int id){
		if(id!=0){
			tiles.put(id,new Tile(id));
			return true;
		}
		return false;
	}



	public boolean addTileData(int id,int data,String t){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			Texture texture=Assets.getTexture(t);
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture),1,1,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileData(int id,int data,String t,float w,float h){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			Texture texture=Assets.getTexture(t);
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture),w,h,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileData(int id,int data,String t,float w,float h,float ox,float oy){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			Texture texture=Assets.getTexture(t);
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture),w,h,ox,oy));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileDataReg(int id,int data,String t,int rx,int ry,int rw,int rh){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			Texture texture=Assets.getTexture(t);
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture,rx,ry,rw,rh),1,1,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileDataReg(int id,int data,String t,int rx,int ry,int rw,int rh,float w,float h){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			Texture texture=Assets.getTexture(t);
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture,rx,ry,rw,rh),w,h,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileDataReg(int id,int data,String t,int rx,int ry,int rw,int rh,float w,float h,float ox,float oy){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			Texture texture=Assets.getTexture(t);
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture,rx,ry,rw,rh),w,h,ox,oy));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}



	public boolean addTileData(int id,int data,Texture texture){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture),1,1,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileData(int id,int data,Texture texture,float w,float h){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture),w,h,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileData(int id,int data,Texture texture,float w,float h,float ox,float oy){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture),w,h,ox,oy));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileDataReg(int id,int data,Texture texture,int rx,int ry,int rw,int rh){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture,rx,ry,rw,rh),1,1,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileDataReg(int id,int data,Texture texture,int rx,int ry,int rw,int rh,float w,float h){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture,rx,ry,rw,rh),w,h,0,0));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}

	public boolean addTileDataReg(int id,int data,Texture texture,int rx,int ry,int rw,int rh,float w,float h,float ox,float oy){
		if(id!=0){
			boolean hasnull=false;
			if(tiles.get(id)==null){
				tiles.put(id,new Tile(id));
				hasnull=true;
			}
			if(texture!=null){
				tiles.get(id).datas.put(data,new TileData(data,new TextureRegion(texture,rx,ry,rw,rh),w,h,ox,oy));
				return true;
			}
			if(hasnull)
				tiles.remove(id);
		}
		return false;
	}
	
	
	
	public TileData getTileData(int id,int data){
		Tile tile=getTile(id);
		if(tile==null)
			return null;
		if(!tile.datas.containsKey(data))
			return null;
		return tiles.get(id).datas.get(data);
	}

	public Tile getTile(int id){
		if(tiles.containsKey(id))
			return tiles.get(id);
		return null;
	}

}
