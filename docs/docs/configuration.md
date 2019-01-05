---
layout: page
title: Configuration
subtitle: FluentLenium
sidebar:
  Properties description: "#configuration-properties"
  Config ways: "#configuration-ways"
  Config examples: "#configuration-examples"
  Accessing config in tests: "#accessing-webdriver-and-capabilities-in-tests"
---

FluentLenium can be configured in many ways through configuration properties.

## Table of Contents
- [Configuration properties](#configuration-properties)
- [Configuration Ways](#configuration-ways)
- [Configuration Examples](#configuration-examples)
  * [Headless Chrome](#headless-chrome)
  * [Custom Capabilities (BrowserStack example)](#custom-capabilities-browserstack-example)
  * [Custom Webdriver](#custom-webdriver)
- [Accessing Webdriver and Capabilities in tests](#accessing-webdriver-and-capabilities-in-tests)

## Configuration properties

  - **webDriver**

    Sets the WebDriver type to use.

    Default value: ```null```.

    Possible values are ```remote```, ```firefox```, ```chrome```, ```ie```, ```safari```,
    ```phantomjs```, ```htmlunit```, or any class name implementing ```WebDriver```.

    If not defined, FluentLenium will use the first value for which WebDriver is available in classpath.

  - **browserTimeout**

    Sets the timeout for webdriver when it should be responsive if not interrupts the obtain driver thread and tries
    maximum amoun of times specified by **browserTimeoutRetries** value

    Default value: ```60000```

  - **browserTimeoutRetries**

    Maximum number of retries to wait for WebDriver to be responsive.

    Default value: ```2```

  - **remoteUrl**

    Sets the remote URL for ```remote``` *webDriver*. This should be the URL to access Selenium-Grid server.

  - **capabilities**

     Sets the [Capabilities](https://github.com/SeleniumHQ/selenium/wiki/DesiredCapabilities) to use with the WebDriver.

     Can be:
       - a `Capabilities` JSON Object string.
       - an URL pointing to a `Capabilities` JSON Object string.
       - a static method available in Selenium `DesiredCapabilities` class, like `firefox` or `chrome`.
       - a class name implementing `Capabilities`.
       - a reference to a `CapabilitiesFactory`.

     Default value: ```null```.

  - **driverLifecycle**

    Sets the lifecycle of the WebDriver. WebDriver is fully managed by FluentLenium, so you should never
    create or quit a WebDriver by yourself.

    Possible values are:

      - `JVM`: WebDriver is created once, and same instance is used for each test class and method.
      - `CLASS`: WebDriver is created for each test class, and same instance is used for each test method in the class.
      - `METHOD`: WebDriver is created for each test method, and this instance is used only for one test method.
      - `THREAD`: WebDriver is created for each test thread. The instance is used only for one thread of test
      method. It was introduced to support annotation driven concurrent test execution mode (you can skip it if you
      are using surefire from maven), please take a look on example below:

      ```java
      @FluentConfiguration(driverLifecycle = ConfigurationProperties.DriverLifecycle.THREAD)
      public class DriverPerThreadTest extends FluentTestNg {
          private List<String> cookiesList = new ArrayList<>();

          @Override
          public WebDriver newWebDriver() {
              DesiredCapabilities caps = new DesiredCapabilities();
              WebDriver driver = new ChromeDriver(caps);
              return driver;
          }

          @Test(invocationCount = 2, threadPoolSize = 2)
          public void firstMethod() {
              goTo("http://google.com");
              await().until($(".gsfi")).present();
              cookiesList.add(getDriver().manage().getCookies().toString());
          }

          @AfterClass()
          public void checkCookies() {
              assertThat(cookiesList.stream().distinct().count()).isEqualTo(2);
          }
      }
      ```

    Please keep in mind that this configures when drivers are created and exited at runtime, but it does not deal with
    concurrency of your tests.

    Default value: ```METHOD```

  - **deleteCookies**

    When using CLASS or JVM *driverLifecycle* configuration property, allow to delete cookies between each
    test.

    Default value: ```false```.

  - **baseUrl**

     Sets the base URL used to build absolute URL when relative URL is given to {@link FluentAdapter#goTo(String)}.

     Default value: ```null```.

  - **pageLoadTimeout**

     Sets the amount of time in millisecond to wait for a page load to complete before throwing an error.
     If the timeout is negative, page loads can be indefinite.

     Default value: ```null```.

  - **implicitlyWait**

     Specifies the amount of time in millisecond the driver should wait when searching for an element if it is
     not immediately present.

     Default value: ```null```.


  - **scriptTimeout**

     Sets the amount of time in millisecond to wait for an asynchronous script to finish execution before
     throwing an error. If the timeout is negative, then the script will be allowed to run
     indefinitely.

     Default value: ```null```.

  - **eventsEnabled**

     Enables ```events()``` by wrapping the ```WebDriver``` in ```EventFiringWebDriver```.

     Default value: ```true```.

  - **screenshotPath**

     Sets the filesystem path where screenshot will be saved.

     Default value: ```null```.


  - **screenshotMode**

     Sets the trigger mode of screenshots. Can be ```AUTOMATIC_ON_FAIL``` to take screenshot when the test fail
     or ```MANUAL```.

     Default value: ```null```.


  - **htmlDumpPath**

     Sets the filesystem path where screenshot will be saved.

     Default value: ```null```.


  - **htmlDumpMode**

     Sets the trigger mode of html dump. Can be ```ON_AUTOMATIC_ON_FAIL``` to take html dump when the test fail
     or ```MANUAL```.

     Default value: ```null```.


  - **configurationDefaults**

    Set this to a class implementing ```ConfigurationProperties``` to provide the default values
    of the configuration properties.

    Default value: ```ConfigurationDefaults```.


  - **configurationFactory**

     Set this to a class implementing ```ConfigurationFactory``` to customize the ways properties are read.
     This allow to configure properties from sources that are not supported by default FluentLenium.

     Default value: ```DefaultConfigurationFactory```.


 Keep in mind that when those properties are defined through System Properties or Environment Variables, they need to
 be prefixed with ```fluentlenium.``` (ie. ```fluentlenium.webDriver=chrome```).

## Configuration Ways

It's possible to define those properties using:

  - **Overrides** of JavaBean **property getters** of the test class.

        public class SomeFluentTest extends FluentTest {
            @Override
            public String getWebDriver() {
                return "chrome";
            }
        }

  - **Calls** of JavaBean **property setters** of the test class.

        public class SomeFluentTest extends FluentTest {
            public SomeFluentTest() {
                setWebDriver("chrome");
            }
        }

  - **System properties** of the Java Environment, passed using ```-D``` on the command line. Property names must be **prefixed with fluentlenium.**.

        mvn clean test -Dfluentlenium.webDriver=chrome

  - **Environment Variable** of the Operating System. Property names **must be prefixed with fluentlenium.**.

        $ EXPORT fluentlenium.webDriver=chrome; mvn clean test;


  - **@FluentConfiguration Annotation** on test class to configure.

         @FluentConfiguration(webDriver="chrome")
         public class SomeFluentTest extends FluentTest {
             ....
         }


  - **Java Properties file** located at ```/fluentlenium.properties``` in the classpath.

        $ cat fluentlenium.properties
        webDriver=chrome
        ...

  - **ConfigurationProperties** custom implementation specified by ```configurationDefaults``` property.

        public class CustomConfigurationDefaults extends ConfigurationDefaults {
            @Override
            public String getWebDriver() {
                return "chrome";
            }
        }

        $ cat fluentlenium.properties
        configurationDefaults=org.your.package.CustomConfigurationDefaults

This list of way to configure fluentlenium is ordered by priority. If a value is defined for a property in an element,
lower ways will just be ignored.

You may implement additionnal ways to read configuration property by implementing another
```ConfigurationFactory``` and set the new configuration factory class name in the ```configurationFactory``` property.

## Configuration examples

### Headless Chrome
You can run your tests using Chrome [headless](https://developers.google.com/web/updates/2017/04/headless-chrome) feature. Just simply add ```{chromeOptions: {args:[headless, disable-gpu]}}``` to capabilities.

To run Chrome in the headless mode you can use following FluentLenium configuration ways:

  1. **Override** JavaBean **property getters** of the test class:
        ```java
        public class SomeFluentTest extends FluentTest {
             @Override
             public String getWebDriver(){
                 return "chrome";
             }

             @Override
             public Capabilities getCapabilities(){
                 ChromeOptions options = new ChromeOptions();
                 options.addArguments("--headless");
                 options.addArguments("--disable-gpu");
                 DesiredCapabilities capabilities = new DesiredCapabilities();
                 capabilities.setCapability("chromeOptions", options);
                 return capabilities;
             }
        }
        ```

  1. **Call** JavaBean **property setters** of the test class:
        ```java
        public class SomeFluentTest extends FluentTest {
             public SomeFluentTest() {
                 ChromeOptions options = new ChromeOptions();
                 options.addArguments("--headless");
                 options.addArguments("--disable-gpu");
                 DesiredCapabilities capabilities = new DesiredCapabilities();
                 capabilities.setCapability("chromeOptions", options);
                 setCapabilities(capabilities);
                 setWebDriver("chrome");
             }
        }
        ```

  1. Pass **system properties** using ```-D``` on the command line:
        ```
        mvn clean test -Dfluentlenium.webDriver=chrome -Dfluentlenium.capabilities=capabilities = "{chromeOptions: {args: [headless,disable-gpu]}}"
        ```

  1. Annotate the test class with **@FluentConfugration**:
        ```java
        @FluentConfiguration(webDriver="chrome", capabilities = "{\"chromeOptions\": {\"args\": [\"headless\",\"disable-gpu\"]}}")
        public class SomeFluentTest extends FluentTest {
             ....
        }
        ```
  1. Create **Java Properties** file ```fluentlenium.properties``` in the project classpath.
        ```
        $ cat fluentlenium.properties
        webDriver=chrome
        capabilities = "{\"chromeOptions\": {\"args\": [\"headless\",\"disable-gpu\"]}}        ...
        ```
  1. Implement custom configuration properties by extending **ConfigurationDefaults**
        ```java
        public class CustomConfigurationDefaults extends ConfigurationDefaults {
             @Override
             public String getWebDriver() {
                 return "chrome";
             }

             @Override
             public Capabilities getCapabilities(){
                 ChromeOptions options = new ChromeOptions();
                 options.addArguments("--headless");
                 options.addArguments("--disable-gpu");
                 DesiredCapabilities capabilities = new DesiredCapabilities();
                 capabilities.setCapability("chromeOptions", options);
                 return capabilities;
             }
        }
        $ cat fluentlenium.properties
        configurationDefaults=org.your.package.CustomConfigurationDefaults
        ```

### Custom Capabilities (BrowserStack example)
You can register custom Capabilities by providing your own implementation of ```CapabilitiesFactory```.

A ```CapabilitiesFactory``` is responsible for creating new instances of ```Capabilities``` that will be
available through ```capabilities``` configuration property

This implementation will be discovered with [classindex](https://github.com/atteo/classindex), a
compile-time alternative to run-time classpath scanning. It requires your IDE to have Annotation Processing
enabled in the Java Compiler configuration.


For instance, to run your tests on [BrowserStack Automate](https://www.browserstack.com/automate), your first need to create a Capabilities
factory.

```java
@FactoryName("browserstack-os-x") // Name to use in capabilities configuration property
public class BrowserStackOsXCapabilitiesFactory implements CapabilitiesFactory {
    @Override
    public Capabilities newCapabilities(ConfigurationProperties configuration) {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("os", "OS X");
        caps.setCapability("os_version", "El Capitan");
        caps.setCapability("browser", "firefox");
        caps.setCapability("browser_version", "44");
        caps.setCapability("build", "Sample FluentLenium Tests");
        caps.setCapability("browserstack.debug", "true");

        return caps;
    }
}
```

And then, configure the following properties.

```
webDriver=remote
capabilities=browserstack-os-x
remoteUrl=http://USERNAME:AUTOMATE_KEY@hub-cloud.browserstack.com/wd/hub
```

### Custom WebDriver

You can register custom WebDriver by providing your own implementation of ```WebDriverFactory```.

A ```WebDriverFactory``` is responsible for creating new instances of ```WebDriver``` that will be available
through ```webDriver``` configuration property

This implementation will be discovered with [classindex](https://github.com/atteo/classindex), a
compile-time alternative to run-time classpath scanning. It requires your IDE to have Annotation Processing
enabled in the Java Compiler configuration.

```java
@FactoryName("custom") // Name to use in webDriver configuration property
public class CustomWebDriverFactory implements WebDriverFactory {
    @Override
    public WebDriver newWebDriver(Capabilities desiredCapabilities, ConfigurationProperties configuration) {
        return new CustomWebDriver(desiredCapabilities);
    }
}
```

Instead of implementing a new ```WebDriverFactory``` class, you may also override ```newWebDriver()``` in the Test
class, but doing so will ignore any value defined in ```webDriver``` configuration property.

## Accessing Webdriver and Capabilities in tests

It's possible to retrieve the underlying WebDriver with `getDriver()` method. Effective Capabilities can be retrieved
with `capabilities()` method.