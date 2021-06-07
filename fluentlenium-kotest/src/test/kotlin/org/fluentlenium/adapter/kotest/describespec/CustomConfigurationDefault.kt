package org.fluentlenium.adapter.kotest.describespec

import org.fluentlenium.configuration.ConfigurationDefaults

class CustomConfigurationDefault : ConfigurationDefaults() {
    override fun getRemoteUrl(): String = "https://www.google.com"
}
