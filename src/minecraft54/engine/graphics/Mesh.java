package minecraft54.engine.graphics;

import minecraft54.engine.utils.FastArrayList;
import minecraft54.engine.utils.GLUtils;

import java.util.List;

import static org.lwjgl.opengl.GL46C.*;

public class Mesh{

    private int mode,vertexSize,vertexCount;
    private final int vao,vbo;
    private float[] vertices;
    private final VertexAttribute[] attributes;

    public Mesh(VertexAttribute... attributes){
        this.attributes=attributes;
        vertices=new float[0];
        mode=GL_TRIANGLES;

        vao=glGenVertexArrays();
        glBindVertexArray(vao);

        vbo=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vbo);

        for(int i=0; i<attributes.length; i++)
            vertexSize+=attributes[i].getCount();

        int point=0;
        for(int i=0; i<attributes.length; i++){
            VertexAttribute attribute=attributes[i];
            glEnableVertexAttribArray(i);
            int typeSize=GLUtils.getGLTypeSize(attribute.getType());
            glVertexAttribPointer(i,attribute.getCount(),attribute.getType(),false,vertexSize*typeSize,point);
            point+=attribute.getCount()*typeSize;
        }

        glBindVertexArray(0);
    }

    public Mesh(Mesh mesh){
        attributes=mesh.attributes;
        vertices=mesh.vertices.clone();
        mode=mesh.mode;
        vertexCount=mesh.vertexCount;

        vao=glGenVertexArrays();
        glBindVertexArray(vao);

        vbo=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,vertices,GL_STATIC_DRAW);

        for(int i=0; i<mesh.attributes.length; i++)
            vertexSize+=attributes[i].getCount();

        int point=0;
        for(int i=0; i<attributes.length; i++){
            VertexAttribute attribute=attributes[i];
            glEnableVertexAttribArray(i);
            int typeSize=GLUtils.getGLTypeSize(attribute.getType());
            glVertexAttribPointer(i,attribute.getCount(),attribute.getType(),false,vertexSize*typeSize,point);
            point+=attribute.getCount()*typeSize;
        }

        glBindVertexArray(0);
    }

    /*public void createEBO(){
        ebo=glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,indices,GL_STATIC_DRAW);
    }*/

    public void render(){
        glBindVertexArray(vao);
        glDrawArrays(mode,0,vertexCount);
        glBindVertexArray(0);
    }

    public void dispose(){
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
    }

    public void setRenderMode(int mode){
        this.mode=mode;
    }

    public void setVertices(float[] vertices){
        this.vertices=vertices.clone();

        vertexCount=vertices.length/vertexSize;
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,vertices,GL_STATIC_DRAW);
        glBindVertexArray(0);
    }

    public void setVertices(FastArrayList<Float> verticesList){
        vertices=new float[verticesList.size()];
        for(int i=0; i<vertices.length; i++)
            vertices[i]=verticesList.get(i);

        vertexCount=vertices.length/vertexSize;
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,vertices,GL_STATIC_DRAW);
        glBindVertexArray(0);
    }

    public float[] getVertices(){
        return vertices;
    }

    public int getVertexSize(){
        return vertexSize;
    }



}
