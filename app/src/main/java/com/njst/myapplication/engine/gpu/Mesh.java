package com.njst.myapplication.engine.gpu;

import android.opengl.GLES31;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import com.njst.myapplication.engine.data.IGeometry;

public class Mesh {
    private int vaoId;
    private int vboId;
    private int iboId;
    private int instanceVboId;
    private int indexCount;
    private int instanceCount = 0;

    public Mesh(IGeometry geometry) {
        this(geometry.getVertices(), geometry.getIndices());
    }

    public Mesh(float[] vertices, short[] indices) {
        indexCount = indices.length;

        int[] vao = new int[1];
        GLES31.glGenVertexArrays(1, vao, 0);
        vaoId = vao[0];
        GLES31.glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = FloatBuffer.wrap(vertices);
        int[] vbo = new int[1];
        GLES31.glGenBuffers(1, vbo, 0);
        vboId = vbo[0];
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, vboId);
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GLES31.GL_STATIC_DRAW);

        GLES31.glVertexAttribPointer(0, 3, GLES31.GL_FLOAT, false, 3 * 4, 0);
        GLES31.glEnableVertexAttribArray(0);

        ShortBuffer indexBuffer = ShortBuffer.wrap(indices);
        int[] ibo = new int[1];
        GLES31.glGenBuffers(1, ibo, 0);
        iboId = ibo[0];
        GLES31.glBindBuffer(GLES31.GL_ELEMENT_ARRAY_BUFFER, iboId);
        GLES31.glBufferData(GLES31.GL_ELEMENT_ARRAY_BUFFER, indices.length * 2, indexBuffer, GLES31.GL_STATIC_DRAW);

        int[] ivbo = new int[1];
        GLES31.glGenBuffers(1, ivbo, 0);
        instanceVboId = ivbo[0];
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, instanceVboId);
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER, 0, null, GLES31.GL_DYNAMIC_DRAW);

        // New Stride: 16 (matrix) + 3 (color) = 19 floats * 4 bytes = 76 bytes
        int stride = 19 * 4;

        // Attributes 4-7: Model Matrix
        for (int i = 0; i < 4; i++) {
            GLES31.glEnableVertexAttribArray(4 + i);
            GLES31.glVertexAttribPointer(4 + i, 4, GLES31.GL_FLOAT, false, stride, (i * 4) * 4);
            GLES31.glVertexAttribDivisor(4 + i, 1);
        }

        // Attribute 8: Color (vec3)
        GLES31.glEnableVertexAttribArray(8);
        GLES31.glVertexAttribPointer(8, 3, GLES31.GL_FLOAT, false, stride, 16 * 4);
        GLES31.glVertexAttribDivisor(8, 1);

        GLES31.glBindVertexArray(0);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, 0);
    }

    public void updateInstanceBuffer(float[] instanceData, int count) {
        this.instanceCount = count;
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, instanceVboId);
        FloatBuffer buffer = FloatBuffer.wrap(instanceData);
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER, instanceData.length * 4, buffer, GLES31.GL_DYNAMIC_DRAW);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, 0);
    }

    public void draw() {
        GLES31.glBindVertexArray(vaoId);
        if (instanceCount > 0) {
            GLES31.glDrawElementsInstanced(GLES31.GL_TRIANGLES, indexCount, GLES31.GL_UNSIGNED_SHORT, 0, instanceCount);
        } else {
            GLES31.glDrawElements(GLES31.GL_TRIANGLES, indexCount, GLES31.GL_UNSIGNED_SHORT, 0);
        }
        GLES31.glBindVertexArray(0);
    }
}
