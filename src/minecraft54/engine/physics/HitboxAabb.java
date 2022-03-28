package minecraft54.engine.physics;

import minecraft54.engine.math.vectors.Vector3f;
import minecraft54.engine.math.vectors.Vector3d;

public class HitboxAabb{
    
    
    private final Vector3d A,B,position,move;


    public HitboxAabb(double ax,double ay,double az,double bx,double by,double bz){
        this.A=new Vector3d(ax,ay,az);
        this.B=new Vector3d(bx,by,bz);
        position=new Vector3d();
        move=new Vector3d();
    }
    
    public HitboxAabb(Vector3d A,Vector3d B){
        this.A=A;
        this.B=B;
        position=new Vector3d();
        move=new Vector3d();
    }

    public HitboxAabb(Vector3f A,Vector3f B){
        this.A=new Vector3d(A);
        this.B=new Vector3d(B);
        position=new Vector3d();
        move=new Vector3d();
    }

    public HitboxAabb(HitboxAabb h){
        this.A=new Vector3d(h.A);
        this.B=new Vector3d(h.B);
        this.position=new Vector3d(h.position);
        this.move=new Vector3d(h.move);
    }


    public HitboxAabb clone(){
        return new HitboxAabb(this);
    }
    
    public void setHitbox(Vector3d A,Vector3d B){
        this.A.set(A);
        this.B.set(B);
    }
    
    public Vector3d getA(){
        return A;
    }
    public Vector3d getB(){
        return B;
    }

    public Vector3d getPosition(){
        return position;
    }

    public Vector3d getPosA(){
        return A.clone().add(position);
    }
    public Vector3d getPosB(){
        return B.clone().add(position);
    }

    public void move(Vector3d v){
        move.set(v);
    }
    public void move(Vector3f v){
        move.set(v);
    }
    public void move(float x,float y,float z){
        move.set(x,y,z);
    }

    public Vector3d getMove(){
        return move;
    }


}
