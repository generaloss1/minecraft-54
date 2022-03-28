package minecraft54.main.server.world;

public abstract class ChunkGenerator{

    private String name;

    public String getName(){
        if(name==null)
            name=getClass().getSimpleName();
        return name;
    }

    abstract void generate(Chunk chunk);

}
