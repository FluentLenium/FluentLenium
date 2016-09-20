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
public class FluentWaitElementListMatcherTest {
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
                return input.isEnabled();
            }
        };

        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isVerified(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, atLeastOnce()).isEnabled();
        verify(fluentWebElement3, atLeastOnce()).isEnabled();

        reset(fluentWebElement1, fluentWebElement2, fluentWebElement3);

        when(fluentWebElement1.isEnabled()).thenReturn(true);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);
        when(fluentWebElement2.isEnabled()).thenReturn(true);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);
        when(fluentWebElement3.isEnabled()).thenReturn(true);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);

        matcher.isVerified(predicate);

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, never()).isEnabled();
        verify(fluentWebElement3, never()).isEnabled();
    }

    @Test
    public void isVerifiedEmpty() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isEnabled();
            }
        };

        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, new ArrayList<FluentWebElement>());
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isVerified(predicate, false);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        matcher.isVerified(predicate, true);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isVerified(predicate, true);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isNotVerified() {
        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return !input.isEnabled();
            }
        };

        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isVerified(predicate);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, atLeastOnce()).isEnabled();
        verify(fluentWebElement3, atLeastOnce()).isEnabled();

        reset(fluentWebElement1, fluentWebElement2, fluentWebElement3);

        when(fluentWebElement1.isEnabled()).thenReturn(true);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);
        when(fluentWebElement2.isEnabled()).thenReturn(true);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);
        when(fluentWebElement3.isEnabled()).thenReturn(true);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);

        matcher.not().isVerified(predicate);

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, never()).isEnabled();
        verify(fluentWebElement3, never()).isEnabled();
    }


    @Test
    public void hasAttribute() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasAttribute("test", "value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).getAttribute("test");
        verify(fluentWebElement2, atLeastOnce()).getAttribute("test");
        verify(fluentWebElement3, atLeastOnce()).getAttribute("test");

        when(fluentWebElement1.getAttribute("test")).thenReturn("value");
        when(fluentWebElement2.getAttribute("test")).thenReturn("value");
        when(fluentWebElement3.getAttribute("test")).thenReturn("value");
        matcher.hasAttribute("test", "value");

        verify(fluentWebElement1, atLeastOnce()).getAttribute("test");
        verify(fluentWebElement2, atLeastOnce()).getAttribute("test");
        verify(fluentWebElement3, atLeastOnce()).getAttribute("test");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasAttribute("test", "value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }


    @Test
    public void hasId() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasId("value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).getId();
        verify(fluentWebElement2, atLeastOnce()).getId();
        verify(fluentWebElement3, atLeastOnce()).getId();

        when(fluentWebElement1.getId()).thenReturn("value");
        when(fluentWebElement2.getId()).thenReturn("value");
        when(fluentWebElement3.getId()).thenReturn("value");
        matcher.hasId("value");

        verify(fluentWebElement1, atLeastOnce()).getId();
        verify(fluentWebElement2, atLeastOnce()).getId();
        verify(fluentWebElement3, atLeastOnce()).getId();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasId("value");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasName() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasName("name");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).getName();
        verify(fluentWebElement2, atLeastOnce()).getName();
        verify(fluentWebElement3, atLeastOnce()).getName();

        when(fluentWebElement1.getName()).thenReturn("name");
        when(fluentWebElement2.getName()).thenReturn("name");
        when(fluentWebElement3.getName()).thenReturn("name");
        matcher.hasName("name");

        verify(fluentWebElement1, atLeastOnce()).getName();
        verify(fluentWebElement2, atLeastOnce()).getName();
        verify(fluentWebElement3, atLeastOnce()).getName();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasName("name");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasText() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().equals("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).getText();
        verify(fluentWebElement2, atLeastOnce()).getText();
        verify(fluentWebElement3, atLeastOnce()).getText();

        when(fluentWebElement1.getText()).thenReturn("text");
        when(fluentWebElement2.getText()).thenReturn("text");
        when(fluentWebElement3.getText()).thenReturn("text");
        matcher.text().equals("text");

        verify(fluentWebElement1, atLeastOnce()).getText();
        verify(fluentWebElement2, atLeastOnce()).getText();
        verify(fluentWebElement3, atLeastOnce()).getText();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().text().equals("text");
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void containsText() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.text().contains("ex");
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).getText();
        verify(fluentWebElement2, atLeastOnce()).getText();
        verify(fluentWebElement3, atLeastOnce()).getText();

        when(fluentWebElement1.getText()).thenReturn("text");
        when(fluentWebElement2.getText()).thenReturn("text");
        when(fluentWebElement3.getText()).thenReturn("text");
        matcher.text().contains("ex");

        verify(fluentWebElement1, atLeastOnce()).getText();
        verify(fluentWebElement2, atLeastOnce()).getText();
        verify(fluentWebElement3, atLeastOnce()).getText();

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

        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isPresent();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        reset(fluentWebElement1);
        reset(fluentWebElement2);
        reset(fluentWebElement3);
        matcher.isPresent();

        verify(fluentWebElement1, atLeastOnce()).now();
        verify(fluentWebElement2, atLeastOnce()).now();
        verify(fluentWebElement3, atLeastOnce()).now();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isPresent();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }


    @Test
    public void isEnabled() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, atLeastOnce()).isEnabled();
        verify(fluentWebElement3, atLeastOnce()).isEnabled();

        when(fluentWebElement1.isEnabled()).thenReturn(true);
        when(fluentWebElement2.isEnabled()).thenReturn(true);
        when(fluentWebElement3.isEnabled()).thenReturn(true);
        matcher.isEnabled();

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, atLeastOnce()).isEnabled();
        verify(fluentWebElement3, atLeastOnce()).isEnabled();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void proxyIsEnabled() {
        when(fluentWebElement1.now()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement2.now()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement3.now()).thenThrow(NoSuchElementException.class);

        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isSelected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isSelected();
        verify(fluentWebElement2, atLeastOnce()).isSelected();
        verify(fluentWebElement3, atLeastOnce()).isSelected();

        when(fluentWebElement1.isSelected()).thenReturn(true);
        when(fluentWebElement2.isSelected()).thenReturn(true);
        when(fluentWebElement3.isSelected()).thenReturn(true);
        matcher.isSelected();

        verify(fluentWebElement1, atLeastOnce()).isSelected();
        verify(fluentWebElement2, atLeastOnce()).isSelected();
        verify(fluentWebElement3, atLeastOnce()).isSelected();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isSelected();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isDisplayed() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isDisplayed();
        verify(fluentWebElement2, atLeastOnce()).isDisplayed();
        verify(fluentWebElement3, atLeastOnce()).isDisplayed();

        when(fluentWebElement1.isDisplayed()).thenReturn(true);
        when(fluentWebElement2.isDisplayed()).thenReturn(true);
        when(fluentWebElement3.isDisplayed()).thenReturn(true);
        matcher.isDisplayed();

        verify(fluentWebElement1, atLeastOnce()).isDisplayed();
        verify(fluentWebElement2, atLeastOnce()).isDisplayed();
        verify(fluentWebElement3, atLeastOnce()).isDisplayed();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isClickable() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isClickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isClickable();
        verify(fluentWebElement2, atLeastOnce()).isClickable();
        verify(fluentWebElement3, atLeastOnce()).isClickable();

        when(fluentWebElement1.isClickable()).thenReturn(true);
        when(fluentWebElement2.isClickable()).thenReturn(true);
        when(fluentWebElement3.isClickable()).thenReturn(true);
        matcher.isClickable();

        verify(fluentWebElement1, atLeastOnce()).isClickable();
        verify(fluentWebElement2, atLeastOnce()).isClickable();
        verify(fluentWebElement3, atLeastOnce()).isClickable();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isClickable();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isStale() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isStale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isStale();
        verify(fluentWebElement2, atLeastOnce()).isStale();
        verify(fluentWebElement3, atLeastOnce()).isStale();

        when(fluentWebElement1.isStale()).thenReturn(true);
        when(fluentWebElement2.isStale()).thenReturn(true);
        when(fluentWebElement3.isStale()).thenReturn(true);
        matcher.isStale();

        verify(fluentWebElement1, atLeastOnce()).isStale();
        verify(fluentWebElement2, atLeastOnce()).isStale();
        verify(fluentWebElement3, atLeastOnce()).isStale();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isStale();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasSize() {
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
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
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);
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
        final FluentWaitElementListMatcher matcher = new FluentWaitElementListMatcher(search, wait, fluentWebElements);

        when(element1.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element2.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element3.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.hasRectangle().withX(5);
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, atLeastOnce()).getRect();
        verify(element3, atLeastOnce()).getRect();

        matcher.hasRectangle().withX(1);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, atLeastOnce()).getRect();
        verify(element3, atLeastOnce()).getRect();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().hasRectangle().withX(1);
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

}
