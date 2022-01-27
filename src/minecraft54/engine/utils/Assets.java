package minecraft54.engine.utils;

import minecraft54.engine.graphics.ShaderProgram;
import minecraft54.engine.graphics.Texture;
import minecraft54.engine.graphics.Texture3D;
import minecraft54.engine.graphics.TrueTypeFont;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Assets{



    public static HashMap<String, Texture> textures=new HashMap<>();
    public static List<String> textures_ids=new ArrayList<>();

    public static boolean loadTexture(String path,String id){
        File file=new File(path);
        if(!file.exists()){
            System.err.println("Texture load error: "+path+" is not exists.");
            return false;
        }
        Texture texture=new Texture(path).gen();
        textures.put(id,texture);
        textures_ids.add(id);
        return true;
    }

    public static boolean loadTexture(Texture texture,String id){
        textures.put(id,texture);
        textures_ids.add(id);
        return true;
    }

    public static Texture getTexture(String id){
        return textures.get(id);
    }

    public static void disposeTextures(){
        for(String id:textures_ids)
            textures.get(id).dispose();
        textures.clear();
    }


    public static HashMap<String,Texture3D> textures3d=new HashMap<>();
    public static List<String> textures3d_ids=new ArrayList<>();

    public static boolean loadTexture3d(Texture3D texture3d,String id){
        textures3d.put(id,texture3d);
        textures3d_ids.add(id);
        return true;
    }

    public static Texture3D getTexture3d(String id){
        return textures3d.get(id);
    }

    public static void disposeTextures3d(){
        for(String id:textures3d_ids)
            textures3d.get(id).dispose();
        textures3d.clear();
    }



    public static HashMap<String,TrueTypeFont> ttfs=new HashMap<>();
    public static List<String> ttfs_ids=new ArrayList<>();

    public static void loadTTF(TrueTypeFont ttf,String id){
        ttfs.put(id,ttf);
        ttfs_ids.add(id);
    }

    public static TrueTypeFont getTTF(String id){
        return ttfs.get(id);
    }

    public static void disposeTTFs(){
        for(String id:ttfs_ids)
            ttfs.get(id).dispose();
        ttfs.clear();
    }



    /*public static HashMap<String,Sound> sounds=new HashMap<>();
    public static List<String> sounds_ids=new ArrayList<>();

    public static void loadSound(String path,String id){
        Sound sound=Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(id,sound);
        sounds_ids.add(id);
    }

    public static Sound getSound(String id){
        return sounds.get(id);
    }

    public static void disposeSounds(){
        for(String id:sounds_ids)
            sounds.get(id).dispose();
        sounds.clear();
    }



    public static HashMap<String,Music> musics=new HashMap<>();
    public static List<String> musics_ids=new ArrayList<>();

    public static void loadMusic(String path,String id){
        Music music=Gdx.audio.newMusic(Gdx.files.internal(path));
        musics.put(id,music);
        musics_ids.add(id);
    }

    public static Music getMusic(String id){
        return musics.get(id);
    }

    public static void disposeMusics(){
        for(String id:musics_ids)
            musics.get(id).dispose();
        musics.clear();
    }



    public static HashMap<String,TextureRegion> texture_regions=new HashMap<>();
    public static List<String> texture_regions_ids=new ArrayList<>();

    public static void loadTextureReg(String path,String id){
        Texture texture=new Texture(Gdx.files.internal(path));
        TextureRegion texture_region=new TextureRegion(texture);
        texture_regions.put(id,texture_region);
        texture_regions_ids.add(id);
    }

    public static void loadTextureReg(String path,String id,int x,int y,int w,int h){
        Texture texture=new Texture(Gdx.files.internal(path));
        TextureRegion texture_region=new TextureRegion(texture,x,y,w,h);
        texture_regions.put(id,texture_region);
        texture_regions_ids.add(id);
    }

    public static void loadTextureReg(Texture texture,String id){
        TextureRegion texture_region=new TextureRegion(texture);
        texture_regions.put(id,texture_region);
        texture_regions_ids.add(id);
    }

    public static void loadTextureReg(Texture texture,String id,int x,int y,int w,int h){
        TextureRegion texture_region=new TextureRegion(texture,x,y,w,h);
        texture_regions.put(id,texture_region);
        texture_regions_ids.add(id);
    }

    public static TextureRegion getTextureReg(String id){
        return texture_regions.get(id);
    }

    public static void disposeTextureRegs(){
        for(String id:texture_regions_ids)
            texture_regions.get(id).getTexture().dispose();
        texture_regions.clear();
    }*/



    public static HashMap<String, ShaderProgram> shaders=new HashMap<>();
    public static List<String> shaders_ids=new ArrayList<>();

    public static void loadShader(ShaderProgram shader,String id){
        shaders.put(id,shader);
        shaders_ids.add(id);
    }

    public static ShaderProgram getShader(String id){
        return shaders.get(id);
    }

    public static void disposeShaders(){
        for(String id:shaders_ids)
            shaders.get(id).dispose();
        shaders.clear();
    }



    public static void dispose(){
        disposeTextures();
        disposeTextures3d();
        //disposeTextureRegs();
        disposeTTFs();
        //disposeSounds();
        //disposeMusics();
        disposeShaders();
    }



}
