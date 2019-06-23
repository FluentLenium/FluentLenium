---
layout: page
title: Key features
subtitle: FluentLenium
sidebar:
  Page Object Pattern: "#page-object-pattern-support"
  Lazy Fluent Locators: "#lazy-fluent-locators"
  Extended awaits: "#extended-awaits"
---

This section contains information about key FluentLenium features. If you wonder about advantages over pure Selenium this section is for you.

## Table of Contents
- [Page Object Pattern Support](#page-object-pattern-support)
  * [Creating Page Objects](#pages)
  * [Page Injection](#page-injection)
  * [Components support](#components)
- [Lazy Fluent Locators](#lazy-fluent-locators)
- [Extended awaits](#extended-awaits)

## Page Object Pattern support

### Pages
Selenium tests can easily become a mess. To avoid this, you should use the [Page Object Pattern](http://code.google.com/p/selenium/wiki/PageObjects).
Page Object Pattern will enclose all the plumbing relating to how pages interact with each other and how the user
interacts with the page, which makes tests a lot easier to read and to maintain.

Try to construct your Page thinking that it is better if you offer services from your page rather than just the internals of the page.
A Page Object can model the whole page or just a part of it.

To construct a Page, extend `FluentPage`.

You can define the URL of the page by overriding the `getUrl` method. Then, it's possible tu use the `goTo()` method in
your test code to set the current URL to the URL of the page.

You can also override isAt method, to run all the assertions necessary in order to ensure that you are on the right
page.

For example, if I choose that the title will be sufficient to know if I'm on the right page:

```java
public class MyPage extends FluentPage {
    @Override
    public String getUrl() {
        return "/app/my-page";
    }

    @Override
    public void isAt() {
        assertThat(window().title()).contains("Selenium");
    }
}
```

Instead of manually implementing `isAt` and `getUrl` method, you can use Selenium `@FindBy` annotation on the page
class, to define an Element Locator that should identify this page, and `@PageUrl` to define the URL
used by `goTo()` method.

```java
@PageUrl("/app/my-page")
@FindBy(css="#my-page")
public class MyPage extends FluentPage {
    ...
}
```

If you are creating a page object class for something that doesn't have URL, e.g. a component of a page, the class won't have the `@PageUrl` annotation applied,
thus also doesn't need to extend `FluentPage`, but the `@Find...` annotated fields in it will be initialized regardless when injecting them using `@Page`. The following construct is also valid:

```java
public class HeroImage {
    @FindBy(css = ".hero .img")
    public FluentWebElement heroImage; 
}
```

#### PageUrl parameters

It's possible to define parameters in FluentPage url using `{[?][/path/]parameter}` syntax.
If it starts with `?`, it means that the parameter is optional. Path can be included in the braces so it is removed when parameter value is not defined.

```java
@PageUrl("/document/{document}{?/page/page}{?/format}")
public class DocumentPage extends FluentPage {
    public DocumentPage customPageMethod(){
            ...
            return this;
        }
        ...
}
```

Also when a page is open, one might want to extract certain parameter values from the url template defined in the `@PageUrl` annotation.
One can do that by calling `getParam(String)` on a FluentPage object. It works only when the FluentPage object is annotated as `@PageUrl'.

So for a template like `/document/{document}{?/page/page}{?/format}` with an actual URL value `/document/345/json` one can query parameters like:

```java
String document = documentPage.getParam("document");
```

The queried value will be 345 in this case.

If the provided parameter name is not defined in the template or it has no value in the actual URL, the returned value is `null`.

#### Local files

You can also refer to files in your test resources directory by specifying the `file` attribute with a file name: 

```java
@PageUrl(file = "page2url.html", value = "?param1={param1}&param2={param2}")
class Page2DynamicP2P1 extends FluentPage {
    @Override
    protected void isAtUsingUrl(String urlTemplate) {
        //overridden to skip URL check because PageUrl is not able to get local file path relatively
    }
}
```

In case you don't specify the `file` attribute but you override either `FluentPage#getUrl()` or `FluentPage#getUrl(Object...)` in a way that it retrieves a local test resource,
you need to also override `FluentPage#isAtUsingUrl(String)` and leave its body empty to skip URL check because PageUrl is not able to get local file path relatively.
```java
@PageUrl(value = "?param1={param1}&param2={param2}")
class Page2DynamicP2P1 extends FluentPage {
    @Override
    protected String getUrl() {
         return someLocalResource;
    }

    @Override
    public void isAtUsingUrl(String urlTemplate) {
    }
}
```

#### Navigation and interaction on pages using parameters

Parameter values are given in order to `isAt` and `go` methods.

```java
@Page
private DocumentPage page;

@Test
public void test() {
    page.go(267); // go to "/document/267"
    page.go(174, 3); // go to "/document/174/page/3"
    page.go(124, 1, "pdf"); // go to "/document/124/page/1/pdf"
    page.go(124, null, "html"); // go to "/document/124/html"
    page.isAt(174, 3); // Assert that current url is "/document/174/page/3"
}
```

In order to be able to chain or return `Page` class which extends `FluentPage` as a result of `go()` method you need to
provide your `FluentPage` extending class name as follows.

The example above can be implemented as follows:

```java
@Page
private DocumentPage page;

@Test
public void test() {
    page.<DocumentPage>go(267) // go to "/document/267"
        .<DocumentPage>go(174, 3) // go to "/document/174/page/3"
        .<DocumentPage>go(124, 1, "pdf") // go to "/document/124/page/1/pdf"
        .<DocumentPage>go(124, null, "html") // go to "/document/124/html"
        .customPageMethod() //do the custom actions
        .<DocumentPage>go(267); // go to "document/267"
        ...
}
```

As a result the `go` method will return `DocumentPage` type instead of generic `FluentPage`

You can always override default `go` method in your `DocumentPage` class instead of typing `<DocumentPage>` for every
single method call

```java
@PageUrl("/document/{document}{?/page/page}{?/format}")
public class DocumentPage extends FluentPage {
    @Override
    DocumentPage go() {
        return super.go();
    }


    public DocumentPage customPageMethod(){
        ...
        return this;
    }
    ...
}
```

Create your own methods to easily fill out forms, go to another or whatever else may be needed in your test.

```java
public class LoginPage extends FluentPage {
    public String getUrl() {
        return "myCustomUrl";
    }
    public void isAt() {
        assertThat(window().title()).isEqualTo("MyTitle");
    }
    public void fillAndSubmitForm(String... paramsOrdered) {
        $("input").fill().with(paramsOrdered);
        $("#create-button").click();
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

Or using [AssertJ](https://github.com/joel-costigliola/assertj-core).

```java
public void checkLoginFailed() {
    goTo(loginPage);
    loginPage.fillAndSubmitLoginForm("login", "wrongPass");
    assertThat($(".error")).hasSize(1);
    assertThat(loginPage).isAt();
}
```

### Page Injection
You can use the annotation `@Page` to construct your Page Objects easily.

```java
public class AnnotationInitialization extends FluentTest {
    @Page
    private MyPage page;

    @Test
    public void test_no_exception() {
        goTo(page);
        //put your assertions here
    }
}
```

You can also use the factory method `newInstance`:

```java
public class BeforeInitialization extends FluentTest {
    private MyPage page;

    @Before
    public void beforeTest() {
        page = newInstance(MyPage.class);
    }

    @Test
    public void test_no_exception() {
        page.go();
    }
}
```

Anyway, Page Objects constructors should never be called directly.

All `FluentWebElement` fields are automatically searched for by name or id. For example, if you declare a
`FluentWebElement` named `createButton`, it will search the page for an element where `id` is `createButton` or
name is `createButton`.

Keep in mind that all elements are Lazy Proxy Locators, they behave exactly like if they where found with `$` method.

```java
public class LoginPage extends FluentPage {
    private FluentWebElement createButton;

    public String getUrl() {
       return "myCustomUrl";
    }

    public void isAt() {
       assertThat(window().title()).isEqualTo("MyTitle");
    }

    public void fillAndSubmitForm(String... paramsOrdered) {
        $("input").fill().with(paramsOrdered);
        createButton.click();
    }
}
```

Not only `FluentWebElement` fields are populated. Every type with a constructor taking a `WebElement` is a candidate.
This makes it possible for the page to expose fields with functional methods and not (only) the 'technical' methods
that `FluentWebElement` exposes.

```java
public class LoginPage extends FluentPage {
   private MyButton createButton;

   public void fillAndSubmitForm(String... paramsOrdered) {
       $("input").fill().with(paramsOrdered);
       createButton.clickTwice();
   }

   public static class MyButton {
       private WebElement webElement;

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
    private FluentWebElement createButton;

    public String getUrl() {
       return "myCustomUrl";
    }

    public void isAt() {
       assertThat(window().title()).isEqualTo("MyTitle");
    }

    public void fillAndSubmitForm(String... paramsOrdered) {
        $("input").fill().with(paramsOrdered);
        createButton.click();
    }
}
```

You can also refer to the list of FluentWebElements using `FluentList` dedicated interface,
or `java.util.List`.

```java
public class LoginPage extends FluentPage {
    @FindBy(css = "button.create-button")
    private FluentList<FluentWebElement> createButtons;

    public String getUrl() {
        return "myCustomUrl";
    }

    public void isAt() {
        assertThat(window().title()).isEqualTo("MyTitle");
        assertThat(buttons).hasSize(2);
    }

    public void fillAndSubmitForm(String... paramsOrdered) {
        $("input").fill().with(paramsOrdered);
        createButtons.get(1).click();
    }
}
```

Injection is recursive, and it's possible to retrieve the parent container using ```@Parent``` annotation.

### Labeling

Using the `@Label` and `@LabelHint` annotations you can define a custom `toString()` representation for `FluentWebElement`s and `FluentList`s.

`@Label` can be added with or without a custom value. If a value is not defined, it defaults to empty value.

The purpose of `@LabelHint` is to provide additional information (kind of tags) to the `toString()` representation of the object that is annotated with it. It may be used with or without the `@Label` annotation, they don't depend on each other.

Below you can find some examples of what labeling combinations will result in what toString() values.

##### Label

If no label value is provided it will use the class name and field name as the label.

**FluentWebElement + Label without value** 
```java
@FindBy(css = ".hero img")
@Label
private FluentWebElement heroImage;
```
will give the following toString: *FluentWaitMessageTest.heroImage (Lazy Element)*

**FluentList + Label without value**
```java
@FindBy(css = "img")
@Label("pictures")
private FluentList<FluentWebElement> images;
```
will give the following toString: *pictures ([])*

##### LabelHint
If the label value is defined the toString value will being with the value, and by specifying label hints, they will be list after the label value enclosed with [ and ]. 

**Label + single label hint** 
```java
@FindBy(css = ".teaser img")
@Label("teaser")
@LabelHint("img")
private FluentWebElement teaserImage;
```
will give the following toString: *teaser [img] (Lazy Element)*

**Label + multiple label hints**
```java
@FindBy(css = ".banner img")
@Label("banner")
@LabelHint({"ad", "img"})
private FluentList<FluentWebElement> bannerImages;
```
will give the following toString: *banner [ad, img] ([])*

### Components

A ```Component``` is an object wrapping a ```WebElement``` instance.

A ```Component``` supports Injection like a Page Object, but all searchs are performed in the local context of the wrapped element.

Using components improves readability of both Page Objects and Tests.

`FluentWebElement` is the default component class in FluentLenium, so you can implement you own custom component
by extending `FluentWebElement` to add custom logic.

```java
public class SelectComponent extends FluentWebElement {
    public SelectComponent(WebElement webElement, WebDriver driver, ComponentInstantiator instantiator) {
        super(webElement, driver, instantiator);
    }

    public void doSelect(String selection) {
        // Implement selection provided by this component.
    }

    public String getSelection() {
        // Return the selected value as text.
    }
}
```

Components are created automatically by injection,
or programmatically by calling `as(Class<?> componentClass)` method of ```FluentWebElement``` or ```FluentList```.

```java
SelectComponent comp = el("#some-select").as(SelectComponent.class);

comp.doSelect("Value to select");
assertThat(comp.getSelection()).isEquals("Value to select");
```

It's not mandatory to extend `FluentWebElement`, but a constructor with at least WebElement parameter is required.

```java
public class SelectComponent {
    private WebElement element;

    private SelectComponent(WebElement element) { // This constructor MUST exist ! But can be private.
        this.element = element;
    }
}
```

Simple & working Component example can be found in our dedicated [GitHub section](https://github.com/FluentLenium/FluentLenium/tree/develop/examples/quickstart-firefox/src/test/java/org/fluentlenium/examples/test/componentexample).

## Lazy Fluent Locators

`$` always returns Lazy Fluent Locators. Building a Locator doesn't perform the search immediately.
It will be searched later, just before it's really needed to interact with the located elements.
(ie: clicking, retrieving text value, send user input).

```java
// Creates the Fluent Locator, but doesn't perform the search
FluentList<FluentWebElement> fluentElements = $(".fluent");

// Perform the search and call click() on found elements
fluentElements.click();

// NoSuchElementException will be throw if no element is found.
```

You can control the state of those Lazy Fluent Locators.

```java
// Check if the element is present in DOM (boolean)
$(".fluent").present();

// Force the underlying search if it's not already loaded
// Throws NoSuchElementException if not found
$(".fluent").now();

// Forget the underlying search results.
// You can then reuse the same locator to perform the search again
$(".fluent").reset();

// Check if the underlying element has been loaded (boolean)
$(".fluent").loaded();
```

## Extended awaits

### Wait for an Ajax Element to be available

There are multiple ways to make your driver wait for the result of an asynchronous call.
FluentLenium provides a rich and fluent API in order to help you to handle AJAX calls.
If you want to wait for at most 2 seconds until the number of elements corresponding to the until criteria (here the class fluent) has the requested size:

```java
await().atMost(2, TimeUnit.SECONDS).until($(".fluent")).size(3);
```
The default wait is 5 seconds.

You can also use after `size()` : `greaterThan(int)`, `lessThan(int)`, `lessThanOrEqualTo(int)`, `greaterThanOrEqualTo(int)` , `equalTo(int)`, `notEqualTo(int)`

Many others conditions like `size(3)` are available, like `present()`, `displayed()`, `enabled()`, `text()`, `id()`, `name()`.

You can use `not()` function to negate any condition.

If you need to be more precise, you can also use filters and matchers in the search:

```java
await().atMost(2, TimeUnit.SECONDS).until($(".fluent", withText("myText"))).size(3);
await().atMost(2, TimeUnit.SECONDS).until($(".fluent", withText().startsWith("start"))).present();
```

Just use `startsWith`, `notStartsWith`, `endsWith`, `notEndsWith`, `contains`, `notContains`, `equalTo`, `containsWord`.

If you need to filter on a custom attribute name, this syntax will help:

```java
await().atMost(2, TimeUnit.SECONDS).until($(".fluent", with("myAttribute").startsWith("myValue"))).present();
```

You can also give instance of elements or list of elements if required.

```java
@FindBy(css = ".button")
private FluentWebElement button;

await().atMost(2, TimeUnit.SECONDS).until(element).enabled();
```

When running Java 8, you can use lambdas with `until`, `untilPredicate`, `untilElement` or `untilElements`.

```java
await().atMost(2, TimeUnit.SECONDS).untilElement(() -> el(".button").enabled());
await().atMost(2, TimeUnit.SECONDS).untilElements(() -> $(".button").each().enabled());
await().atMost(2, TimeUnit.SECONDS).untilElements(() -> $(".button").one().enabled());

await().atMost(2, TimeUnit.SECONDS).untilPredicate((f) -> el(".button").enabled());
```

Using `Supplier` as `until` argument will help you to write `awaits` which can work with dynamic pages.

```java
await().atMost(2, TimeUnit.SECONDS).until(() -> el(".button").enabled());
await().until(() -> $(".listItem").first().displayed());
```

You can also check if the page is loaded.

```java
await().atMost(1, NANOSECONDS).untilPage().loaded();
```

If you want to wait until the page you want is the page that you are at, you can use:

```java
await().atMost(2, TimeUnit.SECONDS).untilPage(myPage).isAt();
```

You can override `await()` method in your own test class to define default settings for wait objects:

```java
@Override
public FluentWait await() {
    return super.await().atMost(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, ElementNotVisibleException.class);
}
```

### Polling Every
You can also define the polling frequency, for example, if you want to poll every 5 seconds:

```java
await().pollingEvery(5, TimeUnit.SECONDS).until($(".fluent", with("myAttribute").startsWith("myValue"))).present();
```
The default value is 500ms.

You can also chain filter in the asynchronous API:

```java
await().atMost(2, TimeUnit.SECONDS).until($(".fluent", with("myAttribute").startsWith("myValue"), with("a second attribute").equalTo("my@ndValue"))).present();
```

### @Wait Hook

```Wait``` hook automatically waits for conditions to be applied before interacting with Elements, avoiding the need
of writing technical waiting and condition code in tests and page objects.