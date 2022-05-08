package engine54.ui;

import engine54.graphics.SpriteBatch;
import engine54.graphics.texture.Texture;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.util.Assets;

public class Button extends LayoutElement{

    private Texture texture1,texture2,texture3;

    public Button(double width,double height,String texture1ID,String texture2ID,String texture3ID){
        super(width,height);
        setTextures(texture1ID,texture2ID,texture3ID);
    }

    public void setTextures(String texture1ID,String texture2ID,String texture3ID){
        texture1=Assets.getTexture(texture1ID);
        texture2=Assets.getTexture(texture2ID);
        texture3=Assets.getTexture(texture3ID);
    }

    public void render(SpriteBatch batch){
        if(isTouched())
            batch.draw(texture3,getRenderX(),getRenderY(),getRenderWidth(),getRenderHeight());
        else if(isAllocated())
            batch.draw(texture2,getRenderX(),getRenderY(),getRenderWidth(),getRenderHeight());
        else
            batch.draw(texture1,getRenderX(),getRenderY(),getRenderWidth(),getRenderHeight());
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues(window);
        updateCallbacks(mouse,window);
    }

    public String getType(){
        return "Button";
    }

}
