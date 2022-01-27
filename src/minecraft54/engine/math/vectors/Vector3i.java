package minecraft54.engine.math.vectors;

public class Vector3i{

    public int x,y,z;

    public Vector3i(){}
    public Vector3i(int xyz){
        x=xyz;
        y=xyz;
        z=xyz;
    }
    public Vector3i(int x,int y,int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public Vector3i(Vector3i v){
        x=v.x;
        y=v.y;
        z=v.z;
    }
    public Vector3i(Vector2i v){
        x=v.x;
        y=v.y;
    }

    public Vector3i set(int xyz){
        x=xyz;
        y=xyz;
        z=xyz;
        return this;
    }
    public Vector3i set(int x,int y,int z){
        this.x=x;
        this.y=y;
        this.z=z;
        return this;
    }
    public Vector3i set(Vector3i v){
        x=v.x;
        y=v.y;
        z=v.z;
        return this;
    }
    public Vector3i set(Vector2i v){
        x=v.x;
        y=v.y;
        z=0;
        return this;
    }

    public Vector3i add(int xyz){
        x+=xyz;
        y+=xyz;
        z+=xyz;
        return this;
    }
    public Vector3i add(int x,int y,int z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }
    public Vector3i add(Vector3i v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }
    public Vector3i add(Vector2i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3i sub(int xyz){
        x-=xyz;
        y-=xyz;
        z-=xyz;
        return this;
    }
    public Vector3i sub(int x,int y,int z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }
    public Vector3i sub(Vector3i v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }
    public Vector3i sub(Vector2i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3i scl(int xyz){
        x*=xyz;
        y*=xyz;
        z*=xyz;
        return this;
    }
    public Vector3i scl(int x,int y,int z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }
    public Vector3i scl(Vector3i v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3i div(int xyz){
        x/=xyz;
        y/=xyz;
        z/=xyz;
        return this;
    }
    public Vector3i div(int x,int y,int z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }
    public Vector3i div(Vector3i v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public float len(){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3i zero(){
        x=0;
        y=0;
        z=0;
        return this;
    }
    public boolean isZero(){
        return x==0 && y==0 && z==0;
    }

    public int dot(int x,int y,int z){
        return this.x*x+this.y*y+this.z*z;
    }
    public int dot(Vector3i v){
        return x*v.x+y*v.y+z*v.z;
    }

    public float dst(int x,int y,int z){
        int dx=this.x-x;
        int dy=this.y-y;
        int dz=this.z-z;
        return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }
    public float dst(Vector3i v){
        int dx=x-v.x;
        int dy=y-v.y;
        int dz=z-v.z;
        return (float)Math.sqrt(dx*dx+dy*dy+dz*dz);
    }

    public boolean equals(Vector3i v){
        return v.x==x && v.y==y && v.z==z;
    }
    public boolean equals(int x,int y,int z){
        return this.x==x && this.y==y && this.z==z;
    }

    public Vector3i crs(Vector3i vector){
        this.x=y*vector.z-z*vector.y;
        this.y=z*vector.x-x*vector.z;
        this.z=x*vector.y-y*vector.x;
        return this;
    }
    public Vector3i crs(int x,int y,int z){
        this.x=this.y*z-this.z*y;
        this.y=this.z*x-this.x*z;
        this.z=this.x*y-this.y*x;
        return this;
    }


    public static int dot(int x1,int y1,int z1,int x2,int y2,int z2){
        return x1*x2+y1*y2+z1*z2;
    }
    public static int dot(Vector3i v1,Vector3i v2){
        return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    }

    public static float dst(int x1,int y1,int z1,int x2,int y2,int z2){
        int dx=x1-x2;
        int dy=y1-y2;
        int dz=z1-z2;
        return (float)Math.round(dx*dx+dy*dy+dz*dz);
    }
    public static float dst(Vector3i v1,Vector3i v2){
        int dx=v1.x-v2.x;
        int dy=v1.y-v2.y;
        int dz=v1.z-v2.z;
        return (float)Math.round(dx*dx+dy*dy+dz*dz);
    }

    public static float len(int x,int y,int z){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }
    public static float len(Vector3i v){
        return (float)Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
    }

    public static boolean equals(Vector3i v1,Vector3i v2){
        return v1.x==v2.x && v1.y==v2.y && v1.z==v2.z;
    }


    @Override
    public String toString(){
        return "x: "+x+", y: "+y+", z: "+z;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().getTypeName().equals(this.getClass().getTypeName())){
            Vector3i v=(Vector3i)o;
            return v.x==x && v.y==y && v.z==z;
        }
        return false;
    }

}
