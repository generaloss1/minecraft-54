package minecraft54.main.client.screens;


import engine54.E54;
import engine54.app.Screen;
import engine54.audio.SoundManager;
import engine54.graphics.SpriteBatch;
import engine54.graphics.camera.OrthographicCamera;
import engine54.maths.Maths;
import engine54.ui.*;
import engine54.util.Assets;
import minecraft54.main.Main;
import minecraft54.main.Options;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.client.world.World;
import minecraft54.main.server.generator.Generator;
import minecraft54.main.util.GameMode;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;

public class WorldCreateScreen implements Screen{


    SpriteBatch sb;
    OrthographicCamera cam;
    public Layout layout;

    String gamemode,generator;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(E54.window());

        layout=new Layout();
        layout.load("gui/worldCreate.json");

        gamemode=GameMode.values()[0].toString();
        ((Text)layout.getElement("btt1txt")).setText("GameMode: "+gamemode);
        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            int gameModeIndex=0;

            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);

                gameModeIndex++;
                if(gameModeIndex==GameMode.values().length)
                    gameModeIndex=0;
                gamemode=GameMode.values()[gameModeIndex].toString();
                ((Text)layout.getElement("btt1txt")).setText("GameMode: "+gamemode);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){

            }
        });

        generator=Generator.fromType.get(Generator.fromType.keySet().toArray()[0]).getType();
        ((Text)layout.getElement("btt2txt")).setText("Generator: "+generator);
        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            int generatorIndex=0;

            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);

                generatorIndex++;
                if(generatorIndex==Generator.fromType.size())
                    generatorIndex=0;
                generator=Generator.fromType.get(Generator.fromType.keySet().toArray()[generatorIndex]).getType();
                ((Text)layout.getElement("btt2txt")).setText("Generator: "+generator);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){

            }
        });
        layout.getElement("btt3").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                String worldname=((TextField)layout.getElement("txtfld")).getText();
                if(worldname.length()==0)
                    return;
                GameScreen.player.setGameMode(GameMode.valueOf(gamemode));
                GameScreen.player.getHitbox().getPosition().set(0,128,0);
                Controls.setPosition(GameScreen.player.getHitbox().getPosition().clone().add(GameScreen.player.getEye()));
                GameScreen.world=new World();
                GameScreen.world.create(worldname,Generator.fromType.get(generator),Maths.randomSeed(8));
                Controls.ignoreRotation();
                E54.context().setScreen("game");
                GameScreen.startThreads();
            }
        });
    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,E54.window().getWidth(),E54.window().getHeight());

        layout.update(E54.mouse(),E54.keyboard(),E54.window());
        layout.render(sb);

        if(E54.keyboard().isKeyDown(GLFW_KEY_ESCAPE))
            E54.context().setScreen("world list");

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
