package com.njst.myapplication.engine.systems;

import android.util.Log;
import com.njst.myapplication.engine.data.ComponentStores;
import com.njst.myapplication.engine.data.SceneData;
import java.util.Arrays;

public class RenderSystem {
    private static final String TAG = "RenderSystem";

    public void render() {
        if (ComponentStores.renderComponents.isEmpty()) {
            return;
        }

        java.util.Map<ComponentStores.RenderComponent, java.util.List<Integer>> batches = new java.util.HashMap<>();

        for (java.util.Map.Entry<Integer, ComponentStores.RenderComponent> entry : ComponentStores.renderComponents
                .entrySet()) {
            ComponentStores.RenderComponent rc = entry.getValue();
            if (!batches.containsKey(rc)) {
                batches.put(rc, new java.util.ArrayList<>());
            }
            batches.get(rc).add(entry.getKey());
        }

        // 16 floats for matrix + 3 floats for RGB color = 19
        int stride = 19; 

        for (java.util.Map.Entry<ComponentStores.RenderComponent, java.util.List<Integer>> batch : batches.entrySet()) {
            ComponentStores.RenderComponent rc = batch.getKey();
            java.util.List<Integer> entities = batch.getValue();

            rc.shader.bind();
            rc.shader.setMat4("uView", SceneData.viewMatrix);
            rc.shader.setMat4("uProjection", SceneData.projectionMatrix);

            float[] instanceData = new float[entities.size() * stride];
            int offset = 0;

            for (Integer entityId : entities) {
                float[] modelMatrix = ComponentStores.modelMatrices.get(entityId);
                if (modelMatrix == null) {
                    modelMatrix = new float[16];
                    android.opengl.Matrix.setIdentityM(modelMatrix, 0);
                }
                System.arraycopy(modelMatrix, 0, instanceData, offset, 16);

                // Copy Color
                float[] color = ComponentStores.colors.get(entityId);
                if (color == null) color = new float[]{1.0f, 1.0f, 1.0f}; // Default white
                instanceData[offset + 16] = color[0];
                instanceData[offset + 17] = color[1];
                instanceData[offset + 18] = color[2];

                offset += stride;
            }

            rc.mesh.updateInstanceBuffer(instanceData, entities.size());
            rc.mesh.draw();
        }
    }
}
