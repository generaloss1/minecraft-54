package minecraft54.main.client.entity;

import minecraft54.engine.audio.SoundManager;
import minecraft54.engine.maths.Maths;
import minecraft54.engine.maths.vectors.Vector3f;
import minecraft54.engine.maths.vectors.Vector3d;
import minecraft54.engine.physics.Collider;
import minecraft54.engine.physics.Aabb;
import minecraft54.engine.physics.Velocity;
import minecraft54.main.Main;
import minecraft54.main.Minecraft54;
import minecraft54.main.Options;
import minecraft54.main.client.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Entity{


    private final Aabb hitbox;
    private final Vector3f eye;
    private final Velocity velocity;
    private float jumpVelocity,gravityVelocity;
    private boolean noClip,noGravity,jumping;
    private float jumpHeight,jumpDuration;
    private double fallUpperY,prevVelocityY;


    public Entity(Vector3f A,Vector3f B,Vector3f eye){
        hitbox=new Aabb(A,B);
        velocity=new Velocity();
        this.eye=eye;
        jumpHeight=1.25f;
        jumpDuration=0.6f;
    }


    public void update(){
        velocity.clampToMax();

        if(!noClip){
            double velocityY=velocity.get().y+jumpVelocity+gravityVelocity;
            if(velocityY<0 && prevVelocityY>=0)
                fallUpperY=hitbox.getPosition().y;
            prevVelocityY=velocityY;

            hitbox.move(velocity.get().clone().add(new Vector3f(0,(jumpVelocity+gravityVelocity),0)).mul(Main.getDeltaTime()));
            Vector3d collidedMove=Collider.getCollidedMove(hitbox,hitboxList());
            hitbox.getPosition().add(collidedMove);
            velocity.collidedAxesToZero(collidedMove).reduce(velocity.max()/30);

            if(isOnGround()){
                if(velocityY<0){
                    double fallHeight=fallUpperY-hitbox.getPosition().y;
                    if(fallHeight>7)
                        SoundManager.play("damage_fallbig",Options.PLAYERS_VOLUME*Options.MASTER_VOLUME);
                    else if(fallHeight>3)
                        SoundManager.play("damage_fallsmall",Options.PLAYERS_VOLUME*Options.MASTER_VOLUME);
                }
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

            if(noGravity || GameScreen.world.chunkProvider.getChunk(Maths.floor(hitbox.getPosition().x/16f),Maths.floor(hitbox.getPosition().z/16f))==null){
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
        List<Aabb> blockList=hitboxList();
        Aabb h=hitbox.clone();
        h.move(0,-Float.MIN_VALUE,0);
        return Collider.getCollidedMove(h,blockList).y==0;
    }

    public boolean isOnGround(double mx,double my,double mz){
        List<Aabb> blockList=hitboxList();
        Aabb h=hitbox.clone();
        h.move(new Vector3f(0,-Float.MIN_VALUE,0).add(mx,my,mz));
        return Collider.getCollidedMove(h,blockList).y==0;
    }

    public boolean isOnCeil(){
        List<Aabb> blockList=hitboxList();
        Aabb h=hitbox.clone();
        h.move(0,Float.MIN_VALUE,0);
        return Collider.getCollidedMove(h,blockList).y==0;
    }


    public List<Aabb> hitboxList(){
        List<Aabb> blockList=new ArrayList<>();
        Vector3d pos=hitbox.getPosition();
        int offset=10;
        for(int x=Maths.round(pos.x-1-offset); x<pos.x+1+offset; x++)
            for(int y=Maths.round(pos.y-1-offset); y<pos.y+3+offset; y++)
                for(int z=Maths.round(pos.z-1-offset); z<pos.z+1+offset; z++){
                    int id=GameScreen.world.getBlockId(x,y,z);
                    if(id!=-1 && id!=Minecraft54.FLOWER.getId() && id!=Minecraft54.AIR.getId() && id!=Minecraft54.WATER.getId() && id!=Minecraft54.WATER_STILL.getId() && id!=Minecraft54.GRASS.getId())
                        blockList.add(new Aabb(x,y,z,x+1,y+1,z+1));
                }
        return blockList;
    }


    public Aabb getHitbox(){
        return hitbox;
    }
    public Vector3f getEye(){
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
