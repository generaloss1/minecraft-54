package djankcraft.engine.gui;

public class Gravity{

    public static final int LEFT_DOWN=0;
    public static final int LEFT_UP=1;
    public static final int RIGHT_UP=2;
    public static final int RIGHT_DOWN=4;

    public static final int CENTER=8;

    public static final int LEFT_CENTER=16;
    public static final int CENTER_UP=32;
    public static final int RIGHT_CENTER=64;
    public static final int CENTER_DOWN=128;

    public static int parseGravity(String stringGravity){
        return switch(stringGravity){
            default->LEFT_DOWN;
            case "LEFT_UP"->LEFT_UP;
            case "RIGHT_UP"->RIGHT_UP;
            case "RIGHT_DOWN"->RIGHT_DOWN;

            case "CENTER"->CENTER;

            case "LEFT_CENTER"->LEFT_CENTER;
            case "CENTER_UP"->CENTER_UP;
            case "RIGHT_CENTER"->RIGHT_CENTER;
            case "CENTER_DOWN"->CENTER_DOWN;
        };
    }

}