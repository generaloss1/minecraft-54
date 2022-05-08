package minecraft54.main.client.screens;

import engine54.E54;
import engine54.app.Screen;
import engine54.audio.SoundManager;
import engine54.graphics.SpriteBatch;
import engine54.graphics.camera.OrthographicCamera;
import engine54.ui.*;
import engine54.util.Assets;
import minecraft54.main.*;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.client.world.World;

import java.io.*;
import java.nio.file.Files;

import static org.lwjgl.glfw.GLFW.*;

public class WorldListScreen implements Screen{


    SpriteBatch sb;
    OrthographicCamera cam;
    Layout layout;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(E54.window());
        layout=new Layout();

        layout.load("gui/worldList.json");
        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                E54.context().setScreen("worldCreate");
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
                            E54.context().setScreen("game");
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
                            E54.context().setScreen("game");
                            GameScreen.startThreads();
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
        updateWorldList();
        layout.update(E54.mouse(),E54.keyboard(),E54.window());
    }


}