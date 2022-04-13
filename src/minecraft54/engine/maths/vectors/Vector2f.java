package minecraft54.engine.maths.vectors;

import minecraft54.engine.maths.Maths;

public class Vector2f{

	public float x,y;

	public Vector2f(){}

	public Vector2f(float xy){
		x=xy;
		y=xy;
	}

	public Vector2f(float x,float y){
		this.x=x;
		this.y=y;
	}

	public Vector2f(double xy){
		x=(float)xy;
		y=(float)xy;
	}

	public Vector2f(double x,double y){
		this.x=(float)x;
		this.y=(float)y;
	}

	public Vector2f(Vector3f v){
		x=v.x;
		y=v.y;
	}

	public Vector2f(Vector3d v){
		x=(float)v.x;
		y=(float)v.y;
	}

	public Vector2f(Vector3i v){
		x=v.x;
		y=v.y;
	}

	public Vector2f(Vector2f v){
		x=v.x;
		y=v.y;
	}

	public Vector2f(Vector2d v){
		x=(float)v.x;
		y=(float)v.y;
	}

	public Vector2f(Vector2i v){
		x=v.x;
		y=v.y;
	}

	public Vector2f set(float xy){
		x=xy;
		y=xy;
		return this;
	}

	public Vector2f set(float x,float y){
		this.x=x;
		this.y=y;
		return this;
	}

	public Vector2f set(Vector3f v){
		x=v.x;
		y=v.y;
		return this;
	}

	public Vector2f set(Vector3d v){
		x=(float)v.x;
		y=(float)v.y;
		return this;
	}

	public Vector2f set(Vector3i v){
		x=v.x;
		y=v.y;
		return this;
	}

	public Vector2f set(Vector2f v){
		x=v.x;
		y=v.y;
		return this;
	}

	public Vector2f set(Vector2d v){
		x=(float)v.x;
		y=(float)v.y;
		return this;
	}

	public Vector2f set(Vector2i v){
		x=v.x;
		y=v.y;
		return this;
	}

	public int x(){
		return Maths.round(x);
	}
	public int y(){
		return Maths.round(y);
	}

	public int xf(){
		return Maths.floor(x);
	}
	public int yf(){
		return Maths.floor(y);
	}

	public int xc(){
		return Maths.ceil(x);
	}
	public int yc(){
		return Maths.ceil(y);
	}

	public Vector2f add(float xy){
		x+=xy;
		y+=xy;
		return this;
	}

	public Vector2f add(float x,float y){
		this.x+=x;
		this.y+=y;
		return this;
	}

	public Vector2f add(double xyz){
		x+=xyz;
		y+=xyz;
		return this;
	}

	public Vector2f add(double x,double y){
		this.x+=x;
		this.y+=y;
		return this;
	}

	public Vector2f add(Vector3f v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2f add(Vector3d v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2f add(Vector3i v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2f add(Vector2f v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2f add(Vector2d v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2f add(Vector2i v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2f sub(float xy){
		x-=xy;
		y-=xy;
		return this;
	}

	public Vector2f sub(float x,float y){
		this.x-=x;
		this.y-=y;
		return this;
	}

	public Vector2f sub(double xy){
		x-=xy;
		y-=xy;
		return this;
	}

	public Vector2f sub(double x,double y){
		this.x-=x;
		this.y-=y;
		return this;
	}

	public Vector2f sub(Vector3f v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2f sub(Vector3d v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2f sub(Vector3i v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2f sub(Vector2f v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2f sub(Vector2d v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2f sub(Vector2i v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2f mul(float xy){
		x*=xy;
		y*=xy;
		return this;
	}

	public Vector2f mul(float x,float y){
		this.x*=x;
		this.y*=y;
		return this;
	}

	public Vector2f mul(double xy){
		x*=xy;
		y*=xy;
		return this;
	}

	public Vector2f mul(double x,double y){
		this.x*=x;
		this.y*=y;
		return this;
	}

	public Vector2f mul(Vector3f v){
		x*=v.x;
		y*=v.y;
		return this;
	}

	public Vector2f mul(Vector3d v){
		x*=v.x;
		y*=v.y;
		return this;
	}

	public Vector2f mul(Vector3i v){
		x*=v.x;
		y*=v.y;
		return this;
	}

	public Vector2f div(float xy){
		x/=xy;
		y/=xy;
		return this;
	}

	public Vector2f div(float x,float y){
		this.x/=x;
		this.y/=y;
		return this;
	}

	public Vector2f div(double xy){
		x/=xy;
		y/=xy;
		return this;
	}

	public Vector2f div(double x,double y){
		this.x/=x;
		this.y/=y;
		return this;
	}

	public Vector2f div(Vector3f v){
		x/=v.x;
		y/=v.y;
		return this;
	}

	public Vector2f div(Vector3d v){
		x/=v.x;
		y/=v.y;
		return this;
	}

	public Vector2f div(Vector3i v){
		x/=v.x;
		y/=v.y;
		return this;
	}

	public double len(){
		return Math.sqrt(x*x+y*y);
	}

	public Vector2f module(){
		if(x<0)
			x*=-1;
		if(y<0)
			y*=-1;
		return this;
	}

	public Vector2f zero(){
		x=0;
		y=0;
		return this;
	}

	public boolean isZero(){
		return x==0 && y==0;
	}

	public Vector2f nor(){
		double len=len();
		if(len==0 || len==1)
			return this;
		return div(len);
	}

	public double dot(float x,float y){
		return this.x*x+this.y*y;
	}

	public double dot(double x,double y){
		return this.x*x+this.y*y;
	}

	public double dot(Vector2f v){
		return x*v.x+y*v.y;
	}

	public double dot(Vector2d v){
		return x*v.x+y*v.y;
	}

	public double dot(Vector2i v){
		return x*v.x+y*v.y;
	}

	public double dst(float x,float y){
		double dx=this.x-x;
		double dy=this.y-y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double dst(double x,double y){
		double dx=this.x-x;
		double dy=this.y-y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double dst(Vector2f v){
		double dx=x-v.x;
		double dy=y-v.y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double dst(Vector2d v){
		double dx=x-v.x;
		double dy=y-v.y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double dst(Vector2i v){
		double dx=x-v.x;
		double dy=y-v.y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	public float deg(){
		return (float)Math.toDegrees(Math.atan2(y,x));
	}

	public float rad(){
		return (float)Math.atan2(y,x);
	}

	public Vector2f rot(float deg){
		return rotRad(Math.toRadians(deg));
	}

	public Vector2f rotRad(double rad){
		float cos=Maths.cos(rad);
		float sin=Maths.sin(rad);
		x=x*cos-y*sin;
		y=x*sin+y*cos;
		return this;
	}

	public boolean equals(Vector2f v){
		return v.x==x && v.y==y;
	}

	public boolean equals(Vector2d v){
		return v.x==x && v.y==y;
	}

	public boolean equals(Vector2i v){
		return v.x==x && v.y==y;
	}

	public float crs(float x,float y){
		return this.x*y-this.y*x;
	}

	public double crs(double x,double y){
		return this.x*y-this.y*x;
	}

	public float crs(Vector2f v){
		return this.x*v.y-this.y*v.x;
	}

	public double crs(Vector2d v){
		return this.x*v.y-this.y*v.x;
	}

	public float crs(Vector2i v){
		return this.x*v.y-this.y*v.x;
	}

	public Vector2f crs(){
		return new Vector2f(y,-x);
	}

	@Override
	public Vector2f clone(){
		return new Vector2f(this);
	}

	@Override
	public String toString(){
		return "x: "+x+", y: "+y;
	}


	public static float crs(Vector2f a,Vector2f b){
		return a.x*b.y-a.y*b.x;
	}

	public static float crs(float x1,float y1,float x2,float y2){
		return x1*y2-y1*x2;
	}

	public static float dot(Vector2f a,Vector2f b){
		return a.x*b.x+a.y*b.y;
	}

	public static float dot(float x1,float y1,float x2,float y2){
		return x1*x2+y1*y2;
	}

	public static double len(float x,float y){
		return Math.sqrt(x*x+y*y);
	}

}
