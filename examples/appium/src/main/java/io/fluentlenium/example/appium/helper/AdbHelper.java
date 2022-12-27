package org.fluentlenium.example.appium.helper;

import io.appium.java_client.AppiumDriver;

import java.util.List;
import java.util.Map;

public class AdbHelper {

    public static final String COMMAND = "command";
    public static final String ARGS = "args";

    public static void typeText(String textToType, AppiumDriver driver) {
        List<String> textInputArgs = List.of("text", textToType);
        Map<String, Object> adbTextInput = Map.of(
                COMMAND, "input",
                ARGS, textInputArgs);
        driver.executeScript("mobile: shell", adbTextInput);
    }

}
