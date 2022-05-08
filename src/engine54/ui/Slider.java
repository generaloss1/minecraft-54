package engine54.ui;

import engine54.graphics.SpriteBatch;
import engine54.graphics.texture.Texture;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.maths.Maths;
import engine54.util.Assets;

public class Slider extends LayoutElement{

    private Texture barTexture,sliderTexture1,sliderTexture2;
    private float value;
    private double sliderWidth,sliderHeight;
    private SliderCallback callback;
    private int sliderRenderWidth;
    private boolean drag;

    public Slider(double width,double height,double sliderWidth,double sliderHeight,String barTextureID,String sliderTextureID1,String sliderTextureID2){
        super(width,height);
        setSliderSize(sliderWidth,sliderHeight);
        setBarImage(barTextureID);
        setSliderImage(sliderTextureID1,sliderTextureID2);
    }

    public void setBarImage(String textureID){
        this.barTexture=Assets.getTexture(textureID);
    }

    public void setSliderImage(String textureID1,String textureID2){
        this.sliderTexture1=Assets.getTexture(textureID1);
        this.sliderTexture2=Assets.getTexture(textureID2);
    }

    public void setValue(float value){
        this.value=value;
    }

    public float getValue(){
        return value;
    }

    public void setSliderSize(double sliderWidth,double sliderHeight){
        this.sliderWidth=sliderWidth;
        this.sliderHeight=sliderHeight;
    }

    public void setSliderCallback(SliderCallback callback){
        this.callback=callback;
    }

    public void render(SpriteBatch batch){
        batch.draw(barTexture,getRenderX(),getRenderY(),getRenderWidth(),getRenderHeight());

        if(isAllocated())
            batch.draw(sliderTexture2,getRenderX()+value*(getRenderWidth()-sliderRenderWidth),getRenderY(),sliderRenderWidth,Maths.round(getRenderHeight()*sliderHeight/getHeight()));
        else
            batch.draw(sliderTexture1,getRenderX()+value*(getRenderWidth()-sliderRenderWidth),getRenderY(),sliderRenderWidth,Maths.round(getRenderHeight()*sliderHeight/getHeight()));
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues(window);
        updateCallbacks(mouse,window);
        sliderRenderWidth=Maths.round(getRenderWidth()*sliderWidth/getWidth());

        int tx=mouse.getX();
        int ty=window.getHeight()-mouse.getY();

        if(mouse.isLeftDown() && tx>=getRenderX() && tx<getRenderX()+getRenderWidth() && ty>=getRenderY() && ty<getRenderY()+getRenderHeight()){
            drag=true;
        }else if(mouse.isLeftReleased())
            drag=false;

        if(drag){
            value=(float)(tx-getRenderX()-sliderRenderWidth/2)/(getRenderWidth()-sliderRenderWidth);
            if(value<0)
                value=0;
            if(value>1)
                value=1;

            if(callback!=null)
                callback.updateValue(this,value);
        }
    }

    public String getType(){
        return "Slider";
    }

}
