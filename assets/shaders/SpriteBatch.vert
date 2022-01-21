#version 400

in layout(location=0) vec3 pos;
in layout(location=1) vec2 uv;
in layout(location=2) vec4 color;
in layout(location=3) float tex;

out VO{
    vec2 uv;
    vec4 color;
    float tex;
} vo;

uniform mat4 u_proj;
uniform mat4 u_view;

void main(void){
    gl_Position=u_proj*u_view*vec4(pos,1.0);

    vo.uv=uv;
    vo.color=color;
    vo.tex=tex;
}