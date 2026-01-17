package com.njst.myapplication.engine.gpu;

import android.content.Context;
import android.opengl.GLES31;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Shaders {
    private static final String TAG = "Shaders";

    public static String loadShaderFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open resource: " + resourceId, e);
        } catch (Exception e) {
            throw new RuntimeException("Resource not found: " + resourceId, e);
        }
        return body.toString();
    }

    public static int compileShader(int type, String shaderCode) {
        int shader = GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, shaderCode);
        GLES31.glCompileShader(shader);

        // Check compilation status
        final int[] compileStatus = new int[1];
        GLES31.glGetShaderiv(shader, GLES31.GL_COMPILE_STATUS, compileStatus, 0);
        if (compileStatus[0] == 0) {
            Log.e(TAG, "Shader compilation failed: " + GLES31.glGetShaderInfoLog(shader));
            GLES31.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    public static int createProgram(String vertexCode, String fragmentCode) {
        int vertexShader = compileShader(GLES31.GL_VERTEX_SHADER, vertexCode);
        int fragmentShader = compileShader(GLES31.GL_FRAGMENT_SHADER, fragmentCode);

        int program = GLES31.glCreateProgram();
        GLES31.glAttachShader(program, vertexShader);
        GLES31.glAttachShader(program, fragmentShader);
        GLES31.glLinkProgram(program);

        return program;
    }
}
