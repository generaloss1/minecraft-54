package djankcraft.minecraft54.screens;

import djankcraft.engine.app.AppScreen;
import djankcraft.engine.graphics.OrthographicCamera;
import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.gui.*;
import djankcraft.engine.math.Maths;
import djankcraft.engine.utils.Assets;
import djankcraft.minecraft54.*;
import djankcraft.minecraft54.generator.Generator;

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

        Button button=new Button(0.7,0.15,"button1_n","button1_a","button1_p");
        button.setPos(0.05,-0.05);
        button.setGravity(Gravity.LEFT_UP);
        button.setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){}
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                GameScreen.world=new World();
                GameScreen.world.create("New World",Generator.NORMAL,Maths.randomSeed(8));
                updateWorldList();
            }
        });
        layout2.addElement("btt1",button);
        Text text=new Text(button.getWidth(),button.getHeight(),"font3","Create New World");
        text.setPos(button.x(),button.y());
        text.setGravity(button.getGravity());
        text.setColor(0.55f,0.55f,0.7f,1);
        layout2.addElement("btt1txt",text);

        button=new Button(0.2,0.15,"button1_n","button1_a","button1_p");
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

        File saves=new File(Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_PATH+"/saves");
        File[] worlds=saves.listFiles();
        if(worlds!=null){
            for(int i=0; i<worlds.length; i++){

                try{
                    File world=worlds[i];
                    File properties=new File(saves+"/"+world.getName()+"/properties");
                    String worldName=Files.readAllLines(properties.toPath()).get(2).split("name: ")[1];

                    Button button=new Button(0.7,0.1,"button1_n","button1_a","button1_p");
                    button.setPos(-0.05,-0.05-0.12*i);
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
                        }
                    });
                    layout1.addElement("btt"+i,button);
                    Text text=new Text(button.getWidth(),button.getHeight(),"font3",worldName);
                    text.setPos(button.x(),button.y());
                    text.setGravity(button.getGravity());
                    text.setColor(0.55f,0.55f,0.7f,1);
                    layout1.addElement("btt"+i+"txt",text);
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
        MouseCursor.render(sb);

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