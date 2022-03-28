package minecraft54.main.client.world;

import minecraft54.engine.graphics.Mesh;
import minecraft54.engine.graphics.VertexAttribute;
import minecraft54.engine.utils.FastArrayList;
import minecraft54.main.Minecraft54;
import minecraft54.main.server.generator.SimplexNoise;
import org.lwjgl.opengl.GL46C;

public class ChunkSection extends ChunkSectionData{


    public Mesh mesh1,mesh2,mesh3,mesh4; // Normal, WithoutCullFace, TranslucentWithoutCullFace, Translucent\

    private boolean isBuild,building;
    private final FastArrayList<Float>[] vertLists=new FastArrayList[]{
            new FastArrayList<>(),
            new FastArrayList<>(),
            new FastArrayList<>(),
            new FastArrayList<>()
    };

    public final Chunk chunk;
    public final int y;


    public ChunkSection(Chunk chunk,int y){
        super(chunk,y);

        this.chunk=chunk;
        this.y=y;
    }


    public void update(){
        if(isBuild){
            mesh1.setVertices(vertLists[0]);
            mesh2.setVertices(vertLists[1]);
            mesh3.setVertices(vertLists[2]);
            mesh4.setVertices(vertLists[3]);

            isBuild=false;
            changed=false;
            building=false;
        }
    }

    public void init(){
        mesh1=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        mesh2=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        mesh3=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
        mesh4=new Mesh(new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(3,GL46C.GL_FLOAT),new VertexAttribute(4,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT),new VertexAttribute(1,GL46C.GL_FLOAT));
    }

    public void dispose(){
        if(mesh1!=null)
            mesh1.dispose();
        if(mesh2!=null)
            mesh2.dispose();
        if(mesh3!=null)
            mesh3.dispose();
        if(mesh4!=null)
            mesh4.dispose();
    }



    public void buildOld(){
        if(!building){
            building=true;

            SimplexNoise noiseR=new SimplexNoise(200,0.1,(int)chunk.world.seed);
            SimplexNoise noiseG=new SimplexNoise(200,0.1,(int)chunk.world.seed+1);
            SimplexNoise noiseB=new SimplexNoise(200,0.1,(int)chunk.world.seed+2);

            vertLists[0].clear();
            vertLists[1].clear();
            vertLists[2].clear();
            vertLists[3].clear();

            //long millis=System.currentTimeMillis(); int faces=0;

            int[] AOSides=new int[WIDTH_X*WIDTH_Z*HEIGHT*6];
            FastArrayList<Integer> checkSides=new FastArrayList<>();

            for(byte lx=0; lx<WIDTH_X; lx++)
                for(byte lz=0; lz<WIDTH_Z; lz++){

                    float grassBlockR=(float)noiseR.getNoise(this.chunk.x*WIDTH_X+lx,this.chunk.z*WIDTH_Z+lz)*(0.4f-0.3f)+0.3f;
                    float grassBlockG=(float)noiseG.getNoise(this.chunk.x*WIDTH_X+lx,this.chunk.z*WIDTH_Z+lz)*(0.85f-0.7f)+0.7f;
                    float grassBlockB=(float)noiseB.getNoise(this.chunk.x*WIDTH_X+lx,this.chunk.z*WIDTH_Z+lz)*(0.3f-0.2f)+0.2f;
                    //System.out.println((x*WIDTH_X+lx)+", "+(z*WIDTH_Z+lz)+": "+grassBlockR+", "+grassBlockG+", "+grassBlockB);

                    for(short ly=0; ly<HEIGHT; ly++){
                        int idData=getIdData(lx,ly,lz);

                        short id=(short)(idData%Short.MAX_VALUE);
                        if(id==0)
                            continue;
                        Block block=BlockManager.getBlockFromId(id);
                        if(block==null)
                            continue;

                        short data=(short)((idData-id)/Short.MAX_VALUE);
                        BlockData blockData=block.getBlockData(data);
                        if(blockData==null)
                            continue;

                        Chunk chunk=this.chunk.world.getChunk(this.chunk.x+1,this.chunk.z);
                        BlockData px_block=lx+1<WIDTH_X?
                                getBlock((short)(lx+1),ly,lz)
                                :
                                chunk!=null?
                                        chunk.sections[y].getBlock((short)0,ly,lz)
                                        :
                                        null;

                        chunk=this.chunk.world.getChunk(this.chunk.x-1,this.chunk.z);
                        BlockData mx_block=lx-1>-1?
                                getBlock((short)(lx-1),ly,lz)
                                :
                                chunk!=null?
                                        chunk.sections[y].getBlock((short)(WIDTH_X-1),ly,lz)
                                        :
                                        null;

                        BlockData py_block=ly+1<HEIGHT?
                                getBlock(lx,(short)(ly+1),lz)
                                :
                                y<Chunk.SECTION_COUNT-1?
                                        this.chunk.sections[y+1].getBlock(lx,(short)0,lz)
                                        :
                                        null;

                        BlockData my_block=ly-1>-1?
                                getBlock(lx,(short)(ly-1),lz)
                                :
                                y>0?
                                        this.chunk.sections[y-1].getBlock(lx,(short)(HEIGHT-1),lz)
                                        :
                                        null;

                        chunk=this.chunk.world.getChunk(this.chunk.x,this.chunk.z+1);
                        BlockData pz_block=lz+1<WIDTH_Z?
                                getBlock(lx,ly,(short)(lz+1))
                                :
                                chunk!=null?
                                        chunk.sections[y].getBlock(lx,ly,(short)0)
                                        :
                                        null;

                        chunk=this.chunk.world.getChunk(this.chunk.x,this.chunk.z-1);
                        BlockData mz_block=lz-1>-1?
                                getBlock(lx,ly,(short)(lz-1))
                                :
                                chunk!=null?
                                        chunk.sections[y].getBlock(lx,ly,(short)(WIDTH_Z-1))
                                        :
                                        null;

                        //Block px_block=Block.fromId.get(px);
                        //Block mx_block=Block.fromId.get(mx);
                        //Block py_block=Block.fromId.get(py);
                        //Block my_block=Block.fromId.get(my);
                        //Block pz_block=Block.fromId.get(pz);
                        //Block mz_block=Block.fromId.get(mz);

                        boolean[] sides=new boolean[]{
                                ( px_block==null || ( (blockData.sides[1]==null || !blockData.sides[1].interruptNeighbors.contains(px_block.getParentBlockId())) && (px_block.sides[1]==null || px_block.isTransparent() || px_block.isTranslucent()) )),
                                ( mx_block==null || ( (blockData.sides[0]==null || !blockData.sides[0].interruptNeighbors.contains(mx_block.getParentBlockId())) && (mx_block.sides[0]==null || mx_block.isTransparent() || mx_block.isTranslucent()) )),
                                ( mz_block==null || ( (blockData.sides[2]==null || !blockData.sides[2].interruptNeighbors.contains(mz_block.getParentBlockId())) && (mz_block.sides[2]==null || mz_block.isTransparent() || mz_block.isTranslucent()) )),
                                ( pz_block==null || ( (blockData.sides[3]==null || !blockData.sides[3].interruptNeighbors.contains(pz_block.getParentBlockId())) && (pz_block.sides[3]==null || pz_block.isTransparent() || pz_block.isTranslucent()) )),
                                ( py_block==null || ( (blockData.sides[5]==null || !blockData.sides[5].interruptNeighbors.contains(py_block.getParentBlockId())) && (py_block.sides[5]==null || py_block.isTransparent() || py_block.isTranslucent()) )),
                                ( my_block==null || ( (blockData.sides[4]==null || !blockData.sides[4].interruptNeighbors.contains(my_block.getParentBlockId())) && (my_block.sides[4]==null || my_block.isTransparent() || my_block.isTranslucent()) )),
                                true
                        };

                        /*boolean[] sides=new boolean[]{
                                (px==0 && (px_block==null || !Utils.contains(px_block.showFor[1],-id))) || (px_block!=null && !Utils.contains(px_block.showFor[1],-id) && (Utils.contains(px_block.showFor[1],id) || (Utils.contains(px_block.showFor[1],Block.ALL) && px!=id))),
                                (mx==0 && (mx_block==null || !Utils.contains(mx_block.showFor[0],-id))) || (mx_block!=null && !Utils.contains(mx_block.showFor[0],-id) && (Utils.contains(mx_block.showFor[0],id) || (Utils.contains(mx_block.showFor[0],Block.ALL) && mx!=id))),
                                (py==0 && (py_block==null || !Utils.contains(py_block.showFor[3],-id))) || (py_block!=null && !Utils.contains(py_block.showFor[3],-id) && (Utils.contains(py_block.showFor[3],id) || (Utils.contains(py_block.showFor[3],Block.ALL) && py!=id))),
                                (my==0 && (my_block==null || !Utils.contains(my_block.showFor[2],-id))) || (my_block!=null && !Utils.contains(my_block.showFor[2],-id) && (Utils.contains(my_block.showFor[2],id) || (Utils.contains(my_block.showFor[2],Block.ALL) && my!=id))),
                                (pz==0 && (pz_block==null || !Utils.contains(pz_block.showFor[5],-id))) || (pz_block!=null && !Utils.contains(pz_block.showFor[5],-id) && (Utils.contains(pz_block.showFor[5],id) || (Utils.contains(pz_block.showFor[5],Block.ALL) && pz!=id))),
                                (mz==0 && (mz_block==null || !Utils.contains(mz_block.showFor[4],-id))) || (mz_block!=null && !Utils.contains(mz_block.showFor[4],-id) && (Utils.contains(mz_block.showFor[4],id) || (Utils.contains(mz_block.showFor[4],Block.ALL) && mz!=id))),
                                true
                        };*/

                        for(byte sideIndex=0; sideIndex<7; sideIndex++){
                            Block.BlockSide side=blockData.sides[sideIndex];
                            if(side==null || !sides[sideIndex])
                                continue;

                            if(blockData.sides[sideIndex].render_layer==0 && sideIndex!=6 && Minecraft54.GLASS.getId()!=block.getId()){
                                int pos=lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+sideIndex;
                                AOSides[pos]=vertLists[0].size()+1;
                                checkSides.add(pos);
                            }

                            float[] sideMesh=side.getMesh();
                            for(int v=0; v<sideMesh.length; v+=Block.BlockSide.VERTEX_SIZE){
                                vertLists[side.render_layer].add(sideMesh[v   ]+lx);
                                vertLists[side.render_layer].add(sideMesh[v+1 ]+ly);
                                vertLists[side.render_layer].add(sideMesh[v+2 ]+lz);

                                vertLists[side.render_layer].add(sideMesh[v+3 ]);
                                vertLists[side.render_layer].add(sideMesh[v+4 ]);
                                vertLists[side.render_layer].add(sideMesh[v+5 ]);

                                /*if((block.getId()==Minecraft54.GRASS_BLOCK.getId() && sideIndex!=5 && (sideIndex==4 || v>=6*Block.BlockSide.VERTEX_SIZE)) || block.getId()==Minecraft54.GRASS.getId()){
                                    vertLists[side.render_layer].add(grassBlockR);
                                    vertLists[side.render_layer].add(grassBlockG);
                                    vertLists[side.render_layer].add(grassBlockB);
                                    vertLists[side.render_layer].add(sideMesh[v+9 ]);
                                }else if(block.getId()==Minecraft54.LEAVES.getId()){
                                    vertLists[side.render_layer].add(grassBlockR*0.7f);
                                    vertLists[side.render_layer].add(grassBlockG*0.9f);
                                    vertLists[side.render_layer].add(grassBlockB*0.7f);
                                    vertLists[side.render_layer].add(sideMesh[v+9 ]);
                                }else*/{
                                    vertLists[side.render_layer].add(sideMesh[v+6 ]);
                                    vertLists[side.render_layer].add(sideMesh[v+7 ]);
                                    vertLists[side.render_layer].add(sideMesh[v+8 ]);
                                    vertLists[side.render_layer].add(sideMesh[v+9 ]);
                                }

                                vertLists[side.render_layer].add(sideMesh[v+10]);

                                vertLists[side.render_layer].add(sideMesh[v+11]);
                            }

                            //faces++;
                        }

                    }
                }


            //Ambient Occlusion

            if(true){

                float ao=0.65f;

                for(int i=0; i<checkSides.size(); i++){
                    int pos=checkSides.get(i);
                    int side=pos%6;
                    int z=(pos-side)/6%WIDTH_Z;
                    int y=(pos-z*6-side)/6/WIDTH_Z%HEIGHT;
                    int x=(pos-y*6*WIDTH_Z-z*6-side)/6/WIDTH_Z/HEIGHT;

                    if(side==0 && x+1<WIDTH_X){
                        if(y-1>-1){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6+4];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(y+1<HEIGHT){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6+5];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(z-1>-1){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6+3];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                        if(z+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6+2];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }

                        if(y-1>-1 && z-1>-1){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0)
                                vertLists[0].set(cSide+10+12*4,ao);
                        }
                        if(y+1<HEIGHT && z-1>-1){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+5];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(y-1>-1 && z+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+4];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(y+1<HEIGHT && z+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0)
                                vertLists[0].set(cSide+10+12*4,ao);
                        }
                    }

                    if(side==1 && x-1>-1){
                        if(y-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6+4];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                        if(y+1<HEIGHT){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6+5];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                        if(z-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6+3];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(z+1<WIDTH_Z){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6+2];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }

                        if(y-1>-1 && z-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                        if(y+1<HEIGHT && z-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+5];
                            if(cSide!=0)
                                vertLists[0].set(cSide+10+12,ao);
                        }
                        if(y-1>-1 && z+1<WIDTH_Z){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+4];
                            if(cSide!=0)
                                vertLists[0].set(cSide+10+12,ao);
                        }
                        if(y+1<HEIGHT && z+1<WIDTH_Z){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                    }

                    if(side==4 && y+1<HEIGHT){
                        if(x-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(x+1<WIDTH_X){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(z-1>-1){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+3];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(z+1<WIDTH_Z){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+2];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }

                        if(x-1>-1 && z-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                            }else{
                                cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+2];
                                if(cSide!=0){
                                    vertLists[0].set(cSide+10,ao);
                                    vertLists[0].set(cSide+10+12*5,ao);
                                }
                            }
                        }
                        if(x+1<WIDTH_X && z-1>-1){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }else{
                                cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+2];
                                if(cSide!=0){
                                    vertLists[0].set(cSide+10+12,ao);
                                }
                            }
                        }
                        if(x-1>-1 && z+1<WIDTH_Z){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }else{
                                cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+3];
                                if(cSide!=0)
                                    vertLists[0].set(cSide+10+12,ao);
                            }
                        }
                        if(x+1<WIDTH_X && z+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                            }else{
                                cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+3];
                                if(cSide!=0){
                                    vertLists[0].set(cSide+10,ao);
                                    vertLists[0].set(cSide+10+12*5,ao);
                                }
                            }
                        }
                    }

                    if(side==5 && y-1>-1){
                        if(x-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                        if(x+1<WIDTH_X){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                        if(z-1>-1){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+3];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                        if(z+1<WIDTH_Z){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+2];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }

                        if(x-1>-1 && z-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }else{
                                cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+2];
                                if(cSide!=0)
                                    vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                        if(x+1<WIDTH_X && z-1>-1){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*4,ao);
                            }else{
                                cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+2];
                                if(cSide!=0){
                                    vertLists[0].set(cSide+10+12*2,ao);
                                    vertLists[0].set(cSide+10+12*3,ao);
                                }
                            }
                        }
                        if(x-1>-1 && z+1<WIDTH_Z){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*4,ao);
                            }else{
                                cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+3];
                                if(cSide!=0){
                                    vertLists[0].set(cSide+10+12*2,ao);
                                    vertLists[0].set(cSide+10+12*3,ao);
                                }
                            }
                        }
                        if(x+1<WIDTH_X && z+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }else{
                                cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+3];
                                if(cSide!=0)
                                    vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                    }

                    if(side==3 && z+1<WIDTH_Z){
                        if(y-1>-1){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z+1)*6+4];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(y+1<HEIGHT){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z+1)*6+5];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                        if(x-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(x+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z+1)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                    }

                    if(side==2 && z-1>-1){
                        if(y-1>-1){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y-1)*6*WIDTH_Z+(z-1)*6+4];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                            }
                        }
                        if(y+1<HEIGHT){
                            int cSide=AOSides[(x)*6*WIDTH_Z*HEIGHT+(y+1)*6*WIDTH_Z+(z-1)*6+5];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                        if(x-1>-1){
                            int cSide=AOSides[(x-1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10+12,ao);
                                vertLists[0].set(cSide+10+12*2,ao);
                                vertLists[0].set(cSide+10+12*3,ao);
                            }
                        }
                        if(x+1<WIDTH_Z){
                            int cSide=AOSides[(x+1)*6*WIDTH_Z*HEIGHT+(y)*6*WIDTH_Z+(z-1)*6+1];
                            if(cSide!=0){
                                vertLists[0].set(cSide+10,ao);
                                vertLists[0].set(cSide+10+12*4,ao);
                                vertLists[0].set(cSide+10+12*5,ao);
                            }
                        }
                    }

                }

            }

            isBuild=true;
            //System.out.println(faces+" faces, "+(System.currentTimeMillis()-millis)/1000f+" sec.");
        }
    }



    /*public void build(){
        /
            I. init
                1. render layers vertices lists
                2. sides list
            II. for in sides
                1. put unknown sides in render layers vertices lists
                2. put their in sides list
            III. ambient occlusion
            IV. put sides list in render layers vertices lists
        /

        if(!building){
            building=true;
            long millis=System.currentTimeMillis(); int faces=0;

            vertLists[0].clear();
            vertLists[1].clear();
            vertLists[2].clear();
            vertLists[3].clear();

            FastArrayList<Integer> sides=new FastArrayList<>(); // I.

            for(byte lx=0; lx<WIDTH_X; lx++)
                for(short ly=0; ly<HEIGHT; ly++)
                    for(byte lz=0; lz<WIDTH_Z; lz++){ // II.

                        /int key=lx*WIDTH_Z*HEIGHT+ly*WIDTH_Z+lz;
                        short id=(short)(blocks[key]%Short.MAX_VALUE);
                        if(id==Minecraft54.AIR.getId())
                            continue;
                        Block_NRE block=BlockManager.getBlockFromId(id);
                        if(block==null)
                            continue;/
                        Block.BlockData blockData=getBlockWoPC(lx,ly,lz);//block.getBlockData((short)(blocks[key]-id));
                        if(blockData==null)
                            continue;

                        // NORTH
                        Chunk chunk;
                        Block.BlockSide side=blockData.sides[Direction.NORTH.ordinal()];
                        if(side!=null){
                            chunk=world.getChunk(x+1,z);
                            int neighbor=lx+1<WIDTH_X?
                                    getBlockIdDataWoPC(lx+1,ly,lz)
                                :
                                    chunk!=null?
                                            chunk.getBlockIdDataWoPC(0,ly,lz)
                                        :
                                            -1;
                            short neighborId=(short)(neighbor%Short.MAX_VALUE);

                            if(neighborId==0)
                                sides.add(lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+Direction.NORTH.ordinal());
                        }
                        //SOUTH
                        side=blockData.sides[Direction.SOUTH.ordinal()];
                        if(side!=null){
                            chunk=world.getChunk(x-1,z);
                            int neighbor=lx-1>-1?
                                    getBlockIdDataWoPC(lx-1,ly,lz)
                                :
                                    chunk!=null?
                                            chunk.getBlockIdDataWoPC(WIDTH_X-1,ly,lz)
                                        :
                                            -1;
                            short neighborId=(short)(neighbor%Short.MAX_VALUE);

                            if(neighborId==0)
                                sides.add(lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+Direction.SOUTH.ordinal());
                        }
                        //EAST
                        side=blockData.sides[Direction.EAST.ordinal()];
                        if(side!=null){
                            chunk=world.getChunk(x,z+1);
                            int neighbor=lz+1<WIDTH_Z?
                                    getBlockIdDataWoPC(lx,ly,lz+1)
                                :
                                    chunk!=null?
                                            chunk.getBlockIdDataWoPC(lx,ly,0)
                                        :
                                            -1;
                            short neighborId=(short)(neighbor%Short.MAX_VALUE);

                            if(neighborId==0)
                                sides.add(lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+Direction.EAST.ordinal());
                        }
                        //WEST
                        side=blockData.sides[Direction.WEST.ordinal()];
                        if(side!=null){
                            chunk=world.getChunk(x,z-1);
                            int neighbor=lz-1>-1?
                                    getBlockIdDataWoPC(lx,ly,lz-1)
                                :
                                    chunk!=null?
                                            chunk.getBlockIdDataWoPC(lx,ly,WIDTH_Z-1)
                                        :
                                            -1;
                            short neighborId=(short)(neighbor%Short.MAX_VALUE);

                            if(neighborId==0)
                                sides.add(lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+Direction.WEST.ordinal());
                        }
                        //UP
                        side=blockData.sides[Direction.UP.ordinal()];
                        if(side!=null){
                            int neighbor=ly+1<HEIGHT?
                                    getBlockIdDataWoPC(lx,ly+1,lz)
                                :
                                    0;
                            short neighborId=(short)(neighbor%Short.MAX_VALUE);

                            if(neighborId==0)
                                sides.add(lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+Direction.UP.ordinal());
                        }
                        //DOWN
                        side=blockData.sides[Direction.DOWN.ordinal()];
                        if(side!=null){
                            int neighbor=ly-1>-1?
                                    getBlockIdDataWoPC(lx,ly-1,lz)
                                :
                                    0;
                            short neighborId=(short)(neighbor%Short.MAX_VALUE);

                            if(neighborId==0)
                                sides.add(lx*6*WIDTH_Z*HEIGHT+ly*6*WIDTH_Z+lz*6+Direction.DOWN.ordinal());
                        }

                        //UNKNOWN SIDE
                        Block.BlockSide uSide=blockData.sides[6];
                        if(uSide==null)
                            continue;

                        float[] sideMesh=uSide.getMesh();
                        for(int v=0; v<sideMesh.length; v+=Block.BlockSide.VERTEX_SIZE){
                            vertLists[uSide.render_layer].add(sideMesh[v   ]+lx);
                            vertLists[uSide.render_layer].add(sideMesh[v+1 ]+ly);
                            vertLists[uSide.render_layer].add(sideMesh[v+2 ]+lz);

                            vertLists[uSide.render_layer].add(sideMesh[v+3 ]);
                            vertLists[uSide.render_layer].add(sideMesh[v+4 ]);
                            vertLists[uSide.render_layer].add(sideMesh[v+5 ]);

                            vertLists[uSide.render_layer].add(sideMesh[v+6 ]);
                            vertLists[uSide.render_layer].add(sideMesh[v+7 ]);
                            vertLists[uSide.render_layer].add(sideMesh[v+8 ]);
                            vertLists[uSide.render_layer].add(sideMesh[v+9 ]);

                            vertLists[uSide.render_layer].add(sideMesh[v+10]);

                            vertLists[uSide.render_layer].add(sideMesh[v+11]);
                        }

                        faces++;

                    }


            for(int i=0; i<sides.size(); i++){ // IV.
                faces++;

                int sideKey=sides.get(i);

                int dir=sideKey%6;
                int lz=(sideKey-dir)/6%WIDTH_Z;
                int ly=(sideKey-lz*6-dir)/6/WIDTH_Z%HEIGHT;
                int lx=(sideKey-ly*6*WIDTH_Z-lz*6-dir)/6/WIDTH_Z/HEIGHT;

                int idDataKey=(sideKey-dir)/6; //lx*WIDTH_Z*HEIGHT+ly*WIDTH_Z+lz;
                short id=(short)(blocks[idDataKey]%Short.MAX_VALUE);

                Block block=BlockManager.getBlockFromId(id);
                if(block==null)
                    continue;

                Block.BlockSide blockSide=block.getBlockData((short)(blocks[idDataKey]-id)).sides[dir];
                float[] sideMesh=blockSide.getMesh();

                for(int v=0; v<sideMesh.length; v+=Block.BlockSide.VERTEX_SIZE){
                    vertLists[blockSide.render_layer].add(sideMesh[v   ]+lx);
                    vertLists[blockSide.render_layer].add(sideMesh[v+1 ]+ly);
                    vertLists[blockSide.render_layer].add(sideMesh[v+2 ]+lz);

                    vertLists[blockSide.render_layer].add(sideMesh[v+3 ]);
                    vertLists[blockSide.render_layer].add(sideMesh[v+4 ]);
                    vertLists[blockSide.render_layer].add(sideMesh[v+5 ]);

                    vertLists[blockSide.render_layer].add(sideMesh[v+6 ]);
                    vertLists[blockSide.render_layer].add(sideMesh[v+7 ]);
                    vertLists[blockSide.render_layer].add(sideMesh[v+8 ]);
                    vertLists[blockSide.render_layer].add(sideMesh[v+9 ]);

                    vertLists[blockSide.render_layer].add(sideMesh[v+10]);

                    vertLists[blockSide.render_layer].add(sideMesh[v+11]);
                }

            }


            isBuild=true;
            //System.out.println(faces+" faces, "+(System.currentTimeMillis()-millis)/1000f+" sec.");
        }
    }*/



}
