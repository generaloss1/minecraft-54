package engine54.physics;

import engine54.maths.vectors.Vector2d;

public class Velocity2d{

    private final Vector2d value;
    private double max;

    public Velocity2d(){
        value=new Vector2d();
        max=1;
    }

    public Velocity2d(Velocity2d velocity){
        value=velocity.value.clone();
        max=velocity.max;
    }

    public Velocity2d collidedAxesToZero(Vector2d collidedMovement){
        if(value.x!=0 && collidedMovement.x==0)
            value.x=0;

        if(value.y!=0 && collidedMovement.y==0)
            value.y=0;

        return this;
    }

    public Velocity2d clampToMax(){
        Vector2d nor=value.clone().nor().module();
        if(value.x>max*nor.x)
            value.x=max*nor.x;
        else if(value.x<-max*nor.x)
            value.x=-max*nor.x;

        if(value.y>max*nor.y)
            value.y=max*nor.y;
        else if(value.y<-max*nor.y)
            value.y=-max*nor.y;

        return this;
    }

    public Velocity2d reduce(double reduce){
        Vector2d nor=value.clone().nor().module();

        double r=reduce*nor.x;
        if(value.x>0){
            if(value.x>=r)
                value.x-=r;
            else
                value.x=0;
        }else if(value.x<0){
            if(value.x<=-r)
                value.x+=r;
            else
                value.x=0;
        }

        r=reduce*nor.y;
        if(value.y>0){
            if(value.y>=r)
                value.y-=r;
            else
                value.y=0;
        }else if(value.y<0){
            if(value.y<=-r)
                value.y+=r;
            else
                value.y=0;
        }

        return this;
    }

    public Vector2d get(){
        return value;
    }

    public double max(){
        return max;
    }

    public Velocity2d setMax(double max){
        this.max=max;
        return this;
    }

    public Velocity2d clone(){
        return new Velocity2d(this);
    }

}
