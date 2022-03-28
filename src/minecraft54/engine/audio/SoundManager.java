package minecraft54.engine.audio;

import minecraft54.engine.utils.Assets;
import org.lwjgl.openal.AL10;

import java.util.ArrayList;
import java.util.List;

public class SoundManager{


    public static List<SoundSource> sources=new ArrayList<>();


    public static void play(String id){
        sources.add(new SoundSource().setBuffer(Assets.getSound(id)).play());
    }

    public static void play(String id,float volume){
        sources.add(new SoundSource().setBuffer(Assets.getSound(id)).setVolume(volume).play());
    }

    public static void play(SoundBuffer soundBuffer){
        sources.add(new SoundSource().setBuffer(soundBuffer).play());
    }

    public static void play(SoundSource soundSource){
        sources.add(soundSource.play());
    }


    public static void update(){
        for(int i=0; i<sources.size(); i++){
            SoundSource source=sources.get(i);
            if(source.getState()==AL10.AL_STOPPED){
                source.dispose();
                sources.remove(source);
            }
        }
    }


    public static void dispose(){
        for(int i=0; i<sources.size(); i++){
            SoundSource source=sources.get(i);
            source.dispose();
            sources.remove(source);
        }
    }


}
