package minecraft54.engine.maths;

import minecraft54.engine.maths.vectors.Vector3f;

public class EulerAngle{


    private float pitch,yaw,roll;


    public EulerAngle(){}

    public EulerAngle(float pitch,float yaw){
        this.pitch=pitch;
        this.yaw=yaw;
    }

    public EulerAngle(float yaw,float pitch,float roll){
        this.pitch=pitch;
        this.yaw=yaw;
        this.roll=roll;
    }

    public void constrain(){
        if(pitch>90)
            pitch=90;
        else if(pitch<-90)
            pitch=-90;

        if(yaw>=360)
            yaw-=360;
        else if(yaw<=-360)
            yaw+=360;
    }

    public Vector3f direction(){
        double yaw=Math.toRadians(this.yaw);
        double pitch=Math.toRadians(this.pitch);

        return new Vector3f(
                Math.cos(pitch)*Math.cos(yaw),
                Math.sin(pitch),
                Math.cos(pitch)*Math.sin(yaw)
        );
    }

    public void setDirection(Vector3f direction){
        Vector3f dir=direction.clone().nor();

        yaw=-Maths.atan2(dir.x,dir.z)*Maths.toDegrees+90;
        pitch=Maths.atan2(dir.y,Math.sqrt(dir.x*dir.x+dir.z*dir.z))*Maths.toDegrees;
    }

    public void set(EulerAngle eulerAngle){
        yaw=eulerAngle.yaw;
        pitch=eulerAngle.pitch;
        roll=eulerAngle.roll;
    }
    public void set(float yaw,float pitch,float roll){
        this.yaw=yaw;
        this.pitch=pitch;
        this.roll=roll;
    }
    public void set(float yaw,float pitch){
        this.yaw=yaw;
        this.pitch=pitch;
        roll=0;
    }

    public void rotate(float yaw,float pitch,float roll){
        this.yaw+=yaw;
        this.pitch+=pitch;
        this.roll+=roll;
    }

    public void setPitch(float pitch){
        this.pitch=pitch;
    }
    public void setYaw(float yaw){
        this.yaw=yaw;
    }
    public void setRoll(float roll){
        this.roll=roll;
    }

    public float yaw(){
        return yaw;
    }
    public float pitch(){
        return pitch;
    }
    public float roll(){
        return roll;
    }

}
