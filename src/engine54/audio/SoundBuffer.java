package engine54.audio;

import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class SoundBuffer{

    private int bufferId;

    public SoundBuffer(String path){
        ShortBuffer rawAudioBuffer;
        int channels,sampleRate;

        try(MemoryStack stack=stackPush()){
            IntBuffer channelsBuffer=stack.mallocInt(1);
            IntBuffer sampleRateBuffer=stack.mallocInt(1);

            rawAudioBuffer=STBVorbis.stb_vorbis_decode_filename(path,channelsBuffer,sampleRateBuffer);

            channels=channelsBuffer.get(0);
            sampleRate=sampleRateBuffer.get(0);
        }

        if(rawAudioBuffer==null){
            System.err.println("Could not load sound "+path);
            return;
        }

        bufferId=alGenBuffers();
        alBufferData(bufferId,channels==1?AL_FORMAT_MONO16:AL_FORMAT_STEREO16,rawAudioBuffer,sampleRate);
        free(rawAudioBuffer);
    }

    public SoundBuffer(SoundBuffer soundBuffer){
        this.bufferId=soundBuffer.bufferId;
    }

    public int getBufferId(){
        return bufferId;
    }

    public void dispose(){
        alDeleteBuffers(bufferId);
    }

}
