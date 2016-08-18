package io.github.dstrekelj.pajamas.screens.record;

import io.github.dstrekelj.pajamas.screens.common.BasePresenter;
import io.github.dstrekelj.pajamas.screens.common.BaseView;

/**
 * TODO: Comment.
 */
public interface RecordContract {
    interface View extends BaseView {
        void updatePlayState(boolean isPlaying);
        void updateRecordState(boolean isRecording);
    }

    interface Presenter extends BasePresenter {
        void changePlayState();
        void changeRecordState();
    }
}
