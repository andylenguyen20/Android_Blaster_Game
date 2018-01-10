package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.graphics.Color;

/**
 * Created by macbookair on 1/7/18.
 */

public class MegaBullet extends FlyingObject{
    public static final int DEFAULT_MEGABULLET_COLOR = Color.argb(255, 250, 0, 0);
    public MegaBullet(){
        super();
    }
    public void reactToCollision(BulletBlasterView bulletBlasterView){
        Character character = bulletBlasterView.character;
        character.radius = character.radius * 2;
    }
    public int getColor(){
        return DEFAULT_MEGABULLET_COLOR;
    }
}
