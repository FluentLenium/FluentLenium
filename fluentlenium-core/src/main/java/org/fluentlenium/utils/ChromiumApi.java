package org.fluentlenium.utils;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.fluentlenium.utils.Commands.SEND_COMMAND;
import static org.fluentlenium.utils.Commands.SEND_COMMAND_AND_GET_RESULT;

public class ChromiumApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChromiumApi.class);
    private final RemoteWebDriver remoteWebDriver;

    public ChromiumApi(RemoteWebDriver remoteWebDriver) {
        requireNonNull(remoteWebDriver, "WebDriver instance must not be null");
        if (!(remoteWebDriver instanceof ChromeDriver)) {
            LOGGER.warn("API currently supports only Chrome browser");
        }
        this.remoteWebDriver = remoteWebDriver;
        defineCommandViaReflection();
    }

    public void sendCommand(String commandName, Map<String, ?> params) {
        Command command = createCommand(commandName, params, SEND_COMMAND.getCmdName());
        CommandExecutor cmdExecutor = remoteWebDriver.getCommandExecutor();
        executeCommand(cmdExecutor, command, commandName);
    }

    public Response sendCommandAndGetResponse(String commandName, Map<String, ?> params) {
        Command command = createCommand(commandName, params, SEND_COMMAND_AND_GET_RESULT.getCmdName());
        CommandExecutor cmdExecutor = remoteWebDriver.getCommandExecutor();
        return executeCommand(cmdExecutor, command, commandName);
    }

    private void defineCommandViaReflection() {
        Method defineCmd;
        try {
            defineCmd = HttpCommandExecutor.class.getDeclaredMethod("defineCommand", String.class, CommandInfo.class);
            defineCmd.setAccessible(true);
            defineCmd.invoke(remoteWebDriver.getCommandExecutor(),
                    SEND_COMMAND_AND_GET_RESULT.getCmdName(), SEND_COMMAND_AND_GET_RESULT.getCmdInfo());
            defineCmd.invoke(remoteWebDriver.getCommandExecutor(), SEND_COMMAND.getCmdName(), SEND_COMMAND.getCmdInfo());
        } catch (Exception e) {
            LOGGER.error("Failed to define command via reflection");
        }
    }

    private Response executeCommand(CommandExecutor cmdExecutor, Command command, String commandName) {
        Response response;
        try {
            response = cmdExecutor.execute(command);
            LOGGER.info("Command {} executed with {} state", commandName, response.getState());
            return response;
        } catch (Exception e) {
            LOGGER.error("Failed to execute {} via Chrome API", command.getName());
            return null;
        }
    }

    private Command createCommand(String commandName, Map<String, ?> commandParams, String endpointName) {
        SessionId sessionId = remoteWebDriver.getSessionId();
        Map<String, ?> param = ImmutableMap.of("cmd", commandName, "params", commandParams);
        return new Command(sessionId, endpointName, param);
    }
}
