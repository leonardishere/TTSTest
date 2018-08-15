package io.github.leonardishere.ttstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake);
    }
}
