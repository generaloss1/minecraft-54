#version 460

in VO{ vec2 uv; vec4 color; float tex; } attr;

out vec4 fragColor;

uniform sampler2D u_textures[192];

void main(void){
    fragColor=attr.color*texture(u_textures[int(round(attr.tex))],attr.uv);
}