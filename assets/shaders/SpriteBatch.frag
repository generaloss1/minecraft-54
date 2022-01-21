#version 400

out vec4 fragColor;

in VO{
    vec2 uv;
    vec4 color;
    float tex;
} attr;

uniform sampler2D u_textures[192];

void main(void){
    fragColor=attr.color*texture(u_textures[int(attr.tex)],attr.uv);
}