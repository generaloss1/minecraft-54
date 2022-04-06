package minecraft54.engine.ui;

public interface TouchCallback{

    void touchOn(LayoutElement current);
    void touched(LayoutElement current);
    void touchOff(LayoutElement current);

}

