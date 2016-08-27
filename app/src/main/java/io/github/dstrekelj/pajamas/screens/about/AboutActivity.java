package io.github.dstrekelj.pajamas.screens.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.dstrekelj.pajamas.R;

/**
 * TODO: Comment.
 */
public class AboutActivity extends AppCompatActivity {
    public static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle(R.string.about_heading);
    }
}
