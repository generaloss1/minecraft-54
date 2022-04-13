package minecraft54.engine.maths;

public class Frustum{


    float[][] frustum;


    public Frustum(float[] view,float[] proj){
        setFrustum(view,proj);
    }


    public void setFrustum(float[] view,float[] proj){
        float[] clip=new float[]{
                view[0 ]*proj[0] + view[1 ]*proj[4] + view[2 ]*proj[8 ] + view[3 ]*proj[12],
                view[0 ]*proj[1] + view[1 ]*proj[5] + view[2 ]*proj[9 ] + view[3 ]*proj[13],
                view[0 ]*proj[2] + view[1 ]*proj[6] + view[2 ]*proj[10] + view[3 ]*proj[14],
                view[0 ]*proj[3] + view[1 ]*proj[7] + view[2 ]*proj[11] + view[3 ]*proj[15],
                view[4 ]*proj[0] + view[5 ]*proj[4] + view[6 ]*proj[8 ] + view[7 ]*proj[12],
                view[4 ]*proj[1] + view[5 ]*proj[5] + view[6 ]*proj[9 ] + view[7 ]*proj[13],
                view[4 ]*proj[2] + view[5 ]*proj[6] + view[6 ]*proj[10] + view[7 ]*proj[14],
                view[4 ]*proj[3] + view[5 ]*proj[7] + view[6 ]*proj[11] + view[7 ]*proj[15],
                view[8 ]*proj[0] + view[9 ]*proj[4] + view[10]*proj[8 ] + view[11]*proj[12],
                view[8 ]*proj[1] + view[9 ]*proj[5] + view[10]*proj[9 ] + view[11]*proj[13],
                view[8 ]*proj[2] + view[9 ]*proj[6] + view[10]*proj[10] + view[11]*proj[14],
                view[8 ]*proj[3] + view[9 ]*proj[7] + view[10]*proj[11] + view[11]*proj[15],
                view[12]*proj[0] + view[13]*proj[4] + view[14]*proj[8 ] + view[15]*proj[12],
                view[12]*proj[1] + view[13]*proj[5] + view[14]*proj[9 ] + view[15]*proj[13],
                view[12]*proj[2] + view[13]*proj[6] + view[14]*proj[10] + view[15]*proj[14],
                view[12]*proj[3] + view[13]*proj[7] + view[14]*proj[11] + view[15]*proj[15]
        };

        frustum=new float[][]{
                { clip[3] - clip[0], clip[7] - clip[4], clip[11] - clip[8 ], clip[15] - clip[12] },
                { clip[3] + clip[0], clip[7] + clip[4], clip[11] + clip[8 ], clip[15] + clip[12] },
                { clip[3] + clip[1], clip[7] + clip[5], clip[11] + clip[9 ], clip[15] + clip[13] },
                { clip[3] - clip[1], clip[7] - clip[5], clip[11] - clip[9 ], clip[15] - clip[13] },
                { clip[3] - clip[2], clip[7] - clip[6], clip[11] - clip[10], clip[15] - clip[14] },
                { clip[3] + clip[2], clip[7] + clip[6], clip[11] + clip[10], clip[15] + clip[14] }
        };

        for(int i=0; i<6; i++)
            divide(i);
    }


    private void divide(int index){
        float f=Maths.sqrt( frustum[index][0]*frustum[index][0] + frustum[index][1]*frustum[index][1] + frustum[index][2]*frustum[index][2] );
        frustum[index][0]/=f;
        frustum[index][1]/=f;
        frustum[index][2]/=f;
        frustum[index][3]/=f;
    }

    private double multiply(int index,double x,double y,double z){
        return frustum[index][0]*x + frustum[index][1]*y + frustum[index][2]*z + frustum[index][3];
    }


    public boolean isBoxInFrustum(double x1,double y1,double z1,double x2,double y2,double z2){
        for(int i=0; i<6; i++){
            if(
                    multiply(i, x1, y1, z1)<=0 &&
                    multiply(i, x2, y1, z1)<=0 &&
                    multiply(i, x1, y2, z1)<=0 &&
                    multiply(i, x2, y2, z1)<=0 &&
                    multiply(i, x1, y1, z2)<=0 &&
                    multiply(i, x2, y1, z2)<=0 &&
                    multiply(i, x1, y2, z2)<=0 &&
                    multiply(i, x2, y2, z2)<=0
            )
                return false;
        }
        return true;
    }


}