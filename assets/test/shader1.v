#version 460

in vec3 pos;
in vec4 color;

out vec4 o_color;

uniform mat4 u_projection;
uniform mat4 u_view;

void main(void){
    gl_Position=u_projection*u_view*vec4(pos,1.0);

    o_color=color;
}