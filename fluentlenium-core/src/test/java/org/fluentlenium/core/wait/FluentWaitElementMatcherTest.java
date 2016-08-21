package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FluentWaitElementMatcherTest {
    @Mock
    private Search search;

    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private WebElement element;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        wait = new FluentWait(fluent, search);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement.conditions()).thenReturn(new WebElementConditions(fluentWebElement));
        when(fluentWebElement.getElement()).thenReturn(element);
        when(fluentWebElement.now()).thenReturn(fluentWebElement);
    }

    @After
    public void after() {
        reset(search);
        reset(fluent);
        reset(fluentWebElement);
        reset(element);
    }

    @Test
    public void isVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isEnabled();
            }
        };

        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isVerified(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isEnabled();

        when(fluentWebElement.isEnabled()).thenReturn(true);
        matcher.isVerified(predicate);

        verify(fluentWebElement, atLeastOnce()).isEnabled();
    }

    @Test
    public void isNotVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isEnabled();
            }
        };

        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isVerified(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isEnabled();

        when(fluentWebElement.isEnabled()).thenReturn(true);
        matcher.not().isVerified(predicate);

        verify(fluentWebElement, atLeastOnce()).isEnabled();
    }

    @Test
    public void hasAttribute() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasAttribute("test", "value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).getAttribute("test");

        when(fluentWebElement.getAttribute("test")).thenReturn("value");
        matcher.hasAttribute("test", "value");

        verify(fluentWebElement, atLeastOnce()).getAttribute("test");

        matcher.not().hasAttribute("test", "not");
    }


    @Test
    public void hasId() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasId("value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).getId();

        when(fluentWebElement.getId()).thenReturn("value");
        matcher.hasId("value");

        verify(fluentWebElement, atLeastOnce()).getId();

        matcher.not().hasId("not");
    }

    @Test
    public void hasName() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasName("name");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).getName();

        when(fluentWebElement.getName()).thenReturn("name");
        matcher.hasName("name");

        verify(fluentWebElement, atLeastOnce()).getName();

        matcher.not().hasName("not");
    }

    @Test
    public void hasText() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasText("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).getText();

        when(fluentWebElement.getText()).thenReturn("text");
        matcher.hasText("text");

        verify(fluentWebElement, atLeastOnce()).getText();

        matcher.not().hasText("not");
    }

    @Test
    public void containsText() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.containsText("ex");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).getText();

        when(fluentWebElement.getText()).thenReturn("text");
        matcher.containsText("ex");

        verify(fluentWebElement, atLeastOnce()).getText();

        matcher.not().containsText("not");
    }

    @Test
    public void isPresent() {
        when(fluentWebElement.now()).thenThrow(NoSuchElementException.class);

        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isPresent();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        reset(fluentWebElement);
        matcher.isPresent();

        verify(fluentWebElement, atLeastOnce()).now();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isPresent();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

    }


    @Test
    public void isEnabled() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isEnabled();

        when(fluentWebElement.isEnabled()).thenReturn(true);
        matcher.isEnabled();

        verify(fluentWebElement, atLeastOnce()).isEnabled();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void proxyIsEnabled() {
        when(fluentWebElement.getTagName()).thenThrow(NoSuchElementException.class);

        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isSelected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isSelected();

        when(fluentWebElement.isSelected()).thenReturn(true);
        matcher.isSelected();

        verify(fluentWebElement, atLeastOnce()).isSelected();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isSelected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isDisplayed() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isDisplayed();

        when(fluentWebElement.isDisplayed()).thenReturn(true);
        matcher.isDisplayed();

        verify(fluentWebElement, atLeastOnce()).isDisplayed();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isClickable() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isClickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isClickable();

        when(fluentWebElement.isClickable()).thenReturn(true);
        matcher.isClickable();

        verify(fluentWebElement, atLeastOnce()).isClickable();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isClickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isStale() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isStale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).isStale();

        when(fluentWebElement.isStale()).thenReturn(true);
        matcher.isStale();

        verify(fluentWebElement, atLeastOnce()).isStale();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isStale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasRectangle() {
        final FluentWaitElementMatcher matcher = new FluentWaitElementMatcher(search, wait, fluentWebElement);

        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasRectangle().withX(5);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(element, atLeastOnce()).getRect();

        matcher.hasRectangle().withX(1);

        verify(element, atLeastOnce()).getRect();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasRectangle().withX(1);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }
}
