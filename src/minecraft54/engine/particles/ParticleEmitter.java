package minecraft54.engine.particles;

import minecraft54.engine.graphics.*;
import minecraft54.engine.maths.Matrix4;
import minecraft54.engine.maths.vectors.Vector3d;
import minecraft54.engine.maths.vectors.Vector3f;
import minecraft54.engine.util.Color;
import minecraft54.engine.util.FastArrayList;
import minecraft54.engine.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL46C.*;

public class ParticleEmitter{


    private final FastArrayList<Particle> particles;

    private final ShaderProgram shader;
    private final VertexBufferObject vbo;
    private final VertexArrayObject vao;
    private final ElementBufferObject ebo;

    private final int maxParticles;
    private int numSprites,numVertices;
    private final float[] vertices;
    private final int[] texSlots;
    private final List<Integer> textures;


    public ParticleEmitter(int maxParticles){
        this.maxParticles=maxParticles;

        particles=new FastArrayList<>();

        shader=new ShaderProgram(Utils.readFile("shaders/Particle.vert"),Utils.readFile("shaders/Particle.frag"));
        shader.addUniforms("u_textures","u_proj","u_world");

        vao=new VertexArrayObject();
        vbo=new VertexBufferObject();
        vbo.enableAttributes(new VertexAttribute(3,GL_FLOAT),new VertexAttribute(2,GL_FLOAT),new VertexAttribute(4,GL_FLOAT),new VertexAttribute(1,GL_FLOAT));

        ebo=new ElementBufferObject();

        int[] indices=new int[6*maxParticles];
        for(int i=0; i<maxParticles; i++){
            int offsetArrayIndex=6*i;
            int offset=4*i;

            indices[offsetArrayIndex]=offset+3;
            indices[offsetArrayIndex+1]=offset+2;
            indices[offsetArrayIndex+2]=offset;

            indices[offsetArrayIndex+3]=offset;
            indices[offsetArrayIndex+4]=offset+2;
            indices[offsetArrayIndex+5]=offset+1;
        }
        ebo.setData(indices,GL_STATIC_DRAW);


        texSlots=new int[192];
        for(int i=0; i<192; i++)
            texSlots[i]=i;

        vertices=new float[maxParticles*4*vbo.getVertexSize()];

        textures=new ArrayList<>();
    }


    public void render(PerspectiveCamera camera){
        render(camera.getProjection(),camera.getView());
    }

    public void render(Matrix4 projection,Matrix4 world){
        for(int i=0; i<particles.size(); i++){
            Particle particle=particles.get(i);

            if(numSprites+1>=maxParticles)
                break;
            numSprites++;

            Vector3d pos=particle.getPosition();
            float width=particle.getWidth();
            Color col=particle.getColor();
            TextureRegion reg=particle.getTexture();
            Texture texture=reg.getTexture();
            Vector3f nor=particle.getNormal();

            int textureId=0;
            if(texture!=null){
                textureId=texture.getId();
                if(!textures.contains(textureId))
                    textures.add(textureId);

                for(int j=0; j<textures.size(); j++)
                    if(textureId==textures.get(j)){
                        textureId=j+1;
                        break;
                    }
            }

            addVertex( width/2, width/2,0,reg.getU2(),reg.getV() ,col.red(),col.green(),col.blue(),col.alpha(),textureId);
            addVertex( width/2,-width/2,0,reg.getU2(),reg.getV2(),col.red(),col.green(),col.blue(),col.alpha(),textureId);
            addVertex(-width/2,-width/2,0,reg.getU() ,reg.getV2(),col.red(),col.green(),col.blue(),col.alpha(),textureId);
            addVertex(-width/2, width/2,0,reg.getU() ,reg.getV() ,col.red(),col.green(),col.blue(),col.alpha(),textureId);
        }

        int cullFace=glGetInteger(GL_CULL_FACE);
        glDisable(GL_CULL_FACE);

        vbo.setData(vertices,GL_STREAM_DRAW);

        shader.bind();
        shader.setUniform("u_proj",projection);
        shader.setUniform("u_world",world);

        for(int i=0; i<textures.size(); i++){
            glActiveTexture(GL_TEXTURE0+i+1);
            glBindTexture(GL_TEXTURE_2D,textures.get(i));
        }

        shader.setUniform("u_textures",texSlots);

        vao.drawElements(numSprites*6);

        textures.clear();

        numSprites=0;
        numVertices=0;

        if(cullFace==1) glEnable(GL_CULL_FACE);
    }

    private void addVertex(double x,double y,double z,float tx,float ty,float r,float g,float b,float a,int id){
        int offset=numVertices*vbo.getVertexSize();
        numVertices++;

        vertices[offset]=(float)x;
        vertices[offset+1]=(float)y;
        vertices[offset+2]=(float)z;

        vertices[offset+3]=tx;
        vertices[offset+4]=ty;

        vertices[offset+5]=r;
        vertices[offset+6]=g;
        vertices[offset+7]=b;
        vertices[offset+8]=a;

        vertices[offset+9]=id;
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
        if(particles.size()>=maxParticles)
            return;
        particles.add(particle);
        particle.resetSpawnTime();
    }

    public void dispose(){
        particles.clear();
        shader.dispose();
        vbo.dispose();
        vao.dispose();
        ebo.dispose();
    }


}
