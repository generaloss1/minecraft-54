package engine54.graphics;

import engine54.graphics.texture.Pixmap;
import engine54.graphics.texture.Texture;
import engine54.util.Utils;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;

import static org.lwjgl.stb.STBTruetype.*;

public class TrueTypeFont{


    private Texture texture;
    private STBTTFontinfo info;
    private int fontHeight,ascent,descent,lineGap;
    private ByteBuffer data;
    private STBTTBakedChar.Buffer charData;
    private float cscale;


    public TrueTypeFont(String path,int size){
        fontHeight=size;
        int width=size*(96);
        int height=size*3;

        try{
            byte[] bytes=Files.readAllBytes(new File(path).toPath());
            data=ByteBuffer.allocateDirect(bytes.length);
            data.put(bytes);
            data.flip();
        }catch(FileNotFoundException e){
            System.err.println("True type font "+path+" is not exists");
            return;
        }catch(IOException e){
            System.err.println("Failed to load true type font "+path);
            return;
        }

        info=STBTTFontinfo.create();
        stbtt_InitFont(info,data);

        ByteBuffer bitmap=BufferUtils.createByteBuffer(width*height);
        charData=STBTTBakedChar.malloc(96);
        stbtt_BakeFontBitmap(data,fontHeight,bitmap,width,height,32,charData);

        Pixmap pixmap=new Pixmap(width,height);
        for(int i=0; i<width; i++)
            for(int j=0; j<height; j++){
                int v2=Byte.toUnsignedInt(bitmap.get(j*width+i));
                pixmap.setPixel(i,j,1,1,1,v2/256f);
            }
        texture=new Texture(pixmap);

        int[] pAscent=new int[1], pDescent=new int[1], pLineGap=new int[1];
        stbtt_GetFontVMetrics(info,pAscent,pDescent,pLineGap);
        ascent=pAscent[0]; descent=pDescent[0]; lineGap=pLineGap[0];

        cscale=stbtt_ScaleForPixelHeight(info,fontHeight);
    }


    public float getLineWidth(String line){
        float length=0f;
        for(int i=0; i<line.length(); i++){
            IntBuffer advancewidth=BufferUtils.createIntBuffer(1);
            IntBuffer leftsidebearing=BufferUtils.createIntBuffer(1);
            stbtt_GetCodepointHMetrics(info,line.charAt(i),advancewidth,leftsidebearing);
            length+=advancewidth.get(0);
        }
        return length*cscale;
    }

    public float getTextWidth(String text){
        String[] lines=text.split("\n");
        float maxWidth=0;
        for(String line:lines){
            float width=getLineWidth(line);
            if(width>maxWidth)
                maxWidth=width;
        }
        return maxWidth;
    }


    public int getTextHeight(String text){
        int height=-fontHeight;
        for(int i=0; i<text.length(); i++)
            if(text.charAt(i)=='\n'){
                height+=fontHeight;
            }
        return height;
    }


    public float getCScale(){
        return cscale;
    }


    public int getFontHeight(){
        return fontHeight;
    }

    public STBTTBakedChar.Buffer getCharData(){
        return charData;
    }

    public ByteBuffer getData(){
        return data;
    }

    public STBTTFontinfo getInfo(){
        return info;
    }

    public Texture getTexture(){
        return texture;
    }

    public int getAscent(){
        return ascent;
    }

    public int getDescent(){
        return descent;
    }

    public int getLineGap(){
        return lineGap;
    }

    public void dispose(){
        if(texture!=null)
            texture.dispose();
    }


}
