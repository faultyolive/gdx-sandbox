package com.faultyolive.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputAdapter;

import java.util.HashMap;
import java.util.Map;

public class Sample extends InputAdapter implements ApplicationListener {

    public static final Map<String, Class<? extends Sample>> samples = new HashMap<String, Class<? extends Sample>>();
    static {
        samples.put("Hello world", TriangleSample.class);
        samples.put("2D Signed Distance", SignedDistance2DSample.class);
    }

    public static String[] all() {
        return samples.keySet().toArray(new String[samples.size()]);
    }

    public static Sample create(String name) {
        try {
           return samples.get(name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
