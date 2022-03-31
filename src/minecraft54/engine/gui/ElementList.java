package minecraft54.engine.gui;

import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.graphics.Texture;
import minecraft54.engine.io.Keyboard;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import minecraft54.engine.utils.Assets;

import java.util.ArrayList;
import java.util.List;

public class ElementList extends LayoutElement{

    private Texture texture;
    private final List<LayoutElement> elements;

    public ElementList(double width,double height,String textureID){
        super(width,height);
        setImage(textureID);
        elements=new ArrayList<>();
    }

    public void setImage(String textureID){
        this.texture=Assets.getTexture(textureID);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,getRenderX(),getRenderY(),getRenderWidth(),getRenderHeight());
        for(LayoutElement element: elements)
            if(!element.isHidden())
                element.render(batch);
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        updateRenderValues(window);
        updateCallbacks(mouse,window);
        for(LayoutElement element: elements)
            element.update(mouse,keyboard,window);
    }

    public void addElement(LayoutElement element){
        element.setParent(this);
        element.setSize(1,0.1);
        element.setGravity(Gravity.RIGHT_UP);
        elements.add(element);
    }

    public void removeElement(int index){
        elements.remove(index);
    }

    public LayoutElement getElement(int index){
        return elements.get(index);
    }

    public void clear(){
        elements.clear();
    }

    public String getType(){
        return "ElementList";
    }

}