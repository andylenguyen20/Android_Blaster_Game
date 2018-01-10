package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by macbookair on 1/8/18.
 */

public abstract class ScreenObject {
    protected PointF position;
    protected int radius;

    public ScreenObject(int r){
        position = new PointF();
        radius = r;
    }

    public void setPoint(float x, float y){
        position.set(x,y);
    }

    public abstract int getColor();
}
