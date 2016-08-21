package io.github.dstrekelj.pajamas.models;

import java.nio.ShortBuffer;

/**
 * TODO: Comment.
 */
public class StemModel {
    private String title;
    private ShortBuffer buffer;

    public StemModel() {}

    public StemModel(String title, ShortBuffer buffer) {
        this.title = title;
        this.buffer = buffer;
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
