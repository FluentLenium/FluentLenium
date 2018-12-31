---
layout: page
title: Test runners
subtitle: FluentLenium
---

## Supported Test Runners
- [Junit 4](#junit-4)
- [Junit 5 Jupiter](#junit-5-jupiter)
- [TestNG](#testng)
- [Cucumber](#cucumber)
- [Spock](#spock)

## Standalone mode (no 3rd party test-runner)
- [Standalone mode](#standalone-mode)

## JUnit 4

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-junit</artifactId>
    <version>4.0.0</version>
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
    <version>4.0.0</version>
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
    <version>4.0.0</version>
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
    <version>4.0.0</version>
</dependency>
```

FluentLenium can be integrated with Cucumber in few ways. You can use classic inheritance from FluentCucumberTest class,
but it is not necessary - now FluentLenium can take care of injecting Cucumber steps by itself!

#### Classic with inheritance
- Create runner class annotated with FluentCucumber.class

```java
@RunWith(FluentCucumber.class)
@CucumberOptions
public class Runner {
}
```

- Extend BaseTest or each Step with `FluentCucumberTest` and configure in any way you want.

- Add this piece of code for to ONE of your Cucumber Step - the best practice is to create separate class for Cucumber
After and Before hooks:

```java
@Before
public void beforeScenario(Scenario scenario) {
    before(scenario);
}

@After
public void afterScenario(Scenario scenario) {
    after(scenario);
}
```

Or in Java 8 inside Step constructor:

```java
Before(this::before);

After(this::after);
```

#### Auto-Injecting Cucumber steps
- Create runner class annotated with FluentCucumber.class and use on it @FluentConfiguration:

```java
@RunWith(FluentCucumber.class)
@CucumberOptions
@FluentConfiguration(webDriver = "chrome")
public class Runner {
}
```
- If you want to use auto-injecting with initialization FluentLenium context you MUST use @FluentConfiguration on a runner
class, but configuration still can be passed through fluentlenium.properties or as system properties
(-Dfluentlenium.webDriver=chrome).

- You do not need to use FluentCucumberTest at all!

- It is possible to access current FluentCucumberTest using syntax:

```java
FluentAdapterContainer.FLUENT_TEST.instance()
```

It can be handy if you want have access to current WebDriver instance etc.

- Due to fact that you do not use inheritance you cannot use configuration throughJava beans or use FluentLenium API
directly in Cucumber steps. Use Page Object pattern instead (@Page annotation is your friend).

- Remember, that page objects still need to inherit FluentPage to run correctly.

#### Using Cucumber.class as runner

- It is still possible to use Cucumber.class instead FluentCucumber.class as runner but it is necessary to
add Before and After hooks to every Cucumber Step for correct injecting FluentLenium context.

E2E Cucumber test are present in [Cucumber example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/cucumber).
Enable it by activating ```examples``` Maven profile.

## Spock

- Import this maven dependency.

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-spock</artifactId>
    <version>4.0.0</version>
    <scope>test</scope>
</dependency>
```

- Extends FluentSpecification instead of FluentTest.

E2E Spock test are present in [Spock example](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/spock).
Enable it by activating ```examples``` Maven profile.

## Standalone mode

If you want to use FluentLenium as a pure automation Framework, without any testing framework adapter,
you can create an instance of `FluentStandalone` and use it directly, or extend `FluentStandaloneRunnable` and
implement `doRun()` method.

### FluentStandalone

Create an instanceof `FluentStandalone` and use it directly. You have to manually invoke `init()` to initialize the
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
instruction. If it's a probleme for your, you should consider extending `FluentStandaloneRunnable`.

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
};
```

```java
MyStandaloneRunnable runnable = new MyStandaloneRunnable();
runnable.run();
```
