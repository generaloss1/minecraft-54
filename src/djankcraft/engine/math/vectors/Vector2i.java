package djankcraft.engine.math.vectors;

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
    public Vector2i set(Vector2i v){
        x=v.x;
        y=v.y;
        return this;
    }

    public Vector2i add(int xy){
        x+=xy;
        y+=xy;
        return this;
    }
    public Vector2i add(int x,int y){
        this.x+=x;
        this.y+=y;
        return this;
    }
    public Vector2i add(Vector2i v){
        x+=v.x;
        y+=v.y;
        return this;
    }

    public Vector2i sub(int xy){
        x-=xy;
        y-=xy;
        return this;
    }
    public Vector2i sub(int x,int y){
        this.x-=x;
        this.y-=y;
        return this;
    }
    public Vector2i sub(Vector2i v){
        x-=v.x;
        y-=v.y;
        return this;
    }

    public Vector2i scl(int xy){
        x*=xy;
        y*=xy;
        return this;
    }
    public Vector2i scl(int x,int y){
        this.x*=x;
        this.y*=y;
        return this;
    }
    public Vector2i scl(Vector2i v){
        x*=v.x;
        y*=v.y;
        return this;
    }

    public Vector2i div(int xy){
        x/=xy;
        y/=xy;
        return this;
    }
    public Vector2i div(int x,int y){
        this.x/=x;
        this.y/=y;
        return this;
    }
    public Vector2i div(Vector2i v){
        x/=v.x;
        y/=v.y;
        return this;
    }

    public float len(){
        return (float)Math.sqrt(x*x+y*y);
    }

    public Vector2i zero(){
        x=0;
        y=0;
        return this;
    }
    public boolean isZero(){
        return x==0 && y==0;
    }

    public int dot(int x,int y){
        return this.x*x+this.y*y;
    }
    public int dot(Vector2i v){
        return x*v.x+y*v.y;
    }

    public float dst(int x,int y){
        int dx=this.x-x;
        int dy=this.y-y;
        return (float)Math.sqrt(dx*dx+dy*dy);
    }
    public float dst(Vector2i v){
        int dx=x-v.x;
        int dy=y-v.y;
        return (float)Math.sqrt(dx*dx+dy*dy);
    }

    public int deg(){
        return (int)(Math.atan2(y,x)*180/Math.PI);
    }
    public int rad(){
        return (int)Math.atan2(y,x);
    }

    public boolean equals(Vector2i v){
        return v.x==x && v.y==y;
    }
    public boolean equals(int x,int y){
        return this.x==x && this.y==y;
    }

    public int crs(Vector2i v){
        return this.x*v.y-this.y*v.x;
    }
    public int crs(int x,int y){
        return this.x*y-this.y*x;
    }


    public static int dot(int x1,int y1,int x2,int y2){
        return x1*x2+y1*y2;
    }
    public static int dot(Vector2i v1,Vector2i v2){
        return v1.x*v2.x+v1.y*v2.y;
    }

    public static float dst(int x1,int y1,int x2,int y2){
        int dx=x1-x2;
        int dy=y1-y2;
        return (float)Math.round(dx*dx+dy*dy);
    }
    public static float dst(Vector2i v1,Vector2i v2){
        int dx=v1.x-v2.x;
        int dy=v1.y-v2.y;
        return (float)Math.round(dx*dx+dy*dy);
    }

    public static float len(int x,int y){
        return (float)Math.sqrt(x*x+y*y);
    }
    public static float len(Vector2i v){
        return (float)Math.sqrt(v.x*v.x+v.y*v.y);
    }

    public static boolean equals(Vector2i v1,Vector2i v2){
        return v1.x==v2.x && v1.y==v2.y;
    }


    @Override
    public String toString(){
        return "x: "+x+", y: "+y;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass().getTypeName().equals(this.getClass().getTypeName())){
            Vector2i v=(Vector2i)o;
            return v.x==x && v.y==y;
        }
        return false;
    }

}
