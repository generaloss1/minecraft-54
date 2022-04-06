package minecraft54.engine.util;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.*;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL30C.*;

public class GLUtils{

    public static void initGLFW(){
        GLFWErrorCallback.createPrint(System.err).set();
        if(!org.lwjgl.glfw.GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
    }

    public static void initAL(){
        String defaultDeviceName=alcGetString(0,ALC_DEFAULT_DEVICE_SPECIFIER);
        long device=alcOpenDevice(defaultDeviceName);

        int[] attributes={0};
        long context=alcCreateContext(device,attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities=ALC.createCapabilities(device);
        AL.createCapabilities(alcCapabilities);
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
