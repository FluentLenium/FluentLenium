package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.configuration.ConfigurationDefaults
import io.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile

class CustomConfigurationDefault : ConfigurationDefaults() {
    override fun getRemoteUrl(): String = getAbsoluteUrlPathFromFile("index.html")
}
