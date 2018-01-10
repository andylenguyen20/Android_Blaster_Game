package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by macbookair on 11/19/17.
 */

public class BulletBlasterView extends SurfaceView implements Runnable{
    Thread gameThread = null;
    SurfaceHolder ourHolder;
    Canvas canvas;
    Paint paint;

    volatile boolean playing;
    boolean paused = false;

    long fps = 50;
    private long timeThisFrame;

    //size of screen in pixels
    int screenX;
    int screenY;

    Character character;
    FlyingObject[] flyingObjects;
    int nextBullet = 0;
    public static final int maxBullets = 50;
    public static final int BACKGROUND_COLOR = Color.BLACK;

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

        character = new Character();
        flyingObjects = new FlyingObject[40];

        restart();
        /*
        new CountDownTimer(3000, 1000){
            public void onTick(long millisUntilFinished){

            }

            public void onFinish(){
                restart();
            }
        }.start();
        */
    }

    //CONTINUOUSLY CALLED
    @Override
    public void run() {
        while(playing){
            long startFrameTime = System.currentTimeMillis();
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
        //TODO: launch bullets in a timed fashion
        for(FlyingObject fo: flyingObjects){

            if(fo.isOffscreen(screenY)){
                fo.setInactive();
                fo.spawn(screenX,screenY);
            }

            //may need to move this to another method, as update gets called with no time delay
            //and we intend to launch bullets with time delay
            fo.update(fps);
            if(character.inCollision(fo) && fo.getStatus()){
                fo.setInactive();
                fo.reactToCollision(this);
                fo.spawn(screenX,screenY);
                System.out.println("collision detected");
            }
        }
    }
    public void draw(){
        //make sure our drawing surface is valid or we crash
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(BACKGROUND_COLOR);
            paint.setColor(character.getColor());
            canvas.drawCircle(character.position.x, character.position.y, character.radius, paint);
            //draw the flying objects
            for(FlyingObject fo: flyingObjects){
                paint.setColor(fo.getColor());
                if(fo.getStatus()){
                    canvas.drawCircle(fo.position.x, fo.position.y, fo.radius, paint);
                }
            }
            if(lives <= 0){
                endGame();
            }
            paint.setTextSize(40);
            score++;
            canvas.drawText("Score: " + score + "   Lives: " + lives, 100, 100, paint);
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }
    public boolean onTouchEvent(MotionEvent motionEvent){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                //System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                //System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
            case MotionEvent.ACTION_UP:
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                //System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
        }
        return true;
    }

    //GAME STAGING
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
    public void endGame(){
        Context myContext = getContext();
        Intent intent = new Intent(myContext, EndPageActivity.class);
        myContext.startActivity(intent);
    }
    public void restart(){
        System.out.println("GAME RESTARTED");
        for(int i = 0; i < flyingObjects.length; i++){
            flyingObjects[i] = generateRandomFlyingObject();
            flyingObjects[i].spawn(screenX,screenY);
        }
        character.reset(screenX,screenY);
        score = 0;
        lives = 3;
    }

    //HELPERS
    public FlyingObject generateRandomFlyingObject(){

        Random random = new Random();
        int randomInt = random.nextInt(100);
        if(randomInt < 10){
            return randomPowerUp();
        }else{
            return new Bullet();
        }
    }
    public FlyingObject randomPowerUp() {
        FlyingObject[] options = new FlyingObject[]
                {new MiniBullet(), new MegaBullet(),
                        new ClearBullet()};
        return options[(int)(Math.random() * options.length)];
    }
}
