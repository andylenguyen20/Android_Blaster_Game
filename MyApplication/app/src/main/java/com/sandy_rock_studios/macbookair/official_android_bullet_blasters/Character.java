package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.PointF;

/**
 * Created by macbookair on 11/19/17.
 */

public class Character {
    PointF point;
    private float radius;

    private float xVelocity;
    private float yVelocity;

    public Character(int screenX, int screenY, int rad){
        point = new PointF();
        radius = rad;
        reset(screenX,screenY);
    }

    public PointF getPoint(){
        return point;
    }

    public float getRadius(){
        return radius;
    }

    public void update(long fps){

    }
    public void reset(int screenX, int screenY){
        point.x = screenX/2;
        point.y = screenY/2;
        xVelocity = 0;
        yVelocity = 0;
    }
}
