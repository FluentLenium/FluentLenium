package org.fluentlenium.core.wait;

import com.google.common.base.Predicate;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
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
    private Search search;

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
        wait = new FluentWait(fluent, search);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(search.getInstantiator()).thenReturn(new DefaultComponentInstantiator(fluent));

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
        reset(search);
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

        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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

        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, new ArrayList<FluentWebElement>()).each();
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

        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().equals("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, never()).text();
        verify(fluentWebElement3, never()).text();

        when(fluentWebElement1.text()).thenReturn("text");
        when(fluentWebElement2.text()).thenReturn("text");
        when(fluentWebElement3.text()).thenReturn("text");
        matcher.text().equals("text");

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().text().equals("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void containsText() {
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        when(fluentWebElement1.now()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement2.now()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement3.now()).thenThrow(NoSuchElementException.class);

        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.present();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        reset(fluentWebElement1);
        reset(fluentWebElement2);
        reset(fluentWebElement3);
        matcher.present();

        verify(fluentWebElement1, atLeastOnce()).now();
        verify(fluentWebElement2, atLeastOnce()).now();
        verify(fluentWebElement3, atLeastOnce()).now();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().present();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }


    @Test
    public void isEnabled() {
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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

        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.enabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
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
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasSize(2);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        matcher.hasSize(3);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasSize(3);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasSizeBuilder() {
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasSize().equalTo(2);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        matcher.hasSize().equalTo(3);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasSize().equalTo(3);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasRectangle() {
        final FluentWaitElementEachMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements).each();

        when(element1.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element2.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element3.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.rectangle().withX(5);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, never()).getRect();
        verify(element3, never()).getRect();

        matcher.rectangle().withX(1);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, atLeastOnce()).getRect();
        verify(element3, atLeastOnce()).getRect();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().rectangle().withX(1);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

}
