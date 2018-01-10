package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.Color;

/**
 * Created by macbookair on 1/7/18.
 */

public class ClearBullet extends FlyingObject {
    public static final int DEFAULT_CLEARBULLET_COLOR = Color.argb(255, 50, 205, 50);
    public ClearBullet(){
        super();
    }
    public void reactToCollision(BulletBlasterView bulletBlasterView){
        FlyingObject[] flyingObjects = bulletBlasterView.flyingObjects;
        for(int i = 0; i < flyingObjects.length; i++){
            flyingObjects[i].setInactive();
        }
    }
    public int getColor(){
        return DEFAULT_CLEARBULLET_COLOR;
    }
}
