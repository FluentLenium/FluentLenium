# What is FluentLenium ?

FluentLenium is a framework that helps you writing [Selenium](http://seleniumhq.org/) tests.
FluentLenium provides you a [fluent interface](http://en.wikipedia.org/wiki/Fluent_interface) to the [Selenium Web Driver](http://seleniumhq.org/docs/03_webdriver.html).
FluentLenium let you use the assertion framework you like, either [jUnit assertions](http://www.junit.org/apidocs/org/junit/Assert.html), [Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial) 
or [Fest-assert](http://docs.codehaus.org/display/FEST/Fluent+Assertions+Module).


# 5 seconds example
```java
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

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

To add FluentLenium to your project, just add the following dependency into your `pom.xml`:

```xml 
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-core</artifactId>
    <version>0.6.0</version>
    <scope>test</scope>
</dependency>
```

By default, FluentLenium provide a jUnit adapter.

If you need the fest-assert dependency to improve the lisibility of your test code :

```xml 
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-festassert</artifactId>
    <version>0.6.0</version>
    <scope>test</scope>
</dependency>
```

An adapter have been built to use FluentLenium with TestNG :
If you need the fest-assert dependency to improve the lisibility of your test code :

```xml
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-testng</artifactId>
    <version>0.6.0</version>
    <scope>test</scope>
</dependency>
```

Just extends `org.fluentlenium.adapter.FluentTestNg` instead of `org.fluentlenium.adapter.FluentTest`.

##Static imports

If you need to do some filtering :

```java
import static org.fluentlenium.core.filter.FilterConstructor.*;
```

### Static import using fest assert
The static assertions to use fest assert

```java
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
```

### Basic Methods
You can use `url()` , `title()` or `pageSource()` to get the url, the title or the page source of the current page.

###  Selector
#### Default Selector
You can use CSS1, CSS2 and CSS3 selector with the same restrictions as in Selenium.

If you want to find the list of elements which have

  - the `id` "title" : `find("#title")`
  - the `class` name "small" : `find(".small")`
  - the `tag` name "input" : `find("input")`

You are free to use most of the CSS3 syntax, wich means that
`find("input[class=rightForm]")`
will return the list of all input elements which have the class rightForm

#### Custom filter
But what if you want all the input that have a text equals to "Sam" ?
You can use filters to allow that kind of search. For example :

```java
find(".small", withName("foo"))
find(".small", withId("idOne"))
find(".small", withText("This field is mandatory."))
```

You can also chained filters :
`find(".small", withName("foo"), withId("id1"))` will return all the elements matching the 3 criterias.

If you want others precisions that just the css selector, just use our filters features.
For now, you have 6 differents filters :

  - `contains`
  - `notContains`
  - `startsWith`
  - `notStartsWith`
  - `endsWith`
  - `notEndsWith`

For each of them, you can choose to use a css selector :

```java
find(".small", withName().notContains("name")
find(".small", withId().notStartsWith("id")
find(".small", withText().endsWith("Female"))
```

Or to be more precise, you can choose to use a regexp :

```java
find(".small", withName().contains(regex("na?me[0-9]*"))
find(".small", withName().notStartsWith(regex("na?me[0-9]*"))
```

Contains, startsWith and endsWith with a regexp pattern are looking for a subsect of the pattern.

Of course, if you are a regexp jedi, that's not needed !

More will come soon to filter to create a complete search tool (containsWord, able to choose if you want to ignore case ...)


### N-th
If you want the first elements that matchs your criteria, just use :

```java
findFirst(myCssSelector)
```

or alternatively

```java
find(myCssSelector).first()
```

If you want the element at the given position :

```java
find(myCssSelector, 2)
```

Of course, you can use both position filter and custom filter :

```java
find(myCssSelector, 2, withName("foo"))
```


#### Find on children
You can also chained the find call :
`find(myCssSelector).find("input")` will return all the web element input into the css selector tree.
You can add more indication :

```java
find(myCssSelector, 2, withName("foo")).find("input", withName("bar"))
```

or

```java
find(myCssSelector, 2, withName("foo")).findFirst("input", withName("bar"))
```

## Form Action
If you need to click, fill, submit or clean an element or a list of element, just go naturally for it.

### Fill
`fill("input").with("bar")` or `find("input").text("bar")` will fill all the element with tag input with bar. 
If you want for example exclude the checkbox, you can use the css filtering like `fill("input:not([type='checkbox'])").with("tomato")`, 
you can also use the filtering provided by FluentLenium `fill("input", with("type", notContains("checkbox"))).with("tomato")`


`fill("input").with("myLogin","myPassword")` will fill the first elements of the input selection with myLogin, the second with myPassword. 
If there are a third input, the last value (myPassword) will be repeat again and again.

Don't forget, only the visible field will be modified. It simulates your action in a browser !

### Click
```java
click("#create-button")
```

It will click on all the visible fields returned by the search.

### Clear
```java
clear("#firstname")
```

It will clear  all the visible fields returned by the search.

### Submit
```java
submit("#account")
```

It will submit all the visible fields returned by the search.


## Page Object pattern
Because Selenium test can easily become a mess, [Page Object Pattern](http://code.google.com/p/selenium/wiki/PageObjects) when writing automated integration test.
Page Pattern will inclosing all the plumbing, which make tests a lot easier to read and to maintain.

Try to construct your Page thinking that it is better if you offer services from your page rather that just the internals of the page.
A Page Object can modelize the whole page or just a part of it.

To construct a Page, it have to extends [org.fluentlenium.core.FluentPage](https://github.com/FluentLenium/FluentLenium/blob/master/fluentlenium-core/src/main/java/org/fluentlenium/core/FluentPage.java).
In most of the cases, you have to defined the url of the page with overriding the `getUrl` methods.
In that way, you can go in your test to that page with `goTo(myPage)`

To control that you are in the good page, not only the url [accessible in your test via the void url() method] can be needed.
Redefined the `isAt` methods to list all the assertions you have to make in order to be sure that you are in the good pages.
For example, if I choose that the title will be sufficient to know if I'm in the page :

```java
@Override
public void isAt() {
    assertThat(title()).contains("Selenium");
}
```

Create you own methods to easily fill form, go to a next page or what else can be needed in your test.

For example :

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

And the corresponding test :

```java
public void checkLoginFailed() {
	goTo(loginPage);
	loginPage.fillAndSubmitLoginForm("login", "wrongPass");
	loginPage.isAt();
}
```

Or if you have the [Fest-assert](http://docs.codehaus.org/display/FEST/Fluent+Assertions+Module) module (just make a static import org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat)

```java
public void checkLoginFailed() {
	goTo(loginPage);
	loginPage.fillAndSubmitLoginForm("login","wrongPass");
	assertThat(find(".error")).hasSize(1);
	assertThat(loginPage).isAt();
}
```

###Page usage
You can use the annotation `@Page` to define your page easily.

For example :

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

You can also used the factory createPage

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

Into a page, all FluentWebElement are automatically searched by name or id. For example, if you declare a FluentWebElement named `createButton`, 
it will look into the page to a element where `id` is `createButton` or name is `createButton`. 
All elements are proxified which means that the search is really done when you try to access the element.

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
If you need to wait for an element to be present, especially when on an ajax call, you can use the @AjaxElement annotation on the fields :

```java
public class LoginPage extends FluentPage {
   @AjaxElement
   FluentWebElement myAjaxElement;
}
```
You can set the timeout in seconds for the page to throw an error if not found with @AjaxElemet(timeountOnSeconds=3) if you want to wait 3 seconds.
By default, the timeout is set to one seconds.


## Wait for an Ajax Call

You can have multiple way to make your driver wait for the result of an asynchronous call.
FluentLenium provides a rich and fluent API in order to help you to handle AJAX call.
If you want to wait for at most 5 seconds until the number of element corresponding to the until criteria (here the class small) has the requested size.


```java
await().atMost(5, TimeUnit.SECONDS).until(".small").hasSize(3);
```
The default wait is 500 ms.

Instead of hasSize, you can also use `hasText("myTextValue")`, `hasId("myId")`, `hasName("myName")`, `containsText("myName")`.
The `isPresent()` assertion is going to check if there is at most one element on the page corresponding to the filter.

If you need to be more precise, you can also use filter on the search :

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").withText("myText").hasSize(3);
```
You can also use after hasSize() : 'greaterThan(int)', 'lessThan(int)', 'lessThanOrEqualTo(int)', 'greaterThanOrEqualTo(int)' , 'equalTo(int)', 'notEqualTo(int)'

You can use `withText("myText")` but also with the same signature withName , `withId("myId")`
You can also use matcher :

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").withText().startsWith("start").isPresent();
```
     
Just use `startsWith`, `notStartsWith`, `endsWith`, `notEndsWith`, `contains`, `notContains`, `equalTo`.

If you need to filter on a custom attribute name, this syntax will help :

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").isPresent();
```

### Polling Every
You can also defined the polling frequency, for example, if you want to pull every 5 seconds :
 ```java
await().pollingEvery(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").isPresent();
```
The default value is 500ms.

You can also chain filter in the asynchronous API :

```java
await().atMost(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").with("a second attribute").equalTo("my@ndValue").isPresent();
```
## Alternative Syntax

If you are more convenient to the [JQuery](http://jquery.com/) syntax, maybe something like that will be more natural for you:

```java
goTo("http://mywebpage/");
$("#firstName").text("toto");
$("#create-button").click();
assertThat(title()).isEqualTo("Hello toto");
```

Both syntax are equivalent. Both `$` and `find` methods are aliases.


## Execute javascript
If you need to execute some javascript, just call `executeScript` with your script as parameter.
For example, if you have a javascript method called change and you want to call them just add this in your test :

```java
executeScript("change();");
```

## Taking Snapshots
You can take a snaphost of the browser
```java
driver.takeScreenShot();
```
The file will be name by the current timestamp.
You can of course specify a path and a name using :
```java
driver.takeScreenShot(pathAndfileName);
```

## Customize FluentLenium

### Driver
If you need to change your driver, just override the `getDefaultDriver` in your test. You can use every driver

### TimeOut
Just override `getDefaultWait` in your test.

## FluentLenium and others framework
### jUnit
FluentLenium used jUnit by default. You can use test using [jUnit](http://www.junit.org) assertions, but can of course use others frameworks, 
more fluent, as [Fluent-assert](http://code.google.com/p/fluent-assert/) or [Hamcrest](http://code.google.com/p/hamcrest/).

```java
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertEqual("Hello toto",title());
```

### Fest-Assert
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

### Built by CloudBees
<img src='http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png'/>