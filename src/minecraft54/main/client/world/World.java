package minecraft54.main.client.world;

import minecraft54.engine.graphics.ShaderProgram;
import minecraft54.engine.math.EulerAngle;
import minecraft54.engine.math.Maths;
import minecraft54.engine.math.Matrix4;
import minecraft54.engine.math.vectors.Vector3f;
import minecraft54.engine.math.vectors.Vector3d;
import minecraft54.engine.utils.Assets;
import minecraft54.engine.utils.FastArrayList;
import minecraft54.main.client.controls.Controls;
import minecraft54.main.util.GameMode;
import minecraft54.main.Minecraft54;
import minecraft54.main.client.entity.Player;
import minecraft54.main.server.generator.Generator;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11C.*;

public class World{


    public Generator generator;
    public long seed;
    public FastArrayList<Chunk> chunks;
    public String worldPath,name;
    public int renderSectionsCount,renderChunksCount;



    public World(){
        chunks=new FastArrayList<>();
    }


    public boolean load(String worldFolder){
        try{
            worldPath=Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_FOLDER+"/saves/"+worldFolder;
            File worldFile=new File(worldPath);
            if(!worldFile.exists() || !worldFile.isDirectory())
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
        String worldFolder=getWorldFolderName(name);
        try{
            worldPath=Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_FOLDER+"/saves/"+worldFolder;
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
            stats.put("gameMode",GameMode.SURVIVAL);
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

    public String getWorldFolderName(String name){
        if(!new File(Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_FOLDER+"/saves/"+name).exists())
            return name;

        int i=1;
        while(new File(Minecraft54.HOME_PATH+"/"+Minecraft54.GAME_FOLDER+"/saves/"+name+" ("+i+")").exists())
            i++;

        return name+" ("+i+")";
    }


    public void render(){
        renderSectionsCount=0;
        renderChunksCount=0;
        Vector3d camPos=Controls.getPosition();

        ShaderProgram shader=Assets.getShader("chunk");
        shader.bind();
        shader.setUniform("u_world",Controls.CAMERA.getView());
        shader.setUniform("u_proj",Controls.CAMERA.getProjection());
        shader.setUniform("u_texture",Assets.getTexture3d("blocks"));

        shader.setUniform("u_camPos",new Vector3f(camPos));


        if( (getBlockId(Maths.floor(camPos.x),Maths.floor(camPos.y),Maths.floor(camPos.z))==Minecraft54.WATER.getId()) || (getBlockId(Maths.floor(camPos.x),Maths.floor(camPos.y),Maths.floor(camPos.z))==Minecraft54.WATER_STILL.getId() && camPos.y-Math.floor(camPos.y)<=1) )
            shader.setUniform("underWater",1);
        else
            shader.setUniform("underWater",0);

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null){
                renderChunksCount++;
                for(int j=0; j<chunk.sections.length; j++){
                   ChunkSection section=chunk.sections[j];
                   if(section.notAirBlockCount!=0 && Controls.frustum.isBoxInFrustum(chunk.x*Chunk.WIDTH_X,j*ChunkSection.HEIGHT,chunk.z*Chunk.WIDTH_Z,(chunk.x+1)*Chunk.WIDTH_X,(j+1)*ChunkSection.HEIGHT,(chunk.z+1)*Chunk.WIDTH_Z)){
                       shader.setUniform("u_model",new Matrix4().translate(
                               (float)(chunk.x*Chunk.WIDTH_X-camPos.x), j*ChunkSection.HEIGHT, (float)(chunk.z*Chunk.WIDTH_Z-camPos.z)
                       ));
                       glEnable(GL_CULL_FACE);
                       if(section.mesh1!=null)
                           section.mesh1.render();
                       glDisable(GL_CULL_FACE);
                       if(section.mesh2!=null)
                           section.mesh2.render();
                       glEnable(GL_CULL_FACE);
                       renderSectionsCount++;
                   }
               }
            }

        }

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null){
                for(int j=0; j<chunk.sections.length; j++){
                    ChunkSection section=chunk.sections[j];
                    if(section.notAirBlockCount!=0 && Controls.frustum.isBoxInFrustum(chunk.x*Chunk.WIDTH_X,j*ChunkSection.HEIGHT,chunk.z*Chunk.WIDTH_Z,(chunk.x+1)*Chunk.WIDTH_X,(j+1)*ChunkSection.HEIGHT,(chunk.z+1)*Chunk.WIDTH_Z)){
                        shader.setUniform("u_model",new Matrix4().translate(
                                (float)(chunk.x*Chunk.WIDTH_X-camPos.x), j*ChunkSection.HEIGHT, (float)(chunk.z*Chunk.WIDTH_Z-camPos.z)
                        ));
                        if(section.mesh4!=null)
                            section.mesh4.render();
                    }
                }
            }
        }

        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            // && chunk.x>=playerChunkX-Minecraft54.RENDER_DISTANCE && chunk.x<playerChunkX+Minecraft54.RENDER_DISTANCE && chunk.z>=playerChunkZ-Minecraft54.RENDER_DISTANCE && chunk.z<playerChunkZ+Minecraft54.RENDER_DISTANCE
            if(chunk!=null){
                for(int j=0; j<chunk.sections.length; j++){
                    ChunkSection section=chunk.sections[j];
                    if(section.notAirBlockCount!=0 && Controls.frustum.isBoxInFrustum(chunk.x*Chunk.WIDTH_X,j*ChunkSection.HEIGHT,chunk.z*Chunk.WIDTH_Z,(chunk.x+1)*Chunk.WIDTH_X,(j+1)*ChunkSection.HEIGHT,(chunk.z+1)*Chunk.WIDTH_Z)){
                        shader.setUniform("u_model",new Matrix4().translate(
                                (float)(chunk.x*Chunk.WIDTH_X-camPos.x), j*ChunkSection.HEIGHT, (float)(chunk.z*Chunk.WIDTH_Z-camPos.z)
                        ));
                        glDisable(GL_CULL_FACE);
                        if(section.mesh3!=null)
                            section.mesh3.render();
                        glEnable(GL_CULL_FACE);
                    }
                }
            }
        }

        shader.unbind();
    }



    public void update(){
        new Thread(()->{
            int playerChunkX=Maths.round(Controls.getPosition().x/Chunk.WIDTH_X);
            int playerChunkZ=Maths.round(Controls.getPosition().z/Chunk.WIDTH_Z);

            for(int i=0; i<chunks.size(); i++){
                Chunk chunk=chunks.get(i);
                if(chunk!=null && (chunk.x<playerChunkX-Minecraft54.RENDER_DISTANCE-1 || chunk.x>=playerChunkX+Minecraft54.RENDER_DISTANCE+1 || chunk.z<playerChunkZ-Minecraft54.RENDER_DISTANCE-1 || chunk.z>=playerChunkZ+Minecraft54.RENDER_DISTANCE+1)){
                    chunk.save();

                    chunks.remove(chunk);
                }
            }
        }).start();
    }

    public void setBlock(short id,int x,int y,int z,boolean updateNeighbors){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        if(getChunk(chunkX,chunkZ)==null)
            createChunk(chunkX,chunkZ);
        getChunk(chunkX,chunkZ).setBlockId(id,fixedIndex(x),y,fixedIndex(z),updateNeighbors);
    }

    public void setBlock(BlockData block,int x,int y,int z,boolean updateNeighbors){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        createChunk(chunkX,chunkZ).setBlock(block,fixedIndex(x),y,fixedIndex(z),updateNeighbors);
    }

    public void setBlock(short id,short data,int x,int y,int z,boolean updateNeighbors){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        createChunk(chunkX,chunkZ).setBlockIdData(id,data,fixedIndex(x),y,fixedIndex(z),updateNeighbors);
    }

    public void setBlockId(short id,int x,int y,int z,boolean updateNeighbors){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        createChunk(chunkX,chunkZ).setBlockId(id,fixedIndex(x),y,fixedIndex(z),updateNeighbors);
    }

    public void setBlockData(short data,int x,int y,int z,boolean updateNeighbors){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        createChunk(chunkX,chunkZ).setBlockData(data,fixedIndex(x),y,fixedIndex(z),updateNeighbors);
    }

    public short getBlockId(int x,int y,int z){
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        Chunk chunk=getChunk(chunkX,chunkZ);
        if(chunk==null)
            return 0;
        return chunk.getBlockId(fixedIndex(x),y,fixedIndex(z));
    }

    public short getBlockData(int x,int y,int z){
        if(y<0 || y>=Chunk.HEIGHT)
            return 0;
        int chunkX=Maths.floor((float)x/Chunk.WIDTH_X);
        int chunkZ=Maths.floor((float)z/Chunk.WIDTH_Z);
        Chunk chunk=getChunk(chunkX,chunkZ);
        if(chunk==null)
            return 0;
        return chunk.getBlockData(fixedIndex(x),y,fixedIndex(z));
    }


    public Chunk getChunk(int x,int z){
        for(int i=0; i<chunks.size(); i++){
            if(i>=chunks.size())
                continue;
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x==x && chunk.z==z)
                return chunk;
        }
        return null;
    }

    public int getChunkIndex(int x,int z){
        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null && chunk.x==x && chunk.z==z)
                return i;
        }
        return -1;
    }

    public Chunk createChunk(int x,int z){
        Chunk chunk=getChunk(x,z);
        if(chunk!=null)
            return chunk;
        chunk=new Chunk(this,x,z);
        chunks.add(chunk);
        return chunk;
    }

    public void generateChunk(int x,int z,Generator generator,long seed){
        createChunk(x,z);
        Chunk chunk=getChunk(x,z);
        if(chunk==null || chunk.generated)
            return;
        generator.generate(chunk,seed);
    }

    public void generateChunk(int x,int z){
        generateChunk(x,z,generator,seed);
    }


    public static int fixedIndex(int index){
        return index & (Chunk.WIDTH_X-1);
    }


    /*public void changedIn(int x,int z){
        Chunk chunk=getChunk(x-1,z);
        if(chunk!=null)
            chunk.changed(true);
        chunk=getChunk(x+1,z);
        if(chunk!=null)
            chunk.changed(true);
        chunk=getChunk(x,z-1);
        if(chunk!=null)
            chunk.changed(true);
        chunk=getChunk(x,z+1);
        if(chunk!=null)
            chunk.changed(true);
    }*/


    public void dispose(){
        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null)
                chunk.dispose();
        }
        chunks.clear();
    }


    /*public void save(){
        System.out.println("saving");
        for(int i=0; i<chunks.size(); i++){
            Chunk chunk=chunks.get(i);
            if(chunk!=null)
                chunk.save();
        }
    }*/

    public boolean loadChunk(int x,int z){
        try{
            Chunk cChunk=getChunk(x,z);
            if(cChunk!=null)
                return false;
            File chunkFile=new File(worldPath+"/chunks/"+x+":"+z);
            if(chunkFile.exists() && chunkFile.canRead()){
                ObjectInputStream in=new ObjectInputStream(new FileInputStream(chunkFile));
                Chunk chunk=new Chunk(this,x,z);

                chunk.generated=in.readBoolean();
                for(ChunkSection section:chunk.sections){
                    section.notAirBlockCount=in.readShort();
                    for(int i=0; i<section.blocks.length; i++)
                        section.blocks[i]=in.readInt();
                }

                in.close();


                chunks.add(chunk);
                chunk.changed(true);

                cChunk=chunk.world.getChunk(chunk.x-1,chunk.z);
                if(cChunk!=null)
                    cChunk.changed(true);
                cChunk=chunk.world.getChunk(chunk.x+1,chunk.z);
                if(cChunk!=null)
                    cChunk.changed(true);
                cChunk=chunk.world.getChunk(chunk.x,chunk.z-1);
                if(cChunk!=null)
                    cChunk.changed(true);
                cChunk=chunk.world.getChunk(chunk.x,chunk.z+1);
                if(cChunk!=null)
                    cChunk.changed(true);
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

            EulerAngle camRot=Controls.CAMERA.getRotation();

            JSONObject stats=new JSONObject();
            stats.put("x",player.getHitbox().getPosition().x);
            stats.put("y",player.getHitbox().getPosition().y);
            stats.put("z",player.getHitbox().getPosition().z);
            stats.put("yaw",camRot.getYaw());
            stats.put("pitch",camRot.getPitch());
            stats.put("roll",camRot.getRoll());
            stats.put("gameMode",player.gameMode());

            File statsFile=new File(worldPath+"/stats/"+player.getName()+".json");
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
            File statsFile=new File(worldPath+"/stats/"+player.getName()+".json");
            if(!statsFile.exists())
                return false;

            String statsString=Files.readString(statsFile.toPath());
            JSONObject stats=new JSONObject(statsString);

            player.getHitbox().getPosition().set(new Vector3f(stats.getFloat("x"),stats.getFloat("y"),stats.getFloat("z")));
            Controls.CAMERA.setRotation(stats.getFloat("yaw"),stats.getFloat("pitch"),stats.getFloat("roll"));
            player.setGameMode(GameMode.valueOf(stats.getString("gameMode")));

            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
