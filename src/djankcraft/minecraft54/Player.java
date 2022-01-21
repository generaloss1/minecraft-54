package djankcraft.minecraft54;

import djankcraft.engine.math.vectors.Vector3;
import djankcraft.engine.physics.Collider;
import djankcraft.engine.physics.CubeHitbox;
import djankcraft.minecraft54.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Player{


    public String name;
    public CubeHitbox hitbox;
    public Velocity velocity;


    public Player(String name){
        this.name=name;
        hitbox=new CubeHitbox(new Vector3(-0.3,0,-0.3),new Vector3(0.3,1.8,0.3));
        velocity=new Velocity();
        setSpeed(4.3);
    }


    public void setSpeed(double speed){
        velocity.setMax((float)speed);
    }


    public boolean isOnGround(){
        List<CubeHitbox> blockList=hitboxList();
        CubeHitbox h=hitbox.clone();
        h.move(0,-Float.MIN_VALUE,0);
        return Collider.getCollidedMove(h,blockList).y==0;
    }


    public List<CubeHitbox> hitboxList(){
        List<CubeHitbox> blockList=new ArrayList<>();
        Vector3 pos=hitbox.getPosition();
        int offset=10;
        for(int x=Math.round(pos.x-1-offset); x<pos.x+1+offset; x++)
            for(int y=Math.round(pos.y-1-offset); y<pos.y+3+offset; y++)
                for(int z=Math.round(pos.z-1-offset); z<pos.z+1+offset; z++){
                    int id=GameScreen.world.getBlock(x,y,z);
                    if(id!=-1 && id!=Block.AIR.id && id!=Block.WATER.id && id!=Block.STILL_WATER.id && id!=Block.GRASS.id)
                        blockList.add(new CubeHitbox(new Vector3(x,y,z),new Vector3(x+1,y+1,z+1)));
                }
        return blockList;
    }


}
