package minecraft54.main.server.event;

public abstract class Event{

    private String name;
    private boolean async;
    private boolean cancelled;

    public Event(){
        this(false);
    }
    public Event(boolean isAsync){
        async=isAsync;
    }

    public String getName(){
        if(name==null)
            name=getClass().getSimpleName();
        return name;
    }

    public boolean isAsync(){
        return async;
    }

    public void setCancelled(boolean cancel){
        cancelled=cancel;
    }
    public boolean isCancelled(){
        return cancelled;
    }

}
