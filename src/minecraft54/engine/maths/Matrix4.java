package minecraft54.engine.maths;

import minecraft54.engine.maths.vectors.Vector3f;
import minecraft54.engine.maths.vectors.Vector3d;

public class Matrix4{


    public static final int m00=0;
    public static final int m10=1;
    public static final int m20=2;
    public static final int m30=3;

    public static final int m01=4;
    public static final int m11=5;
    public static final int m21=6;
    public static final int m31=7;

    public static final int m02=8;
    public static final int m12=9;
    public static final int m22=10;
    public static final int m32=11;

    public static final int m03=12;
    public static final int m13=13;
    public static final int m23=14;
    public static final int m33=15;

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


    public Matrix4 setToPerspective(float width,float height,float near,float far,float fov){
        float ctgFov=1.0f/(float)Math.tan(fov*Maths.toRadians/2.0f);
        float aspect=width/height;

        val[m00]=ctgFov/aspect;
        val[m10]=0;
        val[m20]=0;
        val[m30]=0;

        val[m01]=0;
        val[m11]=ctgFov;
        val[m21]=0;
        val[m31]=0;

        val[m02]=0;
        val[m12]=0;
        val[m22]=(far+near)/(far-near);
        val[m32]=1.0f;

        val[m03]=0;
        val[m13]=0;
        val[m23]=(-2*far*near)/(far-near);
        val[m33]=0;

        return this;
    }

    public Matrix4 setToPerspectiveSphere(float width,float height,float near,float far,float fov){
        float ctgFov=1.0f/(float)Math.tan(fov*Maths.toRadians/2.0f);
        float aspect=width/height;

        val[m00]=ctgFov/aspect;
        val[m10]=0;
        val[m20]=0;
        val[m30]=0;

        val[m01]=0;
        val[m11]=ctgFov;
        val[m21]=0;
        val[m31]=0;

        val[m02]=0;
        val[m12]=0;
        val[m22]=(far+near)/(far-near);
        val[m32]=1.0f;

        val[m03]=0;
        val[m13]=0;
        val[m23]=(-2*far*near)/(far-near);
        val[m33]=0;

        return this;
    }

    public Matrix4 setToOrtho(float left,float right,float bottom,float top,float near,float far){
        val[m00]=2/(right-left);
        val[m10]=0;
        val[m20]=0;
        val[m30]=0;

        val[m01]=0;
        val[m11]=2/(top-bottom);
        val[m21]=0;
        val[m31]=0;

        val[m02]=0;
        val[m12]=0;
        val[m22]=-2/(far-near);
        val[m32]=0;

        val[m03]=-(right+left)/(right-left);
        val[m13]=-(top+bottom)/(top-bottom);
        val[m23]=-(far+near)/(far-near);
        val[m33]=1;

        return this;
    }

    public Matrix4 setToOrtho2D(float x,float y,float width,float height){
        val[m00]=2/width;
        val[m10]=0;
        val[m20]=0;
        val[m30]=0;

        val[m01]=0;
        val[m11]=2/height;
        val[m21]=0;
        val[m31]=0;

        val[m02]=0;
        val[m12]=0;
        val[m22]=-2;
        val[m32]=0;

        val[m03]=-(x*2+width)/width;
        val[m13]=-(y*2+height)/height;
        val[m23]=-1;
        val[m33]=1;

        return this;
    }


    public Matrix4 translate(float x,float y,float z){
        val[m03]+=val[m00]*x+val[m01]*y+val[m02]*z;
        val[m13]+=val[m10]*x+val[m11]*y+val[m12]*z;
        val[m23]+=val[m20]*x+val[m21]*y+val[m22]*z;
        val[m33]+=val[m30]*x+val[m31]*y+val[m32]*z;

        return this;
    }

    public Matrix4 translate(double x,double y,double z){
        val[m03]+=val[m00]*x+val[m01]*y+val[m02]*z;
        val[m13]+=val[m10]*x+val[m11]*y+val[m12]*z;
        val[m23]+=val[m20]*x+val[m21]*y+val[m22]*z;
        val[m33]+=val[m30]*x+val[m31]*y+val[m32]*z;

        return this;
    }

    public Matrix4 translate(Vector3f vector3){
        val[m03]+=val[m00]*vector3.x+val[m01]*vector3.y+val[m02]*vector3.z;
        val[m13]+=val[m10]*vector3.x+val[m11]*vector3.y+val[m12]*vector3.z;
        val[m23]+=val[m20]*vector3.x+val[m21]*vector3.y+val[m22]*vector3.z;
        val[m33]+=val[m30]*vector3.x+val[m31]*vector3.y+val[m32]*vector3.z;

        return this;
    }

    public Matrix4 translate(Vector3d vector3){
        val[m03]+=val[m00]*vector3.x+val[m01]*vector3.y+val[m02]*vector3.z;
        val[m13]+=val[m10]*vector3.x+val[m11]*vector3.y+val[m12]*vector3.z;
        val[m23]+=val[m20]*vector3.x+val[m21]*vector3.y+val[m22]*vector3.z;
        val[m33]+=val[m30]*vector3.x+val[m31]*vector3.y+val[m32]*vector3.z;

        return this;
    }


    public Matrix4 scale(Vector3f scl){
        val[m00]=scl.x;
        val[m11]=scl.y;
        val[m22]=scl.z;

        return this;
    }


    public static Matrix4 translated(Vector3f translate){
        Matrix4 result=new Matrix4();

        result.val[m30]=translate.x;
        result.val[m31]=translate.y;
        result.val[m32]=translate.z;

        return result;
    }

    public static Matrix4 translated(Vector3d translate){
        Matrix4 result=new Matrix4();

        result.val[m30]=(float)translate.x;
        result.val[m31]=(float)translate.y;
        result.val[m32]=(float)translate.z;

        return result;
    }

    public static Matrix4 rotated(float angle,float x,float y,float z){
        Matrix4 result=new Matrix4();

        float cos=(float)Math.cos(Math.toRadians(angle));
        float sin=(float)Math.sin(Math.toRadians(angle));
        float C=1-cos;

        result.val[m00]=cos+x*x*C;
        result.val[m01]=x*y*C-z*sin;
        result.val[m02]=x*z*C+y*sin;
        result.val[m10]=y*x*C+z*sin;
        result.val[m11]=cos+y*y*C;
        result.val[m12]=y*z*C-x*sin;
        result.val[m20]=z*x*C-y*sin;
        result.val[m21]=z*y*C+x*sin;
        result.val[m22]=cos+z*z*C;

        return result;
    }


    public static Matrix4 mul(Matrix4 matrixA,Matrix4 matrixB){
        float[] mata=matrixA.val;
        float[] matb=matrixB.val;
        float[] matc={
                mata[m00]*matb[m00]+mata[m01]*matb[m10]+mata[m02]*matb[m20]+mata[m03]*matb[m30],
                mata[m10]*matb[m00]+mata[m11]*matb[m10]+mata[m12]*matb[m20]+mata[m13]*matb[m30],
                mata[m20]*matb[m00]+mata[m21]*matb[m10]+mata[m22]*matb[m20]+mata[m23]*matb[m30],
                mata[m30]*matb[m00]+mata[m31]*matb[m10]+mata[m32]*matb[m20]+mata[m33]*matb[m30],

                mata[m00]*matb[m01]+mata[m01]*matb[m11]+mata[m02]*matb[m21]+mata[m03]*matb[m31],
                mata[m10]*matb[m01]+mata[m11]*matb[m11]+mata[m12]*matb[m21]+mata[m13]*matb[m31],
                mata[m20]*matb[m01]+mata[m21]*matb[m11]+mata[m22]*matb[m21]+mata[m23]*matb[m31],
                mata[m30]*matb[m01]+mata[m31]*matb[m11]+mata[m32]*matb[m21]+mata[m33]*matb[m31],

                mata[m00]*matb[m02]+mata[m01]*matb[m12]+mata[m02]*matb[m22]+mata[m03]*matb[m32],
                mata[m10]*matb[m02]+mata[m11]*matb[m12]+mata[m12]*matb[m22]+mata[m13]*matb[m32],
                mata[m20]*matb[m02]+mata[m21]*matb[m12]+mata[m22]*matb[m22]+mata[m23]*matb[m32],
                mata[m30]*matb[m02]+mata[m31]*matb[m12]+mata[m32]*matb[m22]+mata[m33]*matb[m32],

                mata[m00]*matb[m03]+mata[m01]*matb[m13]+mata[m02]*matb[m23]+mata[m03]*matb[m33],
                mata[m10]*matb[m03]+mata[m11]*matb[m13]+mata[m12]*matb[m23]+mata[m13]*matb[m33],
                mata[m20]*matb[m03]+mata[m21]*matb[m13]+mata[m22]*matb[m23]+mata[m23]*matb[m33],
                mata[m30]*matb[m03]+mata[m31]*matb[m13]+mata[m32]*matb[m23]+mata[m33]*matb[m33]
        };
        return new Matrix4(matc);
    }


}
