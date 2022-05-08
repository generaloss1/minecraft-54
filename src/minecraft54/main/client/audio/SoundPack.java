package minecraft54.main.client.audio;

import engine54.maths.Maths;
import engine54.maths.vectors.Vector3f;
import engine54.util.Assets;
import minecraft54.main.Options;

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
            step.add(new Sound(Assets.getSound(soundId)));
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


    public void playPlace(float x,float y,float z){
        int size=place.size();
        if(size!=0)
            place.get(Maths.random(0,size-1)).setVolume(Options.BLOCKS_VOLUME*Options.MASTER_VOLUME).setPosition(x,y,z).play();
    }
    public void playDestroy(float x,float y,float z){
        int size=destroy.size();
        if(size!=0)
            destroy.get(Maths.random(0,size-1)).setVolume(Options.BLOCKS_VOLUME*Options.MASTER_VOLUME).setPosition(x,y,z).play();
    }
    public void playStep(Vector3f pos){
        int size=step.size();
        if(size!=0)
            step.get(Maths.random(0,size-1)).setVolume(0.15f*Options.PLAYERS_VOLUME*Options.MASTER_VOLUME).setPosition(pos).play();
    }


}
