package minecraft54.engine.physics;

import minecraft54.engine.math.vectors.Vector3d;

import java.util.List;

public class Collider{


    public static void collidedMove(HitboxAabb h,List<HitboxAabb> h2){
        Vector3d move=h.getMove();
        if(move.isZero())
            return;

        double x=move.x;
        for(HitboxAabb h1:h2)
            if(x!=0)
                x=offsetX(x,h,h1);
        h.getPosition().x+=x;

        double y=move.y;
        for(HitboxAabb h1:h2)
            if(y!=0)
                y=offsetY(y,h,h1);
        h.getPosition().y+=y;

        double z=move.z;
        for(HitboxAabb h1:h2)
            if(z!=0)
                z=offsetZ(z,h,h1);
        h.getPosition().z+=z;

        h.getMove().zero();
    }

    public static Vector3d getCollidedMove(HitboxAabb h,List<HitboxAabb> h2){
        Vector3d move=h.getMove();
        if(move.isZero())
            return move;

        HitboxAabb hc=h.clone();

        double x=move.x;
        for(HitboxAabb h1:h2)
            if(x!=0)
                x=offsetX(x,hc,h1);
        hc.getPosition().x+=x;

        double y=move.y;
        for(HitboxAabb h1:h2)
            if(y!=0)
                y=offsetY(y,hc,h1);
        hc.getPosition().y+=y;

        double z=move.z;
        for(HitboxAabb h1:h2)
            if(z!=0)
                z=offsetZ(z,hc,h1);
        hc.getPosition().z+=z;

        return new Vector3d(x,y,z);
    }


    public static double offsetX(double move,HitboxAabb h,HitboxAabb h2){
        if(move==0)
            return 0;
        if(h2.getPosB().y>h.getPosA().y && h2.getPosA().y<h.getPosB().y && h2.getPosB().z>h.getPosA().z && h2.getPosA().z<h.getPosB().z)
            if(move>0){
                double hMin=Math.min(h2.getPosA().x,h2.getPosB().x);
                double hMax=Math.max(h.getPosA().x,h.getPosB().x);
                double offset=hMin-hMax;
                if(offset>=0 && move>offset)
                    return offset;
            }else{
                double hMin=Math.min(h.getPosA().x,h.getPosB().x);
                double hMax=Math.max(h2.getPosA().x,h2.getPosB().x);
                double offset=hMax-hMin;
                if(offset<=0 && move<offset)
                    return offset;
            }
        return move;
    }

    public static double offsetY(double move,HitboxAabb h,HitboxAabb h2){
        if(move==0)
            return 0;
        if(h2.getPosB().x>h.getPosA().x && h2.getPosA().x<h.getPosB().x && h2.getPosB().z>h.getPosA().z && h2.getPosA().z<h.getPosB().z)
            if(move>0){
                double hMin=Math.min(h2.getPosA().y,h2.getPosB().y);
                double hMax=Math.max(h.getPosA().y,h.getPosB().y);
                double offset=hMin-hMax;
                if(offset>=0 && move>offset)
                    return offset;
            }else{
                double hMin=Math.min(h.getPosA().y,h.getPosB().y);
                double hMax=Math.max(h2.getPosA().y,h2.getPosB().y);
                double offset=hMax-hMin;
                if(offset<=0 && move<offset)
                    return offset;
            }
        return move;
    }

    public static double offsetZ(double move,HitboxAabb h,HitboxAabb h2){
        if(move==0)
            return 0;
        if(h2.getPosB().x>h.getPosA().x && h2.getPosA().x<h.getPosB().x && h2.getPosB().y>h.getPosA().y && h2.getPosA().y<h.getPosB().y)
            if(move>0){
                double hMin=Math.min(h2.getPosA().z,h2.getPosB().z);
                double hMax=Math.max(h.getPosA().z,h.getPosB().z);
                double offset=hMin-hMax;
                HitboxAabb h1=h.clone();
                h1.getPosition().add(0,0,offset);
                if(offset>=0 && move>offset)
                    return offset;
            }else{
                double hMin=Math.min(h.getPosA().z,h.getPosB().z);
                double hMax=Math.max(h2.getPosA().z,h2.getPosB().z);
                double offset=hMax-hMin;
                if(offset<=0 && move<offset)
                    return offset;
            }
        return move;
    }


}