# What is FluentLenium ?

![FluentLenium CI pipeline](https://github.com/FluentLenium/FluentLenium/workflows/FluentLenium%20CI%20pipeline/badge.svg)
[![Coveralls](https://coveralls.io/repos/github/FluentLenium/FluentLenium/badge.svg?branch=develop)](https://coveralls.io/github/FluentLenium/FluentLenium?branch=develop)
[![javadoc](https://javadoc.io/badge2/org.fluentlenium/fluentlenium-core/5.0.4/javadoc.svg)](https://fluentlenium.com/javadoc)
[![Maven Central](https://img.shields.io/maven-central/v/org.fluentlenium/fluentlenium-parent.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.fluentlenium%22%20AND%20a%3A%22fluentlenium-parent%22)
[![Website](https://img.shields.io/website-up-down-green-red/http/fluentlenium.com.svg)](https://fluentlenium.com)

FluentLenium helps you writing readable, reusable, reliable and resilient UI functional tests for the browser and mobile
app.

FluentLenium provides a Java [fluent interface](http://en.wikipedia.org/wiki/Fluent_interface) to
[Selenium](http://www.seleniumhq.org/), and brings some [extra features](https://fluentlenium.com/docs/key_features/)
to avoid common issues faced by Selenium users.

FluentLenium is shipped with adapters for [JUnit4](https://junit.org/junit4/), [JUnit5](https://junit.org/junit5/)
, [TestNG](http://testng.org/doc/index.html), [Spock](http://spockframework.org/)
, [Kotest](https://kotest.io/)
, [Spring TestNG](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/testng/AbstractTestNGSpringContextTests.html)
and [Cucumber](https://cucumber.io), but it can also be
used [standalone](https://fluentlenium.com/docs/test-runners/#standalone-mode).

FluentLenium best integrates with [AssertJ](http://joel-costigliola.github.io/assertj/), but you can also choose to use
the [assertion framework](https://fluentlenium.com/docs/assertion-libraries/) you want.

FluentLenium can be used to make your [mobile Appium tests](https://fluentlenium.com/docs/appium-support/) fluent and
easier to maintain.

FluentLenium gives you [multiple methods](https://fluentlenium.com/docs/test-methods/) which help you write tests
quicker. All those methods are tested daily by commercial regression test suites maintained by project developers.

# Quickstart

Quickstart steps are described in deail in our [separate documentation section](https://fluentlenium.com/quickstart/).

## Example

```java
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

More detailed FluentLenium examples are available
in [examples section](https://github.com/FluentLenium/FluentLenium/tree/develop/examples). Examples include `headless`
Chrome and Firefox, [Spring](https://spring.io/)-based framework supporting multiple browsers
and [much more](https://fluentlenium.com/quickstart/#more-examples).

## Documentation

Detailed documentation is available on [fluentlenium.com](https://fluentlenium.com).

## Javadoc

Javadoc is available on [fluentlenium.com/javadoc](https://fluentlenium.com/javadoc).

# Modules summary

To help you navigate through FluentLenium, here's a short summary about its modules and what support they provide.

- **fluentlenium-core**: Contains core functionality of FluentLenium, including webdriver configuration, page object
  support and injection logic.
- **fluentlenium-junit**: Provides support for integration with [JUnit 4](https://junit.org/junit4/).
- **fluentlenium-junit-jupiter**: Provides support for integration with [JUnit 5](https://junit.org/junit5/).
- **fluentlenium-testng**: Provides support for integration with [TestNG](https://testng.org/doc/index.html).
- **fluentlenium-spock**: Provides support for integration with [Spock](http://spockframework.org).
- **fluentlenium-spring-testng**: Provides support for integration
  with [Spring Test NG](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/testng/AbstractTestNGSpringContextTests.html)
- **fluentlenium-kotest**: Provides support for integration with [Kotest](https://kotest.io)
- **fluentlenium-kotest-assertions**: Provides custom [Kotest matchers](https://kotest.io/docs/assertions/matchers.html)
- **fluentlenium-cucumber**: Provides support for integration with [Cucumber](https://cucumber.io). This may be combined
  with any of the modules above that are also supported by Cucumber.
- **fluentlenium-assertj**: Provides [AssertJ](http://joel-costigliola.github.io/assertj/) assertions for FluentLenium
  specific objects like `FluentWebElement`, `FluentList` and `FluentPage`.
- **fluentlenium-integration-tests**: Integration tests for validating the correctness of FluentLenium features
  internally.
- **fluentlenium-coverage-report**: Creates jacoco test coverage report.
- **fluentlenium-ide-support**: Though not an actual Maven module, it contains resources to make working with
  FluentLenium in the IDE easier.

# Contact Us

If you have any comment, remark or issue, please open an issue on
[FluentLenium Issue Tracker](https://github.com/FluentLenium/FluentLenium/issues)
