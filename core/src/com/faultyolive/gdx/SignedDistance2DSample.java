package com.faultyolive.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.TimeUtils;

public class SignedDistance2DSample extends Sample {
    ShaderProgram shader;
    Mesh mesh;
    long startTime;

    @Override
    public void create () {
        ShaderProgram.pedantic = false;

        String vs = Gdx.files.internal("signed/vertex.glsl").readString();
        String fs = Gdx.files.internal("signed/fragment.glsl").readString();

        shader = new ShaderProgram(vs, fs);

        if (!shader.isCompiled()) {
            throw new IllegalArgumentException("Error compiling distance field shader: " + shader.getLog());
        }


        mesh = new Mesh(true, 4, 6, new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"));
        float[] vertices = {
                 1, -1, 0,
                 1,  1, 0,
                -1,  1, 0,
                -1, -1, 0
        };

        short[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        mesh.setVertices(vertices);
        mesh.setIndices(indices);

        startTime = TimeUtils.millis();
    }

    @Override
    public void render () {
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shader.begin();
        shader.setUniform2fv("u_resolution", new float[]{
                (float)Gdx.graphics.getWidth(),
                (float)Gdx.graphics.getHeight()
        }, 0 , 2);
        shader.setUniformf("u_time", TimeUtils.timeSinceMillis(startTime) / 1000f);
        shader.setUniform2fv("u_mousePos", new float[]{
                Gdx.input.getX(),
                Gdx.graphics.getHeight() - Gdx.input.getY()
        }, 0, 2);
        mesh.render(shader, GL20.GL_TRIANGLES);
        shader.end();
    }
}
