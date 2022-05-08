package engine54;

import engine54.app.*;
import engine54.io.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.*;

import static org.lwjgl.openal.ALC11.*;

public class E54{

    private static final Context context=new Context();

    public static void init(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!org.lwjgl.glfw.GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        String defaultDeviceName=alcGetString(0,ALC_DEFAULT_DEVICE_SPECIFIER);
        long device=alcOpenDevice(defaultDeviceName);

        int[] attributes={0};
        long context=alcCreateContext(device,attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities=ALC.createCapabilities(device);
        AL.createCapabilities(alcCapabilities);
    }

    public static void setIO(Window window){
        context.setWindow(window);
        context.setKeyboard(new Keyboard(window));
        context.setMouse(new Mouse(window));
    }

    public static Context context(){
        return context;
    }

    public static Window window(){
        return context.getWindow();
    }

    public static Keyboard keyboard(){
        return context.getKeyboard();
    }

    public static Mouse mouse(){
        return context.getMouse();
    }

    public static void setScreen(String screenID){
        context.setScreen(screenID);
    }

    public static void defineScreen(Screen screen,String id){
        context.defineScreen(screen,id);
    }

    public static int getWinWidth(){
        return context.getWindow().getWidth();
    }

    public static int getWinHeight(){
        return context.getWindow().getHeight();
    }

    public static float getFps(){
        return context.getFPS();
    }

    public static float getDeltaTime(){
        return context.getDeltaTime();
    }

}
