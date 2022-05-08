package engine54.audio;

import engine54.maths.Maths;
import engine54.maths.vectors.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class SoundSource{


    private final int sourceId;


    public SoundSource(){
        sourceId=alGenSources();
    }


    public SoundSource setBuffer(SoundBuffer soundBuffer){
        if(soundBuffer!=null)
            alSourcei(sourceId,AL_BUFFER,soundBuffer.getBufferId());
        return this;
    }

    public SoundSource setBuffer(int bufferId){
        alSourcei(sourceId,AL_BUFFER,bufferId);
        return this;
    }


    public SoundSource setLoop(boolean loop){
        alSourcei(sourceId,AL_LOOPING,loop?AL_TRUE:AL_FALSE);
        return this;
    }

    public SoundSource setRelative(boolean relative){
        alSourcei(sourceId,AL_SOURCE_RELATIVE,relative?AL_TRUE:AL_FALSE);
        return this;
    }

    public SoundSource setPosition(Vector3f position){
        alSource3f(sourceId,AL_POSITION,position.x,position.y,position.z);
        return this;
    }

    public SoundSource setSpeed(Vector3f speed){
        alSource3f(sourceId,AL_VELOCITY,speed.x,speed.y,speed.z);
        return this;
    }

    public SoundSource setVolume(float gain){
        alSourcef(sourceId,AL_GAIN,gain);
        return this;
    }

    public SoundSource setPitch(float pitch){
        alSourcef(sourceId,AL_PITCH,pitch);
        return this;
    }

    public SoundSource setPan(float pan){
        alSource3f(sourceId,AL_POSITION,Maths.cos((pan-1)*Maths.PI/2),0,Maths.sin((pan+1)*Maths.PI/2));
        return this;
    }


    public String getStatus(){
        int state=alGetSourcei(sourceId,AL_SOURCE_STATE);
        return state==AL_STOPPED?"Stopped":state==AL_PLAYING?"Playing":state==AL_PAUSED?"Paused":state==AL_INITIAL?"Initial":null;
    }

    public int getState(){
        return alGetSourcei(sourceId,AL_SOURCE_STATE);
    }

    public SoundSource play(){
        alSourcePlay(sourceId);
        return this;
    }

    public SoundSource pause(){
        alSourcePause(sourceId);
        return this;
    }

    public SoundSource stop(){
        alSourceStop(sourceId);
        return this;
    }


    public void dispose(){
        stop();
        alDeleteSources(sourceId);
    }


}
