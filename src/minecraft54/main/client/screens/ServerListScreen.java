package minecraft54.main.client.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.OrthographicCamera;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.gui.Layout;
import minecraft54.engine.gui.LayoutElement;
import minecraft54.engine.gui.TouchCallback;
import minecraft54.engine.utils.Assets;
import minecraft54.main.Main;
import minecraft54.main.Minecraft54;
import minecraft54.main.packets.PacketStatusInPing;
import minecraft54.main.packets.PlayerLogIn;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;

public class ServerListScreen implements AppScreen{


    SpriteBatch sb;
    OrthographicCamera cam;
    public Layout layout;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);

        layout=new Layout();
        layout.load("gui/serverList.json");

        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Minecraft54.getClient().send(new PacketStatusInPing());
            }
        });

        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Minecraft54.getClient().send(new PlayerLogIn(Minecraft54.ACCOUNT_NAME));
            }
        });

    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,Main.window.getWidth(),Main.window.getHeight());

        layout.update(Main.mouse,Main.keyboard,Main.window);
        layout.render(sb);

        if(Main.keyboard.isKeyReleased(GLFW_KEY_ESCAPE))
            Main.cfg.setScreen("menu");

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