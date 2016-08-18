package io.github.dstrekelj.pajamas.data.sources;

import android.content.Context;

/**
 * TODO: Comment.
 */
public class PajamasLocalDataSource implements IPajamasDataSource {
    public static final String TAG = "PajamasLocalDataSource";

    private static PajamasLocalDataSource instance;

    private Context context;

    private PajamasLocalDataSource(Context context) {
        this.context = context.getApplicationContext();
    }

    public static PajamasLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new PajamasLocalDataSource(context);
        }
        return instance;
    }
}
