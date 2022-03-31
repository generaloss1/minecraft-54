package minecraft54.main;

import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;

public class Options{



    public static String ACCOUNT_NAME="GeneralPashon";


    public static float FOV=85;
    public static int RENDER_DISTANCE=4;
    public static float SENSITIVITY=100;
    // VOLUME
    public static float MASTER_VOLUME=1;
    public static float BLOCKS_VOLUME=0.4f;
    public static float PLAYERS_VOLUME=0.6f;



    public static void load(){
        try{
            File file=new File(Minecraft54.OPTIONS_PATH);
            if(!file.exists()){
                file.createNewFile();
                JSONObject jsonOptions=new JSONObject();

                jsonOptions.put("fov",FOV);
                jsonOptions.put("renderDistance",RENDER_DISTANCE);
                jsonOptions.put("sensitivity",SENSITIVITY);

                jsonOptions.put("masterVolume",MASTER_VOLUME);
                jsonOptions.put("blocksVolume",BLOCKS_VOLUME);
                jsonOptions.put("playersVolume",PLAYERS_VOLUME);

                Files.writeString(file.toPath(),jsonOptions.toString());
            }else{
                JSONObject jsonOptions=new JSONObject(Files.readString(file.toPath()));

                FOV=jsonOptions.getFloat("fov");
                RENDER_DISTANCE=jsonOptions.getInt("renderDistance");
                SENSITIVITY=jsonOptions.getFloat("sensitivity");

                MASTER_VOLUME=jsonOptions.getFloat("masterVolume");
                BLOCKS_VOLUME=jsonOptions.getFloat("blocksVolume");
                PLAYERS_VOLUME=jsonOptions.getFloat("playersVolume");

            }
        }catch(Exception e){
            e.printStackTrace();
            save();
        }
    }

    public static void save(){
        try{
            File file=new File(Minecraft54.OPTIONS_PATH);
            if(!file.exists())
                file.createNewFile();
            JSONObject jsonOptions=new JSONObject();

            jsonOptions.put("fov",FOV);
            jsonOptions.put("renderDistance",RENDER_DISTANCE);
            jsonOptions.put("sensitivity",SENSITIVITY);

            jsonOptions.put("masterVolume",MASTER_VOLUME);
            jsonOptions.put("blocksVolume",BLOCKS_VOLUME);
            jsonOptions.put("playersVolume",PLAYERS_VOLUME);

            Files.writeString(file.toPath(),jsonOptions.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
