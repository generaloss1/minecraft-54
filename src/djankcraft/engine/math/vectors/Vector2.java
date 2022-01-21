package djankcraft.engine.math.vectors;

import djankcraft.engine.math.Maths;

public class Vector2{

	public float x,y;

	public Vector2(){}
	public Vector2(float xy){
		x=xy;
		y=xy;
	}
	public Vector2(double xy){
		x=(float)xy;
		y=(float)xy;
	}
	public Vector2(double x,double y){
		this.x=(float)x;
		this.y=(float)y;
	}
	public Vector2(float x,float y){
		this.x=x;
		this.y=y;
	}
	public Vector2(Vector2 v){
		x=v.x;
		y=v.y;
	}
	public Vector2(Vector2i v){
		x=v.x;
		y=v.y;
	}

	public Vector2 set(float xy){
		x=xy;
		y=xy;
		return this;
	}
	public Vector2 set(float x,float y){
		this.x=x;
		this.y=y;
		return this;
	}
	public Vector2 set(Vector2 v){
		x=v.x;
		y=v.y;
		return this;
	}
	public Vector2 set(double xy){
		x=(float)xy;
		y=(float)xy;
		return this;
	}
	public Vector2 set(double x,double y){
		this.x=(float)x;
		this.y=(float)y;
		return this;
	}

	public int x(){
		return Maths.round(x);
	}

	public int y(){
		return Maths.round(y);
	}

	public Vector2 add(float xy){
		x+=xy;
		y+=xy;
		return this;
	}
	public Vector2 add(float x,float y){
		this.x+=x;
		this.y+=y;
		return this;
	}
	public Vector2 add(Vector2 v){
		x+=v.x;
		y+=v.y;
		return this;
	}

	public Vector2 sub(float xy){
		x-=xy;
		y-=xy;
		return this;
	}
	public Vector2 sub(float x,float y){
		this.x-=x;
		this.y-=y;
		return this;
	}
	public Vector2 sub(Vector2 v){
		x-=v.x;
		y-=v.y;
		return this;
	}

	public Vector2 scl(float xy){
		x*=xy;
		y*=xy;
		return this;
	}
	public Vector2 scl(float x,float y){
		this.x*=x;
		this.y*=y;
		return this;
	}
	public Vector2 scl(Vector2 v){
		x*=v.x;
		y*=v.y;
		return this;
	}

	public Vector2 div(float xy){
		x/=xy;
		y/=xy;
		return this;
	}
	public Vector2 div(float x,float y){
		this.x/=x;
		this.y/=y;
		return this;
	}
	public Vector2 div(Vector2 v){
		x/=v.x;
		y/=v.y;
		return this;
	}

	public float len(){
		return (float)Math.sqrt(x*x+y*y);
	}

	public Vector2 zero(){
		x=0;
		y=0;
		return this;
	}
	public boolean isZero(){
		return x==0 && y==0;
	}

	public Vector2 nor(){
		double len=Math.sqrt(x*x+y*y);
		if(len!=0){
			x/=len;
			y/=len;
		}
		return this;
	}

	public float dot(float x,float y){
		return this.x*x+this.y*y;
	}
	public float dot(Vector2 v){
		return x*v.x+y*v.y;
	}

	public float dst(float x,float y){
		float dx=this.x-x;
		float dy=this.y-y;
		return (float)Math.sqrt(dx*dx+dy*dy);
	}
	public float dst(Vector2 v){
		float dx=x-v.x;
		float dy=y-v.y;
		return (float)Math.sqrt(dx*dx+dy*dy);
	}

	public Vector2 rot(float degrees){
		double radians=degrees*Maths.toRadians;
		float cos=(float)Math.cos(radians);
		float sin=(float)Math.sin(radians);
		this.x=this.x*cos-this.y*sin;
		this.y=this.x*sin+this.y*cos;
		return this;
	}
	public Vector2 rotRad(float radians){
		float cos=(float)Math.cos(radians);
		float sin=(float)Math.sin(radians);
		this.x=this.x*cos-this.y*sin;
		this.y=this.x*sin+this.y*cos;
		return this;
	}

	public float deg(){
		return (float)(Math.atan2(y,x)*180/Math.PI);
	}
	public float rad(){
		return (float)Math.atan2(y,x);
	}

	public boolean equals(Vector2 v){
		return v.x==x && v.y==y;
	}
	public boolean equals(float x,float y){
		return this.x==x && this.y==y;
	}

	public float crs(Vector2 v){
		return this.x*v.y-this.y*v.x;
	}
	public float crs(float x,float y){
		return this.x*y-this.y*x;
	}


	public static float dot(float x1,float y1,float x2,float y2){
		return x1*x2+y1*y2;
	}
	public static float dot(Vector2 v1,Vector2 v2){
		return v1.x*v2.x+v1.y*v2.y;
	}

	public static float dst(float x1,float y1,float x2,float y2){
		float dx=x1-x2;
		float dy=y1-y2;
		return (float)Math.round(dx*dx+dy*dy);
	}
	public static float dst(Vector2 v1,Vector2 v2){
		float dx=v1.x-v2.x;
		float dy=v1.y-v2.y;
		return (float)Math.round(dx*dx+dy*dy);
	}

	public static float len(float x,float y){
		return (float)Math.sqrt(x*x+y*y);
	}
	public static float len(Vector2 v){
		return (float)Math.sqrt(v.x*v.x+v.y*v.y);
	}

	public static boolean equals(Vector2 v1,Vector2 v2){
		return v1.x==v2.x && v1.y==v2.y;
	}


	@Override
	public String toString(){
		return "x: "+x+", y: "+y;
	}

	@Override
	public boolean equals(Object o){
		if(o.getClass().getTypeName().equals(this.getClass().getTypeName())){
			Vector2 v=(Vector2)o;
			return v.x==x && v.y==y;
		}
		return false;
	}

}
