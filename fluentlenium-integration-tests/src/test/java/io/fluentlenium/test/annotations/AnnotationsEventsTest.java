package io.fluentlenium.test.annotations;

import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.events.NavigateAllListener;
import io.fluentlenium.core.events.annotations.AfterClickOn;
import io.fluentlenium.core.events.annotations.AfterFindBy;
import io.fluentlenium.core.events.annotations.AfterGetText;
import io.fluentlenium.core.events.annotations.AfterNavigate;
import io.fluentlenium.core.events.annotations.AfterNavigateRefresh;
import io.fluentlenium.core.events.annotations.BeforeClickOn;
import io.fluentlenium.core.events.annotations.BeforeFindBy;
import io.fluentlenium.core.events.annotations.BeforeGetText;
import io.fluentlenium.core.events.annotations.BeforeNavigate;
import io.fluentlenium.core.events.annotations.BeforeNavigateRefresh;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationsEventsTest extends IntegrationFluentTest {

    private final List<WebElement> beforeClick = new ArrayList<>();
    private final List<WebElement> afterClick = new ArrayList<>();

    private int beforeFindBy;
    private int afterFindBy;

    private int beforeGetText;
    private int afterGetText;

    private int beforeNavigate;
    private int afterNavigate;

    private int beforeNavigateRefresh;
    private int afterNavigateRefresh;

    private static final class Component {
        private final WebElement element;

        private Component(WebElement element) {
            this.element = element;
        }

        public void click() {
            element.click();
        }

        @BeforeClickOn
        public void beforeClickOn() {
            //Do nothing.
        }

    }

    @Test
    void clickOn() {
        goTo(DEFAULT_URL);

        $("button").click();

        assertThat(beforeClick).hasSize(1);
        assertThat(afterClick).hasSize(1);
    }

    @BeforeClickOn
    private void beforeClickOn(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        assertThat(element.tagName()).isEqualTo("button");
        assertThat(afterClick).doesNotContain(element.getElement());
        beforeClick.add(element.getElement());
    }

    @AfterClickOn
    public void afterClickOn(FluentWebElement element) { // NOPMD UnusedPrivateMethod
        assertThat(element.tagName()).isEqualTo("button");

        ArrayList<WebElement> webElements = new ArrayList<>(afterClick);
        webElements.add(element.getElement());

        assertThat(beforeClick).containsExactlyElementsOf(webElements);
        afterClick.add(element.getElement());
    }

    @Test
    void findBy() {
        goTo(DEFAULT_URL);

        el("button").now();

        assertThat(beforeFindBy).isEqualTo(1);
        assertThat(afterFindBy).isEqualTo(1);
    }

    @BeforeFindBy
    public void beforeFindBy(FluentWebElement element) {
        assertThat(element).isNull();
        beforeFindBy++;
    }

    @AfterFindBy
    public void afterFindBy(FluentWebElement element) {
        assertThat(element).isNull();
        afterFindBy++;
    }

    @Test
    void getText() {
        goTo(DEFAULT_URL);

        assertThat(beforeGetText).isEqualTo(0);
        assertThat(afterGetText).isEqualTo(0);

        el("#linkToPage2").text();

        assertThat(beforeGetText).isEqualTo(1);
        assertThat(afterGetText).isEqualTo(1);
    }

    @BeforeGetText
    public void beforeGetText(FluentWebElement element) {
        assertThat(element).isNotNull();
        beforeGetText++;
    }

    @AfterGetText
    public void afterGetText(FluentWebElement element) {
        assertThat(element).isNotNull();
        afterGetText++;
    }

    @Test
    void navigate() {
        goTo(DEFAULT_URL);

        assertThat(beforeNavigate).isEqualTo(1);
        assertThat(afterNavigate).isEqualTo(1);

        getDriver().navigate().refresh();

        assertThat(beforeNavigate).isEqualTo(2);
        assertThat(afterNavigate).isEqualTo(2);
    }

    @BeforeNavigate
    public void beforeNavigate(String url, NavigateAllListener.Direction direction) {
        beforeNavigate++;

        if (beforeNavigate == 1) {
            assertThat(url).isEqualTo(DEFAULT_URL);
            assertThat(direction).isNull();
        }

        if (beforeNavigate == 2) {
            assertThat(url).isNull();
            assertThat(direction).isEqualTo(NavigateAllListener.Direction.REFRESH);
        }

    }

    @AfterNavigate
    public void afterNavigate(String url, NavigateAllListener.Direction direction) {
        afterNavigate++;

        if (afterNavigate == 1) {
            assertThat(url).isEqualTo(DEFAULT_URL);
            assertThat(direction).isNull();
        }

        if (afterNavigate == 2) {
            assertThat(url).isNull();
            assertThat(direction).isEqualTo(NavigateAllListener.Direction.REFRESH);
        }
    }

    @Test
    void refresh() {
        goTo(DEFAULT_URL);

        assertThat(beforeNavigateRefresh).isEqualTo(0);
        assertThat(afterNavigateRefresh).isEqualTo(0);

        getDriver().navigate().refresh();

        assertThat(beforeNavigateRefresh).isEqualTo(1);
        assertThat(afterNavigateRefresh).isEqualTo(1);
    }

    @BeforeNavigateRefresh
    public void beforeNavigateRefresh() {
        beforeNavigateRefresh++;
    }

    @AfterNavigateRefresh
    public void afterNavigateRefresh() {
        afterNavigateRefresh++;
    }

}
