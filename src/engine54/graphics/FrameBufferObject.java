package engine54.graphics;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class FrameBufferObject{

    private int frameBuffer,frameTexture,depthBuffer,depthTexture;

    public FrameBufferObject(int width,int height){
        frameBuffer=glGenFramebuffers();
        bind();
        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        frameTexture=glGenTextures();
        glBindTexture(GL_TEXTURE_2D,frameTexture);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width,height,0,GL_RGBA,GL_UNSIGNED_BYTE,(ByteBuffer)null);
        glFramebufferTexture(GL_FRAMEBUFFER,GL_COLOR_ATTACHMENT0,frameTexture,0);

        //depthBuffer=glGenRenderbuffers();
        //glBindRenderbuffer(GL_RENDERBUFFER,depthBuffer);
        //glRenderbufferStorage(GL_RENDERBUFFER,GL_DEPTH_COMPONENT,width,height);
        //glFramebufferRenderbuffer(GL_FRAMEBUFFER,GL_DEPTH_ATTACHMENT,GL_RENDERBUFFER,depthBuffer);

        //depthTexture=glGenTextures();
        //glBindTexture(GL_TEXTURE_2D,depthTexture);
        //glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
        //glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        //glTexImage2D(GL_TEXTURE_2D,0,GL_DEPTH_COMPONENT32,width,height,0,GL_DEPTH_COMPONENT,GL_FLOAT,(ByteBuffer)null);
        //glFramebufferTexture(GL_RENDERBUFFER,GL_DEPTH_ATTACHMENT,depthTexture,0);

        unbind();
    }

    public void resize(int width,int height){
        //glBindTexture(GL_TEXTURE_2D,frameTexture);
        glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,width,height,0,GL_RGB,GL_UNSIGNED_BYTE,(ByteBuffer)null);

        //glBindTexture(GL_TEXTURE_2D,depthTexture);
        //glTexImage2D(GL_TEXTURE_2D,0,GL_DEPTH_COMPONENT32,width,height,0,GL_DEPTH_COMPONENT,GL_FLOAT,(ByteBuffer)null);
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER,frameBuffer);
        //glBindRenderbuffer(GL_RENDERBUFFER,depthBuffer);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER,0);
        //glBindRenderbuffer(GL_RENDERBUFFER,0);
    }

    public int getTextureID(){
        return frameTexture;
    }

    public int getFrameBufferID(){
        return frameBuffer;
    }

    public int getDepthBufferID(){
        return depthBuffer;
    }

    public void dispose(){
        glDeleteTextures(frameTexture);
        glDeleteFramebuffers(frameBuffer);
    }

}
