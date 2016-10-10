# What is FluentLenium ?

[![Travis](https://img.shields.io/travis/FluentLenium/FluentLenium.svg)](https://travis-ci.org/FluentLenium/FluentLenium)
[![Coveralls](https://img.shields.io/coveralls/FluentLenium/FluentLenium.svg)](https://coveralls.io/github/FluentLenium/FluentLenium)
[![Code Climate](https://img.shields.io/codeclimate/github/FluentLenium/FluentLenium.svg)](https://codeclimate.com/github/FluentLenium/FluentLenium)
[![Code Climate Issues](https://img.shields.io/codeclimate/issues/github/FluentLenium/FluentLenium.svg)](https://codeclimate.com/github/FluentLenium/FluentLenium/issues)
[![Maven Central](https://img.shields.io/maven-central/v/org.fluentlenium/fluentlenium-parent.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.fluentlenium%22%20AND%20a%3A%22fluentlenium-parent%22)
[![Website](https://img.shields.io/website-up-down-green-red/http/fluentlenium.org.svg)](http://fluentlenium.org)

FluentLenium helps you writing readable, reusable, reliable, and resilient UI functionnal tests for the browser.

FluentLenium provides a Java [fluent interface](http://en.wikipedia.org/wiki/Fluent_interface) to 
[Selenium](http://www.seleniumhq.org/), and brings some magic to avoid common issues faced by Selenium users.

FluentLenium is shipped with adapters for [JUnit](junit.org/), [TestNG](http://testng.org/doc/index.html) and 
[Cucumber](https://cucumber.io), but it can also be used standalone.

FluentLenium best integrates with [AssertJ](http://joel-costigliola.github.io/assertj/), but you can also choose to use 
the assertion framework you want.

# Choose the right version

FluentLenium 1.x is in maintenance state, and no new feature will be added anymore. It requires Selenium 2 and
Java 7, but can also be used with Java 8. Selenium 3 is not supported in this version though.

FluentLenium 3.x is still in development and includes latest enhancements and features, but selenium 3 and Java 8 are 
required to run it.

# Quickstart with JUnit and AssertJ

- Add dependencies to your `pom.xml`.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-junit</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit-driver</artifactId>
    <version>2.21</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>xml-apis</groupId>
    <artifactId>xml-apis</artifactId>
    <version>1.4.01</version>
    <scope>test</scope>
</dependency>
```

- Create a Fluent Test.

```java
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
public class DuckDuckGoTest extends FluentTest {
    @Test
    public void title_of_duck_duck_go_should_contain_search_query_name() {
        goTo("https://duckduckgo.com");
        $("#search_form_input_homepage").fill().with("FluentLenium");
        $("#search_button_homepage").submit();
        assertThat(windows().title()).contains("FluentLenium");
    }
}
```

- Run as a JUnit test.

[More FluentLenium examples are available on github](https://github.com/FluentLenium/FluentLenium/tree/master/fluentlenium-examples).

# Documentation

Full documentation is available on [fluentlenium.org](http://fluentlenium.org/docs), or in the 
[docs sources directory](https://github.com/FluentLenium/FluentLenium/tree/develop/docs).

## Contact Us
If you have any comment, remark or issue, please open an issue on 
[FluentLenium Issue Tracker](https://github.com/FluentLenium/FluentLenium/issues)
