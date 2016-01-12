package com.faultyolive.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

class AccessorVector2 implements TweenAccessor<Vector2> {

    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;

    @Override
    public int getValues(Vector2 target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case X:
                returnValues[0] = target.x;
                return 1;
            case Y:
                returnValues[0] = target.y;
                return 1;
            case XY:
                returnValues[0] = target.x;
                returnValues[1] = target.y;
                return 2;
            default:
                // should not get here
                return -1;
        }
    }

    @Override
    public void setValues(Vector2 target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case X:
                target.set(newValues[0], target.y);
                break;
            case Y:
                target.set(target.x, newValues[0]);
                break;
            case XY:
                target.set(newValues[0], newValues[1]);
                break;
            default:
                // should not get here
        }
    }
}

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
        Tween.registerAccessor(Vector2.class, new AccessorVector2());
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
        Tween.set(position, AccessorVector2.XY).target(left, down).start(tweenManager);
        Tween.to(position, AccessorVector2.X, 1.0f).target(right).start(tweenManager);
        Tween.to(position, AccessorVector2.Y, 1.0f).target(up).delay(1.0f).start(tweenManager);
        Tween.to(position, AccessorVector2.XY, 1.0f).target(left, down).delay(2.0f).start(tweenManager);
        Tween.to(position, AccessorVector2.Y, 1.0f).target(up).delay(3.0f).start(tweenManager);
        Tween.to(position, AccessorVector2.X, 1.0f).target(right).delay(4.0f).start(tweenManager);
        Tween.to(position, AccessorVector2.XY, 1.0f).target(left, down).delay(5.0f).start(tweenManager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                moveTexture();
            }
        });

    }

}
