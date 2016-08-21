package io.github.dstrekelj.pajamas.data.sources;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * TODO: Comment.
 */
public class PajamasLocalDataSource implements IPajamasDataSource {
    public static final String TAG = "PajamasLocalDataSource";

    private static PajamasLocalDataSource instance;

    private Context context;
    private File storageDirectory;

    private PajamasLocalDataSource(Context context) {
        this.context = context.getApplicationContext();

        if (isExternalStorageMediaMounted()) {
            String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "Android"
                    + File.separator + "data"
                    + File.separator + context.getPackageName()
                    + File.separator;
            storageDirectory = new File(storagePath);
        }
    }

    public static PajamasLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new PajamasLocalDataSource(context);
        }
        return instance;
    }

    private boolean isExternalStorageMediaMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
