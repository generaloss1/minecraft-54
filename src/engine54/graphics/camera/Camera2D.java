package engine54.graphics.camera;

import engine54.maths.vectors.Vector2f;

public abstract class Camera2D extends Camera{

    public abstract Vector2f getPosition();

    public abstract float x();
    public abstract float y();

    public abstract float getScale();

}
