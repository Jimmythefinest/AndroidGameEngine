package com.njst.myapplication.engine.gpu;

import android.content.Context;
import android.opengl.GLES31;

import java.util.HashMap;
import java.util.Map;

public class Shader {
    private int programId;
    private Map<String, Integer> uniformLocations = new HashMap<>();

    public Shader(Context context, int vertexResId, int fragmentResId) {
        String vertexSource = Shaders.loadShaderFromResource(context, vertexResId);
        String fragmentSource = Shaders.loadShaderFromResource(context, fragmentResId);
        this.programId = Shaders.createProgram(vertexSource, fragmentSource);
    }

    public void bind() {
        GLES31.glUseProgram(programId);
    }

    public int getProgramId() {
        return programId;
    }

    public int getUniformLocation(String name) {
        if (uniformLocations.containsKey(name)) {
            return uniformLocations.get(name);
        }
        int location = GLES31.glGetUniformLocation(programId, name);
        uniformLocations.put(name, location);
        return location;
    }

    public void setMat4(String name, float[] matrix) {
        int location = getUniformLocation(name);
        if (location != -1) {
            GLES31.glUniformMatrix4fv(location, 1, false, matrix, 0);
        }
    }

    public void setInt(String name, int value) {
        int location = getUniformLocation(name);
        if (location != -1) {
            GLES31.glUniform1i(location, value);
        }
    }
}
