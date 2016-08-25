package io.github.dstrekelj.pajamas.models;

import java.nio.ShortBuffer;

/**
 * TODO: Comment.
 */
public class AudioModel {
    private ShortBuffer buffer;

    public AudioModel() {}

    public AudioModel(ShortBuffer buffer) {
        this.buffer = buffer;
    }

    public ShortBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ShortBuffer buffer) {
        this.buffer = buffer;
    }
}
