package com.sandy_rock_studios.macbookair.official_android_bullet_blasters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndPageActivity extends AppCompatActivity {
    //TextView finalScore = (TextView)findViewById(R.id.textView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //finalScore.setText("FINAL SCORE: " + BulletBlasterView.getScore());
        setContentView(R.layout.activity_end_page);
    }
}
