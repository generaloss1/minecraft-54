package minecraft54.engine.physics;

import minecraft54.engine.math.vectors.Vector3;

public class CubeHitbox{
    
    
    private final Vector3 A,B,position,move;
    
    
    public CubeHitbox(Vector3 A,Vector3 B){
        this.A=A;
        this.B=B;
        position=new Vector3();
        move=new Vector3();
    }

    public CubeHitbox(CubeHitbox h){
        this.A=new Vector3(h.A);
        this.B=new Vector3(h.B);
        this.position=new Vector3(h.position);
        this.move=new Vector3(h.move);
    }


    public CubeHitbox clone(){
        return new CubeHitbox(this);
    }
    
    public void setHitbox(Vector3 A,Vector3 B){
        this.A.set(A);
        this.B.set(B);
    }
    
    public Vector3 getA(){
        return A;
    }
    public Vector3 getB(){
        return B;
    }

    public Vector3 getPosition(){
        return position;
    }

    public Vector3 getPosA(){
        return A.clone().add(position);
    }
    public Vector3 getPosB(){
        return B.clone().add(position);
    }

    public void move(Vector3 v){
        move.set(v);
    }
    public void move(float x,float y,float z){
        move.set(x,y,z);
    }

    public Vector3 getMove(){
        return move;
    }


}
