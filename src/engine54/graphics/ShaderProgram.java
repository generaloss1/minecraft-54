package engine54.graphics;

import engine54.graphics.texture.Texture;
import engine54.graphics.texture.Texture3D;
import engine54.maths.Matrix4d;
import engine54.maths.Matrix4f;
import engine54.maths.vectors.Vector2f;
import engine54.maths.vectors.Vector3f;
import engine54.util.Color;

import java.util.HashMap;

import static org.lwjgl.opengl.GL33.*;

public class ShaderProgram{


    private final int programId;
    private final HashMap<String,Integer> uniforms;


    public ShaderProgram(String vertexCode,String fragmentCode){
        int vertexShaderId=createShader(vertexCode,GL_VERTEX_SHADER);
        int fragmentShaderId=createShader(fragmentCode,GL_FRAGMENT_SHADER);

        programId=glCreateProgram();
        glAttachShader(programId,vertexShaderId);
        glAttachShader(programId,fragmentShaderId);

        glLinkProgram(programId);
        if(glGetProgrami(programId,GL_LINK_STATUS)==GL_FALSE)
            System.err.println("Linking shader error: "+glGetProgramInfoLog(programId,4096));
        glValidateProgram(programId);
        if(glGetProgrami(programId,GL_VALIDATE_STATUS)==GL_FALSE)
            System.err.println("Validating shader error: "+glGetProgramInfoLog(programId,4096));

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        uniforms=new HashMap<>();
    }

    public ShaderProgram(String vertexCode,String fragmentCode,String geometryCode){
        int vertexShaderId=createShader(vertexCode,GL_VERTEX_SHADER);
        int fragmentShaderId=createShader(fragmentCode,GL_FRAGMENT_SHADER);
        int geometryShaderId=createShader(geometryCode,GL_GEOMETRY_SHADER);

        programId=glCreateProgram();
        glAttachShader(programId,vertexShaderId);
        glAttachShader(programId,fragmentShaderId);
        glAttachShader(programId,geometryShaderId);

        glLinkProgram(programId);
        if(glGetProgrami(programId,GL_LINK_STATUS)==GL_FALSE)
            System.err.println("Linking shader error: "+glGetProgramInfoLog(programId,4096));
        glValidateProgram(programId);
        if(glGetProgrami(programId,GL_VALIDATE_STATUS)==GL_FALSE)
            System.err.println("Validating shader error: "+glGetProgramInfoLog(programId,4096));

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteShader(geometryShaderId);

        uniforms=new HashMap<>();
    }

    protected int createShader(String shaderCode,int shaderType){
        int shaderId=glCreateShader(shaderType);
        glShaderSource(shaderId,shaderCode);

        glCompileShader(shaderId);
        if(glGetShaderi(shaderId,GL_COMPILE_STATUS)==GL_FALSE)
            System.err.println("Compiling shader error: "+glGetShaderInfoLog(shaderId,4096));

        return shaderId;
    }


    public void addUniforms(String... names){
        for(String name:names)
            uniforms.put(name,glGetUniformLocation(programId,name));
    }

    public void bindAttribute(int index,String name){
        glBindAttribLocation(programId,index,name);
    }

    public void setUniform(String uniformName,Matrix4d matrix4d){
        //glUniformMatrix4dv(uniforms.get(uniformName),false,matrix4d.val); //OpenGL 4.4
    }

    public void setUniform(String uniformName,Matrix4f matrix4f){
        glUniformMatrix4fv(uniforms.get(uniformName),false,matrix4f.val);
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

    public void setUniform(String uniformName,Vector2f v){
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
        int num=0;
        glActiveTexture(GL_TEXTURE0+num);
        glBindTexture(GL_TEXTURE_2D,texture.getId());
        glUniform1i(uniforms.get(uniformName),num);
    }

    public void setUniform(String uniformName,Texture3D texture){
        int num=1;
        glActiveTexture(GL_TEXTURE0+num);
        glBindTexture(GL_TEXTURE_2D_ARRAY,texture.getId());
        glUniform1i(uniforms.get(uniformName),num);
    }

    public void setUniform(String uniformName,int[] array){
        glUniform1iv(uniforms.get(uniformName),array);
    }


    public int getId(){
        return programId;
    }

    public void bind(){
        glUseProgram(programId);
    }

    public static void unbind(){
        glUseProgram(0);
    }

    public void dispose(){
        glDeleteProgram(programId);
    }


}
