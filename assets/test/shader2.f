#version 460

in vec2 o_uv;

out vec4 fragColor;

uniform sampler2D u_texture;

void main(void){
    fragColor=texture(u_texture,o_uv);
}