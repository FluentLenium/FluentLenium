---
layout: page
title: Quick Start
sidebar:
  Choosing version: "#choose-the-right-version"
  pom.xml config: "#maven-pom-configuration"
  Basic example: "#basic-fluentlenium-test"
  POP example: "#page-object-pattern-example"
  More examples: "#more-examples"
---

## Table of Contents
- [Choosing FluentLenium version](#choose-the-right-version)
- [Maven pom configuration](#maven-pom-configuration)
- [Basic FluentLenium test](#basic-fluentlenium-test)
- [Page Object Pattern example](#page-object-pattern-example)
- [More examples](#more-examples)

## Choose the right version

FluentLenium 4.x is the newest version of FluentLenium it is based on JDK 11 it includes latest enhancements and features, but Selenium 3 is required to run it.

FluentLenium 3.x is based on JDK 1.8 - we are not going to add new features to this version but still planning work on bugfixes.

FluentLenium 1.x is in maintenance state, and no new feature will be added anymore. It requires Selenium 2 and
Java 7, but can also be used with Java 8. Selenium 3 is not supported in this version though.

Starting from FluentLenium 3.1.0 you can use all sparks of Java 8, including lambdas. It is a nice extension in
comparison to Selenium 3 which is still basing on Guava objects. Please take a look on documentation to find `await`
lambda usage example.

If you want to keep up to date please choose FluentLenium 4.x


## Maven pom configuration

Assuming you want to use Java 11, Junit and AssertJ please add the following dependencies into your `pom.xml` file.

```xml
<properties>
    <fluentlenium.version>4.8.1</fluentlenium.version>
    <selenium.version>4.0.0</selenium.version>
</properties>

<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-junit</artifactId>
    <version>4.8.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>4.8.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit-driver</artifactId>
    <version>2.54.0</version>
    <scope>test</scope>
</dependency>
```

## Basic FluentLenium test

To see FluentLenium in action run this test as Junit.

```java
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
public class DuckDuckGoTest extends FluentTest {
    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        goTo("https://duckduckgo.com");
        $("#search_form_input_homepage").fill().with("FluentLenium");
        $("#search_button_homepage").submit();
        assertThat(window().title()).contains("FluentLenium");
    }
}
```

## Page Object Pattern example

In order to take advantage of FluentLenium magic please use Page Object Pattern. Here is more advanced example of the same test as above.

Test:

```java
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;

public class DuckDuckGoTest extends FluentTest {
    @Page
    DuckDuckMainPage duckDuckMainPage;

    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        goTo(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);
    }
}
```

Page Object:

```java
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://duckduckgo.com")
public class DuckDuckMainPage extends FluentPage {
    private static final String SEARCH_FORM_HOMEPAGE = "#search_form_homepage";

    @FindBy(css = "#search_form_input_homepage")
    private FluentWebElement searchInput;

    @FindBy(css = "#search_button_homepage")
    private FluentWebElement searchButton;

    public DuckDuckMainPage typeSearchPhraseIn(String searchPhrase) {
        searchInput.write(searchPhrase);
        return this;
    }

    public DuckDuckMainPage submitSearchForm() {
        searchButton.submit();
        awaitUntilSearchFormDisappear();
        return this;
    }

    public void assertIsPhrasePresentInTheResults(String searchPhrase) {
        assertThat(window().title()).contains(searchPhrase);
    }

    private DuckDuckMainPage awaitUntilSearchFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present();
        return this;
    }
}
```

## More examples

[More FluentLenium examples are available on github](https://github.com/FluentLenium/FluentLenium/tree/develop/examples).
Enable them by activating ```examples``` Maven profile.

Assuming you want to use only one browser please choose:

- **Chrome**: [quickstart-chrome](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/quickstart-chrome) - include `headless` example
- **Safari**: [quickstart-safari](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/quickstart-safari)
- **Firefox**: [quickstart-firefox](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/quickstart-firefox) - include `headless` example
- **IE**: [quickstart-microsoft-browsers](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/quickstart-microsoft-browsers)
- **Edge**: [quickstart-microsoft-browsers](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/quickstart-microsoft-browsers)

If you are more experienced user please choose [Spring based example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/spring)
which has ready to use framework supporting every browser.

If you want to use BDD please take a look into [Cucumber example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/cucumber)

If you want to write in Groovy please take a look into [Spock example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/spock)

[Hook example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/hooks) show this feature in action.