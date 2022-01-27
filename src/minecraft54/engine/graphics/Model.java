package minecraft54.engine.graphics;

import minecraft54.engine.math.Matrix4;
import minecraft54.engine.math.vectors.Vector3;

public class Model{


    private final Vector3 position,rotation,scale;

    public Mesh mesh;
    public Material material;

    private Matrix4 modelView;

    public boolean isReqCalcMat;


    public Model(Mesh mesh,Material material){
        this.mesh=mesh;
        this.material=material;

        position=new Vector3();
        rotation=new Vector3();
        scale=new Vector3(1);

        modelView=new Matrix4();
        isReqCalcMat=false;
    }

    public Model(Model model){
        this.mesh=new Mesh(model.mesh);
        this.material=new Material(model.material);

        position=new Vector3(model.position);
        rotation=new Vector3(model.rotation);
        scale=new Vector3(model.scale);

        modelView=new Matrix4(model.modelView);
        isReqCalcMat=model.isReqCalcMat;
    }


    public void calculateMatrix(){
        Matrix4 translation=new Matrix4().translate(position);
        Matrix4 scaling=new Matrix4().scale(scale);

        Matrix4 rotXMatrix=Matrix4.rotated(rotation.x,1,0,0);
        Matrix4 rotYMatrix=Matrix4.rotated(rotation.y,0,1,0);
        Matrix4 rotZMatrix=Matrix4.rotated(rotation.z,0,0,1);
        Matrix4 rotate=Matrix4.mul(rotXMatrix,Matrix4.mul(rotYMatrix,rotZMatrix));

        modelView=Matrix4.mul(Matrix4.mul(scaling,translation),rotate);
        isReqCalcMat=false;
    }


    public void setPos(Vector3 pos){
        position.set(pos);
        isReqCalcMat=true;
    }
    public void setPos(float x,float y,float z){
        position.set(x,y,z);
        isReqCalcMat=true;
    }
    public void translate(Vector3 tran){
        position.add(tran);
        isReqCalcMat=true;
    }
    public void translate(float x,float y,float z){
        position.add(x,y,z);
        isReqCalcMat=true;
    }

    public void setRotation(Vector3 rot){
        rotation.set(rot);
        isReqCalcMat=true;
    }
    public void setRotation(float x,float y,float z){
        rotation.set(x,y,z);
        isReqCalcMat=true;
    }
    public void rotate(Vector3 rot){
        rotation.add(rot);
        isReqCalcMat=true;
    }
    public void rotate(float x,float y,float z){
        rotation.add(x,y,z);
        isReqCalcMat=true;
    }

    public void setScale(Vector3 scl){
        scale.set(scl);
        isReqCalcMat=true;
    }
    public void setScale(float x,float y,float z){
        scale.set(x,y,z);
        isReqCalcMat=true;
    }
    public void scale(Vector3 scl){
        scale.add(scl);
        isReqCalcMat=true;
    }
    public void scale(float x,float y,float z){
        scale.add(x,y,z);
        isReqCalcMat=true;
    }
    public void scale(float xyz){
        scale.add(xyz);
        isReqCalcMat=true;
    }


    public Vector3 getPos(){
        return position;
    }

    public Vector3 getRotation(){
        return rotation;
    }

    public Vector3 getScale(){
        return scale;
    }

    public Matrix4 getModelMatrix(){
        return modelView;
    }


}
