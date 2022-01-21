package djankcraft.minecraft54;

import djankcraft.engine.graphics.Mesh;
import djankcraft.engine.graphics.VertexAttribute;
import djankcraft.engine.math.Maths;
import djankcraft.engine.utils.Utils;
import org.lwjgl.opengl.GL46C;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Chunk{


    public static final byte WIDTH_X=16;
    public static final short HEIGHT=256;
    public static final byte WIDTH_Z=16;
    public static final byte SIDES=7; // +x,-x,+y,-y,+z,-z,independence


    public short[] blocks; // [x,y,z] -> Block ID
    public short[] heightmap; // [x,y] -> Block Y
    public Mesh mesh1,mesh2,mesh3,mesh4; // Normal, WithoutCullFace, TranslucentWithoutCullFace, Translucent
    public int x,z;
    public World world;
    public boolean isChanged,isGenerated;


    public Chunk(World world,int x,int z){
        blocks=new short[WIDTH_X*HEIGHT*WIDTH_Z];
        heightmap=new short[WIDTH_X*WIDTH_Z];
        this.world=world;
        this.x=x;
        this.z=z;
    }

    public boolean isInit;
    public void init(){
        mesh1=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        mesh2=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        mesh3=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        mesh4=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        isInit=true;
    }


    private final List<Float> vertList1=new ArrayList<>();
    private final List<Float> vertList2=new ArrayList<>();
    private final List<Float> vertList3=new ArrayList<>();
    private final List<Float> vertList4=new ArrayList<>();
    private boolean isBuild,building;



    public synchronized void buildMeshes(){
        if(!building){
            building=true;

            long millis=System.currentTimeMillis(); int faces=0;

            int[] AOSides=new int[WIDTH_X*WIDTH_Z*HEIGHT*6];
            List<Integer> checkSides=new ArrayList<>();

            for(byte lx=0; lx<WIDTH_X; lx++)
                for(short ly=0; ly<HEIGHT; ly++)
                    for(byte lz=0; lz<WIDTH_Z; lz++){

                        int id=getBlockWoPC(lx,ly,lz);
                        if(id==0)
                            continue;

                        Block block=Block.fromId.get(id);
                        if(block==null)
                            continue;

                        Chunk chunk=world.getChunk(x+1,z);
                        int px=lx+1<WIDTH_X?
                                getBlockWoPC(lx+1,ly,lz)
                                :
                                chunk!=null?
                                        chunk.getBlockWoPC(0,ly,lz)
                                        :
                                        -1;

                        chunk=world.getChunk(x-1,z);
                        int mx=lx-1>-1?
                                getBlockWoPC(lx-1,ly,lz)
                                :
                                chunk!=null?
                                        chunk.getBlockWoPC(WIDTH_X-1,ly,lz)
                                        :
                                        -1;

                        int py=ly+1<HEIGHT?
                                getBlockWoPC(lx,ly+1,lz)
                                :
                                0;

                        int my=ly-1>-1?
                                getBlockWoPC(lx,ly-1,lz)
                                :
                                0;

                        chunk=world.getChunk(x,z+1);
                        int pz=lz+1<WIDTH_Z?
                                getBlockWoPC(lx,ly,lz+1)
                                :
                                chunk!=null?
                                        chunk.getBlockWoPC(lx,ly,0)
                                        :
                                        -1;

                        chunk=world.getChunk(x,z-1);
                        int mz=lz-1>-1?
                                getBlockWoPC(lx,ly,lz-1)
                                :
                                chunk!=null?
                                        chunk.getBlockWoPC(lx,ly,WIDTH_Z-1)
                                        :
                                        -1;

                        Block px_block=Block.fromId.get(px);
                        Block mx_block=Block.fromId.get(mx);
                        Block py_block=Block.fromId.get(py);
                        Block my_block=Block.fromId.get(my);
                        Block pz_block=Block.fromId.get(pz);
                        Block mz_block=Block.fromId.get(mz);

                        boolean[] sides=new boolean[]{
                                (px==0 && (px_block==null || !Utils.contains(px_block.showFor[1],-id))) || (px_block!=null && !Utils.contains(px_block.showFor[1],-id) && (Utils.contains(px_block.showFor[1],id) || (Utils.contains(px_block.showFor[1],Block.ALL) && px!=id))),
                                (mx==0 && (mx_block==null || !Utils.contains(mx_block.showFor[0],-id))) || (mx_block!=null && !Utils.contains(mx_block.showFor[0],-id) && (Utils.contains(mx_block.showFor[0],id) || (Utils.contains(mx_block.showFor[0],Block.ALL) && mx!=id))),
                                (py==0 && (py_block==null || !Utils.contains(py_block.showFor[3],-id))) || (py_block!=null && !Utils.contains(py_block.showFor[3],-id) && (Utils.contains(py_block.showFor[3],id) || (Utils.contains(py_block.showFor[3],Block.ALL) && py!=id))),
                                (my==0 && (my_block==null || !Utils.contains(my_block.showFor[2],-id))) || (my_block!=null && !Utils.contains(my_block.showFor[2],-id) && (Utils.contains(my_block.showFor[2],id) || (Utils.contains(my_block.showFor[2],Block.ALL) && my!=id))),
                                (pz==0 && (pz_block==null || !Utils.contains(pz_block.showFor[5],-id))) || (pz_block!=null && !Utils.contains(pz_block.showFor[5],-id) && (Utils.contains(pz_block.showFor[5],id) || (Utils.contains(pz_block.showFor[5],Block.ALL) && pz!=id))),
                                (mz==0 && (mz_block==null || !Utils.contains(mz_block.showFor[4],-id))) || (mz_block!=null && !Utils.contains(mz_block.showFor[4],-id) && (Utils.contains(mz_block.showFor[4],id) || (Utils.contains(mz_block.showFor[4],Block.ALL) && mz!=id))),
                                true
                        };

                        for(byte side=0; side<7; side++){
                            List<Float> meshVerticesList=switch(block.render_layer[6]){
                                case 4 -> vertList4;
                                case 3 -> vertList3;
                                case 2 -> vertList2;
                                default -> vertList1;
                            };

                            if(sides[side]){
                                float[] sideVertices=block.model.getSide(side);
                                float[] sideTexture=block.textures.textures[side];
                                if(sideVertices==null || sideTexture==null)
                                    continue;

                                if(side!=6 && block.render_layer[side]==1){
                                    int pos=lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+side;
                                    AOSides[pos]=vertList1.size()+1;
                                    checkSides.add(pos);
                                }

                                for(short v=0; v<sideVertices.length/3; v++){
                                    meshVerticesList.add(sideVertices[v*3  ]+lx);
                                    meshVerticesList.add(sideVertices[v*3+1]+ly);
                                    meshVerticesList.add(sideVertices[v*3+2]+lz);

                                    meshVerticesList.add(sideTexture [v*3  ]);
                                    meshVerticesList.add(sideTexture [v*3+1]);
                                    meshVerticesList.add(sideTexture [v*3+2]);

                                    float shadow=side>3?0.8f:side<2?0.6f:side==3?0.5f:1f;
                                    meshVerticesList.add(block.color[side][0]*shadow);
                                    meshVerticesList.add(block.color[side][1]*shadow);
                                    meshVerticesList.add(block.color[side][2]*shadow);
                                    meshVerticesList.add(block.color[side][3]);

                                    meshVerticesList.add((float)id);

                                    meshVerticesList.add(1f);
                                }
                                faces++;
                            }
                        }
                    }


            //Ambient Occlusion
            
            float ao=0.65f;

            for(int pos:checkSides){
                int side=pos%6;
                int z=(pos-side)/6%WIDTH_Z;
                int y=(pos-z*6-side)/6/WIDTH_Z%HEIGHT;
                int x=(pos-y*6*WIDTH_Z-z*6-side)/6/WIDTH_Z/HEIGHT;

                if(side==0 && x+1<WIDTH_X){
                    if(y-1>-1){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6+2];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*4,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(y+1<HEIGHT){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6+3];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*4,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(z-1>-1){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6+4];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                    if(z+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6+5];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*4,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }

                    if(y-1>-1 && z-1>-1){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+2];
                        if(cSide!=0)
                            vertList1.set(cSide+10+12*4,ao);
                    }
                    if(y+1<HEIGHT && z-1>-1){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+3];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(y-1>-1 && z+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+2];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(y+1<HEIGHT && z+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+3];
                        if(cSide!=0)
                            vertList1.set(cSide+10+12*4,ao);
                    }
                }

                if(side==1 && x-1>-1){
                    if(y-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6+2];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                    if(y+1<HEIGHT){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6+3];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                    if(z-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6+4];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*4,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(z+1<WIDTH_Z){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6+5];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }

                    if(y-1>-1 && z-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+2];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                    if(y+1<HEIGHT && z-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+3];
                        if(cSide!=0)
                            vertList1.set(cSide+10+12,ao);
                    }
                    if(y-1>-1 && z+1<WIDTH_Z){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+2];
                        if(cSide!=0)
                            vertList1.set(cSide+10+12,ao);
                    }
                    if(y+1<HEIGHT && z+1<WIDTH_Z){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+3];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                }

                if(side==2 && y+1<HEIGHT){
                    if(x-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(x+1<WIDTH_X){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(z-1>-1){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+4];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(z+1<WIDTH_Z){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+5];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }

                    if(x-1>-1 && z-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12,ao);
                        }else{
                            cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0){
                                vertList1.set(cSide+10     ,ao);
                                vertList1.set(cSide+10+12*5,ao);
                            }
                        }
                    }
                    if(x+1<WIDTH_X && z-1>-1){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }else{
                            cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0){
                                vertList1.set(cSide+10+12,ao);
                            }
                        }
                    }
                    if(x-1>-1 && z+1<WIDTH_Z){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }else{
                            cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0)
                                vertList1.set(cSide+10+12,ao);
                        }
                    }
                    if(x+1<WIDTH_X && z+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12,ao);
                        }else{
                            cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0){
                                vertList1.set(cSide+10     ,ao);
                                vertList1.set(cSide+10+12*5,ao);
                            }
                        }
                    }
                }

                if(side==3 && y-1>-1){
                    if(x-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                            vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                    if(x+1<WIDTH_X){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                            vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                    if(z-1>-1){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+4];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                            vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                    if(z+1<WIDTH_Z){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+5];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                            vertList1.set(cSide+10+12*4,ao);
                        }
                    }

                    if(x-1>-1 && z-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }else{
                            cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0)
                                vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                    if(x+1<WIDTH_X && z-1>-1){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*4,ao);
                        }else{
                            cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0){
                                vertList1.set(cSide+10+12*2,ao);
                                vertList1.set(cSide+10+12*3,ao);
                            }
                        }
                    }
                    if(x-1>-1 && z+1<WIDTH_Z){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*4,ao);
                        }else{
                            cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0){
                                vertList1.set(cSide+10+12*2,ao);
                                vertList1.set(cSide+10+12*3,ao);
                            }
                        }
                    }
                    if(x+1<WIDTH_X && z+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }else{
                            cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0)
                                vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                }

                if(side==4 && z+1<WIDTH_Z){
                    if(y-1>-1){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+2];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(y+1<HEIGHT){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+3];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                            vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                    if(x-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*4,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(x+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                }

                if(side==5 && z-1>-1){
                    if(y-1>-1){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+2];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                            vertList1.set(cSide+10+12*4,ao);
                        }
                    }
                    if(y+1<HEIGHT){
                        int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+3];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                    if(x-1>-1){
                        int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6];
                        if(cSide!=0){
                            vertList1.set(cSide+10+12  ,ao);
                            vertList1.set(cSide+10+12*2,ao);
                            vertList1.set(cSide+10+12*3,ao);
                        }
                    }
                    if(x+1<WIDTH_Z){
                        int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6+1];
                        if(cSide!=0){
                            vertList1.set(cSide+10     ,ao);
                            vertList1.set(cSide+10+12*4,ao);
                            vertList1.set(cSide+10+12*5,ao);
                        }
                    }
                }

            }

            isBuild=true;
            //System.out.println(faces+" faces, "+(System.currentTimeMillis()-millis)/1000f+" sec.");
        }
    }



    public void update(){
        if(isBuild && isInit){
            mesh1.setVertices(vertList1);
            mesh2.setVertices(vertList2);
            mesh3.setVertices(vertList3);
            mesh4.setVertices(vertList4);

            vertList1.clear();
            vertList2.clear();
            vertList3.clear();
            vertList4.clear();

            isBuild=false;
            isChanged=false;
            building=false;
        }
    }


    public void setBlockWoPC(int id,int lx,int ly,int lz){ // Without Pos Check
        blocks[lx*WIDTH_Z*HEIGHT+ly*WIDTH_Z+lz]=(short)id;
        changedIn(lx,lz);
    }

    public void changedIn(int lx,int lz){
        isChanged=true;

        Chunk xChunk=lx==0?
                world.getChunk(x-1,z)
            :
                lx==WIDTH_X-1?
                        world.getChunk(x+1,z)
                    :
                        null;
        if(xChunk!=null)
            xChunk.isChanged=true;

        Chunk zChunk=lz==0?
                world.getChunk(x,z-1)
            :
                lz==WIDTH_Z-1?
                        world.getChunk(x,z+1)
                    :
                        null;
        if(zChunk!=null)
            zChunk.isChanged=true;
    }

    public int getBlockWoPC(int lx,int ly,int lz){ // Without Pos Check
        return blocks[lx*WIDTH_Z*HEIGHT+ly*WIDTH_Z+lz];
    }

    public void setBlock(int id,int lx,int ly,int lz){
        if(lx>-1 && ly>-1 && lz>-1 && lx<WIDTH_X && ly<HEIGHT && lz<WIDTH_Z)
            setBlockWoPC(id,lx,ly,lz);
    }

    public void setBlock(Block block,int lx,int ly,int lz){
        if(lx>-1 && ly>-1 && lz>-1 && lx<WIDTH_X && ly<HEIGHT && lz<WIDTH_Z)
            setBlockWoPC(block.id,lx,ly,lz);
    }

    public int getBlock(int lx,int ly,int lz){
        if(lx>-1 && ly>-1 && lz>-1 && lx<WIDTH_X && ly<HEIGHT && lz<WIDTH_Z)
            return getBlockWoPC(lx,ly,lz);
        return -1;
    }


    public int getHeight(int lx,int lz){
        return heightmap[lx*WIDTH_Z+lz];
    }

    public void checkHeight(int lx,int lz){
        for(short j=HEIGHT-1; j>=0; j--){
            int id=getBlock(lx,j,lz);
            if(id!=0){
                heightmap[lx*WIDTH_Z+lz]=j;
                break;
            }
        }
    }


    public void dispose(){
        save();

        if(mesh1!=null)
            mesh1.dispose();
        if(mesh2!=null)
            mesh2.dispose();
        if(mesh3!=null)
            mesh3.dispose();
        if(mesh4!=null)
            mesh4.dispose();
    }


    public void save(){
        try{
            File chunkFile=new File(world.worldPath+"/chunks/"+x+":"+z);
            chunkFile.createNewFile();
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(chunkFile));
            out.writeBoolean(isGenerated);
            for(short block:blocks)
                out.writeShort(block);
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
