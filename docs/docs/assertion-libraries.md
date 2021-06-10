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
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>4.5.1</version>
    <scope>test</scope>
</dependency>
```

- Add those static imports.

```java
import static org.assertj.core.api.Assertions.*;
import static org.fluentlenium.assertj.FluentLeniumAssertions.*;
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
assertEqual("Hello toto", window().title());
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
assertThat(window().title(), equalTo("Hello toto"));
```

## Kotest

There is a set of custom Kotest Matchers available to make assertions on
* Page
* Alert
* FluentWebElement
* FluentList

- Import this additional maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-kotest-assertions</artifactId>
    <version>4.6.3</version>
    <scope>test</scope>
</dependency>
```

```groovy
testImplementation 'org.fluentlenium:fluentlenium-kotest-assertions:4.6.4'
```

```kotlin
testImplementation("org.fluentlenium:fluentlenium-kotest-assertions:4.6.4")
```

- Add those static imports.

```kotlin
import org.fluentlenium.kotest.matchers.el.*
import org.fluentlenium.kotest.matchers.jq.*
import org.fluentlenium.kotest.matchers.alert.*
import org.fluentlenium.kotest.matchers.page.*
```

check the [example](https://github.com/FluentLenium/FluentLenium/blob/develop/examples/kotest/src/test/kotlin/org/fluentlenium/example/kotest/DuckDuckGoSpec.kt) and the [tests](../../fluentlenium-kotest-assertions) for usage examples.
