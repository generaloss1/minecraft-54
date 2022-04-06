package minecraft54.main.client.screens;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.graphics.*;
import minecraft54.engine.particles.BillboardParticleEmitter;
import minecraft54.engine.particles.Particle;
import minecraft54.engine.ui.Layout;
import minecraft54.engine.ui.LayoutElement;
import minecraft54.engine.ui.TouchCallback;
import minecraft54.engine.io.Window;
import minecraft54.engine.maths.Intersector;
import minecraft54.engine.maths.Maths;
import minecraft54.engine.maths.vectors.Vector3d;
import minecraft54.engine.physics.Aabb;
import minecraft54.engine.util.Assets;
import minecraft54.main.*;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.client.controls.CursorRay;
import minecraft54.main.client.entity.Player;
import minecraft54.main.client.world.*;
import minecraft54.main.server.event.EventListener;
import org.lwjgl.opengl.GL46;

import static minecraft54.main.util.GameMode.*;
import static org.lwjgl.glfw.GLFW.*;

public class GameScreen implements AppScreen, EventListener{


    Renderer renderer;
    SpriteBatch sb;
    OrthographicCamera cam;
    public static World world;
    public static Player player;
    private boolean showHud=true;
    public static Thread thread1,thread2;
    public BillboardParticleEmitter partBatch;

    public Layout layout;
    public boolean pause;


    public void create(){
        partBatch=new BillboardParticleEmitter();
        renderer=new Renderer();
        sb=new SpriteBatch();
        cam=new OrthographicCamera(Main.window);

        player=new Player(Options.ACCOUNT_NAME);
        player.setCanFly(true);
        player.setGameMode(CREATIVE);

        Controls.setPosition(new Vector3d(0,84,0));

        itemBar=new int[]{Minecraft54.GRASS_BLOCK.getId(),Minecraft54.LOG.getId(),Minecraft54.PLANKS.getId(),Minecraft54.COBBLESTONE.getId(),Minecraft54.GLASS.getId(),Minecraft54.GRASS.getId(),Minecraft54.WATER.getId()};
        blockId=itemBar[barSelect];

        //Minecraft54.server.registerEvents(this);

        layout=new Layout();
        layout.load("gui/pause.json");

        layout.getElement("btt1").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Main.mouse.show(false);
                Main.mouse.setPosCenter(Main.window);
                pause=false;
            }
        });
        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                Main.cfg.setScreen("settings","arg");
            }
        });
        layout.getElement("btt3").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                world.saveStats(player);
                world.dispose();
                Main.cfg.setScreen("world list");
                Main.mouse.show(true);
                pause=false;
            }
        });

        thread1=new Thread(()->{
            while(!Thread.interrupted()){
                int playerChunkX=Maths.round(Controls.getPosition().x/Chunk.WIDTH_X);
                int playerChunkZ=Maths.round(Controls.getPosition().z/Chunk.WIDTH_Z);
                for(int i=playerChunkX-Options.RENDER_DISTANCE; i<playerChunkX+Options.RENDER_DISTANCE; i++)
                    for(int j=playerChunkZ-Options.RENDER_DISTANCE; j<playerChunkZ+Options.RENDER_DISTANCE; j++){
                        Chunk chunk=world.chunkProvider.getChunk(i,j);
                        if(chunk==null){
                            if(!world.chunkProvider.loadChunk(i,j))
                                world.generateChunk(i,j);
                        }else
                            world.generateChunk(i,j);
                    }

                for(int i=0; i<world.chunkProvider.loadedChunks.size(); i++){
                    Chunk chunk=world.chunkProvider.loadedChunks.get(i);
                    if(chunk!=null)
                        chunk.build();
                }

                try{
                    Thread.sleep(1000/Window.getRefreshRate());
                }catch(InterruptedException ignored){}

            }
        });

        thread2=new Thread(()->{
            while(!Thread.interrupted()){
                int playerChunkX=Maths.round(Controls.getPosition().x/Chunk.WIDTH_X);
                int playerChunkZ=Maths.round(Controls.getPosition().z/Chunk.WIDTH_Z);
                for(int i=playerChunkX-Options.RENDER_DISTANCE; i<playerChunkX+Options.RENDER_DISTANCE; i++)
                    for(int j=playerChunkZ-Options.RENDER_DISTANCE; j<playerChunkZ+Options.RENDER_DISTANCE; j++){
                        Chunk chunk=world.chunkProvider.getChunk(i,j);
                        if(chunk==null){
                            if(!world.chunkProvider.loadChunk(i,j))
                                world.generateChunk(i,j);
                        }else
                            world.generateChunk(i,j);
                    }

                for(int i=0; i<world.chunkProvider.loadedChunks.size(); i++){
                    Chunk chunk=world.chunkProvider.loadedChunks.get(i);
                    if(chunk!=null)
                        chunk.build();
                }

                try{
                    Thread.sleep(1000/Window.getRefreshRate());
                }catch(InterruptedException ignored){}

            }
        });

    }

    public static void startThreads(){
        if(thread1.getState().equals(Thread.State.NEW))
            thread1.start();
        //if(thread2.getState().equals(Thread.State.NEW))
        //    thread2.start();
    }
    public static void stopThreads(){
        thread1.interrupt();
        //thread2.interrupt();
    }


    public void render(){
        GL46.glClearColor(0.4f,0.7f,1,1);
        cam.update();
        Controls.CAMERA.update();

        world.render();
        partBatch.render(Controls.CAMERA);//.getProjection(),Matrix4.mul(Controls.CAMERA.getRotationMatrix(),new Matrix4().translate(Controls.getPosition().clone().mul(-1))));

        if(showHud){
            TrueTypeFont font=Assets.getTTF("font6");
            sb.setAlpha(0.5f);
            sb.drawText(font,"fps: "+Math.round(Main.cfg.FPS)+", vsync: "+Main.window.isVSync(),10,Main.window.getHeight()-10-font.getFontHeight());
            //sb.drawText(font,"tps: "+Math.round(Minecraft54.server.getTps()),10,Main.window.getHeight()-(10+font.getFontHeight())*2);
            sb.drawText(font,"seed: "+world.seed,10,Main.window.getHeight()-(10+font.getFontHeight())*3);
            sb.drawText(font,"pos: "+player.getHitbox().getPosition(),10,Main.window.getHeight()-(10+font.getFontHeight())*4);
            sb.drawText(font,"dir: "+Controls.CAMERA.getDirection(),10,Main.window.getHeight()-(10+font.getFontHeight())*5);
            sb.drawText(font,"world: "+world.name,10,Main.window.getHeight()-(10+font.getFontHeight())*6);
            sb.drawText(font,"game mode: "+player.gameMode(),10,Main.window.getHeight()-(10+font.getFontHeight())*7);
            sb.drawText(font,"sections: "+world.renderSectionsCount,10,Main.window.getHeight()-(10+font.getFontHeight())*8);
            sb.drawText(font,"fov: "+Controls.fov,10,Main.window.getHeight()-(10+font.getFontHeight())*9);
            sb.drawText(font,"sounds: "+SoundManager.sources.size(),10,Main.window.getHeight()-(10+font.getFontHeight())*10);
            sb.drawText(font,"particles: "+partBatch.getParticles().size(),10,Main.window.getHeight()-(10+font.getFontHeight())*11);
            sb.setAlpha(1);

            sb.draw(Assets.getTexture("crosshair"),Main.window.getWidth()/2f-(Main.window.getWidth()/50f/Main.window.getWidth()*Main.window.getHeight()/2),Main.window.getHeight()/2f-(Main.window.getHeight()/50f/2),Main.window.getWidth()/50f/Main.window.getWidth()*Main.window.getHeight(),Main.window.getHeight()/50f);
        }

        if(Main.keyboard.isKeyDown(GLFW_KEY_ESCAPE)){
            pause=!pause;
            Main.mouse.show(pause);
            if(!pause)
                Main.mouse.setPosCenter(Main.window);
        }
        if(Main.keyboard.isKeyDown(GLFW_KEY_F11)){
            Controls.ignoreRotation();
            Main.window.toggleFullscreen();
        }

        if(pause){
            sb.setColor(0,0,0,0.7f);
            sb.draw(Assets.getTexture("white"),0,0,Main.window.getWidth(),Main.window.getHeight());
            sb.resetColor();
            layout.update(Main.mouse,Main.keyboard,Main.window);
            layout.render(sb);
        }else
            update();

        sb.render(cam);
    }



    public void update(){
        Controls.rotateCamera(Main.window,Main.mouse);
        player.controls(Main.keyboard);

        player.update();
        world.update();
        partBatch.update();

        for(int i=0; i<world.chunkProvider.loadedChunks.size(); i++){
            Chunk chunk=world.chunkProvider.loadedChunks.get(i);
            if(chunk!=null){
                if(!chunk.init)
                    chunk.init();
                chunk.update();
            }
        }

        CursorRay.update(world,Controls.CAMERA);

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

        if(Main.keyboard.isKeyPressed(GLFW_KEY_F3) && Main.keyboard.isKeyDown(GLFW_KEY_F4)){
            player.setGameMode(
                    switch(player.gameMode()){
                        case CREATIVE -> SURVIVAL;
                        case SURVIVAL -> SPECTATOR;
                        case SPECTATOR -> ADVENTURE;
                        case ADVENTURE -> CREATIVE;
                    }
            );
        }
        if(Main.keyboard.isKeyReleased(GLFW_KEY_F1))
            showHud=!showHud;

        Vector3d pp=player.getHitbox().getPosition();
        if(Main.keyboard.isKeyPressed(GLFW_KEY_B))
            world.setBlock(Minecraft54.STONE.getId(),pp.xf(),pp.yf()-1,pp.zf(),true);
        Chunk bChunk=world.chunkProvider.getChunk(Maths.floor((float)pp.xf()/Chunk.WIDTH_X),Maths.floor((float)pp.zf()/Chunk.WIDTH_Z));
        if(bChunk!=null)
            bChunk.build();

        for(int[] blockArr:Minecraft54.setBlockStack){
            int id=blockArr[0];
            if(id==0 || !Intersector.aabbAabbNS(player.getHitbox(),new Aabb(blockArr[2],blockArr[3],blockArr[4],blockArr[2]+1,blockArr[3]+1,blockArr[4]+1))){
                if(id==0){
                    Block block=BlockManager.getBlockFromId(world.getBlockId((short)blockArr[2],(short)blockArr[3],(short)blockArr[4]));
                    if(block!=null){
                        BlockData blockData=block.getBlockData(world.getBlockData((short)blockArr[2],(short)blockArr[3],(short)blockArr[4]));

                        for(int i=0; i<32; i++){
                            Particle particle=new Particle(new TextureRegion(Assets.getTexture("dirt"),Maths.random(0,12/16f),Maths.random(0,12/16f),4/16f,4/16f),new Vector3d(blockArr[2]+Maths.random(0,1f),blockArr[3]+Maths.random(0,1f),blockArr[4]+Maths.random(0,1f)),Maths.random(1000,3000),Maths.random(3/32f,3/16f),Minecraft54.particleBehavior1);
                            particle.getVelocity().get().set(Maths.random(-0.05f,0.05f),Maths.random(0,0.1f),Maths.random(-0.05f,0.05f));
                            partBatch.addParticle(particle);
                        }

                        if(blockData.sounds!=null)
                            blockData.sounds.playDestroy(blockArr[2]+0.5f,blockArr[3]+0.5f,blockArr[4]+0.5f);
                    }
                }else{
                    Block block=BlockManager.getBlockFromId((short)blockArr[0]);
                    if(block!=null){
                        BlockData blockData=block.getBlockData((short)blockArr[1]);
                        if(blockData.sounds!=null)
                            blockData.sounds.playPlace(blockArr[2]+0.5f,blockArr[3]+0.5f,blockArr[4]+0.5f);
                    }
                }
                world.setBlock((short)blockArr[0],(short)blockArr[1],blockArr[2],blockArr[3],blockArr[4],true);
                bChunk=world.chunkProvider.getChunk(Maths.floor((float)blockArr[2]/Chunk.WIDTH_X),Maths.floor((float)blockArr[4]/Chunk.WIDTH_Z));
                if(bChunk!=null)
                    bChunk.build();
            }
        }
        Minecraft54.setBlockStack.clear();
    }



    public void resize(int w,int h){
        cam.resize(w,h);
        Controls.CAMERA.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
        partBatch.dispose();
        if(world!=null){
            world.saveStats(player);
            world.dispose();
        }
    }

    public void onSet(String arg){
        layout.update(Main.mouse,Main.keyboard,Main.window);
    }



    public int[] itemBar;
    public int barSelect;
    public int blockId,blockData;

    public void selectedBlock(int x,int y,int z,int side){
        int nx=x+(side==0 ? 1:side==1 ? -1:0);
        int ny=y+(side==2 ? 1:side==3 ? -1:0);
        int nz=z+(side==4 ? 1:side==5 ? -1:0);
        if(Main.mouse.isButtonDown(0)){
            Minecraft54.setBlockStack.add(new int[]{Minecraft54.AIR.getId(),0,x,y,z});
            if(world.getBlockId(x,y+1,z)==Minecraft54.GRASS.getId())
                Minecraft54.setBlockStack.add(new int[]{Minecraft54.AIR.getId(),0,x,y+1,z});
        }if(Main.mouse.isButtonDown(1))
            Minecraft54.setBlockStack.add(new int[]{blockId,blockData,nx,ny,nz});
        if(Main.mouse.isButtonDown(2)){
            blockId=world.getBlockId(x,y,z);
            blockData=world.getBlockData(x,y,z);
        }
    }

    public void playerMoved(Player player){
        if(player.getHitbox().getPosition().y<-50 && player.gameMode()!=SPECTATOR)
            player.getHitbox().getPosition().y=84;
    }


}
