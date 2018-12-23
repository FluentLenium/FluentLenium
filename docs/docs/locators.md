---
layout: page
title: Element locators
subtitle: FluentLenium
---

##  Fluent Locators

FluentLenium provides `$` and `el` methods to build **Fluent Locators**. Fluent Locators are implemented by ```FluentWebElement``` (`el`) and ```FluentList<FluentWebElement>``` (`$`).
Those classes are wrappers of Selenium ```WebElement``` and ```List<WebElement>```.

Element can be located by

  - [CSS Selector](#css-selector): Creates a Fluent Locator from CSS Selector.
  - [Selenium By Locator](#selenium-by-locator): Creates a Fluent Locator with native Selenium API.
  - [Filters](#filters): Add an additional filter to the Fluent Locator.
  - [First/Last/Index](#first-last-and-index): Get a single element from the Fluent Locator.
  - [XPath Axes](#xpath-axes): Get another Fluent Locator from its relative position in the DOM.
  - [JQuery syntax](#jquery-syntax): Sse find() method instead of el() and $()

Whenever element is find you can:
  - [Perform an action](#actions) (click, fill form etc)
  - [Get some information about it](#information) (value, clickability etc)

## CSS Selector

You can use CSS Selector with the same restrictions as in Selenium.

```java
$("#title") // Elements with id named "title"
$(".fluent") // Elements with class named "fluent"
$("input") // Elements with tag name "input"

// Most of CSS3 syntax is supported.
$("form > input[data-custom=selenium]")
```

## Selenium By Locator

You can also use native Selenium By Locator.

```java
$(By.cssSelector(".fluent"))
```

## Filters

You can use Filters to make your Fluent Selection easier to read and more powerful.

```java
// Required import to make filters available
import static org.fluentlenium.core.filter.FilterConstructor.*;
```

```java
$(".fluent", withName("foo"))
$(".fluent", withClass("foo"))
$(".fluent", withId("idOne"))
$(".fluent", withText("This field is mandatory."))
$(withId("idOne")) // Filter only
$(By.cssSelector(".header")) // Native Selenium `By` locator
$(".fluent", withName("foo"), withId("id1")) // Filter chaining
```

Available filter methods with descriptions are shown in the table below:

| Method | Description |
| ------ | ----------- |
| withName(String text) | Filters elements with 'name' attribute equals to value passed as an method argument |
| withId(String id) | Filters elements with 'id' attribute equals to value passed as an method argument |
| withClass(String cssClassName) | Filters elements with 'class' attribute equals to value passed as an method argument |
| withText(String text) | Filters elements with inner text that has to be displayed and equals to value passed as an method argument |
| containingText(String text) | Filters elements with inner text that has to be displayed and contains the value passed as an method argument |
| withTextContent(String text) | Filters elements with inner text that can be displayed or hidden and equals to value passed as an method argument |
| containingTextContent(String text) | Filters elements with inner text that can be displayed or hidden and contains the value passed as an method argument |

You can do more complex string matching on the above filters using the following methods:

  - `contains`
  - `containsWord`
  - `notContains`
  - `startsWith`
  - `notStartsWith`
  - `endsWith`
  - `notEndsWith`

```java
$(".fluent", withName().notContains("name"))
$(".fluent", withId().notStartsWith("id"))
$(".fluent", withText().endsWith("Female"))
$(".fluent", withName().contains(regex("na?me[0-9]*")))
$(".fluent", withName().notStartsWith(regex("na?me[0-9]*")))
```

`contains`, `startsWith` and `endsWith` with a regexp pattern look for a subsection of the pattern.

## First, Last and Index
If you want the first, last or a particular index element, just use:

```java
$(".fluent").first() // First element
el(".fluent") // First element (short form)
$(".fluent").last() // Last element
$(".fluent").index(2) // Third element
$(".fluent", withName("foo")).index(2) // Third element named "foo"
```

### Fluent Locator chains
You can also chain the Fluent Locators.

```java
// All the "input" tag name elements
// inside "fluent" class element subtree.
$(".fluent").$("input")

// The first "input" element named "bar"
// inside the third "fluent" class named "foo" element.
$(".fluent", withName("foo")).index(2).$("input", withName("bar")).first()
// or using el
el(".fluent", withName("foo")).index(2).$("input", withName("bar"))
```

## XPath Axes

If you need to build another Fluent Locator from the position in the DOM of an existing one, you
can use [XPath axes](https://www.w3schools.com/xml/xpath_axes.asp).

```java
$(".fluent"()).axes().parent()
$(".fluent"()).axes().descendants()
$(".fluent"()).axes().ancestors()
$(".fluent"()).axes().followings()
$(".fluent"()).axes().followingSiblings()
$(".fluent"()).axes().precedings()
$(".fluent"()).axes().precedingSiblings()
```

## JQuery syntax

If you don't like the [JQuery](http://jquery.com/) syntax, you can replace `$` and `el` with `find` method:

```java
goTo("http://mywebpage/");
find("#firstName").write("toto");
find("#create-button").click();
assertThat(window().title()).isEqualTo("Hello toto");
```

All syntaxes are equivalent. `$` is simply an alias for the `find()` method, and `el` for `find().first()`.

## Located Elements

## Actions
Fluent Locators have methods to interact with located elements:

```java
// click/double-click on all the enabled elements.
$("#create-button").click()
$("#create-button").doubleClick()

// Clear all the enabled fields
$("#firstname").clear()

// Submit all the enabled forms.
$("#account").submit()

// Place the mouse over the first found element
$("#create-button").mouseOver()

// Scroll the viewport to make the first found element visible
$("#create-button").scrollIntoView();
```

### Filling forms

A fill() builder method is available to quickly fill forms.

```java
$("input").fill().with("bar")
```

Previous statement will fill all the input elements with bar.

```java
$("input").fill().with("myLogin","myPassword")
```

Previous statement will fill the first element of the input selection with `myLogin`, the second with `myPassword`.
If there are more input elements found, the last value `myPassword` will be repeated for each subsequent element.

You can also fill `<select>` elements

```java
// Select "MONDAY" value
$("daySelector").fillSelect().withValue("MONDAY")

// Select second value
$("daySelector").fillSelect().withIndex(1)

// Select value with visible text "Monday"
$("daySelector").fillSelect().withText("Monday")
```

Don't forget that only visible fields will be modified. It simulates a real person using a browser !

## Information

You can also access a list of all the names, visible text, and ids of a list of elements:

```java
$(".fluent").names()
$(By.id("foo")).ids()
$(".fluent").values()
$(".fluent").attributes("myCustomAttribute")
$(".fluent").texts()
$(".fluent").textContents()
```

You can also check if the element is displayed, enabled or selected:

```java
el(".fluent").displayed()
el(".fluent").enabled()
el(".fluent").selected()
```

Advanced conditions are also available

```java
el(".fluent").conditions().clickable();
el(".fluent").conditions().rectangle().size().width().greaterThan(50);
```