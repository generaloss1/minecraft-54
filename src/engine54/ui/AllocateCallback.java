package engine54.ui;

public interface AllocateCallback{

    void allocateOn(LayoutElement current);
    void allocated(LayoutElement current);
    void allocateOff(LayoutElement current);

}
