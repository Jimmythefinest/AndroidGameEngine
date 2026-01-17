package com.njst.myapplication.engine.core;

import android.content.Context;
import android.util.Log;
import com.njst.myapplication.engine.data.ComponentStores;
import com.njst.myapplication.engine.data.EntityManager;
import com.njst.myapplication.engine.gpu.Mesh;
import com.njst.myapplication.engine.gpu.Shaders;
import com.njst.myapplication.engine.systems.RenderSystem;
import android.opengl.GLES31;

/**
 * Central hub for the game engine.
 * Manages the main loop (via GL callbacks), initialization, and systems.
 */
public class Engine {
    private static final String TAG = "Engine";
    private static Engine instance;
    private boolean isInitialized = false;

    private RenderSystem renderSystem;
    private IGame game;

    private Engine() {
        // Private constructor for singleton
    }

    public static synchronized Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    public void setGame(IGame game) {
        this.game = game;
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public void init(Context context) {
        if (isInitialized) {
            Log.w(TAG, "Engine already initialized.");
            return;
        }
        Log.d(TAG, "Initializing Engine...");
        this.context = context;

        // Initialize subsystems
        Time.init();
        renderSystem = new RenderSystem();

        // Enable Depth Testing
        GLES31.glEnable(GLES31.GL_DEPTH_TEST);

        if (game != null) {
            game.init();
        }

        isInitialized = true;
    }

    public void update() {
        if (!isInitialized)
            return;

        Time.update();

        if (game != null) {
            game.update();
        }

        // Update physics, logic, etc.
    }
    boolean once=true;
    public void render() {
        if (!isInitialized)
            return;

        GLES31.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT | GLES31.GL_DEPTH_BUFFER_BIT);
        if(once){
            Log.d(TAG, "Rendering...");
            once=false;
        }
        if (renderSystem != null) {
            renderSystem.render();
        }
    }

    public void shutdown() {
        Log.d(TAG, "Shutting down Engine...");
        // TODO: Cleanup GL resources (Mesh buffers, Programs)
        ComponentStores.clear();
        isInitialized = false;
        instance = null;
    }
}
