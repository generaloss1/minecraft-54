package djankcraft.minecraft54;

import djankcraft.engine.graphics.PerspectiveCamera;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;
import djankcraft.engine.math.vectors.Vector3;
import djankcraft.engine.physics.Collider;
import djankcraft.engine.physics.CubeHitbox;
import djankcraft.minecraft54.screens.GameScreen;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Controls{


    public static PerspectiveCamera CAMERA=new PerspectiveCamera(Main.window.getWidth()/2,Main.window.getHeight()/2,0.1f,1000f,Minecraft54.FOV);
    public static float SENSIVITY=80;
    private static boolean ignoreRotation;
    private static int mouseX,mouseY,oldMouseX,oldMouseY=-1;


    public static void ignoreRotation(){
        ignoreRotation=true;
    }


    public static void update(Window window,Mouse mouse,Keyboard keyboard){
        float vsyncFPS=Window.getRefreshRate();
        float unit=Main.cfg.DELTA_TIME*vsyncFPS;

        if(window.isFocused()){
            if(oldMouseY==-1){
                oldMouseX=window.getWidth()/2;
                oldMouseY=window.getHeight()/2;
                mouse.setPos(window.getWidth()/2,window.getHeight()/2);
            }
            if(mouse.isVisible())
                mouse.hide();
            mouseX=mouse.getX();
            mouseY=mouse.getY();
            float dx=oldMouseX-mouseX;
            float dy=oldMouseY-mouseY;
            if(ignoreRotation){
                dx=0;
                dy=0;
                ignoreRotation=false;
            }
            oldMouseX=window.getWidth()/2;
            oldMouseY=window.getHeight()/2;
            CAMERA.rotate(dy*SENSIVITY/1800,dx*SENSIVITY/1800,0);
            mouse.setPos(window.getWidth()/2,window.getHeight()/2);
            CAMERA.getRotation().constrain();
        }else if(!mouse.isVisible()){
            oldMouseY=-1;
            mouse.show();
        }


        Player player=GameScreen.player;


        if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT) && player.isOnGround())
            player.setSpeed(1.3);
        else player.setSpeed(4.3);

        if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL)){
            if(player.isOnGround())
                player.setSpeed(5.6);
            else player.setSpeed(22);
        }

        float cam_speed=player.velocity.max()/10;

        player.velocity.add(Controls.CAMERA.getDefaultMove(new Vector3(
                keyboard.isKeyPressed(GLFW_KEY_W)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_S)?-cam_speed:0,
                keyboard.isKeyPressed(GLFW_KEY_SPACE)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)&&!player.isOnGround()?-cam_speed:0,
                keyboard.isKeyPressed(GLFW_KEY_D)?cam_speed:keyboard.isKeyPressed(GLFW_KEY_A)?-cam_speed:0
        )));
        player.velocity.clamp();
        player.velocity.reduce(player.velocity.max()/20);

        player.hitbox.move(player.velocity.get().clone().mul(unit/Window.getRefreshRate()));

        List<CubeHitbox> blockList=player.hitboxList();
        Vector3 collidedMove=Collider.getCollidedMove(player.hitbox,blockList);
        player.hitbox.getPosition().add(collidedMove);
        player.velocity.collidedAxesToZero(collidedMove);


        Controls.CAMERA.setPosition(player.hitbox.getPosition().clone().add(0,1.62,0));
    }


}
