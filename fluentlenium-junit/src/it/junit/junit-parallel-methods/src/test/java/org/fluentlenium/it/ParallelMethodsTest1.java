package org.fluentlenium.it;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelMethodsTest1 extends FluentIntegTest {
    @Test
    public void test() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("1");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
        }

        assertThat(el("input").value()).isEqualTo("1");
    }

    @Test
    public void test2() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("2");

        try {
            Thread.sleep(400L);
        } catch (InterruptedException e) {
        }

        assertThat(el("input").value()).isEqualTo("2");
    }

    @Test
    public void test3() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("3");

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
        }

        assertThat(el("input").value()).isEqualTo("3");
    }

    @Test
    public void test4() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        el("input").fill().with("4");

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
        }

        assertThat(el("input").value()).isEqualTo("4");
    }
}
