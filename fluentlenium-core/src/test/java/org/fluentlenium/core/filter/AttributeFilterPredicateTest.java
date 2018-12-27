package org.fluentlenium.core.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.filter.matcher.AbstractMatcher;
import org.fluentlenium.core.filter.matcher.MatcherType;

/**
 * Unit test for {@link AttributeFilterPredicate}.
 */
@RunWith(MockitoJUnitRunner.class)
public class AttributeFilterPredicateTest {

    @Mock
    private AttributeFilter attributeFilter;
    @Mock
    private FluentWebElement fluentWebElement;

    private AttributeFilterPredicate attributeFilterPredicate;

    @Test
    public void textAttributeShouldSatisfyMatcher() {
        setupPredicateAndMatcher("text attribute value");
        when(attributeFilter.getAttribute()).thenReturn("text");
        when(fluentWebElement.text()).thenReturn("text attribute value");

        assertThat(attributeFilterPredicate.test(fluentWebElement)).isTrue();
    }

    @Test
    public void textAttributeShouldNotSatisfyMatcher() {
        setupPredicateAndMatcher("text attribute value");
        when(attributeFilter.getAttribute()).thenReturn("text");
        when(fluentWebElement.text()).thenReturn("text attribute");

        assertThat(attributeFilterPredicate.test(fluentWebElement)).isFalse();
    }

    @Test
    public void otherAttributeShouldSatisfyMatcher() {
        setupPredicateAndMatcher("not-text attribute value");
        when(attributeFilter.getAttribute()).thenReturn("not-text");
        when(fluentWebElement.attribute("not-text")).thenReturn("not-text attribute value");

        assertThat(attributeFilterPredicate.test(fluentWebElement)).isTrue();
    }

    @Test
    public void otherAttributeShouldNotSatisfyMatcher() {
        setupPredicateAndMatcher("not-text attribute value");
        when(attributeFilter.getAttribute()).thenReturn("not-text");
        when(fluentWebElement.attribute("not-text")).thenReturn("not-text attribute");

        assertThat(attributeFilterPredicate.test(fluentWebElement)).isFalse();
    }

    private void setupPredicateAndMatcher(String matcherValue) {
        AbstractMatcher matcher = new DummyEqualsMatcher(matcherValue);
        attributeFilterPredicate = new AttributeFilterPredicate(attributeFilter);
        when(attributeFilter.getMatcher()).thenReturn(matcher);
    }

    private static final class DummyEqualsMatcher extends AbstractMatcher {
        protected DummyEqualsMatcher(String value) {
            super(value);
        }

        @Override
        protected MatcherType getMatcherType() {
            return MatcherType.EQUALS;
        }

        @Override
        public boolean isSatisfiedBy(String value) {
            return this.getValue().equals(value);
        }
    }
}
