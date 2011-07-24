package fr.java.freelance.fluentlenium.core;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;
import fr.java.freelance.fluentlenium.domain.FluentList;
import fr.java.freelance.fluentlenium.domain.FluentWebElement;
import fr.java.freelance.fluentlenium.filter.Filter;
import fr.java.freelance.fluentlenium.filter.FilterPredicate;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rigabertm
 * Date: 7/24/11
 * Time: 11:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Search {
    private Search() {
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public static FluentList search(String name, SearchContext searchContext, final Filter... filters) {
        StringBuilder sb = new StringBuilder(name);
        List<Filter> postFilterSelector = new ArrayList<Filter>();
        if (filters != null) {
            for (Filter selector : filters) {
                if (selector.isPreFilter() && !"text".equalsIgnoreCase(selector.getAttribut())) {
                    sb.append(selector.toString());
                } else {
                    postFilterSelector.add(selector);
                }
            }
        }
        List<FluentWebElement> preFiltered = select(sb.toString(), searchContext);
        Collection<FluentWebElement> postFiltered = preFiltered;
        for (Filter selector : postFilterSelector) {
            postFiltered = Collections2.filter(postFiltered, new FilterPredicate(selector));
        }

        return new FluentList(postFiltered);
    }

    private static List<FluentWebElement> select(String cssSelector, SearchContext searchContext) {
        return Lists.transform(searchContext.findElements(By.cssSelector(cssSelector)), new Function<WebElement, FluentWebElement>() {
            public FluentWebElement apply(@Nullable WebElement webElement) {
                return new FluentWebElement((webElement));
            }
        });
    }

}
