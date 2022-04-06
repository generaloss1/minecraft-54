package minecraft54.engine.util;

import minecraft54.engine.io.Window;

public class Sync{

    private long prevTime;
    private long nanoTickTime;

    public Sync(double tps){
        setTps(tps);
    }

    public Sync(){
        setTps(Window.getRefreshRate());
    }

    public void setTps(double tps){
        nanoTickTime=(long)(1000000000/tps);
        prevTime=System.nanoTime();
    }

    public void sync(){
        final long nanoSleepTime=nanoTickTime-(System.nanoTime()-prevTime);
        if(nanoSleepTime>0L){
            final long startTime=System.nanoTime();
            long elapsed;
            do{
                elapsed=System.nanoTime()-startTime;
            }while(elapsed<nanoSleepTime);
        }
        prevTime=System.nanoTime();
    }

}