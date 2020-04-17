package org.fluentlenium.core.domain;

import com.google.common.collect.Lists;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link FluentListImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount"})
public class FluentListImplTest {
    @Mock
    private FluentWebElement element1;

    @Mock
    private FluentWebElement element2;

    @Mock
    private FluentWebElement element3;

    @Mock
    private WebDriver driver;

    private FluentList<FluentWebElement> list;
    private FluentList<FluentWebElement> singleList;
    private FluentList<FluentWebElement> emptyList;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(driver);

        emptyList = fluentAdapter.newFluentList();

        when(element1.conditions()).thenReturn(new WebElementConditions(element1));
        when(element2.conditions()).thenReturn(new WebElementConditions(element2));
        when(element3.conditions()).thenReturn(new WebElementConditions(element3));

        list = spy(fluentAdapter.newFluentList(element1, element2, element3));
        singleList = spy(fluentAdapter.newFluentList(element2));
    }

    @After
    public void after() {
        reset(element1, element2, element3);
    }

    @Test
    public void testFirst() {
        assertThat(list.first()).isSameAs(element1);

        assertThatThrownBy(() -> emptyList.first()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testLast() {
        assertThat(list.last()).isSameAs(element3);

        assertThatThrownBy(() -> emptyList.last()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testSingle() {
        assertThat(singleList.single()).isSameAs(element2);

        assertThatThrownBy(() -> list.single()).isExactlyInstanceOf(AssertionError.class)
                                               .hasMessageContaining("list should contain one element only but there are");
    }

    //count()

    @Test
    public void shouldReturnListCountWhenProxyExists() {
        assertThat(list.count()).isEqualTo(3);
    }

    @Test
    public void shouldReturnTrueForPresentIfListSizeIsGreaterThanZeroWhenNoLocatorHandlerIsAvailableForProxy() {
        assertThat(list.present()).isTrue();
    }

    @Test
    public void shouldReturnFalseForPresentIfListSizeNotGreaterThanZeroWhenNoLocatorHandlerIsAvailableForProxy() {
        assertThat(emptyList.present()).isFalse();
    }

    //waitAndClick()

    @Test
    public void shouldThrowNoSuchElementExceptionForWaitAndClickWhenListIsEmpty() {
        assertThatThrownBy(() -> emptyList.waitAndClick()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void shouldThrowNoSuchElementExceptionForWaitAndClickSecondsWhenListIsEmpty() {
        assertThatThrownBy(() -> emptyList.waitAndClick(Duration.ofSeconds(4))).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testEach() {
        when(element1.enabled()).thenReturn(true);
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        assertThat(list.each().enabled()).isTrue();

        verify(element1).enabled();
        verify(element2).enabled();
        verify(element3).enabled();
    }

    @Test
    public void testOne() {
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        assertThat(list.one().enabled()).isTrue();

        verify(element1).enabled();
        verify(element2).enabled();
        verify(element3, never()).enabled();
    }

    @Test
    public void testClick() {
        when(element2.conditions().clickable()).thenReturn(true);
        when(element3.conditions().clickable()).thenReturn(true);

        list.click();

        verify(element1, never()).click();
        verify(element2).click();
        verify(element3).click();

        assertThatThrownBy(() -> emptyList.click()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.conditions().clickable()).thenReturn(false);
        when(element3.conditions().clickable()).thenReturn(false);

        assertThatThrownBy(() -> list.contextClick()).isExactlyInstanceOf(NoSuchElementException.class)
                                                     .hasMessageContaining("has no element clickable");
    }

    @Test
    public void testDoubleClick() {
        when(element2.conditions().clickable()).thenReturn(true);
        when(element3.conditions().clickable()).thenReturn(true);

        list.doubleClick();

        verify(element1, never()).doubleClick();
        verify(element2).doubleClick();
        verify(element3).doubleClick();

        assertThatThrownBy(() -> emptyList.doubleClick()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.conditions().clickable()).thenReturn(false);
        when(element3.conditions().clickable()).thenReturn(false);

        assertThatThrownBy(() -> list.contextClick()).isExactlyInstanceOf(NoSuchElementException.class)
                                                     .hasMessageContaining("has no element clickable");
    }

    @Test
    public void testContextClick() {
        when(element2.conditions().clickable()).thenReturn(true);
        when(element3.conditions().clickable()).thenReturn(true);

        list.contextClick();

        verify(element1, never()).contextClick();
        verify(element2).contextClick();
        verify(element3).contextClick();

        assertThatThrownBy(() -> emptyList.contextClick()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.conditions().clickable()).thenReturn(false);
        when(element3.conditions().clickable()).thenReturn(false);

        assertThatThrownBy(() -> list.contextClick()).isExactlyInstanceOf(NoSuchElementException.class)
                                                     .hasMessageContaining("has no element clickable");
    }

    @Test
    public void testText() {
        when(element2.displayed()).thenReturn(true);
        when(element3.displayed()).thenReturn(true);
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        list.write("abc");

        verify(element1, never()).write("abc");
        verify(element2).write("abc");
        verify(element3).write("abc");

        assertThatThrownBy(() -> emptyList.write("abc")).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);

        assertThatThrownBy(() -> list.write("abc")).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testSubmit() {
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        list.submit();

        verify(element1, never()).submit();
        verify(element2).submit();
        verify(element3).submit();

        assertThatThrownBy(() -> emptyList.submit()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.enabled()).thenReturn(false);
        when(element3.enabled()).thenReturn(false);

        assertThatThrownBy(() -> list.submit()).isExactlyInstanceOf(NoSuchElementException.class)
                                               .hasMessageContaining("has no element enabled");
    }

    @Test
    public void shouldDoClear() {
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        list.clear();

        verify(element1, never()).clear();
        verify(element2).clear();
        verify(element3).clear();

        assertThatThrownBy(() -> emptyList.clear()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.enabled()).thenReturn(false);
        when(element3.enabled()).thenReturn(false);

        assertThatThrownBy(() -> list.clear()).isExactlyInstanceOf(NoSuchElementException.class)
                                              .hasMessageContaining("has no element enabled");
    }

    @Test
    public void testClearAll() {
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        list.clearAll();

        verify(element1, never()).clear();
        verify(element2).clear();
        verify(element3).clear();

        assertThatThrownBy(() -> emptyList.clearAll()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.enabled()).thenReturn(false);
        when(element3.enabled()).thenReturn(false);

        assertThatThrownBy(() -> list.clearAll()).isExactlyInstanceOf(NoSuchElementException.class)
                                                 .hasMessageContaining("has no element enabled");
    }

    @Test
    public void testClearAllReactInputs() {
        when(element2.enabled()).thenReturn(true);
        when(element3.enabled()).thenReturn(true);

        list.clearAllReactInputs();

        verify(element1, never()).clearReactInput();
        verify(element2).clearReactInput();
        verify(element3).clearReactInput();

        assertThatThrownBy(() -> emptyList.clearAllReactInputs()).isExactlyInstanceOf(NoSuchElementException.class);

        when(element2.enabled()).thenReturn(false);
        when(element3.enabled()).thenReturn(false);

        assertThatThrownBy(() -> list.clearAllReactInputs()).isExactlyInstanceOf(NoSuchElementException.class)
                                                            .hasMessageContaining("has no element enabled");
    }

    @Test
    public void shouldClearList() {
        list.clearList();

        assertThat(((FluentListImpl<FluentWebElement>) list).getList()).isEmpty();
    }

    @Test
    public void shouldReturnOptionalIfListIsPresent() {
        assertThat(list.optional()).hasValue(list);
    }

    @Test
    public void shouldReturnEmptyOptionalIfListIsNotPresent() {
        assertThat(emptyList.optional()).isEmpty();
    }

    @Test
    public void testProperties() {
        when(element1.value()).thenReturn("1");
        when(element2.value()).thenReturn("2");
        when(element3.value()).thenReturn("3");

        assertThat(list.values()).containsExactly("1", "2", "3");
        assertThat(list.first().value()).isEqualTo("1");
        assertThat(emptyList.values()).isEmpty();

        assertThatThrownBy(() -> emptyList.first().value()).isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.id()).thenReturn("1");
        when(element2.id()).thenReturn("2");
        when(element3.id()).thenReturn("3");

        assertThat(list.ids()).containsExactly("1", "2", "3");
        assertThat(list.first().id()).isEqualTo("1");
        assertThat(emptyList.ids()).isEmpty();
        assertThatThrownBy(() -> emptyList.first().id()).isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.attribute("attr")).thenReturn("1");
        when(element2.attribute("attr")).thenReturn("2");
        when(element3.attribute("attr")).thenReturn("3");

        assertThat(list.attributes("attr")).containsExactly("1", "2", "3");
        assertThat(list.first().attribute("attr")).isEqualTo("1");
        assertThat(emptyList.attributes("attr")).isEmpty();
        assertThatThrownBy(() -> emptyList.frame().first().attribute("attr"))
                .isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.name()).thenReturn("1");
        when(element2.name()).thenReturn("2");
        when(element3.name()).thenReturn("3");

        assertThat(list.names()).containsExactly("1", "2", "3");
        assertThat(list.first().name()).isEqualTo("1");
        assertThat(emptyList.names()).isEmpty();
        assertThatThrownBy(() -> emptyList.first().name()).isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.tagName()).thenReturn("1");
        when(element2.tagName()).thenReturn("2");
        when(element3.tagName()).thenReturn("3");

        assertThat(list.tagNames()).containsExactly("1", "2", "3");
        assertThat(list.first().tagName()).isEqualTo("1");
        assertThat(emptyList.tagNames()).isEmpty();
        assertThatThrownBy(() -> emptyList.first().tagName()).isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.text()).thenReturn("1");
        when(element2.text()).thenReturn("2");
        when(element3.text()).thenReturn("3");

        assertThat(list.texts()).containsExactly("1", "2", "3");
        assertThat(list.first().text()).isEqualTo("1");
        assertThat(emptyList.texts()).isEmpty();
        assertThatThrownBy(() -> emptyList.first().text()).isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.textContent()).thenReturn("1");
        when(element2.textContent()).thenReturn("2");
        when(element3.textContent()).thenReturn("3");

        assertThat(list.textContents()).containsExactly("1", "2", "3");
        assertThat(list.first().textContent()).isEqualTo("1");
        assertThat(emptyList.textContents()).isEmpty();
        assertThatThrownBy(() -> emptyList.first().textContent()).isExactlyInstanceOf(NoSuchElementException.class);
        reset(element1, element2, element3);

        when(element1.value()).thenReturn("1");
        when(element2.value()).thenReturn("2");
        when(element3.value()).thenReturn("3");

        assertThat(list.values()).containsExactly("1", "2", "3");
        assertThat(list.first().value()).isEqualTo("1");
        assertThat(emptyList.values()).isEmpty();
        assertThatThrownBy(() -> emptyList.first().value()).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);
    }

    @Test
    public void testFind() {
        FluentWebElement ret1 = mock(FluentWebElement.class);
        FluentWebElement ret2 = mock(FluentWebElement.class);
        FluentWebElement ret3 = mock(FluentWebElement.class);

        when(element1.find()).thenReturn(fluentAdapter.newFluentList(ret1));
        when(element2.find()).thenReturn(fluentAdapter.newFluentList(ret2));
        when(element3.find()).thenReturn(fluentAdapter.newFluentList(ret3));

        assertThat(list.el()).isSameAs(ret1);
        assertThat(list.find()).containsExactly(ret1, ret2, ret3);
        assertThat(list.find().index(1)).isSameAs(ret2);
        assertThat(list.$()).containsExactly(ret1, ret2, ret3);
        assertThat(list.$().index(1)).isSameAs(ret2);

        assertThatThrownBy(() -> list.find().index(3)).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);

        when(element1.find(".test")).thenReturn(fluentAdapter.newFluentList(ret1));
        when(element2.find(".test")).thenReturn(fluentAdapter.newFluentList(ret2));
        when(element3.find(".test")).thenReturn(fluentAdapter.newFluentList(ret3));

        assertThat(list.el(".test")).isSameAs(ret1);
        assertThat(list.find(".test")).containsExactly(ret1, ret2, ret3);
        assertThat(list.find(".test").index(1)).isSameAs(ret2);
        assertThat(list.$(".test")).containsExactly(ret1, ret2, ret3);
        assertThat(list.$(".test").index(1)).isSameAs(ret2);

        assertThatThrownBy(() -> list.find(".test").index(3)).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);

        when(element1.find(By.cssSelector(".test"))).thenReturn(fluentAdapter.newFluentList(ret1));
        when(element2.find(By.cssSelector(".test"))).thenReturn(fluentAdapter.newFluentList(ret2));
        when(element3.find(By.cssSelector(".test"))).thenReturn(fluentAdapter.newFluentList(ret3));

        assertThat(list.el(By.cssSelector(".test"))).isSameAs(ret1);
        assertThat(list.find(By.cssSelector(".test"))).containsExactly(ret1, ret2, ret3);
        assertThat(list.find(By.cssSelector(".test")).index(1)).isSameAs(ret2);
        assertThat(list.$(By.cssSelector(".test"))).containsExactly(ret1, ret2, ret3);
        assertThat(list.$(By.cssSelector(".test")).index(1)).isSameAs(ret2);

        assertThatThrownBy(() -> list.find(By.cssSelector(".test")).index(3)).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);
    }

    @Test
    public void testNowTrue() {
        list.now(true);
        verify(list).reset();
        verify(list).now();
    }

    @Test
    public void testNowFalse() {
        list.now(false);
        verify(list, never()).reset();
        verify(list).now();
    }

    @Test
    public void testAs() {
        FluentList<Component> as = list.as(Component.class);
        assertThat(as).hasSameSizeAs(list);
    }

    @Test
    public void testToElements() {
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        WebElement webElement3 = mock(WebElement.class);

        when(element1.getElement()).thenReturn(webElement1);
        when(element2.getElement()).thenReturn(webElement2);
        when(element3.getElement()).thenReturn(webElement3);

        List<WebElement> expectedElements = Lists.newArrayList(webElement1, webElement2, webElement3);

        assertThat(list.toElements()).containsExactlyInAnyOrderElementsOf(expectedElements);
    }

    private static class Component extends FluentWebElement {
        Component(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

}
