package minecraft54.main.client.audio;

import minecraft54.engine.audio.SoundBuffer;
import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.math.Maths;
import minecraft54.engine.utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class SoundPack{


    private final List<Sound> place;
    private final List<Sound> destroy;
    private final List<Sound> step;


    public SoundPack(){
        place=new ArrayList<>();
        destroy=new ArrayList<>();
        step=new ArrayList<>();
    }


    public void addPlace(Sound... soundIds){
        place.addAll(List.of(soundIds));
    }
    public void addDestroy(Sound... soundIds){
        destroy.addAll(List.of(soundIds));
    }
    public void addStep(Sound... soundIds){
        step.addAll(List.of(soundIds));
    }

    public void addPlace(String... soundIds){
        for(String soundId: soundIds)
            place.add(new Sound(Assets.getSound(soundId)));
    }
    public void addDestroy(String... soundIds){
        for(String soundId: soundIds)
            destroy.add(new Sound(Assets.getSound(soundId)));
    }
    public void addStep(String... soundIds){
        for(String soundId: soundIds)
            step.add(new Sound(Assets.getSound(soundId)).setVolume(0.3f));
    }

    public void addPlace(float pitch,String... soundIds){
        for(String soundId: soundIds)
            place.add(new Sound(Assets.getSound(soundId)).setPitch(pitch));
    }
    public void addDestroy(float pitch,String... soundIds){
        for(String soundId: soundIds)
            destroy.add(new Sound(Assets.getSound(soundId)).setPitch(pitch));
    }
    public void addStep(float pitch,String... soundIds){
        for(String soundId: soundIds)
            step.add(new Sound(Assets.getSound(soundId)).setPitch(pitch));
    }


    public void playPlace(){
        int size=place.size();
        if(size!=0)
            place.get(Maths.random(0,size-1)).play();
    }
    public void playDestroy(){
        int size=destroy.size();
        if(size!=0)
            destroy.get(Maths.random(0,size-1)).play();
    }
    public void playStep(){
        int size=step.size();
        if(size!=0)
            step.get(Maths.random(0,size-1)).play();
    }


}
