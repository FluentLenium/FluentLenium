package io.fluentlenium.utils.chromium;

/**
 * Commands for the Chromium DevTools API.
 * <p>
 * Additional resources are available at:
 * <ul>
 *     <li>https://github.com/WICG/devtools-protocol</li>
 *     <li>https://chromedevtools.github.io/devtools-protocol/</li>
 * </ul>
 */
public enum Commands {
    /**
     * Send a command to the DevTools API.
     */
    SEND_COMMAND("SEND_COMMAND",
            "/session/:sessionId/chromium/send_command"),
    /**
     * Send a command to the DevTools API and wait for the response.
     */
    SEND_COMMAND_AND_GET_RESULT("SEND_COMMAND_AND_GET_RESULT",
            "/session/:sessionId/chromium/send_command_and_get_result");

    private final String cmdName;
    private final String cmdInfo;

    Commands(String command, String commandInfo) {
        this.cmdName = command;
        this.cmdInfo = commandInfo;
    }

    public String getCmdName() {
        return cmdName;
    }

    public String getCmdInfo() {
        return cmdInfo;
    }
}
