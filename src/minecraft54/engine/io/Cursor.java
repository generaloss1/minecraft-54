package minecraft54.engine.io;

import minecraft54.engine.graphics.Pixmap;
import minecraft54.engine.graphics.Texture;
import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class Cursor{

    private final long id;

    public Cursor(String filePath){
        Pixmap cursorTextureData=new Pixmap(filePath);
        GLFWImage cursorBuffer=GLFWImage.malloc();
        cursorBuffer.set(cursorTextureData.getWidth(),cursorTextureData.getHeight(),cursorTextureData.getPixels());
        id=glfwCreateCursor(cursorBuffer,0,0);
    }

    public Cursor(Texture cursorTexture){
        GLFWImage cursorBuffer=GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(),cursorTexture.getHeight(),cursorTexture.getPixmap().getPixels());
        id=glfwCreateCursor(cursorBuffer,0,0);
    }

    public Cursor(Pixmap cursorTexture){
        GLFWImage cursorBuffer=GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(),cursorTexture.getHeight(),cursorTexture.getPixels());
        id=glfwCreateCursor(cursorBuffer,0,0);
    }

    public long getId(){
        return id;
    }

    public void dispose(){
        glfwDestroyCursor(id);
    }

}
