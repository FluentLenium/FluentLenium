---
layout: page
title: Assertions
subtitle: FluentLenium
sidebar:
AssertJ: "#assertj"
Junit: "#junit"
Hamcrest: "#hamcrest"
Kotest: "#kotest"
---

## Supported Assertions Libraries

- [AssertJ (recommended)](#assertj)
- [Junit](#junit)
- [Hamcrest](#hamcrest)
- [Kotest](#kotest)

## AssertJ

We recommend to use AssertJ because we extend it with FluentWebElement and FluentList custom assertions.

- Import this additional maven dependency.

```xml

<dependency>
    <groupId>io.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>5.0.1</version>
    <scope>test</scope>
</dependency>
```

- Add those static imports.

```java
import static org.assertj.core.api.Assertions.*;
import static io.fluentlenium.assertj.FluentLeniumAssertions.*;
```

```java
goTo("http://mywebpage/");
        $("#firstName").fill().with("toto");
        $("#create-button").click();
        assertThat(window().title()).isEqualTo("Hello toto");
        assertThat($(".fluent")).hasText("present text");
        assertThat($(".fluent")).hasNotText("not present text");
        assertThat($(".another")).hasSize(7);
        assertThat($(".another2")).hasSize().lessThan(5);
        assertThat($(".another2")).hasSize().lessThanOrEqualTo(5);
        assertThat($(".another3")).hasSize().greaterThan(2);
        assertThat($(".another3")).hasSize().greaterThanOrEqualTo(2);
```

## JUnit

You can use FluentLenium using [JUnit](http://www.junit.org) assertions.

We assume you would like to use Junit assertions together with Junit test runner so no additional imports is required.

```java
goTo("http://mywebpage/");
        $("#firstName").fill().with("toto");
        $("#create-button").click();
        assertEqual("Hello toto",window().title());
```

## Hamcrest

- Import this additional maven dependency.

```xml

<dependency>
    <groupId>org.hamcrest</groupId>
    <artifactId>hamcrest-core</artifactId>
    <version>1.3</version>
    <scope>test</scope>
</dependency>
```

- Add those static imports.

```java
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
```

```java
goTo("http://mywebpage/");
        $("#firstName").fill().with("toto");
        $("#create-button").click();
        assertThat(window().title(),equalTo("Hello toto"));
```

## Kotest

There is a set of custom [Kotest Matchers](https://kotest.io/docs/assertions/assertions.html) available to make
assertions on

* Page
* Alert
* FluentWebElement
* FluentList

- Import this additional maven dependency.

Check [here](https://search.maven.org/artifact/io.fluentlenium/fluentlenium-kotest-assertions) for the latest available
version

```xml

<dependency>
    <groupId>io.fluentlenium</groupId>
    <artifactId>fluentlenium-kotest-assertions</artifactId>
    <version>$VERSION</version>
    <scope>test</scope>
</dependency>
```

```groovy
testImplementation 'io.fluentlenium:fluentlenium-kotest-assertions:$VERSION'
```

```kotlin
testImplementation("io.fluentlenium:fluentlenium-kotest-assertions:$VERSION")
```

- Add those static imports.

```kotlin
import io.fluentlenium.kotest.matchers.el.*
import io.fluentlenium.kotest.matchers.jq.*
import io.fluentlenium.kotest.matchers.alert.*
import io.fluentlenium.kotest.matchers.page.*
```

```kotlin
jq("h1") should haveTagName("h1")
jq("#choice option") should haveText("First Value")
jq("button") should haveClass("class1")
```

check
the [example](https://github.com/FluentLenium/FluentLenium/blob/develop/examples/kotest/src/test/kotlin/org/fluentlenium/example/kotest/DuckDuckGoSpec.kt)
and
the [tests](https://github.com/FluentLenium/FluentLenium/tree/develop/fluentlenium-kotest-assertions/src/test/kotlin/org/fluentlenium/kotest/matchers)
for usage examples.
