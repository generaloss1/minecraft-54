package engine54.graphics;

import engine54.E54;
import engine54.graphics.vertices.*;

import static org.lwjgl.opengl.GL33.*;

public class FramePostProcessor{

    private final FrameBufferObject fbo;
    private final VertexArrayObject vao;
    private final VertexBufferObject vbo;
    private final ShaderProgram shader;

    public FramePostProcessor(){
        fbo=new FrameBufferObject(E54.getWinWidth(),E54.getWinHeight());
        glBindFramebuffer(GL_FRAMEBUFFER,fbo.getFrameBufferID());

        String vs=
                """
                #version 330
        
                in vec3 a_pos;
                in vec2 a_uv;
                
                out vec2 uv;
        
                void main(){
                    gl_Position=vec4(a_pos,1.0);
                    uv=a_uv;
                }
                """;
        String fs=
                """
                #version 330

                in vec2 uv;

                out vec4 fragColor;

                uniform sampler2D u_buffer;

                void main(){
                    fragColor=texture(u_buffer,uv);
                }
                """;

        shader=new ShaderProgram(vs,fs);
        shader.addUniforms("u_buffer");

        vao=new VertexArrayObject();
        vbo=new VertexBufferObject();
        vbo.enableAttributes(new VertexAttribute(2,GL_FLOAT),new VertexAttribute(2,GL_FLOAT));
        float[] vertices={
                -1,+1, 0,1,
                -1,-1, 0,0,
                +1,-1, 1,0,

                +1,-1, 1,0,
                +1,+1, 1,1,
                -1,+1, 0,1,
        };
        vbo.setData(vertices,GL_STATIC_DRAW);
    }

    public void resize(int width,int height){
        fbo.resize(width,height);
    }

    public void begin(){
        fbo.bind();
    }

    public void end(){
        fbo.unbind();

        shader.bind();

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,fbo.getTextureID());
        shader.setUniform("u_buffer",0);

        vao.drawArrays(6);
    }

    public void dispose(){
        fbo.unbind();
        fbo.dispose();
        shader.dispose();
        vao.dispose();
        vbo.dispose();
    }

}
