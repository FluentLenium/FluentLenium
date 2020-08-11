---
layout: page
title: Test methods
subtitle: FluentLenium
sidebar:
  Window actions: "#window-actions"
  Keyboard and mouse: "#keyboard-and-mouse-actions"
  Drag and Drop: "#drag-and-drop"
  Events: "#events"
  Annotations: "#annotations"
  Listener API: "#listener-api"
  Hooks: "#hooks"
  Javascript execution: "#javascript-execution"
  CSS injection: "#css-injection"
  Screenshots and HTML dump: "#taking-screenshots-and-html-dumps"
  Alerts: "#alerts"
  Chromium API: "#chromium-api"
---

This section contains description of FluentLenium features which may be useful during writing tests.

## Table of Contents
- [Window actions](#window-actions)
- [Keyboard and Mouse actions](#keyboard-and-mouse-actions)
- [Drag and Drop](#drag-and-drop)
- [Events](#events)
- [Annotations](#annotations)
- [Listener API](#listener-api)
- [Hooks](#hooks)
- [Javascript execution](#javascript-execution)
- [CSS injection](#css-injection)
- [Taking ScreenShots and HTML Dumps](#taking-screenshots-and-html-dumps)
- [Iframe](#iframe)
- [Alerts](#alerts)
- [Performance Timing API](#performance-timing-api)
- [Chromium API](#chromium-api)


## Window actions

Window actions are available on ```window()``` method.

Few additional methods are available from ones inherited from Selenium, like ```clickAndOpenNew```,
```openNewAndSwitch```, ```clickAndCloseCurrent``` and ```switchToLast```

  - **clickAndOpenNew**

    You should use this method when click action invoked on element should end up with new window opened.
    This is just about waiting for new window to open, this method is not opening new browser window.

  - **openNewAndSwitch**

    This method opens new window and switches the context to newly opened one.

  - **clickAndCloseCurrent**

    You should use this method when click action invoked on element should end up with current window closed.
    This is just about waiting for new window to close, this method is not closing browser window.

  - **switchToLast**

    This method switch to the last window. If argument is provided with the name of a window, ensure that the last
    window has this name.

You can also maximize browser window:

```java
maximizeWindow();
```

## Keyboard and Mouse actions

Advanced keyboard and mouse actions are available using keyboard() and mouse() in FluentTest class or element.

## Drag and drop

Drag and drop can be done using `MouseElementActions`

```java
@Test
    public void dragAndDrop() {
        FluentWebElement source = el("#source");
        FluentWebElement target = el("target");
        MouseElementActions actions = new MouseElementActions(getDriver(), source.getElement());
        actions.dragAndDropTo(target.getElement());
    }
```

## Events

Selenium has a driver wrapper named `EventFiringWebDriver` that is able to generate events and register listeners.

FluentLenium brings Events Annotations and a Listener API to register those listeners easily.

### Annotations

You can use annotations from `core.events.annotations` package to register any method as an event
listener.

Annotations can be used in a test class.

```java
@AfterClickOn
public void afterClickOn(FluentWebElement element) {
    System.out.println("Element Clicked: " + element);
}
```

Annotations related to a WebElement can also be used in a component class.

```java
public class SomeComponent {
    private WebElement element;

    public SomeComponent(WebElement element) {
        this.element = element;
    }

    public SomeComponent click() {
        element.click();
        return this;
    }

    @AfterClickOn
    private void afterClickOn() {
        System.out.println("Element Clicked: " + this);
    }
}

public class SomeTest extends FluentTest {

    private SomeComponent clickComponent;
    private SomeComponent otherComponent;

    @Test
    public void test() {
        clickComponent.click();
        // @AfterClickOn annotated method will be invoked for clickComponent instance only.
    }
}

```

Annotations may be also be configured with a priority, so that annotations with higher priority value will be executed first.

```java
public class SomeComponent {
    private WebElement element;

    public SomeComponent(WebElement element) {
        this.element = element;
    }

    public SomeComponent click() {
        element.click();
        return this;
    }

    @AfterClickOn(10)
    private void afterClickOnHigher() {
        System.out.println("Element Clicked, with higher priority: " + this);
    }
    
    @AfterClickOn(5)
    private void afterClickOnLower() {
        System.out.println("Element Clicked, with lower priority: " + this);
    }
}
```

The default priority value of all event annotations are 0.

Using event annotations events will be registered with a default annotation based listener implementation which cannot be overridden.

### Listener API

You can also register events through API using `events()` method.

```java
events().afterClickOn(new ElementListener() {
    @Override
    public void on(FluentWebElement element, WebDriver driver) {
        System.out.println("Element Clicked: " + element);
    }
});

el("button").click(); // This will call the listener.
```

This integrates nicely with Java 8 lambdas:

```java
events().afterClickOn((element, driver) -> System.out.println("Element Clicked: " + element));

el("button").click(); // This will call the listener.
```

The `events()` method provides a more flexible way of registering listeners as it allows one to register their custom listener implementations, and the examples above demonstrate it well.

To also be able to handle the priority of these custom listeners one must also implement the `ListenerPriority` interface, so that listeners get sorted by their priority.

```java
private final class AfterClick implements ListenerPriority, ElementListener {

    @Override
    public void on(FluentWebElement element, WebDriver driver) {
        System.out.println("A really awesome click happened!");
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
```

### Combining the two solutions

Annotation and Listener API based listeners can be used along with each other. If you define priorities for the event annotations, and you don't implement `ListenerPriority` in case of custom listeners,
the custom listeners will be executed last among the registered listeners.

## Hooks

It's possible to add some behavior for any element without changing its code by using hooks.

FluentLenium is shipped with the ```Wait``` hook.

```java
// This call will wait for ajax-element to be clickable.
find(".fluent").withHook(WaitHook.class).click();

// Hooks can be chained and their effect will be associated.
find(".fluent").withHook(WaitHook.class).withHook(AnotherHook.class).click();

// Options can be given to hooks.
find(".fluent")
    .withHook(WaitHook.class, WaitHookOptions.builder().atMost(20L).build())
    .click()
```

### Hook Annotations

Hooks can also be mapped to annotation, like ```@Wait``` for the ```WaitHook``` class.

You can place a hook annotation on injected Field, Fluent Test class or Page.

The hook will be effective for a field if annotation is present on it. All fields from a Fluent Test class if
it's present on a Fluent Test class and all fields from a Page if it's present on a Page.

Page build through Injection recursively inherits hooks from parent pages and fluent test.

If you need to disable all inherited hooks in particular Page or Field, you should use ```@NoHook``` annotation,
or call ```noHook()``` function on the element, with no parameter to remove all hooks, of with parameters to remove
only given hooks.

It's also possible to use the generic ```@Hook``` annotation to enable a hook class.

```java
@Wait
public class MyTest extends FluentTest {
    FluentWebElement waitElement;

    @Hook(AnotherHook.class)
    FluentWebElement waitAndAnotherHookElement;

    @NoHook
    FluentWebElement noHookElement;

    @Page
    MyPage page;
}

@Hook(PageHook.class)
public class MyPage extends FluentPage {

}
```

### Custom hook

It's possible to implement your own hook by extending ```BaseHook``` or ```BaseFluentHook```.

Let's implement Example hook by writing a configurable message before and after click.

- Create the hook option class. It should be a JavaBean containing configuration options for the hook.

```java
public class ExampleHookOptions {
    private String message = "ExampleHook";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

- Create the hook by extending ```BaseHook``` or ```BaseFluentHook```. It should at least have a public
constructor matching parent class, and generic type should be a hook option class.

```java
public class ExampleHook extends BaseHook<ExampleHookOptions> {
    public ExampleHook(FluentControl control, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, ExampleHookOptions options) {
        super(control, instantiator, elementSupplier, locatorSupplier, options);
    }
}

```

- Create the hook annotation. Annotation parameters should match options found in hook option class.

```java
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Hook(ExampleHook.class)
@HookOptions(ExampleHookOptions.class)
public @interface Example {
    String message() default "";
}
```

- Add a constructor to hook options class that will support loading of hook annotation into hook option class.

```java
public class ExampleHookOptions {
    public ExampleHookOptions(Example annotation) {
        message = annotation.message();
    }

    public ExampleHookOptions() {
        // Default constructor
    }
```

- Override methods from ```WebElement``` or ```ElementLocator``` in the hook implementation class to add desired
behavior.

```java
@Override
public void click() {
    System.out.println(getOptions().getMessage() + ": before click!");
    super.click();
    System.out.println(getOptions().getMessage() + ": after click!");
}
```

[Example sources are available on github](https://github.com/FluentLenium/FluentLenium/tree/master/examples/hooks/src/test/java/org/fluentlenium/examples/hooks).

You may also read [sources for @Wait hook](https://github.com/FluentLenium/FluentLenium/tree/master/fluentlenium-core/src/main/java/org/fluentlenium/core/hook/wait) to read how it's implemented.

## Javascript execution
If you need to execute some javascript, just call `executeScript` with your script as parameter.
For example, if you have a javascript method called change and you want to call it just add this in your test:

```java
executeScript("change();");
```

You can either execute javascript with arguments, with async `executeAsyncScript`, and retrieve the result.

```java
executeScript("change();", 12L).getStringResult();
```

## CSS injection

It is possible to manipulate the CSS styling of pages via calling `css()`.

It can be called from a `FluentWebElement`, `FluentDriver` and any of the test adapter implementations.

You can either inject an explicitly defined CSS styling:

```java
css().inject("#location {\ndisplay: none\n}");
``` 

or you can inject the content of a CSS resource from the project classpath:

```java
css().injectResource("/path/to/css/resource.css");
```

## Taking ScreenShots and HTML Dumps
You can take a ScreenShot and an HTML Dump of the browser.

```java
takeScreenshot();
takeHtmlDump();
```
The file will be named using the current timestamp.
You can of course specify a path and a name using:

```java
takeScreenshot(pathAndFileName);
takeHtmlDump(pathAndFileName);
```

Screenshot and HTML Dump can be automatically performed on test fail using configuration properties.

When using `AUTOMATIC_ON_FAIL` with JUnit, you should use custom `@After` annotation from
`org.fluentlenium.adapter.junit` package for screenshot and HTML dump to be performed
just after an exception occurs, before methods annotated with `@After` invocation.

## Iframe
If you want to switch the Selenium webDriver to an iframe (see this [Selenium FAQ](https://code.google.com/p/selenium/wiki/FrequentlyAskedQuestions#Q:_How_do_I_type_into_a_contentEditable_iframe?)),
you can just call the method switchTo() :

To switch to the default context:

```java
switchTo();
switchToDefault(); // Alternative method
```

To switch to the iframe selected:

```java
switchTo($("iframe#frameid"));
```

## Alerts
If you want manage alert (see this [Selenium FAQ](http://code.google.com/p/selenium/wiki/FrequentlyAskedQuestions#Q:_Does_WebDriver_support_Javascript_alerts_and_prompts?)),

When an alert box pops up, click on "OK":

```java
alert().accept();
```

When an alert box pops up, click on "Cancel":

```java
alert().dismiss();
```

Entering an input value in prompt:

```java
alert().prompt("FluentLenium")
```

## Chromium API

FluentLenium gives you an opportunity to run Chrome DevTools commands in your tests using simple Chromium API.


What is Chromium API? 

Chromium API gives you an easy access to the [Chrome DevTools Protocol](https://chromedevtools.github.io/devtools-protocol/). This was possible by extending commands supported by Selenium with two new endpoints to directly call the DevTools API:

- /session/:sessionId/chromium/send_command_and_get_result
- /session/:sessionId/chromium/send_comman

It will allow you to easily automate Chrome browser beyond the standard [WebDriver protocol](https://www.w3.org/TR/webdriver/).


Why you may want to use it? 

With the DevTool protocol will be able to achieve more powerful interactions with the browser compare to WebDriver. Taking full page screenshot or clearing cookies for every domain are just two examples how you could use it.


How to use it?

```java
  public class DuckDuckGoChromiumApiTest extends FluentTest {
  
      private Response response;
  
      @Override
      public WebDriver newWebDriver() {
          return new ChromeDriver();
      }
  
      @Test
      public void resultPageUrlShouldContainSearchQueryName() {
          String searchPhrase = "searchPhrase";
          String duckDuckUrl = "https://duckduckgo.com";
  
          getChromiumApi().sendCommand("Page.navigate", ImmutableMap.of("url", duckDuckUrl));
          getChromiumApi().sendCommand("Input.insertText", ImmutableMap.of("text", searchPhrase));
          getChromiumApi().sendCommand("Input.dispatchKeyEvent", sendEnterKeyEventParams());
          response = getChromiumApi().sendCommandAndGetResponse("Page.getNavigationHistory", ImmutableMap.of());
  
          assertIsPhrasePresentInTheResultsPageUrl(searchPhrase);
      }
  
      private Map<String, String> sendEnterKeyEventParams() {
          return ImmutableMap.of("type", "char", "text", "\r");
      }
  
      private void assertIsPhrasePresentInTheResultsPageUrl(String searchPhrase) {
          assertThat(response.getValue().toString()).contains(searchPhrase);
      }
  }
```

## Performance Timing API

FluentLenium provides an API for retrieving the performance timing metrics based on the [PerformanceTiming interface defined by W3C](https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface).

The main interface for this API is `PerformanceTiming` from which you can query individual metric values and metrics in bulk as well.
Most methods return `long` values querying the `window.performance.timing.<metric>` Javascript attribute, except the ones that are explicitly stated in the W3C documentation that they
may have other type of values as well.

They can be retrieved via `FluentTest` and its other framework specific variants:

```java
public class SomeTest extends FluentTest {

    @Page
    private Homepage homepage;

    @Test
    public void test() {
        //Get metric via parameterized method
        long loadEventEnd = performanceTiming().getEventValue(PerformanceTimingEvent.LOAD_EVENT_END);
        
        //The same as the previous call but the value is converted to the given time unit
        long loadEventEndInSeconds = performanceTiming().getEventValue(PerformanceTimingEvent.LOAD_EVENT_END, TimeUnit.SECONDS);
        
        //This is a convenience method for calling performanceTiming().getEventValue(PerformanceTimingEvent.DOM_COMPLETE);
        long domComplete = performanceTiming().domComplete();
        
        //The same as the previous call but the value is converted to the given time unit
        long domCompleteInSeconds = performanceTiming().domComplete(TimeUnit.SECONDS);
        
    }
}

and via `FluentPage` as well:

public class Homepage extends FluentPage {
    
    public long getDomComplete() {
        return performanceTiming().domComplete();
    }
    
    public long getLoadEventEnd() {
        return performanceTiming().getEventValue(PerformanceTimingEvent.LOAD_EVENT_END);
    }
}
```

Each method returning a specific metric execute a separate Javascript command.

There is another way to get metrics, specifically to get all metrics in a single object called `PerformanceTimingMetrics`. This returns the object returned by the `window.performance.timing`
Javascript attribute.

```java
@Test
public void test() {
    //This returns the metrics by default in default milliseconds
    PerformanceTimingMetrics metrics = performanceTiming().getMetrics();
    long domComplete = metrics.getDomComplete();
    
    //This returns a new metrics object that will return the values in the set time unit
    PerformanceTimingMetrics metricsInSeconds = metrics.in(TimeUnit.SECONDS);
    long domCompleteInSeconds = metricsInSeconds.getDomComplete();
}
```

In this case only a single Javascript command is executed for `performanceTiming().getMetrics()`, `getDomComplete()` (actually any method) on this object returns the saved value,
and none of the getter methods execute any additional Javascript command.

It is important to note the the implementations of both the `PerformanceTiming` and `PerformanceTimingMetrics` interfaces provided by FluentLenium return handle the `navigationStart`
attribute as zero and every other metric is calculated and returned relative to `navigationStart`.

Before retrieving a performance timing metrics value make sure that the page where you query it loaded completely.
In case when navigation happens to a specific URL, or bz some action performed on the page make sure in your test that the page where it navigates to loads completely.
Otherwise certain metrics might not have been registered until that moment.

You can find some examples in the [FluentLenium project](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/performance) for how you can use these features.
