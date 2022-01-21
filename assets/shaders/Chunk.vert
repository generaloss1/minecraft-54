#version 400

in layout(location=0) vec3 pos;
in layout(location=1) vec3 uv;
in layout(location=2) vec4 color;
in layout(location=3) float id;
in layout(location=4) float ao;

out VO{
    vec3 uv;
    vec4 color;
    float id;
    noperspective float ao;
} vo;

void main(void){
    gl_Position=vec4(pos,1.0);

    vo.uv=uv;
    vo.color=color;
    vo.id=id;
    vo.ao=ao;
}