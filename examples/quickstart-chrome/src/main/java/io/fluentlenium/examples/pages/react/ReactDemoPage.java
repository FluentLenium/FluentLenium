package io.fluentlenium.examples.pages.react;

import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.PageUrl;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(id = "app")
@PageUrl("https://foxhound87.github.io/mobx-react-form-demo/demo")
public class ReactDemoPage extends FluentPage {

    @FindBy(name = "username")
    private FluentWebElement usernameInput;

    @FindBy(name = "email")
    private FluentWebElement emailInput;

    @FindBy(name = "emailConfirm")
    private FluentWebElement confirmEmailInput;

    @FindBy(name = "password")
    private FluentWebElement passwordInput;

    @FindBy(name = "devSkills")
    private FluentWebElement devSkillsInput;

    @FindBy(css = "input[type=text]")
    private FluentList<FluentWebElement> textInputs;

    @Override
    public void isAt() {
        await().until(usernameInput).displayed();
    }

    public FluentWebElement getUsernameInput() {
        return usernameInput;
    }

    public FluentWebElement getEmailInput() {
        return emailInput;
    }

    public FluentWebElement getConfirmEmailInput() {
        return confirmEmailInput;
    }

    public FluentWebElement getPasswordInput() {
        return passwordInput;
    }

    public ReactDemoPage clearAllTextInputs() {
        textInputs.clearAllReactInputs();
        return this;
    }

}
