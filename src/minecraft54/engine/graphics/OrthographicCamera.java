package minecraft54.engine.graphics;

import minecraft54.engine.io.Window;
import minecraft54.engine.maths.Matrix4f;
import minecraft54.engine.maths.vectors.Vector2f;

public class OrthographicCamera{

    private float scale,rotation;
    private final Vector2f position;
    private float width,height;
    private Matrix4f projection,view;

    public OrthographicCamera(float width,float height){
        this.width=width;
        this.height=height;
        position=new Vector2f();
        scale=1;

        projection=new Matrix4f();
        view=new Matrix4f();
    }

    public OrthographicCamera(Window window){
        this.width=window.getWidth();
        this.height=window.getHeight();
        position=new Vector2f();
        scale=1;

        projection=new Matrix4f();
        view=new Matrix4f();
    }

    public void resize(int width,int height){
        this.width=width;
        this.height=height;
    }

    public void update(){
        projection.identity().setToOrthographic(0,0,width,height);

        Matrix4f translationMatrix=Matrix4f.translated(position.clone().mul(-1));
        Matrix4f rotationMatrix=Matrix4f.rotatedZ(rotation);

        view=Matrix4f.mul(translationMatrix,rotationMatrix);
    }

    public void translate(float x,float y){
        position.add(x,y);
    }

    public void translate(Vector2f v){
        position.add(v);
    }

    public void setPosition(float x,float y){
        position.set(x,y);
    }

    public void setPosition(Vector2f pos){
        position.set(pos);
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

    public Vector2f getPos(){
        return position;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
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
