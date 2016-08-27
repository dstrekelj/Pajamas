package io.github.dstrekelj.pajamas.screens.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.dstrekelj.pajamas.screens.record.RecordActivity;

/**
 * The Intro screen serves only as a visual while the application is setting up from a cold start.
 */
public class IntroActivity extends AppCompatActivity implements IntroContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goToRecordScreen();
    }

    @Override
    public void goToRecordScreen() {
        startActivity(new Intent(this, RecordActivity.class));
        finish();
    }
}
