import org.fluentlenium.configuration.ConfigurationException;
import org.fluentlenium.configuration.WebDriverFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class AugmenterRemoteWebDriverFactory implements WebDriverFactory {
    @Override
    public WebDriver newWebDriver(Capabilities desiredCapabilities) {
        try {
            return new Augmenter().augment(new RemoteWebDriver(new URL((String) desiredCapabilities.getCapability("augmenter.hublocation")), desiredCapabilities));
        } catch (MalformedURLException e) {
            throw new ConfigurationException("Can't configure augmenter driver", e);
        }
    }

    @Override
    public String getName() {
        return "augmenter";
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
