package com.faultyolive.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.faultyolive.gdx.utils.Vector2Accessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class TweenSample extends Sample {

    private SpriteBatch sb;
    private Texture texture;
    private Vector2 position;
    private TweenManager tweenManager;

    @Override
    public void create () {

        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("circle.png"));
        position = new Vector2(0.0f, 0.0f);
        tweenManager = new TweenManager();
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());
        moveTexture();

    }

    @Override
    public void render () {

        tweenManager.update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(texture, position.x, position.y);
        sb.end();

    }

    @Override
    public void dispose () {

        sb.dispose();
        texture.dispose();

    }

    private void moveTexture () {

        float left = (Gdx.graphics.getWidth() * 0.2f) - (texture.getWidth() * 0.5f);
        float right = (Gdx.graphics.getWidth() * 0.8f) - (texture.getWidth() * 0.5f);
        float up = (Gdx.graphics.getHeight() * 0.8f) - (texture.getHeight() * 0.5f);
        float down = (Gdx.graphics.getHeight() * 0.2f) - (texture.getHeight() * 0.5f);
        Tween.set(position, Vector2Accessor.XY).target(left, down).start(tweenManager);
        Tween.to(position, Vector2Accessor.X, 1.0f).target(right).start(tweenManager);
        Tween.to(position, Vector2Accessor.Y, 1.0f).target(up).delay(1.0f).start(tweenManager);
        Tween.to(position, Vector2Accessor.XY, 1.0f).target(left, down).delay(2.0f).start(tweenManager);
        Tween.to(position, Vector2Accessor.Y, 1.0f).target(up).delay(3.0f).start(tweenManager);
        Tween.to(position, Vector2Accessor.X, 1.0f).target(right).delay(4.0f).start(tweenManager);
        Tween.to(position, Vector2Accessor.XY, 1.0f).target(left, down).delay(5.0f).start(tweenManager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                moveTexture();
            }
        });

    }

}
