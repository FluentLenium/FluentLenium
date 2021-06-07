package org.fluentlenium.adapter.kotest

import org.fluentlenium.utils.UrlUtils.getAbsoluteUrlFromFile

object TestConstants {

    val DEFAULT_URL: String = getAbsoluteUrlFromFile("index.html")
    val PAGE2_URL: String = getAbsoluteUrlFromFile("page2.html")

}