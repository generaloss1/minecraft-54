package engine54.maths.vectors;

import engine54.maths.Maths;

public class Vector3d{

    public double x,y,z;

    public Vector3d(){}

    public Vector3d(float xyz){
        x=xyz;
        y=xyz;
        z=xyz;
    }

    public Vector3d(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public Vector3d(double xyz){
        x=xyz;
        y=xyz;
        z=xyz;
    }

    public Vector3d(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public Vector3d(Vector3f v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3d(Vector3d v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3d(Vector3i v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3d(Vector2f v){
        x=v.x;
        y=v.y;
    }

    public Vector3d(Vector2d v){
        x=v.x;
        y=v.y;
    }

    public Vector3d(Vector2i v){
        x=v.x;
        y=v.y;
    }

    public Vector3d set(float xyz){
        x=xyz;
        y=xyz;
        z=xyz;
        return this;
    }

    public Vector3d set(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }

    public Vector3d set(double xyz){
        x=xyz;
        y=xyz;
        z=xyz;
        return this;
    }

    public Vector3d set(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }

    public Vector3d set(Vector3f v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }

    public Vector3d set(Vector3d v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }

    public Vector3d set(Vector3i v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }

    public Vector3d set(Vector2f v){
        x=v.x;
        y=v.y;
        z=0;
        return this;
    }

    public Vector3d set(Vector2d v){
        x=v.x;
        y=v.y;
        z=0;
        return this;
    }

    public Vector3d set(Vector2i v){
        x=v.x;
        y=v.y;
        z=0;
        return this;
    }

    public int x(){
        return Maths.round(x);
    }
    public int y(){
        return Maths.round(y);
    }
    public int z(){
        return Maths.round(z);
    }

    public int xf(){
        return Maths.floor(x);
    }
    public int yf(){
        return Maths.floor(y);
    }
    public int zf(){
        return Maths.floor(z);
    }

    public int xc(){
        return Maths.ceil(x);
    }
    public int yc(){
        return Maths.ceil(y);
    }
    public int zc(){
        return Maths.ceil(z);
    }

    public Vector3d add(float xyz){
        x+=xyz;
        y+=xyz;
        z+=xyz;
        return this;
    }

    public Vector3d add(float x,float y,float z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }

    public Vector3d add(double xyz){
        x+=xyz;
        y+=xyz;
        z+=xyz;
        return this;
    }

    public Vector3d add(double x,double y,double z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }

    public Vector3d add(Vector3f v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3d add(Vector3d v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3d add(Vector3i v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3d add(Vector2f v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3d add(Vector2d v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3d add(Vector2i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3d sub(float xyz){
        x-=xyz;
        y-=xyz;
        z-=xyz;
        return this;
    }

    public Vector3d sub(float x,float y,float z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }

    public Vector3d sub(double xyz){
        x-=xyz;
        y-=xyz;
        z-=xyz;
        return this;
    }

    public Vector3d sub(double x,double y,double z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }

    public Vector3d sub(Vector3f v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3d sub(Vector3d v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3d sub(Vector3i v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3d sub(Vector2f v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3d sub(Vector2d v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3d sub(Vector2i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3d mul(float xyz){
        x*=xyz;
        y*=xyz;
        z*=xyz;
        return this;
    }

    public Vector3d mul(float x,float y,float z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }

    public Vector3d mul(double xyz){
        x*=xyz;
        y*=xyz;
        z*=xyz;
        return this;
    }

    public Vector3d mul(double x,double y,double z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }

    public Vector3d mul(Vector3f v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3d mul(Vector3d v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3d mul(Vector3i v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3d div(float xyz){
        x/=xyz;
        y/=xyz;
        z/=xyz;
        return this;
    }

    public Vector3d div(float x,float y,float z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }

    public Vector3d div(double xyz){
        x/=xyz;
        y/=xyz;
        z/=xyz;
        return this;
    }

    public Vector3d div(double x,double y,double z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }

    public Vector3d div(Vector3f v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public Vector3d div(Vector3d v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public Vector3d div(Vector3i v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public double len(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3d module(){
        if(x<0)
            x*=-1;
        if(y<0)
            y*=-1;
        if(z<0)
            z*=-1;
        return this;
    }

    public Vector3d zero(){
        x=0;
        y=0;
        z=0;
        return this;
    }

    public boolean isZero(){
        return x==0 && y==0 && z==0;
    }

    public Vector3d nor(){
        double len=len();
        if(len==0 || len==1)
            return this;
        return div(len);
    }

    public double dot(float x,float y,float z){
        return this.x*x+this.y*y+this.z*z;
    }

    public double dot(double x,double y,double z){
        return this.x*x+this.y*y+this.z*z;
    }

    public double dot(Vector3f v){
        return x*v.x+y*v.y+z*v.z;
    }

    public double dot(Vector3d v){
        return x*v.x+y*v.y+z*v.z;
    }

    public double dot(Vector3i v){
        return x*v.x+y*v.y+z*v.z;
    }

    public double dst(float x,float y,float z){
        double dx=this.x-x;
        double dy=this.y-y;
        double dz=this.z-z;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public double dst(double x,double y,double z){
        double dx=this.x-x;
        double dy=this.y-y;
        double dz=this.z-z;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public double dst(Vector3f v){
        double dx=x-v.x;
        double dy=y-v.y;
        double dz=z-v.z;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public double dst(Vector3d v){
        double dx=x-v.x;
        double dy=y-v.y;
        double dz=z-v.z;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public double dst(Vector3i v){
        double dx=x-v.x;
        double dy=y-v.y;
        double dz=z-v.z;
        return Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public boolean equals(Vector3f v){
        return v.x==x && v.y==y && v.z==z;
    }

    public boolean equals(Vector3d v){
        return v.x==x && v.y==y && v.z==z;
    }

    public boolean equals(Vector3i v){
        return v.x==x && v.y==y && v.z==z;
    }

    public Vector3d crs(float x,float y,float z){
        this.x=this.y*z-this.z*y;
        this.y=this.z*x-this.x*z;
        this.z=this.x*y-this.y*x;
        return this;
    }

    public Vector3d crs(double x,double y,double z){
        this.x=this.y*z-this.z*y;
        this.y=this.z*x-this.x*z;
        this.z=this.x*y-this.y*x;
        return this;
    }

    public Vector3d crs(Vector3f v){
        this.x=y*v.z-z*v.y;
        this.y=z*v.x-x*v.z;
        this.z=x*v.y-y*v.x;
        return this;
    }

    public Vector3d crs(Vector3d v){
        this.x=y*v.z-z*v.y;
        this.y=z*v.x-x*v.z;
        this.z=x*v.y-y*v.x;
        return this;
    }

    public Vector3d crs(Vector3i v){
        this.x=y*v.z-z*v.y;
        this.y=z*v.x-x*v.z;
        this.z=x*v.y-y*v.x;
        return this;
    }


    @Override
    public Vector3d clone(){
        return new Vector3d(this);
    }

    @Override
    public String toString(){
        return "x: "+x+", y: "+y+", z: "+z;
    }


    public static Vector3d crs(Vector3d a,Vector3d b){
        return new Vector3d(a.y*b.z-a.z*b.y,a.z*b.x-a.x*b.z,a.x*b.y-a.y*b.x);
    }

    public static Vector3d crs(double x1,double y1,double z1,double x2,double y2,double z2){
        return new Vector3d(y1*z2-z1*y2,z1*x2-x1*z2,x1*y2-y1*x2);
    }

    public static double dot(Vector3d a,Vector3d b){
        return a.x*b.x+a.y*b.y+a.z*b.z;
    }

    public static double dot(double x1,double y1,double z1,double x2,double y2,double z2){
        return x1*x2+y1*y2+z1*z2;
    }

    public static double len(double x,double y,double z){
        return Math.sqrt(x*x+y*y+z*z);
    }
    
}
