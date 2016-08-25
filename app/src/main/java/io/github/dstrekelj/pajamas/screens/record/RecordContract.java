package io.github.dstrekelj.pajamas.screens.record;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.common.BasePresenter;
import io.github.dstrekelj.pajamas.screens.common.BaseView;

/**
 * The following interfaces define the behaviour of Record screen logic and visuals.
 */
public interface RecordContract {
    interface View extends BaseView {
        /**
         * Performs and displays the insertion of a new stem into the list of track stems.
         * @param stem  Stem model
         */
        void displayStemInsertion(StemModel stem);

        /**
         * Performs and displays the removal of an existing stem from the list of track stems.
         * @param stem  Stem model
         */
        void displayStemRemoval(StemModel stem);

        /**
         * Updates the display of the recorded track's title.
         * @param title Track title
         */
        void displayTrackTitle(String title);
    }

    interface Presenter extends BasePresenter {
        /**
         * Prompts the recording session to create a new track stem.
         */
        void createStem();

        /**
         * Prompts the deletion of an existing track stem.
         * @param stem  Stem model
         */
        void deleteStem(StemModel stem);

        /**
         * Updates the play state of a track stem (playing / stopped).
         * @param stem  Stem model
         * @return      Stem play state
         */
        int updateStemPlayState(StemModel stem);

        /**
         * Updates the record state of a track stem (recording / stopped).
         * @param stem  Stem model
         * @return      Stem record state
         */
        int updateStemRecordState(StemModel stem);

        /**
         * Updates the play state of the track (playing / stopped).
         * @return  Track play state
         */
        int updateTrackPlayState();

        /**
         * Updates track title.
         * @param title Track title
         */
        void updateTrackTitle(String title);

        /**
         * Finalizes track by mixing all of the recorded track stems together.
         */
        void finalizeTrack();
    }
}
