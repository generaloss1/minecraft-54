package djankcraft.engine.gui;

import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.graphics.Texture;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;
import djankcraft.engine.utils.Assets;

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
