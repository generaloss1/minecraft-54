package djankcraft.minecraft54;

import djankcraft.engine.graphics.ShaderProgram;
import djankcraft.engine.math.EulerAngle;
import djankcraft.engine.math.Maths;
import djankcraft.engine.math.Matrix4;
import djankcraft.engine.math.vectors.Vector3;
import djankcraft.engine.utils.Assets;
import djankcraft.minecraft54.generator.Generator;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static org.lwjgl.opengl.GL11C.*;

public class World{


    public Generator generator;
    public long seed;
    public List<Chunk> chunks;
    public String worldPath,name;



    public World(){
        chunks=new ArrayList<>();
    }


    public boolean load(String worldFolder){
        try{
            worldPath=Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_PATH+"/saves/"+worldFolder;
            File worldFile=new File(worldPath);
            if(!worldFile.exists())
                return false;

            File propertiesFile=new File(worldPath+"/properties");
            if(!propertiesFile.exists())
                return false;
            Scanner in=new Scanner(new FileInputStream(propertiesFile));
            this.seed=Long.parseLong(in.nextLine().split("seed: ")[1]);
            this.generator=Generator.fromType.get(in.nextLine().split("generator: ")[1]);
            this.name=in.nextLine().split("name: ")[1];
            in.close();

            File chunksFolder=new File(worldPath+"/chunks");
            if(!chunksFolder.exists())
                chunksFolder.mkdirs();

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void create(String name,Generator generator,long seed){
        this.name=name;
        String worldFolder=name+"_"+UUID.randomUUID();
        try{
            worldPath=Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_PATH+"/saves/"+worldFolder;
            File worldFile=new File(worldPath);
            if(!worldFile.exists()) // create world folder
                worldFile.mkdirs();

            File propertiesFile=new File(worldPath+"/properties");
            if(!propertiesFile.exists()){
                propertiesFile.createNewFile();
                PrintWriter out=new PrintWriter(new FileOutputStream(propertiesFile));
                out.println("seed: "+seed);
                out.println("generator: "+generator.getType());
                out.println("name: "+name);
                out.close();
                this.seed=seed;
                this.generator=generator;
            }

            File chunksFolder=new File(worldPath+"/chunks");
            if(!chunksFolder.exists())
                chunksFolder.mkdirs();

            File statsFolder=new File(worldPath+"/stats");
            if(!statsFolder.exists())
                statsFolder.mkdirs();

            JSONObject stats=new JSONObject();
            stats.put("x",0f);
            stats.put("y",84f);
            stats.put("z",0f);
            stats.put("yaw",0f);
            stats.put("pitch",0f);
            stats.put("roll",0f);
            File statsFile=new File(worldPath+"/stats/"+Minecraft54.ACCOUNT_NAME+".json");
            if(!statsFile.exists())
                statsFile.createNewFile();
            PrintWriter out=new PrintWriter(new FileOutputStream(statsFile));
            out.write(stats.toString());
            out.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void render(){
        ShaderProgram shader=Assets.getShader("chunk");
        shader.bind();
        shader.setUniform("u_world",Controls.CAMERA.getView());
        shader.setUniform("u_proj",Controls.CAMERA.getProjection());
        shader.setUniform("u_texture",Assets.getTexture3d("blocks"));

        shader.setUniform("u_camPos",Controls.CAMERA.getPosition());

        Vector3 camPos=Controls.CAMERA.getPosition();
        if( (getBlock(Maths.floor(camPos.x),Maths.floor(camPos.y),Maths.floor(camPos.z))==Block.WATER.id) || (getBlock(Maths.floor(camPos.x),Maths.floor(camPos.y),Maths.floor(camPos.z))==Block.STILL_WATER.id && camPos.y-Math.floor(camPos.y)<=1-1.5/16) )
            shader.setUniform("underWater",1);
        else
            shader.setUniform("underWater",0);

        int playerChunkX=Math.round(Controls.CAMERA.getPosition().x/Chunk.WIDTH_X);
        int playerChunkZ=Math.round(Controls.CAMERA.getPosition().z/Chunk.WIDTH_Z);

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x>=playerChunkX-Minecraft54.RENDER_DISTANCE && chunk.x<playerChunkX+Minecraft54.RENDER_DISTANCE && chunk.z>=playerChunkZ-Minecraft54.RENDER_DISTANCE && chunk.z<playerChunkZ+Minecraft54.RENDER_DISTANCE){
                shader.setUniform("u_model",new Matrix4().translate(chunk.x*Chunk.WIDTH_X,0,chunk.z*Chunk.WIDTH_Z));
                glEnable(GL_CULL_FACE);
                if(chunk.mesh1!=null)
                    chunk.mesh1.render();
                glDisable(GL_CULL_FACE);
                if(chunk.mesh2!=null)
                    chunk.mesh2.render();
                glEnable(GL_CULL_FACE);
            }
        }

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x>=playerChunkX-Minecraft54.RENDER_DISTANCE && chunk.x<playerChunkX+Minecraft54.RENDER_DISTANCE && chunk.z>=playerChunkZ-Minecraft54.RENDER_DISTANCE && chunk.z<playerChunkZ+Minecraft54.RENDER_DISTANCE){
                shader.setUniform("u_model",new Matrix4().translate(chunk.x*Chunk.WIDTH_X,0,chunk.z*Chunk.WIDTH_Z));
                if(chunk.mesh4!=null)
                    chunk.mesh4.render();
            }
        }

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x>=playerChunkX-Minecraft54.RENDER_DISTANCE && chunk.x<playerChunkX+Minecraft54.RENDER_DISTANCE && chunk.z>=playerChunkZ-Minecraft54.RENDER_DISTANCE && chunk.z<playerChunkZ+Minecraft54.RENDER_DISTANCE){
                shader.setUniform("u_model",new Matrix4().translate(chunk.x*Chunk.WIDTH_X,0,chunk.z*Chunk.WIDTH_Z));
                glDisable(GL_CULL_FACE);
                if(chunk.mesh3!=null)
                    chunk.mesh3.render();
                glEnable(GL_CULL_FACE);
            }
        }

        shader.unbind();
    }


    public void update(){
        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null){
                chunk.update();
                if(!chunk.isInit)
                    chunk.init();
            }
        }

        int playerChunkX=Math.round(Controls.CAMERA.getPosition().x/Chunk.WIDTH_X);
        int playerChunkZ=Math.round(Controls.CAMERA.getPosition().z/Chunk.WIDTH_Z);

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null && (chunk.x<playerChunkX-Minecraft54.RENDER_DISTANCE || chunk.x>=playerChunkX+Minecraft54.RENDER_DISTANCE || chunk.z<playerChunkZ-Minecraft54.RENDER_DISTANCE || chunk.z>=playerChunkZ+Minecraft54.RENDER_DISTANCE)){
                chunk.save();
                chunk.dispose();
                chunks.remove(chunk);
            }
        }
    }

    public void setBlock(int id,int x,int y,int z){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        if(getChunk(chunkX,chunkZ)==null)
            createChunk(chunkX,chunkZ);
        getChunk(chunkX,chunkZ).setBlock(id,fixedIndex(x),y,fixedIndex(z));
    }

    public void setBlock(Block block,int x,int y,int z){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        if(getChunk(chunkX,chunkZ)==null)
            createChunk(chunkX,chunkZ);
        Chunk chunk=getChunk(chunkX,chunkZ);
        if(chunk!=null)
            chunk.setBlock(block,fixedIndex(x),y,fixedIndex(z));
    }

    public int getBlock(int x,int y,int z){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        Chunk chunk=getChunk(chunkX,chunkZ);
        if(chunk==null)
            return 0;
        return chunk.getBlock(fixedIndex(x),y,fixedIndex(z));
    }


    public synchronized Chunk getChunk(int x,int z){
        for(int i=0; i<chunks.size(); i++){
            if(i>=chunks.size())
                continue;
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x==x && chunk.z==z)
                return chunk;
        }
        return null;
    }

    public synchronized int getChunkIndex(int x,int z){
        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x==x && chunk.z==z)
                return i;
        }
        return -1;
    }

    public synchronized boolean createChunk(int x,int z){
        int i=getChunkIndex(x,z);
        if(i!=-1)
            return false;
        chunks.add(new Chunk(this,x,z));
        return true;
    }

    public synchronized void generateChunk(int x,int z,Generator generator,long seed){
        createChunk(x,z);
        Chunk chunk=getChunk(x,z);
        if(chunk==null || chunk.isGenerated)
            return;
        generator.generate(chunk,seed);
    }

    public synchronized void generateChunk(int x,int z){
        generateChunk(x,z,generator,seed);
    }


    public static int fi(int index){
        return index>=0 && index<Chunk.WIDTH_X?
                    index
                :
                    index>0?
                                index-(index/Chunk.WIDTH_X)*Chunk.WIDTH_X
                            :
                                index-(index/Chunk.WIDTH_X-1)*Chunk.WIDTH_X;
    }

    public static int fixedIndex(int index){
        return fi(fi(index));
    }


    public void changedIn(int x,int z){
        Chunk chunk=getChunk(x-1,z);
        if(chunk!=null)
            chunk.isChanged=true;
        chunk=getChunk(x+1,z);
        if(chunk!=null)
            chunk.isChanged=true;
        chunk=getChunk(x,z-1);
        if(chunk!=null)
            chunk.isChanged=true;
        chunk=getChunk(x,z+1);
        if(chunk!=null)
            chunk.isChanged=true;
    }


    public void dispose(){
        for(Chunk chunk:chunks)
            if(chunk!=null)
                chunk.dispose();
        chunks.clear();
    }


    public void save(){
        System.out.println("saving");
        for(Chunk chunk:chunks)
            if(chunk!=null)
                chunk.save();
    }

    public boolean loadChunk(int x,int z){
        try{
            File chunkFile=new File(worldPath+"/chunks/"+x+":"+z);
            if(chunkFile.exists() && chunkFile.canRead()){
                ObjectInputStream in=new ObjectInputStream(new FileInputStream(chunkFile));
                Chunk chunk=new Chunk(this,x,z);
                chunk.isGenerated=in.readBoolean();
                for(int i=0; i<chunk.blocks.length; i++)
                    chunk.blocks[i]=in.readShort();
                in.close();
                chunk.isChanged=true;
                Chunk cchunk=getChunk(x,z);
                if(cchunk!=null)
                    chunks.remove(cchunk);
                chunks.add(chunk);
                changedIn(x,z);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public void saveStats(Player player){
        try{
            File statsFolder=new File(worldPath+"/stats");
            if(!statsFolder.exists())
                statsFolder.mkdirs();

            Vector3 camPos=Controls.CAMERA.getPosition();
            EulerAngle camRot=Controls.CAMERA.getRotation();

            JSONObject stats=new JSONObject();
            stats.put("x",player.hitbox.getPosition().x);
            stats.put("y",player.hitbox.getPosition().y);
            stats.put("z",player.hitbox.getPosition().z);
            stats.put("yaw",camRot.getYaw());
            stats.put("pitch",camRot.getPitch());
            stats.put("roll",camRot.getRoll());

            File statsFile=new File(worldPath+"/stats/"+player.name+".json");
            if(!statsFile.exists())
                statsFile.createNewFile();

            PrintWriter out=new PrintWriter(new FileOutputStream(statsFile));
            out.write(stats.toString());
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean loadStats(Player player){
        try{
            File statsFile=new File(worldPath+"/stats/"+player.name+".json");
            if(!statsFile.exists())
                return false;

            String statsString=Files.readString(statsFile.toPath());
            JSONObject stats=new JSONObject(statsString);

            player.hitbox.getPosition().set(new Vector3(stats.getFloat("x"),stats.getFloat("y"),stats.getFloat("z")));
            Controls.CAMERA.setRotation(stats.getFloat("yaw"),stats.getFloat("pitch"),stats.getFloat("roll"));

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
