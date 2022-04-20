package minecraft54.test;

import minecraft54.engine.app.AppListener;

import static org.lwjgl.glfw.GLFW.*;

public class TestApp implements AppListener{

    @Override
    public void create(){
        Main.cfg.addScreen("screen1",new TestScreen1());
        Main.cfg.addScreen("screen2",new TestScreen2());
        Main.cfg.addScreen("screen3",new TestScreen3());

        Main.cfg.setScreen("screen3");
    }

    @Override
    public void update(){
        if(Main.keyboard.isKeyDown(GLFW_KEY_F11))
            Main.window.setFullscreen(!Main.window.isFullscreen());
        if(Main.keyboard.isKeyDown(GLFW_KEY_V))
            Main.window.setVSync(!Main.window.isVSync());
        if(Main.keyboard.isKeyDown(GLFW_KEY_ESCAPE))
            System.exit(0);
        Main.window.setTitle("Test 54; Fps: "+Main.cfg.FPS);
    }

    @Override
    public void dispose(){

    }

}
