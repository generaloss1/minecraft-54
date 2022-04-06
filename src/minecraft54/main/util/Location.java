package minecraft54.main.util;

import minecraft54.engine.maths.vectors.Vector3d;

public class Location{

    private double x,y,z,pitch,yaw;

    public Location(){}

    public Location(double x,double y,double z){
        setPosition(x,y,z);
    }

    public Location(double x,double y,double z,double pitch,double yaw){
        setLocation(x,y,z,pitch,yaw);
    }


    public void setLocation(double x,double y,double z,double pitch,double yaw){
        this.x=x;
        this.y=y;
        this.z=z;
        this.pitch=pitch;
        this.yaw=yaw;
    }
    public void setPosition(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public void setRotation(double pitch,double yaw){
        this.pitch=pitch;
        this.yaw=yaw;
    }


    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
    public void setZ(double z){
        this.z=z;
    }
    public void setPitch(double pitch){
        this.pitch=pitch;
    }
    public void setYaw(double yaw){
        this.yaw=yaw;
    }


    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public double getPitch(){
        return pitch;
    }
    public double getYaw(){
        return yaw;
    }


    public Vector3d getDirection(){
        double radPitch=Math.toRadians(pitch);
        double radYaw=Math.toRadians(yaw);

        double xz=Math.cos(radPitch);
        return new Vector3d(
                -xz*Math.sin(radYaw),
                -Math.sin(radPitch),
                xz*Math.cos(radYaw)
        );
    }

    public void setDirection(Vector3d vector){
        double _2PI = 6.283185307179586D;
        double x = vector.x;
        double z = vector.z;
        if(x == 0.0D && z == 0.0D){
            this.pitch=(float)(vector.y>0.0D? -90:90);
        }else{
            double theta = Math.atan2(-x, z);
            this.yaw = (float)Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D);
            double x2 = x*x;
            double z2 = z*z;
            double xz = Math.sqrt(x2 + z2);
            this.pitch = (float)Math.toDegrees(Math.atan(-vector.y / xz));
        }
    }


}
