package io.github.dstrekelj.pajamas.screens.record;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.common.BasePresenter;
import io.github.dstrekelj.pajamas.screens.common.BaseView;

/**
 * TODO: Comment.
 */
public interface RecordContract {
    interface View extends BaseView {
        void displayStemInsertion(StemModel stem);
        void displayStemRemoval(StemModel stem);
        void displayTrackTitle(String title);
    }

    interface Presenter extends BasePresenter {
        void createStem();
        void deleteStem(StemModel stem);
        int updateStemPlayState(StemModel stem);
        int updateStemRecordState(StemModel stem);
        int updateTrackPlayState();
        void updateTrackTitle(String title);
        void finalizeTrack();
    }
}
