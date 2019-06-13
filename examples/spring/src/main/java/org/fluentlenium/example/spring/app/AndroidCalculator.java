package org.fluentlenium.example.spring.app;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class AndroidCalculator extends FluentPage {

    @FindBy(id = "com.android.calculator2:id/digit_2")
    private FluentWebElement digit2;

    @FindBy(id = "com.android.calculator2:id/op_add")
    private FluentWebElement add;

    @FindBy(id = "com.android.calculator2:id/digit_4")
    private FluentWebElement digit4;

    @FindBy(id = "com.android.calculator2:id/eq")
    private FluentWebElement equal;

    @FindBy(id = "com.android.calculator2:id/result")
    private FluentWebElement result;

    public AndroidCalculator addTwoPlusFour() {
        digit2.click();
        add.click();
        digit4.click();
        equal.click();
        return this;
    }

    public void verifyResult(int value) {
        assertThat(Integer.valueOf(result.text())).isEqualTo(value);
    }
}
