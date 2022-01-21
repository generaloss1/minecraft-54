package djankcraft.engine.gui;

import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;

import java.util.UUID;

public interface LayoutElement{


    int BUTTON=0;


    void render(SpriteBatch spriteBatch);
    void update(Mouse mouse,Keyboard keyboard,Window window);

    void setGravity(int gravity);

    boolean isHidden();
    UUID getUUID();
    int getElementType();

    void setAllocated(boolean action);
    boolean isAllocated();

}
