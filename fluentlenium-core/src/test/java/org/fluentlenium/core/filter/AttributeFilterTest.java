package org.fluentlenium.core.filter;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.matcher.AbstractMatcher;
import org.fluentlenium.core.filter.matcher.ContainsWordMatcher;
import org.fluentlenium.core.filter.matcher.EqualMatcher;
import org.fluentlenium.core.filter.matcher.MatcherType;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link AttributeFilter}.
 */
public class AttributeFilterTest {

    private static final String A_VALUE = "value";
    private static final String A_REGEX_VALUE = "regex.*";

    //toString()

    @Test
    public void shouldGetToStringWhenMatcherIsNull() {
        AbstractMatcher matcher = null;
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThatNullPointerException().isThrownBy(attributeFilter::toString);
    }

    @Test
    public void shouldGetToStringWhenMatcherIsPresent() {
        AbstractMatcher matcher = new EqualMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThat(attributeFilter.toString()).isEqualTo("with id equals to \"value\"");
    }

    @Test
    public void shouldGetToStringWhenMatcherIsPresentWithoutToString() {
        AbstractMatcher matcher = new NoOpMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThat(attributeFilter.toString()).isEqualTo("with id \"value\"");
    }

    //getCssFilter()

    @Test
    public void shouldGetCSSFilterWithEmptyMatcherAttribute() {
        AbstractMatcher matcher = null;
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThatNullPointerException().isThrownBy(attributeFilter::getCssFilter);
    }

    @Test
    public void shouldGetCSSFilterWithEmptyMatcherSymbol() {
        AbstractMatcher matcher = new EqualMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        String cssFilter = "[id=\"value\"]";

        assertThat(attributeFilter.getCssFilter()).isEqualTo(cssFilter);
    }

    @Test
    public void shouldGetCSSFilterWithNonEmptyMatcherSymbol() {
        AbstractMatcher matcher = new ContainsWordMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        String cssFilter = "[id~=\"value\"]";

        assertThat(attributeFilter.getCssFilter()).isEqualTo(cssFilter);
    }

    @Test
    public void shouldGetCSSFilterWithMatcherSymbolWithNullMatcherType() {
        AbstractMatcher matcher = new NoOpMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        String cssFilter = "[id=\"value\"]";

        assertThat(attributeFilter.getCssFilter()).isEqualTo(cssFilter);
    }

    //isCssFilterSupported()

    @Test
    public void shouldSupportCssFilter() {
        AbstractMatcher matcher = new EqualMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThat(attributeFilter.isCssFilterSupported()).isTrue();
    }

    @Test
    public void shouldNotSupportCssFilterWhenMatcherIsNull() {
        AbstractMatcher matcher = null;
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThat(attributeFilter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void shouldNotSupportCssFilterWhenCssFilterIsNotSupportedInMatcher() {
        AbstractMatcher matcher = new EqualMatcher(Pattern.compile(A_REGEX_VALUE));
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThat(attributeFilter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void shouldNotSupportCssFilterWhenAttributeIsText() {
        AbstractMatcher matcher = new EqualMatcher(Pattern.compile(A_REGEX_VALUE));
        AttributeFilter attributeFilter = new AttributeFilter("text", matcher);

        assertThat(attributeFilter.isCssFilterSupported()).isFalse();
    }

    @Test
    public void shouldNotSupportCssFilterWhenAttributeIsTextContent() {
        AbstractMatcher matcher = new EqualMatcher(Pattern.compile(A_REGEX_VALUE));
        AttributeFilter attributeFilter = new AttributeFilter("textContent", matcher);

        assertThat(attributeFilter.isCssFilterSupported()).isFalse();
    }

    //applyFilter()

    @Test
    public void shouldApplyFilter() {
        WebElement webElement1 = mock(WebElement.class);
        when(webElement1.getAttribute("id")).thenReturn(A_VALUE);
        WebElement webElement3 = mock(WebElement.class);
        when(webElement3.getAttribute("id")).thenReturn(A_VALUE);

        FluentControl control = mock(FluentControl.class);
        ComponentInstantiator instantiator = mock(ComponentInstantiator.class);

        FluentWebElement fluentWebElem1 = new FluentWebElement(webElement1, control, instantiator);
        FluentWebElement fluentWebElem2 = new FluentWebElement(mock(WebElement.class), control, instantiator);
        FluentWebElement fluentWebElem3 = new FluentWebElement(webElement3, control, instantiator);
        FluentWebElement fluentWebElem4 = new FluentWebElement(mock(WebElement.class), control, instantiator);

        List<FluentWebElement> elementsToFilter = List.of(fluentWebElem1, fluentWebElem2, fluentWebElem3, fluentWebElem4);
        List<FluentWebElement> filteredElements = List.of(fluentWebElem1, fluentWebElem3);

        AbstractMatcher matcher = new EqualMatcher(A_VALUE);
        AttributeFilter attributeFilter = new AttributeFilter("id", matcher);

        assertThat(attributeFilter.applyFilter(elementsToFilter)).containsExactlyInAnyOrderElementsOf(filteredElements);
    }

    private final class NoOpMatcher extends AbstractMatcher {
        protected NoOpMatcher(String value) {
            super(value);
        }

        @Override
        protected MatcherType getMatcherType() {
            return null;
        }

        @Override
        public boolean isSatisfiedBy(String value) {
            return false;
        }

        @Override
        public String toString() {
            return null;
        }
    }
}
