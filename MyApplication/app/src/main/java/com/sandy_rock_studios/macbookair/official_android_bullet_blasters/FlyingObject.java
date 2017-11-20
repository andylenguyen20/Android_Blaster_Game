package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.PointF;

import java.util.Random;

/**
 * Created by macbookair on 11/19/17.
 */

public abstract class FlyingObject {
    private float myX, myY, radius;
    private float xVelocity, yVelocity;
    private int velocityBuffer = 100;
    private boolean isVisible = true;

    public FlyingObject(int screenX, int screenY, float r){
        radius = r;
        reset(screenX, screenY);
    }
    public void update(long fps){
        myX = myX + xVelocity/fps;
        myY = myY + yVelocity/fps;
    }
    public void assignVelocity(int screenY){
        if(myY == screenY){
            xVelocity = 0;
            yVelocity = -velocityBuffer;
        }else{
            xVelocity = 0;
            yVelocity = velocityBuffer;
        }
    }
    public void assignRandomVelocity(){
        Random random = new Random();
        xVelocity = random.nextFloat() * velocityBuffer - velocityBuffer;
        yVelocity = random.nextFloat() * velocityBuffer - velocityBuffer;
    }
    public void assignRandomPosition(int screenX, int screenY){
        Random random = new Random();
        myX = random.nextFloat() * screenX;
        random.nextFloat();
        myY = random.nextInt(2) * screenY; //starts bullet at either bottom or top of screen
    }
    public float getMyX(){
        return myX;
    }
    public float getMyY(){
        return myY;
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
    public boolean intersectsWithCharacter(Character character){
        //this is a formula taken from https://stackoverflow.com/questions/8367512/algorithm-to-detect-if-a-circles-intersect-with-any-other-circle-in-the-same-pla
        double squaredCenterDistance = Math.pow(character.getX() - myX, 2) + Math.pow(character.getY() - myY, 2);
        double squaredRadiusSum = Math.pow(character.getRadius() + radius,2);
        double squaredRadiusDiff = Math.pow(character.getRadius() - radius,2);
        return squaredCenterDistance >= squaredRadiusDiff && squaredCenterDistance <= squaredRadiusSum;
    }
    public void reset(int screenX, int screenY){
        assignRandomPosition(screenX,screenY);
        //assignRandomVelocity();
        assignVelocity(screenY);
    }
    public boolean isVisible(){
        return isVisible;
    }
    public void setVisibility(boolean visibility){
        isVisible = visibility;
    }
}
