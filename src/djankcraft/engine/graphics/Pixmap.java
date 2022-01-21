package djankcraft.engine.graphics;

import djankcraft.engine.utils.Color;
import java.nio.ByteBuffer;

public interface Pixmap{

    Pixmap setPixel(int x,int y,int color);
    Pixmap setPixel(int x,int y,Color color);

    int getWidth();
    int getHeight();
    ByteBuffer getPixels();

    Pixmap clone();

    int getFormat();

}
