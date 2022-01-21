package djankcraft.engine.gui;

import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;
import org.lwjgl.glfw.GLFW;
import java.util.HashMap;

public class Layout{


    public HashMap<String,LayoutElement> elements;
    public int aen,oaen;


    public Layout(){
        elements=new HashMap<>();
        aen=-1;
        oaen=-1;
    }


    public void addElement(String id,LayoutElement element){
        elements.put(id,element);
    }

    public void removeElement(String id,LayoutElement element){
        elements.put(id,element);
    }

    public LayoutElement getElement(String id){
        return elements.get(id);
    }


    public void render(SpriteBatch batch){
        for(String k:elements.keySet()){
            LayoutElement e=elements.get(k);
            if(!e.isHidden())
                e.render(batch);
        }
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        if(keyboard.isKeyDown(GLFW.GLFW_KEY_TAB)){
            oaen=aen;
            if(aen==-1)
                aen=0;
            else{
                aen++;
                if(aen>=elements.size())
                    aen=0;
            }

            if(oaen!=-1)
                elements.get(oaen).setAllocated(false);
            elements.get(aen).setAllocated(true);
        }
        if(mouse.isButtonDown(0) || mouse.isButtonDown(1) || mouse.isButtonDown(2)){
            for(String k:elements.keySet())
                elements.get(k).setAllocated(false);
            aen=-1;
            oaen=-1;
        }

        for(String k:elements.keySet())
            elements.get(k).update(mouse,keyboard,window);
    }


}
