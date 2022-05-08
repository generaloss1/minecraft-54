package engine54.maths;

import engine54.maths.vectors.Vector2f;
import engine54.maths.vectors.Vector3f;
import engine54.maths.vectors.Vector3d;

public class Matrix4d{


    final public static byte m00=0;
    final public static byte m10=1;
    final public static byte m20=2;
    final public static byte m30=3;

    final public static byte m01=4;
    final public static byte m11=5;
    final public static byte m21=6;
    final public static byte m31=7;

    final public static byte m02=8;
    final public static byte m12=9;
    final public static byte m22=10;
    final public static byte m32=11;

    final public static byte m03=12;
    final public static byte m13=13;
    final public static byte m23=14;
    final public static byte m33=15;


    public double[] val;

    public Matrix4d(){
        val=new double[16];
        val[m00]=1;
        val[m11]=1;
        val[m22]=1;
        val[m33]=1;
    }

    public Matrix4d(Matrix4d matrix4){
        val=new double[16];
        set(matrix4);
    }

    public Matrix4d(double[] values){
        val=new double[16];
        set(values);
    }

    public Matrix4d zero(){
        val[m00]=0;
        val[m10]=0;
        val[m20]=0;
        val[m30]=0;

        val[m01]=0;
        val[m11]=0;
        val[m21]=0;
        val[m31]=0;

        val[m02]=0;
        val[m12]=0;
        val[m22]=0;
        val[m32]=0;

        val[m03]=0;
        val[m13]=0;
        val[m23]=0;
        val[m33]=0;

        return this;
    }

    public Matrix4d identity(){
        val[m00]=1;
        val[m10]=0;
        val[m20]=0;
        val[m30]=0;

        val[m01]=0;
        val[m11]=1;
        val[m21]=0;
        val[m31]=0;

        val[m02]=0;
        val[m12]=0;
        val[m22]=1;
        val[m32]=0;

        val[m03]=0;
        val[m13]=0;
        val[m23]=0;
        val[m33]=1;

        return this;
    }

    public Matrix4d set(Matrix4d matrix4){
        val[m00]=matrix4.val[m00];
        val[m10]=matrix4.val[m10];
        val[m20]=matrix4.val[m20];
        val[m30]=matrix4.val[m30];

        val[m01]=matrix4.val[m01];
        val[m11]=matrix4.val[m11];
        val[m21]=matrix4.val[m21];
        val[m31]=matrix4.val[m31];

        val[m02]=matrix4.val[m02];
        val[m12]=matrix4.val[m12];
        val[m22]=matrix4.val[m22];
        val[m32]=matrix4.val[m32];

        val[m03]=matrix4.val[m03];
        val[m13]=matrix4.val[m13];
        val[m23]=matrix4.val[m23];
        val[m33]=matrix4.val[m33];

        return this;
    }

    public Matrix4d set(double[] values){
        val[m00]=values[m00];
        val[m10]=values[m10];
        val[m20]=values[m20];
        val[m30]=values[m30];

        val[m01]=values[m01];
        val[m11]=values[m11];
        val[m21]=values[m21];
        val[m31]=values[m31];

        val[m02]=values[m02];
        val[m12]=values[m12];
        val[m22]=values[m22];
        val[m32]=values[m32];

        val[m03]=values[m03];
        val[m13]=values[m13];
        val[m23]=values[m23];
        val[m33]=values[m33];

        return this;
    }

    public Matrix4d translate(Vector2f v){
        return translate(v.x,v.y,0);
    }

    public Matrix4d translate(Vector3f v){
        return translate(v.x,v.y,v.z);
    }

    public Matrix4d translate(Vector3d v){
        return translate(v.x,v.y,v.z);
    }

    public Matrix4d translate(double x,double y,double z){
        val[m03]+=val[m00]*x+val[m01]*y+val[m02]*z;
        val[m13]+=val[m10]*x+val[m11]*y+val[m12]*z;
        val[m23]+=val[m20]*x+val[m21]*y+val[m22]*z;
        val[m33]+=val[m30]*x+val[m31]*y+val[m32]*z;

        return this;
    }

    public static Matrix4d scaled(Vector3f v){
        return scaled(v.x,v.y,v.z);
    }

    public static Matrix4d scaled(Vector3d v){
        return scaled(v.x,v.y,v.z);
    }

    public static Matrix4d scaled(double x,double y,double z){
        Matrix4d result=new Matrix4d();

        result.val[m00]=x;
        result.val[m11]=y;
        result.val[m22]=z;

        return result;
    }

    public static Matrix4d translated(Vector2f v){
        return translated(v.x,v.y,0);
    }

    public static Matrix4d translated(Vector3f v){
        return translated(v.x,v.y,v.z);
    }

    public static Matrix4d translated(Vector3d v){
        return translated(v.x,v.y,v.z);
    }

    public static Matrix4d translated(double x,double y,double z){
        Matrix4d result=new Matrix4d();

        result.val[m03]=x;
        result.val[m13]=y;
        result.val[m23]=z;

        return result;
    }

    public static Matrix4d rotatedX(double deg){
        return rotatedXRad(Math.toRadians(deg));
    }

    public static Matrix4d rotatedXRad(double rad){
        Matrix4d result=new Matrix4d();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m11]=cos;
        result.val[m12]=-sin;
        result.val[m21]=sin;
        result.val[m22]=cos;

        return result;
    }

    public static Matrix4d rotatedY(double deg){
        return rotatedYRad(Math.toRadians(deg));
    }

    public static Matrix4d rotatedYRad(double rad){
        Matrix4d result=new Matrix4d();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m00]=cos;
        result.val[m02]=sin;
        result.val[m20]=-sin;
        result.val[m22]=cos;

        return result;
    }

    public static Matrix4d rotatedZ(double deg){
        return rotatedZRad(Math.toRadians(deg));
    }

    public static Matrix4d rotatedZRad(double rad){
        Matrix4d result=new Matrix4d();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m00]=cos;
        result.val[m01]=-sin;
        result.val[m10]=sin;
        result.val[m11]=cos;

        return result;
    }

    public Matrix4d setToOrthographic(double left,double right,double bottom,double top,double near,double far){
        val[m00]=2/(right-left);             val[m10]=0;                          val[m20]=0;                      val[m30]=0;
        val[m01]=0;                          val[m11]=2/(top-bottom);             val[m21]=0;                      val[m31]=0;
        val[m02]=0;                          val[m12]=0;                          val[m22]=-2/(far-near);          val[m32]=0;
        val[m03]=-(right+left)/(right-left); val[m13]=-(top+bottom)/(top-bottom); val[m23]=-(far+near)/(far-near); val[m33]=1;

        return this;
    }

    public Matrix4d setToOrthographic(double x,double y,double width,double height){
        val[m00]=2/width;            val[m10]=0;                    val[m20]=0;  val[m30]=0;
        val[m01]=0;                  val[m11]=2/height;             val[m21]=0;  val[m31]=0;
        val[m02]=0;                  val[m12]=0;                    val[m22]=-2; val[m32]=0;
        val[m03]=-(x*2+width)/width; val[m13]=-(y*2+height)/height; val[m23]=-1; val[m33]=1;

        return this;
    }

    public Matrix4d setToPerspective(double width,double height,double near,double far,double fov){
        double ctgFov=1/Math.tan(Math.toRadians(fov/2));
        double aspect=width/height;

        val[m00]=ctgFov/aspect; val[m10]=0;      val[m20]=0;                        val[m30]=0;
        val[m01]=0;             val[m11]=ctgFov; val[m21]=0;                        val[m31]=0;
        val[m02]=0;             val[m12]=0;      val[m22]=(far+near)/(far-near);    val[m32]=1;
        val[m03]=0;             val[m13]=0;      val[m23]=-(2*far*near)/(far-near); val[m33]=0;

        return this;
    }

    public static Matrix4d lookAt(double posX,double posY,double posZ,double leftX,double leftY,double leftZ,double upX,double upY,double upZ,double forwardX,double forwardY,double forwardZ){
        Matrix4d a=new Matrix4d();

        a.val[m00]=leftX; a.val[m10]=upX; a.val[m20]=forwardX;
        a.val[m01]=leftY; a.val[m11]=upY; a.val[m21]=forwardY;
        a.val[m02]=leftZ; a.val[m12]=upZ; a.val[m22]=forwardZ;

        return mul(a,Matrix4d.translated(-posX,-posY,-posZ));
    }

    public static Matrix4d lookAt(Vector3d pos,Vector3d left,Vector3d up,Vector3d forward){
        return lookAt(pos.x,pos.y,pos.z,left.x,left.y,left.z,up.x,up.y,up.z,forward.x,forward.y,forward.z);
    }

    public static Matrix4d lookAt(Vector3f pos,Vector3f left,Vector3f up,Vector3f forward){
        return lookAt(pos.x,pos.y,pos.z,left.x,left.y,left.z,up.x,up.y,up.z,forward.x,forward.y,forward.z);
    }

    public static Matrix4d lookAt(Vector3d position,Vector3d direction){
        Vector3d up=new Vector3d(0,1,0);
        Vector3d left=Vector3d.crs(up,direction.nor()).nor();
        up=Vector3d.crs(direction,left).nor();
        return lookAt(position,left,up,direction);
    }

    public static Matrix4d lookAt(Vector3f position,Vector3f direction){
        Vector3f up=new Vector3f(0,1,0);
        Vector3f left=Vector3f.crs(up,direction.nor()).nor();
        up=Vector3f.crs(direction,left).nor();
        return lookAt(position,left,up,direction);
    }

    public static Matrix4d mul(Matrix4d a,Matrix4d b){
        return new Matrix4d(new double[]{
                a.val[m00]*b.val[m00]+a.val[m01]*b.val[m10]+a.val[m02]*b.val[m20]+a.val[m03]*b.val[m30],
                a.val[m10]*b.val[m00]+a.val[m11]*b.val[m10]+a.val[m12]*b.val[m20]+a.val[m13]*b.val[m30],
                a.val[m20]*b.val[m00]+a.val[m21]*b.val[m10]+a.val[m22]*b.val[m20]+a.val[m23]*b.val[m30],
                a.val[m30]*b.val[m00]+a.val[m31]*b.val[m10]+a.val[m32]*b.val[m20]+a.val[m33]*b.val[m30],

                a.val[m00]*b.val[m01]+a.val[m01]*b.val[m11]+a.val[m02]*b.val[m21]+a.val[m03]*b.val[m31],
                a.val[m10]*b.val[m01]+a.val[m11]*b.val[m11]+a.val[m12]*b.val[m21]+a.val[m13]*b.val[m31],
                a.val[m20]*b.val[m01]+a.val[m21]*b.val[m11]+a.val[m22]*b.val[m21]+a.val[m23]*b.val[m31],
                a.val[m30]*b.val[m01]+a.val[m31]*b.val[m11]+a.val[m32]*b.val[m21]+a.val[m33]*b.val[m31],

                a.val[m00]*b.val[m02]+a.val[m01]*b.val[m12]+a.val[m02]*b.val[m22]+a.val[m03]*b.val[m32],
                a.val[m10]*b.val[m02]+a.val[m11]*b.val[m12]+a.val[m12]*b.val[m22]+a.val[m13]*b.val[m32],
                a.val[m20]*b.val[m02]+a.val[m21]*b.val[m12]+a.val[m22]*b.val[m22]+a.val[m23]*b.val[m32],
                a.val[m30]*b.val[m02]+a.val[m31]*b.val[m12]+a.val[m32]*b.val[m22]+a.val[m33]*b.val[m32],

                a.val[m00]*b.val[m03]+a.val[m01]*b.val[m13]+a.val[m02]*b.val[m23]+a.val[m03]*b.val[m33],
                a.val[m10]*b.val[m03]+a.val[m11]*b.val[m13]+a.val[m12]*b.val[m23]+a.val[m13]*b.val[m33],
                a.val[m20]*b.val[m03]+a.val[m21]*b.val[m13]+a.val[m22]*b.val[m23]+a.val[m23]*b.val[m33],
                a.val[m30]*b.val[m03]+a.val[m31]*b.val[m13]+a.val[m32]*b.val[m23]+a.val[m33]*b.val[m33]
        });
    }

}