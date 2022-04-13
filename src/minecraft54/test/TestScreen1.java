package minecraft54.test;

import minecraft54.engine.app.AppScreen;
import minecraft54.engine.graphics.*;
import minecraft54.engine.maths.vectors.Vector3f;
import minecraft54.engine.util.Utils;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46C.*;

public class TestScreen1 implements AppScreen{


    VertexBufferObject vbo1,vbo2;
    VertexArrayObject vao1,vao2;

    ShaderProgram shader;

    PerspectiveCamera cam;


    @Override
    public void create(){
        cam=new PerspectiveCamera(Main.window.getWidth(),Main.window.getHeight(),0.001f,1000,85);
        cam.getPosition().set(3,3,3);

        shader=new ShaderProgram(Utils.readFile("test/shader1.v"),Utils.readFile("test/shader1.f"));
        shader.addUniforms("u_projection","u_view");

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
        vbo2.enableAttributes(new VertexAttribute(3,GL_FLOAT),new VertexAttribute(4,GL_FLOAT));
        vbo2.setData((long)2*vbo1.getVertexSize(),GL_DYNAMIC_DRAW);
    }

    @Override
    public void render(){
        glClearColor(0.1f,0.1f,0.15f,1f);
        cam.update();
        rotateCamera();
        controlCamera();

        shader.bind();
        shader.setUniform("u_projection",cam.getProjection());
        shader.setUniform("u_view",cam.getView());
        vao1.draw(GL_LINES,6);
        vao2.draw(GL_LINES,2);

        if(Main.keyboard.isKeyDown(GLFW_KEY_E))
            cam.getRotation().set(0,0,0);

        System.out.println(cam.getRotation().pitch());

        if(Main.keyboard.isKeyDown(GLFW_KEY_G)){
            Vector3f pos=cam.getPosition();
            Vector3f dir=cam.getRotation().direction();
            float[] vertices2=new float[]{
                    pos.x,pos.y,pos.z, 0,1,1,1,
                    pos.x+dir.x,pos.y+dir.y,pos.z+dir.z, 0,1,1,1
            };
            vbo2.setData(vertices2,GL_DYNAMIC_DRAW);
        }

        if(Main.keyboard.isKeyDown(GLFW_KEY_F11))
            Main.window.setFullscreen(!Main.window.isFullscreen());
        if(Main.keyboard.isKeyDown(GLFW_KEY_V))
            Main.window.setVSync(!Main.window.isVSync());
        if(Main.keyboard.isKeyDown(GLFW_KEY_ESCAPE))
            System.exit(0);
        Main.window.setTitle("Test 54; Fps: "+Main.cfg.FPS);
    }

    @Override
    public void resize(int width,int height){
        cam.resize(width,height);
    }

    @Override
    public void dispose(){
        shader.dispose();
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
        Vector3f dirXZ=cam.getRotation().direction();
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