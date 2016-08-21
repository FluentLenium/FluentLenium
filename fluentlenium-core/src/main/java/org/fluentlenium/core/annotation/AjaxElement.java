package org.fluentlenium.core.annotation;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AjaxElement {
    /**
     * How long to wait for the element to appear. Measured in seconds.
     *
     * @see org.openqa.selenium.support.pagefactory.AjaxElementLocator#AjaxElementLocator(SearchContext, int, AbstractAnnotations)
     *
     * @return
     */
    int timeOutInSeconds() default 10;

    /**
     * By default, we sleep for 250ms between polls. You may override this method in order to change
     * how it sleeps.
     *
     * {@link AjaxElementLocator#sleepFor()}
     *
     * @return
     */
    int pollingInterval() default 250;

    /**
     * By default, elements are considered "found" if they are in the DOM.
     *
     * Give an implementation of Predicate<WebElement> to change whether or not you consider the element loaded.
     *
     * @see AjaxElementLocator#isElementUsable(WebElement)
     *
     * @return
     */
    Class<? extends Predicate<WebElement>> usableCondition() default NoUsableCondition.class;

    class NoUsableCondition implements Predicate<WebElement> {
        @Override
        public boolean apply(WebElement input) {
            return true;
        }

        @Override
        public boolean equals(Object object) {
            return object != null && object.getClass() == getClass();
        }
    }
}
