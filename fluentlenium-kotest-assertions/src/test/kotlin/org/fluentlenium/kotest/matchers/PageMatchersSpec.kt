package org.fluentlenium.kotest.matchers

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.should
import io.mockk.every
import io.mockk.mockk
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.fluentlenium.core.page.ClassAnnotations
import org.openqa.selenium.WebDriver

class PageMatchersSpec : AnnotationSpec() {

    private val element: FluentWebElement = mockk()

    private val fluentPage: FluentPage = mockk()

    private val list: FluentList<FluentWebElement> = mockk()

    private val driver: WebDriver = mockk()

    private val classAnnotations: ClassAnnotations = mockk()

    @Test
    fun hasElementOk() {
        every {element.present()} returns true

        fluentPage should haveElement(element)
    }
}