package minecraft54.main.client.screens;

import engine54.E54;
import engine54.app.Screen;
import engine54.audio.SoundManager;
import engine54.graphics.FramePostProcessor;
import engine54.graphics.SpriteBatch;
import engine54.graphics.TrueTypeFont;
import engine54.graphics.camera.OrthographicCamera;
import engine54.graphics.texture.TextureRegion;
import engine54.io.Window;
import engine54.maths.Intersector;
import engine54.maths.Maths;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;
import engine54.particles.Particle;
import engine54.particles.ParticleEmitter;
import engine54.physics.BoundingBox3d;
import engine54.ui.Layout;
import engine54.ui.LayoutElement;
import engine54.ui.TouchCallback;
import engine54.util.Assets;
import minecraft54.main.*;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.client.controls.CursorRay;
import minecraft54.main.client.entity.Player;
import minecraft54.main.client.world.*;
import minecraft54.main.server.event.EventListener;
import org.lwjgl.opengl.GL46;

import static minecraft54.main.util.GameMode.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glClearColor;

public class GameScreen implements Screen,EventListener{


    SpriteBatch sb;
    OrthographicCamera cam;
    public static World world;
    public static Player player;
    private boolean showHud=true;
    public static Thread thread1,thread2;
    public ParticleEmitter partBatch;
    public FramePostProcessor postProcessor;

    public Layout layout;
    public boolean pause;


    public void create(){
        partBatch=new ParticleEmitter(10000);
        sb=new SpriteBatch();
        cam=new OrthographicCamera(E54.window());

        //postProcessor=new FramePostProcessor();
        //E54.context().setFramePostProcessor(postProcessor);

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
                E54.mouse().show(false);
                E54.mouse().setPosCenter(E54.window());
                pause=false;
            }
        });
        layout.getElement("btt2").setTouchCallback(new TouchCallback(){
            public void touchOn(LayoutElement current){
                SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);
            }
            public void touched(LayoutElement current){}
            public void touchOff(LayoutElement current){
                E54.context().setScreen("settings","arg");
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
                E54.context().setScreen("world list");
                E54.mouse().show(true);
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
        glClearColor(0.4f,0.7f,1,1);

        cam.update();
        Controls.CAMERA.update();

        world.render();
        partBatch.render(Controls.CAMERA.getProjection(),Matrix4f.lookAt(new Vector3f(Controls.getPosition()),Controls.CAMERA.getRotation().direction()));

        if(showHud){
            TrueTypeFont font=Assets.getTTF("font6");
            sb.setAlpha(0.5f);
            sb.drawText(font,"fps: "+Math.round(E54.getFps())+", vsync: "+E54.window().isVSync(),10,E54.window().getHeight()-10-font.getFontHeight());
            //sb.drawText(font,"tps: "+Math.round(Minecraft54.server.getTps()),10,E54.window().getHeight()-(10+font.getFontHeight())*2);
            sb.drawText(font,"seed: "+world.seed,10,E54.window().getHeight()-(10+font.getFontHeight())*3);
            sb.drawText(font,"pos: "+player.getHitbox().getPosition(),10,E54.window().getHeight()-(10+font.getFontHeight())*4);
            sb.drawText(font,"dir: "+Controls.CAMERA.getRotation().direction(),10,E54.window().getHeight()-(10+font.getFontHeight())*5);
            sb.drawText(font,"world: "+world.name,10,E54.window().getHeight()-(10+font.getFontHeight())*6);
            sb.drawText(font,"game mode: "+player.gameMode(),10,E54.window().getHeight()-(10+font.getFontHeight())*7);
            sb.drawText(font,"sections: "+world.renderSectionsCount,10,E54.window().getHeight()-(10+font.getFontHeight())*8);
            sb.drawText(font,"fov: "+Controls.fov,10,E54.window().getHeight()-(10+font.getFontHeight())*9);
            sb.drawText(font,"sounds: "+SoundManager.sources.size(),10,E54.window().getHeight()-(10+font.getFontHeight())*10);
            sb.drawText(font,"particles: "+partBatch.getParticles().size(),10,E54.window().getHeight()-(10+font.getFontHeight())*11);
            sb.setAlpha(1);

            sb.draw(Assets.getTexture("crosshair"),E54.window().getWidth()/2f-(E54.window().getWidth()/50f/E54.window().getWidth()*E54.window().getHeight()/2),E54.window().getHeight()/2f-(E54.window().getHeight()/50f/2),E54.window().getWidth()/50f/E54.window().getWidth()*E54.window().getHeight(),E54.window().getHeight()/50f);
        }

        if(E54.keyboard().isKeyDown(GLFW_KEY_ESCAPE)){
            pause=!pause;
            E54.mouse().show(pause);
            if(!pause)
                E54.mouse().setPosCenter(E54.window());
        }
        if(E54.keyboard().isKeyDown(GLFW_KEY_F11)){
            Controls.ignoreRotation();
            E54.window().toggleFullscreen();
        }

        if(pause){
            sb.setColor(0,0,0,0.7f);
            sb.draw(Assets.getTexture("white"),0,0,E54.window().getWidth(),E54.window().getHeight());
            sb.resetColor();
            layout.update(E54.mouse(),E54.keyboard(),E54.window());
            layout.render(sb);
        }else
            update();

        sb.render(cam);
    }



    public void update(){
        Controls.rotateCamera(E54.window(),E54.mouse());
        player.controls(E54.keyboard());

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

        if(E54.mouse().getScroll()>0){
            if(barSelect+1<itemBar.length)
                barSelect++;
            else
                barSelect=0;
            blockId=itemBar[barSelect];
        }else if(E54.mouse().getScroll()<0){
            if(barSelect-1>-1)
                barSelect--;
            else
                barSelect=itemBar.length-1;
            blockId=itemBar[barSelect];
        }

        if(E54.keyboard().isKeyPressed(GLFW_KEY_F3) && E54.keyboard().isKeyDown(GLFW_KEY_F4)){
            player.setGameMode(
                    switch(player.gameMode()){
                        case CREATIVE -> SURVIVAL;
                        case SURVIVAL -> SPECTATOR;
                        case SPECTATOR -> ADVENTURE;
                        case ADVENTURE -> CREATIVE;
                    }
            );
        }
        if(E54.keyboard().isKeyReleased(GLFW_KEY_F1))
            showHud=!showHud;

        Vector3d pp=player.getHitbox().getPosition();
        if(E54.keyboard().isKeyPressed(GLFW_KEY_B))
            world.setBlock(Minecraft54.STONE.getId(),pp.xf(),pp.yf()-1,pp.zf(),true);
        Chunk bChunk=world.chunkProvider.getChunk(Maths.floor((float)pp.xf()/Chunk.WIDTH_X),Maths.floor((float)pp.zf()/Chunk.WIDTH_Z));
        if(bChunk!=null)
            bChunk.build();

        for(int[] blockArr:Minecraft54.setBlockStack){
            int id=blockArr[0];
            if(id==0 || !Intersector.aabbAabbNS(player.getHitbox(),new BoundingBox3d(blockArr[2],blockArr[3],blockArr[4],blockArr[2]+1,blockArr[3]+1,blockArr[4]+1))){
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
        //postProcessor.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
        partBatch.dispose();
        if(world!=null){
            world.saveStats(player);
            world.dispose();
        }
        postProcessor.dispose();
    }

    @Override
    public void set(String arg){
        layout.update(E54.mouse(),E54.keyboard(),E54.window());
    }


    public int[] itemBar;
    public int barSelect;
    public int blockId,blockData;

    public void selectedBlock(int x,int y,int z,int side){
        int nx=x+(side==0 ? 1:side==1 ? -1:0);
        int ny=y+(side==2 ? 1:side==3 ? -1:0);
        int nz=z+(side==4 ? 1:side==5 ? -1:0);
        if(E54.mouse().isButtonDown(0)){
            Minecraft54.setBlockStack.add(new int[]{Minecraft54.AIR.getId(),0,x,y,z});
            if(world.getBlockId(x,y+1,z)==Minecraft54.GRASS.getId())
                Minecraft54.setBlockStack.add(new int[]{Minecraft54.AIR.getId(),0,x,y+1,z});
        }if(E54.mouse().isButtonDown(1))
            Minecraft54.setBlockStack.add(new int[]{blockId,blockData,nx,ny,nz});
        if(E54.mouse().isButtonDown(2)){
            blockId=world.getBlockId(x,y,z);
            blockData=world.getBlockData(x,y,z);
        }
    }

    public void playerMoved(Player player){
        if(player.getHitbox().getPosition().y<-50 && player.gameMode()!=SPECTATOR)
            player.getHitbox().getPosition().y=84;
    }


}
