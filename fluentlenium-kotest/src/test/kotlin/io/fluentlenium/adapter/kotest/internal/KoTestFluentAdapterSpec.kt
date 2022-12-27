package io.fluentlenium.adapter.kotest.internal

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec

class KoTestFluentAdapterSpec : StringSpec({

    "ensureTestStarted should fail by default" {
        shouldThrow<IllegalStateException> {
            KoTestFluentAdapter().ensureTestStarted()
        }
    }
})
