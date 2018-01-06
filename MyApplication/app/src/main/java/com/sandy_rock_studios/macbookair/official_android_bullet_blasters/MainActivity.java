package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //BulletBlasterView bulletBlasterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bulletBlasterView = new BulletBlasterView(this);
        setContentView(R.layout.activity_main);
    }

    public void onPlay(View view){
        //bulletBlasterView = new BulletBlasterView(this);
        Intent intent = new Intent(this, CreateBulletBlasterViewActivity.class);
        startActivity(intent);
    }


    /*@Override
    protected void onResume() {
        super.onResume();
        bulletBlasterView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bulletBlasterView.pause();
    }*/
}
