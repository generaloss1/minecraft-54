package djankcraft.engine.graphics;

import djankcraft.engine.math.Matrix4;
import djankcraft.engine.utils.Color;
import djankcraft.engine.utils.Utils;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.ARBVertexArrayObject.*;
import static org.lwjgl.opengl.GL11C.glBindTexture;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13C.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.stb.STBTruetype.stbtt_GetBakedQuad;
import static org.lwjgl.system.MemoryStack.stackPush;

public class SpriteBatch{

    private final int POS_SIZE=2;
    private final int COLOR_SIZE=4;
    private final int TEX_COORDS_SIZE=2;
    private final int TEX_ID_SIZE=1;

    private final int POS_OFFSET=0;
    private final int COLOR_OFFSET=POS_OFFSET+POS_SIZE*Float.BYTES;
    private final int TEX_COORDS_OFFSET=COLOR_OFFSET+COLOR_SIZE*Float.BYTES;
    private final int TEX_ID_OFFSET=TEX_COORDS_OFFSET+TEX_COORDS_SIZE*Float.BYTES;
    private final int VERTEX_SIZE=2+4+2+1;
    private final int VERTEX_SIZE_BYTES=VERTEX_SIZE*Float.BYTES;

    private int numSprites,numVertices;
    private final float[] vertices;
    private int[] texSlots;
    private Color color;

    private List<Integer> textures;
    private int vaoID,vboID;
    private int maxBatchSize,maxTexSlots;
    private ShaderProgram shader;

    public SpriteBatch(){
        this(10000);
    }

    public SpriteBatch(int maxBatchSize){
        maxTexSlots=192;

        shader=new ShaderProgram(Utils.readFile("shaders/SpriteBatch.vert"),Utils.readFile("shaders/SpriteBatch.frag"));
        shader.addUniforms("u_textures","u_proj","u_view");

        color=new Color();

        this.maxBatchSize=maxBatchSize;

        texSlots=new int[maxTexSlots];
        for(int i=0; i<maxTexSlots; i++)
            texSlots[i]=i;

        // 4 vertices quads
        vertices=new float[maxBatchSize*4*VERTEX_SIZE];

        this.numSprites=0;
        this.textures=new ArrayList<>();

        // Generate and bind a Vertex Array Object
        vaoID=glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        glBufferData(GL_ARRAY_BUFFER,(long)vertices.length*Float.BYTES,GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID=glGenBuffers();
        // 6 indices per quad (3 per triangle)
        int[] indices=new int[6*maxBatchSize];
        for(int i=0; i<maxBatchSize; i++){
            int offsetArrayIndex=6*i;
            int offset=4*i;

            // 3, 2, 0, 0, 2, 1        7, 6, 4, 4, 6, 5

            // Triangle 1
            indices[offsetArrayIndex]=offset+3;
            indices[offsetArrayIndex+1]=offset+2;
            indices[offsetArrayIndex+2]=offset+0;
            // Triangle 2
            indices[offsetArrayIndex+3]=offset+0;
            indices[offsetArrayIndex+4]=offset+2;
            indices[offsetArrayIndex+5]=offset+1;
        }
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,indices,GL_STATIC_DRAW);

        // Enable the buffer attribute pointers
        glVertexAttribPointer(0,POS_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,POS_OFFSET);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1,TEX_COORDS_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,TEX_COORDS_OFFSET);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2,COLOR_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,COLOR_OFFSET);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3,TEX_ID_SIZE,GL_FLOAT,false,VERTEX_SIZE_BYTES,TEX_ID_OFFSET);
        glEnableVertexAttribArray(3);
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

        glBindBuffer(GL_ARRAY_BUFFER,vboID);
        glBufferSubData(GL_ARRAY_BUFFER,0,vertices);

        shader.bind();
        shader.setUniform("u_proj",projection);
        shader.setUniform("u_view",view);

        for(int i=0; i<textures.size(); i++){
            glActiveTexture(GL_TEXTURE0+i+1);
            glBindTexture(GL11C.GL_TEXTURE_2D,textures.get(i));
        }

        shader.setUniform("u_textures",texSlots);

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES,this.numSprites*6,GL_UNSIGNED_INT,0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        glBindTexture(GL_TEXTURE_2D,0);

        shader.unbind();

        textures.clear();
        numSprites=0;
        numVertices=0;

        if(depthTest==1)
            glEnable(GL_DEPTH_TEST);
        if(cullFace==1)
            glEnable(GL_CULL_FACE);
    }

    private void addVertex(float x,float y,float tx,float ty,int id){
        int offset=numVertices*VERTEX_SIZE;
        numVertices++;

        vertices[offset]=x;
        vertices[offset+1]=y;

        vertices[offset+6]=tx;
        vertices[offset+7]=ty;

        vertices[offset+2]=color.red();
        vertices[offset+3]=color.green();
        vertices[offset+4]=color.blue();
        vertices[offset+5]=color.alpha();

        vertices[offset+8]=id;
    }


    public void setColor(Color color){
        this.color.set(color);
    }

    public void setColor(float r,float g,float b,float a){
        this.color.set(r,g,b,a);
    }

    public void setAlpha(float a){
        this.color.setAlpha(a);
    }

    public boolean hasRoom(){
        return numSprites<maxBatchSize;
    }

    public void dispose(){
        shader.dispose();
        glDeleteBuffers(vboID);
        glDeleteVertexArrays(vaoID);
    }

}