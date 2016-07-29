package org.fluentlenium.core.domain;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private FluentList<FluentWebElement> emptyList = new FluentListImpl<>();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        when(element1.conditions()).thenReturn(new WebElementConditions(element1));
        when(element2.conditions()).thenReturn(new WebElementConditions(element2));
        when(element3.conditions()).thenReturn(new WebElementConditions(element3));


        list = new FluentListImpl<>();
        list.add(element1);
        list.add(element2);
        list.add(element3);
    }

    @After
    public void after() {
        reset(element1, element2, element3);
    }

    @Test
    public void testFirst() {
        assertThat(list.first()).isSameAs(element1);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                emptyList.first();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testLast() {
        assertThat(list.last()).isSameAs(element3);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                emptyList.last();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testFromToElements() {
        WebElement webElement1 = mock(WebElement.class);
        WebElement webElement2 = mock(WebElement.class);
        WebElement webElement3 = mock(WebElement.class);

        FluentListImpl<FluentWebElement> list = FluentListImpl.fromElements(webElement1, webElement2, webElement3);
        assertThat(list.toElements()).containsExactly(webElement1, webElement2, webElement3);
    }

    @Test
    public void testEach() {
        when(element1.isEnabled()).thenReturn(true);
        when(element2.isEnabled()).thenReturn(true);
        when(element3.isEnabled()).thenReturn(true);

        assertThat(list.each().isEnabled()).isTrue();

        verify(element1).isEnabled();
        verify(element2).isEnabled();
        verify(element3).isEnabled();
    }

    @Test
    public void testOne() {
        when(element2.isEnabled()).thenReturn(true);
        when(element3.isEnabled()).thenReturn(true);

        assertThat(list.one().isEnabled()).isTrue();

        verify(element1).isEnabled();
        verify(element2).isEnabled();
        verify(element3, never()).isEnabled();
    }

    @Test
    public void testClick() {
        when(element2.isEnabled()).thenReturn(true);
        when(element3.isEnabled()).thenReturn(true);

        list.click();

        verify(element1, never()).click();
        verify(element2).click();
        verify(element3).click();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                emptyList.click();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testText() {
        when(element2.isDisplayed()).thenReturn(true);
        when(element3.isDisplayed()).thenReturn(true);
        when(element2.isEnabled()).thenReturn(true);
        when(element3.isEnabled()).thenReturn(true);

        list.text("abc");

        verify(element1, never()).text("abc");
        verify(element2).text("abc");
        verify(element3).text("abc");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                emptyList.text("abc");
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                list.text("abc");
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testSubmit() {
        when(element2.isEnabled()).thenReturn(true);
        when(element3.isEnabled()).thenReturn(true);

        list.submit();

        verify(element1, never()).submit();
        verify(element2).submit();
        verify(element3).submit();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                emptyList.submit();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testClearAll() {
        when(element2.isEnabled()).thenReturn(true);
        when(element3.isEnabled()).thenReturn(true);

        list.clearAll();

        verify(element1, never()).clear();
        verify(element2).clear();
        verify(element3).clear();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                emptyList.clearAll();
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testProperties() {
        when(element1.getValue()).thenReturn("1");
        when(element2.getValue()).thenReturn("2");
        when(element3.getValue()).thenReturn("3");

        assertThat(list.getValues()).containsExactly("1", "2", "3");
        assertThat(list.getValue()).isEqualTo("1");
        assertThat(emptyList.getValues()).isEmpty();
        assertThat(emptyList.getValue()).isNull();
        reset(element1, element2, element3);

        when(element1.getId()).thenReturn("1");
        when(element2.getId()).thenReturn("2");
        when(element3.getId()).thenReturn("3");

        assertThat(list.getIds()).containsExactly("1", "2", "3");
        assertThat(list.getId()).isEqualTo("1");
        assertThat(emptyList.getIds()).isEmpty();
        assertThat(emptyList.getId()).isNull();
        reset(element1, element2, element3);


        when(element1.getAttribute("attr")).thenReturn("1");
        when(element2.getAttribute("attr")).thenReturn("2");
        when(element3.getAttribute("attr")).thenReturn("3");

        assertThat(list.getAttributes("attr")).containsExactly("1", "2", "3");
        assertThat(list.getAttribute("attr")).isEqualTo("1");
        assertThat(emptyList.getAttributes("attr")).isEmpty();
        assertThat(emptyList.getAttribute("attr")).isNull();
        reset(element1, element2, element3);

        when(element1.getName()).thenReturn("1");
        when(element2.getName()).thenReturn("2");
        when(element3.getName()).thenReturn("3");

        assertThat(list.getNames()).containsExactly("1", "2", "3");
        assertThat(list.getName()).isEqualTo("1");
        assertThat(emptyList.getNames()).isEmpty();
        assertThat(emptyList.getName()).isNull();
        reset(element1, element2, element3);

        when(element1.getTagName()).thenReturn("1");
        when(element2.getTagName()).thenReturn("2");
        when(element3.getTagName()).thenReturn("3");

        assertThat(list.getTagNames()).containsExactly("1", "2", "3");
        assertThat(list.getTagName()).isEqualTo("1");
        assertThat(emptyList.getTagNames()).isEmpty();
        assertThat(emptyList.getTagName()).isNull();
        reset(element1, element2, element3);

        when(element1.getText()).thenReturn("1");
        when(element2.getText()).thenReturn("2");
        when(element3.getText()).thenReturn("3");

        assertThat(list.getTexts()).containsExactly("1", "2", "3");
        assertThat(list.getText()).isEqualTo("1");
        assertThat(emptyList.getTexts()).isEmpty();
        assertThat(emptyList.getText()).isNull();
        reset(element1, element2, element3);

        when(element1.getTextContent()).thenReturn("1");
        when(element2.getTextContent()).thenReturn("2");
        when(element3.getTextContent()).thenReturn("3");

        assertThat(list.getTextContents()).containsExactly("1", "2", "3");
        assertThat(list.getTextContent()).isEqualTo("1");
        assertThat(emptyList.getTextContents()).isEmpty();
        assertThat(emptyList.getTextContent()).isNull();
        reset(element1, element2, element3);

        when(element1.getValue()).thenReturn("1");
        when(element2.getValue()).thenReturn("2");
        when(element3.getValue()).thenReturn("3");

        assertThat(list.getValues()).containsExactly("1", "2", "3");
        assertThat(list.getValue()).isEqualTo("1");
        assertThat(emptyList.getValues()).isEmpty();
        assertThat(emptyList.getValue()).isNull();

        reset(element1, element2, element3);
    }

    @Test
    public void testFind() {
        FluentWebElement ret1 = mock(FluentWebElement.class);
        FluentWebElement ret2 = mock(FluentWebElement.class);
        FluentWebElement ret3 = mock(FluentWebElement.class);

        when(element1.find()).thenReturn(new FluentListImpl<FluentWebElement>(ret1));
        when(element2.find()).thenReturn(new FluentListImpl<FluentWebElement>(ret2));
        when(element3.find()).thenReturn(new FluentListImpl<FluentWebElement>(ret3));

        assertThat(list.findFirst()).isSameAs(ret1);
        assertThat(list.find()).containsExactly(ret1, ret2, ret3);
        assertThat(list.find(1)).isSameAs(ret2);
        assertThat(list.$()).containsExactly(ret1, ret2, ret3);
        assertThat(list.$(1)).isSameAs(ret2);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                list.find(3);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);

        when(element1.find(".test")).thenReturn(new FluentListImpl<FluentWebElement>(ret1));
        when(element2.find(".test")).thenReturn(new FluentListImpl<FluentWebElement>(ret2));
        when(element3.find(".test")).thenReturn(new FluentListImpl<FluentWebElement>(ret3));

        assertThat(list.findFirst(".test")).isSameAs(ret1);
        assertThat(list.find(".test")).containsExactly(ret1, ret2, ret3);
        assertThat(list.find(".test", 1)).isSameAs(ret2);
        assertThat(list.$(".test")).containsExactly(ret1, ret2, ret3);
        assertThat(list.$(".test", 1)).isSameAs(ret2);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                list.find(".test", 3);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);

        when(element1.find(By.cssSelector(".test"))).thenReturn(new FluentListImpl<FluentWebElement>(ret1));
        when(element2.find(By.cssSelector(".test"))).thenReturn(new FluentListImpl<FluentWebElement>(ret2));
        when(element3.find(By.cssSelector(".test"))).thenReturn(new FluentListImpl<FluentWebElement>(ret3));

        assertThat(list.findFirst(By.cssSelector(".test"))).isSameAs(ret1);
        assertThat(list.find(By.cssSelector(".test"))).containsExactly(ret1, ret2, ret3);
        assertThat(list.find(By.cssSelector(".test"), 1)).isSameAs(ret2);
        assertThat(list.$(By.cssSelector(".test"))).containsExactly(ret1, ret2, ret3);
        assertThat(list.$(By.cssSelector(".test"), 1)).isSameAs(ret2);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                list.find(By.cssSelector(".test"), 3);
            }
        }).isExactlyInstanceOf(NoSuchElementException.class);

        reset(element1, element2, element3);
    }

    @Test
    public void testAs() {
        FluentList<Component> as = list.as(Component.class);
        assertThat(as).hasSameSizeAs(list);
    }


    private static class Component extends FluentWebElement {
        public Component(WebElement element) {
            super(element);
        }
    }

}
