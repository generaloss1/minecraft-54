package engine54.graphics.camera;

import engine54.maths.Matrix4f;

public abstract class Camera{

    public abstract void update();
    public abstract void resize(int width,int height);

    public abstract Matrix4f getView();
    public abstract Matrix4f getProjection();

    public abstract float getWidth();
    public abstract float getHeight();

}
