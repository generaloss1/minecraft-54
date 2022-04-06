package minecraft54.engine.graphics;

import minecraft54.engine.maths.Matrix4;
import minecraft54.engine.util.Color;
import minecraft54.engine.util.Utils;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL46C.*;
import static org.lwjgl.stb.STBTruetype.stbtt_GetBakedQuad;
import static org.lwjgl.system.MemoryStack.stackPush;

public class SpriteBatch{


    private final ShaderProgram shader;
    private final Color color;
    private final VertexBufferObject vbo;
    private final VertexArrayObject vao;
    private final ElementBufferObject ebo;

    private final int maxBatchSize;
    private int numSprites,numVertices;
    private final float[] vertices;
    private final int[] texSlots;
    private final List<Integer> textures;


    public SpriteBatch(){
        this(10000);
    }

    public SpriteBatch(int maxBatchSize){
        this.maxBatchSize=maxBatchSize;

        shader=new ShaderProgram(Utils.readFile("shaders/SpriteBatch.vert"),Utils.readFile("shaders/SpriteBatch.frag"));
        shader.addUniforms("u_textures","u_proj","u_view");

        color=new Color();

        { // Create VAO
            vao=new VertexArrayObject();

            vbo=new VertexBufferObject();
            vbo.enableAttributes(new VertexAttribute(2,GL_FLOAT),new VertexAttribute(2,GL_FLOAT),new VertexAttribute(4,GL_FLOAT),new VertexAttribute(1,GL_FLOAT));

            ebo=new ElementBufferObject();

            int[] indices=new int[6*maxBatchSize];
            for(int i=0; i<maxBatchSize; i++){
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
        }

        texSlots=new int[192];
        for(int i=0; i<192; i++)
            texSlots[i]=i;

        vertices=new float[maxBatchSize*4*vbo.getVertexSize()];

        textures=new ArrayList<>();
    }


    public void drawText(TrueTypeFont font,String text,int sx,int sy){
        if(font==null)
            return;

        Texture texture=font.getTexture();
        int textureId;
        if(texture!=null){
            textureId=texture.getId();
            if(!textures.contains(textureId))
                textures.add(textureId);

            for(int i=0; i<textures.size(); i++)
                if(textureId==textures.get(i)){
                    textureId=i+1;
                    break;
                }
        }else
            return;

        try(MemoryStack stack=stackPush()){
            FloatBuffer x=stack.floats(sx);
            FloatBuffer y=stack.floats(sy);

            STBTTAlignedQuad q=STBTTAlignedQuad.malloc(stack);

            for(int i=0; i<text.length(); i++){

                if(numSprites+1>=maxBatchSize)
                    return;

                char c=text.charAt(i);
                if(c=='\n'){
                    y.put(0,y.get(0)+font.getFontHeight());
                    x.put(0,sx);
                    continue;
                }else if(c<32 || 128<=c)
                    continue;

                stbtt_GetBakedQuad(font.getCharData(),texture.getWidth(),texture.getHeight(),c-32,x,y,q,false);

                addVertex(q.x1(),sy*2-q.y0(),q.s1(),q.t0(),textureId);
                addVertex(q.x1(),sy*2-q.y1(),q.s1(),q.t1(),textureId);
                addVertex(q.x0(),sy*2-q.y1(),q.s0(),q.t1(),textureId);
                addVertex(q.x0(),sy*2-q.y0(),q.s0(),q.t0(),textureId);

                numSprites++;
            }
        }
    }

    public void draw(Texture texture,float x,float y,float width,float height){
        if(numSprites+1>=maxBatchSize)
            return;
        numSprites++;

        int textureId=0;
        if(texture!=null){
            textureId=texture.getId();
            if(!textures.contains(textureId))
                textures.add(textureId);

            for(int i=0; i<textures.size(); i++)
                if(textureId==textures.get(i)){
                    textureId=i+1;
                    break;
                }
        }

        addVertex(
                x+width,y+height,
                1,0,
                textureId
        );
        addVertex(
                x+width,y,
                1,1,
                textureId
        );
        addVertex(
                x,y,
                0,1,
                textureId
        );
        addVertex(
                x,y+height,
                0,0,
                textureId
        );
    }

    public void draw(TextureRegion textureRegion,float x,float y,float width,float height){
        if(numSprites+1>=maxBatchSize)
            return;
        numSprites++;

        Texture texture=textureRegion.getTexture();

        int textureId=0;
        if(texture!=null){
            textureId=texture.getId();
            if(!textures.contains(textureId))
                textures.add(textureId);

            for(int i=0; i<textures.size(); i++)
                if(textureId==textures.get(i)){
                    textureId=i+1;
                    break;
                }
        }

        float u=textureRegion.getU();
        float v=textureRegion.getV();
        float u2=textureRegion.getU2();
        float v2=textureRegion.getV2();

        addVertex(
                x+width,y+height,
                u2,v,
                textureId
        );
        addVertex(
                x+width,y,
                u2,v2,
                textureId
        );
        addVertex(
                x,y,
                u,v2,
                textureId
        );
        addVertex(
                x,y+height,
                u,v,
                textureId
        );
    }

    public void render(OrthographicCamera cam){
        render(cam.getProjection(),cam.getView());
    }

    public void render(Matrix4 projection,Matrix4 view){
        int depthTest=glGetInteger(GL_DEPTH_TEST);
        int cullFace=glGetInteger(GL_CULL_FACE);

        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);

        vbo.setData(vertices,GL_STREAM_DRAW);

        shader.bind();
        shader.setUniform("u_proj",projection);
        shader.setUniform("u_view",view);

        for(int i=0; i<textures.size(); i++){
            glActiveTexture(GL_TEXTURE0+i+1);
            glBindTexture(GL_TEXTURE_2D,textures.get(i));
        }

        shader.setUniform("u_textures",texSlots);

        vao.drawElements(numSprites*6);

        textures.clear();

        numSprites=0;
        numVertices=0;

        if(depthTest==1) glEnable(GL_DEPTH_TEST);
        if(cullFace==1) glEnable(GL_CULL_FACE);
    }

    private void addVertex(float x,float y,float tx,float ty,int id){
        int offset=numVertices*vbo.getVertexSize();
        numVertices++;

        vertices[offset]=x;
        vertices[offset+1]=y;

        vertices[offset+2]=tx;
        vertices[offset+3]=ty;

        vertices[offset+4]=color.red();
        vertices[offset+5]=color.green();
        vertices[offset+6]=color.blue();
        vertices[offset+7]=color.alpha();

        vertices[offset+8]=id;
    }

    public void resetColor(){
        color.set(1,1,1,1);
    }
    public void setColor(Color color){
        this.color.set(color);
    }
    public void setColor(float r,float g,float b,float a){
        this.color.set(r,g,b,a);
    }
    public Color getColor(){
        return color;
    }
    public void setAlpha(float a){
        this.color.setAlpha(a);
    }

    public void dispose(){
        shader.dispose();
        vbo.dispose();
        vao.dispose();
        ebo.dispose();
    }


    public boolean hasRoom(){
        return numSprites<maxBatchSize;
    }

}