package io.fluentlenium.utils.chromium;

/**
 * Control interface for chromium api.
 */
public interface ChromiumControl {
    /**
     * Features related to browser control under devtools protocol.
     *
     * @return a new chromium api instance
     */
    ChromiumApi getChromiumApi();
}
