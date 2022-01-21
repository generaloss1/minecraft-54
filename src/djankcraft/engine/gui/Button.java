package djankcraft.engine.gui;

import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.graphics.Texture;
import djankcraft.engine.graphics.TextureRegion;
import djankcraft.engine.graphics.TrueTypeFont;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;
import djankcraft.engine.utils.Assets;

import java.util.UUID;

public class Button implements LayoutElement{


    public static final int RELEASED=0;
    public static final int JUST_PRESSED=1;
    public static final int PRESSED=2;
    public static final int RELEASE=3;
    public static final int ALLOCATED=4;


    private final UUID uuid;

    private double x,y,width,height;
    private TextureRegion released,pressed,allocated;
    private boolean isHidden,isAllocated,isPressed,isJustPressed, isReleased,oldIsPressed;
    private int dx,dy,dw,dh,gravity;
    private TrueTypeFont font;
    private String text;
    private ButtonCallback callback;


    public Button(double width,double height,TextureRegion released,TextureRegion allocated,TextureRegion pressed){
        uuid=UUID.randomUUID();

        this.width=width;
        this.height=height;
        this.released=released;
        this.pressed=pressed;
        this.allocated=allocated;
    }

    public Button(double width,double height,Texture released,Texture allocated,Texture pressed){
        uuid=UUID.randomUUID();

        this.width=width;
        this.height=height;
        this.released=new TextureRegion(released);
        this.pressed=new TextureRegion(pressed);
        this.allocated=new TextureRegion(allocated);
    }

    public Button(double width,double height,String textureReleased,String textureAllocated,String texturePressed){
        uuid=UUID.randomUUID();

        this.width=width;
        this.height=height;
        this.released=new TextureRegion(Assets.getTexture(textureReleased));
        this.pressed=new TextureRegion(Assets.getTexture(texturePressed));
        this.allocated=new TextureRegion(Assets.getTexture(textureAllocated));
    }


    public void setCallback(ButtonCallback callback){
        this.callback=callback;
    }


    public void setAllocated(boolean a){
        isAllocated=a;
    }

    public void setPos(double x,double y){
        this.x=x;
        this.y=y;
    }

    public void setSize(double width,double height){
        this.width=width;
        this.height=height;
    }

    public void setTextureReleased(TextureRegion t){
        released=t;
    }

    public void setTexturePressed(TextureRegion t){
        pressed=t;
    }

    public void setTextureAllocated(TextureRegion t){
        allocated=t;
    }

    public void setTextureReleased(Texture t){
        released=new TextureRegion(t);
    }

    public void setTexturePressed(Texture t){
        pressed=new TextureRegion(t);
    }

    public void setTextureAllocated(Texture t){
        allocated=new TextureRegion(t);
    }

    public void setTextures(TextureRegion released,TextureRegion pressed,TextureRegion allocated){
        this.released=released;
        this.pressed=pressed;
        this.allocated=allocated;
    }

    public void setTextures(Texture released,Texture pressed,Texture allocated){
        this.released=new TextureRegion(released);
        this.pressed=new TextureRegion(pressed);
        this.allocated=new TextureRegion(allocated);
    }

    public void show(){
        isHidden=false;
    }

    public void hide(){
        isHidden=true;
    }


    public boolean isAllocated(){
        return isAllocated;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public TextureRegion getTextureReleased(){
        return released;
    }

    public TextureRegion getTexturePressed(){
        return pressed;
    }

    public TextureRegion getTextureAllocated(){
        return allocated;
    }

    public boolean isHidden(){
        return isHidden;
    }

    public boolean isJustPressed(){
        return isJustPressed;
    }

    public boolean isPressed(){
        return isPressed;
    }

    public boolean isReleased(){
        return isReleased;
    }


    public void render(SpriteBatch batch){
        if(isPressed)
            batch.draw(pressed,dx,dy,dw,dh);
        else if(isAllocated)
            batch.draw(allocated,dx,dy,dw,dh);
        else
            batch.draw(released,dx,dy,dw,dh);

        if(text!=null && font!=null){
            batch.setColor(0.9f,0.9f,1f,1);
            batch.drawText(font,text,
                    Math.round(dx+dw/2f-font.getTextWidth(text)/2),
                    Math.round(dy+dh/2f+font.getTextHeight(text)/2f-font.getCScale()*font.getDescent())
            );
            batch.setColor(0.2f,0.2f,0.4f,1);
            batch.drawText(font,text,
                    Math.round(dx+dw/2f-font.getTextWidth(text)/2)+1,
                    Math.round(dy+dh/2f+font.getTextHeight(text)/2f-font.getCScale()*font.getDescent())-1
            );
        }
        batch.setColor(1,1,1,1);
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        int ww=window.getWidth();
        int wh=window.getHeight();

        dw=(int)Math.round(width*wh);
        dh=(int)Math.round(height*wh);

        float px=0,py=0;

        if(gravity==Gravity.RIGHT_DOWN)
            px=ww-dw;
        else if(gravity==Gravity.LEFT_UP)
            py=wh-dh;
        else if(gravity==Gravity.CENTER_DOWN)
            px=ww/2f-dw/2f;
        else if(gravity==Gravity.LEFT_CENTER)
            py=wh/2f-dh/2f;
        else if(gravity==Gravity.RIGHT_UP){
            px=ww-dw;
            py=wh-dh;
        }else if(gravity==Gravity.CENTER){
            px=ww/2f-dw/2f;
            py=wh/2f-dh/2f;
        }else if(gravity==Gravity.RIGHT_CENTER){
            px=ww-dw;
            py=wh/2f-dh/2f;
        }else if(gravity==Gravity.CENTER_UP){
            px=ww/2f-dw/2f;
            py=wh-dh;
        }

        dx=(int)Math.round(x*wh + px);
        dy=(int)Math.round(y*wh + py);

        float tx=mouse.getX();
        float ty=window.getHeight()-mouse.getY();

        if(isJustPressed)
            isJustPressed=false;
        if(isReleased)
            isReleased=false;

        if(tx>=dx && tx<dx+dw && ty>=dy && ty<dy+dh){
            if(mouse.isButtonPressed(0))
                isPressed=true;
            else
                isPressed=false;
            isAllocated=true;
        }else{
            isPressed=false;
            isAllocated=false;
        }

        if(!oldIsPressed && isPressed)
            isJustPressed=true;
        else if(oldIsPressed && !isPressed)
            isReleased=true;

        if(callback!=null){
            if(isJustPressed)
                callback.down();
            if(isPressed)
                callback.pressed();
            if(isReleased)
                callback.release();
        }

        oldIsPressed=isPressed;
    }

    public void setText(TrueTypeFont font,String text){
        this.font=font;
        this.text=text;
    }

    public void setGravity(int gravity){
        this.gravity=gravity;
    }

    public UUID getUUID(){
        return uuid;
    }

    public int getElementType(){
        return LayoutElement.BUTTON;
    }

}
