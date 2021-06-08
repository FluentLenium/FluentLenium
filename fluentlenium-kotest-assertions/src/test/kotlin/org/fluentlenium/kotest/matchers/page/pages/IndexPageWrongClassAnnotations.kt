package org.fluentlenium.kotest.matchers.page.pages

import org.fluentlenium.core.FluentPage
import org.openqa.selenium.support.FindBy

@FindBy(id = "nonexisting")
class IndexPageWrongClassAnnotations : FluentPage()