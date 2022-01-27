package minecraft54.engine.gui;

public interface AllocateCallback{

    void allocateOn(LayoutElement current);
    void allocated(LayoutElement current);
    void allocateOff(LayoutElement current);

}
