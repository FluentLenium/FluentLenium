package org.fluentlenium.example.spring.app;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class IosTestApp extends FluentPage {

    @FindBy(xpath = "//XCUIElementTypeTextField[@name='IntegerA']")
    private FluentWebElement integerA;

    @FindBy(xpath = "//XCUIElementTypeTextField[@name='IntegerB']")
    private FluentWebElement integerB;

    @FindBy(xpath = "//XCUIElementTypeButton[@name='ComputeSumButton']")
    private FluentWebElement computeSumButton;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Answer']")
    private FluentWebElement answer;

    public IosTestApp add(int a, int b) {
        integerA.write(String.valueOf(a));
        integerB.write(String.valueOf(b));
        computeSumButton.click();
        return this;
    }

    public void verifyResult(int result) {
        assertThat(answer).hasText(String.valueOf(result));
    }

}
