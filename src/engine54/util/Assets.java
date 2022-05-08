package engine54.util;

import engine54.audio.SoundBuffer;
import engine54.graphics.ShaderProgram;
import engine54.graphics.texture.Texture;
import engine54.graphics.texture.Texture3D;
import engine54.graphics.TrueTypeFont;

import java.util.HashMap;

public class Assets{


    public static HashMap<String,Texture> textures=new HashMap<>();

    public static boolean loadTexture(String path,String id,boolean invY){
        Texture texture=new Texture(path,invY);
        textures.put(id,texture);
        return true;
    }

    public static boolean loadTexture(String path,String id){
        Texture texture=new Texture(path);
        textures.put(id,texture);
        return true;
    }

    public static boolean loadTexture(Texture texture,String id){
        textures.put(id,texture);
        return true;
    }

    public static Texture getTexture(String id){
        return textures.get(id);
    }

    public static void disposeTextures(){
        for(String id:textures.keySet())
            textures.get(id).dispose();
        textures.clear();
    }


    public static HashMap<String,Texture3D> textures3D=new HashMap<>();

    public static boolean loadTexture3D(Texture3D texture3d,String id){
        textures3D.put(id,texture3d);
        return true;
    }

    public static Texture3D getTexture3D(String id){
        return textures3D.get(id);
    }

    public static void disposeTextures3D(){
        for(String id: textures3D.keySet())
            textures3D.get(id).dispose();
        textures3D.clear();
    }



    public static HashMap<String,TrueTypeFont> TTFs=new HashMap<>();

    public static void loadTTF(TrueTypeFont ttf,String id){
        TTFs.put(id,ttf);
    }

    public static TrueTypeFont getTTF(String id){
        return TTFs.get(id);
    }

    public static void disposeTTFs(){
        for(String id: TTFs.keySet())
            TTFs.get(id).dispose();
        TTFs.clear();
    }



    public static HashMap<String,SoundBuffer> sounds=new HashMap<>();

    public static boolean loadSound(String path,String id){
        SoundBuffer sound=new SoundBuffer(path);
        sounds.put(id,sound);
        return true;
    }

    public static SoundBuffer getSound(String id){
        return sounds.get(id);
    }

    public static void disposeSounds(){
        for(String id:sounds.keySet())
            sounds.get(id).dispose();
        sounds.clear();
    }


    public static HashMap<String,ShaderProgram> shaders=new HashMap<>();

    public static void loadShader(ShaderProgram shader,String id){
        shaders.put(id,shader);
    }

    public static ShaderProgram getShader(String id){
        return shaders.get(id);
    }

    public static void disposeShaders(){
        for(String id:shaders.keySet())
            shaders.get(id).dispose();
        shaders.clear();
    }


    public static void dispose(){
        disposeTextures();
        disposeTextures3D();
        disposeTTFs();
        disposeSounds();
        disposeShaders();
    }

}
