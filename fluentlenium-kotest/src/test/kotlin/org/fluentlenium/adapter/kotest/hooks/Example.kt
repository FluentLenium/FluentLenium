package org.fluentlenium.adapter.kotest.hooks

import org.fluentlenium.core.hook.Hook
import org.fluentlenium.core.hook.HookOptions
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Hook(
    ExampleHook::class
)
@HookOptions(ExampleHookOptions::class)
annotation class Example(val message: String = "")
