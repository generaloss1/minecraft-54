package minecraft54.engine.graphics;

import minecraft54.engine.math.Matrix4;
import minecraft54.engine.math.vectors.Vector2;
import minecraft54.engine.math.vectors.Vector3f;
import minecraft54.engine.utils.Color;

import java.util.HashMap;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram{

    private final int programId;
    private HashMap<String,Integer> uniforms;


    public ShaderProgram(String vertexCode,String fragmentCode){
        programId=glCreateProgram();
        if(programId==0){
            System.out.println("Could not create shader");
            return;
        }
        int vertexShaderId=createShader(vertexCode,GL_VERTEX_SHADER);
        int fragmentShaderId=createShader(fragmentCode,GL_FRAGMENT_SHADER);
        glAttachShader(programId,vertexShaderId);
        glAttachShader(programId,fragmentShaderId);

        glLinkProgram(programId);
        glValidateProgram(programId);
        if(glGetProgrami(programId,GL_VALIDATE_STATUS)==GL_FALSE)
            System.err.println("Shader: "+glGetShaderInfoLog(programId,1024));

        glDetachShader(programId,vertexShaderId);
        glDetachShader(programId,fragmentShaderId);

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        uniforms=new HashMap<>();
    }

    public ShaderProgram(String vertexCode,String fragmentCode,String geometryCode){
        programId=glCreateProgram();
        if(programId==0){
            System.out.println("Could not create shader");
            return;
        }
        int vertexShaderId=createShader(vertexCode,GL_VERTEX_SHADER);
        int fragmentShaderId=createShader(fragmentCode,GL_FRAGMENT_SHADER);
        int geometryShaderId=createShader(geometryCode,GL_GEOMETRY_SHADER);
        glAttachShader(programId,vertexShaderId);
        glAttachShader(programId,fragmentShaderId);
        glAttachShader(programId,geometryShaderId);

        glLinkProgram(programId);
        glValidateProgram(programId);
        if(glGetProgrami(programId,GL_VALIDATE_STATUS)==GL_FALSE)
            System.err.println("Warning validating shader code: "+glGetProgramInfoLog(programId,4096));

        glDetachShader(programId,vertexShaderId);
        glDetachShader(programId,fragmentShaderId);
        glDetachShader(programId,geometryShaderId);

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteShader(geometryShaderId);

        uniforms=new HashMap<>();
    }

    public void addUniforms(String... names){
        for(String name:names)
            uniforms.put(name,glGetUniformLocation(programId,name));
    }

    public void bindAttribute(int index,String name){
        glBindAttribLocation(programId,index,name);
    }

    protected int createShader(String shaderCode,int shaderType){
        int shaderId=glCreateShader(shaderType);
        glShaderSource(shaderId,shaderCode);
        glCompileShader(shaderId);
        if(glGetShaderi(shaderId,GL_COMPILE_STATUS)==GL_FALSE)
            System.err.println("Error compiling shader code: "+glGetShaderInfoLog(shaderId,4096));
        return shaderId;
    }

    public void setUniform(String uniformName,Matrix4 matrix4){
        glUniformMatrix4fv(uniforms.get(uniformName),false,matrix4.val);
    }

    public void setUniform(String uniformName,float x,float y){
        glUniform2f(uniforms.get(uniformName),x,y);
    }

    public void setUniform(String uniformName,float x,float y,float z){
        glUniform3f(uniforms.get(uniformName),x,y,z);
    }

    public void setUniform(String uniformName,float x,float y,float z,float w){
        glUniform4f(uniforms.get(uniformName),x,y,z,w);
    }

    public void setUniform(String uniformName,Vector2 v){
        glUniform2f(uniforms.get(uniformName),v.x,v.y);
    }

    public void setUniform(String uniformName,Vector3f v){
        glUniform3f(uniforms.get(uniformName),v.x,v.y,v.z);
    }

    public void setUniform(String uniformName,int value){
        glUniform1i(uniforms.get(uniformName),value);
    }

    public void setUniform(String uniformName,float[] value){
        glUniform1fv(uniforms.get(uniformName),value);
    }

    public void setUniform(String uniformName,float value){
        glUniform1f(uniforms.get(uniformName),value);
    }

    public void setUniform(String uniformName,Color color){
        glUniform4f(uniforms.get(uniformName),color.red(),color.green(),color.blue(),color.alpha());
    }

    public void setUniform(String uniformName,Texture texture){
        int uint=1;
        glActiveTexture(GL_TEXTURE0+uint);
        glBindTexture(GL_TEXTURE_2D,texture.getId());
        glUniform1i(uniforms.get(uniformName),uint);
    }

    public void setUniform(String uniformName,Texture3D texture){
        int uint=1;
        glActiveTexture(GL_TEXTURE0+uint);
        glBindTexture(GL_TEXTURE_2D_ARRAY,texture.getId());
        glUniform1i(uniforms.get(uniformName),uint);
    }

    public void setUniform(String uniformName,int[] array){
        glUniform1iv(uniforms.get(uniformName),array);
    }

    public void bind(){
        glUseProgram(programId);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void dispose(){
        unbind();
        if(programId!=0)
            glDeleteProgram(programId);
    }

    public int getProgramId(){
        return programId;
    }
}
