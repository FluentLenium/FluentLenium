package io.fluentlenium.adapter.kotest.describespec

import io.fluentlenium.configuration.ConfigurationDefaults
import io.fluentlenium.utils.UrlUtils.getAbsoluteUrlPathFromFile

class CustomConfigurationDefault : _root_ide_package_.io.fluentlenium.configuration.ConfigurationDefaults() {
    override fun getRemoteUrl(): String = getAbsoluteUrlPathFromFile("index.html")
}
