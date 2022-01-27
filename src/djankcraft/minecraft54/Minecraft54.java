package djankcraft.minecraft54;

import djankcraft.engine.app.AppListener;
import djankcraft.engine.graphics.*;
import djankcraft.engine.utils.Assets;
import djankcraft.engine.utils.Color;
import djankcraft.engine.utils.Utils;
import djankcraft.minecraft54.screens.GameScreen;
import djankcraft.minecraft54.screens.MenuScreen;
import djankcraft.minecraft54.screens.WorldListScreen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Minecraft54 implements AppListener{


    public static float FOV=110;
    public static int TICK_RATE=20;
    public static Color CURSOR_COLOR_1=new Color(0,0.6f,0.2f,1);
    public static Color CURSOR_COLOR_2=new Color(0.75f,1,0.81f,1);
    public static int RENDER_DISTANCE=3;
    public static String ACCOUNT_NAME="GeneralPashon";

    public static String GAME_PATH=".minecraft54";
    public static String HOME_PATH=System.getProperty("user.home");


    public static Thread tickThread;
    public static float TPS;


    public static List<int[]> setBlockStack=new ArrayList<>();


    @Override
    public void create(){
        { // Create Files
            try{

                File saves=new File(HOME_PATH+"/"+GAME_PATH+"/saves");
                if(!saves.exists())
                    saves.mkdirs();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        { // Load Textures
            Assets.loadTexture(new Texture(new PixmapRGB(1,1).setPixel(0,0,1,1,1)),"white");
            Assets.loadTexture("textures/ui/cursor_0.png","cursor0");
            Assets.loadTexture("textures/ui/cursor_1.png","cursor1");
            Assets.loadTexture("textures/ui/crosshair.png","crosshair");

            Assets.loadTexture("textures/ui/button1.png","button1_n");
            Assets.loadTexture("textures/ui/button1_selected.png","button1_a");
            Assets.loadTexture("textures/ui/button1_pressed.png","button1_p");
            Assets.loadTexture("textures/ui/background.png","background");

            String blocks="textures/blocks3/";
            Assets.loadTexture3d(new Texture3D(16,16,
                    blocks+"grass_block_side.png", // 0
                    blocks+"grass_block_top.png",
                    blocks+"dirt.png",
                    blocks+"stone.png",
                    blocks+"sand.png",
                    blocks+"oak_log.png",
                    blocks+"oak_log_top.png",
                    blocks+"oak_leaves.png",
                    blocks+"birch_log.png",
                    blocks+"birch_log_top.png",
                    blocks+"glass.png",            // 10
                    blocks+"grass.png",
                    blocks+"cobblestone.png",
                    blocks+"water_still.png",
                    blocks+"oak_planks.png",
                    blocks+"chest_front.png",
                    blocks+"chest_side.png",
                    blocks+"chest_top.png",
                    blocks+"chest_back.png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,               // 20
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"/*,
                    blocks+".png"*/
            ),"blocks");
        }

        { // Tests
            /*
            int lside=Maths.random(0,5);
            int lx=Maths.random(0,15);
            int ly=Maths.random(0,255);
            int lz=Maths.random(0,15);

            System.out.println(lx+", "+ly+", "+lz+", "+lside);

            int pos=lx*6*Chunk.WIDTH_Z*Chunk.HEIGHT+ly*6*Chunk.WIDTH_Z+lz*6+lside;

            int side=pos%6;
            int z=(pos-side)/6%Chunk.WIDTH_Z;
            int y=(pos-z*6-side)/6/Chunk.WIDTH_Z%Chunk.HEIGHT;
            int x=(pos-y*6*Chunk.WIDTH_Z-z*6-side)/6/Chunk.WIDTH_Z/Chunk.HEIGHT;

            System.out.println(x+", "+y+", "+z+", "+side);
            */
        }

        { // Load TTFs
            int size=64;

            TrueTypeFont ttf1=new TrueTypeFont("fonts/andy_bold.ttf",size);
            Assets.loadTTF(ttf1,"font1");
            TrueTypeFont ttf2=new TrueTypeFont("fonts/digit.ttf",size);
            Assets.loadTTF(ttf2,"font2");
            TrueTypeFont ttf3=new TrueTypeFont("fonts/minecraft.ttf",40);
            Assets.loadTTF(ttf3,"font3");
            TrueTypeFont ttf4=new TrueTypeFont("fonts/trashco.ttf",32);
            Assets.loadTTF(ttf4,"font4");
            TrueTypeFont ttf5=new TrueTypeFont("fonts/unreal_tournament.ttf",32);
            Assets.loadTTF(ttf5,"font5");
            TrueTypeFont ttf6=new TrueTypeFont("fonts/minecraft.ttf",16);
            Assets.loadTTF(ttf6,"font6");
        }

        { // Load Shaders
            ShaderProgram chunkShader=new ShaderProgram(Utils.readFile("shaders/Chunk.vert"),Utils.readFile("shaders/Chunk.frag"),Utils.readFile("shaders/Chunk.geom"));
            chunkShader.addUniforms("u_texture","u_world","u_proj","u_model","underWater","u_camPos");
            Assets.loadShader(chunkShader,"chunk");
        }

        { // Configure Screens
            Main.cfg.addScreen("menu",new MenuScreen());
            Main.cfg.addScreen("world list",new WorldListScreen());
            Main.cfg.addScreen("game",new GameScreen());
            Main.cfg.setScreen("menu");
        }

        { // Tick Cycle
            tickThread=new Thread(()->{
                long prevTime=System.nanoTime();
                while(!Thread.currentThread().isInterrupted()){
                    TPS=-1000000000f/(prevTime-(prevTime=System.nanoTime()));
                    ((GameScreen)Main.cfg.getScreen("game")).tick();
                    try{
                        Thread.sleep(1000/TICK_RATE);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @Override
    public void update(){
        if(Main.keyboard.isKeyReleased(GLFW_KEY_V))
            Main.window.setVSync(!Main.window.isVSync());
    }


    @Override
    public void dispose(){
        Assets.dispose();
    }

}
