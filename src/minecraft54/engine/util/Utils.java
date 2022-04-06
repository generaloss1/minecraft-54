package minecraft54.engine.util;

import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class Utils{

    public static boolean contains(int[] arr,int e){
        for(int i=0; i<arr.length; i++)
            if(arr[i]==e)
                return true;
        return false;
    }

    public static String readFile(String path){
        try{
            byte[] encoded=Files.readAllBytes(Paths.get(path));
            return new String(encoded,StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String readFile(File file){
        try{
            byte[] encoded=Files.readAllBytes(file.toPath());
            return new String(encoded,StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ByteBuffer fileByteBuffer(File file){
        try{
            FileInputStream stream=new FileInputStream(file);
            byte[] bytes=stream.readAllBytes();
            ByteBuffer buffer=BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;
        }catch(FileNotFoundException e){
            System.err.println("File "+file.getPath()+" is not exists");
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ByteBuffer resizeBuffer(ByteBuffer buffer,int newCapacity){
        ByteBuffer newBuffer=createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static float[] add(float[] a,float[] b){
        float[] c=new float[a.length+b.length];
        System.arraycopy(a,0,c,0,a.length);
        System.arraycopy(b,0,c,a.length,b.length);
        return c;
    }

    public static float[] add(float[] a,float[]... b){
        float[] c=add(a,b[0]);
        for(int i=1; i<b.length; i++)
            c=add(c,b[i]);
        return c;
    }


}
