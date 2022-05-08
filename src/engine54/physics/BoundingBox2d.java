package engine54.physics;

import engine54.maths.vectors.Vector2d;
import engine54.maths.vectors.Vector2f;

public class BoundingBox2d{

    private final Vector2d A,B,position,velocity;

    public BoundingBox2d(double ax,double ay,double bx,double by){
        this.A=new Vector2d(ax,ay);
        this.B=new Vector2d(bx,by);
        position=new Vector2d();
        velocity=new Vector2d();
    }

    public BoundingBox2d(Vector2d A,Vector2d B){
        this.A=A;
        this.B=B;
        position=new Vector2d();
        velocity=new Vector2d();
    }

    public BoundingBox2d(Vector2f A,Vector2f B){
        this.A=new Vector2d(A);
        this.B=new Vector2d(B);
        position=new Vector2d();
        velocity=new Vector2d();
    }

    public BoundingBox2d(BoundingBox2d h){
        this.A=h.A.clone();
        this.B=h.B.clone();
        this.position=h.position.clone();
        this.velocity=h.velocity.clone();
    }

    public void setHitbox(Vector2d A,Vector2d B){
        this.A.set(A);
        this.B.set(B);
    }

    public Vector2d getA(){
        return A;
    }
    public Vector2d getB(){
        return B;
    }

    public Vector2d getPosition(){
        return position;
    }

    public Vector2d getPosA(){
        return A.clone().add(position);
    }
    public Vector2d getPosB(){
        return B.clone().add(position);
    }

    public double getWidth(){
        return B.x-A.x;
    }

    public double getHeight(){
        return B.y-A.y;
    }

    public Vector2d getVelocity(){
        return velocity;
    }

    public BoundingBox2d clone(){
        return new BoundingBox2d(this);
    }

}
