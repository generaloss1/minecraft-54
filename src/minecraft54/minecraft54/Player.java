package minecraft54.minecraft54;

import minecraft54.engine.io.Keyboard;
import minecraft54.engine.math.vectors.Vector3;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;

public class Player extends Entity{


    public String name;
    private boolean canFly;
    private boolean flying;
    private GameMode gameMode;


    public Player(String name){
        super(new Vector3(-0.3,0,-0.3),new Vector3(0.3,1.8,0.3),new Vector3(0,1.62,0));
        this.name=name;
        setSpeed(4.3);
    }


    public void controls(Keyboard keyboard){
        double cam_speed=getVelocity().max()/10;
        Vector3 controlMoveVel=Controls.CAMERA.getDefaultMove(new Vector3(
                keyboard.isKeyPressed(GLFW_KEY_W)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_S)?-cam_speed:0,
                flying?(keyboard.isKeyPressed(GLFW_KEY_SPACE) ? cam_speed:keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) ? -cam_speed:0):0,
                keyboard.isKeyPressed(GLFW_KEY_D)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_A)?-cam_speed:0
        ));

        if(isOnGround() && gameMode!=GameMode.SPECTATOR){
            if(flying)
                flying=false;

            if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)){
                setSpeed(1.3);
                getEye().y=getEye().y>1.495f?getEye().y-0.03f:1.495f;

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
                getEye().y=getEye().y<1.62f?getEye().y+0.03f:1.62f;
            }else{
                setSpeed(4.3);
                getEye().y=getEye().y<1.62f?getEye().y+0.03f:1.62f;
            }

            if(keyboard.isKeyPressed(GLFW_KEY_SPACE))
                jump();
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

        if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL) && getVelocity().get().x!=0 && getVelocity().get().z!=0)
            Controls.CAMERA.setFOV(Controls.CAMERA.getFOV()<Minecraft54.FOV+8?Controls.CAMERA.getFOV()+1:Minecraft54.FOV+8);
        else
            Controls.CAMERA.setFOV(Controls.CAMERA.getFOV()>Minecraft54.FOV?Controls.CAMERA.getFOV()-1:Minecraft54.FOV);
    }


    public void setSpeed(double speed){
        getVelocity().setMax((float)speed);
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


}
