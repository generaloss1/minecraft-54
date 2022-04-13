package minecraft54.engine.maths.vectors;

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

    public Vector3i add(float xyz){
        x+=xyz;
        y+=xyz;
        z+=xyz;
        return this;
    }

    public Vector3i add(float x,float y,float z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }

    public Vector3i add(double xyz){
        x+=xyz;
        y+=xyz;
        z+=xyz;
        return this;
    }

    public Vector3i add(double x,double y,double z){
        this.x+=x;
        this.y+=y;
        this.z+=z;
        return this;
    }

    public Vector3i add(Vector3f v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3i add(Vector3d v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3i add(Vector3i v){
        x+=v.x;
        y+=v.y;
        z+=v.z;
        return this;
    }

    public Vector3i add(Vector2f v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3i add(Vector2d v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3i add(Vector2i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector3i sub(float xyz){
        x-=xyz;
        y-=xyz;
        z-=xyz;
        return this;
    }

    public Vector3i sub(float x,float y,float z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }

    public Vector3i sub(double xyz){
        x-=xyz;
        y-=xyz;
        z-=xyz;
        return this;
    }

    public Vector3i sub(double x,double y,double z){
        this.x-=x;
        this.y-=y;
        this.z-=z;
        return this;
    }

    public Vector3i sub(Vector3f v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3i sub(Vector3d v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3i sub(Vector3i v){
        x-=v.x;
        y-=v.y;
        z-=v.z;
        return this;
    }

    public Vector3i sub(Vector2f v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3i sub(Vector2d v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3i sub(Vector2i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector3i mul(float xyz){
        x*=xyz;
        y*=xyz;
        z*=xyz;
        return this;
    }

    public Vector3i mul(float x,float y,float z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }

    public Vector3i mul(double xyz){
        x*=xyz;
        y*=xyz;
        z*=xyz;
        return this;
    }

    public Vector3i mul(double x,double y,double z){
        this.x*=x;
        this.y*=y;
        this.z*=z;
        return this;
    }

    public Vector3i mul(Vector3f v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3i mul(Vector3d v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3i mul(Vector3i v){
        x*=v.x;
        y*=v.y;
        z*=v.z;
        return this;
    }

    public Vector3i div(float xyz){
        x/=xyz;
        y/=xyz;
        z/=xyz;
        return this;
    }

    public Vector3i div(float x,float y,float z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }

    public Vector3i div(double xyz){
        x/=xyz;
        y/=xyz;
        z/=xyz;
        return this;
    }

    public Vector3i div(double x,double y,double z){
        this.x/=x;
        this.y/=y;
        this.z/=z;
        return this;
    }

    public Vector3i div(Vector3f v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public Vector3i div(Vector3d v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public Vector3i div(Vector3i v){
        x/=v.x;
        y/=v.y;
        z/=v.z;
        return this;
    }

    public double len(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public Vector3i module(){
        if(x<0)
            x*=-1;
        if(y<0)
            y*=-1;
        if(z<0)
            z*=-1;
        return this;
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

    public Vector3i nor(){
        double len2=x*x+y*y+z*z;
        if(len2==0 || len2==1)
            return this;
        div(Math.sqrt(len2));
        return this;
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

    public Vector3i crs(int x,int y,int z){
        this.x=this.y*z-this.z*y;
        this.y=this.z*x-this.x*z;
        this.z=this.x*y-this.y*x;
        return this;
    }

    public Vector3i crs(Vector3i v){
        this.x=y*v.z-z*v.y;
        this.y=z*v.x-x*v.z;
        this.z=x*v.y-y*v.x;
        return this;
    }


    @Override
    public Vector3i clone(){
        return new Vector3i(this);
    }

    @Override
    public String toString(){
        return "x: "+x+", y: "+y+", z: "+z;
    }


    public static Vector3i crs(Vector3i a,Vector3i b){
        return new Vector3i(a.y*b.z-a.z*b.y,a.z*b.x-a.x*b.z,a.x*b.y-a.y*b.x);
    }

    public static Vector3i crs(int x1,int y1,int z1,int x2,int y2,int z2){
        return new Vector3i(y1*z2-z1*y2,z1*x2-x1*z2,x1*y2-y1*x2);
    }

    public static int dot(Vector3i a,Vector3i b){
        return a.x*b.x+a.y*b.y+a.z*b.z;
    }

    public static int dot(int x1,int y1,int z1,int x2,int y2,int z2){
        return x1*x2+y1*y2+z1*z2;
    }

    public static double len(int x,int y,int z){
        return Math.sqrt(x*x+y*y+z*z);
    }

}
