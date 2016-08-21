package io.github.dstrekelj.pajamas.data;

import android.content.Context;

import io.github.dstrekelj.pajamas.data.sources.IPajamasDataSource;
import io.github.dstrekelj.pajamas.data.sources.PajamasLocalDataSource;

/**
 * TODO: Comment.
 */
public class PajamasDataRepository {
    public static final String TAG = "PajamasDataRepository";

    private static PajamasDataRepository instance = null;

    public IPajamasDataSource localDataSource;

    private PajamasDataRepository(Context context) {
        localDataSource = PajamasLocalDataSource.getInstance(context);
    }

    public static PajamasDataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PajamasDataRepository(context.getApplicationContext());
        }
        return instance;
    }
}
