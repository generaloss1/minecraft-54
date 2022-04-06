package minecraft54.main.client.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.graphics.OrthographicCamera;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.ui.*;
import minecraft54.engine.maths.Maths;
import minecraft54.engine.util.Assets;
import minecraft54.main.Main;
import minecraft54.main.Options;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.client.world.World;
import minecraft54.main.server.generator.Generator;
import minecraft54.main.util.GameMode;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;

public class WorldCreateScreen implements AppScreen{


    SpriteBatch sb;
    OrthographicCamera cam;
    public Layout layout;

    String gamemode,generator;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);

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
                Main.cfg.setScreen("game");
                GameScreen.startThreads();
            }
        });
    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,Main.window.getWidth(),Main.window.getHeight());

        layout.update(Main.mouse,Main.keyboard,Main.window);
        layout.render(sb);

        if(Main.keyboard.isKeyDown(GLFW_KEY_ESCAPE))
            Main.cfg.setScreen("world list");

        if(Main.keyboard.isKeyDown(GLFW_KEY_F11))
            Main.window.toggleFullscreen();

        sb.render(cam);
    }


    public void resize(int w,int h){
        cam.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
    }

    public void onSet(String arg){
        layout.update(Main.mouse,Main.keyboard,Main.window);
    }

}
