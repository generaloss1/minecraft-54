package minecraft54.engine.maths;

import minecraft54.engine.maths.vectors.Vector2f;
import minecraft54.engine.maths.vectors.Vector3f;
import minecraft54.engine.maths.vectors.Vector3d;

public class Matrix4{


    final public static int m00=0;
    final public static int m10=1;
    final public static int m20=2;
    final public static int m30=3;

    final public static int m01=4;
    final public static int m11=5;
    final public static int m21=6;
    final public static int m31=7;

    final public static int m02=8;
    final public static int m12=9;
    final public static int m22=10;
    final public static int m32=11;

    final public static int m03=12;
    final public static int m13=13;
    final public static int m23=14;
    final public static int m33=15;


    public float[] val;

    public Matrix4(){
        val=new float[16];
        val[m00]=1;
        val[m11]=1;
        val[m22]=1;
        val[m33]=1;
    }

    public Matrix4(Matrix4 matrix4){
        val=new float[16];
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
    }

    public Matrix4(float[] floats){
        val=new float[16];
        val[m00]=floats[m00];
        val[m10]=floats[m10];
        val[m20]=floats[m20];
        val[m30]=floats[m30];

        val[m01]=floats[m01];
        val[m11]=floats[m11];
        val[m21]=floats[m21];
        val[m31]=floats[m31];

        val[m02]=floats[m02];
        val[m12]=floats[m12];
        val[m22]=floats[m22];
        val[m32]=floats[m32];

        val[m03]=floats[m03];
        val[m13]=floats[m13];
        val[m23]=floats[m23];
        val[m33]=floats[m33];
    }

    public Matrix4 setZero(){
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

    public Matrix4 setIdentity(){
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

    public Matrix4 set(Matrix4 matrix4){
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

    public Matrix4 set(float[] floats){
        val[m00]=floats[m00];
        val[m10]=floats[m10];
        val[m20]=floats[m20];
        val[m30]=floats[m30];

        val[m01]=floats[m01];
        val[m11]=floats[m11];
        val[m21]=floats[m21];
        val[m31]=floats[m31];

        val[m02]=floats[m02];
        val[m12]=floats[m12];
        val[m22]=floats[m22];
        val[m32]=floats[m32];

        val[m03]=floats[m03];
        val[m13]=floats[m13];
        val[m23]=floats[m23];
        val[m33]=floats[m33];

        return this;
    }

    public Matrix4 translate(Vector2f v){
        return translate(v.x,v.y,0);
    }

    public Matrix4 translate(Vector3f v){
        return translate(v.x,v.y,v.z);
    }

    public Matrix4 translate(float x,float y,float z){
        val[m03]+=val[m00]*x+val[m01]*y+val[m02]*z;
        val[m13]+=val[m10]*x+val[m11]*y+val[m12]*z;
        val[m23]+=val[m20]*x+val[m21]*y+val[m22]*z;
        val[m33]+=val[m30]*x+val[m31]*y+val[m32]*z;

        return this;
    }

    public Matrix4 translate(Vector3d v){
        return translate(v.x,v.y,v.z);
    }

    public Matrix4 translate(double x,double y,double z){
        val[m03]+=val[m00]*x+val[m01]*y+val[m02]*z;
        val[m13]+=val[m10]*x+val[m11]*y+val[m12]*z;
        val[m23]+=val[m20]*x+val[m21]*y+val[m22]*z;
        val[m33]+=val[m30]*x+val[m31]*y+val[m32]*z;

        return this;
    }

    public static Matrix4 scaled(Vector3f v){
        return scaled(v.x,v.y,v.z);
    }

    public static Matrix4 scaled(Vector3d v){
        return scaled((float)v.x,(float)v.y,(float)v.z);
    }

    public static Matrix4 scaled(double x,double y,double z){
        return scaled((float)x,(float)y,(float)z);
    }

    public static Matrix4 scaled(float x,float y,float z){
        Matrix4 result=new Matrix4();

        result.val[m00]=x;
        result.val[m11]=y;
        result.val[m22]=z;

        return result;
    }

    public static Matrix4 translated(Vector2f v){
        return translated(v.x,v.y,0);
    }

    public static Matrix4 translated(Vector3f v){
        return translated(v.x,v.y,v.z);
    }

    public static Matrix4 translated(Vector3d v){
        return translated((float)v.x,(float)v.y,(float)v.z);
    }

    public static Matrix4 translated(double x,double y,double z){
        return translated((float)x,(float)y,(float)z);
    }

    public static Matrix4 translated(float x,float y,float z){
        Matrix4 result=new Matrix4();

        result.val[m03]=x;
        result.val[m13]=y;
        result.val[m23]=z;

        return result;
    }

    public static Matrix4 rotatedX(float deg){
        return rotatedXRad(Math.toRadians(deg));
    }

    public static Matrix4 rotatedXRad(double rad){
        Matrix4 result=new Matrix4();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m11]=cos;
        result.val[m12]=-sin;
        result.val[m21]=sin;
        result.val[m22]=cos;

        return result;
    }

    public static Matrix4 rotatedY(float deg){
        return rotatedYRad(Math.toRadians(deg));
    }

    public static Matrix4 rotatedYRad(double rad){
        Matrix4 result=new Matrix4();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m00]=cos;
        result.val[m02]=sin;
        result.val[m20]=-sin;
        result.val[m22]=cos;

        return result;
    }

    public static Matrix4 rotatedZ(float deg){
        return rotatedZRad(Math.toRadians(deg));
    }

    public static Matrix4 rotatedZRad(double rad){
        Matrix4 result=new Matrix4();

        float cos=Maths.cos(rad);
        float sin=Maths.sin(rad);

        result.val[m00]=cos;
        result.val[m01]=-sin;
        result.val[m10]=sin;
        result.val[m11]=cos;

        return result;
    }

    public Matrix4 setToOrthographic(float left,float right,float bottom,float top,float near,float far){
        val[m00]=2/(right-left);             val[m10]=0;                          val[m20]=0;                      val[m30]=0;
        val[m01]=0;                          val[m11]=2/(top-bottom);             val[m21]=0;                      val[m31]=0;
        val[m02]=0;                          val[m12]=0;                          val[m22]=-2/(far-near);          val[m32]=0;
        val[m03]=-(right+left)/(right-left); val[m13]=-(top+bottom)/(top-bottom); val[m23]=-(far+near)/(far-near); val[m33]=1;

        return this;
    }

    public Matrix4 setToOrthographic(float x,float y,float width,float height){
        val[m00]=2/width;            val[m10]=0;                    val[m20]=0;  val[m30]=0;
        val[m01]=0;                  val[m11]=2/height;             val[m21]=0;  val[m31]=0;
        val[m02]=0;                  val[m12]=0;                    val[m22]=-2; val[m32]=0;
        val[m03]=-(x*2+width)/width; val[m13]=-(y*2+height)/height; val[m23]=-1; val[m33]=1;

        return this;
    }

    public Matrix4 setToPerspective(float width,float height,float near,float far,float fov){
        float ctgFov=1/(float)Math.tan(Math.toRadians(fov/2));
        float aspect=width/height;

        val[m00]=ctgFov/aspect; val[m10]=0;      val[m20]=0;                        val[m30]=0;
        val[m01]=0;             val[m11]=ctgFov; val[m21]=0;                        val[m31]=0;
        val[m02]=0;             val[m12]=0;      val[m22]=(far+near)/(far-near);    val[m32]=1;
        val[m03]=0;             val[m13]=0;      val[m23]=-(2*far*near)/(far-near); val[m33]=0;

        return this;
    }

    public static Matrix4 lookAt(Vector3f position,Vector3f axisX,Vector3f axisY,Vector3f axisZ){
        Matrix4 a=new Matrix4();

        a.val[m00]=axisX.x; a.val[m10]=axisY.x; a.val[m20]=axisZ.x;
        a.val[m01]=axisX.y; a.val[m11]=axisY.y; a.val[m21]=axisZ.y;
        a.val[m02]=axisX.z; a.val[m12]=axisY.z; a.val[m22]=axisZ.z;

        return mul(a,Matrix4.translated(position.clone().mul(-1)));
    }

    public static Matrix4 lookAt(Vector3f position,Vector3f direction){
        Vector3f up=new Vector3f(0,1,0);
        Vector3f right=Vector3f.crs(up,direction.nor()).nor();
        up=Vector3f.crs(direction,right).nor();
        return lookAt(position,right,up,direction);
    }

    public static Matrix4 mul(Matrix4 a,Matrix4 b){
        return new Matrix4(new float[]{
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
