package org.fluentlenium.adapter.kotest.describespec

import org.fluentlenium.configuration.ConfigurationDefaults
import org.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile

class CustomConfigurationDefault : ConfigurationDefaults() {
    override fun getRemoteUrl(): String = getAbsoluteUrlPathFromFile("index.html")
}
