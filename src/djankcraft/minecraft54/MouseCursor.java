package djankcraft.minecraft54;

import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.utils.Assets;

public class MouseCursor{

    public static int size=14*2;

    public static void render(SpriteBatch sb){
        int[] pos=Main.mouse.getPos();

        if(Main.mouse.inWindow() || pos[1]>-size || pos[0]>-size){
            sb.setColor(Minecraft54.CURSOR_COLOR_2);
            sb.draw(Assets.getTexture("cursor1"),pos[0]+size/14,Main.window.getHeight()-pos[1]-size*9/7,size*9/7,size*9/7);
            sb.draw(Assets.getTexture("cursor1"),pos[0],Main.window.getHeight()-pos[1]+(int)(-size/14f-size*9f/7),size*9/7,size*9/7);
            sb.setColor(Minecraft54.CURSOR_COLOR_1);
            sb.draw(Assets.getTexture("cursor0"),pos[0]+size/7,Main.window.getHeight()-pos[1]-size-size/7,size,size);
            sb.setColor(1,1,1,1);
        }
    }

}
