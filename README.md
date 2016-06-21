# What is FluentLenium ?

[![Travis](https://img.shields.io/travis/FluentLenium/FluentLenium.svg)](https://travis-ci.org/FluentLenium/FluentLenium)
[![Coveralls](https://img.shields.io/coveralls/FluentLenium/FluentLenium.svg)](https://coveralls.io/github/FluentLenium/FluentLenium)
[![HuBoard](https://img.shields.io/badge/Hu-Board-7965cc.svg)](https://huboard.com/FluentLenium/FluentLenium)

FluentLenium is a framework that helps you to write [Selenium](http://seleniumhq.org/) tests.
FluentLenium provides you a [fluent interface](http://en.wikipedia.org/wiki/Fluent_interface) to the [Selenium Web Driver](http://seleniumhq.org/docs/03_webdriver.html).
FluentLenium lets you use the assertion framework you like, either [jUnit assertions](http://www.junit.org/apidocs/org/junit/Assert.html), [Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial) 
or [AssertJ](https://github.com/joel-costigliola/assertj-core)  (old one: ~~[Fest-assert](https://github.com/alexruiz/fest-assert-2.x/wiki)~~).


# 5 second example
```java
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BingTest extends FluentTest {
    @Test
    public void title_of_bing_should_contain_search_query_name() {
        goTo("http://www.bing.com");
        fill("#sb_form_q").with("FluentLenium");
        submit("#sb_form_go");
        assertThat(title()).contains("FluentLenium");
    }
}
```

## Maven

To add FluentLenium to your project, just add the following dependency to your `pom.xml`:

```xml 
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-core</artifactId>
    <version>0.13.0</version>
    <scope>test</scope>
</dependency>
```

By default, FluentLenium provides a jUnit adapter.

If you need the assertj dependency to improve the legibility of your test code:

```xml 
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>0.13.0</version>
    <scope>test</scope>
</dependency>
```

An adapter has also been built for using FluentLenium with TestNG:

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-testng</artifactId>
    <version>0.13.0</version>
    <scope>test</scope>
</dependency>
```

Just extend `org.fluentlenium.adapter.FluentTestNg` instead of `org.fluentlenium.adapter.FluentTest`.

##Static imports

If you need to do some filtering:

```java
import static org.fluentlenium.core.filter.FilterConstructor.*;
```

### Static import using AssertJ
The static assertions to use AssertJ are:

```java
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
```

### Basic Methods
You can use `url()` , `title()` or `pageSource()` to get the url, the title or the page source of the current page.

###  Selector
#### Default Selector
You can use CSS1, CSS2 and CSS3 selectors with the same restrictions as in Selenium.

If you want to find the list of elements which have:

  - the `id` "title" : `find("#title")`
  - the `class` name "small" : `find(".small")`
  - the `tag` name "input" : `find("input")`

You are free to use most of the CSS3 syntax, which means that
`find("input[class=rightForm]")`
will return the list of all input elements which have the class rightForm

#### Custom filter
But what if you want all the input that have text equal to "Sam" ?
You can use filters to allow that kind of search. For example:

```java
find(".small", withName("foo"))
find(".small", withClass("foo"))
find(".small", withId("idOne"))
find(".small", withText("This field is mandatory."))
```

You can skip CSS selector argument:
`find(withId("idOne"))` will return the element whose id attribute is "idOne".

You can use `By` locator `Selenium object` instead of CSS selector argument:
`find(By.cssSelector(".header"))` will return the element using `By` locator as a reference - so you can use everything what `By` object offering to you.

You can also write chained filters:
`find(".small", withName("foo"), withId("id1"))` will return all the elements matching the 3 criteria.

You can do more complex string matching on the above filters using the following methods:

  - `contains`
  - `containsWord`
  - `notContains`
  - `startsWith`
  - `notStartsWith`
  - `endsWith`
  - `notEndsWith`

For each of them, you can choose to use a css selector:

```java
find(".small", withName().notContains("name"))
find(".small", withId().notStartsWith("id"))
find(".small", withText().endsWith("Female"))
```

Or to be more precise, you can use regular expressions:

```java
find(".small", withName().contains(regex("na?me[0-9]*")))
find(".small", withName().notStartsWith(regex("na?me[0-9]*")))
```

Contains, startsWith and endsWith with a regexp pattern look for a subsection of the pattern.


### N-th
If you want the first element that matches your criteria, just use:

```java
findFirst(myCssSelector)
```

or alternatively

```java
find(myCssSelector).first()
```

If you want the element at the given position:

```java
find(myCssSelector, 2)
```

Of course, you can use both position filter and custom filter:

```java
find(myCssSelector, 2, withName("foo"))
```


#### Find on children
You can also chain the find call:
`find(myCssSelector).find("input")` will return all the input elements in the css selector tree.
You can be more specific:

```java
find(myCssSelector, 2, withName("foo")).find("input", withName("bar"))
```

or

```java
find(myCssSelector, 2, withName("foo")).findFirst("input", withName("bar"))
```

## Element
If you need to access to the name, the id, the value, the tagname or the visible text of an element:

```java
findFirst(myCssSelector).getName()
findFirst(By.cssSelector(".foo")).getId()
findFirst(myCssSelector).getValue()
findFirst(myCssSelector).getTagName()
findFirst(myCssSelector).getText()
```

If you need to access the text content of an element, including hidden parts:

```java
findFirst(myCssSelector).getTextContent()
```

If you need to access a specific value of an attribute:

```java
findFirst(myCssSelector).getAttribute("myCustomAttribute")
```

You can also access a list of all the names, visible text, and ids of a list of elements:
```java
find(myCssSelector).getNames()
find(By.id("foo")).getIds()
find(myCssSelector).getValues()
find(myCssSelector).getAttributes("myCustomAttribute")
find(myCssSelector).getTexts()
```

If you want to know the name, the id, the value, the visible text or the value of an attribute of the first element on the list:
```java
find(myCssSelector).getName()
find(myCssSelector).getId()
find(myCssSelector).getValue()
find(myCssSelector).getAttribute("myCustomAttribute")
find(myCssSelector).getText()
```

If you need to get the underlying html content of an element:
```java
findFirst(myCssSelector).html()
```
To know the dimension of an element (with and height):

```java
Dimension dimension = findFirst(myCssSelector).getSize()
```

You can also check if the element is displayed, enabled or selected:
```java
findFirst(myCssSelector).isDisplayed()
findFirst(myCssSelector).isEnabled()
findFirst(myCssSelector).isSelected()
```

If you need to retrieve other elements from a selected one, you 
can use [XPath axes](http://www.w3schools.com/xsl/xpath_axes.asp).

find(myCssSelector()).axes().parent()
find(myCssSelector()).axes().descendants()
find(myCssSelector()).axes().ancestors()
find(myCssSelector()).axes().followings()
find(myCssSelector()).axes().followingSiblings()
find(myCssSelector()).axes().precedings()
find(myCssSelector()).axes().precedingSiblings()

## Form Actions
Clicking, filling, submitting and cleaning an element or list of elements is simple and intuitive.

### Fill
`fill("input").with("bar")` or `find("input").text("bar")` will fill all the input elements with bar. 
If you want for example to exclude checkboxes, you can use the css filtering like `fill("input:not([type='checkbox'])").with("tomato")`, 
you can also use the filtering provided by FluentLenium `fill("input", with("type", notContains("checkbox"))).with("tomato")`


`fill("input").with("myLogin","myPassword")` will fill the first element of the input selection with myLogin, the second with myPassword. 
If there are more input elements found, the last value (myPassword) will be repeated for each subsequent element.

If you're trying to fill a select element, you can use `fillSelect("daySelector").withValue("MONDAY")` to fill it with a value, `fillSelect("daySelector").withIndex(1)` to fill it with a value by its index or `fillSelect("daySelector").withText("Monday")` to fill it with a value by its text.

`fillSelect`can also be invoked directly on an element, for instance `findFirst("daySelector").fillSelect().withValue("MONDAY")`.

Don't forget, only visible fields will be modified. It simulates a real person using a browser!

### Click
```java
click("#create-button")
```

This will click on all the enabled fields returned by the search.

### Clear
```java
clear("#firstname")
```

This will clear all the enabled fields returned by the search.

### Submit
```java
submit("#account")
```

This will submit all the enabled fields returned by the search.

### Double click
```java
find("#create-button").doubleClick()
```

### Mouse over
```java
find("#create-button").mouseOver()
```

## Events

Selenium has a driver wrapper named `EventFiringWebDriver` that is able to generate events and register listeners.

FluentLenium brings an Events API to register those listeners easily. Make sure you have configured FluentLenium to use
an instance of `org.openqa.selenium.support.events.EventFiringWebDriver` by overriding `getDefaultDriver`.

```java
public WebDriver getDefaultDriver() {
    return new EventFiringWebDriver(super.getDefaultDriver());
}
```

And use `events` methods to register listeners.

```java
events().afterClickOn(new ElementListener() {
    @Override
    public void on(FluentWebElement element, WebDriver driver) {
        System.out.println("Element Clicked: " + element);
    }
});

findFirst("button").click(); // This will call the listener.
```

This integrates nicely with Java 8 lambdas

```java
events().afterClickOn((element, driver) -> System.out.println("Element Clicked: " + element));

findFirst("button").click(); // This will call the listener.
```


## Page Object pattern
Selenium tests can easily become a mess.  To avoid this, you can use the [Page Object Pattern](http://code.google.com/p/selenium/wiki/PageObjects).
Page Object Pattern will enclose all the plumbing relating to how pages interact with each other and how the user
interacts with the page, which makes tests a lot easier to read and to maintain.

Try to construct your Page thinking that it is better if you offer services from your page rather than just the internals of the page.
A Page Object can model the whole page or just a part of it.

To construct a Page, extend [org.fluentlenium.core.FluentPage](https://github.com/FluentLenium/FluentLenium/blob/master/fluentlenium-core/src/main/java/org/fluentlenium/core/FluentPage.java).
In most cases, you have to define the url of the page by overriding the `getUrl` method.
It is also possible to use `@PageUrl` annotation, its value will be passed to the `getUrl` method, overriding is not required.
By doing this, you can then use the `goTo(myPage)` method in your test code.

It may be necessary to ensure that you are on the right page, not just at the url returned by `getUrl` [accessible in your test via the void url() method].
To do this, override the `isAt` method to run all the assertions necessary in order to ensure that you are on the right page.
For example, if I choose that the title will be sufficient to know if I'm on the right page:

```java
@Override
public void isAt() {
    assertThat(title()).contains("Selenium");
}
```

Create your own methods to easily fill out forms, go to another or whatever else may be needed in your test.

For example:

```java
public class LoginPage extends FluentPage {
    public String getUrl() {
        return "myCustomUrl";
    }
    public void isAt() {
        assertThat(title()).isEqualTo("MyTitle");
    }
    public void fillAndSubmitForm(String... paramsOrdered) {
        fill("input").with(paramsOrdered);
        click("#create-button");
    }
}
```

or using `@PageUrl` annotation instead of overriding `getUrl` method

```java
@PageUrl("myCustomUrl")
public class LoginPage extends FluentPage {
    public void isAt() {
        assertThat(title()).isEqualTo("MyTitle");
    }
    public void fillAndSubmitForm(String... paramsOrdered) {
        fill("input").with(paramsOrdered);
        click("#create-button");
    }
}
```

And the corresponding test:

```java
public void checkLoginFailed() {
	goTo(loginPage);
	loginPage.fillAndSubmitLoginForm("login", "wrongPass");
	loginPage.isAt();
}
```

Or if you have the [AssertJ](https://github.com/joel-costigliola/assertj-core) module (just static import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat)

```java
public void checkLoginFailed() {
	goTo(loginPage);
	loginPage.fillAndSubmitLoginForm("login","wrongPass");
	assertThat(find(".error")).hasSize(1);
	assertThat(loginPage).isAt();
}
```

###Page usage
You can use the annotation `@Page` to construct your page easily.

For example:

```java
public class AnnotationInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

    @Page
    public TestPage page;


    @Test
    public void test_no_exception() {
        goTo(page);
        //put your assertions here
    }


    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

}
```
It's now possible to use the `@Page` annotation in a FluentPage.

You can also use the factory method `createPage`:

```java
public class BeforeInitialization extends FluentTest {
	public WebDriver webDriver = new HtmlUnitDriver();
	public TestPage page;
	@Before
	public void beforeTest() {
		page = createPage(TestPage.class);
	}
	@Test
	public void test_no_exception() {
		page.go();
	}
	@Override
	public WebDriver getDefaultDriver() {
		return webDriver;
	}
}
```

Within a page, all FluentWebElement fields are automatically searched for by name or id. For example, if you declare a FluentWebElement named `createButton`, 
it will search the page for an element where `id` is `createButton` or name is `createButton`. 
All elements are proxified which means that the search is not done until you try to access the element.

```java
public class LoginPage extends FluentPage {
   FluentWebElement createButton;
   public String getUrl() {
       return "myCustomUrl";
   }
   public void isAt() {
       assertThat(title()).isEqualTo("MyTitle");
   }
   public void fillAndSubmitForm(String... paramsOrdered) {
       fill("input").with(paramsOrdered);
       createButton.click();
   }
}
```

Not only FluentWebElement fields are populated. Every type with a constructor taking a WebElement is a candidate.
This makes it possible for the page to expose fields with functional methods and not (only) the 'technical' methods
that FluentWebElement exposes.

```java
public class LoginPage extends FluentPage {
   MyButton createButton;
   public void fillAndSubmitForm(String... paramsOrdered) {
       fill("input").with(paramsOrdered);
       createButton.clickTwice();
   }
   public static class MyButton {
       WebElement webElement;
       public MyButton(WebElement webElement) {
           this.webElement = webElement;
       }
       public void clickTwice() {
           webElement.click();
           webElement.click();
       }
   }
}
```

If the naming conventions of your HTML ids and names don't match with the naming conventions of your Java fields,
or if you want to select an element with something other than the id or name, you can annotate the field with the
Selenium `@FindBy` (or `@FindBys`) annotation. The following example shows how to find the create button if its CSS class is
`create-button`.

```java
public class LoginPage extends FluentPage {
   @FindBy(css = "button.create-button")
   FluentWebElement createButton;
   public String getUrl() {
       return "myCustomUrl";
   }
   public void isAt() {
       assertThat(title()).isEqualTo("MyTitle");
   }
   public void fillAndSubmitForm(String... paramsOrdered) {
       fill("input").with(paramsOrdered);
       createButton.click();
   }
}
```

You can also refer to the list of FluentWebElements

```java
public class LoginPage extends FluentPage {
   @FindBy(css = "button.create-button")
   FluentList<FluentWebElement> createButtons;
   public String getUrl() {
       return "myCustomUrl";
   }
   public void isAt() {
       assertThat(title()).isEqualTo("MyTitle");
       assertThat(buttons).hasSize(2);
   }
   public void fillAndSubmitForm(String... paramsOrdered) {
       fill("input").with(paramsOrdered);
       createButtons.get(1).click();
   }
}
```

If you need to wait for an element to be present, especially when waiting for an ajax call to complete, you can use the @AjaxElement annotation on the fields:

```java
public class LoginPage extends FluentPage {
   @AjaxElement
   FluentWebElement myAjaxElement;
}
```
You can set the timeout in seconds for the page to throw an error if not found with `@AjaxElement(timeountOnSeconds=3)` if you want to wait 3 seconds.
By default, the timeout is set to one second.

## Extend FluentWebElement to model components

You can implement reusable components by extending FluentWebElement. Doing so will improve readability of both Page Objects and Tests.

```java
public class SelectComponent extends FluentWebElement {
   public FluentWebElement(WebElement element) { // This constructor MUST exist !
      super(element);
   }
   
   public void doSelect(String selection) {
      // Implement selection provided by this component.
   }
   
   public String getSelection() {
      // Return the selected value as text.
   }
}
```

These kind of component can be created automatically by `FluentPage`, 
or programmatically by calling `as` method of FluentWebElement or FluentList.

```java
SelectComponent comp = findFirst("#some-select").as(SelectComponent.class);

comp.doSelect("Value to select");
assertThat(comp.getSelection()).isEquals("Value to select");
```


## Wait for an Ajax Call

There are multiple ways to make your driver wait for the result of an asynchronous call.
FluentLenium provides a rich and fluent API in order to help you to handle AJAX calls.
If you want to wait for at most 5 seconds until the number of elements corresponding to the until criteria (here the class small) has the requested size:


```java
await().atMost(5, TimeUnit.SECONDS).until(".small").hasSize(3);
```
The default wait is 500 ms.

Instead of hasSize, you can also use `hasText("myTextValue")`, `isPresent()`, `isNotPresent()`, `hasId("myId")`, `hasName("myName")`, `containsText("myName")`,`areDisplayed()`, `areEnabled()`.
The `isPresent()` assertion is going to check if there is at most one element on the page corresponding to the filter.

If you need to be more precise, you can also use filters in the search:

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").withText("myText").hasSize(3);
```
You can also use after hasSize() : 'greaterThan(int)', 'lessThan(int)', 'lessThanOrEqualTo(int)', 'greaterThanOrEqualTo(int)' , 'equalTo(int)', 'notEqualTo(int)'

You can also use matchers:

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").withText().startsWith("start").isPresent();
```
     
Just use `startsWith`, `notStartsWith`, `endsWith`, `notEndsWith`, `contains`, `notContains`, `equalTo`, `containsWord`.

If you need to filter on a custom attribute name, this syntax will help:

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").isPresent();
```

You can also give instance of elements or list of elements if required.

```java
@FindBy(css = ".button")
FluentWebElement button;

await().atMost(5, TimeUnit.SECONDS).until(element).isEnabled();
```

When running Java >= 8, you can use lambdas with `until`, `untilPredicate`, `untilElement` or `untilElements`.
```java
await().atMost(5, TimeUnit.SECONDS).untilElement(() -> findFirst(".button")).isEnabled();
await().atMost(5, TimeUnit.SECONDS).untilElements(() -> find(".button")).areEnabled();

await().atMost(5, TimeUnit.SECONDS).untilPredicate((f) -> findFirst(".button").isEnabled());
await().atMost(5, TimeUnit.SECONDS).until(() -> findFirst(".button").isEnabled());
```

You can also check if the page is loaded using
```java
await().atMost(1, NANOSECONDS).untilPage().isLoaded();
```

If you want to wait until the page you want is the page that you are at, you can use:
```java
await().atMost(5, TimeUnit.SECONDS).untilPage(myPage).isAt();
```
This methods actually calls myPage.isAt(). If the isAt() method of the myPage object does not throw any exception during the time specified, then the framework will consider that the page is the one wanted.

You can override `await()` method in your own test class to define global settings on wait objects.
```java
@Override
public FluentWait await() {
    return super.await().atMost(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, ElementNotVisibleException.class);
}
```

### Polling Every
You can also define the polling frequency, for example, if you want to poll every 5 seconds:
```java
await().pollingEvery(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").isPresent();
```
The default value is 500ms.

You can also chain filter in the asynchronous API:

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").with("a second attribute").equalTo("my@ndValue").isPresent();
```
## Alternative Syntax

If you are more familiar with the [JQuery](http://jquery.com/) syntax, you can use the familiar `$` method:

```java
goTo("http://mywebpage/");
$("#firstName").text("toto");
$("#create-button").click();
assertThat(title()).isEqualTo("Hello toto");
```

Both syntax are equivalent. `$` is simply an alias for the `find` method.


## Execute javascript
If you need to execute some javascript, just call `executeScript` with your script as parameter.
For example, if you have a javascript method called change and you want to call it just add this in your test:

```java
executeScript("change();");
```

You can either execute javascript with arguments, with async `executeAsyncScript`, and retrieve the result.

```java
executeScript("change();", 12L).getStringResult();
```

## Taking ScreenShots and HTML Dumps
You can take a ScreenShot and a HTML Dump of the browser.
```java
takeScreenShot();
takeHtmlDump();
```
The file will be named using the current timestamp.
You can of course specify a path and a name using:
```java
takeScreenShot(pathAndfileName);
takeHtmlDump(pathAndfileName);
```
Screenshot and HTML Dump can be automatically performed on test fail.
```java
setScreenShotPath(path);
setHtmlDumpPath(path);
setScreenShotMode(TriggerMode.ON_FAIL);
setHtmlDumpMode(TriggerMode.ON_FAIL);
```

## Isolated Tests
If you want to test concurrency or if you need for any reason to not use the mechanism of extension of FluentLenium, you can also, instead of extending FluentTest, instantiate your fluent test object directly.
```java
= new IsolatedTest().goTo(DEFAULT_URL).
    await().atMost(1, SECONDS).until(".small").with("name").equalTo("name").isPresent().
    find("input").first().isEnabled();
```



## Customize FluentLenium

### Driver
If you need to change your driver, just override the `getDefaultDriver` method in your test. You can use every driver.
For instance, to run your tests on [BrowserStack](https://browserstack.com)

```java
  @Override
  public WebDriver getDefaultDriver() {
    String HUB_URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@hub.browserstack.com/wd/hub";

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability("os", "OS X");
    caps.setCapability("os_version", "El Capitan");
    caps.setCapability("browser", "firefox");
    caps.setCapability("browser_version", "44");
    caps.setCapability("build", "Sample FluentLenium Tests");
    caps.setCapability("browserstack.debug", "true");

    URL hubURL = null;
    try {
      hubURL = new URL(HUB_URL);
    } catch(Exception e) {
      System.out.println("Please provide proper crendentials. Error " + e);
    }

    return new RemoteWebDriver(hubURL, caps);
  }
```

### Base Url
If you want to defined a default base url, just override the `getDefaultBaseUrl` method in your test. Every pages create with @Page will also use this variable.
If a base url is provided, the current url will be relative to that base url.

### TimeOut
To set the time to wait when searching an element, you can use in your test:
```java
withDefaultSearchWait(long l, TimeUnit timeUnit);
```

 To set the time to wait when loading a page, you can use:
```java
withDefaultPageWait(long l, TimeUnit timeUnit);
```

Be aware that when you modified this elements, the webDriver instance will be modified so your page will also be affected.

### Configuration
You can define a default driver configuration using two ways.
First, just override the getDriver method and use the selenium way to configure your driver.
You can also override the getDefaultConfig method and use both selenium and FluentLenium way (withDefaultSearchWait,withDefaultPageWait) to configure your driver.

## Browser Lifecycle
For JUnit and TestNG, you can define the browser lifecycle.
Use the class annotation @SharedDriver and you will be able to define how the driver will be created:
```java
@SharedDriver(type = SharedDriver.SharedType.ONCE)
``` 
will allow you to use the same driver for every test annotate with that annotation (it can also be on a parent class) for all classes and methods.
```java
@SharedDriver(type = SharedDriver.SharedType.PER_CLASS)
``` 
will allow you to use the same driver for every test annotate with that annotation (it can also be on a parent class) for all methods on a same class.
```java
@SharedDriver(type = SharedDriver.SharedType.PER_METHOD)
``` 
will allow you to create a new driver for each method.

The default is PER_METHOD.

You will also be able to decide if you want to clean the cookies between two methods using ```@SharedDriver(deleteCookies=true)``` or ```@SharedDriver(deleteCookies=false)```

Please keep in mind that this annotation tells how the drivers are created on runtime but it is not dealing with
concurrency. If you need to make your tests parallel you should use dedicated libraries/extensions. You can use
Surefire maven plugin for example.

**Surefire JUnit example**

```xml
<profile>
    <id>junit-tests</id>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <dependencies>
                    <dependency>
                        <groupId>org.apache.maven.surefire</groupId>
                        <artifactId>surefire-junit47</artifactId>
                    </dependency>
                </dependencies>
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

**Surefire TestNG example**

```xml
<profile>
    <id>testng-tests</id>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <dependencies>
                    <dependency>
                        <groupId>org.apache.maven.surefire</groupId>
                        <artifactId>surefire-testng</artifactId>
                    </dependency>
                </dependencies>
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

Both test frameworks are giving possibility to define the parallelism level of tests. It is possible when you have
multiple execution/concurrency levels set in your tests to face driver sharing issues, so please use driver
sharing set to **PER_METHOD** when your execution methods are mixed up.

*Example failure*: might occur when you set the Surefire to per method and FluentLenium to PER_CLASS and you will end
up with ghost webdriver instances which won't be stopped after tests execution. The good practice is to check the
number of running process (chromedriver, firefox, etc.) before and after your tests run just to make sure the cleanup
 is working properly.

##Iframe
If you want to switch the Selenium webDriver to an iframe (see this [Selenium FAQ](https://code.google.com/p/selenium/wiki/FrequentlyAskedQuestions#Q:_How_do_I_type_into_a_contentEditable_iframe?)),
you can just call the method switchTo() :

To switch to the default context:
```java
switchTo();
```
or
```java
switchToDefault();
```

To switch to the iframe selected:
```java
switchTo(findFirst("iframe#frameid"));
```

##Alert
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

##Window
Maximize browser window:
```java
maximizeWindow();
```

##Users/dev
If you have any comments/remarks/bugs, please raise an issue on github:
[FluentLenium](https://github.com/FluentLenium/FluentLenium/issues) or contact us through the [mailing-list](https://groups.google.com/group/fluentlenium)

## FluentLenium and other frameworks

### jUnit
FluentLenium uses jUnit by default. You can use test using [jUnit](http://www.junit.org) assertions, but can of course use others frameworks such as [AssertJ](https://github.com/joel-costigliola/assertj-core) or [Hamcrest](http://code.google.com/p/hamcrest/).

```java
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertEqual("Hello toto",title());
```

### Fest-Assert
Fest-Assert is now deprecated. This lib is no longer maintained. Consider switching to AssertJ

### AssertJ
```java
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertThat(title()).isEqualTo("Hello toto");
assertThat(find(myCssSelector)).hasText("present text");
assertThat(find(myCssSelector)).hasNotText("not present text");
assertThat(find(myCssSelecto1)).hasSize(7);
assertThat(find(myCssSelecto2)).hasSize().lessThan(5);
assertThat(find(myCssSelecto2)).hasSize().lessThanOrEqualTo(5);
assertThat(find(myCssSelecto3)).hasSize().greaterThan(2);
assertThat(find(myCssSelecto3)).hasSize().greaterThanOrEqualTo(2);
```


### Hamcrest
```java
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertThat(title(),equalTo("Hello toto"));
```
##Resources

In English:

  - [Play2 and FluentLenium screencast](http://www.youtube.com/watch?v=diVhWRtJuxU)  and the associated [code] (http://ics-software-engineering.github.io/play-example-fluentlenium/)

In French:

  - [SlideShare](http://www.slideshare.net/MathildeLemee/fluentlenium)
  - [Cucumber and FluentLenium - more to come](http://blog.jetoile.fr/2013/04/fluentlenium-et-cucumber-jvm-complement.html)

Please contact us on the mailing list if you want your post to be added to that list !

### Built by CloudBees
<img src='http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png'/>
