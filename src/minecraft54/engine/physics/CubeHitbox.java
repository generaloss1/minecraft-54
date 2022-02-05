package minecraft54.engine.physics;

import minecraft54.engine.math.vectors.Vector3;
import minecraft54.engine.math.vectors.Vector3d;

public class CubeHitbox{
    
    
    private final Vector3d A,B,position,move;
    
    
    public CubeHitbox(Vector3d A,Vector3d B){
        this.A=A;
        this.B=B;
        position=new Vector3d();
        move=new Vector3d();
    }

    public CubeHitbox(Vector3 A,Vector3 B){
        this.A=new Vector3d(A);
        this.B=new Vector3d(B);
        position=new Vector3d();
        move=new Vector3d();
    }

    public CubeHitbox(CubeHitbox h){
        this.A=new Vector3d(h.A);
        this.B=new Vector3d(h.B);
        this.position=new Vector3d(h.position);
        this.move=new Vector3d(h.move);
    }


    public CubeHitbox clone(){
        return new CubeHitbox(this);
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
    public void move(Vector3 v){
        move.set(v);
    }
    public void move(float x,float y,float z){
        move.set(x,y,z);
    }

    public Vector3d getMove(){
        return move;
    }


}
