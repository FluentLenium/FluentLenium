package org.fluentlenium.core.wait;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitElementListTest {
    @Mock
    private FluentWait fluentControlWait;

    @Mock
    private List<FluentWebElement> fluentList;

    private FluentWaitElementList wait;

    @Before
    public void before() {
        wait = new FluentWaitElementList(fluentControlWait, fluentList);
    }

    @Test
    public void until() {
        wait.until();
        Mockito.verify(fluentControlWait).until(fluentList);
    }

    @Test
    public void untilEachNoParam() {
        wait.untilEach();
        Mockito.verify(fluentControlWait).untilEach(fluentList);
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
        Collection<Class<? extends Throwable>> classes = new ArrayList<>();

        assertThat(wait.ignoreAll(classes)).isSameAs(wait);
        Mockito.verify(fluentControlWait).ignoreAll(classes);
    }

    @Test
    public void ignoring() {
        Class<? extends RuntimeException> exceptionType = RuntimeException.class;

        assertThat(wait.ignoring(exceptionType)).isSameAs(wait);
        Mockito.verify(fluentControlWait).ignoring(exceptionType);
    }

    @Test
    public void ignoringTwoTypes() {
        Class<? extends RuntimeException> firstType = RuntimeException.class;
        Class<? extends RuntimeException> secondType = RuntimeException.class;

        assertThat(wait.ignoring(firstType, secondType)).isSameAs(wait);
        Mockito.verify(fluentControlWait).ignoring(firstType, secondType);
    }

    @Test
    public void untilPredicate() {
        Predicate<FluentControl> predicate = mock(Predicate.class);

        wait.untilPredicate(predicate);
        Mockito.verify(fluentControlWait).untilPredicate(predicate);
    }

    @Test
    public void withMessage() {
        String message = "test";

        wait.withMessage(message);
        Mockito.verify(fluentControlWait).withMessage(message);
    }

    @Test
    public void withMessageSupplier() {
        Supplier<String> message = () -> "test";

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
        FluentWebElement element = mock(FluentWebElement.class);

        wait.until(element);
        Mockito.verify(fluentControlWait).until(element);
    }

    @Test
    public void untilElements() {
        FluentList<? extends FluentWebElement> elements = mock(FluentList.class);

        wait.until(elements);
        Mockito.verify(fluentControlWait).until(elements);
    }

    @Test
    public void untilEach() {
        FluentList<? extends FluentWebElement> elements = mock(FluentList.class);

        wait.untilEach(elements);
        Mockito.verify(fluentControlWait).untilEach(elements);
    }

    @Test
    public void untilElementSupplier() {
        Supplier<? extends FluentWebElement> selector = mock(Supplier.class);

        wait.untilElement(selector);
        Mockito.verify(fluentControlWait).untilElement(selector);
    }

    @Test
    public void untilElementsSupplier() {
        Supplier<? extends List<? extends FluentWebElement>> selector = mock(Supplier.class);

        wait.untilElements(selector);
        Mockito.verify(fluentControlWait).untilElements(selector);
    }

    @Test
    public void untilEachElements() {
        Supplier<? extends List<? extends FluentWebElement>> selector = mock(Supplier.class);

        wait.untilEachElements(selector);
        Mockito.verify(fluentControlWait).untilEachElements(selector);
    }

    @Test
    public void untilWindow() {
        String windowName = "test";

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
        FluentPage page = mock(FluentPage.class);

        wait.untilPage(page);
        Mockito.verify(fluentControlWait).untilPage(page);
    }

    @Test
    public void explicitlyFor() {
        long amount = 10;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        wait.explicitlyFor(amount, timeUnit);
        Mockito.verify(fluentControlWait).explicitlyFor(amount, timeUnit);
    }

    @Test
    public void explicitlyForMillis() {
        long amount = 10;

        wait.explicitlyFor(amount);
        Mockito.verify(fluentControlWait).explicitlyFor(amount);
    }

    @Test
    public void untilBooleanSupplier() {
        Supplier<Boolean> isTrue = mock(Supplier.class);

        wait.until(isTrue);
        Mockito.verify(fluentControlWait).until(isTrue);
    }

    @Test
    public void untilFunction() {
        Function<? super FluentControl, ?> isTrue = mock(Function.class);

        wait.until(isTrue);
        Mockito.verify(fluentControlWait).until(isTrue);
    }

    @Test
    public void useCustomMessage() {
        wait.hasMessageDefined();
        Mockito.verify(fluentControlWait).hasMessageDefined();
    }
}
