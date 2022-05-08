package engine54.graphics.camera;

import engine54.maths.EulerAngle;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector3f;

public class PerspectiveCamera extends Camera3D{

    private final Vector3f position;
    private final EulerAngle rotation;

    private boolean dirtyProjection;
    private final Matrix4f projection;
    private Matrix4f view;

    private float fov,near,far;
    private int width,height;

    public PerspectiveCamera(int width,int height,float near,float far,float fov){
        this.width=width;
        this.height=height;
        this.near=near;
        this.far=far;
        this.fov=fov;

        position=new Vector3f();
        rotation=new EulerAngle();

        projection=new Matrix4f().setToPerspective(width,height,near,far,fov);
        view=new Matrix4f();
    }

    public void update(){
        if(dirtyProjection){
            projection.setToPerspective(width,height,near,far,fov);
            dirtyProjection=false;
        }
        view=Matrix4f.lookAt(position,rotation.direction());
    }

    public void resize(int width,int height){
        if(this.width!=width || this.height!=height){
            this.width=width;
            this.height=height;

            dirtyProjection=true;
        }
    }

    public Matrix4f getView(){
        return view;
    }

    public Matrix4f getProjection(){
        return projection;
    }

    public Vector3f getPosition(){
        return position;
    }

    public float x(){
        return position.x;
    }
    public float y(){
        return position.y;
    }
    public float z(){
        return position.z;
    }

    public EulerAngle getRotation(){
        return rotation;
    }

    public float getNear(){
        return near;
    }

    public void setNear(float near){
        if(this.near==near)
            return;

        this.near=near;
        dirtyProjection=true;
    }

    public float getFar(){
        return far;
    }

    public void setFar(float far){
        if(this.far==far)
            return;

        this.far=far;
        dirtyProjection=true;
    }

    public float getFov(){
        return fov;
    }

    public void setFov(float fov){
        if(this.fov==fov)
            return;

        this.fov=fov;
        dirtyProjection=true;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }


}
