#version 310 es
uniform mat4 uModel;
uniform mat4 uView;
uniform mat4 uProjection;

layout(location = 0) in vec3 aPosition;
// layout(location = 1) in vec3 aColor; // Future use

out vec3 vPos;

void main() {
    vPos = aPosition;
    gl_Position = uProjection * uView * uModel * vec4(aPosition, 1.0);
}
