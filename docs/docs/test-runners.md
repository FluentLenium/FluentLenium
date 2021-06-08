---
layout: page
title: Test runners
subtitle: FluentLenium
sidebar:
  JUnit 4: "#junit-4"
  JUnit 5 Jupiter: "#junit-5-jupiter"
  TestNG: "#testng"
  Cucumber: "#cucumber"
  Spock: "#spock"
  Spring TestNG: "#spring-testng"
  FluentStandalone: "#standalone-mode"
---

## Supported Test Runners
- [Junit 4](#junit-4)
- [Junit 5 Jupiter](#junit-5-jupiter)
- [TestNG](#testng)
- [Cucumber](#cucumber)
- [Spock](#spock)
- [Spring TestNG](#spring-testng)

## Standalone mode (no 3rd party test-runner)
- [Standalone mode](#standalone-mode)

## JUnit 4

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-junit</artifactId>
    <version>4.6.2</version>
    <scope>test</scope>
</dependency>
```

- Extends FluentTest.

Fluent Test is the entry point of FluentLenium. You only have to extend ```FluentTest``` and implement a test as usual.

```java
public class MyTest extends FluentTest {
    @Test
    public void testGoogle() {
        goTo("http://www.google.com");
    }
}
```

If you need to make your tests run concurrently to speed them up, you should use dedicated libraries/extensions.
You can use the Surefire maven plugin for example.

**Surefire JUnit example**

```xml
<profile>
    <id>junit-tests</id>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <parallel>methods</parallel>
                    <threadCount>4</threadCount>
                    <forkCount>8</forkCount>
                    <reuseForks>true</reuseForks>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```

More configuration examples can be found in our [Integration tests](https://github.com/FluentLenium/FluentLenium/tree/develop/fluentlenium-junit/src/it/junit).
Enable them by activating ```framework-integration-tests``` Maven profile.

## JUnit 5 Jupiter

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-junit-jupiter</artifactId>
    <version>4.6.2</version>
    <scope>test</scope>
</dependency>
```

- Extends FluentTest.

If you need to run your tests in parallel please take a look into our [Integration tests examples](https://github.com/FluentLenium/FluentLenium/tree/develop/fluentlenium-junit-jupiter/src/it/junit-jupiter).
Enable them by activating ```framework-integration-tests``` Maven profile.

## TestNG

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-testng</artifactId>
    <version>4.6.2</version>
    <scope>test</scope>
</dependency>
```

- Extends FluentTestNG instead of FluentTest.

If you need to make your tests run concurrently to speed them up, you should use dedicated libraries/extensions.
You can use the Surefire maven plugin for example.

**Surefire TestNG example**

```xml
<profile>
    <id>testng-tests</id>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>test-suites/basic.xml</suiteXmlFile>
                    </suiteXmlFiles>
                    <goal>test</goal>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```

**TestNG test suite file**

```xml
<suite name="Parallel tests" parallel="tests" thread-count="10">
    <test name="Home Page Tests" parallel="methods" thread-count="10">
        <parameter name="test-name" value="Home page"/>
        <classes>
            <class name="com.example.testng.OurLocationTest"/>
            <class name="com.example.testng.OurStoryTest"/>
        </classes>
    </test>
    <test name="Another Home Page Tests" parallel="classes" thread-count="2">
        <parameter name="test-name" value="another home page"/>
        <classes>
            <class name="com.example.testng.HomeTest"/>
            <class name="com.example.testng.JoinUsTest"/>
        </classes>
    </test>
</suite>
```

TestNG gives you more flexibility in order to the concurrency level, test suites and having better control on executed
 scenarios.

Both test frameworks are giving possibility to define the parallelism level of tests.

It is possible when you have multiple execution/concurrency levels set in your tests to face driver sharing issues,
so please use ```METHOD``` ```driverLifecycle``` configuration property when your execution methods are mixed up.

More configuration examples can be found in our [Integration tests](https://github.com/FluentLenium/FluentLenium/tree/develop/fluentlenium-testng/src/it/testng).
Enable them by activating ```framework-integration-tests``` Maven profile.

## Cucumber

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-cucumber</artifactId>
    <version>4.4.1</version>
</dependency>
```

- Create runner class annotated with Cucumber.class

```java
@RunWith(Cucumber.class)
@CucumberOptions
public class Runner {
}
```

- Extend `FluentCucumberTest` class in all of your steps classes:

```java
public class ExampleSteps1 extends FluentCucumberTest {
}
```
```java
public class ExampleSteps2 extends FluentCucumberTest {
}
```
or only in your `BaseTest` class:

```java
public class BaseTest extends FluentCucumberTest {
}
```
```java
public class ExampleSteps1 extends BaseTest {
}
```

- Add `Before` and `After` hooks: 

```java
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class FluentHooks extends FluentCucumberTest {
    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
```

When using Cucumber with Java 8 (package `cucumber-java8`), hooks can be added like this:

```java
public class FluentHooks extends FluentCucumberTest {
    
    public FluentHooks(){
        Before(this::before);
        
        After(this::after);
    }
}
```

**Notes related to Cucumber hooks:**

1) Hooks should be added **ONCE** for your tests. Do not add these hooks in all your steps classes.
 
2) Adding these hooks starts FluentLenium context during execution of scenario. It means that driver will start 
automatically. If you want to start WebDriver only for some scenarios, you can use tagged Cucumber hooks, for example:

```
@fluent
Scenario: Driver should start when tag is present above scenario
    Then WebDriver starts as expected
```
and add tags to your hooks:

```java
@Before({"@fluent"})
public void beforeScenario(Scenario scenario) {
    before(scenario);
}

@After({"@fluent"})
public void afterScenario(Scenario scenario) {
    after(scenario);
}
```
3) Hooks in `Cucumber` cannot be extended. So do not add `Before` and `After` hooks in `BaseTest` (if you use such
pattern) but add them in any other class:

```java
public class FluentHooks extends BaseTest {
    
    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }
    
    @After
     public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
```

- It is possible to access current `FluentCucumberTest` context using syntax:

```
FluentTestContainer.FLUENT_TEST.instance()
```

It can be handy if you want to have access to the current WebDriver instance. The purpose of `FluentTestContainer` is to share
state between many steps classes.

- Keep your configuration in `BaseTest`:

```java
// example configuration
@FluentConfiguration(webDriver = "chrome")
public class BaseTest extends FluentCucumberTest {

    @Override
    public Capabilities getCapabilities() {
        return new ChromeOptions();
    }
}
```

or class with Cucumber hooks:
 
```java
// example configuration
@FluentConfiguration(webDriver = "chrome")
public class FluentHooks extends FluentCucumberTest {
 
    @Override
    public Capabilities getCapabilities() {
        return new ChromeOptions();
    }
    
    @Before
    public void beforeScenario(Scenario scenario) {
        before(scenario);
    }
    
    @After
    public void afterScenario(Scenario scenario) {
        after(scenario);
    }
}
```
 
- By default, a new instance of WebDriver is created for each scenario. If you want to run single WebDriver for all 
scenarios in feature, change DriverLifecycle to JVM level:

```java
import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.configuration.ConfigurationProperties;
import org.fluentlenium.configuration.FluentConfiguration;

@FluentConfiguration(webDriver = "chrome", driverLifecycle = ConfigurationProperties.DriverLifecycle.JVM)
public class BaseTest extends FluentCucumberTest {
}
```

- Remember, that page objects still need to inherit `FluentPage` to run correctly.

E2E Cucumber tests are present in [Cucumber example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/cucumber).
Enable it by activating ```examples``` Maven profile.

- Until FLuentLenium 4.3.1, both `cucumber.api` and `io.cucumber` based classes (`@Given`, `@When`, `@CucumberOptions`, etc.) are supported,
however the `FluentCucumberTest` based tests still use the old Cucumber `ObjectFactory` due to the Cucumber limitation of allowing only one instance of
`ObjectFactory` to be used, and also to provide backward compatibility for projects where Cucumber version cannot be upgraded.
- From FluentLenium 4.4.1 on support for `cucumber.api` package based Before and After hooks is removed. Only `io.cucumber` packages are supported.

## Spock

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-spock</artifactId>
    <version>4.6.2</version>
    <scope>test</scope>
</dependency>
```

- Extends FluentSpecification instead of FluentTest.

E2E Spock tests are present in [Spock example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/spock).
Enable it by activating ```examples``` Maven profile.

## Spring TestNG

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-spring-testng</artifactId>
    <version>4.6.2</version>
    <scope>test</scope>
</dependency>
```

- Extends FluentTestNgSpringTest instead of FluentTest.

E2E Spring testNG tests are present in [Spring example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/spring).
Enable it by activating ```examples``` Maven profile.

## Standalone mode

If you want to use FluentLenium as a pure automation Framework, without any testing framework adapter,
you can create an instance of `FluentStandalone` and use it directly, or extend `FluentStandaloneRunnable` and
implement `doRun()` method.

### FluentStandalone

Create an instance of `FluentStandalone` and use it directly. You have to manually invoke `init()` to initialize the
WebDriver, and `quit()` to close it.

```java
FluentStandalone standalone = new FluentStandalone()
standalone.init();

standalone.goTo(DEFAULT_URL);
standalone.await().atMost(1, SECONDS).until(test.$(".fluent", with("name").equalTo("name"))).present();
standalone.el("input").enabled();

standalone.quit();
```

Using a `FluentStandalone` instance is quite verbose because of the need to repeat the instance name before each
instruction. If it's a problem for you, you should consider extending `FluentStandaloneRunnable`.

### FluentStandaloneRunnable

Another option is to extend `FluentStandaloneRunnable`, implement `doRun()` method and invoke `run()` method on an
instance of this class. Fluent WebDriver is initialized before and released after `run()` method invocation.

```java
public class MyStandaloneRunnable extends FluentStandaloneRunnable {
    @Override
    protected void doRun() {
        goTo(DEFAULT_URL);
        await().atMost(1, SECONDS).until(test.$(".fluent", with("name").equalTo("name"))).present();
        el("input").enabled();
    }
}
```

```java
MyStandaloneRunnable runnable = new MyStandaloneRunnable();
runnable.run();
```
