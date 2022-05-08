package minecraft54.main.client.screens;

import engine54.E54;
import engine54.app.Screen;
import engine54.audio.SoundManager;
import engine54.graphics.SpriteBatch;
import engine54.graphics.camera.OrthographicCamera;
import engine54.ui.Layout;
import engine54.ui.LayoutElement;
import engine54.ui.TouchCallback;
import engine54.util.Assets;
import minecraft54.main.Main;
import minecraft54.main.Options;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;

public class ServerListScreen implements Screen{


    SpriteBatch sb;
    OrthographicCamera cam;
    public Layout layout;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(E54.window());

        layout=new Layout();
        layout.load("gui/serverList.json");

        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                //Minecraft54.client.send(new PacketStatusInPing());
            }
        });

        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                //Minecraft54.client.send(new PlayerLogIn(Options.ACCOUNT_NAME));
            }
        });

    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,E54.window().getWidth(),E54.window().getHeight());

        layout.update(E54.mouse(),E54.keyboard(),E54.window());
        layout.render(sb);

        if(E54.keyboard().isKeyDown(GLFW_KEY_ESCAPE))
            E54.context().setScreen("menu");

        if(E54.keyboard().isKeyDown(GLFW_KEY_F11))
            E54.window().toggleFullscreen();

        sb.render(cam);
    }


    public void resize(int w,int h){
        cam.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
    }

    public void set(String arg){
        layout.update(E54.mouse(),E54.keyboard(),E54.window());
    }


}