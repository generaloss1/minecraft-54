package engine54.ui;

import engine54.graphics.SpriteBatch;
import engine54.graphics.texture.Texture;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.util.Assets;

public class Image extends LayoutElement{

    private Texture texture;

    public Image(double width,double height,String textureID){
        super(width,height);
        setImage(textureID);
    }

    public void setImage(String textureID){
        this.texture=Assets.getTexture(textureID);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,getRenderX(),getRenderY(),getRenderWidth(),getRenderHeight());
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues(window);
        updateCallbacks(mouse,window);
    }

    public String getType(){
        return "Image";
    }

}