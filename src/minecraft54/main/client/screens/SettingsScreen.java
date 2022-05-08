package minecraft54.main.client.screens;


import engine54.E54;
import engine54.app.Screen;
import engine54.audio.SoundManager;
import engine54.graphics.SpriteBatch;
import engine54.graphics.camera.OrthographicCamera;
import engine54.maths.Maths;
import engine54.ui.*;
import engine54.util.Assets;
import minecraft54.main.Main;
import minecraft54.main.Options;
import minecraft54.main.client.controls.Controls;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;

public class SettingsScreen implements Screen{


    SpriteBatch sb;
    OrthographicCamera cam;
    public Layout layout;
    public boolean escFunc;


    public void create(){
        sb=new SpriteBatch();
        cam=new OrthographicCamera(E54.window());

        layout=new Layout();
        layout.load("gui/settings.json");

        layout.getElement("slr1").setTouchCallback(new TouchCallback(){public void touchOn(LayoutElement current){}public void touched(LayoutElement current){}public void touchOff(LayoutElement current){SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);}});
        layout.getElement("slr2").setTouchCallback(new TouchCallback(){public void touchOn(LayoutElement current){}public void touched(LayoutElement current){}public void touchOff(LayoutElement current){SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);}});
        layout.getElement("slr3").setTouchCallback(new TouchCallback(){public void touchOn(LayoutElement current){}public void touched(LayoutElement current){}public void touchOff(LayoutElement current){SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);}});
        layout.getElement("slr4").setTouchCallback(new TouchCallback(){public void touchOn(LayoutElement current){}public void touched(LayoutElement current){}public void touchOff(LayoutElement current){SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);}});
        layout.getElement("slr5").setTouchCallback(new TouchCallback(){public void touchOn(LayoutElement current){}public void touched(LayoutElement current){}public void touchOff(LayoutElement current){SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);}});
        layout.getElement("slr6").setTouchCallback(new TouchCallback(){public void touchOn(LayoutElement current){}public void touched(LayoutElement current){}public void touchOff(LayoutElement current){SoundManager.play("random_click",0.25f*Options.MASTER_VOLUME);}});

        ((Slider)layout.getElement("slr1")).setValue(Options.FOV/110);
        ((Text)layout.getElement("slr1txt")).setText("Field of view: "+Options.FOV);
        ((Slider)layout.getElement("slr2")).setValue(Options.RENDER_DISTANCE/32f);
        ((Text)layout.getElement("slr2txt")).setText("Render distance: "+Options.RENDER_DISTANCE);
        ((Slider)layout.getElement("slr3")).setValue(Options.SENSITIVITY/200);
        ((Text)layout.getElement("slr3txt")).setText("Sensitivity: "+Options.SENSITIVITY);
        ((Slider)layout.getElement("slr4")).setValue(Options.MASTER_VOLUME);
        ((Text)layout.getElement("slr4txt")).setText("Master volume: "+Maths.round(Options.MASTER_VOLUME*100));
        ((Slider)layout.getElement("slr5")).setValue(Options.BLOCKS_VOLUME);
        ((Text)layout.getElement("slr5txt")).setText("Blocks volume: "+Maths.round(Options.BLOCKS_VOLUME*100));
        ((Slider)layout.getElement("slr6")).setValue(Options.PLAYERS_VOLUME);
        ((Text)layout.getElement("slr6txt")).setText("Players volume: "+Maths.round(Options.PLAYERS_VOLUME*100));

        ((Slider)layout.getElement("slr1")).setSliderCallback(new SliderCallback(){
            public void updateValue(LayoutElement current,float value){
                Options.FOV=Maths.round(value*(110-10)+10);
                Controls.CAMERA.setFov(Options.FOV);
                Controls.fov=Options.FOV;
                ((Text)layout.getElement("slr1txt")).setText("Field of view: "+(int)Options.FOV);
                Options.save();
            }
        });
        ((Slider)layout.getElement("slr2")).setSliderCallback(new SliderCallback(){
            public void updateValue(LayoutElement current,float value){
                Options.RENDER_DISTANCE=Maths.round(value*(32-1)+1);
                ((Text)layout.getElement("slr2txt")).setText("Render distance: "+Options.RENDER_DISTANCE);
                Options.save();
            }
        });
        ((Slider)layout.getElement("slr3")).setSliderCallback(new SliderCallback(){
            public void updateValue(LayoutElement current,float value){
                Options.SENSITIVITY=Maths.round(value*(200-1)+1);
                ((Text)layout.getElement("slr3txt")).setText("Sensitivity: "+(int)Options.SENSITIVITY);
                Options.save();
            }
        });
        ((Slider)layout.getElement("slr4")).setSliderCallback(new SliderCallback(){
            public void updateValue(LayoutElement current,float value){
                Options.MASTER_VOLUME=value;
                ((Text)layout.getElement("slr4txt")).setText("Master volume: "+Maths.round(value*100));
                Options.save();
            }
        });
        ((Slider)layout.getElement("slr5")).setSliderCallback(new SliderCallback(){
            public void updateValue(LayoutElement current,float value){
                Options.BLOCKS_VOLUME=value;
                ((Text)layout.getElement("slr5txt")).setText("Blocks volume: "+Maths.round(value*100));
                Options.save();
            }
        });
        ((Slider)layout.getElement("slr6")).setSliderCallback(new SliderCallback(){
            public void updateValue(LayoutElement current,float value){
                Options.PLAYERS_VOLUME=value;
                ((Text)layout.getElement("slr6txt")).setText("Players volume: "+Maths.round(value*100));
                Options.save();
            }
        });

    }


    public void render(){
        cam.update();

        sb.draw(Assets.getTexture("background"),0,0,E54.window().getWidth(),E54.window().getHeight());

        layout.update(E54.mouse(),E54.keyboard(),E54.window());
        layout.render(sb);

        if(E54.keyboard().isKeyDown(GLFW_KEY_ESCAPE))
            if(escFunc)
                E54.context().setScreen("game");
            else
                E54.context().setScreen("menu");

        if(E54.keyboard().isKeyDown(GLFW_KEY_F11))
            E54.window().toggleFullscreen();

        sb.render(cam);
    }


    public void resize(int w,int h){
        cam.resize(w,h);
    }

    public void dispose(){
        sb.dispose();
    }

    public void set(String arg){
        layout.update(E54.mouse(),E54.keyboard(),E54.window());
        escFunc=arg!=null;
    }


}
