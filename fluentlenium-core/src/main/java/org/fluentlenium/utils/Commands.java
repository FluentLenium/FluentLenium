package org.fluentlenium.utils;

import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.http.HttpMethod;

public enum Commands {
    /**
     * Send a command to the DevTools API.
     */
    SEND_COMMAND("SEND_COMMAND",
            new CommandInfo("/session/:sessionId/chromium/send_command", HttpMethod.POST)),
    /**
     * Send a command to the DevTools API and wait for the response.
     */
    SEND_COMMAND_AND_GET_RESULT("SEND_COMMAND_AND_GET_RESULT",
            new CommandInfo("/session/:sessionId/chromium/send_command_and_get_result", HttpMethod.POST));

    private String cmdName;
    private CommandInfo cmdInfo;

    Commands(String command, CommandInfo commandInfo) {
        this.cmdName = command;
        this.cmdInfo = commandInfo;
    }

    public String getCmdName() {
        return cmdName;
    }

    public CommandInfo getCmdInfo() {
        return cmdInfo;
    }
}
