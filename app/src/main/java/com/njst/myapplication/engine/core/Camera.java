package com.njst.myapplication.engine.core;

import android.opengl.Matrix;
import com.njst.myapplication.engine.data.SceneData;

public class Camera {
    private float[] eye = new float[] { 0, 0, 10 };
    private float[] center = new float[] { 0, 0, 0 };
    private float[] up = new float[] { 0, 1, 0 };

    // Frustum settings
    private float ratio = 1.0f;
    private float near = 1.0f;
    private float far = 100.0f;

    public Camera() {
        updateProjection();
        updateView();
    }

    public void setAspectRatio(float ratio) {
        this.ratio = ratio;
        updateProjection();
    }

    public void updateView() {
        Matrix.setLookAtM(SceneData.viewMatrix, 0,
                eye[0], eye[1], eye[2],
                center[0], center[1], center[2],
                up[0], up[1], up[2]);
    }

    public void updateProjection() {
        Matrix.frustumM(SceneData.projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
    }
}
