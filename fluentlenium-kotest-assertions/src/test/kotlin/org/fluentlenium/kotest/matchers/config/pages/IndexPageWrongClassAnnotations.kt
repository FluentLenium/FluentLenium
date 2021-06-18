package org.fluentlenium.kotest.matchers.config.pages

import org.fluentlenium.core.FluentPage
import org.openqa.selenium.support.FindBy

@FindBy(id = "nonexisting")
class IndexPageWrongClassAnnotations : FluentPage()
