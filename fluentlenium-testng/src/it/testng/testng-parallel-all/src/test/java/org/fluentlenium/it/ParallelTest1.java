package org.fluentlenium.it;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelTest1 extends FluentIntegTestNg {
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
            Thread.sleep(4000L);
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
