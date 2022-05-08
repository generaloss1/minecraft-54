package engine54.graphics.texture;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL33.*;

public class Texture{

    private Pixmap pixmap;
    private int id;

    public Texture(String file){
        this(file,false);
        genTexture();
    }

    public Texture(String file,boolean invY){
        pixmap=new Pixmap(file,invY);
        genTexture();
    }

    public Texture(int id){
        this.id=id;
    }

    public Texture(int id,Pixmap pixmap){
        this.pixmap=pixmap;
        this.id=id;
        genTexture();
    }

    public Texture(Pixmap pixmap){
        this.pixmap=pixmap;
        genTexture();
    }

    public Texture(Texture texture){
        pixmap=texture.getPixmap().clone();
        genTexture();
    }

    public Texture(BufferedImage bufferedImage){
        pixmap=new Pixmap(bufferedImage);
        genTexture();
    }

    public Texture setPixmap(Pixmap pixmap){
        this.pixmap.set(pixmap);
        dispose();
        genTexture();

        return this;
    }

    private void genTexture(){
        id=glGenTextures();
        glBindTexture(GL_TEXTURE_2D,id);

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,pixmap.getWidth(),pixmap.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,pixmap.getPixels());
    }

    public void bind(int unit){
        glActiveTexture(GL_TEXTURE0+unit);
        glBindTexture(GL_TEXTURE_2D,id);
    }

    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D,0);
    }

    public void dispose(){
        glDeleteTextures(id);
    }

    public Pixmap getPixmap(){
        return pixmap;
    }

    public int getWidth(){
        return pixmap.getWidth();
    }

    public int getHeight(){
        return pixmap.getHeight();
    }

    public int getId(){
        return id;
    }

}