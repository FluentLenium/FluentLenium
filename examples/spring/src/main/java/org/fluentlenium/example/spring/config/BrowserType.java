package org.fluentlenium.example.spring.config;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public enum BrowserType {

    @SuppressWarnings("unused")
    CHROME() {
        @Override
        public WebDriver getWebDriver() {
            return new ChromeDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return new ChromeOptions();
        }

        @Override
        protected String getDriverExecutableName() {
            return "chromedriver";
        }

        @Override
        public String getDriverSystemPropertyName() {
            return "webdriver.chrome.driver";
        }
    },
    SAFARI() {
        @Override
        public WebDriver getWebDriver() {
            return new SafariDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return new SafariOptions();
        }

        @Override
        protected String getDriverExecutableName() {
            return "safaridriver";
        }

        @Override
        public String getDriverSystemPropertyName() {
            return "webdriver.safari.driver";
        }
    },
    FIREFOX() {
        @Override
        public WebDriver getWebDriver() {
            return new FirefoxDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return new FirefoxOptions();
        }

        @Override
        protected String getDriverExecutableName() {
            return "geckodriver";
        }

        @Override
        public String getDriverSystemPropertyName() {
            return "webdriver.gecko.driver";
        }
    },
    IE() {
        @Override
        public WebDriver getWebDriver() {
            return new InternetExplorerDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return new InternetExplorerOptions();
        }

        @Override
        protected String getDriverExecutableName() {
            return "IEDriverServer";
        }

        @Override
        public String getDriverSystemPropertyName() {
            return "webdriver.ie.driver";
        }
    },
    EDGE() {
        @Override
        public WebDriver getWebDriver() {
            return new EdgeDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return new EdgeOptions();
        }

        @Override
        protected String getDriverExecutableName() {
            return "MicrosoftWebDriver";
        }

        @Override
        public String getDriverSystemPropertyName() {
            return "webdriver.edge.driver";
        }
    },
    OPERA() {
        @Override
        public WebDriver getWebDriver() {
            return new OperaDriver();
        }

        @Override
        protected MutableCapabilities getBrowserCapabilities() {
            return new OperaOptions();
        }

        @Override
        protected String getDriverExecutableName() {
            return "operadriver";
        }

        @Override
        public String getDriverSystemPropertyName() {
            return "webdriver.opera.driver";
        }
    };

    protected abstract WebDriver getWebDriver();

    protected abstract MutableCapabilities getBrowserCapabilities();

    protected abstract String getDriverExecutableName();

    public abstract String getDriverSystemPropertyName();

    public WebDriver getRemoteWebDriver(String hubLocation) {
        try {
            return new Augmenter().augment(new RemoteWebDriver(new URL(hubLocation), getBrowserCapabilities()));
        } catch (MalformedURLException e) {
            throw new ConfigException("Invalid hub location: " + hubLocation, e);
        }
    }

}
