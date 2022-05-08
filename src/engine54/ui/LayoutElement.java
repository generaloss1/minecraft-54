package engine54.ui;

import engine54.graphics.SpriteBatch;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.maths.Maths;

import java.util.UUID;

public abstract class LayoutElement{


    private final UUID uuid;

    private int renderX,renderY,renderWidth,renderHeight, gravity;
    private boolean show,allocated,oldAllocated,touched,oldTouched,touchedDown,touchedUp;
    private double x,y,width,height,gravityX,gravityY;

    private TouchCallback touchCallback;
    private AllocateCallback allocateCallback;

    private LayoutElement parent;


    public LayoutElement(double width,double height){
        uuid=UUID.randomUUID();
        this.width=width;
        this.height=height;
        show=true;
    }


    void updateRenderValues(LayoutElement parent,Window window){
        double ww=parent.getRelativeWidth() *window.getWidth();
        double wh=parent.getRelativeHeight()*window.getHeight();

        double px=0,py=0;
        int gravity=getGravity();

        if(gravity==Gravity.RIGHT_DOWN)
            px=ww-renderWidth;
        else if(gravity==Gravity.LEFT_UP)
            py=wh-renderHeight;
        else if(gravity==Gravity.CENTER_DOWN)
            px=ww/2f-renderWidth/2f;
        else if(gravity==Gravity.LEFT_CENTER)
            py=wh/2f-renderHeight/2f;
        else if(gravity==Gravity.RIGHT_UP){
            px=ww-renderWidth;
            py=wh-renderHeight;
        }else if(gravity==Gravity.CENTER){
            px=ww/2f-renderWidth/2f;
            py=wh/2f-renderHeight/2f;
        }else if(gravity==Gravity.RIGHT_CENTER){
            px=ww-renderWidth;
            py=wh/2f-renderHeight/2f;
        }else if(gravity==Gravity.CENTER_UP){
            px=ww/2f-renderWidth/2f;
            py=wh-renderHeight;
        }

        double wwh=parent.getRelativeWidth()*window.getHeight();

        renderX=Maths.round(x*wh + px + parent.getRelativeX()*window.getWidth ());
        renderY=Maths.round(y*wh + py + parent.getRelativeY()*window.getHeight());
        renderWidth =Maths.round(width *wwh);
        renderHeight=Maths.round(height*wh);
    }

    void updateRenderValues(Window window){
        if(parent!=null){
            updateRenderValues(parent,window);
            return;
        }

        double ww=window.getWidth();
        double wh=window.getHeight();
        double wwh=window.getHeight();

        double px=0,py=0;
        int gravity=getGravity();

        if(gravity==Gravity.RIGHT_DOWN)
            px=ww-renderWidth;
        else if(gravity==Gravity.LEFT_UP)
            py=wh-renderHeight;
        else if(gravity==Gravity.CENTER_DOWN)
            px=ww/2f-renderWidth/2f;
        else if(gravity==Gravity.LEFT_CENTER)
            py=wh/2f-renderHeight/2f;
        else if(gravity==Gravity.RIGHT_UP){
            px=ww-renderWidth;
            py=wh-renderHeight;
        }else if(gravity==Gravity.CENTER){
            px=ww/2f-renderWidth/2f;
            py=wh/2f-renderHeight/2f;
        }else if(gravity==Gravity.RIGHT_CENTER){
            px=ww-renderWidth;
            py=wh/2f-renderHeight/2f;
        }else if(gravity==Gravity.CENTER_UP){
            px=ww/2f-renderWidth/2f;
            py=wh-renderHeight;
        }

        renderX=Maths.round(x*wh + px);
        renderY=Maths.round(y*wh + py);
        renderWidth =Maths.round(width *wwh);
        renderHeight=Maths.round(height*wh);
    }

    void updateWindowGravity(){
        double px=0,py=0;
        int gravity=getGravity();

        double rWidth=getRelativeWidth();
        double rHeight=getRelativeHeight();

        if(gravity==Gravity.RIGHT_DOWN)
            px=1-rWidth;
        else if(gravity==Gravity.LEFT_UP)
            py=1-rHeight;
        else if(gravity==Gravity.CENTER_DOWN)
            px=1/2f-rWidth/2f;
        else if(gravity==Gravity.LEFT_CENTER)
            py=1/2f-rHeight/2f;
        else if(gravity==Gravity.RIGHT_UP){
            px=1-rWidth;
            py=1-rHeight;
        }else if(gravity==Gravity.CENTER){
            px=1/2f-rWidth/2f;
            py=1/2f-rHeight/2f;
        }else if(gravity==Gravity.RIGHT_CENTER){
            px=1-rWidth;
            py=1/2f-rHeight/2f;
        }else if(gravity==Gravity.CENTER_UP){
            px=1/2f-rWidth/2f;
            py=1-rHeight;
        }

        gravityX=px;
        gravityY=py;
    }

    public void updateCallbacks(Mouse mouse,Window window){
        if(!window.isFocused())
            return;

        float tx=mouse.getX();
        float ty=window.getHeight()-mouse.getY();

        boolean cancelledTouch=false;
        if(tx>=renderX && tx<renderX+renderWidth && ty>=renderY && ty<renderY+renderHeight){
            allocated=true;
            touched=mouse.isButtonPressed(0);
        }else{
            allocated=false;
            cancelledTouch=true;
            touched=false;
        }

        touchedDown=false;
        touchedUp=false;

        TouchCallback touchCallback=getTouchCallback();
        if(touchCallback!=null){
            if(touched){
                touchCallback.touched(this);
                if(!oldTouched){
                    touchCallback.touchOn(this);
                    touchedDown=true;
                }
            }else if(oldTouched && !cancelledTouch){
                touchCallback.touchOff(this);
                touchedUp=true;
            }
        }

        AllocateCallback allocateCallback=getAllocateCallback();
        if(allocateCallback!=null){
            if(allocated){
                allocateCallback.allocated(this);
                if(!oldAllocated)
                    allocateCallback.allocateOn(this);
            }else if(oldAllocated)
                allocateCallback.allocateOff(this);
        }

        oldTouched=touched;
        oldAllocated=allocated;
    }

    public boolean isTouched(){
        return touched;
    }

    public boolean isTouchedDown(){
        return touchedDown;
    }

    public boolean isTouchedUp(){
        return touchedUp;
    }

    public int getRenderX(){
        return renderX;
    }
    public int getRenderY(){
        return renderY;
    }

    public int getRenderWidth(){
        return renderWidth;
    }
    public int getRenderHeight(){
        return renderHeight;
    }

    public void setTouchCallback(TouchCallback touchCallback){
        this.touchCallback=touchCallback;
    }
    public TouchCallback getTouchCallback(){
        return touchCallback;
    }

    public void setAllocateCallback(AllocateCallback allocateCallback){
        this.allocateCallback=allocateCallback;
    }
    public AllocateCallback getAllocateCallback(){
        return allocateCallback;
    }


    public void setPos(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void setX(double value){
        x=value;
    }
    public void setY(double value){
        y=value;
    }

    public double x(){
        return x;
    }
    public double y(){
        return y;
    }


    public void setSize(double width,double height){
        this.width=width;
        this.height=height;
    }
    public void setWidth(double value){
        width=value;
    }
    public void setHeight(double value){
        height=value;
    }

    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }


    abstract void render(SpriteBatch batch);
    abstract void update(Mouse mouse,Keyboard keyboard,Window window);
    abstract String getType();


    public void setGravity(int gravity){
        this.gravity=gravity;
    }

    public int getGravity(){
        return gravity;
    }

    public boolean isHidden(){
        return !show;
    }
    public void show(boolean flag){
        show=flag;
    }

    public void setAllocated(boolean flag){
        allocated=flag;
    }
    public boolean isAllocated(){
        return allocated;
    }


    public void setParent(LayoutElement parent){
        this.parent=parent;
    }

    public LayoutElement getParent(){
        return parent;
    }

    public double getRelativeX(){
        if(parent==null)
            if(getType().equals(Layout.class.getSimpleName()))
                return gravityX;
            else
                return x;
        return parent.getRelativeX()+x*parent.getRelativeWidth();
    }

    public double getRelativeY(){
        if(parent==null)
            if(getType().equals(Layout.class.getSimpleName()))
                return gravityY;
            else
                return y;
        return parent.getRelativeY()+y*parent.getRelativeHeight();
    }

    public double getRelativeWidth(){
        if(parent==null)
            return width;
        return width*parent.getRelativeWidth();
    }

    public double getRelativeHeight(){
        if(parent==null)
            return height;
        return height*parent.getRelativeHeight();
    }


    public UUID getUUID(){
        return uuid;
    }


}
