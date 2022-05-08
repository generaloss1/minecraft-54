package engine54.audio;

import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class SoundListener{

    public static void setSpeed(Vector3f speed){
        alListener3f(AL_VELOCITY,speed.x,speed.y,speed.z);
    }

    public static void setSpeed(Vector3d speed){
        alListener3f(AL_VELOCITY,(float)speed.x,(float)speed.y,(float)speed.z);
    }

    public static void setPosition(Vector3f position){
        alListener3f(AL_POSITION,position.x,position.y,position.z);
    }

    public static void setPosition(Vector3d position){
        alListener3f(AL_POSITION,(float)position.x,(float)position.y,(float)position.z);
    }

    public static void setOrientation(Vector3f at,Vector3f up){
        float[] data=new float[6];
        data[0]=at.x;
        data[1]=at.y;
        data[2]=at.z;
        data[3]=up.x;
        data[4]=up.y;
        data[5]=up.z;
        alListenerfv(AL_ORIENTATION,data);
    }

}
