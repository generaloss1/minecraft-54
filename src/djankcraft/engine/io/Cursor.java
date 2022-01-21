package djankcraft.engine.io;

import djankcraft.engine.graphics.PixmapRGBA;
import djankcraft.engine.graphics.Texture;
import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class Cursor{

    private final long id;

    public Cursor(String filePath){
        PixmapRGBA cursorTextureData=new PixmapRGBA(filePath);
        GLFWImage cursorBuffer=GLFWImage.malloc();
        cursorBuffer.set(cursorTextureData.getWidth(),cursorTextureData.getHeight(),cursorTextureData.getPixels());
        id=glfwCreateCursor(cursorBuffer,0,0);
    }

    public Cursor(Texture cursorTexture){
        GLFWImage cursorBuffer=GLFWImage.malloc();
        cursorBuffer.set(cursorTexture.getWidth(),cursorTexture.getHeight(),cursorTexture.getPixmap().getPixels());
        id=glfwCreateCursor(cursorBuffer,0,0);
    }

    public Cursor(PixmapRGBA cursorTexture){
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
