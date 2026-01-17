#version 310 es
precision mediump float;

in vec3 vPos;
out vec4 fragColor;

void main() {
    // Simple color based on local position to visualize cube faces
    fragColor = vec4(vPos + 0.5, 1.0);
}
