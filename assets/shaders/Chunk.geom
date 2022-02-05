#version 460

layout(triangle_strip,max_vertices=3)out;
layout(triangles)in;

in VO{
    vec3 uv;
    vec4 color;
    float id;
    float ao;
} vo[];

out GO{
    vec3 uv;
    vec4 color;
    float id;
    float ao;
} go;

uniform mat4 u_world;
uniform mat4 u_proj;
uniform mat4 u_model;

uniform vec3 u_camPos;

float dist(vec4 a,vec3 b){
    return sqrt((a.x-b.x)*(a.x-b.x)+(a.z-b.z)*(a.z-b.z));
}

void main(void){
    for(int i=0; i<3; i++){
        go.uv=vo[i].uv;
        go.color=vo[i].color;
        go.id=vo[i].id;
        go.ao=vo[i].ao;

        vec4 newpos=gl_in[i].gl_Position;
        //newpos.y-=pow(dist(u_model*newpos,u_camPos)/10,3.14);

        gl_Position=u_proj*u_world*u_model*newpos;
        EmitVertex();
    }

    EndPrimitive();
}