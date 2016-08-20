package io.github.dstrekelj.pajamas.models;

import java.util.List;

/**
 * TODO: Comment.
 */
public class TrackModel {
    private String title;
    private List<StemModel> stems;

    public TrackModel() {}

    public TrackModel(String title, List<StemModel> stems) {
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
