package engine54.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class Utils{

    public static String readFile(String path){
        try{
            byte[] encoded=Files.readAllBytes(Paths.get(path));
            return new String(encoded,StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ByteBuffer resizeBuffer(ByteBuffer buffer,int newCapacity){
        ByteBuffer newBuffer=createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

}
