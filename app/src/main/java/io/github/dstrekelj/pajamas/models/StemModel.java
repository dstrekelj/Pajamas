package io.github.dstrekelj.pajamas.models;

import java.nio.ShortBuffer;

/**
 * A stem is an audio recording which will make up the final audio track. It has an ID unique to the
 * session during which it was recorded, a title, and a buffer of recorded PCM audio data.
 */
public class StemModel {
    private int id;
    private String title;
    private ShortBuffer buffer;

    public StemModel() {}

    public StemModel(int id, String title, ShortBuffer buffer) {
        this.id = id;
        this.title = title;
        this.buffer = buffer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ShortBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ShortBuffer buffer) {
        this.buffer = buffer;
    }
}
