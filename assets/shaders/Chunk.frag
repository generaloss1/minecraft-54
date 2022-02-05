#version 460

out vec4 fragColor;

in GO{
    vec3 uv;
    vec4 color;
    float id;
    float ao;
} attr;

uniform sampler2DArray u_texture;
uniform int underWater;

void main(void){
    vec4 result=attr.color*texture(u_texture,attr.uv);
    if(result.a<=0)
        discard;

    if(underWater==1)
        result*=vec4(0.3,0.5,1.0,1.0);

    result.rgb*=attr.ao;
    fragColor=result;
}