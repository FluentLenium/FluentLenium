package org.fluentlenium.assertj.custom;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentWebElementAssertTest {

    @Mock
    private FluentWebElement element;

    @Test
    public void isPresent() {
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                assertThat(element).isPresent();
            }
        }).hasMessage("element is not present");

        when(element.present()).thenReturn(true);
        assertThat(element).isPresent();
    }

    @Test
    public void isNotPresent() {
        when(element.present()).thenReturn(true);
        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                assertThat(element).isNotPresent();
            }
        }).hasMessage("element is present");

        when(element.present()).thenReturn(false);
        assertThat(element).isNotPresent();
    }

}
