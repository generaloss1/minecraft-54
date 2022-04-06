package minecraft54.main.server.generator;

import minecraft54.engine.maths.Maths;
import minecraft54.engine.util.Random;
import minecraft54.main.client.world.Block;
import minecraft54.main.client.world.BlockData;
import minecraft54.main.client.world.Chunk;
import minecraft54.main.Minecraft54;

import java.util.HashMap;

public class Generator{


    public static HashMap<String,Generator> fromType=new HashMap<>();


    private final String type;
    private final GeneratorCallback callback;

    public Generator(String type,GeneratorCallback callback){
        this.type=type;
        this.callback=callback;
        
        fromType.put(type,this);
    }

    public String getType(){
        return type;
    }

    public void generate(Chunk chunk,long seed){
        new Thread(()->{
            if(chunk!=null && !chunk.generation){
                chunk.generation=true;
                callback.generate(chunk,seed);

                chunk.changed(true);
                Chunk cChunk=chunk.world.chunkProvider.getChunk(chunk.x-1,chunk.z);
                if(cChunk!=null)
                    cChunk.changed(true);
                cChunk=chunk.world.chunkProvider.getChunk(chunk.x+1,chunk.z);
                if(cChunk!=null)
                    cChunk.changed(true);
                cChunk=chunk.world.chunkProvider.getChunk(chunk.x,chunk.z-1);
                if(cChunk!=null)
                    cChunk.changed(true);
                cChunk=chunk.world.chunkProvider.getChunk(chunk.x,chunk.z+1);
                if(cChunk!=null)
                    cChunk.changed(true);
                chunk.generation=false;
                chunk.generated=true;
            }
        }).start();
    }



    public static final Generator NORMAL,FLAT,NORMAL_OLD,VOID,TEST_1,TEST_2,TEST_3;

    static{

        TEST_3=new Generator("Test-3 (Caves)",(Chunk chunk,long seed)->{

            byte[][][] tmp=new byte[Chunk.WIDTH_X][Chunk.HEIGHT][Chunk.WIDTH_Z];

            SimplexNoise noise0=new SimplexNoise(50,0.35f,(int)seed);
            SimplexNoise noise1=new SimplexNoise(400,0.4f,(int)seed);
            SimplexNoise noise2=new SimplexNoise(300,0.2f,(int)seed);
            SimplexNoise noise3=new SimplexNoise(30,0.1f,(int)seed);
            SimplexNoise noise4=new SimplexNoise(90,0.1f,(int)seed+1);

            SimplexNoise sandGenNoise=new SimplexNoise(140,0.2f,(int)seed);
            SimplexNoise treeGenNoise=new SimplexNoise(2,0.1f,(int)seed);
            Random treeGenRandom=new Random(seed+chunk.x*1000L+chunk.z);

            for(short lx=0; lx<Chunk.WIDTH_X; lx++)
                for(short lz=0; lz<Chunk.WIDTH_Z; lz++){
                    int gx=chunk.x*Chunk.WIDTH_X+lx;
                    int gz=chunk.z*Chunk.WIDTH_Z+lz;
                    for(int ly=128; ly>=0; ly--){
                        double height=noise1.getNoise(gx,gz)*16+64;
                        double noise3dGen=noise2.getNoise(gx,ly,gz);
                        double noise3dGenCaves=noise3.getNoise(gx,ly,gz);
                        double noise3dGenCaves2=noise4.getNoise(gx,ly,gz);
                        double noiseGenCavesFloor=noise3.getNoise(gx,gz)*5+5;
                        if((noise3dGen>ly/(height/2) || ly<=height) && (noise3dGenCaves<ly/((height*2.6)/2f) || noise3dGenCaves2>0.25 || ly<noiseGenCavesFloor))
                            tmp[lx][ly][lz]=1;
                    }
                }

            for(short lx=0; lx<Chunk.WIDTH_X; lx++)
                for(short lz=0; lz<Chunk.WIDTH_Z; lz++){
                    chunk.setBlock(Minecraft54.BEDROCK,lx,0,lz,false);
                    if(treeGenRandom.randomBoolean(0.8))
                        chunk.setBlock(Minecraft54.BEDROCK,lx,1,lz,false);
                    if(treeGenRandom.randomBoolean(0.6))
                        chunk.setBlock(Minecraft54.BEDROCK,lx,2,lz,false);
                    if(treeGenRandom.randomBoolean(0.4))
                        chunk.setBlock(Minecraft54.BEDROCK,lx,3,lz,false);
                    if(treeGenRandom.randomBoolean(0.2))
                        chunk.setBlock(Minecraft54.BEDROCK,lx,4,lz,false);
                }

            for(short lx=0; lx<Chunk.WIDTH_X; lx++)
                for(short lz=0; lz<Chunk.WIDTH_Z; lz++){
                    int gx=chunk.x*Chunk.WIDTH_X+lx;
                    int gz=chunk.z*Chunk.WIDTH_Z+lz;
                    for(int ly=128; ly>=0; ly--){
                        if(tmp[lx][ly][lz]==1){
                            boolean sand=sandGenNoise.getNoise(gx,gz)>0.5;

                            if(sand && ly<64+noise0.getNoise(gx,gz)*2){
                                int height=Maths.round(noise0.getNoise(lx,lz)*(5-3)+3);
                                for(int dly=ly; dly>=ly-height; dly--){
                                    if(tmp[lx][dly][lz]==0)
                                        break;
                                    chunk.setBlock(Minecraft54.SAND,lx,dly,lz,false);
                                }
                            }else{
                                if(ly>=63)
                                    chunk.setBlock(Minecraft54.GRASS_BLOCK,lx,ly,lz,false);
                                else
                                    chunk.setBlock(Minecraft54.DIRT,lx,ly,lz,false);
                                int height=Maths.round(noise0.getNoise(lx,lz)*(5-3)+3);
                                if(ly!=0){
                                    for(int dly=ly-1; dly>=ly-height; dly--){
                                        if(tmp[lx][dly][lz]==0)
                                            break;
                                        chunk.setBlock(Minecraft54.DIRT,lx,dly,lz,false);
                                    }
                                }

                                if(ly>=64){
                                    if(treeGenNoise.getNoise(gx,gz)>0.94)
                                        spawnTree(chunk,lx,lz,gx,ly,gz,treeGenRandom,Minecraft54.LOG.getBlockData((short)treeGenRandom.random(0,1)),Minecraft54.LEAVES.getBlockData());
                                    else if(treeGenNoise.getNoise(gx,gz)>0.75){
                                        if(treeGenRandom.random()>0.1)
                                            chunk.setBlock(Minecraft54.GRASS,lx,ly+1,lz,false);
                                        else
                                            chunk.setBlock(Minecraft54.FLOWER.getBlockData((short)treeGenRandom.random(0,1)),lx,ly+1,lz,false);
                                    }
                                }
                            }

                            for(int ly2=ly; ly2>=0; ly2--)
                                if(tmp[lx][ly2][lz]==1 && chunk.getBlockId(lx,ly2,lz)==0)
                                    chunk.setBlock(Minecraft54.STONE.getBlockData(),lx,ly2,lz,false);

                            break;
                        }else if(ly==63)
                            chunk.setBlock(Minecraft54.WATER_STILL,lx,ly,lz,false);
                        else if(ly<63)
                            chunk.setBlock(Minecraft54.WATER,lx,ly,lz,false);
                    }
                }

        });

        TEST_2=new Generator("Test-2",(Chunk chunk,long seed)->{

            SimplexNoise noise0=new SimplexNoise(50,0.35f,(int)seed);
            SimplexNoise noise1=new SimplexNoise(400,0.4f,(int)seed);
            SimplexNoise noise2=new SimplexNoise(300,0.4f,(int)seed);

            int[][] heightMap=new int[Chunk.WIDTH_X][Chunk.WIDTH_Z];

            for(short lx=0; lx<Chunk.WIDTH_X; lx++)
                for(short lz=0; lz<Chunk.WIDTH_Z; lz++){
                    int gx=chunk.x*Chunk.WIDTH_X+lx;
                    int gz=chunk.z*Chunk.WIDTH_Z+lz;
                    for(int ly=128; ly>=0; ly--){
                        double height=noise1.getNoise(gx,gz)*16+64;
                        double noise3dGen=noise2.getNoise(gx,ly,gz);
                        if(noise3dGen>ly/(height/3) || ly<=height){
                            if(heightMap[lx][lz]==0)
                                heightMap[lx][lz]=ly;
                        }
                    }
                }

            SimplexNoise sandGenNoise=new SimplexNoise(140,0.2f,(int)seed);
            SimplexNoise treeGenNoise=new SimplexNoise(2,0.1f,(int)seed);
            Random treeGenRandom=new Random(seed+chunk.x*1000L+chunk.z);

            for(short lx=0; lx<Chunk.WIDTH_X; lx++)
                for(short lz=0; lz<Chunk.WIDTH_Z; lz++){
                    int gx=chunk.x*Chunk.WIDTH_X+lx;
                    int gz=chunk.z*Chunk.WIDTH_Z+lz;
                    double noise0Gen=noise0.getNoise(gx,gz);
                    int height=heightMap[lx][lz];
                    boolean sand=sandGenNoise.getNoise(gx,gz)>0.5;

                    if((height>63 && !sand) || height>66)
                        chunk.setBlock(Minecraft54.GRASS_BLOCK.getBlockData(),lx,height,lz,false);
                    else if(sand)
                        chunk.setBlock(Minecraft54.SAND.getBlockData(),lx,height,lz,false);
                    else
                        chunk.setBlock(Minecraft54.DIRT.getBlockData(),lx,height,lz,false);

                    for(int ly=0; ly<height; ly++){
                        if(height-ly>=noise0Gen*3+4)
                            chunk.setBlock(Minecraft54.STONE.getBlockData(),lx,ly,lz,false);
                        else if(height>66 || !sand)
                            chunk.setBlock(Minecraft54.DIRT.getBlockData(),lx,ly,lz,false);
                        else
                            chunk.setBlock(Minecraft54.SAND.getBlockData(),lx,ly,lz,false);
                    }

                    if(height<64){
                        chunk.setBlockId(Minecraft54.WATER_STILL.getId(),lx,64,lz,false);
                        if(height<63)
                            for(int ly=63; ly>height; ly--)
                                chunk.setBlockId(Minecraft54.WATER.getId(),lx,ly,lz,false);
                    }else if(height>66){
                        if(treeGenNoise.getNoise(gx,gz)>0.94)
                            spawnTree(chunk,lx,lz,gx,height,gz,treeGenRandom,Minecraft54.LOG.getBlockData((short)treeGenRandom.random(0,1)),Minecraft54.LEAVES.getBlockData());
                        else if(treeGenNoise.getNoise(gx,gz)>0.75){
                            if(treeGenRandom.random()>0.1)
                                chunk.setBlock(Minecraft54.GRASS,lx,height+1,lz,false);
                            else
                                chunk.setBlock(Minecraft54.FLOWER.getBlockData((short)treeGenRandom.random(0,1)),lx,height+1,lz,false);
                        }

                    }
                }

        });

        TEST_1=new Generator("Test-1",(Chunk chunk,long seed)->{

            Random treeGenerationRandom=new Random(seed+chunk.x*1000L+chunk.z);
            minecraft54.engine.maths.SimplexNoise noise=new minecraft54.engine.maths.SimplexNoise();
            noise.genGrad(seed);

            for(int lx=0; lx<Chunk.WIDTH_X; lx++)
                for(int lz=0; lz<Chunk.WIDTH_Z; lz++){
                    int gx=chunk.x*Chunk.WIDTH_X+lx;
                    int gz=chunk.z*Chunk.WIDTH_Z+lz;

                    double noiseGeneration=noise.generate(gx,gz,20,0.46f,1/400f)/2+0.5;
                    int height=Maths.round(noiseGeneration*128)+16;

                    Block block;
                    if(height<50)
                        block=Minecraft54.DIRT;
                    else if(height<64)
                        block=Minecraft54.SAND;

                    else if(height<70)
                        block=Minecraft54.SAND;

                    else// if(height<90)
                        block=Minecraft54.GRASS_BLOCK;

                    /*else if(height<115)
                        block=Minecraft54.STONE;
                    else
                        block=Minecraft54.STONE;*/

                    chunk.setBlock(block.getBlockData(),lx,height,lz,false);

                    if(block.getId()==Minecraft54.GRASS_BLOCK.getId()){
                        block=Minecraft54.DIRT;
                        if(treeGenerationRandom.randomBoolean(0.02))
                            spawnTree(chunk,lx,lz,gx,height,gz,treeGenerationRandom,Minecraft54.LOG.getBlockData((short)treeGenerationRandom.random(0,1)),Minecraft54.LEAVES.getBlockData());
                        else if(treeGenerationRandom.randomBoolean(0.08))
                            chunk.setBlock(Minecraft54.GRASS.getBlockData(),lx,height+1,lz,false);
                    }

                    for(int ly=height-1; ly>=0; ly--)
                        chunk.setBlock(block.getBlockData(),lx,ly,lz,false);

                }

            // fill water y<64
            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<=64; j++)
                        if(chunk.getBlockId(i,j,k)==Minecraft54.AIR.getId())
                            chunk.setBlockId(Minecraft54.WATER.getId(),i,j,k,false);

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<=63; j++)
                        if(chunk.getBlockId(i,j,k)==Minecraft54.WATER.getId() && (chunk.getBlockId(i,j+1,k)==Minecraft54.AIR.getId() || chunk.getBlockId(i,j+1,k)==-1))
                            chunk.setBlockId(Minecraft54.WATER_STILL.getId(),i,j,k,false);

        });

        /*NORMAL=new Generator("Normal",(Chunk chunk,long seed)->{

            SimplexNoise noise1=new SimplexNoise(1000,0.1,(int)seed+1);
            SimplexNoise noise2=new SimplexNoise(100,0.1,(int)seed+2);
            SimplexNoise noise3=new SimplexNoise(2000,0.1,(int)seed+3);
            SimplexNoise noise4=new SimplexNoise(100,0.1,(int)seed+4);

            int height=64;

            for(int lx=0; lx<Chunk.WIDTH_X; lx++)
                for(int lz=0; lz<Chunk.WIDTH_Z; lz++){
                    int gx=chunk.x*Chunk.WIDTH_X+lx;
                    int gz=chunk.z*Chunk.WIDTH_Z+lz;

                    double gen1=noise1.getNoise(gx,gz);
                    double gen2=noise2.getNoise(gx,gz);
                    double gen3=noise3.getNoise(gx,gz);
                    double gen4=noise4.getNoise(gx,gz);

                    int block=Block.GRASS_BLOCK.id;

                    int y1=(int)Math.round(height-8+gen1*100+gen2*10-(100+10)/2f);
                    if(y1>64)
                        y1=64;

                    int y2=0;
                    if(gen3>0.46 && gen3<0.54){
                        y2=-(int)Math.round(gen3*15);
                        block=Block.SAND.id;
                    }

                    int y=y1+y2;

                    y=Math.max(y,0);
                    chunk.setBlockIdDataWoPC((short)block,(short)0,lx,y,lz);
                }
        });*/



        VOID=new Generator("Void",(Chunk chunk,long seed)->{
            if(chunk.x==0 && chunk.z==0)
                chunk.setBlock(Minecraft54.STONE.getBlockData(),0,0,0,false);
        });



        NORMAL=new Generator("Normal",(Chunk chunk,long seed)->{
            SimplexNoise noise1=new SimplexNoise(10000,0.1,(int)seed);
            SimplexNoise noise2=new SimplexNoise(800,0.1,(int)seed);
            SimplexNoise noise3=new SimplexNoise(200,0.1,(int)seed);
            SimplexNoise noise4=new SimplexNoise(70,0.1,(int)seed);
            SimplexNoise noise5=new SimplexNoise(40,0.1,(int)seed);
            SimplexNoise noise6=new SimplexNoise(20,0.1,(int)seed);
            SimplexNoise noise7=new SimplexNoise(200,0.1,(int)seed);
            Random random=new Random(seed+chunk.x*1000L+chunk.z);

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++){
                    int x=chunk.x*Chunk.WIDTH_X+i;
                    int z=chunk.z*Chunk.WIDTH_Z+k;

                    int y=(int)Math.round(noise1.getNoise(x,z)*30+noise2.getNoise(x,z)*10+noise3.getNoise(x,z)*10+noise4.getNoise(x,z)*5+noise5.getNoise(x,z)*4)-(30+10+10+5+4)/2+64;
                    int nor=y-64;
                    //if(nor>0){
                    //    y-=Math.min(nor,1);
                    //}
                    if(nor<0)
                        y-=Math.pow(1+(float)nor/(30+10+10+5+4),8)*nor;

                    boolean sand=y<65;

                    if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                        chunk.setBlockId(Minecraft54.SAND.getId(),i,y,k,false);
                    else if(y<64){
                        chunk.setBlockId(Minecraft54.DIRT.getId(),i,y,k,false);
                    }else{
                        chunk.setBlockId(Minecraft54.GRASS_BLOCK.getId(),i,y,k,false);

                        if(noise7.getNoise(x,z)>0.5 && random.randomBoolean(0.02))
                            spawnTree(chunk,i,k,x,y,z,random,Minecraft54.LOG.getBlockData((short)random.random(0,1)),Minecraft54.LEAVES.getBlockData());
                        else if(random.randomBoolean(0.1) && nor>1)
                            chunk.setBlockId(Minecraft54.GRASS.getId(),i,y+1,k,false);
                    }

                    //System.out.println(noise6.getNoise(x,z));

                    for(int a=0; a+1<3+noise6.getNoise(x,z)*4; a++){
                        y--;
                        if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                            chunk.setBlockId(Minecraft54.SAND.getId(),i,y,k,false);
                        else
                            chunk.setBlockId(Minecraft54.DIRT.getId(),i,y,k,false);
                    }
                    for(y--; y>=0; y--)
                        chunk.setBlockId(Minecraft54.STONE.getId(),i,y,k,false);

                    // set bedrock

                    //chunk.setBlock(10,i,0,k);
                    //if(Math.random()>0.4)  chunk.setBlock(10,i,4,k);
                    //if(Math.random()>0.2)  chunk.setBlock(10,i,3,k);
                    //if(Math.random()>0.1)  chunk.setBlock(10,i,2,k);
                    //if(Math.random()>0.05) chunk.setBlock(10,i,1,k);

                }

            // fill water y<64
            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlockId(i,j,k)==Minecraft54.AIR.getId() && j<=64)
                            chunk.setBlockId(Minecraft54.WATER.getId(),i,j,k,false);

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlockId(i,j,k)==Minecraft54.WATER.getId() && (chunk.getBlockId(i,j+1,k)==Minecraft54.AIR.getId() || chunk.getBlockId(i,j+1,k)==-1))
                            chunk.setBlockId(Minecraft54.WATER_STILL.getId(),i,j,k,false);
        });



        NORMAL_OLD=new Generator("Old Normal",(Chunk chunk,long seed)->{
            SimplexNoise noise2=new SimplexNoise(800,0.1,(int)seed);
            SimplexNoise noise3=new SimplexNoise(200,0.1,(int)seed);
            SimplexNoise noise4=new SimplexNoise(70,0.1,(int)seed);
            SimplexNoise noise5=new SimplexNoise(40,0.1,(int)seed);
            SimplexNoise noise6=new SimplexNoise(20,0.1,(int)seed);
            SimplexNoise noise7=new SimplexNoise(120,0.1,(int)seed);
            Random random=new Random(seed+chunk.x*1000L+chunk.z);

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++){
                    int x=chunk.x*Chunk.WIDTH_X+i;
                    int z=chunk.z*Chunk.WIDTH_Z+k;

                    int y=(int)Math.round(noise2.getNoise(x,z)*40+noise3.getNoise(x,z)*30+noise4.getNoise(x,z)*5+noise5.getNoise(x,z)*4)-(40+30+5+4)/2+64;
                    int nor=y-64;
                    if(nor>0){
                        y-=Math.min(nor,2);
                    }if(nor<0)
                        y-=Math.pow(1+(float)nor/(40+30+5+4),0.7)*nor;

                    boolean sand=y<65;

                    if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                        chunk.setBlockId(Minecraft54.SAND.getId(),i,y,k,false);
                    else if(y<64){
                        chunk.setBlockId(Minecraft54.DIRT.getId(),i,y,k,false);
                    }else{
                        chunk.setBlockId(Minecraft54.GRASS_BLOCK.getId(),i,y,k,false);

                        if(noise7.getNoise(x,z)>0.3 && random.randomBoolean(0.02))
                            spawnTree(chunk,i,k,x,y,z,random,Minecraft54.LOG.getBlockData((short)random.random(0,1)),Minecraft54.LEAVES.getBlockData());
                        else if(random.randomBoolean(0.1) && nor>1)
                            chunk.setBlockId(Minecraft54.GRASS.getId(),i,y+1,k,false);
                    }

                    //System.out.println(noise6.getNoise(x,z));

                    for(int a=0; a+1<3+noise6.getNoise(x,z)*4; a++){
                        y--;
                        if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                            chunk.setBlockId(Minecraft54.SAND.getId(),i,y,k,false);
                        else
                            chunk.setBlockId(Minecraft54.DIRT.getId(),i,y,k,false);
                    }
                    for(y--; y>=0; y--)
                        chunk.setBlockId(Minecraft54.STONE.getId(),i,y,k,false);

                    // set bedrock

                    //chunk.setBlock(10,i,0,k);
                    //if(Math.random()>0.4)  chunk.setBlock(10,i,4,k);
                    //if(Math.random()>0.2)  chunk.setBlock(10,i,3,k);
                    //if(Math.random()>0.1)  chunk.setBlock(10,i,2,k);
                    //if(Math.random()>0.05) chunk.setBlock(10,i,1,k);

                }

            // fill water y<64
            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlockId(i,j,k)==0 && j<=64)
                            chunk.setBlockId(Minecraft54.WATER.getId(),i,j,k,false);
                    // set height map

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlockId(i,j,k)==Minecraft54.WATER.getId() && (chunk.getBlockId(i,j+1,k)==0 || chunk.getBlockId(i,j+1,k)==-1))
                            chunk.setBlockId(Minecraft54.WATER_STILL.getId(),i,j,k,false);
        });



        FLAT=new Generator("Flat",(Chunk chunk,long seed)->{
            int height=3;

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++){
                    chunk.setBlockId(Minecraft54.GRASS_BLOCK.getId(),i,height,k,false);
                    for(int y=height-1; y>=0; y--)
                        chunk.setBlockId(Minecraft54.DIRT.getId(),i,y,k,false);
                    //chunk.setBlock(Block.BEDROCK,i,0,k);
                }
        });
    }



    public static void spawnTree(Chunk chunk,int i,int k,int x,int y,int z,Random random,BlockData block1,BlockData block2){
        int tree_height=random.random(4,7);
        for(int j=0; j<tree_height; j++)
            chunk.setBlock(block1,i,y+j+1,k,false);

        chunk.world.setBlock(block2,x  ,y+tree_height+1,z  ,false);
        chunk.world.setBlock(block2,x+1,y+tree_height+1,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height+1,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height+1,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height+1,z-1,false);

        chunk.world.setBlock(block2,x+1,y+tree_height  ,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height  ,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height  ,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height  ,z-1,false);
        chunk.world.setBlock(block2,x+1,y+tree_height  ,z+1,false);
        chunk.world.setBlock(block2,x+1,y+tree_height  ,z-1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height  ,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height  ,z-1,false);

        chunk.world.setBlock(block2,x+1,y+tree_height-1,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height-1,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height-1,z-1,false);
        chunk.world.setBlock(block2,x+1,y+tree_height-1,z+1,false);
        chunk.world.setBlock(block2,x+1,y+tree_height-1,z-1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z-1,false);

        chunk.world.setBlock(block2,x  ,y+tree_height-1,z+2,false);
        chunk.world.setBlock(block2,x  ,y+tree_height-1,z-2,false);
        chunk.world.setBlock(block2,x-2,y+tree_height-1,z  ,false);
        chunk.world.setBlock(block2,x+2,y+tree_height-1,z  ,false);

        chunk.world.setBlock(block2,x+1,y+tree_height-2,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height-2,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z  ,false);
        chunk.world.setBlock(block2,x  ,y+tree_height-2,z-1,false);
        chunk.world.setBlock(block2,x+1,y+tree_height-2,z+1,false);
        chunk.world.setBlock(block2,x+1,y+tree_height-2,z-1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z+1,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z-1,false);

        chunk.world.setBlock(block2,x+1,y+tree_height-2,z+2,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z+2,false);
        chunk.world.setBlock(block2,x+1,y+tree_height-2,z-2,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z-2,false);
        chunk.world.setBlock(block2,x-2,y+tree_height-2,z+1,false);
        chunk.world.setBlock(block2,x-2,y+tree_height-2,z-1,false);
        chunk.world.setBlock(block2,x+2,y+tree_height-2,z+1,false);
        chunk.world.setBlock(block2,x+2,y+tree_height-2,z-1,false);

        chunk.world.setBlock(block2,x  ,y+tree_height-2,z+2,false);
        chunk.world.setBlock(block2,x  ,y+tree_height-2,z-2,false);
        chunk.world.setBlock(block2,x-2,y+tree_height-2,z  ,false);
        chunk.world.setBlock(block2,x+2,y+tree_height-2,z  ,false);

        chunk.world.setBlock(block2,x+1,y+tree_height-1,z+2,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z+2,false);
        chunk.world.setBlock(block2,x+1,y+tree_height-1,z-2,false);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z-2,false);
        chunk.world.setBlock(block2,x-2,y+tree_height-1,z+1,false);
        chunk.world.setBlock(block2,x-2,y+tree_height-1,z-1,false);
        chunk.world.setBlock(block2,x+2,y+tree_height-1,z+1,false);
        chunk.world.setBlock(block2,x+2,y+tree_height-1,z-1,false);

        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-1,z+2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-1,z+2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-1,z-2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-1,z-2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-2,z+2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-2,z-2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-2,z+2,false);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-2,z-2,false);
    }



}
