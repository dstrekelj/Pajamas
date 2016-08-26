package io.github.dstrekelj.pajamas.screens.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.dstrekelj.pajamas.screens.home.HomeActivity;
import io.github.dstrekelj.pajamas.screens.record.RecordActivity;

/**
 * TODO: Comment.
 */
public class IntroActivity extends AppCompatActivity implements IntroContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goToHomeScreen();
    }

    @Override
    public void goToHomeScreen() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}