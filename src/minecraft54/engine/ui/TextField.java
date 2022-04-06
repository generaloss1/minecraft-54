package minecraft54.engine.ui;

import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.graphics.TrueTypeFont;
import minecraft54.engine.io.Keyboard;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import minecraft54.engine.util.Assets;
import minecraft54.engine.util.Color;
import minecraft54.main.Main;
import org.lwjgl.glfw.GLFW;

public class TextField extends LayoutElement{

    private String text;
    private TrueTypeFont font;
    private final Color color;
    private int maxSymbolCount;

    public TextField(double width,double height,String fontID,String text,int maxSymbolCount){
        super(width,height);
        setText(text);
        setFont(fontID);
        color=new Color();
        this.maxSymbolCount=maxSymbolCount;
    }

    public void setColor(Color color){
        this.color.set(color);
    }
    public void setColor(float r,float g,float b,float a){
        this.color.set(r,g,b,a);
    }
    public Color getColor(){
        return color;
    }

    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return text;
    }

    public void setFont(String fontID){
        this.font=Assets.getTTF(fontID);
    }
    public TrueTypeFont getFont(){
        return font;
    }

    public void setMaxSymbolCount(int maxSymbolCount){
        this.maxSymbolCount=maxSymbolCount;
    }
    public int getMaxSymbolCount(){
        return maxSymbolCount;
    }

    public void render(SpriteBatch batch){
        int rw=getRenderWidth();
        int rh=getRenderHeight();
        float tw=font.getTextWidth(text);
        float th=font.getTextHeight(text);
        Color currentColor=batch.getColor().clone();
        batch.setColor(color);
        batch.drawText(font,text,
                Math.round((rw-tw)/2+getRenderX()),
                Math.round((rh+th)/2+getRenderY()-font.getCScale()*font.getDescent())
        );
        batch.setColor(currentColor);
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues(window);
        updateCallbacks(mouse,window);

        if(text.length()<maxSymbolCount){
            for(int i=44; i<=93; i++)
                if(Main.keyboard.isKeyDown(i)){
                    String symbol=GLFW.glfwGetKeyName(i,0);
                    if(symbol==null)
                        continue;
                    if(i>=65 && i<=90 && Main.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
                        symbol=symbol.toUpperCase();
                    text+=symbol;
                }
            if(Main.keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE))
                text+=" ";
        }
        if(Main.keyboard.isKeyDown(GLFW.GLFW_KEY_BACKSPACE) && text.length()>0)
            text=text.substring(0,text.length()-1);
    }

    public String getType(){
        return "TextField";
    }

}