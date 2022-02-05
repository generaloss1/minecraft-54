package minecraft54.engine.math;

import minecraft54.engine.math.vectors.Vector3;
import minecraft54.engine.math.vectors.Vector3d;
import minecraft54.engine.physics.CubeHitbox;

public class Intersector{


    public static Vector3d lineQuad(Vector3d q1,Vector3d q2,Vector3d p1,Vector3d p2,Vector3d p3,Vector3d p4){
        Vector3d i1=lineTriangle(q1,q2,p1,p2,p3);
        Vector3d i2=lineTriangle(q1,q2,p1,p4,p3);
        if(i1==null & i2==null)
            return null;
        if(i2==null)
            return i1;
        else
            return i2;
    }

    public static Vector3d lineQuad(Vector3d q1,Vector3d q2,double x1,double y1,double z1,double x2,double y2,double z2,double x3,double y3,double z3,double x4,double y4,double z4){
        Vector3d p1=new Vector3d(x1,y1,z1);
        Vector3d p2=new Vector3d(x2,y2,z2);
        Vector3d p3=new Vector3d(x3,y3,z3);
        Vector3d p4=new Vector3d(x4,y4,z4);
        Vector3d i1=lineTriangle(q1,q2,p1,p2,p3);
        Vector3d i2=lineTriangle(q1,q2,p1,p4,p3);
        if(i1==null & i2==null)
            return null;
        if(i2==null)
            return i1;
        else
            return i2;
    }

    public static Vector3d lineTriangle(Vector3d q1,Vector3d q2,Vector3d p1,Vector3d p2,Vector3d p3){
        double s1=signed_tetra_volume(q1,p1,p2,p3);
        double s2=signed_tetra_volume(q2,p1,p2,p3);

        if(s1!=s2){
            double s3=signed_tetra_volume(q1,q2,p1,p2);
            double s4=signed_tetra_volume(q1,q2,p2,p3);
            double s5=signed_tetra_volume(q1,q2,p3,p1);
            if(s3==s4 && s4==s5){
                Vector3d n=Vector3d.crs( p2.clone().sub(p1), p3.clone().sub(p1) );
                double t=-Vector3d.dot(q1,n.clone().sub(p1))/Vector3d.dot(q1,q2.clone().sub(q1));
                return q1.add((q2.clone().sub(q1)).mul(t));
            }
        }
        return null;
    }


    private static double signed_tetra_volume(Vector3d a,Vector3d b,Vector3d c,Vector3d d){
        return Math.signum(
                Vector3d.dot(
                        Vector3d.crs(
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
