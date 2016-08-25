package io.github.dstrekelj.pajamas.data;

import android.content.Context;

import io.github.dstrekelj.pajamas.data.sources.IPajamasDataSource;
import io.github.dstrekelj.pajamas.data.sources.PajamasLocalDataSource;

/**
 * The Pajamas data repository is an access point to various data sources available to the
 * application.
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
