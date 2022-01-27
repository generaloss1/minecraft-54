package minecraft54.engine.tiledmap;

import minecraft54.engine.graphics.OrthographicCamera;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.math.vectors.Vector2i;

import java.io.*;

public class TMLayer implements Serializable{


	public int id;
	public boolean show=true;

	public int[][][] map;
	public float[][][] colormap;
	public int width;
	public int height;

	public float tiles_offset_x;
	public float tiles_offset_y;

	public int view;



	public TMLayer(int lid,int w,int h,int ox,int oy){
		width=w;
		height=h;
		id=lid;
		tiles_offset_x=ox;
		tiles_offset_y=oy;

		map=new int[w][h][2];
		colormap=new float[w][h][4];
		for(int x=0; x<width; x++)
			for(int y=0; y<height; y++){
				colormap[x][y][0]=1;
				colormap[x][y][1]=1;
				colormap[x][y][2]=1;
				colormap[x][y][3]=1;
			}
	}



	public Vector2i getStartRender(OrthographicCamera cam){
		int x=Math.round(cam.getX()/tiles_offset_x)-view-1;
		int y=Math.round(cam.getY()/tiles_offset_y)-view-1;
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(x>width)
			x=width;
		if(y>height)
			y=height;
		return new Vector2i(x,y);
	}

	public Vector2i getEndRender(OrthographicCamera cam){
		int x=Math.round((cam.getX()+cam.getWidth())/tiles_offset_x)+view+1;
		int y=Math.round((cam.getY()+cam.getHeight())/tiles_offset_y)+view+1;
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(x>width)
			x=width;
		if(y>height)
			y=height;
		return new Vector2i(x,y);
	}

	public Vector2i getStartRender(OrthographicCamera cam,int v){
		int x=Math.round(cam.getX()/tiles_offset_x)-view+v-1;
		int y=Math.round(cam.getY()/tiles_offset_y)-view+v-1;
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(x>width)
			x=width;
		if(y>height)
			y=height;
		return new Vector2i(x,y);
	}

	public Vector2i getEndRender(OrthographicCamera cam,int v){
		int x=Math.round((cam.getX()+cam.getWidth())/tiles_offset_x)+view+v+1;
		int y=Math.round((cam.getY()+cam.getHeight())/tiles_offset_y)+view+v+1;
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(x>width)
			x=width;
		if(y>height)
			y=height;
		return new Vector2i(x,y);
	}



	public void draw(TileManager tileManager,SpriteBatch sb,OrthographicCamera cam){
		if(show){
			Vector2i start=getStartRender(cam);
			Vector2i end=getEndRender(cam);
			for(int tx=start.x; tx<end.x; tx++){
				for(int ty=start.y; ty<end.y; ty++){
					if(map[tx][ty][0]!=0){
						TileData t=tileManager.getTileData(map[tx][ty][0],map[tx][ty][1]);
						if(t!=null && t.texture!=null){
							sb.setColor(colormap[tx][ty][0],colormap[tx][ty][1],colormap[tx][ty][2],colormap[tx][ty][3]);
							sb.draw(t.texture,tx*tiles_offset_x+t.offset_x*tiles_offset_x,ty*tiles_offset_y+t.offset_y*tiles_offset_y,t.width*tiles_offset_x,t.height*tiles_offset_y);
						}
					}
				}
			}
			sb.setColor(1,1,1,1);
		}
	}

	public void draw(TileManager tileManager,SpriteBatch sb,OrthographicCamera cam,float px,float py){
		if(show){
			Vector2i start=getStartRender(cam);
			Vector2i end=getEndRender(cam);
			for(int tx=start.x; tx<end.x; tx++){
				for(int ty=start.y; ty<end.y; ty++){
					if(map[tx][ty][0]!=0){
						TileData t=tileManager.getTileData(map[tx][ty][0],map[tx][ty][1]);
						if(t!=null && t.texture!=null){
							sb.setColor(colormap[tx][ty][0],colormap[tx][ty][1],colormap[tx][ty][2],colormap[tx][ty][3]);
							sb.draw(t.texture,px+tx*tiles_offset_x+t.offset_x*tiles_offset_x,py+ty*tiles_offset_y+t.offset_y*tiles_offset_y,t.width*tiles_offset_x,t.height*tiles_offset_y);
						}
					}
				}
			}
			sb.setColor(1,1,1,1);
		}
	}


	public void save(String path){
		try{
			FileOutputStream fout=new FileOutputStream(path);
			ObjectOutputStream oos=new ObjectOutputStream(fout);
			oos.writeObject(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void load(String path){
		try{
			FileInputStream fin=new FileInputStream(path);
			ObjectInputStream oos=new ObjectInputStream(fin);
			TMLayer l=(TMLayer)oos.readObject();
			id=l.id;
			show=l.show;
			map=l.map;
			colormap=l.colormap;
			width=l.width;
			height=l.height;
			tiles_offset_x=l.tiles_offset_x;
			tiles_offset_y=l.tiles_offset_y;
			view=l.view;
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public boolean inBounds(int x,int y){
		return x>=0 && y>=0 && x<width && y<height;
	}


	public void setTile(int x,int y,int id,int data){
		if(inBounds(x,y)){
			map[x][y][0]=id;
			map[x][y][1]=data;
		}
	}

	public void setTileId(int x,int y,int id){
		if(inBounds(x,y))
			map[x][y][0]=id;
	}

	public void setTileData(int x,int y,int data){
		if(inBounds(x,y))
			map[x][y][1]=data;
	}

	public int getTileId(int x,int y){
		if(inBounds(x,y))
			return map[x][y][0];
		else
			return 0;
	}

	public int getTileData(int x,int y){
		if(inBounds(x,y))
			return map[x][y][1];
		else
			return 0;
	}



	public void fill(int x1,int y1,int x2,int y2,int id,int data){
		if(x1<0)
			x1=0;
		if(x1>=width)
			x1=width-1;
		if(y1<0)
			y1=0;
		if(y1>=height)
			y1=height-1;
		if(x2<0)
			x2=0;
		if(x2>=width)
			x2=width-1;
		if(y2<0)
			y2=0;
		if(y2>=height)
			y2=height-1;

		for(int x=x1; x<=x2; x++)
			for(int y=y1; y<=y2; y++){
				map[x][y][0]=id;
				map[x][y][1]=data;
			}
	}

	public void fillId(int x1,int y1,int x2,int y2,int id){
		if(x1<0) x1=0;
		if(x1>=width) x1=width-1;
		if(y1<0) y1=0;
		if(y1>=height) y1=height-1;
		if(x2<0) x2=0;
		if(x2>=width) x2=width-1;
		if(y2<0) y2=0;
		if(y2>=height) y2=height-1;

		for(int x=x1; x<=x2; x++)
			for(int y=y1; y<=y2; y++)
				map[x][y][0]=id;
	}

	public void fillData(int x1,int y1,int x2,int y2,int data){
		if(x1<0) x1=0;
		if(x1>=width) x1=width-1;
		if(y1<0) y1=0;
		if(y1>=height) y1=height-1;
		if(x2<0) x2=0;
		if(x2>=width) x2=width-1;
		if(y2<0) y2=0;
		if(y2>=height) y2=height-1;

		for(int x=x1; x<=x2; x++)
			for(int y=y1; y<=y2; y++)
				map[x][y][1]=data;
	}

	public void setView(int v){
		view=v;
	}



	public void show(boolean ls){
		show=ls;
	}

	public boolean isShow(){
		return show;
	}



}
