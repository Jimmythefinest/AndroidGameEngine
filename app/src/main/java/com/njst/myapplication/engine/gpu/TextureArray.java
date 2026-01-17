package com.njst.myapplication.engine.gpu;

import android.graphics.Bitmap;
import android.opengl.GLES31;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TextureArray {
    private static final String TAG = "TextureArray";
    private int textureId;
    private int width;
    private int height;
    private int layers;

    public TextureArray(int width, int height, int layers) {
        this.width = width;
        this.height = height;
        this.layers = layers;

        int[] textures = new int[1];
        GLES31.glGenTextures(1, textures, 0);
        textureId = textures[0];

        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D_ARRAY, textureId);

        // Allocate storage for the texture array
        // We use 1 mipmap level for now
        GLES31.glTexStorage3D(GLES31.GL_TEXTURE_2D_ARRAY, 1, GLES31.GL_RGBA8, width, height, layers);

        // Set parameters
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D_ARRAY, GLES31.GL_TEXTURE_MIN_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D_ARRAY, GLES31.GL_TEXTURE_MAG_FILTER, GLES31.GL_NEAREST);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D_ARRAY, GLES31.GL_TEXTURE_WRAP_S, GLES31.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES31.GL_TEXTURE_2D_ARRAY, GLES31.GL_TEXTURE_WRAP_T, GLES31.GL_CLAMP_TO_EDGE);

        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D_ARRAY, 0);
    }

    public void upload(int index, Bitmap bitmap) {
        if (index < 0 || index >= layers) {
            Log.e(TAG, "Invalid layer index: " + index);
            return;
        }
        if (bitmap.getWidth() != width || bitmap.getHeight() != height) {
            Log.e(TAG, "Bitmap dimensions mismatch. Expected: " + width + "x" + height + ", Got: " + bitmap.getWidth()
                    + "x" + bitmap.getHeight());
            return;
        }

        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D_ARRAY, textureId);

        // GLUtils doesn't support 3D/Array textures, so we manually upload via ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocateDirect(bitmap.getByteCount());
        buffer.order(ByteOrder.nativeOrder());
        bitmap.copyPixelsToBuffer(buffer);
        buffer.position(0);


        // For a typical ARGB_8888 Bitmap, the format is GL_RGBA and type is GL_UNSIGNED_BYTE
        GLES31.glTexSubImage3D(
                GLES31.GL_TEXTURE_2D_ARRAY,
                0,      // level
                0, 0,   // xoffset, yoffset
                index,  // zoffset (layer index)
                width,
                height,
                1,      // depth (1 layer)
                GLES31.GL_RGBA,
                GLES31.GL_UNSIGNED_BYTE,
                buffer
        );

        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D_ARRAY, 0);
    }


    public void bind(int unit) {
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0 + unit);
        GLES31.glBindTexture(GLES31.GL_TEXTURE_2D_ARRAY, textureId);
    }
}
