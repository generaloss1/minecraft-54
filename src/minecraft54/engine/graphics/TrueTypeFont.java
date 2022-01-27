package minecraft54.engine.graphics;

import minecraft54.engine.utils.Utils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13C;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class TrueTypeFont{


    private Texture texture;
    private STBTTFontinfo info;
    private int fontHeight,ascent,descent,lineGap;
    private ByteBuffer data;
    private STBTTBakedChar.Buffer charData;
    private float cscale;


    public TrueTypeFont(String filePath,int size){
        this(new File(filePath),size);
    }

    public TrueTypeFont(File file,int size){
        fontHeight=size;
        int width=size*(96);
        int height=size*3;


        data=Utils.fileByteBuffer(file);

        if(data==null){
            System.err.println("Failed to create true type font");
            return;
        }

        info=STBTTFontinfo.create();
        stbtt_InitFont(info,data);

        ByteBuffer bitmap=BufferUtils.createByteBuffer(width*height);
        charData=STBTTBakedChar.malloc(96);
        stbtt_BakeFontBitmap(data,fontHeight,bitmap,width,height,32,charData);

        PixmapRGBA pixmap=new PixmapRGBA(width,height);
        for(int i=0; i<width; i++)
            for(int j=0; j<height; j++){
                int v2=Byte.toUnsignedInt(bitmap.get(i*height+j));
                pixmap.setPixel(i,j,1,1,1,v2/256f);
            }
        texture=new Texture(pixmap).setFilter(GL13C.GL_LINEAR).gen();

        try(MemoryStack stack=stackPush()){
            IntBuffer pAscent=stack.mallocInt(1);
            IntBuffer pDescent=stack.mallocInt(1);
            IntBuffer pLineGap=stack.mallocInt(1);

            stbtt_GetFontVMetrics(info,pAscent,pDescent,pLineGap);

            ascent=pAscent.get(0);
            descent=pDescent.get(0);
            lineGap=pLineGap.get(0);
        }

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
