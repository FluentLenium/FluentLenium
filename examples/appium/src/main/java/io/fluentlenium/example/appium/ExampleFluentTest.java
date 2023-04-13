package io.fluentlenium.example.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.fluentlenium.adapter.junit.FluentTest;
import io.fluentlenium.example.appium.config.Config;
import io.fluentlenium.example.appium.device.Device;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = Config.class)
public class ExampleFluentTest extends FluentTest {

    protected AppiumDriver appiumDriver;
    protected static AppiumDriverLocalService service;

    @Autowired
    private Device device;

    @Override
    public WebDriver newWebDriver() {
        appiumDriver = new AppiumDriver(service, getCapabilities());
        return appiumDriver;
    }

    @BeforeClass
    public static void startServer() {
        DesiredCapabilities cap = new DesiredCapabilities();
        // cap.setCapability("noReset", "false");
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withCapabilities(cap)
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info");

        builder = builder.withAppiumJS(new File("node_modules/.bin/appium"));

        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    @AfterClass
    public static void stopServer() {
        service.stop();
    }

    @Override
    public Capabilities getCapabilities() {
        return getDevice().getCapabilities();
    }

    private Device getDevice() {
        return device;
    }

}
