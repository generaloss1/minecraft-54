package engine54.physics;

import engine54.maths.vectors.Vector3f;
import engine54.maths.vectors.Vector3d;

public class BoundingBox3d{

    private final Vector3d A,B,position,velocity;

    public BoundingBox3d(double ax,double ay,double az,double bx,double by,double bz){
        this.A=new Vector3d(ax,ay,az);
        this.B=new Vector3d(bx,by,bz);
        position=new Vector3d();
        velocity=new Vector3d();
    }

    public BoundingBox3d(Vector3d A,Vector3d B){
        this.A=A;
        this.B=B;
        position=new Vector3d();
        velocity=new Vector3d();
    }

    public BoundingBox3d(Vector3f A,Vector3f B){
        this.A=new Vector3d(A);
        this.B=new Vector3d(B);
        position=new Vector3d();
        velocity=new Vector3d();
    }

    public BoundingBox3d(BoundingBox3d h){
        this.A=h.A.clone();
        this.B=h.B.clone();
        this.position=h.position.clone();
        this.velocity=h.velocity.clone();
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

    public Vector3d getVelocity(){
        return velocity;
    }

    public BoundingBox3d clone(){
        return new BoundingBox3d(this);
    }

}
