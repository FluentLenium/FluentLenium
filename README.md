FluentLenium is a framework which provide help when writting Selenium Web Driver test with jUnit.
You can use the framework of assertion you want, classical jUnit assertions, hamcrest or as in the following examples fest-assert.


## Cross Browser
FluentLenium can support all the browser available in Selenium WebDriver.
Wich means :
    * FireFox
    * Internet Explorer
    * Google Chrome
    * Opera

But there is also some project working on android etc ...

## Basic

Launching the first test.
Your Test Class may extend fr.javafreelance.fluentlenium.core.test.FluentTest By default, it use FirefoxWebDriver to launch all tests.
After that, all your test methods will be able to be tested into a browser.
The syntaxes offers directly by Selenium are quite a bit too verbose. FluentLenium proposes two differents syntaxes to easily launch tests.
For examples, if you want to go to an adresse, fill a form and click to a button to submit and tests that the title of the page have changed
``` java
goTo("http://mywebpage/");
fill("#firstName").with("toto");
click("#create-button");
assertThat(title()).isEqualTo("Hello toto");
```

## Maven

To add FluentLenium to your project, just add the following dependency into your pom.xml :
``` java
&lt;dependencies&gt;
    ...
    &lt;dependency&gt;
        &lt;groupId&gt;fr.javafreelance.fluentlenium&lt;/groupId&gt;
        &lt;artifactId&gt;fluentlenium&lt;/artifactId&gt;
        &lt;version&gt;0.3&lt;/version&gt;
    &lt;/dependency&gt;
    ...
&lt;/dependencies&gt;
 ```

This dependency include the core of the framework and a fest-assert tool. If you don't need the fest-assert tool, you can grab only the core :
``` java
 &lt;dependencies&gt;
     ...
     &lt;dependency&gt;
         &lt;groupId&gt;fr.javafreelance.fluentlenium&lt;/groupId&gt;
         &lt;artifactId&gt;fluentlenium-core&lt;/artifactId&gt;
         &lt;version&gt;0.3&lt;/version&gt;
     &lt;/dependency&gt;
     ...
 &lt;/dependencies&gt;
```



##Static imports

If you need to do some filtering :
```java
import static fr.javafreelance.fluentlenium.core.filter.FilterConstructor.*;
```
### Static import using fest assert
The static assertions to use fest assert
```java
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
```

### Basic Methods
You can use url() , title() or pageSource() to get the url, the title or the page source of the current page.

###  Selector
#### Default Selector
You can use CSS1, CSS2 and CSS3 selector with the same restrictions as in Selenium.

     If you want to find the list of elements which have
- the id "title" : find("#title")
- the class name "small" : find(".small")
- the tag name "input" : find("input")

You are free to use most of the CSS3 syntax, wich means that
find("input[class=rightForm]")
will return the list of all input elements which have the class rightForm

#### Custom filter
But what if you want all the input that have a text equals to "Sam" ?
You can use filters to allow that kind of search. For example :
```java
     find(".small",withName("foo"))
     find(".small",withId("idOne"))
     find(".small",withText("This field is mandatory."))
```
You can also chained filters :
find(".small",withName("foo"),withId("id1")) will return all the elements matching the 3 criterias : - class .small ,id id1 and name foo.

If you want others precisions that just the css selector, just use our filters features.
For now, you have 6 differents filters :
- contains
- notContains
- startsWith
- notStartsWith
- endsWith
- notEndsWith

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
     findFirst(myCssSelector) or find(myCssSelector).first()
```

If you want the element at the given position :
```java
     find(myCssSelector,2)
```

Of course, you can use both position filter and custom filter :
```java
     find(myCssSelector,2,withName("foo"))
```


#### Find on children
     You can also chained the find call :
     find(myCssSelector).find("input") will return all the web element input into the css selector tree.
     You can add more indication :
```java
     find(myCssSelector,2,withName("foo")).find("input",withName("bar"))
     or find(myCssSelector,2,withName("foo")).findFirst("input",withName("bar"))
```
## Form Action
If you need to click, fill, submit or clean an element or a list of element, just go naturally for it.

### Fill
fill("input").with("bar") OR find("input").text("bar") will fill all the element with tag input with bar. If you want for example exclude the checkbox, you can use the css filtering like         fill("input:not([type='checkbox'])").with("tototo"), you can also use the filtering provided by FluentLenium fill("input", with("type", notContains("checkbox"))).with("tototo")


fill("input").with("myLogin","myPassword") will fill the first elements of the input selection with myLogin, the second with myPassword. If there are a third input, the last value (myPassword) will be repeat again and again.

Don't forget, only the visible field will be modified. It simulates your action in a browser !

#### Click
click("#create-button")

It will click on all the visible fields returned by the search.

#### Clear
clear("#create-button")

It will clear  all the visible fields returned by the search.

#### Submit
submit("#create-button")

It will submit all the visible fields returned by the search.

//TODO Add more infos there


## Page Object pattern
Because Selenium test can easily become a mess, Page Object Pattern are a recommanded Pattern when writing automatised integration test.
Page Pattern will inclosing all the pumbling, which make tests a lot easier to read and to maintain.

Try to construct your Page thinking that it is better if you offert services from your page rather that just the internals of the page.
A Page Object can modelized the whole page or just a part of it.

To construct a Page, it have to extends fr.javafreelance.fluentlenium.core.FluentPage.
In most of the cases, you have to defined the url of the page with overriding the getUrl methods.
In that way, you can go in your test to that page with goTo(myPage)

To control that you are in the good page, not only the url [accessible in your test via the void url() method] can be needed.
Redefined the isAt methods to list all the assertions you have to make in order to be sure that you are in the good pages.
For exemple, if I choose that the title will be sufficient to know if I'm in the page :

```java
        @Override
        public void isAt() {
            assertThat(title()).contains("Selenium");
        }
```

Create you owm methods to easily fill form, go to a next page or what else can be needed in your test.

For exemple :
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
 loginPage.fillAndSubmitLoginForm("login","wrongPass");
 loginPage.isAt();
}
```
   Or if you have the fest assert module (just made a static import org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat)

    ```java
    public void checkLoginFailed() {
     goTo(loginPage);
     loginPage.fillAndSubmitLoginForm("login","wrongPass");
      assertThat(find(".error")).hasSize(1);
      assertThat(loginPage).isAt();
    }
    ```

###Page usage
You can use the annotation @Page to define your page easily.

For example :
```java
 public class AnnotationInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

    @Page
    public TestPage2 page2;


    @Test
    public void test_no_exception() {
        goTo(page2);
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
        public TestPage2 page;

         @Before
         public void beforeTest() {
              page  = createPage(TestPage2.class);
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

Into a page, all FluentWebElement are automatically searched by name or id. For exemple, if you declare un FluentWebElement named createButton, it will look into the page to a element where id is createButton or name is createButton. All elements are proxified which means that the search is really done when you try to access the element.

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
## Wait for an Ajax Call
    You can have multiple way to make your driver wait for the result of an asynchronous call.
    FluentLenium provides a rich and fluent API in order to help you to handle AJAX call.
    If you want to wait for at most 5 seconds until the number of element corresponding to the until criteria (here the class small) has the requested size.


```java
     await().atMost(5, TimeUnit.SECONDS).until(".small").hasSize(3);
```

   Instead of hasSize, you can also use hasText("myTextValue") , hasId("myId"), hasName("myName") . isPresent() are going to check if there is at most one element on the page corresponding to the filter.

    If you need to be more precise, you can also use filter on the search :

```java
     await().atMost(5, TimeUnit.SECONDS).until(".small").withText("myText").hasSize(3);
```     You can use withText("myText") but also with the same signature withName , withId

     You can also use matcher :

```java
     await().atMost(5, TimeUnit.SECONDS).until(".small").withText().startsWith("start").isPresent();
```
     Just use startsWith, notStartsWith , endsWith, notEndsWith , contains , notContains, equalTo.

     If you need to filter on a custom attribute name, this syntax will help :

```java
         await().atMost(5, TimeUnit.SECONDS).until(".small").with("myAttribute").startsWith("myValue").isPresent();
```
## Alternative Syntax

If you are more convenient to the JQuery Syntax, maybe something like that will be more natural for you:
```java
goTo("http://mywebpage/");
$("#firstName").text("toto");
$("#create-button").click();
assertThat(title()).isEqualTo("Hello toto");

```
Both syntax are equivalent. $ or find methods are aliases.


## Execute javascript
If you need to execute some javascript, just call executeScript with your script as parameter.
For example, if you have a javascript method called change and you want to call them just add this in your test :
```java
         executeScript("change();");
```

## Customize FluentLenium
###Driver
If you need to change your driver, just override the getDefaultDriver in your test. You can use every driver
###TimeOut
Just override getDefaultWait in your test.

## FluentLenium and others framework
### jUnit
FluentLenium used jUnit by default. You can use test using jUnit assertions, but can of course use others frameworks, more fluent, as Fluent-assert or Hamcrest.

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
```

### Hamcrest
```java
 goTo("http://mywebpage/");
 fill("#firstName").with("toto");
 click("#create-button");
 assertThat(title(),equalTo("Hello toto"));
```

### Full example using Fest-Assert assertion framework
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


### Built by CloudBees
<img src='http://web-static-cloudfront.s3.amazonaws.com/images/badges/BuiltOnDEV.png'/>