package org.fluentlenium.adapter.kotest

import io.github.bonigarcia.wdm.WebDriverManager
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener
import io.kotest.core.listeners.ProjectListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KoTestConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> =
        listOf(object : ProjectListener {
            override suspend fun beforeProject() {
                withContext(Dispatchers.IO) {
                    WebDriverManager.chromedriver().setup()
                }
            }
        })
}
