package engine54.app;

public interface Screen{

    void create();
    void render();
    void resize(int width,int height);
    void dispose();
    void set(String arg);

}
