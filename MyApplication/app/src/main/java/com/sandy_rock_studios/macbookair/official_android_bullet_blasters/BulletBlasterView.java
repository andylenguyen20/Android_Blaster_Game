package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by macbookair on 11/19/17.
 */

public class BulletBlasterView extends SurfaceView implements Runnable{
    Thread gameThread = null;
    SurfaceHolder ourHolder;

    volatile boolean playing;

    boolean paused = false;

    Canvas canvas;
    Paint paint;

    long fps = 50;

    private long timeThisFrame;

    //size of screen in pixels
    int screenX;
    int screenY;

    Character character;
    int charRadius = 50;

    int bulletRadius = 10;

    int velocityBuffer = 50;

    ArrayList<FlyingObject> bullets;

    int score = 0;
    int lives = 3;

    public BulletBlasterView(Context context){
        super(context);
        ourHolder = getHolder();
        paint = new Paint();

        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        character = new Character(screenX, screenY, charRadius);
        bullets = new ArrayList<>();
        //initialization

        for(int i = 0; i < 10; i++){
            bullets.add(new Bullet(screenX, screenY, bulletRadius));
        }

        restart();
    }

    public void restart(){
        //other stuff here
        for(FlyingObject bullet: bullets){
            bullet.reset(screenX,screenY);
        }

        character.reset(screenX,screenY);
        if(lives == 0){
            score = 0;
            lives = 3;
        }
    }


    @Override
    public void run() {
        while(playing){

            long startFrameTime = System.currentTimeMillis();

            //update the fram
            if(!paused){
                update();
            }
            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame >=1){
                fps = 1000/timeThisFrame;
            }
        }
    }

    public void update(){
        System.out.println("updating");
        character.update(fps);

        for(FlyingObject fo: bullets){
            fo.update(fps);

        }
    }

    //draw the newly updated scene
    public void draw(){

        //make sure our drawing surface is valid or we crash
        if(ourHolder.getSurface().isValid()){
            //Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            //background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            paint.setColor(Color.argb(255,255,255,255));

            //draw the character
            canvas.drawCircle(character.getX(), character.getY(), charRadius, paint);
            //draw the flying objects

            //Draw everthing to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }catch(InterruptedException e){
            Log.e("error:", "joining thread");
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
            //Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                //action
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
            //Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                //action
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
        }
        return true;
    }
}
