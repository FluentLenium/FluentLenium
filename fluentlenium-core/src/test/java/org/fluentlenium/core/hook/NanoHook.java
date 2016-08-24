package org.fluentlenium.core.hook;

import com.google.common.base.Supplier;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public class NanoHook extends BaseHook<NanoHookOptions> {
    private long beforeClickNano;
    private long afterClickNano;
    private String optionValue;

    private long beforeFindElementsNano;
    private long afterFindElementsNano;

    private long beforeFindElementNano;
    private long afterFindElementNano;

    public NanoHook(WebDriver webDriver, ComponentInstantiator instantiator, Supplier<WebElement> elementSupplier, Supplier<ElementLocator> locatorSupplier, NanoHookOptions options) {
        super(webDriver, instantiator, elementSupplier, locatorSupplier, options);
    }

    @Override
    protected NanoHookOptions newOptions() {
        return new NanoHookOptions();
    }

    public long getBeforeClickNano() {
        return beforeClickNano;
    }

    public long getAfterClickNano() {
        return afterClickNano;
    }

    public long getBeforeFindElementNano() {
        return beforeFindElementNano;
    }

    public long getAfterFindElementNano() {
        return afterFindElementNano;
    }

    public long getBeforeFindElementsNano() {
        return beforeFindElementsNano;
    }

    public long getAfterFindElementsNano() {
        return afterFindElementsNano;
    }

    public String getOptionValue() {
        return optionValue;
    }

    @Override
    public void click() {
        beforeClickNano = System.nanoTime();
        if (getOptions().getValue() != null) {
            optionValue = getOptions().getValue();
        }
        super.click();
        afterClickNano = System.nanoTime();
    }

    @Override
    public List<WebElement> findElements() {
        beforeFindElementsNano = System.nanoTime();
        try {
            return super.findElements();
        } finally {
            afterFindElementsNano = System.nanoTime();
        }
    }

    @Override
    public WebElement findElement() {
        beforeFindElementNano = System.nanoTime();
        try {
            return super.findElement();
        } finally {
            afterFindElementNano = System.nanoTime();
        }
    }
}
