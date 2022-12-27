package io.fluentlenium.core.action;

/**
 * Access interface for input controls.
 */
public interface InputControl {
    /**
     * Execute keyboard actions
     *
     * @return mouse keyboard object
     */
    KeyboardActions keyboard();

    /**
     * Execute mouse actions
     *
     * @return mouse actions object
     */
    MouseActions mouse();
}
