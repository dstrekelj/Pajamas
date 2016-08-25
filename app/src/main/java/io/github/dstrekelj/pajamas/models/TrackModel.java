package io.github.dstrekelj.pajamas.models;

import java.nio.ShortBuffer;
import java.util.List;

/**
 * A track is a collection of recorded audio stems. When a track is finalized, the stems are mixed
 * together into the track's buffer. A track also defines a title, and a buffer of PCM audio data
 * which is the result of mixing track stems.
 */
public class TrackModel extends AudioModel {
    private String title;
    private List<StemModel> stems;

    public TrackModel() {}

    public TrackModel(ShortBuffer buffer, String title, List<StemModel> stems) {
        super(buffer);
        this.title = title;
        this.stems = stems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<StemModel> getStems() {
        return stems;
    }

    public void setStems(List<StemModel> stems) {
        this.stems = stems;
    }
}
