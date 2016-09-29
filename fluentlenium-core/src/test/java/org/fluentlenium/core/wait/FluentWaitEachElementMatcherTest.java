package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.FluentListConditions;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitEachElementMatcherTest {
    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentWebElement fluentWebElement1;

    @Mock
    private FluentWebElement fluentWebElement2;

    @Mock
    private FluentWebElement fluentWebElement3;

    private List<FluentWebElement> fluentWebElements;

    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement1.conditions()).thenReturn(new WebElementConditions(fluentWebElement1));
        when(fluentWebElement1.getElement()).thenReturn(element1);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);

        when(fluentWebElement2.conditions()).thenReturn(new WebElementConditions(fluentWebElement2));
        when(fluentWebElement2.getElement()).thenReturn(element2);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);

        when(fluentWebElement3.conditions()).thenReturn(new WebElementConditions(fluentWebElement3));
        when(fluentWebElement3.getElement()).thenReturn(element3);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);

        fluentWebElements = Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3);
    }

    @After
    public void after() {
        reset(fluent);
        reset(fluentWebElement1);
        reset(fluentWebElement2);
        reset(fluentWebElement3);
        reset(element1);
        reset(element2);
        reset(element3);
    }

    @Test
    public void isVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.enabled();
            }
        };


        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.verify(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, never()).enabled();
        verify(fluentWebElement3, never()).enabled();

        reset(fluentWebElement1, fluentWebElement2, fluentWebElement3);

        when(fluentWebElement1.enabled()).thenReturn(true);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);
        when(fluentWebElement2.enabled()).thenReturn(true);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);
        when(fluentWebElement3.enabled()).thenReturn(true);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);
        matcher.verify(predicate);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();
    }

    @Test
    public void isVerifiedEmpty() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.enabled();
            }
        };

        final FluentListConditions matcher = wait.untilEach(new ArrayList<FluentWebElement>());
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.verify(predicate, false);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        matcher.verify(predicate, true);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().verify(predicate, true);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isNotVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.enabled();
            }
        };

        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().verify(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, never()).enabled();
        verify(fluentWebElement3, never()).enabled();

        reset(fluentWebElement1, fluentWebElement2, fluentWebElement3);

        when(fluentWebElement1.enabled()).thenReturn(true);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);
        when(fluentWebElement2.enabled()).thenReturn(true);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);
        when(fluentWebElement3.enabled()).thenReturn(true);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);
        matcher.not().verify(predicate);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();
    }


    @Test
    public void hasAttribute() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.attribute("test", "value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).attribute("test");
        verify(fluentWebElement2, never()).attribute("test");
        verify(fluentWebElement3, never()).attribute("test");

        when(fluentWebElement1.attribute("test")).thenReturn("value");
        when(fluentWebElement2.attribute("test")).thenReturn("value");
        when(fluentWebElement3.attribute("test")).thenReturn("value");
        matcher.attribute("test", "value");

        verify(fluentWebElement1, atLeastOnce()).attribute("test");
        verify(fluentWebElement2, atLeastOnce()).attribute("test");
        verify(fluentWebElement3, atLeastOnce()).attribute("test");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().attribute("test", "value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }


    @Test
    public void hasId() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.id("value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).id();
        verify(fluentWebElement2, never()).id();
        verify(fluentWebElement3, never()).id();

        when(fluentWebElement1.id()).thenReturn("value");
        when(fluentWebElement2.id()).thenReturn("value");
        when(fluentWebElement3.id()).thenReturn("value");
        matcher.id("value");

        verify(fluentWebElement1, atLeastOnce()).id();
        verify(fluentWebElement2, atLeastOnce()).id();
        verify(fluentWebElement3, atLeastOnce()).id();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().id("value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasName() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.name("name");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).name();
        verify(fluentWebElement2, never()).name();
        verify(fluentWebElement3, never()).name();

        when(fluentWebElement1.name()).thenReturn("name");
        when(fluentWebElement2.name()).thenReturn("name");
        when(fluentWebElement3.name()).thenReturn("name");
        matcher.name("name");

        verify(fluentWebElement1, atLeastOnce()).name();
        verify(fluentWebElement2, atLeastOnce()).name();
        verify(fluentWebElement3, atLeastOnce()).name();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().name("name");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasText() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().equalTo("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, never()).text();
        verify(fluentWebElement3, never()).text();

        when(fluentWebElement1.text()).thenReturn("text");
        when(fluentWebElement2.text()).thenReturn("text");
        when(fluentWebElement3.text()).thenReturn("text");
        matcher.text().equalTo("text");

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().text().equalTo("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void containsText() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().contains("ex");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, never()).text();
        verify(fluentWebElement3, never()).text();

        when(fluentWebElement1.text()).thenReturn("text");
        when(fluentWebElement2.text()).thenReturn("text");
        when(fluentWebElement3.text()).thenReturn("text");
        matcher.text().contains("ex");

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().text().contains("ex");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isPresent() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.present();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        when(fluentWebElement1.present()).thenReturn(true);
        when(fluentWebElement2.present()).thenReturn(true);
        when(fluentWebElement3.present()).thenReturn(true);
        matcher.present();

        verify(fluentWebElement1, atLeastOnce()).present();
        verify(fluentWebElement2, atLeastOnce()).present();
        verify(fluentWebElement3, atLeastOnce()).present();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().present();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }


    @Test
    public void isEnabled() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, never()).enabled();
        verify(fluentWebElement3, never()).enabled();

        when(fluentWebElement1.enabled()).thenReturn(true);
        when(fluentWebElement2.enabled()).thenReturn(true);
        when(fluentWebElement3.enabled()).thenReturn(true);
        matcher.enabled();

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void proxyIsEnabled() {
        when(fluentWebElement1.tagName()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement2.tagName()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement3.tagName()).thenThrow(NoSuchElementException.class);

        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.selected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).selected();
        verify(fluentWebElement2, never()).selected();
        verify(fluentWebElement3, never()).selected();

        when(fluentWebElement1.selected()).thenReturn(true);
        when(fluentWebElement2.selected()).thenReturn(true);
        when(fluentWebElement3.selected()).thenReturn(true);
        matcher.selected();

        verify(fluentWebElement1, atLeastOnce()).selected();
        verify(fluentWebElement2, atLeastOnce()).selected();
        verify(fluentWebElement3, atLeastOnce()).selected();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().selected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isDisplayed() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.displayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).displayed();
        verify(fluentWebElement2, never()).displayed();
        verify(fluentWebElement3, never()).displayed();

        when(fluentWebElement1.displayed()).thenReturn(true);
        when(fluentWebElement2.displayed()).thenReturn(true);
        when(fluentWebElement3.displayed()).thenReturn(true);
        matcher.displayed();

        verify(fluentWebElement1, atLeastOnce()).displayed();
        verify(fluentWebElement2, atLeastOnce()).displayed();
        verify(fluentWebElement3, atLeastOnce()).displayed();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().displayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isClickable() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.clickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).clickable();
        verify(fluentWebElement2, never()).clickable();
        verify(fluentWebElement3, never()).clickable();

        when(fluentWebElement1.clickable()).thenReturn(true);
        when(fluentWebElement2.clickable()).thenReturn(true);
        when(fluentWebElement3.clickable()).thenReturn(true);
        matcher.clickable();

        verify(fluentWebElement1, atLeastOnce()).clickable();
        verify(fluentWebElement2, atLeastOnce()).clickable();
        verify(fluentWebElement3, atLeastOnce()).clickable();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().clickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isStale() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.stale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).stale();
        verify(fluentWebElement2, never()).stale();
        verify(fluentWebElement3, never()).stale();

        when(fluentWebElement1.stale()).thenReturn(true);
        when(fluentWebElement2.stale()).thenReturn(true);
        when(fluentWebElement3.stale()).thenReturn(true);
        matcher.stale();

        verify(fluentWebElement1, atLeastOnce()).stale();
        verify(fluentWebElement2, atLeastOnce()).stale();
        verify(fluentWebElement3, atLeastOnce()).stale();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().stale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasSize() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.size(2);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        matcher.size(3);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().size(3);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasSizeBuilder() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.size().equalTo(2);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        matcher.size().equalTo(3);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().size().equalTo(3);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasRectangle() {
        final FluentListConditions matcher = wait.untilEach(fluentWebElements);

        when(element1.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element2.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element3.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.rectangle().x(5);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, never()).getRect();
        verify(element3, never()).getRect();

        matcher.rectangle().x(1);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, atLeastOnce()).getRect();
        verify(element3, atLeastOnce()).getRect();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().rectangle().x(1);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

}
