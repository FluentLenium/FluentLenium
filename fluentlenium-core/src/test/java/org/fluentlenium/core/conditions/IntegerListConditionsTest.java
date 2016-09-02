package org.fluentlenium.core.conditions;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IntegerListConditionsTest {

    @Mock
    private WebElement webElement1;

    @Mock
    private WebElement webElement2;

    @Mock
    private WebElement webElement3;

    @Mock
    private WebDriver driver;

    private FluentWebElement fluentWebElement1;

    private FluentWebElement fluentWebElement2;

    private FluentWebElement fluentWebElement3;

    @Before
    public void before() {
        FluentAdapter fluentAdapter = new FluentAdapter(driver);
        DefaultComponentInstantiator instantiator = new DefaultComponentInstantiator(fluentAdapter);

        fluentWebElement1 = new FluentWebElement(webElement1, fluentAdapter, instantiator);
        fluentWebElement2 = new FluentWebElement(webElement2, fluentAdapter, instantiator);
        fluentWebElement3 = new FluentWebElement(webElement3, fluentAdapter, instantiator);
    }

    @After
    public void after() {
        reset(webElement1);
        reset(webElement2);
        reset(webElement3);
    }

    @Test
    public void fromEachElementConditions() {
        EachElementConditions conditions = new EachElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        final IntegerListConditionsImpl integerConditions = new IntegerListConditionsImpl(conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return Integer.valueOf(input.getId());
            }
        });

        when(webElement1.getAttribute("id")).thenReturn("1");
        when(webElement2.getAttribute("id")).thenReturn("1");
        when(webElement3.getAttribute("id")).thenReturn("1");

        assertThat(integerConditions.isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 1;
            }
        }));

        IntegerConditionsTest.assertConditions(integerConditions, 1);
        IntegerConditionsTest.assertNotConditions(integerConditions.not(), 1);

        when(webElement1.getAttribute("id")).thenReturn("1");
        when(webElement2.getAttribute("id")).thenReturn("2");
        when(webElement3.getAttribute("id")).thenReturn("1");

        assertThat(integerConditions.isVerified(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input == 1 || input == 2;
            }
        }));

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                IntegerConditionsTest.assertConditions(integerConditions, 1);
            }
        });


    }

    @Test
    public void fromAtLeastOneElementConditions() {
        AtLeastOneElementConditions conditions = new AtLeastOneElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));

        final IntegerListConditionsImpl integerConditions = new IntegerListConditionsImpl(conditions, new Function<FluentWebElement, Integer>() {
            @Override
            public Integer apply(FluentWebElement input) {
                return Integer.valueOf(input.getId());
            }
        });

        when(webElement1.getAttribute("id")).thenReturn("1");
        when(webElement2.getAttribute("id")).thenReturn("1");
        when(webElement3.getAttribute("id")).thenReturn("1");

        IntegerConditionsTest.assertConditions(integerConditions, 1);
        IntegerConditionsTest.assertNotConditions(integerConditions.not(), 1);

        when(webElement1.getAttribute("id")).thenReturn("1");
        when(webElement2.getAttribute("id")).thenReturn("2");
        when(webElement3.getAttribute("id")).thenReturn("3");

        assertThat(integerConditions.equalTo(1)).isTrue();
        assertThat(integerConditions.equalTo(2)).isTrue();
        assertThat(integerConditions.equalTo(3)).isTrue();

        assertThat(integerConditions.greaterThanOrEqualTo(1)).isTrue();
        assertThat(integerConditions.greaterThanOrEqualTo(2)).isTrue();
        assertThat(integerConditions.greaterThanOrEqualTo(3)).isTrue();

        assertThat(integerConditions.greaterThan(1)).isTrue();
        assertThat(integerConditions.greaterThan(2)).isTrue();
        assertThat(integerConditions.greaterThan(3)).isFalse();

        assertThat(integerConditions.lessThanOrEqualTo(1)).isTrue();
        assertThat(integerConditions.lessThanOrEqualTo(2)).isTrue();
        assertThat(integerConditions.lessThanOrEqualTo(3)).isTrue();

        assertThat(integerConditions.lessThan(1)).isFalse();
        assertThat(integerConditions.lessThan(2)).isTrue();
        assertThat(integerConditions.lessThan(3)).isTrue();

        when(webElement1.getAttribute("id")).thenReturn("2");
        when(webElement2.getAttribute("id")).thenReturn("3");
        when(webElement3.getAttribute("id")).thenReturn("4");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                IntegerConditionsTest.assertConditions(integerConditions, 1);
            }
        });
    }

}

