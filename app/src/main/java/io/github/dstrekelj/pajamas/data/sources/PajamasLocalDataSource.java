package io.github.dstrekelj.pajamas.data.sources;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import io.github.dstrekelj.pajamas.models.TrackModel;
import io.github.dstrekelj.pajamas.recorder.StemRecorderFactory;
import io.github.dstrekelj.toolkit.audio.PcmToWav;

/**
 * The local data source writes data to the external storage directory, inside a folder named
 * according to the application's package name.
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
            storageDirectory.mkdir();
        }
    }

    public static PajamasLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new PajamasLocalDataSource(context);
        }
        return instance;
    }

    @Override
    public void saveTrack(TrackModel track) {
        File file = new File(this.storageDirectory + File.separator + track.getTitle() + ".wav");

        ShortBuffer trackBuffer = track.getBuffer();
        trackBuffer.rewind();

        ByteBuffer bb = ByteBuffer.allocate(trackBuffer.capacity() * 2);
        bb.asShortBuffer().put(trackBuffer);

        byte[] data = PcmToWav.write(bb.array(), (byte)1, StemRecorderFactory.SAMPLE_RATE, (byte)16);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs a check to see if external storage media is mounted.
     * @return  `true` if external storage media is mounted, `false` if not
     */
    private boolean isExternalStorageMediaMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
