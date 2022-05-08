package engine54.app;

import engine54.audio.SoundManager;
import engine54.graphics.*;
import engine54.graphics.texture.Texture;
import engine54.graphics.texture.Texture3D;
import engine54.graphics.vertices.ElementBufferObject;
import engine54.graphics.vertices.VertexArrayObject;
import engine54.graphics.vertices.VertexBufferObject;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;

import java.util.HashMap;

import static org.lwjgl.opengl.GL33.*;

public class Context{

    private Keyboard keyboard;
    private Mouse mouse;
    private Window window;
    private Screen screen;
    private String currentScreen;
    private float FPS,DELTA_TIME;
    private int prevWidth,prevHeight;
    private final HashMap<String,Screen> screens;
    private FramePostProcessor postProcessor;

    public Context(){
        screens=new HashMap<>();
    }

    public void setWindow(Window window){
        this.window=window;
    }

    public void setKeyboard(Keyboard keyboard){
        this.keyboard=keyboard;
    }

    public void setMouse(Mouse mouse){
        this.mouse=mouse;
    }

    public void begin(AppListener listener){
        listener.create();

        for(String screenId:screens.keySet())
            screens.get(screenId).create();
        window.show();

        long prevTime=System.nanoTime();
        while(!window.close()){

            int width=window.getWidth();
            int height=window.getHeight();
            if(prevWidth!=width || prevHeight!=height){
                prevWidth=width;
                prevHeight=height;
                glViewport(0,0,width,height);
                for(String screenId:screens.keySet())
                    screens.get(screenId).resize(width,height);
            }

            FPS=-1000000000f/(prevTime-(prevTime=System.nanoTime()));
            DELTA_TIME=1/FPS;

            window.pollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            listener.update();
            SoundManager.update();
            if(postProcessor!=null)
                postProcessor.begin();
            if(screen!=null)
                screen.render();
            if(postProcessor!=null)
                postProcessor.end();

            mouse.reset();
            keyboard.reset();
            window.swapBuffers();
        }

        window.hide();
        for(String screenId:screens.keySet())
            screens.get(screenId).dispose();

        VertexArrayObject.unbind();
        VertexBufferObject.unbind();
        ElementBufferObject.unbind();
        ShaderProgram.unbind();
        Texture.unbind();
        Texture3D.unbind();

        listener.dispose();
        window.dispose();
    }


    public void setFramePostProcessor(FramePostProcessor postProcessor){
        this.postProcessor=postProcessor;
    }


    public void setScreen(String screenId,String arg){
        Screen scr=screens.get(screenId);
        screen=scr;
        scr.set(arg);
        currentScreen=screenId;
    }

    public float getFPS(){
        return FPS;
    }

    public float getDeltaTime(){
        return DELTA_TIME;
    }

    public void setScreen(String screenID){
        setScreen(screenID,null);
    }

    public Screen getScreen(String id){
        return screens.get(id);
    }

    public void defineScreen(Screen screen,String id){
        screens.put(id,screen);
    }

    public String currentScreen(){
        return currentScreen;
    }


    public Window getWindow(){
        return window;
    }

    public Keyboard getKeyboard(){
        return keyboard;
    }

    public Mouse getMouse(){
        return mouse;
    }


}
