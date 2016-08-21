package io.github.dstrekelj.pajamas.models;

/**
 * TODO: Comment.
 */
public class StemModel {
    private String title;
    private short[] buffer;

    public StemModel() {}

    public StemModel(String title, short[] buffer) {
        this.title = title;
        this.buffer = buffer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short[] getBuffer() {
        return buffer;
    }

    public void setBuffer(short[] buffer) {
        this.buffer = buffer;
    }
}
