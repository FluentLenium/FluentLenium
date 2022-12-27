package io.fluentlenium.adapter.kotest.hooks

import io.fluentlenium.core.hook.Hook
import io.fluentlenium.core.hook.HookOptions
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@_root_ide_package_.io.fluentlenium.core.hook.Hook(
    ExampleHook::class
)
@_root_ide_package_.io.fluentlenium.core.hook.HookOptions(ExampleHookOptions::class)
annotation class Example(val message: String = "")
