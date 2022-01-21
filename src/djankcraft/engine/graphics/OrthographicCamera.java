package djankcraft.engine.graphics;

import djankcraft.engine.io.Window;
import djankcraft.engine.math.Matrix4;
import djankcraft.engine.math.vectors.Vector2;
import djankcraft.engine.math.vectors.Vector3;

public class OrthographicCamera{

    private float scale,rotation;
    private final Vector2 position;
    private float width,height;
    private Matrix4 projection,view;

    public OrthographicCamera(float width,float height){
        this.width=width;
        this.height=height;
        position=new Vector2();
        scale=1;

        projection=new Matrix4();
        view=new Matrix4();
    }

    public OrthographicCamera(Window window){
        this.width=window.getWidth();
        this.height=window.getHeight();
        position=new Vector2();
        scale=1;

        projection=new Matrix4();
        view=new Matrix4();
    }

    public void resize(int width,int height){
        this.width=width;
        this.height=height;
    }

    public void update(){
        projection.setIdentity().setToOrtho2D(0,0,width,height);

        Matrix4 translationMatrix=new Matrix4().translate(new Vector3(position).mul(-1));
        Matrix4 rotationMatrix=Matrix4.rotated(rotation,0,0,1);

        view=Matrix4.mul(translationMatrix,rotationMatrix);
    }

    public void translate(float x,float y){
        position.add(x,y);
    }

    public void translate(Vector2 v){
        position.add(v);
    }

    public void setPosition(float x,float y){
        position.set(x,y);
    }

    public void setPosition(Vector2 pos){
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

    public Matrix4 getProjection(){
        return projection;
    }

    public Matrix4 getView(){
        return view;
    }

    public Vector2 getPos(){
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
