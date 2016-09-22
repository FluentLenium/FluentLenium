package org.fluentlenium.core.wait;


import org.fluentlenium.core.search.Search;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//TODO: Update this test
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AwaitMessageTest {

    @Mock
    FluentWait wait;

    @Mock
    Search search;

    FluentWaitLocatorSelectorMatcher builder;

    @Before
    public void before() {
        builder = new FluentWaitLocatorSelectorMatcher(search, wait, "select");
        when(wait.withMessage(anyString())).thenReturn(wait);
    }

    @Test
    public void when_is_present_and_no_filter_then_print_only_principal_message() {
        builder.present();
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select");
    }

    @Test
    public void when_is_present_filter_then_print_principal_message_and_filters() {
        builder.withId("myId");
        builder.with("custom").equalTo("myCustom");
        builder.present();
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("[id=\"myId\"]").contains("[custom=\"myCustom\"]");
    }

    @Test
    public void when_has_size_filter_then_print_principal_message() {
        builder.each().hasSize(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("size").contains("5");
    }

    @Test
    public void when_has_attribute_then_print_principal_message() {
        builder.attribute("attr", "val");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("attr").contains("val");
    }

    @Test
    public void when_has_id_then_print_principal_message() {
        builder.id("myId");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("id").contains("myId");
    }

    @Test
    public void when_has_text_then_print_principal_message() {
        builder.text().contains("myText");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("text").contains("myText");
    }

    @Test
    public void when_has_name_then_print_principal_message() {
        builder.name("myName");
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("name").contains("myName");
    }


    @Test
    public void when_has_size_equalTo_5_then_print_principal_message() {
        builder.size().equalTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("size").contains("5");
    }

    @Test
    public void when_has_size_not_equalTo_5_then_print_principal_message() {
        builder.size().not().equalTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("is equal").contains("5");
    }

    @Test
    public void when_has_size_less_than_then_print_principal_message() {
        builder.size().lessThan(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("less than").contains("5");
    }

    @Test
    public void when_has_size_less_than_or_equalTo_5_then_print_principal_message() {
        builder.size().lessThanOrEqualTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("less").contains("equal").contains("5");
    }

    @Test
    public void when_has_size_greater_than_then_print_principal_message() {
        builder.size().greaterThan(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("greater than").contains("5");
    }

    @Test
    public void when_has_size_greater_or_equal_to_than_then_print_principal_message() {
        builder.size().greaterThanOrEqualTo(5);
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(wait).withMessage(message.capture());
        assertThat(message.getValue()).contains("select").contains("greater than").contains("equal").contains("5");
    }

}
