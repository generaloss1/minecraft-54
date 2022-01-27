package djankcraft.minecraft54;

import djankcraft.engine.app.AppCfg;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;
import djankcraft.engine.utils.GLUtils;
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
        cfg=new AppCfg();

        window=new Window(1280,720,"LwjglTest",true,true);
        keyboard=new Keyboard(window);
        mouse=new Mouse(window);

        window.setIcon("icon.png");
        mouse.hide();

        cfg.setWindow(window);
        cfg.setKeyboard(keyboard);
        cfg.setMouse(mouse);

        glEnable(GL_DEPTH_TEST);

        glEnable(GL_CULL_FACE);

        glEnable(GL_TEXTURE);

        glEnable(GL_MULTISAMPLE);
        glfwWindowHint(GLFW.GLFW_SAMPLES,4);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

        cfg.init(new Minecraft54());
    }

    public static float getFPS(){
        return cfg.FPS;
    }

    public static float getDeltaTime(){
        return cfg.DELTA_TIME;
    }

}
