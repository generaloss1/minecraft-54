package djankcraft.engine.graphics;

public class VertexAttribute{

    private final int count;
    private final int type;

    public VertexAttribute(int count,int type){
        this.type=type;
        this.count=count;
    }

    public int getCount(){
        return count;
    }

    public int getType(){
        return type;
    }

}
