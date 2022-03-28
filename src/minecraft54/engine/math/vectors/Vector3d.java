package minecraft54.engine.math.vectors;

import minecraft54.engine.math.Maths;

public class Vector3d{

    public double x, y, z;

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

    public Vector3d(Vector3d v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3d(Vector3f v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3d(Vector3i v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3d(Vector2 v){
        x=v.x;
        y=v.y;
    }

    public Vector3d(Vector2i v){
        x=v.x;
        y=v.y;
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

    public Vector3d set(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }

    public Vector3d set(Vector3d v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }

    public Vector3d set(Vector3f v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }

    public Vector3d set(Vector2 v){
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

    public Vector3d add(Vector3d v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
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

    public Vector3d add(Vector3f v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3d add(Vector2 v){
        x+=v.x;
        y+=v.y;
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

    public Vector3d sub(Vector3d v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
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

    public Vector3d sub(Vector3f v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3d sub(Vector2 v){
        x-=v.x;
        y-=v.y;
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

    public Vector3d mul(Vector3d v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
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

    public Vector3d mul(Vector3f v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
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

    public Vector3d div(Vector3d v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
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

    public Vector3d div(Vector3f v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public double len(){
        return (double)Math.sqrt(x*x+y*y+z*z);
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
        double len2=x*x+y*y+z*z;
        if(len2==0 || len2==1)
            return this;
        double invLen=1/Math.sqrt(len2);
        x*=invLen;
        y*=invLen;
        z*=invLen;
        return this;
    }

    public double dot(double x,double y,double z){
        return this.x*x+this.y*y+this.z*z;
    }

    public double dot(Vector3d v){
        return x*v.x+y*v.y+z*v.z;
    }

    public double dst(double x,double y,double z){
        double dx=this.x-x;
        double dy=this.y-y;
        double dz=this.z-z;
        return (double)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public double dst(Vector3d v){
        double dx=x-v.x;
        double dy=y-v.y;
        double dz=z-v.z;
        return (double)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public boolean equals(Vector3d v){
        return v.x==x && v.y==y && v.z==z;
    }

    public boolean equals(double x,double y){
        return this.x==x && this.y==y && this.z==z;
    }

    public Vector3d crs(Vector3d vector){
        this.x=y*vector.z-z*vector.y;
        this.y=z*vector.x-x*vector.z;
        this.z=x*vector.y-y*vector.x;
        return this;
    }

    public Vector3d crs(double x,double y,double z){
        this.x=this.y*z-this.z*y;
        this.y=this.z*x-this.x*z;
        this.z=this.x*y-this.y*x;
        return this;
    }

    public Vector3d clone(){
        return new Vector3d(this);
    }


    public static Vector3d crs(Vector3d a,Vector3d b){
        return new Vector3d(a.y*b.z-a.z*b.y,a.z*b.x-a.x*b.z,a.x*b.y-a.y*b.x);
    }

    public static Vector3d crs(double x1,double y1,double z1,double x2,double y2,double z2){
        return new Vector3d(y1*z2-z1*y2,z1*x2-x1*z2,x1*y2-y1*x2);
    }

    public static double dot(double x1,double y1,double z1,double x2,double y2,double z2){
        return x1*x2+y1*y2+z1*z2;
    }

    public static double dot(Vector3d v1,Vector3d v2){
        return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    }

    public static double dst(double x1,double y1,double z1,double x2,double y2,double z2){
        double dx=x1-x2;
        double dy=y1-y2;
        double dz=z1-z2;
        return (double)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public static double dst(Vector3d v1,Vector3d v2){
        double dx=v1.x-v2.x;
        double dy=v1.y-v2.y;
        double dz=v1.z-v2.z;
        return (double)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public static double len(double x,double y,double z){
        return (double)Math.sqrt(x*x+y*y+z*z);
    }

    public static double len(Vector3d v){
        return (double)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
    }

    public static boolean equals(Vector3d v1,Vector3d v2){
        return v1.x==v2.x && v1.y==v2.y && v1.z==v2.z;
    }


    @Override
    public String toString(){
        return "x: "+x+", y: "+y+", z: "+z;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().getTypeName().equals(this.getClass().getTypeName())){
            Vector3d v=(Vector3d)o;
            return v.x==x && v.y==y && v.z==z;
        }
        return false;
    }
    
}
