package io.github.dstrekelj.pajamas;

import android.app.Application;

import io.github.dstrekelj.pajamas.data.PajamasDataRepository;

/**
 * Pajamas application entry point.
 */
public class PajamasApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PajamasDataRepository.getInstance(getApplicationContext());
    }
}
