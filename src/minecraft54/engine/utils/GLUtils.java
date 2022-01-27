package minecraft54.engine.utils;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_DOUBLE;
import static org.lwjgl.opengl.GL20C.GL_BOOL;
import static org.lwjgl.opengl.GL30C.GL_HALF_FLOAT;

public class GLUtils{

    public static void initGLFW(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!org.lwjgl.glfw.GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
    }

    public static int getGLTypeSize(int type) throws IllegalArgumentException{
        return switch(type){
            case GL_FLOAT, GL_INT, GL_UNSIGNED_INT -> 4;
            case GL_HALF_FLOAT, GL_SHORT, GL_UNSIGNED_SHORT -> 2;
            case GL_BOOL, GL_BYTE, GL_UNSIGNED_BYTE -> 1;
            case GL_DOUBLE -> 8;
            default -> throw new IllegalArgumentException();
        };
    }

}
