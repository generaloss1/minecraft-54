package djankcraft.minecraft54.screens;

import djankcraft.engine.app.AppScreen;
import djankcraft.engine.graphics.OrthographicCamera;
import djankcraft.engine.graphics.Renderer;
import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.graphics.TrueTypeFont;
import djankcraft.engine.math.vectors.Vector3;
import djankcraft.engine.utils.Assets;
import djankcraft.minecraft54.*;
import djankcraft.minecraft54.events.EventProcessor;
import djankcraft.minecraft54.events.EventsListener;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

import static org.lwjgl.glfw.GLFW.*;

public class GameScreen implements AppScreen, EventsListener{


    Renderer renderer;
    SpriteBatch sb;
    OrthographicCamera cam;
    public static World world;
    public static Player player;


    public void create(){
        renderer=new Renderer();
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);

        player=new Player(Minecraft54.ACCOUNT_NAME);
        player.setCanFly(true);
        player.setJumpBoost(1);

        Controls.CAMERA.setPosition(new Vector3(0,84,0));

        Minecraft54.tickThread.start();
        EventProcessor.listeners.add(this);
    }


    public void render(){
        GL46.glClearColor(0.4f,0.7f,1,1);
        cam.update();
        Controls.rotateCamera(Main.window,Main.mouse);
        player.controls(Main.keyboard);
        Controls.CAMERA.update();

        player.update();

        world.update();
        world.render();

        TrueTypeFont font=Assets.getTTF("font6");
        sb.setAlpha(0.5f);
        sb.drawText(font,"vsync: "+Main.window.isVSync()+", fps: "+Math.round(Main.cfg.FPS),10,Main.window.getHeight()-10-font.getFontHeight());
        sb.drawText(font,"tps: "+Math.round(Minecraft54.TPS),10,Main.window.getHeight()-(10+font.getFontHeight())*2);
        sb.drawText(font,"seed: "+world.seed,10,Main.window.getHeight()-(10+font.getFontHeight())*3);
        sb.drawText(font,"pos: "+player.getHitbox().getPosition(),10,Main.window.getHeight()-(10+font.getFontHeight())*4);
        sb.drawText(font,"dir: "+Controls.CAMERA.getDirection(),10,Main.window.getHeight()-(10+font.getFontHeight())*5);
        sb.drawText(font,"world: "+world.name,10,Main.window.getHeight()-(10+font.getFontHeight())*6);
        sb.setAlpha(1);

        sb.draw(Assets.getTexture("crosshair"),Main.window.getWidth()/2f-(Main.window.getWidth()/50f/Main.window.getWidth()*Main.window.getHeight()/2),Main.window.getHeight()/2f-(Main.window.getHeight()/50f/2),Main.window.getWidth()/50f/Main.window.getWidth()*Main.window.getHeight(),Main.window.getHeight()/50f);

        sb.render(cam);

        EventProcessor.update(world,Controls.CAMERA);

        if(Main.keyboard.isKeyDown(GLFW.GLFW_KEY_C))
            Controls.CAMERA.setFOV(Minecraft54.FOV/4);
        if(Main.keyboard.isKeyReleased(GLFW.GLFW_KEY_C))
            Controls.CAMERA.setFOV(Minecraft54.FOV);
        if(Main.mouse.getScroll()>0){
            if(barSelect+1<itemBar.length)
                barSelect++;
            else
                barSelect=0;
            blockId=itemBar[barSelect];
        }else if(Main.mouse.getScroll()<0){
            if(barSelect-1>-1)
                barSelect--;
            else
                barSelect=itemBar.length-1;
            blockId=itemBar[barSelect];
        }

        new Thread(()->{
            int playerChunkX=Math.round(Controls.CAMERA.getPosition().x/Chunk.WIDTH_X);
            int playerChunkZ=Math.round(Controls.CAMERA.getPosition().z/Chunk.WIDTH_Z);
            for(int i=playerChunkX-Minecraft54.RENDER_DISTANCE; i<playerChunkX+Minecraft54.RENDER_DISTANCE; i++)
                for(int j=playerChunkZ-Minecraft54.RENDER_DISTANCE; j<playerChunkZ+Minecraft54.RENDER_DISTANCE; j++){
                    Chunk chunk=world.getChunk(i,j);
                    if(chunk==null){
                        if(!world.loadChunk(i,j))
                            world.generateChunk(i,j);
                    }else
                        world.generateChunk(i,j);
                }
        }).start();

        new Thread(()->{
            for(int i=0; i<world.chunks.size(); i++){
                Chunk chunk=world.chunks.get(i);
                if(chunk!=null && chunk.isChanged)
                    chunk.buildMeshes();
            }
        }).start();

        if(Main.keyboard.isKeyReleased(GLFW_KEY_ESCAPE)){
            GameScreen.world.saveStats(player);
            GameScreen.world.dispose();
            Main.cfg.setScreen("world list");
        }

        if(Main.keyboard.isKeyReleased(GLFW_KEY_F11)){
            Controls.ignoreRotation();
            Main.window.toggleFullscreen();
        }
    }

    public void tick(){
        for(int[] block:Minecraft54.setBlockStack)
            world.setBlock((short)block[0],block[1],block[2],block[3]);
        Minecraft54.setBlockStack.clear();
    }


    public void resize(int w,int h){
        cam.resize(w,h);
        Controls.CAMERA.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
        if(world!=null){
            world.dispose();
            world.saveStats(player);
        }
    }

    public void onSet(){}


    public int[] itemBar={Block.PLANKS.id,Block.COBBLESTONE.id,Block.GLASS.id,Block.CHEST.id,Block.OAK_STAIRS.id};
    public int barSelect;
    public int blockId=itemBar[barSelect];

    public void selectedBlock(int x,int y,int z,int side){
        int nx=x+(side==0 ? 1:side==1 ? -1:0);
        int ny=y+(side==2 ? 1:side==3 ? -1:0);
        int nz=z+(side==4 ? 1:side==5 ? -1:0);
        if(Main.mouse.isButtonDown(0)){
            Minecraft54.setBlockStack.add(new int[]{Block.AIR.id,x,y,z});
            if(world.getBlock(x,y+1,z)==Block.GRASS.id)
                Minecraft54.setBlockStack.add(new int[]{Block.AIR.id,x,y+1,z});
        }if(Main.mouse.isButtonDown(1))
            Minecraft54.setBlockStack.add(new int[]{blockId,nx,ny,nz});
        if(Main.mouse.isButtonDown(2))
            blockId=world.getBlock(x,y,z);
    }


}
