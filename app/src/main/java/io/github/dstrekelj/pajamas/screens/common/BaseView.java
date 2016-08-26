package io.github.dstrekelj.pajamas.screens.common;

/**
 * Base view interface.
 */
public interface BaseView {
    /**
     * Displays toast.
     * @param message    Toast message
     */
    void displayToast(String message);

    /**
     * Displays toast.
     * @param stringResourceId  String resource ID
     */
    void displayToast(int stringResourceId);
}
