package org.fluentlenium.utils.chromium;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.CommandInfo;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.fluentlenium.utils.chromium.Commands.SEND_COMMAND;
import static org.fluentlenium.utils.chromium.Commands.SEND_COMMAND_AND_GET_RESULT;

public class ChromiumApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChromiumApi.class);
    private final RemoteWebDriver remoteWebDriver;

    private static final List<String> SUPPORTED_BROWSERS = ImmutableList.of(
            "chrome", "msedge"
    );

    public ChromiumApi(RemoteWebDriver remoteWebDriver) {
        requireNonNull(remoteWebDriver, "WebDriver instance must not be null");
        String browserName = remoteWebDriver.getCapabilities().getBrowserName();
        if (!SUPPORTED_BROWSERS.contains(browserName)) {
            throw new ChromiumApiNotSupportedException("API supported only by Chrome and Edge");
        }
        this.remoteWebDriver = remoteWebDriver;
        defineCommandViaReflection();
    }

    public void sendCommand(String methodName, Map<String, ?> params) {
        Command command = createCommand(methodName, params, SEND_COMMAND.getCmdName());
        CommandExecutor cmdExecutor = remoteWebDriver.getCommandExecutor();
        executeCommand(cmdExecutor, command, methodName);
    }

    public Response sendCommandAndGetResponse(String methodName, Map<String, ?> params) {
        Command command = createCommand(methodName, params, SEND_COMMAND_AND_GET_RESULT.getCmdName());
        CommandExecutor cmdExecutor = remoteWebDriver.getCommandExecutor();
        return executeCommand(cmdExecutor, command, methodName);
    }

    private void defineCommandViaReflection() {
        Method defineCmd;
        try {
            defineCmd = HttpCommandExecutor.class.getDeclaredMethod("defineCommand", String.class, CommandInfo.class);
            defineCmd.setAccessible(true);
            defineCmd.invoke(remoteWebDriver.getCommandExecutor(), SEND_COMMAND_AND_GET_RESULT.getCmdName(),
                new CommandInfo(SEND_COMMAND_AND_GET_RESULT.getCmdInfo(), HttpMethod.POST));
            defineCmd.invoke(remoteWebDriver.getCommandExecutor(), SEND_COMMAND.getCmdName(),
                new CommandInfo(SEND_COMMAND.getCmdInfo(), HttpMethod.POST));
        } catch (Exception e) {
            LOGGER.error("Failed to define command via reflection");
        }
    }

    private Response executeCommand(CommandExecutor cmdExecutor, Command command, String methodName) {
        Response response;
        try {
            response = cmdExecutor.execute(command);
            LOGGER.info("Command \"{}\" executed with {} state", methodName, response.getState());
            return response;
        } catch (Exception e) {
            LOGGER.error("Failed to execute {} via Chrome API", command.getName());
            return null;
        }
    }

    private Command createCommand(String methodName, Map<String, ?> commandParams, String endpointName) {
        SessionId sessionId = remoteWebDriver.getSessionId();
        Map<String, ?> param = ImmutableMap.of("cmd", methodName, "params", commandParams);
        return new Command(sessionId, endpointName, param);
    }
}
