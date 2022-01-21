package djankcraft.minecraft54.screens;

import djankcraft.engine.app.AppScreen;
import djankcraft.engine.graphics.OrthographicCamera;
import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.gui.Button;
import djankcraft.engine.gui.ButtonCallback;
import djankcraft.engine.gui.Gravity;
import djankcraft.engine.gui.Layout;
import djankcraft.engine.utils.Assets;
import djankcraft.minecraft54.Main;
import djankcraft.minecraft54.MouseCursor;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.opengl.GL11C.*;

public class MenuScreen implements AppScreen{


    SpriteBatch sb;
    OrthographicCamera cam;
    Layout layout;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);
        layout=new Layout();

        Button button=new Button(0.5,0.15,"button1_n","button1_a","button1_p");
        button.setText(Assets.getTTF("font3"),"Settings");
        button.setPos(0.3,-0.4);
        button.setGravity(Gravity.CENTER_UP);
        layout.addElement("btt1",button);

        button=new Button(0.5,0.15,"button1_n","button1_a","button1_p");
        button.setText(Assets.getTTF("font3"),"Play");
        button.setPos(-0.3,-0.4);
        button.setGravity(Gravity.CENTER_UP);
        button.setCallback(new ButtonCallback(){
            public void down(){}
            public void pressed(){}
            public void release(){
                Main.cfg.setScreen("world list");
            }
        });
        layout.addElement("btt2",button);

        button=new Button(0.5,0.15,"button1_n","button1_a","button1_p");
        button.setText(Assets.getTTF("font3"),"Quit");
        button.setPos(0.3,-0.6);
        button.setGravity(Gravity.CENTER_UP);
        button.setCallback(new ButtonCallback(){
            public void down(){}
            public void pressed(){}
            public void release(){
                System.exit(0);
            }
        });
        layout.addElement("btt3",button);

        button=new Button(0.5,0.15,"button1_n","button1_a","button1_p");
        button.setText(Assets.getTTF("font3"),"Button");
        button.setPos(-0.3,-0.6);
        button.setGravity(Gravity.CENTER_UP);
        layout.addElement("btt4",button);
    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,Main.window.getWidth(),Main.window.getHeight());

        layout.update(Main.mouse,Main.keyboard,Main.window);
        layout.render(sb);
        MouseCursor.render(sb);

        if(Main.keyboard.isKeyReleased(GLFW_KEY_ESCAPE))
            System.exit(0);

        if(Main.keyboard.isKeyReleased(GLFW_KEY_F11))
            Main.window.toggleFullscreen();

        sb.render(cam);
    }


    public void resize(int w,int h){
        cam.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
    }

    public void onSet(){}


}
