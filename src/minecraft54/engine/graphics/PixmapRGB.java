package minecraft54.engine.graphics;

import minecraft54.engine.utils.Color;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class PixmapRGB implements Pixmap{


    private ByteBuffer buffer;
    private int width,height;


    public PixmapRGB(BufferedImage bufferedImage){
        setFromBufferedImage(bufferedImage);
    }

    public PixmapRGB(String filePath){
        try{
            setFromBufferedImage(ImageIO.read(new File(filePath)));
        }catch(FileNotFoundException e){
            System.err.println("Pixmap "+filePath+" is not exists");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public PixmapRGB(PixmapRGB pixmap){
        buffer=pixmap.buffer.duplicate();
        width=pixmap.width;
        height=pixmap.height;
    }

    public PixmapRGB(ByteBuffer buffer,int width,int height){
        this.buffer=buffer.duplicate();
        this.width=width;
        this.height=height;
    }

    public PixmapRGB(int width,int height){
        buffer=ByteBuffer.allocateDirect(width*height*3);
        this.width=width;
        this.height=height;
    }

    public PixmapRGB setPixel(int x,int y,int color){
        buffer.put( (x*height+y)*3,   (byte)((color>>24) & 0xFF));
        buffer.put( (x*height+y)*3+1, (byte)((color>>16) & 0xFF));
        buffer.put( (x*height+y)*3+2, (byte)((color>>8 ) & 0xFF));
        return this;
    }

    public PixmapRGB setPixel(int x,int y,Color color){
        buffer.put( (x*height+y)*3  , (byte)( (int)(color.red()*255  ) & 0xFF));
        buffer.put( (x*height+y)*3+1, (byte)( (int)(color.green()*255) & 0xFF));
        buffer.put( (x*height+y)*3+2, (byte)( (int)(color.blue()*255 ) & 0xFF));
        return this;
    }

    public PixmapRGB setPixel(int x,int y,float r,float g,float b){
        buffer.put( (x*height+y)*3  , (byte)( (int)(r*255) & 0xFF));
        buffer.put( (x*height+y)*3+1, (byte)( (int)(g*255) & 0xFF));
        buffer.put( (x*height+y)*3+2, (byte)( (int)(b*255) & 0xFF));
        return this;
    }

    private void setFromBufferedImage(BufferedImage bufferedImage){
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
        int[] pixels=new int[width*height];
        bufferedImage.getRGB(0,0,width,height,pixels,0,width);
        buffer=ByteBuffer.allocateDirect(width*height*3);

        for(int h=0; h<height; h++){
            for(int w=0; w<width; w++){
                int pixel=pixels[h*width+w];

                buffer.put((byte)((pixel>>16) & 0xFF));
                buffer.put((byte)((pixel>>8) & 0xFF));
                buffer.put((byte)((pixel) & 0xFF));

            }
        }
        buffer.flip();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ByteBuffer getPixels(){
        return buffer;
    }

    public Pixmap clone(){
        return new PixmapRGB(this);
    }

    public int getFormat(){
        return GL11.GL_RGB;
    }

}
