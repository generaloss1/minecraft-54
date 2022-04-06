package minecraft54.engine.graphics;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL15C.glDeleteBuffers;

public class ElementBufferObject{

    private final int id;

    public ElementBufferObject(){
        id=glGenBuffers();
        bind();
    }

    public void setData(long size,int usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,size,usage);
    }
    public void setData(float[] data,int usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,data,usage);
    }
    public void setData(double[] data,int usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,data,usage);
    }
    public void setData(int[] data,int usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,data,usage);
    }
    public void setData(short[] data,int usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,data,usage);
    }
    public void setData(long[] data,int usage){
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,data,usage);
    }

    public void setSubData(long offset,float[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,double[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,int[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,short[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,long[] data){
        bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER,offset,data);
    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,id);
    }

    public static void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }

    public int getId(){
        return id;
    }

    public void dispose(){
        glDeleteBuffers(id);
    }

}