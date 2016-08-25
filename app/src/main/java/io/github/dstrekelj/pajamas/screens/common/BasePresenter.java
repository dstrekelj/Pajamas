package io.github.dstrekelj.pajamas.screens.common;

/**
 * Base presenter interface.
 */
public interface BasePresenter {
    /**
     * Destroys presenter.
     */
    void destroy();

    /**
     * Starts presenter.
     */
    void start();

    /**
     * Stops presenter.
     */
    void stop();
}
