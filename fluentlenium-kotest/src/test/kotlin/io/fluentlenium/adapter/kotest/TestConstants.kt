package io.fluentlenium.adapter.kotest

import io.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile

object TestConstants {

    val DEFAULT_URL: String = getAbsoluteUrlFromFile("index.html")
    val PAGE2_URL: String = getAbsoluteUrlFromFile("page2.html")
}
