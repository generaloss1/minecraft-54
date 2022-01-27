package minecraft54.engine.math;

import minecraft54.engine.math.vectors.Vector3;
import minecraft54.engine.physics.CubeHitbox;

public class Intersector{


    public static Vector3 lineQuad(Vector3 q1,Vector3 q2,Vector3 p1,Vector3 p2,Vector3 p3,Vector3 p4){
        Vector3 i1=lineTriangle(q1,q2,p1,p2,p3);
        Vector3 i2=lineTriangle(q1,q2,p1,p4,p3);
        if(i1==null & i2==null)
            return null;
        if(i2==null)
            return i1;
        else
            return i2;
    }

    public static Vector3 lineQuad(Vector3 q1,Vector3 q2,float x1,float y1,float z1,float x2,float y2,float z2,float x3,float y3,float z3,float x4,float y4,float z4){
        Vector3 p1=new Vector3(x1,y1,z1);
        Vector3 p2=new Vector3(x2,y2,z2);
        Vector3 p3=new Vector3(x3,y3,z3);
        Vector3 p4=new Vector3(x4,y4,z4);
        Vector3 i1=lineTriangle(q1,q2,p1,p2,p3);
        Vector3 i2=lineTriangle(q1,q2,p1,p4,p3);
        if(i1==null & i2==null)
            return null;
        if(i2==null)
            return i1;
        else
            return i2;
    }

    public static Vector3 lineTriangle(Vector3 q1,Vector3 q2,Vector3 p1,Vector3 p2,Vector3 p3){
        float s1=signed_tetra_volume(q1,p1,p2,p3);
        float s2=signed_tetra_volume(q2,p1,p2,p3);

        if(s1!=s2){
            float s3=signed_tetra_volume(q1,q2,p1,p2);
            float s4=signed_tetra_volume(q1,q2,p2,p3);
            float s5=signed_tetra_volume(q1,q2,p3,p1);
            if(s3==s4 && s4==s5){
                Vector3 n=Vector3.crs( p2.clone().sub(p1), p3.clone().sub(p1) );
                float t=-Vector3.dot(q1,n.clone().sub(p1))/Vector3.dot(q1,q2.clone().sub(q1));
                return q1.add((q2.clone().sub(q1)).mul(t));
            }
        }
        return null;
    }


    private static float signed_tetra_volume(Vector3 a,Vector3 b,Vector3 c,Vector3 d){
        return Math.signum(
                Vector3.dot(
                        Vector3.crs(
                                b.clone().sub(a),
                                c.clone().sub(a)
                        ),
                        d.clone().sub(a)
                )/6f
        );
    }


    public static boolean aabbAabb(CubeHitbox a,CubeHitbox b){
        return (a.getPosA().x<=b.getPosB().x && a.getPosB().x>=b.getPosA().x) &&
               (a.getPosA().y<=b.getPosB().y && a.getPosB().y>=b.getPosA().y) &&
               (a.getPosA().z<=b.getPosB().z && a.getPosB().z>=b.getPosA().z);
    }


}
