package io.fluentlenium.example.kotest

import io.kotest.core.annotation.EnabledCondition
import io.kotest.core.spec.Spec
import kotlin.reflect.KClass

class IsNotMacOS : EnabledCondition {
    override fun enabled(kclass: KClass<out Spec>): Boolean =
        !System.getProperty("os.name").startsWith("Mac")
}