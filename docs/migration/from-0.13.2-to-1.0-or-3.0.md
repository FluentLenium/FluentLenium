---
layout: page
title: Migration
subtitle: Guidelines to migrate from 0.13.2 to 1.0 or 3.0
---

FluentLenium 1.0 and 3.0 brings many new features but also comes with breaking changes.

If you have some tests written using previous version, reading this guideline should help you to migrate.

Maven
-----
Some changes are required in maven configuration.

- Replace `fluentlenium-core` dependency with `fluentlenium-junit`.
- Add dependency to Selenium drivers actually used.

Configuration
-------------
In previous version, overriding `getDefaultDriver()` method was the only way to define
the `WebDriver` to use. FluentLenium now provides configuration features including
`webDriver` and `capabilities` properties that should be enough for most use case.

If you still need to instantiate the WebDriver manually, or if configuration
features doesn't seem to integrates well in your environment, `getDefaultDriver()` has been renamed to `newWebDriver()`
and can still be overridden.

Keep in mind that events related features requires to wrap the `WebDriver` instance into `EventFiringWebDriver`.

- Use `@FluentConfiguration(driverLifecycle=...)` instead of `@SharedDriver(...)`.

- Rename `TriggerMode` enumeration values (ie: `ON_FAIL` => `AUTOMATIC_ON_FAIL`).

Search method names
-------------------
Some methods have been renamed and others have been removed to improve consistency and reduce codebase.

- Replace `findFirst()` occurrences with `el()`, `$(...).first()` or `find(...).first()`.
- Replace `$(..., index)` or `find(..., index)` occurrences with `$(...).index(index)` or `find(...).index(index)`.

Element method names
--------------------
- Many method prefixes like `is`, `has` and `get` have been removed from method names.
Methods like `isDisplayed`, `isEnabled`, `getValue()`, `getAttribute(name)` have been
renamed with their shorter form `displayed()`, `enabled()`, `value()`, `attribute(name)`.

- `text(...)` setter has been renamed in `write(...)` to avoid collision with `text()` getter;

- `fill().withValues()` has been renamed to `fill().withText()` to avoid confusion with `value()`
that retrieves the `value` attribute.

Conditions and await()
----------------------
`await()` now supports more conditions, provides better error message, and is easier to maintain. 

`await().untilEach()` match the condition when each element match the condition

`await().until()` match the condition when at least one element match the condition.

`isNotPresent()` has been removed, in favor of `not().present()`. `not()` method on condition builder
 allows to negate any condition.

You can also check the exact same conditions on elements or list of elements, using
`FluentWebElement#conditions()`, `FluentList#one()` and `FluentList#each()`

- Replace `await().until("some-selector")...` with `await().until($(some-selector))...` or
`$(some-selector).await().until()...`.

Pages and Components
--------------------

- Each method available in `FluentTest` is now available in both `FluentPage` and `FluentWebElement`, allowing 
implementation of any logic into Pages and Components.
- Ensure your Page Objects extends `FluentPage` and have a public empty constructor.
- Ensure your Components extends `FluentWebElement` and have a constructor matching `FluentWebElement` constructor.
- `createPage` method has been removed in favor of `newInstance`.

A Component is roughly the same thing as a Page Object. A Page Object is global, but a Component is local and attached
to an element so it can appear many time in a single page. A component can be created with
`FluentWebElement#as(Component.class)` / `FluentList#as(Component.class)`.

Ajax support
------------
FluentLenium now supports Ajax natively, using either injected elements or `$`/`el`/`find` search methods.
`@WaitHook` makes writing Ajax Webapp tests as simple as writing Static Website tests.

- `@AjaxElement` has been removed. 

Lazy Locators
-------------

When using search methods `find`, `$` or `el`, effective search is **NOT** performed at invocation time.
It's a major change in FluentLenium 1.0. Those methods now returns a **Lazy Locator** implementing 
`FluentWebElement` / `FluentList`. 

Those locators looks like elements from previous versions, but the search will be performed lazily, when invoking the
first action or check on the element.

So beware if you used to rely on catching `NoSuchElementException`: Those exceptions will now be
raised later in the test, or even not raised at all the element is not actively used.

You may use `FluentWebElement#now()` / `FluentList#now()` to force the search at any time.

Implicit waits
--------------
When using raw Selenium, explicit waits may spoil testing code and using implicit wait can be a solution.
But using implicit waits may hard to diagnose delays when running tests.
 
FluentLenium now advocates for never using implicit wait, in favor of using explicit waits only. FluentLenium hides the
complexity of explicit waits so them become readable when invoked manually, and can even automate them before each action with the
use of `@Wait` hook so you don't have to care about them.

- `withDefaultSearchWait` has been removed. Use `@Wait` instead.

Something wrong ?
------
You may [open issue](https://github.com/FluentLenium/FluentLenium/issues) if something wrong occurs during migration.
