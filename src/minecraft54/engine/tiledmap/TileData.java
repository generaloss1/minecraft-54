package minecraft54.engine.tiledmap;

import minecraft54.engine.graphics.TextureRegion;

public class TileData{

	public int data;
	public TextureRegion texture;
	public float width,height,offset_x,offset_y;
	
	public TileData(int tdata,TextureRegion t,float w,float h,float ox,float oy){
		data=tdata;
		texture=t;
		width=w+0.002f;
		height=h+0.002f;
		offset_x=ox-0.001f;
		offset_y=oy-0.001f;
	}

}
