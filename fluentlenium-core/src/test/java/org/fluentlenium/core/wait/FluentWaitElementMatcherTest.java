package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitElementMatcherTest {
    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private WebElement element;

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement.conditions()).thenReturn(new WebElementConditions(fluentWebElement));
        when(fluentWebElement.getElement()).thenReturn(element);
        when(fluentWebElement.now()).thenReturn(fluentWebElement);
    }

    @After
    public void after() {
        reset(fluent);
        reset(fluentWebElement);
        reset(element);
    }

    @Test
    public void isVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return input.enabled();
            }
        };

        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.verify(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.verify(predicate);

        verify(fluentWebElement, atLeastOnce()).enabled();
    }

    @Test
    public void isNotVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(final FluentWebElement input) {
                return !input.enabled();
            }
        };

        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().verify(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.not().verify(predicate);

        verify(fluentWebElement, atLeastOnce()).enabled();
    }

    @Test
    public void hasAttribute() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.attribute("test", "value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).attribute("test");

        when(fluentWebElement.attribute("test")).thenReturn("value");
        matcher.attribute("test", "value");

        verify(fluentWebElement, atLeastOnce()).attribute("test");

        matcher.not().attribute("test", "not");
    }

    @Test
    public void hasId() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.id("value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).id();

        when(fluentWebElement.id()).thenReturn("value");
        matcher.id("value");

        verify(fluentWebElement, atLeastOnce()).id();

        matcher.not().id("not");
    }

    @Test
    public void hasName() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.name("name");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).name();

        when(fluentWebElement.name()).thenReturn("name");
        matcher.name("name");

        verify(fluentWebElement, atLeastOnce()).name();

        matcher.not().name("not");
    }

    @Test
    public void hasText() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().equalTo("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).text();

        when(fluentWebElement.text()).thenReturn("text");
        matcher.text().equalTo("text");

        verify(fluentWebElement, atLeastOnce()).text();

        matcher.not().text().equalTo("not");
    }

    @Test
    public void containsText() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().contains("ex");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).text();

        when(fluentWebElement.text()).thenReturn("text");
        matcher.text().contains("ex");

        verify(fluentWebElement, atLeastOnce()).text();

        matcher.not().text().contains("not");
    }

    @Test
    public void isPresent() {
        when(fluentWebElement.now()).thenThrow(NoSuchElementException.class);

        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.present();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        reset(fluentWebElement);
        when(fluentWebElement.present()).thenReturn(true);
        matcher.present();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().present();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isEnabled() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.enabled();

        verify(fluentWebElement, atLeastOnce()).enabled();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void proxyIsEnabled() {
        when(fluentWebElement.tagName()).thenThrow(NoSuchElementException.class);

        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.selected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).selected();

        when(fluentWebElement.selected()).thenReturn(true);
        matcher.selected();

        verify(fluentWebElement, atLeastOnce()).selected();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().selected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isDisplayed() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.displayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).displayed();

        when(fluentWebElement.displayed()).thenReturn(true);
        matcher.displayed();

        verify(fluentWebElement, atLeastOnce()).displayed();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().displayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isClickable() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.clickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).clickable();

        when(fluentWebElement.clickable()).thenReturn(true);
        matcher.clickable();

        verify(fluentWebElement, atLeastOnce()).clickable();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().clickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isStale() {
        final FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.stale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).stale();

        when(fluentWebElement.stale()).thenReturn(true);
        matcher.stale();

        verify(fluentWebElement, atLeastOnce()).stale();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().stale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasRectangle() {
        final FluentConditions matcher = wait.until(fluentWebElement);

        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.rectangle().x(5);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(element, atLeastOnce()).getRect();

        matcher.rectangle().x(1);

        verify(element, atLeastOnce()).getRect();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().rectangle().x(1);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }
}
