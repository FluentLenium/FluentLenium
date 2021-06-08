package org.fluentlenium.kotest.matchers.config

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

    override val parallelism: Int = 2
}
