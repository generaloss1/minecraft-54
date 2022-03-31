package minecraft54.main.client.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.audio.SoundManager;
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
import java.util.Set;

import static org.lwjgl.glfw.GLFW.*;

public class WorldListScreen implements AppScreen{


    SpriteBatch sb;
    OrthographicCamera cam;
    Layout layout;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);
        layout=new Layout();

        layout.load("gui/worldList.json");
        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Main.cfg.setScreen("worldCreate");
            }
        });

    }


    public void updateWorldList(){
        /*((ElementList)layout.getElement("worldList")).clear();

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

                    Layout elementLayout=new Layout();
                    ((ElementList)layout.getElement("worldList")).addElement(elementLayout);

                    Button button=new Button(1,1,"button1_n","button1_a","button1_p");
                    button.setPos(0,0);
                    button.setGravity(Gravity.RIGHT_UP);
                    button.setTouchCallback(new TouchCallback(){
                        public void touchOn(LayoutElement current){
                            SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
                        }
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
                    elementLayout.addElement("ebtt",button);
                    Text text=new Text(button.getWidth(),button.getHeight(),"font3",worldName);
                    text.setPos(button.x(),button.y());
                    text.setGravity(button.getGravity());
                    text.setColor(1,1,1,1);
                    elementLayout.addElement("ebtttxt",text);

                    n++;
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }*/


        for(String id: layout.ids){
            if(id.equals("btt1") || id.equals("btt1txt"))
                continue;
            layout.elements.remove(id);
        }

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

                    Button button=new Button(0.8,0.08,"button1_n","button1_a","button1_p");
                    button.setPos(-0.05,-0.05-0.082*n);
                    button.setGravity(Gravity.RIGHT_UP);
                    button.setTouchCallback(new TouchCallback(){
                        public void touchOn(LayoutElement current){
                            SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
                        }
                        public void touched(LayoutElement current){}
                        public void touchOff(LayoutElement current){
                            //seeds: 12733317, 31184696, 41231155
                            GameScreen.world=new World();
                            GameScreen.world.load(world.getName());
                            GameScreen.world.loadStats(GameScreen.player);
                            Controls.setPosition(GameScreen.player.getHitbox().getPosition().clone().add(GameScreen.player.getEye()));
                            Controls.ignoreRotation();
                            Main.cfg.setScreen("game");
                            if(GameScreen.chunkUpdateThread.getState().equals(Thread.State.NEW))
                                GameScreen.chunkUpdateThread.start();
                        }
                    });
                    layout.addElement("listElement"+i,button);
                    Text text=new Text(button.getWidth(),button.getHeight(),"font3",worldName);
                    text.setPos(button.x(),button.y());
                    text.setGravity(button.getGravity());
                    text.setColor(1,1,1,1);
                    layout.addElement("listElement"+i+"txt",text);
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

    public void onSet(String arg){
        updateWorldList();
        layout.update(Main.mouse,Main.keyboard,Main.window);
    }


}