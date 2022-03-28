package minecraft54.main.server.world;

public interface BlockData{

    //Material getMaterial();

    String getAsString();
    String getAsString(boolean hideUnspecified);
    boolean matches(BlockData data);

    //BlockData merge(BlockData data);

    //SoundGroup getSoundGroup();

}