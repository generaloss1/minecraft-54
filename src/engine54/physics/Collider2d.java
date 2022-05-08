package engine54.physics;

import engine54.maths.vectors.Vector2d;
import java.util.List;

public class Collider2d{


    public static void collidedMove(BoundingBox2d h,List<BoundingBox2d> h2){
        Vector2d move=h.getVelocity();
        if(move.isZero())
            return;

        double x=move.x;
        for(BoundingBox2d h1:h2)
            if(x!=0)
                x=offsetX(x,h,h1);
        h.getPosition().x+=x;

        double y=move.y;
        for(BoundingBox2d h1:h2)
            if(y!=0)
                y=offsetY(y,h,h1);
        h.getPosition().y+=y;

        h.getVelocity().zero();
    }

    public static Vector2d getCollidedMove(BoundingBox2d h,List<BoundingBox2d> h2){
        Vector2d move=h.getVelocity();
        if(move.isZero())
            return move;

        BoundingBox2d hc=h.clone();

        double x=move.x;
        for(BoundingBox2d h1:h2)
            if(x!=0)
                x=offsetX(x,hc,h1);
        hc.getPosition().x+=x;

        double y=move.y;
        for(BoundingBox2d h1:h2)
            if(y!=0)
                y=offsetY(y,hc,h1);
        hc.getPosition().y+=y;

        return new Vector2d(x,y);
    }


    public static double offsetX(double move,BoundingBox2d h,BoundingBox2d h2){
        if(move==0)
            return 0;
        if(h2.getPosB().y>h.getPosA().y && h2.getPosA().y<h.getPosB().y)
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

    public static double offsetY(double move,BoundingBox2d h,BoundingBox2d h2){
        if(move==0)
            return 0;
        if(h2.getPosB().x>h.getPosA().x && h2.getPosA().x<h.getPosB().x)
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
    
    
}
