package minecraft54.engine.io;

import minecraft54.engine.graphics.Texture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

public class Window{

    private final long id;
    private int width,height,x,y;
    private String title;
    private boolean isVSync,isResizable,isFocused,isFullscreen;

    private int ow,oh,ox,oy;

    public Window(int width,int height,String title,boolean resizable,boolean vsync){
        this.title=title;
        this.isResizable=resizable;
        this.isVSync=vsync;
        this.width=width;
        this.height=height;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_SAMPLES,4);
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,resizable?1:0);

        id=glfwCreateWindow(width,height,title,0,0);
        if(id==0)
            System.err.println("Failed to create the GLFW Window");


        GLFWVidMode vidMode=GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        glfwSetWindowPos(id,vidMode.width()/2-width/2,vidMode.height()/2-height/2);

        glfwMakeContextCurrent(id);
        createCapabilities();
        glfwSwapInterval(vsync?1:0);

        { // Set Callbacks
            glfwSetWindowFocusCallback(id,(windowHandle,flag)->{
                isFocused=flag;
            });

            glfwSetWindowSizeCallback(id,(windowHandle,w,h)->{
                this.width=w;
                this.height=h;
            });

            glfwSetWindowPosCallback(id,(windowHandle,x,y)->{
                this.x=x;
                this.y=y;
            });
        }
    }

    public void setIcon(String filePath){
        Texture iconTexture=new Texture(filePath,true);
        GLFWImage iconImage=GLFWImage.malloc();
        GLFWImage.Buffer iconBuffer=GLFWImage.malloc(1);
        iconImage.set(iconTexture.getWidth(),iconTexture.getHeight(),iconTexture.getPixmap().getPixels());
        iconTexture.dispose();
        iconBuffer.put(0,iconImage);
        glfwSetWindowIcon(id,iconBuffer);
    }

    public void setCursor(Cursor cursor){
        glfwSetCursor(id,cursor.getId());
    }

    public void setFullscreen(boolean flag){
        if(flag!=isFullscreen){
            if(flag){
                ow=width;oh=height;
                ox=x;oy=y;
                long monitor=glfwGetPrimaryMonitor();
                GLFWVidMode mode=glfwGetVideoMode(monitor);
                glfwSetWindowMonitor(id,monitor,0,0,mode.width(),mode.height(),mode.refreshRate());
            }else{
                glfwSetWindowMonitor(id,0,ox,oy,ow,oh,getRefreshRate());
            }
            isFullscreen=flag;
        }
    }
    public void toggleFullscreen(){
        setFullscreen(!isFullscreen);
    }
    public boolean isFullscreen(){
        return isFullscreen;
    }

    /*public void setResizable(boolean resizable){
        isResizable=resizable;
        glfwWindowHint(GLFW_RESIZABLE,resizable?1:0);
    }*/

    public void setVSync(boolean vsync){
        this.isVSync=vsync;
        glfwSwapInterval(vsync?1:0);
    }

    public void setTitle(String title){
        this.title=title;
        glfwSetWindowTitle(id,title);
    }

    public void setSize(int width,int height){
        glfwSetWindowSize(id,width,height);
    }
    public void setPos(int x,int y){
        glfwSetWindowPos(id,x,y);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public float aspect(){
        return (float)width/height;
    }

    public void show(){
        glfwShowWindow(id);
    }
    public void hide(){
        glfwHideWindow(id);
    }

    public void destroy(){
        glfwDestroyWindow(id);
    }
    public boolean close(){
        return glfwWindowShouldClose(id);
    }
    public void update(){
        glfwSwapBuffers(id);
        glfwPollEvents();
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public boolean isResizable(){
        return isResizable;
    }

    public boolean isVSync(){
        return isVSync;
    }

    public boolean isFocused(){
        return isFocused;
    }



    public static GLFWVidMode getVidMode(){
        return glfwGetVideoMode(glfwGetPrimaryMonitor());
    }

    public static int getRefreshRate(){
        return glfwGetVideoMode(glfwGetPrimaryMonitor()).refreshRate();
    }

    public static int getMonitorWidth(){
        return glfwGetVideoMode(glfwGetPrimaryMonitor()).width();
    }

    public static int getMonitorHeight(){
        return glfwGetVideoMode(glfwGetPrimaryMonitor()).height();
    }


}