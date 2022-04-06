#version 460

layout(location=0) in vec2 pos;
layout(location=1) in vec2 uv;
layout(location=2) in vec4 color;

out VO{ vec2 uv; vec4 color; } vertout;

uniform mat4 u_proj;
uniform mat4 u_modelView;

void main(void){
    gl_Position=u_proj*u_modelView*vec4(pos,2.0,1.0);

    vertout.uv=uv;
    vertout.color=color;
}