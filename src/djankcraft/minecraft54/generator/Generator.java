package djankcraft.minecraft54.generator;

import djankcraft.engine.math.Maths;
import djankcraft.engine.utils.Random;
import djankcraft.minecraft54.Block;
import djankcraft.minecraft54.Chunk;

import java.util.HashMap;

public class Generator{
    
    
    public static HashMap<String,Generator> fromType=new HashMap<>();
    

    private final GeneratorCallback callback;
    private String type;

    public Generator(String type,GeneratorCallback callback){
        this.type=type;
        this.callback=callback;
        
        fromType.put(type,this);
    }

    public void generate(Chunk chunk,long seed){
        if(chunk!=null){
            callback.generate(chunk,seed);
            chunk.isGenerated=true;
        }
    }

    public String getType(){
        return type;
    }


    public static void spawnTree(Chunk chunk,int i,int k,int x,int y,int z,Random random,Block block1,Block block2){
        int tree_height=random.random(4,7);
        for(int j=0; j<tree_height; j++)
            chunk.setBlock(block1,i,y+j+1,k);

        chunk.world.setBlock(block2,x  ,y+tree_height+1,z  );
        chunk.world.setBlock(block2,x+1,y+tree_height+1,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height+1,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height+1,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height+1,z-1);

        chunk.world.setBlock(block2,x+1,y+tree_height  ,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height  ,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height  ,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height  ,z-1);
        chunk.world.setBlock(block2,x+1,y+tree_height  ,z+1);
        chunk.world.setBlock(block2,x+1,y+tree_height  ,z-1);
        chunk.world.setBlock(block2,x-1,y+tree_height  ,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height  ,z-1);

        chunk.world.setBlock(block2,x+1,y+tree_height-1,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height-1,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height-1,z-1);
        chunk.world.setBlock(block2,x+1,y+tree_height-1,z+1);
        chunk.world.setBlock(block2,x+1,y+tree_height-1,z-1);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z-1);

        chunk.world.setBlock(block2,x  ,y+tree_height-1,z+2);
        chunk.world.setBlock(block2,x  ,y+tree_height-1,z-2);
        chunk.world.setBlock(block2,x-2,y+tree_height-1,z  );
        chunk.world.setBlock(block2,x+2,y+tree_height-1,z  );

        chunk.world.setBlock(block2,x+1,y+tree_height-2,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height-2,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z  );
        chunk.world.setBlock(block2,x  ,y+tree_height-2,z-1);
        chunk.world.setBlock(block2,x+1,y+tree_height-2,z+1);
        chunk.world.setBlock(block2,x+1,y+tree_height-2,z-1);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z+1);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z-1);

        chunk.world.setBlock(block2,x+1,y+tree_height-2,z+2);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z+2);
        chunk.world.setBlock(block2,x+1,y+tree_height-2,z-2);
        chunk.world.setBlock(block2,x-1,y+tree_height-2,z-2);
        chunk.world.setBlock(block2,x-2,y+tree_height-2,z+1);
        chunk.world.setBlock(block2,x-2,y+tree_height-2,z-1);
        chunk.world.setBlock(block2,x+2,y+tree_height-2,z+1);
        chunk.world.setBlock(block2,x+2,y+tree_height-2,z-1);

        chunk.world.setBlock(block2,x  ,y+tree_height-2,z+2);
        chunk.world.setBlock(block2,x  ,y+tree_height-2,z-2);
        chunk.world.setBlock(block2,x-2,y+tree_height-2,z  );
        chunk.world.setBlock(block2,x+2,y+tree_height-2,z  );

        chunk.world.setBlock(block2,x+1,y+tree_height-1,z+2);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z+2);
        chunk.world.setBlock(block2,x+1,y+tree_height-1,z-2);
        chunk.world.setBlock(block2,x-1,y+tree_height-1,z-2);
        chunk.world.setBlock(block2,x-2,y+tree_height-1,z+1);
        chunk.world.setBlock(block2,x-2,y+tree_height-1,z-1);
        chunk.world.setBlock(block2,x+2,y+tree_height-1,z+1);
        chunk.world.setBlock(block2,x+2,y+tree_height-1,z-1);

        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-1,z+2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-1,z+2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-1,z-2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-1,z-2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-2,z+2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x-2,y+tree_height-2,z-2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-2,z+2);
        if(random.randomBoolean()) chunk.world.setBlock(block2,x+2,y+tree_height-2,z-2);
    }



    public static final Generator NORMAL,FLAT,NORMAL_OLD;

    static{

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

                    int y1=Maths.round(height-8+gen1*100+gen2*10-(100+10)/2f);
                    if(y1>64)
                        y1=64;

                    int y2=0;
                    if(gen3>0.46 && gen3<0.54){
                        y2=-Maths.round(gen3*15);
                        block=Block.SAND.id;
                    }

                    int y=y1+y2;

                    y=Math.max(y,0);
                    chunk.setBlockWoPC(block,lx,y,lz);
                }
        });*/



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
                    /*if(nor>0){
                        y-=Math.min(nor,1);
                    }*/if(nor<0)
                        y-=Math.pow(1+(float)nor/(30+10+10+5+4),0.7)*nor;

                    boolean sand=y<65;

                    if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                        chunk.setBlock(Block.SAND,i,y,k);
                    else if(y<64){
                        chunk.setBlock(Block.DIRT,i,y,k);
                    }else{
                        chunk.setBlock(Block.GRASS_BLOCK,i,y,k);

                        if(noise7.getNoise(x,z)>0.5 && random.randomBoolean(0.02)){
                            if(random.randomBoolean())
                                spawnTree(chunk,i,k,x,y,z,random,Block.OAK_LOG,Block.OAK_LEAVES);
                            else
                                spawnTree(chunk,i,k,x,y,z,random,Block.BIRCH_LOG,Block.OAK_LEAVES);
                        }
                        else if(random.randomBoolean(0.1) && nor>1)
                            chunk.setBlock(Block.GRASS,i,y+1,k);
                    }

                    //System.out.println(noise6.getNoise(x,z));

                    for(int a=0; a+1<3+noise6.getNoise(x,z)*4; a++){
                        y--;
                        if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                            chunk.setBlock(Block.SAND,i,y,k);
                        else
                            chunk.setBlock(Block.DIRT,i,y,k);
                    }
                    for(y--; y>=0; y--)
                        chunk.setBlock(Block.STONE,i,y,k);

                    // set bedrock

                    //chunk.setBlock(10,i,0,k);
                    //if(Math.random()>0.4)  chunk.setBlock(10,i,4,k);
                    //if(Math.random()>0.2)  chunk.setBlock(10,i,3,k);
                    //if(Math.random()>0.1)  chunk.setBlock(10,i,2,k);
                    //if(Math.random()>0.05) chunk.setBlock(10,i,1,k);

                }

            // fill water y<64
            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++){
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlock(i,j,k)==0 && j<=64)
                            chunk.setBlock(Block.WATER,i,j,k);
                    // set height map
                    chunk.checkHeight(i,k);
                }

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlock(i,j,k)==Block.WATER.id && (chunk.getBlock(i,j+1,k)==0 || chunk.getBlock(i,j+1,k)==-1))
                            chunk.setBlock(Block.STILL_WATER,i,j,k);
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
                        chunk.setBlock(Block.SAND,i,y,k);
                    else if(y<64){
                        chunk.setBlock(Block.DIRT,i,y,k);
                    }else{
                        chunk.setBlock(Block.GRASS_BLOCK,i,y,k);

                        if(noise7.getNoise(x,z)>0.3 && random.randomBoolean(0.02)){
                            if(random.randomBoolean())
                                spawnTree(chunk,i,k,x,y,z,random,Block.OAK_LOG,Block.OAK_LEAVES);
                            else
                                spawnTree(chunk,i,k,x,y,z,random,Block.BIRCH_LOG,Block.OAK_LEAVES);
                        }
                        else if(random.randomBoolean(0.1) && nor>1)
                            chunk.setBlock(Block.GRASS,i,y+1,k);
                    }

                    //System.out.println(noise6.getNoise(x,z));

                    for(int a=0; a+1<3+noise6.getNoise(x,z)*4; a++){
                        y--;
                        if(sand && noise4.getNoise(x,z)+noise3.getNoise(x,z)>1.1)
                            chunk.setBlock(Block.SAND,i,y,k);
                        else
                            chunk.setBlock(Block.DIRT,i,y,k);
                    }
                    for(y--; y>=0; y--)
                        chunk.setBlock(Block.STONE,i,y,k);

                    // set bedrock

                    //chunk.setBlock(10,i,0,k);
                    //if(Math.random()>0.4)  chunk.setBlock(10,i,4,k);
                    //if(Math.random()>0.2)  chunk.setBlock(10,i,3,k);
                    //if(Math.random()>0.1)  chunk.setBlock(10,i,2,k);
                    //if(Math.random()>0.05) chunk.setBlock(10,i,1,k);

                }

            // fill water y<64
            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++){
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlock(i,j,k)==0 && j<=64)
                            chunk.setBlock(Block.WATER,i,j,k);
                    // set height map
                    chunk.checkHeight(i,k);
                }

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    for(int j=0; j<Chunk.HEIGHT; j++)
                        if(chunk.getBlock(i,j,k)==Block.WATER.id && (chunk.getBlock(i,j+1,k)==0 || chunk.getBlock(i,j+1,k)==-1))
                            chunk.setBlock(Block.STILL_WATER,i,j,k);
        });



        FLAT=new Generator("Flat",(Chunk chunk,long seed)->{
            int height=3;

            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++){
                    chunk.setBlock(Block.GRASS_BLOCK,i,height,k);
                    for(int y=height-1; y>=0; y--)
                        chunk.setBlock(Block.DIRT,i,y,k);
                    //chunk.setBlock(Block.BEDROCK,i,0,k);
                }

            // setting height map
            for(int i=0; i<Chunk.WIDTH_X; i++)
                for(int k=0; k<Chunk.WIDTH_Z; k++)
                    chunk.checkHeight(i,k);
        });
    }


}
