package minecraft54.main;

import minecraft54.engine.app.AppCfg;
import minecraft54.engine.io.Keyboard;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import minecraft54.engine.utils.GLUtils;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46C.*;

public class Main{

    public static AppCfg cfg;

    public static Window window;
    public static Keyboard keyboard;
    public static Mouse mouse;

    public static void main(String[] args){
        GLUtils.initGLFW();
        GLUtils.initAL();

        cfg=new AppCfg();

        window=new Window(925,530,"Minecraft 54",true,true);
        keyboard=new Keyboard(window);
        mouse=new Mouse(window);

        window.setIcon("icon.png");

        cfg.setWindow(window);
        cfg.setKeyboard(keyboard);
        cfg.setMouse(mouse);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        glEnable(GL_CULL_FACE);

        glEnable(GL_TEXTURE);

        glEnable(GL_MULTISAMPLE);
        glfwWindowHint(GLFW.GLFW_SAMPLES,1);

        cfg.init(new Minecraft54());
    }

    public static float getFPS(){
        return cfg.FPS;
    }

    public static float getDeltaTime(){
        return cfg.DELTA_TIME;
    }

}
