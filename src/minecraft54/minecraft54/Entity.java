package minecraft54.minecraft54;

import minecraft54.engine.math.Maths;
import minecraft54.engine.math.vectors.Vector3;
import minecraft54.engine.math.vectors.Vector3d;
import minecraft54.engine.physics.Collider;
import minecraft54.engine.physics.CubeHitbox;
import minecraft54.minecraft54.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Entity{


    private final CubeHitbox hitbox;
    private final Vector3 eye;
    private final Velocity velocity;
    private float jumpVelocity,gravityVelocity;
    private boolean noClip,noGravity,jumping;
    private float jumpHeight,jumpDuration;


    public Entity(Vector3 A,Vector3 B,Vector3 eye){
        hitbox=new CubeHitbox(A,B);
        velocity=new Velocity();
        this.eye=eye;
        jumpHeight=1.25f;
        jumpDuration=0.6f;
    }


    public void update(){
        velocity.clampToMax();

        if(!noClip){

            hitbox.move(velocity.get().clone().add(new Vector3(0,(jumpVelocity+gravityVelocity),0)).mul(Main.getDeltaTime()));

            Vector3d collidedMove=Collider.getCollidedMove(hitbox,hitboxList());
            hitbox.getPosition().add(collidedMove);
            velocity.collidedAxesToZero(collidedMove).reduce(velocity.max()/30);

            if(isOnGround()){
                gravityVelocity=0;
                if(jumping){
                    jumping=false;
                    jumpVelocity=0;
                }
            }else{
                if(!noGravity)
                    gravityVelocity-=2*(jumpHeight)/(jumpDuration*jumpDuration*Main.getFPS()/4);

                if(isOnCeil() && jumping){
                    jumping=false;
                    jumpVelocity=0;
                    gravityVelocity=0;
                }
            }

            if(noGravity || GameScreen.world.getChunk(Maths.floor(hitbox.getPosition().x/16f),Maths.floor(hitbox.getPosition().z/16f))==null){
                jumpVelocity=0;
                gravityVelocity=0;
            }

        }else{
            hitbox.move(velocity.get().clone().mul(Main.getDeltaTime()));
            hitbox.getPosition().add(hitbox.getMove());
            velocity.reduce(velocity.max()/30);
        }

    }


    public void jump(){
        if(jumping)
            return;

        jumping=true;
        jumpVelocity=2*(jumpHeight)/(jumpDuration/2);
    }


    public void setJumpBoost(int level){
        if(level<1)
            level=1;
        jumpHeight=1.25f*level;
        jumpDuration=0.6f*Maths.sqrt(level);
    }


    public boolean isOnGround(){
        List<CubeHitbox> blockList=hitboxList();
        CubeHitbox h=hitbox.clone();
        h.move(0,-Float.MIN_VALUE,0);
        return Collider.getCollidedMove(h,blockList).y==0;
    }

    public boolean isOnGround(double mx,double my,double mz){
        List<CubeHitbox> blockList=hitboxList();
        CubeHitbox h=hitbox.clone();
        h.move(new Vector3(0,-Float.MIN_VALUE,0).add(mx,my,mz));
        return Collider.getCollidedMove(h,blockList).y==0;
    }

    public boolean isOnCeil(){
        List<CubeHitbox> blockList=hitboxList();
        CubeHitbox h=hitbox.clone();
        h.move(0,Float.MIN_VALUE,0);
        return Collider.getCollidedMove(h,blockList).y==0;
    }


    public List<CubeHitbox> hitboxList(){
        List<CubeHitbox> blockList=new ArrayList<>();
        Vector3d pos=hitbox.getPosition();
        int offset=10;
        for(int x=Maths.round(pos.x-1-offset); x<pos.x+1+offset; x++)
            for(int y=Maths.round(pos.y-1-offset); y<pos.y+3+offset; y++)
                for(int z=Maths.round(pos.z-1-offset); z<pos.z+1+offset; z++){
                    int id=GameScreen.world.getBlock(x,y,z);
                    if(id!=-1 && id!=Block.AIR.id && id!=Block.WATER.id && id!=Block.STILL_WATER.id && id!=Block.GRASS.id)
                        blockList.add(new CubeHitbox(new Vector3(x,y,z),new Vector3(x+1,y+1,z+1)));
                }
        return blockList;
    }


    public CubeHitbox getHitbox(){
        return hitbox;
    }
    public Vector3 getEye(){
        return eye;
    }
    public Velocity getVelocity(){
        return velocity;
    }
    public void setNoClip(boolean noClip){
        this.noClip=noClip;
    }
    public void setNoGravity(boolean noGravity){
        this.noGravity=noGravity;
    }
    public boolean isNoClip(){
        return noClip;
    }
    public boolean isNoGravity(){
        return noGravity;
    }
    public float getJumpHeight(){
        return jumpHeight;
    }
    public void setJumpHeight(float jumpHeight){
        this.jumpHeight=jumpHeight;
    }
    public boolean isJumping(){
        return jumping;
    }
    public void cancelJump(){
        jumping=false;
    }


}
