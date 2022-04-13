package minecraft54.engine.maths.vectors;

import minecraft54.engine.maths.Maths;

public class Vector2d{

    public double x,y;

    public Vector2d(){}

    public Vector2d(float xy){
        x=xy;
        y=xy;
    }

    public Vector2d(float x,float y){
        this.x=x;
        this.y=y;
    }

    public Vector2d(double xy){
        x=xy;
        y=xy;
    }

    public Vector2d(double x,double y){
        this.x=x;
        this.y=y;
    }

    public Vector2d(Vector3f v){
        x=v.x;
        y=v.y;
    }

    public Vector2d(Vector3d v){
        x=v.x;
        y=v.y;
    }

    public Vector2d(Vector3i v){
        x=v.x;
        y=v.y;
    }

    public Vector2d(Vector2f v){
        x=v.x;
        y=v.y;
    }

    public Vector2d(Vector2d v){
        x=v.x;
        y=v.y;
    }

    public Vector2d(Vector2i v){
        x=v.x;
        y=v.y;
    }

    public Vector2d set(float xy){
        x=xy;
        y=xy;
        return this;
    }

    public Vector2d set(float x,float y){
        this.x=x;
        this.y=y;
        return this;
    }

    public Vector2d set(double xy){
        x=xy;
        y=xy;
        return this;
    }

    public Vector2d set(double x,double y){
        this.x=x;
        this.y=y;
        return this;
    }

    public Vector2d set(Vector3f v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2d set(Vector3d v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2d set(Vector3i v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2d set(Vector2f v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2d set(Vector2d v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2d set(Vector2i v){
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

    public Vector2d add(float xy){
        x+=xy;
        y+=xy;
        return this;
    }

    public Vector2d add(float x,float y){
        this.x+=x;
        this.y+=y;
        return this;
    }

    public Vector2d add(double xyz){
        x+=xyz;
        y+=xyz;
        return this;
    }

    public Vector2d add(double x,double y){
        this.x+=x;
        this.y+=y;
        return this;
    }

    public Vector2d add(Vector3f v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2d add(Vector3d v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2d add(Vector3i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2d add(Vector2f v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2d add(Vector2d v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2d add(Vector2i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2d sub(float xy){
        x-=xy;
        y-=xy;
        return this;
    }

    public Vector2d sub(float x,float y){
        this.x-=x;
        this.y-=y;
        return this;
    }

    public Vector2d sub(double xy){
        x-=xy;
        y-=xy;
        return this;
    }

    public Vector2d sub(double x,double y){
        this.x-=x;
        this.y-=y;
        return this;
    }

    public Vector2d sub(Vector3f v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2d sub(Vector3d v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2d sub(Vector3i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2d sub(Vector2f v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2d sub(Vector2d v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2d sub(Vector2i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2d mul(float xy){
        x*=xy;
        y*=xy;
        return this;
    }

    public Vector2d mul(float x,float y){
        this.x*=x;
        this.y*=y;
        return this;
    }

    public Vector2d mul(double xy){
        x*=xy;
        y*=xy;
        return this;
    }

    public Vector2d mul(double x,double y){
        this.x*=x;
        this.y*=y;
        return this;
    }

    public Vector2d mul(Vector3f v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2d mul(Vector3d v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2d mul(Vector3i v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2d div(float xy){
        x/=xy;
        y/=xy;
        return this;
    }

    public Vector2d div(float x,float y){
        this.x/=x;
        this.y/=y;
        return this;
    }

    public Vector2d div(double xy){
        x/=xy;
        y/=xy;
        return this;
    }

    public Vector2d div(double x,double y){
        this.x/=x;
        this.y/=y;
        return this;
    }

    public Vector2d div(Vector3f v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public Vector2d div(Vector3d v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public Vector2d div(Vector3i v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public double len(){
        return Math.sqrt(x*x+y*y);
    }

    public Vector2d module(){
        if(x<0)
            x*=-1;
        if(y<0)
            y*=-1;
        return this;
    }

    public Vector2d zero(){
        x=0;
        y=0;
        return this;
    }

    public boolean isZero(){
        return x==0 && y==0;
    }

    public Vector2d nor(){
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

    public Vector2d rot(float deg){
        return rotRad(Math.toRadians(deg));
    }

    public Vector2d rotRad(double rad){
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

    public double crs(float x,float y){
        return this.x*y-this.y*x;
    }

    public double crs(double x,double y){
        return this.x*y-this.y*x;
    }

    public double crs(Vector2f v){
        return this.x*v.y-this.y*v.x;
    }

    public double crs(Vector2d v){
        return this.x*v.y-this.y*v.x;
    }

    public double crs(Vector2i v){
        return this.x*v.y-this.y*v.x;
    }

    public Vector2d crs(){
        return new Vector2d(y,-x);
    }

    @Override
    public Vector2d clone(){
        return new Vector2d(this);
    }

    @Override
    public String toString(){
        return "x: "+x+", y: "+y;
    }


    public static double crs(Vector2d a,Vector2d b){
        return a.x*b.y-a.y*b.x;
    }

    public static double crs(double x1,double y1,double x2,double y2){
        return x1*y2-y1*x2;
    }

    public static double dot(Vector2d a,Vector2d b){
        return a.x*b.x+a.y*b.y;
    }

    public static double dot(double x1,double y1,double x2,double y2){
        return x1*x2+y1*y2;
    }

    public static double len(double x,double y){
        return Math.sqrt(x*x+y*y);
    }

}
