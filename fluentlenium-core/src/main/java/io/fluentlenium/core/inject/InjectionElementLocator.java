package io.fluentlenium.core.inject;

import io.fluentlenium.core.label.FluentLabelImpl;
import io.fluentlenium.core.label.FluentLabelProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

/**
 * The injection element locator, which will lazily locate an element or an element list on a page. This class is
 * designed for use with the {@link org.openqa.selenium.support.PageFactory} and understands the
 * annotations {@link org.openqa.selenium.support.FindBy} and {@link org.openqa.selenium.support.CacheLookup}.
 */
public class InjectionElementLocator implements ElementLocator, FluentLabelProvider {
    private final SearchContext searchContext;
    private final boolean shouldCache;
    private final By by;
    private WebElement cachedElement;
    private List<WebElement> cachedElementList;
    private final FluentLabelImpl<InjectionElementLocator> label;

    /**
     * Use this constructor in order to process custom annotaions.
     *
     * @param searchContext The context to use when finding the element
     * @param annotations   InjectionAnnotations class implementation
     * @param isFirst       Is this locator used to retrieve list or single element.
     */
    public InjectionElementLocator(SearchContext searchContext, InjectionAnnotations annotations, boolean isFirst) {
        this.searchContext = searchContext;
        shouldCache = annotations.isLookupCached();
        by = annotations.buildBy();
        label = new FluentLabelImpl<>(this, () -> by.toString() + (isFirst ? " (first)" : ""));
        label.withLabel(annotations.getLabel());
        label.withLabelHint(annotations.getLabelHints());
    }

    private FluentLabelProvider getLabelProvider() { // NOPMD UnusedPrivateMethod
        return label;
    }

    /**
     * Find the element.
     *
     * @return then found element
     */
    public WebElement findElement() {
        if (cachedElement != null && shouldCache) {
            return cachedElement;
        }

        WebElement element = searchContext.findElement(by);
        if (shouldCache) {
            cachedElement = element;
        }

        return element;
    }

    /**
     * Find the element list.
     *
     * @return list of found elements
     */
    public List<WebElement> findElements() {
        if (cachedElementList != null && shouldCache) {
            return cachedElementList;
        }

        List<WebElement> elements = searchContext.findElements(by);
        if (shouldCache) {
            cachedElementList = elements;
        }

        return elements;
    }

    @Override
    public String toString() {
        return label.toString();
    }

    public String getLabel() {
        return getLabelProvider().getLabel();
    }

    public String[] getLabelHints() {
        return getLabelProvider().getLabelHints();
    }
}

