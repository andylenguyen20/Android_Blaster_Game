package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.Color;
import android.graphics.PointF;

/**
 * Created by macbookair on 11/19/17.
 */

public class Character extends ScreenObject {
    public static final int DEFAULT_CHARACTER_RADIUS = 50;
    public static final int DEFAULT_CHARACTER_COLOR = Color.argb(255,255,255,255);

    public Character(){
        super(DEFAULT_CHARACTER_RADIUS);
    }

    public boolean reset(int screenX, int screenY){
        setPoint(screenX/2,screenY/2);
        radius = DEFAULT_CHARACTER_RADIUS;
        return true;
    }
    public boolean inCollision(FlyingObject flyingObject){
        //this is a formula taken from https://stackoverflow.com/questions/8367512/algorithm-to-detect-if-a-circles-intersect-with-any-other-circle-in-the-same-pla
        double squaredCenterDistance = Math.pow(position.x - flyingObject.position.x, 2) + Math.pow(position.y - flyingObject.position.y, 2);
        double squaredRadiusSum = Math.pow(radius + flyingObject.radius,2);
        double squaredRadiusDiff = Math.pow(radius - flyingObject.radius,2);
        return squaredCenterDistance >= squaredRadiusDiff && squaredCenterDistance <= squaredRadiusSum;
    }
    public int getColor(){
        return DEFAULT_CHARACTER_COLOR;
    }
}
