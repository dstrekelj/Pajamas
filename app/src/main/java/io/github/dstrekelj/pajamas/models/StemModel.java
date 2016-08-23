package io.github.dstrekelj.pajamas.models;

import java.nio.ShortBuffer;

/**
 * TODO: Comment.
 */
public class StemModel {
    private int id;
    private String title;
    private ShortBuffer buffer;
    private boolean isNowPlaying;
    private boolean isNowRecording;

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

    public boolean isNowPlaying() {
        return isNowPlaying;
    }

    public void setNowPlaying(boolean nowPlaying) {
        isNowPlaying = nowPlaying;
    }

    public boolean isNowRecording() {
        return isNowRecording;
    }

    public void setNowRecording(boolean nowRecording) {
        isNowRecording = nowRecording;
    }
}
