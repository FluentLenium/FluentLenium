package org.fluentlenium.it;

import org.junit5.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test1 extends FluentIntegTest {
    @Test
    public void test() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("1");

        await().explicitlyFor(5000L);

        assertThat(el("input").value()).isEqualTo("1");
    }

    @Test
    public void test2() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("2");

        await().explicitlyFor(4000L);

        assertThat(el("input").value()).isEqualTo("2");
    }

    @Test
    public void test3() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("3");

        await().explicitlyFor(3000L);

        assertThat(el("input").value()).isEqualTo("3");
    }

    @Test
    public void test4() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("4");

        await().explicitlyFor(2000L);

        assertThat(el("input").value()).isEqualTo("4");
    }
}
