package minecraft54.engine.io;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard{

    private final long windowId;
    private final boolean[] pressed,down,released;

    public Keyboard(Window window){
        this.windowId=window.getId();
        pressed=new boolean[GLFW_KEY_LAST+1];
        down=new boolean[GLFW_KEY_LAST+1];
        released=new boolean[GLFW_KEY_LAST+1];

        glfwSetKeyCallback(windowId,(long windowHandle,int key,int scancode,int action,int mods)->{
            if(key!=-1)
                if(action==GLFW_PRESS){
                    down[key]=true;
                    pressed[key]=true;
                }else if(action==GLFW_RELEASE){
                    released[key]=true;
                    pressed[key]=false;
                }
        });
    }

    public void reset(){
        Arrays.fill(released,false);
        Arrays.fill(down,false);
    }

    public boolean isKeyDown(int key){
        return down[key];
    }

    public boolean isKeyPressed(int key){
        return pressed[key];
    }

    public boolean isKeyReleased(int key){
        return released[key];
    }

    public long getWindowId(){
        return windowId;
    }

}
