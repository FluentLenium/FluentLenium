package org.fluentlenium.adapter.kotest

import io.github.bonigarcia.wdm.WebDriverManager
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KoTestConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> =
        listOf(object : TestListener {
            override suspend fun beforeSpec(spec: Spec) {
                withContext(Dispatchers.IO) {
                    WebDriverManager.chromedriver().setup()
                }
            }
        })
}
