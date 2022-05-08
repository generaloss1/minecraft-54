package engine54.graphics.vertices;

import engine54.util.FastArrayList;

import static org.lwjgl.opengl.GL33.*;

public class Mesh{

    private int mode=GL_TRIANGLES,vertexCount;
    private float[] vertices;
    private final VertexAttribute[] attributes;
    private final VertexBufferObject vbo;
    private final VertexArrayObject vao;

    public Mesh(VertexAttribute... attributes){
        this.attributes=attributes;
        vertices=new float[0];

        vao=new VertexArrayObject();
        vbo=new VertexBufferObject();
        vbo.enableAttributes(attributes);
    }

    public Mesh(Mesh mesh){
        attributes=mesh.attributes;
        vertices=mesh.vertices.clone();
        mode=mesh.mode;
        vertexCount=mesh.vertexCount;

        vao=new VertexArrayObject();
        vbo=new VertexBufferObject();
        vbo.enableAttributes(attributes);
        vbo.setData(vertices,GL_DYNAMIC_DRAW);
    }

    public void render(){
        vao.drawArrays(mode,vertexCount);
    }

    public void dispose(){
        vbo.dispose();
        vao.dispose();
    }

    public void setRenderMode(int mode){
        this.mode=mode;
    }

    public void setVertices(float[] vertices){
        this.vertices=vertices.clone();
        vertexCount=vertices.length/vbo.getVertexSize();
        vbo.setData(vertices,GL_DYNAMIC_DRAW);
    }

    public boolean setVertices(FastArrayList<Float> verticesList){
        vertices=new float[verticesList.size()];
        for(int i=0; i<vertices.length; i++){
            Float v=verticesList.get(i);
            if(v==null)
                return false;
            vertices[i]=v;
        }
        vertexCount=vertices.length/vbo.getVertexSize();

        vbo.setData(vertices,GL_DYNAMIC_DRAW);
        return true;
    }

}
