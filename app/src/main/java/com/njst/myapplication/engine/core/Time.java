package com.njst.myapplication.engine.core;

/**
 * Static utility for tracking time and frame deltas.
 */
public class Time {
    public static float deltaTime = 0.0f;
    public static float totalTime = 0.0f;
    
    private static long lastTime = 0;

    public static void init() {
        lastTime = System.nanoTime();
        totalTime = 0.0f;
    }

    public static void update() {
        long currentTime = System.nanoTime();
        // Convert nanoseconds to seconds
        deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
        lastTime = currentTime;
        totalTime += deltaTime;
    }
}
