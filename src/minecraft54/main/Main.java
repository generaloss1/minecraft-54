package minecraft54.main;

import engine54.E54;
import engine54.io.Window;

import static org.lwjgl.opengl.GL46C.*;

public class Main{

    public static void main(String[] args){
        E54.init();

        Window window=new Window(925,530,"Minecraft 54",true,true);
        window.setIcon("icon.png");
        E54.setIO(window);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        glEnable(GL_CULL_FACE);

        E54.context().begin(new Minecraft54());
    }

}
