package minecraft54.engine.gui;

import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.graphics.Texture;
import minecraft54.engine.io.Keyboard;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import minecraft54.engine.utils.Assets;

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
        int width=Math.round(getRenderWidth()*((float)texture.getWidth()/texture.getHeight()));
        batch.draw(texture,Math.round(getRenderX()-(width-getRenderWidth())/2f),getRenderY(),width,getRenderHeight());
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues();
        updateCallbacks(mouse,window);
    }

    public String getType(){
        return "Image";
    }

}
