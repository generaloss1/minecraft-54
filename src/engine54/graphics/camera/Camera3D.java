package engine54.graphics.camera;

import engine54.maths.vectors.Vector3f;

public abstract class Camera3D extends Camera{

    public abstract Vector3f getPosition();

    public abstract float x();
    public abstract float y();
    public abstract float z();
}
