package engine54.particles;

import engine54.graphics.*;
import engine54.graphics.camera.Camera;
import engine54.graphics.texture.TextureRegion;
import engine54.graphics.vertices.VertexArrayObject;
import engine54.graphics.vertices.VertexAttribute;
import engine54.graphics.vertices.VertexBufferObject;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector3d;
import engine54.util.Color;
import engine54.util.FastArrayList;
import engine54.util.Utils;

import static org.lwjgl.opengl.GL33.*;

public class BillboardParticleEmitter{



    private final FastArrayList<Particle> particles;

    private final ShaderProgram shader;
    private final VertexBufferObject vbo;
    private final VertexArrayObject vao;

    private final float[] vertices;

    public BillboardParticleEmitter(){
        particles=new FastArrayList<>();

        shader=new ShaderProgram(Utils.readFile("shaders/BillboardParticle.v"),Utils.readFile("shaders/BillboardParticle.f"));
        shader.addUniforms("u_texture","u_proj","u_modelView");

        vao=new VertexArrayObject();
        vbo=new VertexBufferObject();
        vbo.enableAttributes(new VertexAttribute(2,GL_FLOAT),new VertexAttribute(2,GL_FLOAT),new VertexAttribute(4,GL_FLOAT));

        vertices=new float[vbo.getVertexSize()*6];
    }


    public void render(Camera camera){
        render(camera.getProjection(),camera.getView());
    }

    public void render(Matrix4f projection,Matrix4f view){
        int cullFace=glGetInteger(GL_CULL_FACE);
        glDisable(GL_CULL_FACE);

        shader.bind();
        for(int i=0; i<particles.size(); i++){
            Particle particle=particles.get(i);

            Vector3d pos=particle.getPosition();
            float width=particle.getWidth()/2;
            float height=width;
            Color col=particle.getColor();
            TextureRegion reg=particle.getTexture();

            //setVertex(2,vertices,+width,+height,reg.getU2(),reg.getV(),col.red(),col.green(),col.blue(),col.alpha());
            //setVertex(1,vertices,+width,-height,reg.getU2(),reg.getV2(),col.red(),col.green(),col.blue(),col.alpha());
            //setVertex(5,vertices,-width,-height,reg.getU(),reg.getV2(),col.red(),col.green(),col.blue(),col.alpha());
            //setVertex(4,vertices,-width,+height,reg.getU(),reg.getV(),col.red(),col.green(),col.blue(),col.alpha());

            setVertex(0,vertices,2-width,-height,reg.getU(),reg.getV2(),col.red(),col.green(),col.blue(),col.alpha());
            setVertex(1,vertices,2+width,-height,reg.getU2(),reg.getV2(),col.red(),col.green(),col.blue(),col.alpha());
            setVertex(2,vertices,2+width,+height,reg.getU2(),reg.getV(),col.red(),col.green(),col.blue(),col.alpha());
            setVertex(3,vertices,2+width,+height,reg.getU2(),reg.getV(),col.red(),col.green(),col.blue(),col.alpha());
            setVertex(4,vertices,2-width,+height,reg.getU(),reg.getV(),col.red(),col.green(),col.blue(),col.alpha());
            setVertex(5,vertices,2-width,-height,reg.getU(),reg.getV2(),col.red(),col.green(),col.blue(),col.alpha());
            vbo.setData(vertices,GL_STREAM_DRAW);

            //Matrix4 model=new Matrix4().translate(pos);
            //Matrix4 modelView=Matrix4.mul(view,model);

            shader.setUniform("u_proj",projection);
            shader.setUniform("u_modelView",view);
            shader.setUniform("u_texture",reg.getTexture().getId());
            vao.drawArrays(vertices.length);

        }
        if(cullFace==0)
            glDisable(GL_CULL_FACE);
    }

    private void setVertex(int index,float[] vertices,double x,double y,float tx,float ty,float r,float g,float b,float a){
        int offset=index*vbo.getVertexSize();

        vertices[offset]=(float)x;
        vertices[offset+1]=(float)y;

        vertices[offset+1]=tx;
        vertices[offset+2]=ty;

        vertices[offset+3]=r;
        vertices[offset+4]=g;
        vertices[offset+5]=b;
        vertices[offset+6]=a;
    }


    public FastArrayList<Particle> getParticles(){
        return particles;
    }

    public void update(){
        for(int i=0; i<particles.size(); i++){
            Particle particle=particles.get(i);
            if(particle.getLiveTimeMillis()>particle.getLiveTime()){
                particles.remove(i);
                continue;
            }
            particle.updateBehavior();
        }
    }

    public void addParticle(Particle particle){
        particles.add(particle);
        particle.resetSpawnTime();
    }

    public void dispose(){
        particles.clear();
        shader.dispose();
        vbo.dispose();
        vao.dispose();
    }



}
