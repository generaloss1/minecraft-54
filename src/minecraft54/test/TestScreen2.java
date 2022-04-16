package minecraft54.test;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.*;
import minecraft54.engine.maths.Matrix4f;
import minecraft54.engine.maths.vectors.Vector3f;
import minecraft54.engine.util.Utils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46C.*;

public class TestScreen2 implements AppScreen{


    VertexBufferObject vbo1,vbo2;
    VertexArrayObject vao1,vao2;

    ShaderProgram shader1,shader2;

    PerspectiveCamera cam;

    Texture texture;


    @Override
    public void create(){
        cam=new PerspectiveCamera(Main.window.getWidth(),Main.window.getHeight(),0.001f,1000,85);
        cam.getPosition().set(0,0,2);
        cam.getRotation().set(0,0,0);

        texture=new Texture("textures/block/deepslate.png");
        texture.gen();

        shader1=new ShaderProgram(Utils.readFile("test/shader1.v"),Utils.readFile("test/shader1.f"));
        shader1.addUniforms("u_projection","u_view");

        shader2=new ShaderProgram(Utils.readFile("test/shader2.v"),Utils.readFile("test/shader2.f"));
        shader2.addUniforms("u_texture","u_projection","u_view");

        vao1=new VertexArrayObject();
        vbo1=new VertexBufferObject();
        vbo1.enableAttributes(new VertexAttribute(3,GL_FLOAT),new VertexAttribute(4,GL_FLOAT));
        float[] vertices1=new float[]{
                0 ,0,0,  1,0,0,1,
                10,0,0,  1,0,0,1,

                0,0 ,0,  0,1,0,1,
                0,10,0,  0,1,0,1,

                0,0,0 ,  0,0,1,1,
                0,0,10,  0,0,1,1,
        };
        vbo1.setData(vertices1,GL_STATIC_DRAW);

        vao2=new VertexArrayObject();
        vbo2=new VertexBufferObject();
        vbo2.enableAttributes(new VertexAttribute(3,GL_FLOAT),new VertexAttribute(2,GL_FLOAT));
        float[] vertices2={
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
                 0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
                 0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                 0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
                 0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                 0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
                 0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                 0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                 0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                 0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                 0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                 0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                 0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
                 0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                 0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
                 0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
                 0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
                 0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                 0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
        };
        vbo2.setData(vertices2,GL_STATIC_DRAW);
    }

    @Override
    public void render(){
        glClearColor(0.1f,0.1f,0.15f,1f);

        //rotateCamera();
        controlCamera();
        cam.update();

        texture.bind(0);

        shader1.bind();
        shader1.setUniform("u_projection",cam.getProjection());
        shader1.setUniform("u_view",Matrix4f.lookAt(cam.getPosition(),cam.getPosition().clone().mul(-1)));
        vao1.draw(GL_LINES,6);

        shader2.bind();
        shader2.setUniform("u_texture",0);
        shader2.setUniform("u_projection",cam.getProjection());
        shader2.setUniform("u_view",Matrix4f.lookAt(cam.getPosition(),cam.getPosition().clone().mul(-1)));
        vao2.draw(36);
    }

    @Override
    public void resize(int width,int height){
        cam.resize(width,height);
    }

    @Override
    public void dispose(){
        texture.dispose();
        shader1.dispose();
        shader2.dispose();
        vbo1.dispose();
        vao1.dispose();
        vbo2.dispose();
        vao2.dispose();
    }

    @Override
    public void onSet(String arg){

    }


    public boolean ignoreCamRot=true;
    public void rotateCamera(){
        if(Main.window.isFocused()){
            int x=Main.mouse.getX();
            int y=Main.mouse.getY();
            int halfW=Math.round(Main.window.getWidth()/2f);
            int halfH=Math.round(Main.window.getHeight()/2f);

            float dx=halfW-x;
            float dy=halfH-y;
            float rotX=dx/Main.window.getWidth()*85;
            float rotY=dy/Main.window.getHeight()*85;

            Main.mouse.setPos(
                    rotX==0?x:halfW,
                    rotY==0?y:halfH
            );

            if(ignoreCamRot){
                ignoreCamRot=false;
                return;
            }

            cam.getRotation().rotate(rotX,rotY,0);
            cam.getRotation().constrain();

        }
        Main.mouse.show(!Main.window.isFocused());
    }

    public void controlCamera(){
        float cam_speed=0.05f;
        Vector3f controlMoveVel=new Vector3f();
        Vector3f dirXZ=cam.getPosition().clone().mul(-1).nor();//cam.getRotation().direction();
        dirXZ.y=0;
        if(Main.keyboard.isKeyPressed(GLFW_KEY_W))
            controlMoveVel.add(dirXZ.mul(cam_speed));
        if(Main.keyboard.isKeyPressed(GLFW_KEY_S))
            controlMoveVel.add(dirXZ.mul(-cam_speed));
        if(Main.keyboard.isKeyPressed(GLFW_KEY_SPACE))
            controlMoveVel.y+=cam_speed;
        if(Main.keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
            controlMoveVel.y-=cam_speed;
        if(Main.keyboard.isKeyPressed(GLFW_KEY_D))
            controlMoveVel.add(Vector3f.crs(new Vector3f(0,1,0),dirXZ).mul(cam_speed));
        if(Main.keyboard.isKeyPressed(GLFW_KEY_A))
            controlMoveVel.add(Vector3f.crs(new Vector3f(0,1,0),dirXZ).mul(-cam_speed));
        controlMoveVel.nor().mul(cam_speed);
        cam.getPosition().add(controlMoveVel);
    }


}