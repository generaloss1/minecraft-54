package minecraft54.engine.math.vectors;

import minecraft54.engine.math.Maths;

public class Vector3f{

    public float x, y, z;

    public Vector3f(){}

    public Vector3f(float xyz){
        x=xyz;
        y=xyz;
        z=xyz;
    }

    public Vector3f(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public Vector3f(double xyz){
        x=(float)xyz;
        y=(float)xyz;
        z=(float)xyz;
    }

    public Vector3f(double x,double y,double z){
        this.x=(float)x;
        this.y=(float)y;
        this.z=(float)z;
    }

    public Vector3f(Vector3f v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3f(Vector3d v){
        x=(float)v.x;
        y=(float)v.y;
        z=(float)v.z;
    }

    public Vector3f(Vector3i v){
        x=v.x;
        y=v.y;
        z=v.z;
    }

    public Vector3f(Vector2 v){
        x=v.x;
        y=v.y;
    }

    public Vector3f(Vector2i v){
        x=v.x;
        y=v.y;
    }

    public Vector3f set(float xyz){
        x=xyz;
        y=xyz;
        z=xyz;
        return this;
    }

    public Vector3f set(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }

    public Vector3f set(Vector3f v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }

    public Vector3f set(Vector3d v){
        x=(float)v.x;
        y=(float)v.y;
        z=(float)v.z;
        return this;
    }

    public Vector3f set(Vector2 v){
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

    public Vector3f add(float xyz){
        x+=xyz;
        y+=xyz;
        z+=xyz;
        return this;
    }

    public Vector3f add(float x,float y,float z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }

    public Vector3f add(double x,double y,double z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }

    public Vector3f add(Vector3f v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3f add(Vector3d v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3f add(Vector2 v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3f sub(float xyz){
        x-=xyz;
        y-=xyz;
        z-=xyz;
        return this;
    }

    public Vector3f sub(float x,float y,float z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }

    public Vector3f sub(double x,double y,double z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }

    public Vector3f sub(Vector3f v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3f sub(Vector3d v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3f sub(Vector2 v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3f mul(float xyz){
        x*=xyz;
        y*=xyz;
        z*=xyz;
        return this;
    }

    public Vector3f mul(float x,float y,float z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }

    public Vector3f mul(double x,double y,double z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }

    public Vector3f mul(Vector3f v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3f mul(Vector3d v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3f div(float xyz){
        x/=xyz;
        y/=xyz;
        z/=xyz;
        return this;
    }

    public Vector3f div(float x,float y,float z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }

    public Vector3f div(double x,double y,double z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }

    public Vector3f div(Vector3f v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public Vector3f div(Vector3d v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public float len(){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3f module(){
        if(x<0)
            x*=-1;
        if(y<0)
            y*=-1;
        if(z<0)
            z*=-1;
        return this;
    }

    public Vector3f zero(){
        x=0;
        y=0;
        z=0;
        return this;
    }

    public boolean isZero(){
        return x==0 && y==0 && z==0;
    }

    public Vector3f nor(){
        double len2=x*x+y*y+z*z;
        if(len2==0 || len2==1)
            return this;
        double invLen=1/Math.sqrt(len2);
        x*=invLen;
        y*=invLen;
        z*=invLen;
        return this;
    }

    public float dot(float x,float y,float z){
        return this.x*x+this.y*y+this.z*z;
    }

    public float dot(Vector3f v){
        return x*v.x+y*v.y+z*v.z;
    }

    public float dst(float x,float y,float z){
        float dx=this.x-x;
        float dy=this.y-y;
        float dz=this.z-z;
        return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public float dst(Vector3f v){
        float dx=x-v.x;
        float dy=y-v.y;
        float dz=z-v.z;
        return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public boolean equals(Vector3f v){
        return v.x==x && v.y==y && v.z==z;
    }

    public boolean equals(float x,float y){
        return this.x==x && this.y==y && this.z==z;
    }

    public Vector3f crs(Vector3f vector){
        this.x=y*vector.z-z*vector.y;
        this.y=z*vector.x-x*vector.z;
        this.z=x*vector.y-y*vector.x;
        return this;
    }

    public Vector3f crs(float x,float y,float z){
        this.x=this.y*z-this.z*y;
        this.y=this.z*x-this.x*z;
        this.z=this.x*y-this.y*x;
        return this;
    }

    public Vector3f clone(){
        return new Vector3f(this);
    }


    public static Vector3f crs(Vector3f a,Vector3f b){
        return new Vector3f(a.y*b.z-a.z*b.y,a.z*b.x-a.x*b.z,a.x*b.y-a.y*b.x);
    }

    public static Vector3f crs(float x1,float y1,float z1,float x2,float y2,float z2){
        return new Vector3f(y1*z2-z1*y2,z1*x2-x1*z2,x1*y2-y1*x2);
    }

    public static float dot(float x1,float y1,float z1,float x2,float y2,float z2){
        return x1*x2+y1*y2+z1*z2;
    }

    public static float dot(Vector3f v1,Vector3f v2){
        return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    }

    public static float dst(float x1,float y1,float z1,float x2,float y2,float z2){
        float dx=x1-x2;
        float dy=y1-y2;
        float dz=z1-z2;
        return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public static float dst(Vector3f v1,Vector3f v2){
        float dx=v1.x-v2.x;
        float dy=v1.y-v2.y;
        float dz=v1.z-v2.z;
        return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public static float len(float x,float y,float z){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public static float len(Vector3f v){
        return (float)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
    }

    public static boolean equals(Vector3f v1,Vector3f v2){
        return v1.x==v2.x && v1.y==v2.y && v1.z==v2.z;
    }


    @Override
    public String toString(){
        return "x: "+x+", y: "+y+", z: "+z;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().getTypeName().equals(this.getClass().getTypeName())){
            Vector3f v=(Vector3f)o;
            return v.x==x && v.y==y && v.z==z;
        }
        return false;
    }

}