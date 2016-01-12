package com.faultyolive.gdx.utils;

import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenAccessor;

public class Vector2Accessor implements TweenAccessor<Vector2> {

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