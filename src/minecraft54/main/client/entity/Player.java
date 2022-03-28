package minecraft54.main.client.entity;

import minecraft54.engine.io.Keyboard;
import minecraft54.engine.math.vectors.Vector3d;
import minecraft54.engine.math.vectors.Vector3f;
import minecraft54.engine.utils.Timer;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.Main;
import minecraft54.main.Minecraft54;
import minecraft54.main.client.screens.GameScreen;
import minecraft54.main.client.world.Block;
import minecraft54.main.client.world.BlockData;
import minecraft54.main.client.world.BlockManager;
import minecraft54.main.util.GameMode;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;

public class Player extends Entity{


    private final String name;
    private boolean canFly;
    private boolean flying;
    private GameMode gameMode;
    private final Timer stepTimer;
    private float maxSpeed;


    public Player(String name){
        super(new Vector3f(-0.3,0,-0.3),new Vector3f(0.3,1.8,0.3),new Vector3f(0,1.62,0));
        this.name=name;
        setSpeed(4.3);
        stepTimer=new Timer().start().setSeconds(1);
    }


    public void controls(Keyboard keyboard){
        double cam_speed=getVelocity().max()/10;
        Vector3f controlMoveVel=Controls.CAMERA.getDefaultMove(new Vector3f(
                keyboard.isKeyPressed(GLFW_KEY_W)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_S)?-cam_speed:0,
                flying?(keyboard.isKeyPressed(GLFW_KEY_SPACE) ? cam_speed:keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) ? -cam_speed:0):0,
                keyboard.isKeyPressed(GLFW_KEY_D)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_A)?-cam_speed:0
        ));

        if(isOnGround() && gameMode!=GameMode.SPECTATOR){
            if(flying)
                flying=false;

            if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
                setSpeed(1.3);
                getEye().y=getEye().y>1.3f?getEye().y-0.04f:1.3f; //1.32
                getHitbox().getB().set(0.3,1.3,0.3); //1.3

                if(!isOnGround(controlMoveVel.x,0,0)){
                    controlMoveVel.x=0;
                    getVelocity().get().x=0;
                }
                if(!isOnGround(0,0,controlMoveVel.z)){
                    controlMoveVel.z=0;
                    getVelocity().get().z=0;
                }

            }else if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL)){
                setSpeed(5.6);
                getEye().y=getEye().y<1.62f?getEye().y+0.04f:1.62f;
                getHitbox().getB().set(0.3,1.8,0.3);
            }else{
                setSpeed(4.3);
                getEye().y=getEye().y<1.62f?getEye().y+0.04f:1.62f;
                getHitbox().getB().set(0.3,1.8,0.3);
            }

            if(keyboard.isKeyPressed(GLFW_KEY_SPACE)){
                jump();
                stepTimer.setSeconds(1);
            }

            // Step Sounds
            if(getVelocity().get().len()!=0){
                Vector3d pos=getHitbox().getPosition();
                Block floorBlock=BlockManager.getBlockFromId(GameScreen.world.getBlockId(pos.xf(),pos.yf()-1,pos.zf()));
                if(floorBlock!=null){
                    BlockData floorBlockData=floorBlock.getBlockData(GameScreen.world.getBlockData(pos.xf(),pos.yf()-1,pos.zf()));
                    if(floorBlockData!=null && floorBlockData.sounds!=null && stepTimer.getMillis()>1600*(1/getVelocity().get().len())){
                        floorBlockData.sounds.playStep();
                        stepTimer.reset();
                    }
                }
            }
        }

        if(isJumping())
            controlMoveVel.div(2f);

        if(flying){
            setNoGravity(true);
            if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
                setSpeed(22);
            else
                setSpeed(4.3);
        }else{
            setNoGravity(false);
            if(keyboard.isKeyDown(GLFW_KEY_F) && isCanFly())
                flying=true;
        }

        getVelocity().get().add(controlMoveVel);

        Controls.setPosition(getHitbox().getPosition().clone().add(getEye()));

        if(!Main.keyboard.isKeyPressed(GLFW_KEY_C)){
            if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL) && getVelocity().get().x!=0 && getVelocity().get().z!=0)
                Controls.interpolateFov(Minecraft54.FOV+10);
            else
                Controls.interpolateFov(Minecraft54.FOV);
        }
        if(Main.keyboard.isKeyDown(GLFW_KEY_C))
            Controls.fov=Minecraft54.FOV/4;
        else if(Main.keyboard.isKeyReleased(GLFW_KEY_C))
            Controls.fov=Minecraft54.FOV;
        if(Main.keyboard.isKeyDown(org.lwjgl.glfw.GLFW.GLFW_KEY_MINUS)){
            float f=Controls.fov-5;
            Controls.interpolateFov(f);
            Minecraft54.FOV=f;
        }
        if(Main.keyboard.isKeyDown(GLFW.GLFW_KEY_EQUAL)){
            float f=Controls.fov+5;
            Controls.interpolateFov(f);
            Minecraft54.FOV=f;
        }

        ((GameScreen)Main.cfg.getScreen("game")).playerMoved(this);
    }


    public void setSpeed(double speed){
        getVelocity().setMax(speed);
    }

    public void setGameMode(GameMode gameMode){
        if(this.gameMode==gameMode)
            return;
        this.gameMode=gameMode;

        if(gameMode==GameMode.SPECTATOR){
            setNoClip(true);
            setFlying(true);
            setCanFly(true);
        }else{
            setNoClip(false);
            if(gameMode==GameMode.CREATIVE){
                setCanFly(true);
            }else if(gameMode==GameMode.SURVIVAL || gameMode==GameMode.ADVENTURE){
                setCanFly(false);
            }
        }
    }

    public GameMode gameMode(){
        return gameMode;
    }


    public boolean isCanFly(){
        return canFly;
    }
    public void setCanFly(boolean canFly){
        this.canFly=canFly;
        if(!canFly && flying)
            flying=false;
    }

    public boolean isFlying(){
        return flying;
    }
    public void setFlying(boolean flying){
        this.flying=flying;
        setNoGravity(flying);
    }

    public String getName(){
        return name;
    }


}
