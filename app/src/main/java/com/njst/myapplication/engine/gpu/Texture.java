package com.njst.myapplication.engine.gpu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.opengl.GLES31;

public class Texture {
    public static Bitmap loadBitmap(Context context, int resourceId) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false; // No pre-scaling
        return BitmapFactory.decodeResource(context.getResources(), resourceId, options);
    }
}
