package com.njst.myapplication.engine.data;

import java.util.HashMap;
import java.util.Map;

public class ComponentStores {
    public static final Map<Integer, float[]> transforms = new HashMap<>();
    public static final Map<Integer, RenderComponent> renderComponents = new HashMap<>();
    public static final Map<Integer, float[]> modelMatrices = new HashMap<>();
    public static final Map<Integer, float[]> colors = new HashMap<>(); // New store for RGB

    public static class RenderComponent {
        public com.njst.myapplication.engine.gpu.Mesh mesh;
        public com.njst.myapplication.engine.gpu.Shader shader;
        public com.njst.myapplication.engine.gpu.TextureArray textureArray; 

        public RenderComponent(com.njst.myapplication.engine.gpu.Mesh mesh,
                com.njst.myapplication.engine.gpu.Shader shader,
                com.njst.myapplication.engine.gpu.TextureArray textureArray) {
            this.mesh = mesh;
            this.shader = shader;
            this.textureArray = textureArray;
        }
    }

    public static void clear() {
        transforms.clear();
        renderComponents.clear();
        modelMatrices.clear();
        colors.clear();
    }
}
