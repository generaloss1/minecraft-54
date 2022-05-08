package engine54.ui;

import engine54.E54;
import engine54.graphics.SpriteBatch;
import engine54.graphics.TrueTypeFont;
import engine54.io.Keyboard;
import engine54.io.Mouse;
import engine54.io.Window;
import engine54.util.Assets;
import engine54.util.Color;
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
                if(E54.keyboard().isKeyDown(i)){
                    String symbol=GLFW.glfwGetKeyName(i,0);
                    if(symbol==null)
                        continue;
                    if(i>=65 && i<=90 && E54.keyboard().isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
                        symbol=symbol.toUpperCase();
                    text+=symbol;
                }
            if(E54.keyboard().isKeyDown(GLFW.GLFW_KEY_SPACE))
                text+=" ";
        }
        if(E54.keyboard().isKeyDown(GLFW.GLFW_KEY_BACKSPACE) && text.length()>0){
            if(E54.keyboard().isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)){
                int symbols=1;
                while(true){
                    if(symbols==text.length())
                        break;
                    char symbol=text.charAt(text.length()-symbols-1);
                    if(symbol==' ')
                        break;

                    symbols++;
                }
                text=text.substring(0,text.length()-symbols);
            }else
                text=text.substring(0,text.length()-1);
        }
    }

    public String getType(){
        return "TextField";
    }

}