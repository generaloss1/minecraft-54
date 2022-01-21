package djankcraft.engine.graphics;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL46C.*;

public class Texture{

    private Pixmap pixmap;
    private int id;
    private int filter=GL_NEAREST;

    public Texture(String file){
        try{
            BufferedImage img=ImageIO.read(new FileInputStream(file));
            pixmap=new PixmapRGBA(img);
        }catch(FileNotFoundException e){
            System.err.println("Texture "+file+" is not exists");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Texture(int id){
        this.id=id;
    }

    public Texture(int id,Pixmap pixmap){
        this.pixmap=pixmap.clone();
        this.id=id;
    }

    public Texture(Pixmap pixmap){
        this.pixmap=pixmap.clone();
    }

    public Texture(Texture texture){
        pixmap=texture.getPixmap().clone();
    }

    public Texture(BufferedImage bufferedImage){
        pixmap=new PixmapRGBA(bufferedImage);
    }


    public Texture setFilter(int value){
        filter=value;
        return this;
    }

    public Texture gen(){
        int width=pixmap.getWidth();
        int height=pixmap.getHeight();

        id=glGenTextures();
        glBindTexture(GL_TEXTURE_2D,id);
        {
            glTexStorage2D(GL_TEXTURE_2D,1,GL_RGBA8,width,height);

            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,filter);

            float amount=Math.min(4f,glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAX_ANISOTROPY,amount);

            glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAX_LEVEL,1);

            glTexSubImage2D(GL_TEXTURE_2D,0,0,0,width,height,pixmap.getFormat(),GL_UNSIGNED_BYTE,pixmap.getPixels());

            glGenerateMipmap(GL_TEXTURE_2D);
        }
        glBindTexture(GL_TEXTURE_2D,0);

        return this;
    }

    public void bind(int unit){
        glActiveTexture(GL_TEXTURE0+unit);
        glBindTexture(GL_TEXTURE_2D,id);
    }

    public void dispose(){
        if(id!=0){
            glDeleteTextures(id);
            id=0;
        }
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