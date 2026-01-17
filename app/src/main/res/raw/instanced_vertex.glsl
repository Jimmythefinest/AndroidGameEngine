#version 310 es

layout(location = 0) in vec3 aPosition;

// Instanced Attributes
layout(location = 4) in vec4 aInstanceModelCol0;
layout(location = 5) in vec4 aInstanceModelCol1;
layout(location = 6) in vec4 aInstanceModelCol2;
layout(location = 7) in vec4 aInstanceModelCol3;

layout(location = 8) in vec3 aInstanceColor; 

uniform mat4 uView;
uniform mat4 uProjection;

out vec3 vColor;

void main() {
    mat4 modelMatrix = mat4(
        aInstanceModelCol0,
        aInstanceModelCol1,
        aInstanceModelCol2,
        aInstanceModelCol3
    );

    vColor = aInstanceColor;
    gl_Position = uProjection * uView * modelMatrix * vec4(aPosition, 1.0);
}
