package minecraft54.engine.graphics;

public class VertexAttribute{

    private final int count;
    private final int type;
    private final boolean normalize;

    public VertexAttribute(int count,int type){
        this(count,type,false);
    }

    public VertexAttribute(int count,int type,boolean normalize){
        this.type=type;
        this.count=count;
        this.normalize=normalize;
    }

    public int getCount(){
        return count;
    }

    public int getType(){
        return type;
    }

    public boolean isNormalize(){
        return normalize;
    }

}
