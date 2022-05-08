package minecraft54.main.server.entity;

import engine54.maths.vectors.Vector3d;
import engine54.physics.BoundingBox3d;
import minecraft54.main.util.Location;
import minecraft54.main.server.world.World;

import java.util.UUID;

public interface Entity{

    World getWorld();

    Location getLocation();

    void setVelocity(Vector3d var1);
    Vector3d getVelocity();

    BoundingBox3d getHitbox();
    boolean isOnGround();
    boolean hasGravity();
    void setGravity(boolean var1);

    void remove();

    boolean isDead();
    boolean isValid();
    boolean isEmpty();

    void setCustomNameVisible(boolean var1);
    boolean isCustomNameVisible();

    //EntityType getType();

    UUID getUuid();

}
