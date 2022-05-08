package engine54.graphics.camera;

import engine54.io.Window;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector2f;

public class OrthographicCamera extends Camera2D{

    private float rotation,scale=1;
    private final Vector2f position=new Vector2f();
    private Matrix4f view=new Matrix4f();
    private boolean dirtyProjection;
    private final Matrix4f projection;
    private float width,height;

    public OrthographicCamera(float width,float height){
        this.width=width;
        this.height=height;

        projection=new Matrix4f().setToOrthographic(0,0,width,height);
    }

    public OrthographicCamera(Window window){
        this(window.getWidth(),window.getHeight());
    }

    public void update(){
        if(dirtyProjection){
            projection.identity().setToOrthographic(0,0,width,height);
            dirtyProjection=false;
        }

        Matrix4f translationMatrix=Matrix4f.translated(-position.x,-position.y,0);
        Matrix4f rotationMatrix=Matrix4f.rotatedZ(rotation);
        Matrix4f scaleMatrix=Matrix4f.scaled(scale,scale,1);

        view=Matrix4f.mul(Matrix4f.mul(translationMatrix,scaleMatrix),rotationMatrix);
    }

    public void resize(int width,int height){
        if(this.width!=width || this.height!=height){
            this.width=width;
            this.height=height;

            dirtyProjection=true;
        }
    }

    public void scale(float scale){
        this.scale*=scale;
    }

    public void setScale(float scale){
        this.scale=scale;
    }

    public void rotate(float deg){
        rotation+=deg;
    }

    public void setRotation(float deg){
        rotation=deg;
    }

    public float getRotation(){
        return rotation;
    }

    public Matrix4f getProjection(){
        return projection;
    }

    public Matrix4f getView(){
        return view;
    }

    public Vector2f getPosition(){
        return position;
    }

    public float x(){
        return position.x;
    }
    public float y(){
        return position.y;
    }

    public float getScale(){
        return scale;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

}
