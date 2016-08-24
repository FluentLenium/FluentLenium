package org.fluentlenium.it;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test1 extends FluentIntegTest {
    @Test
    public void test() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        findFirst("input").fill().with("1");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
        }

        assertThat(findFirst("input").getValue()).isEqualTo("1");
    }

    @Test
    public void test2() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        findFirst("input").fill().with("2");

        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
        }

        assertThat(findFirst("input").getValue()).isEqualTo("2");
    }

    @Test
    public void test3() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        findFirst("input").fill().with("3");

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
        }

        assertThat(findFirst("input").getValue()).isEqualTo("3");
    }

    @Test
    public void test4() {
        goTo(UrlUtil.getAbsoluteUrlFromFile("inputs.html"));
        findFirst("input").fill().with("4");

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
        }

        assertThat(findFirst("input").getValue()).isEqualTo("4");
    }
}
