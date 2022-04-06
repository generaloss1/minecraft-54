package minecraft54.engine.graphics;

import static org.lwjgl.opengl.GL46C.*;

public class VertexArrayObject{

    private final int id;

    public VertexArrayObject(){
        id=glGenVertexArrays();
        bind();
    }

    public void draw(int mode,int vertexCount){
        bind();
        glDrawArrays(mode,0,vertexCount);
    }

    public void draw(int vertexCount){
        bind();
        glDrawArrays(GL_TRIANGLES,0,vertexCount);
    }

    public void drawElements(int indicesCount){
        bind();
        glDrawElements(GL_TRIANGLES,indicesCount,GL_UNSIGNED_INT,0);
    }

    public void drawElements(int mode,int indicesCount){
        bind();
        glDrawElements(mode,indicesCount,GL_UNSIGNED_INT,0);
    }

    public int getId(){
        return id;
    }

    public void bind(){
        glBindVertexArray(id);
    }

    public static void unbind(){
        glBindVertexArray(0);
    }

    public void dispose(){
        glDeleteVertexArrays(id);
    }

}