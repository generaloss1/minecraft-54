package minecraft54.main.client.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.OrthographicCamera;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.gui.*;
import minecraft54.engine.math.Maths;
import minecraft54.engine.utils.Assets;
import minecraft54.main.Main;
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

        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){

            }
        });

        gamemode=GameMode.values()[0].toString();
        ((Text)layout.getElement("btt2txt")).setText("GameMode: "+gamemode);
        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            int gameModeIndex=0;

            public void touchOn(LayoutElement current){
                gameModeIndex++;
                if(gameModeIndex==GameMode.values().length)
                    gameModeIndex=0;
                gamemode=GameMode.values()[gameModeIndex].toString();
                ((Text)layout.getElement("btt2txt")).setText("GameMode: "+gamemode);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){

            }
        });

        generator=Generator.fromType.get(Generator.fromType.keySet().toArray()[0]).getType();
        ((Text)layout.getElement("btt3txt")).setText("Generator: "+generator);
        layout.getElement("btt3").setTouchCallback(new TouchCallback(){
            int generatorIndex=0;

            public void touchOn(LayoutElement current){
                generatorIndex++;
                if(generatorIndex==Generator.fromType.size())
                    generatorIndex=0;
                generator=Generator.fromType.get(Generator.fromType.keySet().toArray()[generatorIndex]).getType();
                ((Text)layout.getElement("btt3txt")).setText("Generator: "+generator);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){

            }
        });
        layout.getElement("btt4").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                String worldname=((TextField)layout.getElement("btt1txt")).getText();
                if(worldname.length()==0)
                    return;
                GameScreen.player.setGameMode(GameMode.valueOf(gamemode));
                GameScreen.player.getHitbox().getPosition().set(0,128,0);
                GameScreen.world=new World();
                GameScreen.world.create(worldname,Generator.fromType.get(generator),Maths.randomSeed(8));
                Controls.ignoreRotation();
                Main.cfg.setScreen("game");
                if(GameScreen.chunkUpdateThread.getState().equals(Thread.State.NEW))
                    GameScreen.chunkUpdateThread.start();
            }
        });
    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,Main.window.getWidth(),Main.window.getHeight());

        layout.update(Main.mouse,Main.keyboard,Main.window);
        layout.render(sb);

        if(Main.keyboard.isKeyReleased(GLFW_KEY_ESCAPE))
            Main.cfg.setScreen("world list");

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
