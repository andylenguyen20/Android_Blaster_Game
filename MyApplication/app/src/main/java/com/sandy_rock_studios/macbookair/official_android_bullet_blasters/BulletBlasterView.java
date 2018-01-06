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
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import static com.sandy_rock_studios.macbookair.official_android_bullet_blasters.R.id.textView;

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
    int charRadius = 20;

    int bulletRadius = 10;

    int velocityBuffer = 50;

    ArrayList<FlyingObject> bullets;

    static int score = 0;
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
        System.out.println(screenX);
        System.out.println(screenY);

        character = new Character(screenX, screenY, charRadius);
        bullets = new ArrayList<>();

        new CountDownTimer(3000, 1000){
            public void onTick(long millisUntilFinished){

            }
            public void onFinish(){
                restart();
            }
        }.start();
    }

    public void restart(){
        //initialization of bullets
        for(int i = 0; i < 40; i++){
            bullets.add(new Bullet(screenX, screenY, bulletRadius));
        }

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
        character.update(fps);

        Iterator<FlyingObject> iter = bullets.iterator();
        for(FlyingObject fo: bullets){
            fo.update(fps);
            if(fo.intersectsWithCharacter(character) && fo.isVisible()){
                fo.setVisibility(false);
                lives--;
                System.out.println("character has died");
            }
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
            for(FlyingObject fo: bullets){
                if(fo.isVisible()){
                    canvas.drawCircle(fo.getMyX(), fo.getMyY(), fo.getRadius(), paint);
                }
            }
            if(lives <= 0){

                endGame();
                /*
                paint.setTextSize(50);
                String lost = "YOU HAVE LOST!";
                int xPos = (screenX/2) - (int)(paint.measureText("YOU HAVE LOST")/2);
                int yPos = (int)((screenY/2) - ((paint.descent() + paint.ascent())/2));
                canvas.drawText(lost, xPos, yPos, paint);*/
            }
            paint.setTextSize(40);
            score++;
            canvas.drawText("Score: " + score + "   Lives: " + lives, 100, 100, paint);
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
                //System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                //System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
            //Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                //action
                character.setPoint(motionEvent.getX(), motionEvent.getY());
                //System.out.println("point: (" + character.getX() + "," + character.getY() + ")");
                break;
        }
        return true;
    }

    public void endGame(){
        Context myContext = getContext();
        Intent intent = new Intent(myContext, EndPageActivity.class);
        myContext.startActivity(intent);
    }

}
