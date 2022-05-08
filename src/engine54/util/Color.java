package engine54.util;

public class Color{

    private float r,g,b,a;

    public Color(float red,float green,float blue,float alpha){
        set(red,green,blue,alpha);
    }

    public Color(Color color){
        set(color);
    }

    public Color(int color){
        set(color);
    }

    public Color(float[] color){
        set(color);
    }

    public Color(){
        r=1;
        g=1;
        b=1;
        a=1;
    }

    public void set(float red,float green,float blue,float alpha){
        r=red;
        g=green;
        b=blue;
        a=alpha;
    }

    public void set(Color color){
        r=color.r;
        g=color.g;
        b=color.b;
        a=color.a;
    }

    public void set(int color){
        r=((color>>16) & 0xff)/255f;
        g=((color>>8 ) & 0xff)/255f;
        b=((color    ) & 0xff)/255f;
        a=((color>>24) & 0xff)/255f;
    }

    public void set(float[] color){
        r=color[0];
        g=color[1];
        b=color[2];
        a=color[3];
    }

    public void setRed(float r){
        this.r=r;
    }
    public void setGreen(float g){
        this.g=g;
    }
    public void setBlue(float b){
        this.b=b;
    }
    public void setAlpha(float a){
        this.a=a;
    }

    public float red(){
        return r;
    }
    public float green(){
        return g;
    }
    public float blue(){
        return b;
    }
    public float alpha(){
        return a;
    }

    public Color clone(){
        return new Color(this);
    }

    public String toString(){
        return r+" "+g+" "+b+" "+a;
    }

    public static int floatToIntColor(float value){
        int intBits=Float.floatToRawIntBits(value);
        intBits|=(int)((intBits >>> 24)*(255f/254f)) << 24;
        return intBits;
    }

    public static float intToFloatColor(int value){
        return Float.intBitsToFloat(value & 0xfeffffff);
    }

}
