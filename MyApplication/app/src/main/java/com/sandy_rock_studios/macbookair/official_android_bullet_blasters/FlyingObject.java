package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.PointF;

import java.util.Random;

/**
 * Created by macbookair on 11/19/17.
 */

public abstract class FlyingObject {
    float myX;
    float myY;
    float xVelocity;
    float yVelocity;
    float radius;
    int velocityBuffer = 50;

    public FlyingObject(int screenX, int screenY, float r){
        radius = r;
        reset(screenX, screenY);
    }
    public void update(long fps){
        myX = myX + xVelocity/fps;
        myY = myY + yVelocity/fps;
    }
    public void assignRandomVelocity(){
        Random random = new Random();
        xVelocity = random.nextFloat() * velocityBuffer;
        yVelocity = random.nextFloat() * velocityBuffer;
    }
    public void assignRandomPosition(int screenX, int screenY){
        Random random = new Random();
        myX = random.nextFloat() * screenX;
        myY = random.nextFloat() * screenY;
    }
    public float getxVelocity(){
        return xVelocity;
    }
    public float getyVelocity(){
        return yVelocity;
    }
    public float getRadius(){
        return radius;
    }
    public boolean intersectsWithCharacter(PointF characterPoint, float characterRadius){
        //this is a formula taken from https://stackoverflow.com/questions/8367512/algorithm-to-detect-if-a-circles-intersect-with-any-other-circle-in-the-same-pla
        double squaredCenterDistance = Math.pow(characterPoint.x - myX, 2) + Math.pow(characterPoint.y - myY, 2);
        double squaredRadiusSum = Math.pow(characterRadius + radius,2);
        double squaredRadiusDiff = Math.pow(characterRadius - radius,2);
        return squaredCenterDistance >= squaredRadiusDiff && squaredCenterDistance <= squaredRadiusSum;
    }
    public void reset(int screenX, int screenY){
        assignRandomPosition(screenX,screenY);
        assignRandomVelocity();
    }
}
