kotest-fluentlenium-assertions
===================

This module provides a set of
custom [Kotest](https://kotest.io/) [matchers](https://kotest.io/docs/assertions/matchers.html) to be used with
Fluentlenium. These matchers do not depend on the `fluentlenium-kotest` module and
can also be used with in combination with Kotlin, Fluentlenium and e.g. JUnit as Testrunner.

# Custom matchers

* [Page](./src/test/kotlin/org/fluentlenium/kotest/matchers/page/PageMatchersSpec.kt)
* [Alert](./src/test/kotlin/org/fluentlenium/kotest/matchers/alert/AlertMatchersSpec.kt)
* [FluentWebElement](./src/test/kotlin/org/fluentlenium/kotest/matchers/el/FluentWebElementMatchersSpec.kt)
* [FluentList](./src/test/kotlin/org/fluentlenium/kotest/matchers/jq/FluentListMatchersSpec.kt)

See the respective tests for usage examples