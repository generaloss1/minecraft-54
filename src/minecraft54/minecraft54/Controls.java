package minecraft54.minecraft54;

import minecraft54.engine.graphics.PerspectiveCamera;
import minecraft54.engine.io.Mouse;
import minecraft54.engine.io.Window;
import minecraft54.engine.math.vectors.Vector3;
import minecraft54.engine.math.vectors.Vector3d;

public class Controls{


    public static PerspectiveCamera CAMERA=new PerspectiveCamera(Main.window.getWidth()/2,Main.window.getHeight()/2,0.1f,1000f,Minecraft54.FOV);
    public static float SENSIVITY=80;
    private static boolean ignoreRotation;
    private static final Vector3d position=new Vector3d();


    public static void ignoreRotation(){
        ignoreRotation=true;
    }


    public static void rotateCamera(Window window,Mouse mouse){
        CAMERA.getPosition().set(0,(float)position.y,0);

        if(window.isFocused()){
            int x=mouse.getX();
            int y=mouse.getY();
            int halfW=Math.round(window.getWidth()/2f);
            int halfH=Math.round(window.getHeight()/2f);

            float dx=halfW-x;
            float dy=halfH-y;
            float rotX=dx/window.getWidth()*SENSIVITY;
            float rotY=dy/window.getHeight()*SENSIVITY;

            if(mouse.isVisible()){
                mouse.hide();
                ignoreRotation=true;
            }

            if(ignoreRotation){
                ignoreRotation=false;
                rotX=0;
                rotY=0;
            }

            mouse.setPos(
                    rotX==0?x:halfW,
                    rotY==0?y:halfH
            );

            CAMERA.rotate(rotY,rotX,0);
            CAMERA.getRotation().constrain();

        }else if(!mouse.isVisible())
            mouse.show();
    }



    public static void setPosition(Vector3d pos){
        position.set(pos);
    }

    public static Vector3d getPosition(){
        return position;
    }


}
