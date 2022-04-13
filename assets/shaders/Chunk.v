#version 460

layout(location=0) in vec3 pos;
layout(location=1) in vec3 uv;
layout(location=2) in vec4 color;
layout(location=3) in float id;
layout(location=4) in float ao;

out VO{ vec3 uv;vec4 color;float id;float ao; } vertout;

void main(void){
    gl_Position=vec4(pos,1.0);

    vertout.uv=uv;
    vertout.color=color;
    vertout.id=id;
    vertout.ao=ao;
}
