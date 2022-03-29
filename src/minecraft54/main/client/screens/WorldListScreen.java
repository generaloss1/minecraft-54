package minecraft54.main.client.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.OrthographicCamera;
import minecraft54.engine.graphics.SpriteBatch;
import minecraft54.engine.gui.*;
import minecraft54.engine.gui.Button;
import minecraft54.engine.utils.Assets;
import minecraft54.main.*;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.client.world.World;

import java.io.*;
import java.nio.file.Files;

import static org.lwjgl.glfw.GLFW.*;

public class WorldListScreen implements AppScreen{


    SpriteBatch sb;
    OrthographicCamera cam;
    Layout layout1,layout2;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);
        layout1=new Layout();
        layout2=new Layout();

        Button button=new Button(0.7,0.1,"button1_n","button1_a","button1_p");
        button.setPos(0.05,-0.05);
        button.setGravity(Gravity.LEFT_UP);
        button.setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Main.cfg.setScreen("worldCreate");
            }
        });
        layout2.addElement("btt1",button);
        Text text=new Text(button.getWidth(),button.getHeight(),"font3","Create New World");
        text.setPos(button.x(),button.y());
        text.setGravity(button.getGravity());
        text.setColor(0.55f,0.55f,0.7f,1);
        layout2.addElement("btt1txt",text);

        button=new Button(0.2,0.1,"button1_n","button1_a","button1_p");
        button.setPos(0.05,0.05);
        button.setGravity(Gravity.LEFT_DOWN);
        button.setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Main.cfg.setScreen("menu");
            }
        });
        layout2.addElement("btt2",button);
        text=new Text(button.getWidth(),button.getHeight(),"font3","Back");
        text.setPos(button.x(),button.y());
        text.setGravity(button.getGravity());
        text.setColor(0.55f,0.55f,0.7f,1);
        layout2.addElement("btt2txt",text);
    }


    public void updateWorldList(){
        layout1.elements.clear();

        File saves=new File(Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_FOLDER+"/saves");
        File[] worlds=saves.listFiles();
        if(worlds!=null){
            for(int i=0,n=0; i<worlds.length; i++){

                try{
                    File world=worlds[i];
                    if(!world.isDirectory())
                        continue;
                    File properties=new File(saves+"/"+world.getName()+"/properties");
                    if(!properties.exists())
                        continue;
                    String worldName=Files.readAllLines(properties.toPath()).get(2).split("name: ")[1];

                    Button button=new Button(0.7,0.1,"button1_n","button1_a","button1_p");
                    button.setPos(-0.05,-0.05-0.12*n);
                    button.setGravity(Gravity.RIGHT_UP);
                    button.setTouchCallback(new TouchCallback(){
                        public void touchOn(LayoutElement current){}
                        public void touched(LayoutElement current){}
                        public void touchOff(LayoutElement current){
                            //seeds: 12733317, 31184696, 41231155
                            GameScreen.world=new World();
                            GameScreen.world.load(world.getName());
                            GameScreen.world.loadStats(GameScreen.player);
                            Controls.ignoreRotation();
                            Main.cfg.setScreen("game");
                            if(GameScreen.chunkUpdateThread.getState().equals(Thread.State.NEW))
                                GameScreen.chunkUpdateThread.start();
                        }
                    });
                    layout1.addElement("btt"+i,button);
                    Text text=new Text(button.getWidth(),button.getHeight(),"font3",worldName);
                    text.setPos(button.x(),button.y());
                    text.setGravity(button.getGravity());
                    text.setColor(0.55f,0.55f,0.7f,1);
                    layout1.addElement("btt"+i+"txt",text);
                    n++;
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }
    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,Main.window.getWidth(),Main.window.getHeight());

        layout1.update(Main.mouse,Main.keyboard,Main.window);
        layout1.render(sb);
        layout2.update(Main.mouse,Main.keyboard,Main.window);
        layout2.render(sb);

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

    public void onSet(){
        updateWorldList();
    }


}