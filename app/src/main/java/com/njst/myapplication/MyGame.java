package com.njst.myapplication;

import com.njst.myapplication.engine.core.Engine;
import com.njst.myapplication.engine.core.Time;
import android.content.Context;
import android.opengl.Matrix;
import com.njst.myapplication.engine.core.IGame;
import com.njst.myapplication.engine.data.ComponentStores;
import com.njst.myapplication.engine.data.EntityManager;
import com.njst.myapplication.engine.gpu.Mesh;
import com.njst.myapplication.engine.core.Camera;
import com.njst.myapplication.engine.data.CubeGeometry;
import com.njst.myapplication.engine.gpu.Shader;
import android.util.Log;

public class MyGame implements IGame {
    private static final String TAG = "MyGame";
    private Camera camera;

    @Override
    public void init() {
        Log.d(TAG, "Initializing MyGame...");
        camera = new Camera();
        camera.setAspectRatio(1920.0f / 1080.0f);
        createCubes();
    }

    @Override
    public void update() {
        for (float[] modelMatrix : ComponentStores.modelMatrices.values()) {
            Matrix.rotateM(modelMatrix, 0, 100 * Time.deltaTime, 0.5f, 1.0f, 0.0f);
        }
    }

    private void createCubes() {
        Context context = Engine.getInstance().getContext();
        if (context == null) return;

        // Load Shader (without texture logic)
        Shader shader = new Shader(context, R.raw.instanced_vertex, R.raw.instanced_fragment);
        if (shader.getProgramId() == 0) return;

        Mesh mesh = new Mesh(new CubeGeometry());

        for (int i = 0; i < 100; i++) {
            int entityId = EntityManager.createEntity();

            float[] modelMatrix = new float[16];
            Matrix.setIdentityM(modelMatrix, 0);

            float x = (float) (Math.random() * 10.0 - 5.0);
            float y = (float) (Math.random() * 6.0 - 3.0);
            float z = (float) (Math.random() * 10.0 - 15.0);
            Matrix.translateM(modelMatrix, 0, x, y, z);
            
            float rx = (float) Math.random();
            float ry = (float) Math.random();
            Matrix.rotateM(modelMatrix, 0, (float) (Math.random() * 360), rx, ry, 0);

            ComponentStores.modelMatrices.put(entityId, modelMatrix);

            // Random Color
            float[] color = new float[] {
                (float)Math.random(), (float)Math.random(), (float)Math.random()
            };
            ComponentStores.colors.put(entityId, color);

            ComponentStores.renderComponents.put(entityId,
                    new ComponentStores.RenderComponent(mesh, shader, null));
        }
        Log.d(TAG, "Created 100 cubes with random colors.");
    }
}
