package engine54.maths;

import engine54.maths.vectors.Vector2f;
import engine54.maths.vectors.Vector3d;
import engine54.maths.vectors.Vector3f;

public class Matrix4f{


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


    public float[] val;

    public Matrix4f(){
        val=new float[16];
        val[m00]=1;
        val[m11]=1;
        val[m22]=1;
        val[m33]=1;
    }

    public Matrix4f(Matrix4f matrix4){
        val=new float[16];
        set(matrix4);
    }

    public Matrix4f(float[] values){
        val=new float[16];
        set(values);
    }

    public Matrix4f zero(){
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

    public Matrix4f identity(){
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

    public Matrix4f set(Matrix4f matrix4){
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

    public Matrix4f set(float[] values){
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

    public Matrix4f translate(Vector2f v){
        return translate(v.x,v.y,0);
    }

    public Matrix4f translate(Vector3f v){
        return translate(v.x,v.y,v.z);
    }

    public Matrix4f translate(Vector3d v){
        return translate(v.x,v.y,v.z);
    }

    public Matrix4f translate(double x,double y,double z){
        val[m03]+=val[m00]*x+val[m01]*y+val[m02]*z;
        val[m13]+=val[m10]*x+val[m11]*y+val[m12]*z;
        val[m23]+=val[m20]*x+val[m21]*y+val[m22]*z;
        val[m33]+=val[m30]*x+val[m31]*y+val[m32]*z;

        return this;
    }

    public static Matrix4f scaled(Vector3f v){
        return scaled(v.x,v.y,v.z);
    }

    public static Matrix4f scaled(Vector3d v){
        return scaled((float)v.x,(float)v.y,(float)v.z);
    }

    public static Matrix4f scaled(float x,float y,float z){
        Matrix4f result=new Matrix4f();

        result.val[m00]=x;
        result.val[m11]=y;
        result.val[m22]=z;

        return result;
    }

    public static Matrix4f translated(Vector2f v){
        return translated(v.x,v.y,0);
    }

    public static Matrix4f translated(Vector3f v){
        return translated(v.x,v.y,v.z);
    }

    public static Matrix4f translated(Vector3d v){
        return translated((float)v.x,(float)v.y,(float)v.z);
    }

    public static Matrix4f translated(float x,float y,float z){
        Matrix4f result=new Matrix4f();

        result.val[m03]=x;
        result.val[m13]=y;
        result.val[m23]=z;

        return result;
    }

    public static Matrix4f rotatedX(double deg){
        return rotatedXRad(Math.toRadians(deg));
    }

    public static Matrix4f rotatedXRad(double rad){
        Matrix4f result=new Matrix4f();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m11]=cos;
        result.val[m12]=-sin;
        result.val[m21]=sin;
        result.val[m22]=cos;

        return result;
    }

    public static Matrix4f rotatedY(double deg){
        return rotatedYRad(Math.toRadians(deg));
    }

    public static Matrix4f rotatedYRad(double rad){
        Matrix4f result=new Matrix4f();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m00]=cos;
        result.val[m02]=sin;
        result.val[m20]=-sin;
        result.val[m22]=cos;

        return result;
    }

    public static Matrix4f rotatedZ(double deg){
        return rotatedZRad(Math.toRadians(deg));
    }

    public static Matrix4f rotatedZRad(double rad){
        Matrix4f result=new Matrix4f();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m00]=cos;
        result.val[m01]=-sin;
        result.val[m10]=sin;
        result.val[m11]=cos;

        return result;
    }

    public Matrix4f setToOrthographic(float left,float right,float bottom,float top,float near,float far){
        val[m00]=2/(right-left);             val[m10]=0;                          val[m20]=0;                      val[m30]=0;
        val[m01]=0;                          val[m11]=2/(top-bottom);             val[m21]=0;                      val[m31]=0;
        val[m02]=0;                          val[m12]=0;                          val[m22]=-2/(far-near);          val[m32]=0;
        val[m03]=-(right+left)/(right-left); val[m13]=-(top+bottom)/(top-bottom); val[m23]=-(far+near)/(far-near); val[m33]=1;

        return this;
    }

    public Matrix4f setToOrthographic(float x,float y,float width,float height){
        val[m00]=2/width;            val[m10]=0;                    val[m20]=0;  val[m30]=0;
        val[m01]=0;                  val[m11]=2/height;             val[m21]=0;  val[m31]=0;
        val[m02]=0;                  val[m12]=0;                    val[m22]=-2; val[m32]=0;
        val[m03]=-(x*2+width)/width; val[m13]=-(y*2+height)/height; val[m23]=-1; val[m33]=1;

        return this;
    }

    public Matrix4f setToPerspective(float width,float height,float near,float far,float fov){
        float ctgFov=1/Maths.tan(Math.toRadians(fov/2));
        float aspect=width/height;

        val[m00]=ctgFov/aspect; val[m10]=0;      val[m20]=0;                        val[m30]=0;
        val[m01]=0;             val[m11]=ctgFov; val[m21]=0;                        val[m31]=0;
        val[m02]=0;             val[m12]=0;      val[m22]=(far+near)/(far-near);    val[m32]=1;
        val[m03]=0;             val[m13]=0;      val[m23]=-(2*far*near)/(far-near); val[m33]=0;

        return this;
    }

    public static Matrix4f lookAt(float posX,float posY,float posZ,float leftX,float leftY,float leftZ,float upX,float upY,float upZ,float forwardX,float forwardY,float forwardZ){
        Matrix4f a=new Matrix4f();

        a.val[m00]=leftX; a.val[m10]=upX; a.val[m20]=forwardX;
        a.val[m01]=leftY; a.val[m11]=upY; a.val[m21]=forwardY;
        a.val[m02]=leftZ; a.val[m12]=upZ; a.val[m22]=forwardZ;

        return mul(a,Matrix4f.translated(-posX,-posY,-posZ));
    }

    public static Matrix4f lookAt(Vector3d pos,Vector3d left,Vector3d up,Vector3d forward){
        return lookAt((float)pos.x,(float)pos.y,(float)pos.z,(float)left.x,(float)left.y,(float)left.z,(float)up.x,(float)up.y,(float)up.z,(float)forward.x,(float)forward.y,(float)forward.z);
    }

    public static Matrix4f lookAt(Vector3f pos,Vector3f left,Vector3f up,Vector3f forward){
        return lookAt(pos.x,pos.y,pos.z,left.x,left.y,left.z,up.x,up.y,up.z,forward.x,forward.y,forward.z);
    }

    public static Matrix4f lookAt(Vector3d position,Vector3d direction){
        Vector3d up=new Vector3d(0,1,0);
        Vector3d left=Vector3d.crs(up,direction.nor()).nor();
        up=Vector3d.crs(direction,left).nor();
        return lookAt(position,left,up,direction);
    }

    public static Matrix4f lookAt(Vector3f position,Vector3f direction){
        Vector3f up=new Vector3f(0,1,0);
        Vector3f left=Vector3f.crs(up,direction.nor()).nor();
        up=Vector3f.crs(direction,left).nor();
        return lookAt(position,left,up,direction);
    }

    public static Matrix4f mul(Matrix4f a,Matrix4f b){
        return new Matrix4f(new float[]{
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