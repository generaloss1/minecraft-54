package engine54.graphics.texture;

import engine54.util.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Pixmap{


    private ByteBuffer buffer;
    private int width,height;


    // CONSTRUCTOR

    public Pixmap(BufferedImage bufferedImage){
        set(bufferedImage,false);
    }

    public Pixmap(String path){
        set(path,false);
    }

    public Pixmap(BufferedImage bufferedImage,boolean invY){
        set(bufferedImage,invY);
    }

    public Pixmap(String path,boolean invY){
        set(path,invY);
    }

    public Pixmap(Pixmap pixmap){
        buffer=pixmap.buffer.duplicate();
        width=pixmap.width;
        height=pixmap.height;
    }

    public Pixmap(ByteBuffer buffer,int width,int height){
        this.width=width;
        this.height=height;
        this.buffer=buffer.duplicate();
    }

    public Pixmap(int width,int height){
        this.width=width;
        this.height=height;
        buffer=ByteBuffer.allocateDirect(width*height*4);
    }

    // SET

    public void set(String path,boolean invY){
        try{
            set(ImageIO.read(new File(path)),invY);
        }catch(Exception e){
            System.err.println("Pixmap "+path+" is not exists");
        }
    }

    public void set(BufferedImage bufferedImage,boolean invY){
        width=bufferedImage.getWidth();
        height=bufferedImage.getHeight();
        int[] pixels=new int[width*height];
        bufferedImage.getRGB(0,0,width,height,pixels,0,width);
        buffer=ByteBuffer.allocateDirect(width*height*4);

        for(int h=0; h<height; h++){
            for(int w=0; w<width; w++){
                int pixel=pixels[(!invY?h:height-1-h)*width+w];

                buffer.put((byte)((pixel>>16) & 0xFF));
                buffer.put((byte)((pixel>>8 ) & 0xFF));
                buffer.put((byte)((pixel    ) & 0xFF));
                buffer.put((byte)((pixel>>24) & 0xFF));
            }
        }
        buffer.flip();
    }

    public void set(Pixmap pixmap){
        if(pixmap==null)
            return;
        if(width!=pixmap.width || height!=pixmap.height){
            width=pixmap.width;
            height=pixmap.height;
            buffer=ByteBuffer.allocateDirect(width*height*4);
        }
        for(int i=0; i<buffer.limit(); i++)
            buffer.put(i,pixmap.buffer.get(i));
    }

    // SET PIXEL

    public Pixmap setPixel(int x,int y,int color){
        buffer.put( (y*width+x)*4  , (byte)((color>>24) & 0xFF));
        buffer.put( (y*width+x)*4+1, (byte)((color>>16) & 0xFF));
        buffer.put( (y*width+x)*4+2, (byte)((color>>8 ) & 0xFF));
        buffer.put( (y*width+x)*4+3, (byte)((color    ) & 0xFF));
        return this;
    }

    public Pixmap setPixel(int x,int y,Color color){
        buffer.put( (y*width+x)*4  , (byte)( (int)(color.red()  *255) & 0xFF));
        buffer.put( (y*width+x)*4+1, (byte)( (int)(color.green()*255) & 0xFF));
        buffer.put( (y*width+x)*4+2, (byte)( (int)(color.blue() *255) & 0xFF));
        buffer.put( (y*width+x)*4+3, (byte)( (int)(color.alpha()*255) & 0xFF));
        return this;
    }

    public Pixmap setPixel(int x,int y,float r,float g,float b,float a){
        buffer.put( (y*width+x)*4  , (byte)( (int)(r*255) & 0xFF));
        buffer.put( (y*width+x)*4+1, (byte)( (int)(g*255) & 0xFF));
        buffer.put( (y*width+x)*4+2, (byte)( (int)(b*255) & 0xFF));
        buffer.put( (y*width+x)*4+3, (byte)( (int)(a*255) & 0xFF));
        return this;
    }

    public Pixmap setPixel(int x,int y,int r,int g,int b,int a){
        buffer.put( (y*width+x)*4  , (byte)(r & 0xFF));
        buffer.put( (y*width+x)*4+1, (byte)(g & 0xFF));
        buffer.put( (y*width+x)*4+2, (byte)(b & 0xFF));
        buffer.put( (y*width+x)*4+3, (byte)(a & 0xFF));
        return this;
    }

    // GET PIXEL

    public int getPixel(int x,int y){
        int r=buffer.get((y*width+x)*4  ) & 0xFF;
        int g=buffer.get((y*width+x)*4+1) & 0xFF;
        int b=buffer.get((y*width+x)*4+2) & 0xFF;
        int a=buffer.get((y*width+x)*4+3) & 0xFF;
        return r<<24 | g<<16 | b<<8 | a;
    }

    public Color getPixelColor(int x,int y){
        return new Color(
                buffer.get((y*width+x)*4  )/255f,
                buffer.get((y*width+x)*4+1)/255f,
                buffer.get((y*width+x)*4+2)/255f,
                buffer.get((y*width+x)*4+3)/255f
        );
    }

    // DRAW PIXMAP

    public Pixmap drawPixmap(Pixmap pixmap){
        if(pixmap==null)
            return this;
        final float iEnd=Math.min(pixmap.width,width);
        final float jEnd=Math.min(pixmap.height,height);

        for(int i=0; i<iEnd; i++)
            for(int j=0; j<jEnd; j++){
                buffer.put( (j*width+i)*4  , pixmap.buffer.get((j*pixmap.width+i)*4  ));
                buffer.put( (j*width+i)*4+1, pixmap.buffer.get((j*pixmap.width+i)*4+1));
                buffer.put( (j*width+i)*4+2, pixmap.buffer.get((j*pixmap.width+i)*4+2));
                buffer.put( (j*width+i)*4+3, pixmap.buffer.get((j*pixmap.width+i)*4+3));
            }

        return this;
    }

    public Pixmap drawPixmap(Pixmap pixmap,int x,int y){
        if(pixmap==null)
            return this;
        float iEnd=(iEnd=x+pixmap.width)>width?width:iEnd;
        float jEnd=(jEnd=y+pixmap.height)>height?height:jEnd;

        for(int i=Math.max(0,x); i<iEnd; i++)
            for(int j=Math.max(0,y); j<jEnd; j++){
                int px=i-x;
                int py=j-y;

                buffer.put( (j*width+i)*4  , pixmap.buffer.get((py*pixmap.width+px)*4  ));
                buffer.put( (j*width+i)*4+1, pixmap.buffer.get((py*pixmap.width+px)*4+1));
                buffer.put( (j*width+i)*4+2, pixmap.buffer.get((py*pixmap.width+px)*4+2));
                buffer.put( (j*width+i)*4+3, pixmap.buffer.get((py*pixmap.width+px)*4+3));
            }

        return this;
    }

    public Pixmap drawPixmap(Pixmap pixmap,float scale){
        if(pixmap==null || scale<=0)
            return this;
        float iEnd=(iEnd=pixmap.width*scale)>width?width:iEnd;
        float jEnd=(jEnd=pixmap.height*scale)>height?height:jEnd;

        for(int i=0; i<iEnd; i++)
            for(int j=0; j<jEnd; j++){
                int px=(int)(i/scale);
                int py=(int)(j/scale);

                buffer.put( (j*width+i)*4  , pixmap.buffer.get((py*pixmap.width+px)*4  ));
                buffer.put( (j*width+i)*4+1, pixmap.buffer.get((py*pixmap.width+px)*4+1));
                buffer.put( (j*width+i)*4+2, pixmap.buffer.get((py*pixmap.width+px)*4+2));
                buffer.put( (j*width+i)*4+3, pixmap.buffer.get((py*pixmap.width+px)*4+3));
            }

        return this;
    }

    public Pixmap drawPixmap(Pixmap pixmap,int x,int y,float scale){
        if(pixmap==null || scale<=0)
            return this;
        float iEnd=(iEnd=x+pixmap.width*scale)>width?width:iEnd;
        float jEnd=(jEnd=y+pixmap.height*scale)>height?height:jEnd;

        for(int i=Math.max(0,x); i<iEnd; i++)
            for(int j=Math.max(0,y); j<jEnd; j++){
                int px=(int)((i-x)/scale);
                int py=(int)((j-y)/scale);

                buffer.put( (j*width+i)*4  , pixmap.buffer.get((py*pixmap.width+px)*4  ));
                buffer.put( (j*width+i)*4+1, pixmap.buffer.get((py*pixmap.width+px)*4+1));
                buffer.put( (j*width+i)*4+2, pixmap.buffer.get((py*pixmap.width+px)*4+2));
                buffer.put( (j*width+i)*4+3, pixmap.buffer.get((py*pixmap.width+px)*4+3));
            }

        return this;
    }

    // CLEAR

    public Pixmap clear(){
        for(int i=0; i<buffer.limit(); i++)
            buffer.put(i,(byte)0);

        return this;
    }

    public Pixmap clear(float r,float g,float b,float a){
        for(int i=0; i<buffer.limit(); i+=4){
            buffer.put(i  ,(byte)(r*255));
            buffer.put(i+1,(byte)(g*255));
            buffer.put(i+2,(byte)(b*255));
            buffer.put(i+3,(byte)(a*255));
        }

        return this;
    }

    public Pixmap clear(int r,int g,int b,int a){
        for(int i=0; i<buffer.limit(); i+=4){
            buffer.put(i  ,(byte)r);
            buffer.put(i+1,(byte)g);
            buffer.put(i+2,(byte)b);
            buffer.put(i+3,(byte)a);
        }

        return this;
    }

    public Pixmap clear(Color color){
        for(int i=0; i<buffer.limit(); i+=4){
            buffer.put(i  ,(byte)(color.red()*255));
            buffer.put(i+1,(byte)(color.green()*255));
            buffer.put(i+2,(byte)(color.blue()*255));
            buffer.put(i+3,(byte)(color.alpha()*255));
        }

        return this;
    }

    // GETTERS

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ByteBuffer getPixels(){
        return buffer;
    }

    // OTHER

    public Pixmap clone(){
        return new Pixmap(this);
    }


}
