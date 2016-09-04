package org.fluentlenium.core.domain;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface WrapsElements {
    List<WebElement> getWrappedElements();
}
