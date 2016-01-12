package com.faultyolive.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.faultyolive.gdx.utils.Vector2Accessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Box2dLightsSample extends Sample {

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private RayHandler rayHandler;
    private PointLight redLight;
    private PointLight greenLight;
    private PointLight blueLight;
    private Vector2 redLightPosition;
    private Vector2 greenLightPosition;
    private Vector2 blueLightPosition;
    private TweenManager tweenManager;
    private float timeWithoutUpdatingFPS;
    private String frameRate;

    @Override
    public void create () {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        timeWithoutUpdatingFPS = 0.0f;
        frameRate = "";
        World lightingWorld = new World(new Vector2(0.0f, 0.0f), true);
        RayHandler.setGammaCorrection(true);
        rayHandler = new RayHandler(lightingWorld, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rayHandler.setShadows(true);
        redLight = new PointLight(rayHandler, 3000, new Color(1.0f, 0.1f, 0.1f, 0.5f), Gdx.graphics.getWidth() * 1.5f, 0.0f, 0.0f);
        greenLight = new PointLight(rayHandler, 3000, new Color(0.1f, 1.0f, 0.1f, 0.5f), Gdx.graphics.getWidth() * 1.5f, 0.0f, 0.0f);
        blueLight = new PointLight(rayHandler, 3000, new Color(0.1f, 0.1f, 1.0f, 0.5f), Gdx.graphics.getWidth() * 1.5f, 0.0f, 0.0f);
        redLightPosition = new Vector2(0.0f, 0.0f);
        greenLightPosition = new Vector2(0.0f, 0.0f);
        blueLightPosition = new Vector2(0.0f, 0.0f);
        tweenManager = new TweenManager();
        Tween.registerAccessor(Vector2.class, new Vector2Accessor());

        BodyDef squareBodyDef = new BodyDef();
        squareBodyDef.position.set(Gdx.graphics.getWidth() * 0.65f, Gdx.graphics.getHeight() * 0.35f);
        Body squareBody = lightingWorld.createBody(squareBodyDef);
        PolygonShape squareShape = new PolygonShape();
        squareShape.setAsBox(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
        FixtureDef squareFixtureDef = new FixtureDef();
        squareFixtureDef.shape = squareShape;
        squareBody.createFixture(squareFixtureDef);

        BodyDef circleBodyDef = new BodyDef();
        circleBodyDef.position.set(Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getHeight() * 0.65f);
        Body circleBody = lightingWorld.createBody(circleBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Gdx.graphics.getWidth() * 0.08f);
        FixtureDef circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circleShape;
        circleBody.createFixture(circleFixtureDef);

        moveRedLight();
        moveGreenLight();
        moveBlueLight();

    }

    @Override
    public void render () {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
        tweenManager.update(Gdx.graphics.getDeltaTime());
        redLight.setPosition(redLightPosition);
        greenLight.setPosition(greenLightPosition);
        blueLight.setPosition(blueLightPosition);
        timeWithoutUpdatingFPS += Gdx.graphics.getDeltaTime();
        if(timeWithoutUpdatingFPS > 1.0f) {
            timeWithoutUpdatingFPS = 0.0f;
            frameRate = (int)(1 / Gdx.graphics.getDeltaTime()) + "fps";
        }
        spriteBatch.begin();
        font.draw(spriteBatch, frameRate, Gdx.graphics.getWidth() * 0.01f, Gdx.graphics.getHeight() * 0.99f);
        spriteBatch.end();

    }

    private void moveRedLight () {

        float l = Gdx.graphics.getWidth() * 0.05f; // left
        float u = Gdx.graphics.getHeight() * 0.95f; // up
        float d = Gdx.graphics.getHeight() * 0.05f; // down
        float t = 1.5f; // time
        Tween.set(redLightPosition, Vector2Accessor.XY).target(l, u).start(tweenManager);
        Tween.to(redLightPosition, Vector2Accessor.Y, t).target(d).start(tweenManager);
        Tween.to(redLightPosition, Vector2Accessor.Y, t).target(u).delay(t).start(tweenManager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                moveRedLight();
            }
        });

    }

    private void moveGreenLight() {

        float l = Gdx.graphics.getWidth() * 0.2f; // left
        float r = Gdx.graphics.getWidth() * 0.8f; // right
        float u = Gdx.graphics.getHeight() * 0.8f; // up
        float d = Gdx.graphics.getHeight() * 0.2f; // down
        float t = 3.0f; // time
        Tween.set(greenLightPosition, Vector2Accessor.XY).target(l, d).start(tweenManager);
        Tween.to(greenLightPosition, Vector2Accessor.X, t).target(r).start(tweenManager);
        Tween.to(greenLightPosition, Vector2Accessor.Y, t).target(u).delay(t).start(tweenManager);
        Tween.to(greenLightPosition, Vector2Accessor.XY, t).target(l, d).delay(t * 2).start(tweenManager);
        Tween.to(greenLightPosition, Vector2Accessor.Y, t).target(u).delay(t * 3).start(tweenManager);
        Tween.to(greenLightPosition, Vector2Accessor.X, t).target(r).delay(t * 4).start(tweenManager);
        Tween.to(greenLightPosition, Vector2Accessor.XY, t).target(l, d).delay(t * 5).start(tweenManager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                moveGreenLight();
            }
        });

    }

    private void moveBlueLight () {

        float r = Gdx.graphics.getWidth() * 0.95f; // right
        float u = Gdx.graphics.getHeight() * 0.95f; // up
        float d = Gdx.graphics.getHeight() * 0.05f; // down
        float t = 2.0f; // time
        Tween.set(blueLightPosition, Vector2Accessor.XY).target(r, d).start(tweenManager);
        Tween.to(blueLightPosition, Vector2Accessor.Y, t).target(u).start(tweenManager);
        Tween.to(blueLightPosition, Vector2Accessor.Y, t).target(d).delay(t).start(tweenManager).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                moveBlueLight();
            }
        });

    }

}
