package com.njst.myapplication.engine.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.njst.myapplication.engine.core.Engine;

public class GLRenderer implements GLSurfaceView.Renderer {
    private final Context context;

    public GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Engine.getInstance().init(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Handle screen resize, update projection matrix in SceneData/Camera
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Engine.getInstance().update();
        Engine.getInstance().render();
    }
}
