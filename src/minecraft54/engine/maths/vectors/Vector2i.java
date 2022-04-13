package minecraft54.engine.maths.vectors;

import minecraft54.engine.maths.Maths;

public class Vector2i{

    public int x,y;

    public Vector2i(){}

    public Vector2i(int xy){
        x=xy;
        y=xy;
    }

    public Vector2i(int x,int y){
        this.x=x;
        this.y=y;
    }

    public Vector2i(Vector3i v){
        x=v.x;
        y=v.y;
    }

    public Vector2i(Vector2i v){
        x=v.x;
        y=v.y;
    }

    public Vector2i set(int xy){
        x=xy;
        y=xy;
        return this;
    }

    public Vector2i set(int x,int y){
        this.x=x;
        this.y=y;
        return this;
    }

    public Vector2i set(Vector3i v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2i set(Vector2i v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2i add(float xy){
        x+=xy;
        y+=xy;
        return this;
    }

    public Vector2i add(float x,float y){
        this.x+=x;
        this.y+=y;
        return this;
    }

    public Vector2i add(double xyz){
        x+=xyz;
        y+=xyz;
        return this;
    }

    public Vector2i add(double x,double y){
        this.x+=x;
        this.y+=y;
        return this;
    }

    public Vector2i add(Vector3f v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i add(Vector3d v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i add(Vector3i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i add(Vector2f v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i add(Vector2d v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i add(Vector2i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i sub(float xy){
        x-=xy;
        y-=xy;
        return this;
    }

    public Vector2i sub(float x,float y){
        this.x-=x;
        this.y-=y;
        return this;
    }

    public Vector2i sub(double xy){
        x-=xy;
        y-=xy;
        return this;
    }

    public Vector2i sub(double x,double y){
        this.x-=x;
        this.y-=y;
        return this;
    }

    public Vector2i sub(Vector3f v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i sub(Vector3d v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i sub(Vector3i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i sub(Vector2f v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i sub(Vector2d v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i sub(Vector2i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i mul(float xy){
        x*=xy;
        y*=xy;
        return this;
    }

    public Vector2i mul(float x,float y){
        this.x*=x;
        this.y*=y;
        return this;
    }

    public Vector2i mul(double xy){
        x*=xy;
        y*=xy;
        return this;
    }

    public Vector2i mul(double x,double y){
        this.x*=x;
        this.y*=y;
        return this;
    }

    public Vector2i mul(Vector3f v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2i mul(Vector3d v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2i mul(Vector3i v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2i div(float xy){
        x/=xy;
        y/=xy;
        return this;
    }

    public Vector2i div(float x,float y){
        this.x/=x;
        this.y/=y;
        return this;
    }

    public Vector2i div(double xy){
        x/=xy;
        y/=xy;
        return this;
    }

    public Vector2i div(double x,double y){
        this.x/=x;
        this.y/=y;
        return this;
    }

    public Vector2i div(Vector3f v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public Vector2i div(Vector3d v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public Vector2i div(Vector3i v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public double len(){
        return Math.sqrt(x*x+y*y);
    }

    public Vector2i module(){
        if(x<0)
            x*=-1;
        if(y<0)
            y*=-1;
        return this;
    }

    public Vector2i zero(){
        x=0;
        y=0;
        return this;
    }

    public boolean isZero(){
        return x==0 && y==0;
    }

    public Vector2i nor(){
        double len=len();
        if(len==0 || len==1)
            return this;
        return div(len);
    }

    public int dot(int x,int y){
        return this.x*x+this.y*y;
    }

    public int dot(Vector2i v){
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

    public Vector2i crs(){
        return new Vector2i(y,-x);
    }

    @Override
    public Vector2i clone(){
        return new Vector2i(this);
    }

    @Override
    public String toString(){
        return "x: "+x+", y: "+y;
    }


    public static int crs(Vector2i a,Vector2i b){
        return a.x*b.y-a.y*b.x;
    }

    public static int crs(int x1,int y1,int x2,int y2){
        return x1*y2-y1*x2;
    }

    public static int dot(Vector2i a,Vector2i b){
        return a.x*b.x+a.y*b.y;
    }

    public static int dot(int x1,int y1,int x2,int y2){
        return x1*x2+y1*y2;
    }

    public static double len(float x,float y){
        return Math.sqrt(x*x+y*y);
    }

}
