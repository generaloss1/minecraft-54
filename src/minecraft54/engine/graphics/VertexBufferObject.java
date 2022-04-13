package minecraft54.engine.graphics;

import minecraft54.engine.util.GLUtils;
import static org.lwjgl.opengl.GL46C.*;

public class VertexBufferObject{

    private final int id;
    private int vertexSize;

    public VertexBufferObject(){
        id=glGenBuffers();
        bind();
    }

    public void enableAttributes(VertexAttribute... attributes){
        //bind();
        for(VertexAttribute attribute: attributes)
            vertexSize+=attribute.getCount();

        int pointer=0;
        for(byte i=0; i<attributes.length; i++){
            VertexAttribute attribute=attributes[i];
            int typeSize=GLUtils.getGLTypeSize(attribute.getType());

            glVertexAttribPointer(i,attribute.getCount(),attribute.getType(),attribute.isNormalize(),vertexSize*typeSize,pointer);
            glEnableVertexAttribArray(i);

            pointer+=attribute.getCount()*typeSize;
        }
    }

    public int getVertexSize(){
        return vertexSize;
    }

    public void setData(long size,int usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER,size,usage);
    }
    public void setData(float[] data,int usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER,data,usage);
    }
    public void setData(double[] data,int usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER,data,usage);
    }
    public void setData(int[] data,int usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER,data,usage);
    }
    public void setData(short[] data,int usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER,data,usage);
    }
    public void setData(long[] data,int usage){
        bind();
        glBufferData(GL_ARRAY_BUFFER,data,usage);
    }

    public void setSubData(long offset,float[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,double[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,int[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,short[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER,offset,data);
    }
    public void setSubData(long offset,long[] data){
        bind();
        glBufferSubData(GL_ARRAY_BUFFER,offset,data);
    }

    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER,id);
    }

    public static void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    public int getId(){
        return id;
    }

    public void dispose(){
        glDeleteBuffers(id);
    }

}