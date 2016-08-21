package io.github.dstrekelj.pajamas;

import android.app.Application;

import io.github.dstrekelj.pajamas.data.PajamasDataRepository;

/**
 * TODO: Comment.
 */
public class PajamasApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PajamasDataRepository.getInstance(getApplicationContext());
    }
}
