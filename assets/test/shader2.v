#version 460

in vec3 pos;
in vec2 uv;

out vec2 o_uv;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(void){
    gl_Position=u_projection*u_view*vec4(pos,1.0);

    o_uv=uv;
}