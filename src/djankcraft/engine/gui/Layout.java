package djankcraft.engine.gui;

import djankcraft.engine.graphics.SpriteBatch;
import djankcraft.engine.io.Keyboard;
import djankcraft.engine.io.Mouse;
import djankcraft.engine.io.Window;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Layout extends LayoutElement{


    public HashMap<String,LayoutElement> elements;
    public List<String> ids=new ArrayList<>();


    public Layout(){
        super(0,0);
        elements=new HashMap<>();
    }


    public void addElement(String id,LayoutElement element){
        elements.put(id,element);
        element.setLayout(this);
        ids.add(id);
    }

    public void removeElement(String id){
        elements.remove(id);
        ids.remove(id);
    }

    public LayoutElement getElement(String id){
        return elements.get(id);
    }


    public void render(SpriteBatch batch){
        for(String k:ids){
            LayoutElement e=elements.get(k);
            if(!e.isHidden())
                e.render(batch);
        }
    }

    public void update(Mouse mouse,Keyboard keyboard,Window window){
        setSize(window.getWidth(),window.getHeight());
        for(String k:ids)
            elements.get(k).update(mouse,keyboard,window);
    }


    public String getType(){
        return "Layout";
    }


    public void load(String guiFile){
        try{
            JSONArray jsonElements=new JSONArray(Files.readString(new File(guiFile).toPath()));
            for(int i=0; i<jsonElements.length(); i++){
                JSONObject jsonElement=jsonElements.getJSONObject(i);

                JSONArray jsonSize=jsonElement.getJSONArray("size");
                String id=jsonElement.getString("id");

                switch(jsonElement.getString("type")){
                    case "Image"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");
                        Image text=new Image(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("textureID"));
                        text.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        text.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        addElement(id,text);
                    }
                    case "Button"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");

                        Button button=new Button(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("texture1ID"),jsonElement.getString("texture2ID"),jsonElement.getString("texture3ID"));
                        button.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        button.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        addElement(id,button);
                    }
                    case "Text"->{
                        JSONArray jsonPos=jsonElement.getJSONArray("pos");
                        JSONArray jsonColor=jsonElement.getJSONArray("color");

                        Text text=new Text(jsonSize.getDouble(0),jsonSize.getDouble(1),jsonElement.getString("fontID"),jsonElement.getString("text"));
                        text.setPos(jsonPos.getDouble(0),jsonPos.getDouble(1));
                        text.setGravity(Gravity.parseGravity(jsonElement.getString("gravity")));
                        text.setColor((float)jsonColor.getDouble(0),(float)jsonColor.getDouble(1),(float)jsonColor.getDouble(2),(float)jsonColor.getDouble(3));
                        addElement(id,text);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
