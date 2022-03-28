package minecraft54.engine.app;

import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.io.Keyboard;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11C.*;

public class AppCfg{

    private Keyboard keyboard;
    private Mouse mouse;
    private Window window;
    private AppScreen screen;
    private String currentScreen;
    public float FPS,DELTA_TIME;

    private int prevWidth,prevHeight;

    private final HashMap<String,AppScreen> screens;

    public AppCfg(){
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

    public void init(AppListener listener){
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

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            listener.update();
            SoundManager.update();
            if(screen!=null)
                screen.render();
            mouse.reset();
            keyboard.reset();
            window.update();

        }

        window.hide();
        for(String screenId:screens.keySet())
            screens.get(screenId).dispose();
        listener.dispose();
        window.destroy();
    }


    public void setScreen(String screenId){
        AppScreen scr=screens.get(screenId);
        screen=scr;
        scr.onSet();
        currentScreen=screenId;
    }

    public AppScreen getScreen(String id){
        return screens.get(id);
    }

    public void addScreen(String id,AppScreen screen){
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
