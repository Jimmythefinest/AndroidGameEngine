package com.njst.myapplication.engine.gpu;

import android.opengl.GLES31;

public class SSBO {
    private int bufferId;

    public SSBO() {
        int[] buffers = new int[1];
        GLES31.glGenBuffers(1, buffers, 0);
        bufferId = buffers[0];
    }

    public void bind(int bindingPoint) {
        GLES31.glBindBufferBase(GLES31.GL_SHADER_STORAGE_BUFFER, bindingPoint, bufferId);
    }
    
    public void uploadData(long size, java.nio.Buffer data) {
        GLES31.glBindBuffer(GLES31.GL_SHADER_STORAGE_BUFFER, bufferId);
        GLES31.glBufferData(GLES31.GL_SHADER_STORAGE_BUFFER, (int)size, data, GLES31.GL_DYNAMIC_DRAW);
        GLES31.glBindBuffer(GLES31.GL_SHADER_STORAGE_BUFFER, 0);
    }

    public void delete() {
        GLES31.glDeleteBuffers(1, new int[]{bufferId}, 0);
    }
}
