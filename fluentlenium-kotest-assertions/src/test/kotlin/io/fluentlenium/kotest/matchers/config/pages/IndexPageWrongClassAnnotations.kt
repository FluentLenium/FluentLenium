package io.fluentlenium.kotest.matchers.config.pages

import io.fluentlenium.core.FluentPage
import org.openqa.selenium.support.FindBy

@FindBy(id = "nonexisting")
class IndexPageWrongClassAnnotations : FluentPage()
