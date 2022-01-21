package djankcraft.engine.app;

public interface AppScreen{

    void create();
    void render();
    void resize(int width,int height);
    void dispose();
    void onSet();

}
