kotest-fluentlenium
===================

Fluentlenium [Kotest](https://kotest.io/) support

# Writing tests

supported Kotest [testing styles](https://kotest.io/docs/framework/testing-styles.html):

* [Fun Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/funspec/ExampleFunSpec.kt)
* [Describe Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/describespec/ExampleDescribeSpec.kt)
* [Should Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/shouldspec/ExampleShouldSpec.kt)
* [Behavior Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/behaviorspec/ExampleBehaviorSpec.kt)
* [Free Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/freespec/ExampleFreeSpec.kt)
* [Word Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/wordspec/ExampleWordSpec.kt)
* [Feature Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/featurespec/ExampleFeatureSpec.kt)
* [Expect Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/expectspec/ExampleExpectSpec.kt)
* [Annotation Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/annotationspec/ExampleAnnotationSpec.kt)
* [String Spec](./src/test/kotlin/io/fluentlenium/adapter/kotest/stringspec/ExampleStringSpec.kt)

# Extension functions

In Kotlin usage of the fluentlenium
method [$](https://fluentlenium.io/javadoc/io/fluentlenium/core/search/SearchControl.html#$(io.fluentlenium.core.search.SearchFilter...))
method
must be escaped. As an alternative this library offers the extension function `jq`.