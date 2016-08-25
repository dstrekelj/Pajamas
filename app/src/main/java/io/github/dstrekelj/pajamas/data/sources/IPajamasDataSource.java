package io.github.dstrekelj.pajamas.data.sources;

import io.github.dstrekelj.pajamas.models.TrackModel;

/**
 * Interface for storing and retrieving data from a Pajamas data source.
 */
public interface IPajamasDataSource {
    /**
     * Saves track to data source.
     * @param track Track
     */
    void saveTrack(TrackModel track);
}
