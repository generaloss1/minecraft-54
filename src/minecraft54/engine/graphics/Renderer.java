package minecraft54.engine.graphics;

import minecraft54.engine.math.Matrix4;
import minecraft54.engine.utils.Color;

public class Renderer{


    private final ShaderProgram defaultShader;
    private ShaderProgram customShader;
    private boolean isBegin,isCustomShader;
    private Color color;


    public Renderer(){
        color=new Color(1,1,1,1);

        String vert=
                "#version 400 core                                          \n"+
                "                                                           \n"+
                "in vec3 aPos;                                              \n"+
                "in vec2 aTexCoord;                                         \n"+
                "in vec2 aVColor;                                           \n"+
                "out vec2 texCoord;                                         \n"+
                "out vec4 vColor;                                           \n"+
                "                                                           \n"+
                "uniform mat4 viewMat;                                      \n"+
                "uniform mat4 projMat;                                      \n"+
                "uniform mat4 modelMat;                                     \n"+
                "                                                           \n"+
                "void main(void){                                           \n"+
                "    gl_Position=projMat*viewMat*modelMat*vec4(aPos,1.0);   \n"+
                "    texCoord=aTexCoord;                                    \n"+
                "}                                                            ";

        String frag=
                "#version 400 core                                          \n"+
                "                                                           \n"+
                "in vec2 texCoord;                                          \n"+
                "in vec4 vColor;                                            \n"+
                "out vec4 fragColor;                                        \n"+
                "                                                           \n"+
                "uniform sampler2D u_texture;                               \n"+
                "uniform vec4 u_color;                                      \n"+
                "                                                           \n"+
                "void main(void){                                           \n"+
                "    vec4 color=vColor*u_color*texture(u_texture,texCoord); \n"+
                "    if(color.a<=0.0)                                       \n"+
                "        discard;                                           \n"+
                "    fragColor=color;                                       \n"+
                "}                                                            ";

        defaultShader=new ShaderProgram(vert,frag);
        defaultShader.addUniforms("u_texture","u_color","viewMat","projMat","modelMat");
    }

    public void begin(PerspectiveCamera cam){
        if(!isBegin){
            defaultShader.bind();
            defaultShader.setUniform("viewMat",cam.getView());
            defaultShader.setUniform("projMat",cam.getProjection());
            isBegin=true;
        }
    }

    public void begin(Matrix4 projection,Matrix4 view){
        if(!isBegin){
            defaultShader.bind();
            defaultShader.setUniform("viewMat",view);
            defaultShader.setUniform("projMat",projection);
            isBegin=true;
        }
    }

    public void begin(ShaderProgram shader){
        if(!isBegin && shader!=null){
            customShader=shader;
            customShader.bind();
            isBegin=true;
        }
    }

    public void end(){
        if(isBegin){
            if(isCustomShader)
                customShader.unbind();
            else
                defaultShader.unbind();
            isCustomShader=false;
            isBegin=false;
        }
    }


    public void draw(Model model){
        if(model.mesh==null)
            return;

        if(model.isReqCalcMat)
            model.calculateMatrix();

        if(!isCustomShader){
            int texture_unit=0;
            if(model.material!=null)
                if(model.material.texture!=null)
                    model.material.texture.bind(texture_unit);
                else if(model.material.texture3d!=null)
                    model.material.texture3d.bind(texture_unit);

            defaultShader.setUniform("u_texture",texture_unit);
            defaultShader.setUniform("modelMat",model.getModelMatrix());
            defaultShader.setUniform("u_color",color);
        }

        model.mesh.render();
    }


    public void draw(Mesh mesh,Texture3D texture3d,Matrix4 modelMatrix){
        if(texture3d==null || mesh==null)
            return;

        if(!isCustomShader){
            int texture_unit=0;
            texture3d.bind(texture_unit);

            defaultShader.setUniform("u_texture",texture_unit);
            defaultShader.setUniform("modelMat",modelMatrix);
            defaultShader.setUniform("u_color",color);
        }

        mesh.render();
    }

    public void draw(Mesh mesh,Texture texture,Matrix4 modelMatrix){
        if(texture==null || mesh==null)
            return;

        if(!isCustomShader){
            int texture_unit=0;
            texture.bind(texture_unit);

            defaultShader.setUniform("u_texture",texture_unit);
            defaultShader.setUniform("modelMat",modelMatrix);
            defaultShader.setUniform("u_color",color);
        }

        mesh.render();
    }


    public void setColor(float r,float g,float b,float a){
        color.set(r,g,b,a);
    }
    public void setColor(Color color){
        this.color=color;
    }
    public Color getColor(){
        return color;
    }

    public void dispose(){
        defaultShader.dispose();
        if(customShader!=null)
            customShader.dispose();
    }


}