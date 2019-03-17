# Changelog

## Version 4.x - Java 11 support

### 4.2.2 (Mar 17 2019)
- [#345](https://github.com/FluentLenium/FluentLenium/issues/345) - Added FluentPage assertions to fluentlenium-assertj module
- [#336](https://github.com/FluentLenium/FluentLenium/issues/336) - Added warning log when user is using wrong version of Selenium
- [#456](https://github.com/FluentLenium/FluentLenium/issues/456) - Added .waitAndClick() helper method for FluentWebElement
- [#696](https://github.com/FluentLenium/FluentLenium/issues/696) - Add constructor accepting FluentWebElement to ElementActions and add missing moveByOffset method
- [#704](https://github.com/FluentLenium/FluentLenium/issues/704) - Add isPresent() and isNotPresent() assertions into Fluent AssertJ module
- [#757](https://github.com/FluentLenium/FluentLenium/issues/757) - Add Opera to DefaultWebDriverFactories
- [#707](https://github.com/FluentLenium/FluentLenium/issues/707) - Junit 5 bump to 5.4.0
- [#723](https://github.com/FluentLenium/FluentLenium/pull/723) - Major Spock and Groovy upgrade
- [#686](https://github.com/FluentLenium/FluentLenium/issues/686) - Major Cucumber upgrade  
* Multiple small dependencies bumps
* Multiple documentation upgrades
* Added slf4j-api to each module
- [#767](https://github.com/FluentLenium/FluentLenium/issues/767) - Remove isLocalFile attribute of PageUrl (API improvement)
- [#595](https://github.com/FluentLenium/FluentLenium/issues/595) - Bugfix: Awaits() with not() method does not work as expected
- [#602](https://github.com/FluentLenium/FluentLenium/issues/602) - Bugfix: Multiple await() fixes - reseting components list does not work as expected, enforced element refresh when called by .get()
- [#698](https://github.com/FluentLenium/FluentLenium/issues/698) - Bugfix: isAt() assertion throw exception even url matches

### 4.1.1 (Jan 30 2019)
- [#690](https://github.com/FluentLenium/FluentLenium/issues/690) - Add method clearing React input
- [#654](https://github.com/FluentLenium/FluentLenium/issues/654) - Add more assertions to AssertJ module
- [#664](https://github.com/FluentLenium/FluentLenium/issues/664) - UrlTemplate#put(Map) doesn't set properties
- [#655](https://github.com/FluentLenium/FluentLenium/issues/655) - Bug: @org.fluentlenium.adapter.junit.After is not executed on test success (JUnit)
- [#626](https://github.com/FluentLenium/FluentLenium/issues/626) - Delombok
- [#628](https://github.com/FluentLenium/FluentLenium/issues/628) - Improvement: Allow DragAndDropBy to accept a target element as a parameter


### 4.0.0 (Nov 29 2018)
- [#631](https://github.com/FluentLenium/FluentLenium/issues/) - Introduce Java 11
- Fix for Chrome bug - redirects to 3rd party page stop working when user opens it in the new window or popup

## Version 3.x - Java 8 support
### 3.8.1 ((Mar 17 2019))
- Backported 4.0.0, 4.1.1 and 4.2.2 into Java 8
- Fix for Chrome bug - redirects to 3rd party page stop working when user opens it in the new window or popup
- [#690](https://github.com/FluentLenium/FluentLenium/issues/690) - Add method clearing React input
- [#654](https://github.com/FluentLenium/FluentLenium/issues/654) - Add more assertions to AssertJ module
- [#664](https://github.com/FluentLenium/FluentLenium/issues/664) - UrlTemplate#put(Map) doesn't set properties
- [#655](https://github.com/FluentLenium/FluentLenium/issues/655) - Bug: @org.fluentlenium.adapter.junit.After is not executed on test success (JUnit)
- [#626](https://github.com/FluentLenium/FluentLenium/issues/626) - Delombok
- [#628](https://github.com/FluentLenium/FluentLenium/issues/628) - Improvement: Allow DragAndDropBy to accept a target element as a parameter
- [#345](https://github.com/FluentLenium/FluentLenium/issues/345) - Added FluentPage assertions to fluentlenium-assertj module
- [#336](https://github.com/FluentLenium/FluentLenium/issues/336) - Added warning log when user is using wrong version of Selenium
- [#456](https://github.com/FluentLenium/FluentLenium/issues/456) - Added .waitAndClick() helper method for FluentWebElement
- [#696](https://github.com/FluentLenium/FluentLenium/issues/696) - Add constructor accepting FluentWebElement to ElementActions and add missing moveByOffset method
- [#704](https://github.com/FluentLenium/FluentLenium/issues/704) - Add isPresent() and isNotPresent() assertions into Fluent AssertJ module
- [#757](https://github.com/FluentLenium/FluentLenium/issues/757) - Add Opera to DefaultWebDriverFactories
- [#707](https://github.com/FluentLenium/FluentLenium/issues/707) - Junit 5 bump to 5.4.0
- [#723](https://github.com/FluentLenium/FluentLenium/pull/723) - Major Spock and Groovy upgrade
- [#686](https://github.com/FluentLenium/FluentLenium/issues/686) - Major Cucumber upgrade  
* Multiple small dependencies bumps
* Multiple documentation upgrades
* Added slf4j-api to each module
- [#767](https://github.com/FluentLenium/FluentLenium/issues/767) - Remove isLocalFile attribute of PageUrl (API improvement)
- [#595](https://github.com/FluentLenium/FluentLenium/issues/595) - Bugfix: Awaits() with not() method does not work as expected
- [#602](https://github.com/FluentLenium/FluentLenium/issues/602) - Bugfix: Multiple await() fixes - reseting components list does not work as expected, enforced element refresh when called by .get()
- [#698](https://github.com/FluentLenium/FluentLenium/issues/698) - Bugfix: isAt() assertion throw exception even url matches

### 3.7.1 (Nov 23 2018)
- [#632](https://github.com/FluentLenium/FluentLenium/issues/632) - Selenium bumpup 3.141.59
- [#630](https://github.com/FluentLenium/FluentLenium/issues/630) - Upgrade JUnit 5 to latest stable

### 3.7.0 (Sep 7 2018)
Bug fixes:
- [#491](https://github.com/FluentLenium/FluentLenium/issues/491) - IllegalArgumentException: object is not an instance of declaring class
- [#593](https://github.com/FluentLenium/FluentLenium/issues/593) - Await for lists seems to not working as expecting
- [#600](https://github.com/FluentLenium/FluentLenium/issues/600) - Set default timeout and polling interval for Wait hook annotation

Features:
- [#611](https://github.com/FluentLenium/FluentLenium/issues/611) Update Cucumber to 3.0.2
- [#612](https://github.com/FluentLenium/FluentLenium/issues/612) Change handling Before and After from Cucumber
- [#616](https://github.com/FluentLenium/FluentLenium/issues/616) Selenium 3.14.0 upgrade

Documentation:
- [#620](https://github.com/FluentLenium/FluentLenium/issues/620) - Add documentation for FluentCucumber new way of integration
- [#588](https://github.com/FluentLenium/FluentLenium/issues/588) - Add containingText(...) to docs
- [#601](https://github.com/FluentLenium/FluentLenium/issues/601) - Bad default timeout in documentation

### 3.6.1 (Jul 2 2018)
- Problem with RemoteWebDriver on version 3.6.0 ([#591](https://github.com/FluentLenium/FluentLenium/issues/))

### 3.6.0 (Jun 27 2018)
- Selenium upgrade to 3.12.0
- Nullpointer is thrown during html dump [#571](https://github.com/FluentLenium/FluentLenium/issues/571)
- Bump-up versions of dependencies [#577](https://github.com/FluentLenium/FluentLenium/issues/577)
- Spock - Can't create new WebDriver instance [#569](https://github.com/FluentLenium/FluentLenium/issues/569)
- Support screenshot for alert [#455](https://github.com/FluentLenium/FluentLenium/issues/455)
- FluentLenium cucumber upgrade [#318](https://github.com/FluentLenium/FluentLenium/issues/318)
- Cannot separate Hooks class [#291](https://github.com/FluentLenium/FluentLenium/issues/291)
- JUnit Jupiter (JUnit 5) adapter [#276](https://github.com/FluentLenium/FluentLenium/issues/276)

### 3.5.2 (Feb 14 2018)
- Selenium 3.9.1 support added

### 3.5.1 (Feb 7 2018)
- Selenium upgrade to 3.9.0

### 3.5.0 (Feb 7 2018)
- Selenium 3.8.1 support
- Change axes() to more descriptive name [#544](https://github.com/FluentLenium/FluentLenium/issues/544)
- Ambiguous FluentList / FluentWebElement [#536](https://github.com/FluentLenium/FluentLenium/issues/536)
- Local PageUrl is not working for relatives paths [#535](https://github.com/FluentLenium/FluentLenium/issues/535)
- public method typo fix getAttribut became getAttribute in AttributeFilter class

### 3.4.1 (Oct 18 2017)
- await().until(FluentList).displayed() does not work ([#541](https://github.com/FluentLenium/FluentLenium/issues/541)) - documentation update + tests added
- Self referencing bound FluentPage changes in release 3.4.0 ([#546](https://github.com/FluentLenium/FluentLenium/issues/546))

### 3.4.0 (Sep 29 2017)
- Selenium upgrade to 3.5.3 ([#530](https://github.com/FluentLenium/FluentLenium/issues/530))
- Go is void but it may be FluentPage ([#466](https://github.com/FluentLenium/FluentLenium/issues/466))
- Headless chrome config - docs updated ([#516](https://github.com/FluentLenium/FluentLenium/issues/516))
- await() doesn't find a new element in a list of FluentWebElement's bug ([#489](https://github.com/FluentLenium/FluentLenium/issues/489))

### 3.3.0 (Jul 25 2017)
- Obtain browser session lock - timeout ([#520](https://github.com/FluentLenium/FluentLenium/issues/520))
- Multi threading support on method level (TestNG can be instrumented by annotation) ([#518](https://github.com/FluentLenium/FluentLenium/issues/518), [#508](https://github.com/FluentLenium/FluentLenium/issues/508))
- FluentLenium E2E tests are executed on BrowserStack now ([#363](https://github.com/FluentLenium/FluentLenium/issues/363))

### 3.2.0 (Apr 18 2017)
- FluentLenium AssertJ support -> assertThat method inherits from org.assertj.core.api.Assertions so you will be able to use all methods in one place ([#472](https://github.com/FluentLenium/FluentLenium/issues/472))
- isAt() does not accept parameters ([#494](https://github.com/FluentLenium/FluentLenium/issues/494))
- Documentation update
- Spock support ([#467](https://github.com/FluentLenium/FluentLenium/issues/467))
- HTMLUnit (v2.26) upgrade
- Selenium upgrade to 3.3.1

### 3.1.1 (Jan 12 2017)
#### Fixes
- fluentlenium.properties values set for driveLifecycle is not loaded ([#461](https://github.com/FluentLenium/FluentLenium/issues/))
- Selenium WebDriver version update (3.0.1)
- fluentlenium.properties values are case insensitive now

### 3.1.0 (Dec 18 2016)
#### Fixes
- Makes annotation events priority consistent between Component and Test/Page ([#440](https://github.com/FluentLenium/FluentLenium/issues/440))
- Apply FluentLenium injection on Components creating with find API ([#439](https://github.com/FluentLenium/FluentLenium/issues/439))

#### Features
- Allow wrapping of existing selenium element with search API ([#450](https://github.com/FluentLenium/FluentLenium/issues/450))
- Use java.util.function package as much as possible instead of guava ([#179](https://github.com/FluentLenium/FluentLenium/issues/))

### 3.0.5 (Dec 16 2016)
#### Fixes
- Race condition in Asserts on error messages level ([#440](https://github.com/FluentLenium/FluentLenium/issues/440))

### 3.0.4 (Nov 25 2016)
#### Features
- Add parameters in @PageUrl/getUrl() ([#342](https://github.com/FluentLenium/FluentLenium/issues/342))
- isAt() now uses @PageUrl/getUrl() when **@findby** is not defined. ([#424](https://github.com/FluentLenium/FluentLenium/issues/424))
- Add capabilities() to retrieve current driver capabilities ([#419](https://github.com/FluentLenium/FluentLenium/issues/419))

#### Fixes
- HtmlUnitDriver is now properly used by default when available in classpath ([#422](https://github.com/FluentLenium/FluentLenium/issues/422))

### 3.0.3 (Nov 10 2016)
- `scrollToElement` added for elements it is extending scrollInToView method.

### 3.0.2 (Oct 18 2016)
#### Features
- Timeout messages now displays the actual value ([#406](https://github.com/FluentLenium/FluentLenium/issues/406))
- Configuration properties `awaitAtMost` and `awaitPollingEvery` added ([#362](https://github.com/FluentLenium/FluentLenium/issues/362))
- `scrollIntoView` javascript function added ([#388](https://github.com/FluentLenium/FluentLenium/issues/388))

#### Refactoring
- Upgrade tests to use mockito 2 and drop powermock usage ([#408](https://github.com/FluentLenium/FluentLenium/issues/408))

#### Fixes
- Use default WebDriver timeouts value when timeouts configuration properties are not defined.

### 3.0.1 (Oct 15 2016)
#### Features
- Selenium updated to 3.0.0 ([#400](https://github.com/FluentLenium/FluentLenium/issues/400))
- `@NoHook` and `noHook()` can now have hook parameters to disable specified hooks ([#393](https://github.com/FluentLenium/FluentLenium/issues/393))
- `@After` custom annotation introduced. Methods annotated with this annotation in test will be called after the screenshot is taken when using AUTOMATIC_ON_FAIL ([#390](https://github.com/FluentLenium/FluentLenium/issues/390))
- Default `Capabilities` and `WebDriver` factories can now be overriden ([#394](https://github.com/FluentLenium/FluentLenium/issues/394))

#### Refactoring
- `windows()` refactored to match the Selenium API ([#399](https://github.com/FluentLenium/FluentLenium/issues/399))

#### Fixes
- Error message when performing search with filters now display information about locator ([#386](https://github.com/FluentLenium/FluentLenium/issues/3866))

### 3.0.0 (Oct 12 2016)
- All fixes and changes included in `v1.0.0` release
- Migration to Java 8
- Selenium `3.0.0-beta4` support added
- Selenium 2 will be supported in `v1.0.0` version which will contain bug fixes only.

### v3.0.0-alpha (Oct 4 2016)
- Selenium3.0-beta4 support added
- All features introduced with FluentLenium-1.0.0 are contained here and will be merged successfully

## Version 1.x

### 1.1.1
#### Fixes
- fluentlenium.properties values set for driveLifecycle is not loaded ([#461](https://github.com/FluentLenium/FluentLenium/issues/))
- fluentlenium.properties values are case insensitive now

### 1.1.0 (Dec 18 2016)
#### Fixes
- Makes annotation events priority consistent between Component and Test/Page ([#440](https://github.com/FluentLenium/FluentLenium/issues/440))
- Apply FluentLenium injection on Components creating with find API ([#439](https://github.com/FluentLenium/FluentLenium/issues/439))

#### Features
- Allow wrapping of existing selenium element with search API ([#450](https://github.com/FluentLenium/FluentLenium/issues/450))

### 1.0.5 (Dec 17 2016)
#### Fixes
- Race condition in Asserts on error messages level ([#440](https://github.com/FluentLenium/FluentLenium/issues/440))

### 1.0.4 (Nov 25 2016)
#### Features
- Add parameters in @PageUrl/getUrl() ([#342](https://github.com/FluentLenium/FluentLenium/issues/342))
- isAt() now uses @PageUrl/getUrl() when **@findby** is not defined. ([#424](https://github.com/FluentLenium/FluentLenium/issues/424))
- Add capabilities() to retrieve current driver capabilities ([#419](https://github.com/FluentLenium/FluentLenium/issues/419))

#### Fixes
- HtmlUnitDriver is now properly used by default when available in classpath ([#422](https://github.com/FluentLenium/FluentLenium/issues/422))

### 1.0.2 (Oct 18 2016)
#### Features
- Timeout messages now displays the actual value ([#406](https://github.com/FluentLenium/FluentLenium/issues/406))
- Configuration properties `awaitAtMost` and `awaitPollingEvery` added ([#362](https://github.com/FluentLenium/FluentLenium/issues/362))
- `scrollIntoView` javascript function added ([#388](https://github.com/FluentLenium/FluentLenium/issues/388))

#### Refactoring
- Upgrade tests to use mockito 2 and drop powermock usage ([#408](https://github.com/FluentLenium/FluentLenium/issues/408))

#### Fixes
- Use default WebDriver timeouts value when timeouts configuration properties are not defined.

### 1.0.1 (Oct 15 2016)
#### Features
- `@NoHook` and `noHook()` can now have hook parameters to disable specified hooks ([#393](https://github.com/FluentLenium/FluentLenium/issues/393))
- `@After` custom annotation introduced. Methods annotated with this annotation in test will be called after the screenshot is taken when using AUTOMATIC_ON_FAIL ([#390](https://github.com/FluentLenium/FluentLenium/issues/390))
- Default `Capabilities` and `WebDriver` factories can now be overriden ([#394](https://github.com/FluentLenium/FluentLenium/issues/394))

#### Refactoring
- `windows()` refactored to match the Selenium API ([#399](https://github.com/FluentLenium/FluentLenium/issues/399))

#### Fixes
- Error message when performing search with filters now display information about locator ([#386](https://github.com/FluentLenium/FluentLenium/issues/3866))

