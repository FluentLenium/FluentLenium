package org.fluentlenium.unit;


import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.wait.FluentWaitMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.support.ui.FluentWait;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AwaitMessageTest {

    @Mock
    FluentWait wait;

    FluentWaitMatcher builder;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        builder = new FluentWaitMatcher(mock(Search.class), wait, "select");
        when(wait.withMessage(anyString())).thenReturn(wait);
    }

    @Test
    public void when_is_present_and_no_filter_then_print_only_principal_message() {
        builder.isPresent();
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select");
    }

    @Test
    public void when_is_present_filter_then_print_principal_message_and_filters() {
        builder.withId("myId");
        builder.with("custom").equalTo("myCustom");
        builder.isPresent();
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("[id=\"myId\"]").contains("[custom=\"myCustom\"]");
    }

    @Test
    public void when_has_size_filter_then_print_principal_message() {
        builder.hasSize(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("size").contains("5");
    }

    @Test
    public void when_has_attribute_then_print_principal_message() {
        builder.hasAttribute("attr", "val");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("attr").contains("val");
    }

    @Test
    public void when_has_id_then_print_principal_message() {
        builder.hasId("myId");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("id").contains("myId");
    }

    @Test
    public void when_has_text_then_print_principal_message() {
        builder.hasText("myText");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("text").contains("myText");
    }

    @Test
    public void when_has_name_then_print_principal_message() {
        builder.hasName("myName");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("name").contains("myName");
    }


    @Test
    public void when_has_size_equalTo_5_then_print_principal_message() {
        builder.hasSize().equalTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("size").contains("5");
    }

    @Test
    public void when_has_size_not_equalTo_5_then_print_principal_message() {
        builder.hasSize().notEqualTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("is equal").contains("5");
    }

    @Test
    public void when_has_size_less_than_then_print_principal_message() {
        builder.hasSize().lessThan(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("less than").contains("5");
    }

    @Test
    public void when_has_size_less_than_or_equalTo_5_then_print_principal_message() {
        builder.hasSize().lessThanOrEqualTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("less").contains("equal").contains("5");
    }

    @Test
    public void when_has_size_greather_than_then_print_principal_message() {
        builder.hasSize().greaterThan(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("greather than").contains("5");
    }

    @Test
    public void when_has_size_greather_or_equal_to_than_then_print_principal_message() {
        builder.hasSize().greaterThanOrEqualTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("greather than").contains("equal").contains("5");
    }

}
