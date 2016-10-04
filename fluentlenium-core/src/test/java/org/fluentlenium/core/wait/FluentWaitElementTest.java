package org.fluentlenium.core.wait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitElementTest {
    @Mock
    private FluentWait fluentControlWait;

    @Mock
    private FluentWebElement fluentWebElement;

    private FluentWaitElement wait;

    @Before
    public void before() {
        wait = new FluentWaitElement(fluentControlWait, fluentWebElement);
    }

    @Test
    public void until() {
        wait.until();
        Mockito.verify(fluentControlWait).until(fluentWebElement);
    }

    @Test
    public void getWait() {
        wait.getWait();
        Mockito.verify(fluentControlWait).getWait();
    }

    @Test
    public void atMost() {
        assertThat(wait.atMost(10, TimeUnit.MILLISECONDS)).isSameAs(wait);
        Mockito.verify(fluentControlWait).atMost(10, TimeUnit.MILLISECONDS);
    }

    @Test
    public void atMostMillis() {
        assertThat(wait.atMost(10)).isSameAs(wait);
        Mockito.verify(fluentControlWait).atMost(10);
    }

    @Test
    public void pollingEvery() {
        assertThat(wait.pollingEvery(10, TimeUnit.MILLISECONDS)).isSameAs(wait);
        Mockito.verify(fluentControlWait).pollingEvery(10, TimeUnit.MILLISECONDS);
    }

    @Test
    public void pollingEveryMillis() {
        assertThat(wait.pollingEvery(10)).isSameAs(wait);
        Mockito.verify(fluentControlWait).pollingEvery(10);
    }

    @Test
    public void ignoreAll() {
        final Collection<Class<? extends Throwable>> classes = new ArrayList<>();

        assertThat(wait.ignoreAll(classes)).isSameAs(wait);
        Mockito.verify(fluentControlWait).ignoreAll(classes);
    }

    @Test
    public void ignoring() {
        final Class<? extends RuntimeException> exceptionType = RuntimeException.class;

        assertThat(wait.ignoring(exceptionType)).isSameAs(wait);
        Mockito.verify(fluentControlWait).ignoring(exceptionType);
    }

    @Test
    public void ignoringTwoTypes() {
        final Class<? extends RuntimeException> firstType = RuntimeException.class;
        final Class<? extends RuntimeException> secondType = RuntimeException.class;

        assertThat(wait.ignoring(firstType, secondType)).isSameAs(wait);
        Mockito.verify(fluentControlWait).ignoring(firstType, secondType);
    }

    @Test
    public void untilPredicate() {
        final Predicate<FluentControl> predicate = mock(Predicate.class);

        wait.untilPredicate(predicate);
        Mockito.verify(fluentControlWait).untilPredicate(predicate);
    }

    @Test
    public void withMessage() {
        final String message = "test";

        wait.withMessage(message);
        Mockito.verify(fluentControlWait).withMessage(message);
    }

    @Test
    public void withMessageSupplier() {
        final Supplier<String> message = Suppliers.ofInstance("test");

        wait.withMessage(message);
        Mockito.verify(fluentControlWait).withMessage(message);
    }

    @Test
    public void withNoDefaultsException() {
        wait.withNoDefaultsException();
        Mockito.verify(fluentControlWait).withNoDefaultsException();
    }

    @Test
    public void untilElement() {
        final FluentWebElement element = mock(FluentWebElement.class);

        wait.until(element);
        Mockito.verify(fluentControlWait).until(element);
    }

    @Test
    public void untilElements() {
        final List<? extends FluentWebElement> elements = mock(List.class);

        wait.until(elements);
        Mockito.verify(fluentControlWait).until(elements);
    }

    @Test
    public void untilEach() {
        final List<? extends FluentWebElement> elements = mock(List.class);

        wait.untilEach(elements);
        Mockito.verify(fluentControlWait).untilEach(elements);
    }

    @Test
    public void untilElementSupplier() {
        final Supplier<? extends FluentWebElement> selector = mock(Supplier.class);

        wait.untilElement(selector);
        Mockito.verify(fluentControlWait).untilElement(selector);
    }

    @Test
    public void untilElementsSupplier() {
        final Supplier<? extends List<? extends FluentWebElement>> selector = mock(Supplier.class);

        wait.untilElements(selector);
        Mockito.verify(fluentControlWait).untilElements(selector);
    }

    @Test
    public void untilEachElements() {
        final Supplier<? extends List<? extends FluentWebElement>> selector = mock(Supplier.class);

        wait.untilEachElements(selector);
        Mockito.verify(fluentControlWait).untilEachElements(selector);
    }

    @Test
    public void untilWindow() {
        final String windowName = "test";

        wait.untilWindow(windowName);
        Mockito.verify(fluentControlWait).untilWindow(windowName);
    }

    @Test
    public void untilPage() {
        wait.untilPage();
        Mockito.verify(fluentControlWait).untilPage();
    }

    @Test
    public void untilPagePage() {
        final FluentPage page = mock(FluentPage.class);

        wait.untilPage(page);
        Mockito.verify(fluentControlWait).untilPage(page);
    }

    @Test
    public void explicitlyFor() {
        final long amount = 10;
        final TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        wait.explicitlyFor(amount, timeUnit);
        Mockito.verify(fluentControlWait).explicitlyFor(amount, timeUnit);
    }

    @Test
    public void explicitlyForMillis() {
        final long amount = 10;

        wait.explicitlyFor(amount);
        Mockito.verify(fluentControlWait).explicitlyFor(amount);
    }

    @Test
    public void untilBooleanSupplier() {
        final Supplier<Boolean> isTrue = mock(Supplier.class);

        wait.until(isTrue);
        Mockito.verify(fluentControlWait).until(isTrue);
    }

    @Test
    public void untilFunction() {
        final Function<? super FluentControl, ?> isTrue = mock(Function.class);

        wait.until(isTrue);
        Mockito.verify(fluentControlWait).until(isTrue);
    }

    @Test
    public void useCustomMessage() {
        wait.useCustomMessage();
        Mockito.verify(fluentControlWait).useCustomMessage();
    }
}
