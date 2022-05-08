package engine54.io;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse{

    private final long windowId;
    private boolean inWindow,isVisible;
    private int scroll;
    private final boolean[] down,pressed,released;

    public Mouse(Window window){
        this.windowId=window.getId();
        isVisible=true;

        down=new boolean[GLFW_MOUSE_BUTTON_LAST+1];
        pressed=new boolean[GLFW_MOUSE_BUTTON_LAST+1];
        released=new boolean[GLFW_MOUSE_BUTTON_LAST+1];

        glfwSetCursorEnterCallback(windowId,(windowHandle,entered)->{
            inWindow=entered;
        });

        glfwSetScrollCallback(windowId,(windowHandle,x,y)->{
            scroll+=y;
        });

        glfwSetMouseButtonCallback(windowId,(windowHandle,button,action,mode)->{
            if(action==GLFW_PRESS){
                down[button]=true;
                pressed[button]=true;
            }else if(action==GLFW_RELEASE){
                released[button]=true;
                pressed[button]=false;
            }
        });
    }

    public void reset(){
        scroll=0;
        Arrays.fill(released,false);
        Arrays.fill(down,false);
    }

    public void show(boolean show){
        if(show==isVisible)
            return;
        glfwSetInputMode(windowId,GLFW_CURSOR,show?GLFW_CURSOR_NORMAL:GLFW_CURSOR_HIDDEN);
        isVisible=show;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void setPos(int x,int y){
        glfwSetCursorPos(windowId,x,y);
    }

    public void setPosCenter(Window window){
        glfwSetCursorPos(windowId,window.getWidth()/2f,window.getHeight()/2f);
    }

    public int getScroll(){
        return scroll;
    }

    public long getWindowId(){
        return windowId;
    }

    public boolean inWindow(){
        return inWindow;
    }

    public int[] getPos(){
        double[] x=new double[1];
        double[] y=new double[1];
        glfwGetCursorPos(windowId,x,y);
        return new int[]{(int)x[0],(int)y[0]};
    }
    public int getX(){
        double[] x=new double[1];
        glfwGetCursorPos(windowId,x,null);
        return (int)x[0];
    }
    public int getY(){
        double[] y=new double[1];
        glfwGetCursorPos(windowId,null,y);
        return (int)y[0];
    }

    public void setGrabbed(){

    }

    public boolean isLeftDown(){
        return down[0];
    }
    public boolean isLeftPressed(){
        return pressed[0];
    }
    public boolean isLeftReleased(){
        return released[0];
    }

    public boolean isRightDown(){
        return down[1];
    }
    public boolean isRightPressed(){
        return pressed[1];
    }
    public boolean isRightReleased(){
        return released[1];
    }

    public boolean isButtonDown(int button){
        return down[button];
    }
    public boolean isButtonPressed(int button){
        return pressed[button];
    }
    public boolean isButtonReleased(int button){
        return released[button];
    }


    public int getScrollX(Keyboard keyboard){
        if(keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) || keyboard.isKeyPressed(GLFW_KEY_RIGHT_SHIFT))
            return scroll;
        else
            return 0;
    }

    public int getScrollY(Keyboard keyboard){
        if(keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) || keyboard.isKeyPressed(GLFW_KEY_RIGHT_SHIFT))
            return 0;
        else
            return scroll;
    }

}
