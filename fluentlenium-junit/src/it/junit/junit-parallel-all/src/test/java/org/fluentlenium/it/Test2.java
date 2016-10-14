package org.fluentlenium.it;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test2 extends FluentIntegTest {
    @Test
    public void test5() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("5");

        await().explicitlyFor(5000L);

        assertThat(el("input").value()).isEqualTo("5");
    }

    @Test
    public void test6() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("6");

        await().explicitlyFor(4000L);

        assertThat(el("input").value()).isEqualTo("6");
    }

    @Test
    public void test7() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("7");

        await().explicitlyFor(3000L);

        assertThat(el("input").value()).isEqualTo("7");
    }

    @Test
    public void test8() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("8");

        await().explicitlyFor(2000L);

        assertThat(el("input").value()).isEqualTo("8");
    }
}
