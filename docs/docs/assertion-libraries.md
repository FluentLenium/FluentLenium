---
layout: page
title: Assertions
subtitle: FluentLenium
sidebar:
  AssertJ: "#assertj"
  Junit: "#junit"
  Hamcrest: "#hamcrest"
---

## Supported Assertions Libraries
- [AssertJ (recommended)](#assertj)
- [Junit](#junit)
- [Hamcrest](#hamcrest)

## AssertJ

We recommend to use AssertJ because we extend it with FluentWebElement and FluentList custom assertions.

- Import this additional maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>4.2.2</version>
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

