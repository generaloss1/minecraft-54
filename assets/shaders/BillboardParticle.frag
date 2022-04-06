#version 460

in VO{ vec2 uv; vec4 color; } attr;

out vec4 fragColor;

uniform sampler2D u_texture;

void main(void){
    fragColor=attr.color*texture(u_texture,attr.uv);
}