package engine54.util;

import static org.lwjgl.opengl.GL33.*;

public class GLUtils{

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
