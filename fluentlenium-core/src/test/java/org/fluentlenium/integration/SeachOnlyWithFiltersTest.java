package org.fluentlenium.integration;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;

public class SeachOnlyWithFiltersTest extends LocalFluentCase {

    @Test
    public void checkWithNameWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withName("name"));
        assertThat(list.getIds()).containsOnly("id");
    }
        
    @Test
    public void checkWithTextWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withText("Pharmacy"));
        assertThat(list.getIds()).containsOnly("location");
    }
    
    @Test
    public void checkWithTextStartsWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withText().startsWith("Pharmac"));
        assertThat(list.getIds()).containsOnly("location");
    }
    
    @Test
    public void checkIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(1, withClass("small"));
        assertThat(element.getId()).isEqualTo("id2");
    }
    
    @Test
    public void checkFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(withClass("small"));
        assertThat(element.getId()).isEqualTo("id");
    }
    
    @Test
    public void checkMultipleWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withClass("small"), withName("name"));
        assertThat(list.getIds()).containsOnly("id");
    }
    
    @Test
    public void check$Works() {
        goTo(DEFAULT_URL);
        FluentList list = $(withName("name"));
        assertThat(list.getIds()).containsOnly("id");
    }
    
    @Test
    public void check$AndIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = $(1, withClass("small"));
        assertThat(element.getId()).isEqualTo("id2");
    }

    @Test
    public void checkFillWorks() {
        goTo(DEFAULT_URL);
        fill(withId("name")).with("FillTest");
        assertThat($("#name").getValue()).isEqualTo("FillTest");
    }

    @Test
    public void checkFillSelectWorks() {
        goTo(DEFAULT_URL);        
        Select select = new Select(findFirst("#select").getElement());
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        fillSelect(withId("select")).withValue("value-3");
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");        
    }

    @Test
    public void checkClickWorks() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        click(withId("linkToPage2"));
        assertThat(title()).isEqualTo("Page 2");
    }

    @Test
    public void checkClearWorks() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValue()).isEqualTo("John");
        clear(withId("name"));
        assertThat($("#name").getValue()).isEqualTo("");
    }

    @Test
    public void checkTextWorks() {
        goTo(DEFAULT_URL);
        assertThat(text(withName("name"))).containsOnly("Small 1");
    }

    @Test
    public void checkValueWorks() {
        goTo(DEFAULT_URL);
        assertThat(value(withId("name"))).containsOnly("John");
    }

    @Test
    public void checkFindChildFindWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withClass("parent")).find(withClass("child"));
        assertThat(list.getTexts()).containsOnly("Alex");
    }
    
    @Test
    public void checkFindChildFindWithIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(withClass("parent")).find(0, withClass("child"));
        assertThat(element.getText()).isEqualTo("Alex");
    }
    
    @Test
    public void checkFindChildFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(withClass("parent")).findFirst(withClass("child"));
        assertThat(element.getText()).isEqualTo("Alex");
    }

    @Test
    public void checkFindFirstChildFindWorks() {
        goTo(DEFAULT_URL);
        FluentList list = findFirst(withClass("parent")).find(withClass("child"));
        assertThat(list.getTexts()).containsOnly("Alex");
    }
    
    @Test
    public void checkFindFirstChildFindWithIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(withClass("parent")).find(0, withClass("child"));
        assertThat(element.getText()).isEqualTo("Alex");
    }
    
    @Test
    public void checkFindFirstChildFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(withClass("parent")).findFirst(withClass("child"));
        assertThat(element.getText()).isEqualTo("Alex");
    }
               
    @Test
    public void checkWaitWorks() {
        goTo(DEFAULT_URL);
        await().atMost(10, TimeUnit.NANOSECONDS).until(withClass(".small")).isPresent(); 
    }
}
