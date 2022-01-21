package djankcraft.engine.physics;

import djankcraft.engine.math.vectors.Vector3;
import java.util.List;

public class Collider{


    public static void collidedMove(CubeHitbox h,List<CubeHitbox> h2){
        Vector3 move=h.getMove();
        if(move.isZero())
            return;

        float x=move.x;
        for(CubeHitbox h1:h2)
            if(x!=0)
                x=offsetX(x,h,h1);
        h.getPosition().x+=x;

        float y=move.y;
        for(CubeHitbox h1:h2)
            if(y!=0)
                y=offsetY(y,h,h1);
        h.getPosition().y+=y;

        float z=move.z;
        for(CubeHitbox h1:h2)
            if(z!=0)
                z=offsetZ(z,h,h1);
        h.getPosition().z+=z;

        h.getMove().zero();
    }

    public static Vector3 getCollidedMove(CubeHitbox h,List<CubeHitbox> h2){
        Vector3 move=h.getMove();
        if(move.isZero())
            return move;

        CubeHitbox hc=h.clone();

        float x=move.x;
        for(CubeHitbox h1:h2)
            if(x!=0)
                x=offsetX(x,hc,h1);
        hc.getPosition().x+=x;

        float y=move.y;
        for(CubeHitbox h1:h2)
            if(y!=0)
                y=offsetY(y,hc,h1);
        hc.getPosition().y+=y;

        float z=move.z;
        for(CubeHitbox h1:h2)
            if(z!=0)
                z=offsetZ(z,hc,h1);
        hc.getPosition().z+=z;

        return new Vector3(x,y,z);
    }


    public static float offsetX(float move,CubeHitbox h,CubeHitbox h2){
        if(move==0)
            return 0;
        if(h2.getPosB().y>h.getPosA().y && h2.getPosA().y<h.getPosB().y && h2.getPosB().z>h.getPosA().z && h2.getPosA().z<h.getPosB().z)
            if(move>0){
                float hMin=Math.min(h2.getPosA().x,h2.getPosB().x);
                float hMax=Math.max(h.getPosA().x,h.getPosB().x);
                float offset=hMin-hMax;
                if(offset>=0 && move>offset)
                    return offset;
            }else{
                float hMin=Math.min(h.getPosA().x,h.getPosB().x);
                float hMax=Math.max(h2.getPosA().x,h2.getPosB().x);
                float offset=hMax-hMin;
                if(offset<=0 && move<offset)
                    return offset;
            }
        return move;
    }

    public static float offsetY(float move,CubeHitbox h,CubeHitbox h2){
        if(move==0)
            return 0;
        if(h2.getPosB().x>h.getPosA().x && h2.getPosA().x<h.getPosB().x && h2.getPosB().z>h.getPosA().z && h2.getPosA().z<h.getPosB().z)
            if(move>0){
                float hMin=Math.min(h2.getPosA().y,h2.getPosB().y);
                float hMax=Math.max(h.getPosA().y,h.getPosB().y);
                float offset=hMin-hMax;
                if(offset>=0 && move>offset)
                    return offset;
            }else{
                float hMin=Math.min(h.getPosA().y,h.getPosB().y);
                float hMax=Math.max(h2.getPosA().y,h2.getPosB().y);
                float offset=hMax-hMin;
                if(offset<=0 && move<offset)
                    return offset;
            }
        return move;
    }

    public static float offsetZ(float move,CubeHitbox h,CubeHitbox h2){
        if(move==0)
            return 0;
        if(h2.getPosB().x>h.getPosA().x && h2.getPosA().x<h.getPosB().x && h2.getPosB().y>h.getPosA().y && h2.getPosA().y<h.getPosB().y)
            if(move>0){
                float hMin=Math.min(h2.getPosA().z,h2.getPosB().z);
                float hMax=Math.max(h.getPosA().z,h.getPosB().z);
                float offset=hMin-hMax;
                CubeHitbox h1=h.clone();
                h1.getPosition().add(0,0,offset);
                if(offset>=0 && move>offset)
                    return offset;
            }else{
                float hMin=Math.min(h.getPosA().z,h.getPosB().z);
                float hMax=Math.max(h2.getPosA().z,h2.getPosB().z);
                float offset=hMax-hMin;
                if(offset<=0 && move<offset)
                    return offset;
            }
        return move;
    }


}