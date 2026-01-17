package com.njst.myapplication.engine.data;

import java.util.ArrayList;
import java.util.List;

public class SceneData {
    // Example: List of active entities in the scene
    // In a real engine, this might be a spatial partition structure
    public static List<Integer> sceneEntities = new ArrayList<>();

    // Global environment settings
    public static float[] ambientLightColor = { 1.0f, 1.0f, 1.0f, 1.0f };
    public static int activeCameraEntity = -1;

    // Camera Matrices
    public static float[] viewMatrix = new float[16];
    public static float[] projectionMatrix = new float[16];

    public static void addEntity(int entityId) {
        sceneEntities.add(entityId);
    }

    public static void removeEntity(int entityId) {
        sceneEntities.remove(Integer.valueOf(entityId));
    }

    public static void clear() {
        sceneEntities.clear();
        activeCameraEntity = -1;
    }
}
