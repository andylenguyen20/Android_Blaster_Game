package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.PointF;

/**
 * Created by macbookair on 11/19/17.
 */

public class Character {
    private float myX;
    private float myY;
    private float radius;

    private float xVelocity;
    private float yVelocity;

    public Character(int screenX, int screenY, int rad){
        radius = rad;
        reset(screenX,screenY);
    }

    public void setPoint(float x, float y){
        myX = x;
        myY = y;
    }
    public void setxVelocity(float velocity){
        xVelocity = velocity;
    }
    public void setyVelocity(float velocity){
        yVelocity = velocity;
    }
    public float getyVelocity(){
        return yVelocity;
    }
    public float getX(){
        return myX;
    }
    public float getY(){
        return myY;
    }

    public float getRadius(){
        return radius;
    }

    public void update(long fps){
        myX = myX + xVelocity/fps;
        myY = myY + yVelocity/fps;
    }
    public void reset(int screenX, int screenY){
        myX = screenX/2;
        myY = screenY/2;
        xVelocity = 0;
        yVelocity = 0;
    }
}
