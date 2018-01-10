package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import java.util.Random;

/**
 * Created by macbookair on 11/19/17.
 */

public abstract class FlyingObject extends ScreenObject {
    public static final int VELOCITY_BUFFER = 60;
    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 5;
    public static final int DEFAULT_FO_RADIUS = 10;

    private float velocity;
    private boolean isActive;

    public FlyingObject(){
        super(DEFAULT_FO_RADIUS);
        isActive = false;
    }
    public void update(long fps){
        setPoint(position.x, position.y + position.y * velocity/fps);
    }

    public abstract void reactToCollision(BulletBlasterView bulletBlasterView);
    public boolean isOffscreen(int screenY){
        return position.y < 200;
    }

    public void spawn(int screenX, int screenY){
        setPositionAndVelocity(screenX,screenY);
        isActive = true;
    }
    public boolean getStatus(){
        return isActive;
    }
    public void setInactive(){
        isActive = false;
    }

    //HELPER METHOD
    /*
    public void setPositionAndVelocity(int screenX, int screenY){
        Random random = new Random();
        if(random.nextInt(2) == 1){
            setPoint(random.nextFloat() * screenX, screenY);
            velocity = -(random.nextFloat() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED)/VELOCITY_BUFFER;
        }else{
            setPoint(random.nextFloat() * screenX, 200);
            velocity = (random.nextFloat() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED)/VELOCITY_BUFFER;
        }
        System.out.println("x: " + position.x + " y: " + position.y + " vel: " + velocity);
    }*/

    public void setPositionAndVelocity(int screenX, int screenY){
        Random random = new Random();
        setPoint(random.nextFloat() * screenX, screenY);
        velocity = -(random.nextFloat() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED)/VELOCITY_BUFFER;
    }
}
