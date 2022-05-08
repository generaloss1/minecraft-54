package minecraft54.main.client.controls;

import engine54.E54;
import engine54.graphics.camera.PerspectiveCamera;
import engine54.maths.Intersector;
import engine54.maths.Maths;
import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;
import minecraft54.main.Main;
import minecraft54.main.Minecraft54;
import minecraft54.main.client.screens.GameScreen;
import minecraft54.main.client.world.World;

import java.util.ArrayList;
import java.util.List;

public class CursorRay{


    public static int rayLength=5;


    public static List<Short> rayIgnoreIds=new ArrayList<>();
    public static List<Short> rayIgnoreIds2=new ArrayList<>();
    static{
        rayIgnoreIds.add((short)-1);
        rayIgnoreIds.add(Minecraft54.AIR.getId());

        rayIgnoreIds2.add(Minecraft54.WATER.getId());
        rayIgnoreIds2.add(Minecraft54.WATER_STILL.getId());
    }


    public static void update(World world,PerspectiveCamera cam){
        Vector3d dir=new Vector3d(cam.getRotation().direction());
        for(float i=0.75f; i<rayLength; i+=0.001f){
            Vector3f pos=new Vector3f(dir).mul(i).add(Controls.getPosition());
            int x=Maths.floor(pos.x);
            int y=Maths.floor(pos.y);
            int z=Maths.floor(pos.z);
            short id=world.getBlockId(x,y,z);
            if(!rayIgnoreIds.contains(id) && !rayIgnoreIds2.contains(id)){
                Vector3d line=dir.clone().mul(rayLength).add(Controls.getPosition());

                Vector3d px=Intersector.lineQuad(Controls.getPosition().clone(),line, x+1,y+1,z+1,  x+1,y  ,z+1,  x+1,y  ,z  ,  x+1,y+1,z   );
                Vector3d mx=Intersector.lineQuad(Controls.getPosition().clone(),line, x  ,y+1,z+1,  x  ,y  ,z+1,  x  ,y  ,z  ,  x  ,y+1,z   );
                Vector3d py=Intersector.lineQuad(Controls.getPosition().clone(),line, x+1,y+1,z+1,  x+1,y+1,z  ,  x  ,y+1,z  ,  x  ,y+1,z+1 );
                Vector3d my=Intersector.lineQuad(Controls.getPosition().clone(),line, x+1,y  ,z+1,  x+1,y  ,z  ,  x  ,y  ,z  ,  x  ,y  ,z+1 );
                Vector3d pz=Intersector.lineQuad(Controls.getPosition().clone(),line, x  ,y+1,z+1,  x  ,y  ,z+1,  x+1,y  ,z+1,  x+1,y+1,z+1 );
                Vector3d mz=Intersector.lineQuad(Controls.getPosition().clone(),line, x  ,y+1,z  ,  x  ,y  ,z  ,  x+1,y  ,z  ,  x+1,y+1,z   );

                Vector3d[] points={
                        new Vector3d(x+1,y+0.5,z+0.5),new Vector3d(x,y+0.5,z+0.5),new Vector3d(x+0.5,y+1,z+0.5),new Vector3d(x+0.5,y,z+0.5),new Vector3d(x+0.5,y+0.5,z+1),new Vector3d(x+0.5,y+0.5,z)
                };

                boolean[] sides={
                        px!=null,mx!=null,py!=null,my!=null,pz!=null,mz!=null
                };

                int n=0;
                for(boolean s:sides)
                    if(s)
                        n++;

                int side=0;
                if(n==1){
                    for(int j=0; j<6; j++){
                        if(sides[j]){
                            side=j;
                            break;
                        }
                    }
                }else{
                    for(int q=0; q<6; q++){
                        for(int t=0; t<6; t++){
                            if(sides[q] && sides[t] && q!=t){
                                double dq=points[q].dst(Controls.getPosition());
                                double dt=points[t].dst(Controls.getPosition());
                                if(dq<dt)
                                    side=q;
                                else
                                    side=t;

                                q=6;
                                break;
                            }
                        }
                    }
                }

                ((GameScreen)E54.context().getScreen("game")).selectedBlock(x,y,z,side);

                break;
            }
        }
    }


}
