package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.Color;

/**
 * Created by macbookair on 11/19/17.
 */

public class Bullet extends FlyingObject{
    public static final int DEFAULT_BULLET_COLOR = Color.argb(255, 255, 255, 255);
    public Bullet() {
        super();
    }
    public void reactToCollision(BulletBlasterView bulletBlasterView){
        bulletBlasterView.lives--;
    }
    public int getColor(){
        return DEFAULT_BULLET_COLOR;
    }
}
