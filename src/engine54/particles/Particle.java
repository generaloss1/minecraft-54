package engine54.particles;

import engine54.graphics.texture.TextureRegion;
import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;
import engine54.physics.Velocity3d;
import engine54.util.Color;

public class Particle{

    private final Vector3d position;
    private final Vector3f normal;
    private final Color color;
    private float width;
    private long spawnedTime;
    private final TextureRegion texture;
    private long liveTimeMillis;
    private final ParticleBehavior behavior;
    private final Velocity3d velocity;
    public float gravityVelocity;

    public Particle(TextureRegion texture,Vector3d position,long liveTimeMillis,float width,ParticleBehavior behavior){
        this.texture=texture;
        this.position=position;
        this.behavior=behavior;
        this.width=width;
        this.liveTimeMillis=liveTimeMillis;

        velocity=new Velocity3d();
        color=new Color();
        normal=new Vector3f();
    }

    public void setTexture(TextureRegion texture){
        this.texture.setTextureRegion(texture);
    }
    public TextureRegion getTexture(){
        return texture;
    }

    public Vector3d getPosition(){
        return position;
    }

    public Velocity3d getVelocity(){
        return velocity;
    }

    public Vector3f getNormal(){
        return normal;
    }

    public Color getColor(){
        return color;
    }

    public void setWidth(float width){
        this.width=width;
    }
    public float getWidth(){
        return width;
    }

    public void resetSpawnTime(){
        spawnedTime=System.currentTimeMillis();
    }
    public long getLiveTimeMillis(){
        return System.currentTimeMillis()-spawnedTime;
    }

    public void setLiveTime(long millis){
        liveTimeMillis=millis;
    }
    public long getLiveTime(){
        return liveTimeMillis;
    }

    public void updateBehavior(){
        behavior.update(this);
    }

}
