package minecraft54.test;

import minecraft54.engine.graphics.Pixmap;
import minecraft54.engine.graphics.Texture;

import java.util.HashMap;

public class TextureAtlas{


    private Texture result;
    private HashMap<String,double[]> textureCoordinates;

    private int height,width;


    public TextureAtlas(){
        result=new Texture(new Pixmap(width,height));
        result.genTexture();
    }


    public void add(String path,String id){
        Pixmap pixmap=new Pixmap(path);

        int pixmapHeight=pixmap.getHeight();
        if(pixmapHeight>height)
            height=pixmapHeight;
        width+=pixmap.getWidth();

        Pixmap newPixmap=new Pixmap(width,height);
        for(int i=0; i<1000000; i++){

        }
    }


    public Texture getResult(){
        return result;
    }


}
