---
layout: page
title: Appium support
subtitle: FluentLenium
sidebar:
  Overview: "#overview"
  Web support: "#web-support"
  Native support: "#native-support"
---

## Appium support
- [Overview](#overview)
- [Web support](#web-support)
- [Native support](#native-support)

## Overview

FluentLenium syntax can be used for mobile automation together with Appium.
Just add additional dependency into your pom.xml.

```xml
 <dependency>
       <groupId>io.appium</groupId>
       <artifactId>java-client</artifactId>
       <version>7.0.0</version>
 </dependency>
```

Environment setup is not related to FluentLenium but we recommend the following tutorials:

- [Appium Install](https://www.swtestacademy.com/appium-tutorial/)
- [iOS Guide](https://medium.com/2359media/tutorial-automated-testing-on-ios-with-appium-test-ng-and-java-on-mac-bc115d0ec881)
- [Android Guide](https://medium.com/2359media/tutorial-automated-testing-on-android-and-ios-with-appium-testng-and-java-on-mac-210119edf323)

And sample apps:

- [iOS](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/iOS)
- [Android](https://github.com/King-of-Spades/AppCenter-Samples/tree/master/Appium/Android)

## Web support

If your only goal is to run web tests using Google Chrome (Android) or Safari (iOS) you don't even have to modify your tests.

For remote execution just connect devices to Selenium Grid. 

For local execution run Appium server and provide path to chromedriver (it's needed for Android tests).

`appium --address 127.0.0.1 --port 4723 --chromedriver-executable /home/fluentlenium/drivers/chromedriver`

You need to provide correct Capabilities. iPhone simulator example:

```java
@Override
    public Capabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
        return capabilities;
    }
```

Examples can be found [here](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/spring)

## Native support

FluentLenium fully supports native iOS, Android, Mac and Windows automation
 including the most popular drivers (XCUITest, Espresso, UIAutomator2, Windows).

You can provide custom MobileBy selectors and FluentLenium would inject them in the same way as web selectors. Examples:

```java
    @iOSXCUITFindBy(accessibility = "iosAccessibilityId")
    private FluentWebElement iosAccessibilityId;

    @AndroidFindBy(uiAutomator = "androidUiAutomator")
    private FluentWebElement androidUiAutomator;

    @WindowsFindBy(windowsAutomation = "windows")
    private FluentWebElement windowsAutomation;
```

This element would be identified by tag in iOS profile and be accessibilityId in Android profile.

```java
    @AndroidFindBy(accessibility = "android")
    @iOSXCUITFindBy(tagName = "ios")
    private FluentWebElement multiPlatformElement;
```

You can also find elements without injection (just like in Web FluentLenium):

```java
public SwiftNoteHomePage search(String searchPhrase) {
        el(MobileBy.AccessibilityId("Search")).click();
        FluentWebElement searchInput = el(MobileBy.id("com.moonpi.swiftnotes:id/search_src_text"));
        await().until(searchInput).displayed();
        searchInput.fill().with(searchPhrase);
        return this;
    }
```

Please have in mind that mobile automation work well only if correct Capabilities are configured.
We throw the following exception if something is missing:

```java
private void checkCapabilities(String platform, String automation) {
        boolean correctConfiguration = isAndroid(platform) || isIos(platform, automation) || isWindows(platform);
        if (!correctConfiguration) {
            throw new ConfigurationException("You have annotated elements with Appium @FindBys" +
                    " but capabilities are incomplete. Please use one of these configurations:\n" +
                    "platformName:Windows\n" +
                    "plaformName:Android\n" +
                    "plaformName:iOS, automationName:XCUITest");
        }
    }
```

Examples can be found [here](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/appium)
