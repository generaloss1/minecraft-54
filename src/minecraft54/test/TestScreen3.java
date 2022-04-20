package minecraft54.test;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.*;

import static org.lwjgl.opengl.GL11C.*;

public class TestScreen3 implements AppScreen{


    OrthographicCamera cam;
    SpriteBatch batch;

    Texture canvas;
    Pixmap pixmap,bg;

    @Override
    public void create(){
        cam=new OrthographicCamera(Main.window.getWidth(),Main.window.getHeight());
        batch=new SpriteBatch();

        bg=new Pixmap("icon.png");
        canvas=new Texture(new Pixmap(256,256));
        pixmap=new Pixmap("textures/ui/background.png");
    }

    float scale=1;

    @Override
    public void render(){
        glClearColor(0.1f,0.1f,0.15f,1f);
        cam.update();

        canvas.getPixmap().clear(0.1f,0.2f,0.5f,1f).drawPixmap(pixmap,scale);
        canvas.genTexture();

        int scroll=Main.mouse.getScroll();
        if(scroll>0)
            scale*=1.05;
        else if(scroll<0)
            scale*=0.95;

        batch.draw(canvas,15,15,500,500);
        batch.render(cam);

        canvas.dispose();
    }

    @Override
    public void resize(int width,int height){
        cam.resize(width,height);
    }

    @Override
    public void dispose(){
        batch.dispose();
        canvas.dispose();
    }

    @Override
    public void onSet(String arg){

    }


}