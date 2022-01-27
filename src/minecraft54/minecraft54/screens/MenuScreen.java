package minecraft54.minecraft54.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.OrthographicCamera;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.gui.*;
import minecraft54.minecraft54.Main;
import minecraft54.minecraft54.MouseCursor;

import static org.lwjgl.glfw.GLFW.*;

public class MenuScreen implements AppScreen{


    SpriteBatch sb;
    OrthographicCamera cam;
    Layout layout;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);

        layout=new Layout();
        layout.load("gui/menu.json");

        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Main.cfg.setScreen("world list");
            }
        });
        layout.getElement("btt3").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                System.exit(0);
            }
        });
    }


    public void render(){
        cam.update();

        //sb.draw(Assets.getTexture("background"),0,0,Main.window.getWidth(),Main.window.getHeight());

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
