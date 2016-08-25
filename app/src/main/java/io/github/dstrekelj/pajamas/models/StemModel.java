package io.github.dstrekelj.pajamas.models;

import java.nio.ShortBuffer;

/**
 * A stem is an audio recording which will make up the final audio track. It has an ID unique to the
 * session during which it was recorded, a title, and a buffer of recorded PCM audio data.
 */
public class StemModel extends AudioModel {
    private String title;
    private int id;

    public StemModel() {
        super();
    }

    public StemModel(ShortBuffer buffer, String title, int id) {
        super(buffer);
        this.title = title;
        this.id = id;
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
}
