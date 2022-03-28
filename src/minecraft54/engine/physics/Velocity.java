package minecraft54.engine.physics;

import minecraft54.engine.math.vectors.Vector3f;
import minecraft54.engine.math.vectors.Vector3d;

public class Velocity{


    private final Vector3d value;
    private double max;


    public Velocity(){
        value=new Vector3d();
        max=1;
    }

    public Velocity(Velocity velocity){
        value=velocity.value.clone();
        max=velocity.max;
    }


    public Velocity collidedAxesToZero(Vector3d collidedMovement){
        if(value.x!=0 && collidedMovement.x==0)
            value.x=0;

        if(value.y!=0 && collidedMovement.y==0)
            value.y=0;

        if(value.z!=0 && collidedMovement.z==0)
            value.z=0;

        return this;
    }

    public Velocity clampToMax(){
        Vector3d nor=value.clone().nor().module();
        if(value.x>max*nor.x)
            value.x=max*nor.x;
        else if(value.x<-max*nor.x)
            value.x=-max*nor.x;

        if(value.y>max*nor.y)
            value.y=max*nor.y;
        else if(value.y<-max*nor.y)
            value.y=-max*nor.y;

        if(value.z>max*nor.z)
            value.z=max*nor.z;
        else if(value.z<-max*nor.z)
            value.z=-max*nor.z;
        return this;
    }

    public Velocity reduce(double reduce){
        Vector3d nor=value.clone().nor().module();

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

        r=reduce*nor.z;
        if(value.z>0){
            if(value.z>=r)
                value.z-=r;
            else
                value.z=0;
        }else if(value.z<0){
            if(value.z<=-r)
                value.z+=r;
            else
                value.z=0;
        }

        return this;
    }

    public Velocity add(Vector3f v){
        return this;
    }
    public Velocity add(Vector3d v){
        return this;
    }
    public Velocity add(float x,float y,float z){
        return this;
    }
    public Velocity add(double x,double y,double z){
        return this;
    }


    public Vector3d get(){
        return value;
    }

    public double max(){
        return max;
    }

    public Velocity setMax(double max){
        this.max=max;
        return this;
    }

    public Velocity clone(){
        return new Velocity(this);
    }


}
