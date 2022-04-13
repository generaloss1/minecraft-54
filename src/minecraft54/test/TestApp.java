package minecraft54.test;

import minecraft54.engine.app.AppListener;

public class TestApp implements AppListener{

    @Override
    public void create(){
        Main.cfg.addScreen("screen1",new TestScreen1());
        Main.cfg.addScreen("screen2",new TestScreen2());

        Main.cfg.setScreen("screen1");
    }

    @Override
    public void update(){

    }

    @Override
    public void dispose(){

    }

}
