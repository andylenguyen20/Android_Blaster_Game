package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BulletBlasterView bulletBlasterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bulletBlasterView = new BulletBlasterView(this);
        setContentView(bulletBlasterView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bulletBlasterView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bulletBlasterView.pause();
    }
}
