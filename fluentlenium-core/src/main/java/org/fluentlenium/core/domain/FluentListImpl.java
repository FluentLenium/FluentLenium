package org.fluentlenium.core.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.fluentlenium.core.filter.Filter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Map the list to a FluentList in order to offers some events like click(), submit(), value() ...
 */
public class FluentListImpl<E extends FluentWebElement> extends ArrayList<E> implements FluentList<E> {

    public FluentListImpl() {
        super();
    }

    public FluentListImpl(Collection<E> listFiltered) {
        super(listFiltered);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E first() {
        if (this.size() == 0) {
            throw new NoSuchElementException("Element not found");
        }
        return this.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList click() {
        if (this.size() == 0) {
            throw new NoSuchElementException("No Element found");
        }

        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.click();
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList text(String... with) {
        boolean atMostOne = false;
        if (with.length > 0) {
            int id = 0;
            String value;

            for (E fluentWebElement : this) {
                if (fluentWebElement.isDisplayed()) {
                    if (with.length > id) {
                        value = with[id++];
                    } else {
                        value = with[with.length - 1];
                    }
                    if (fluentWebElement.isEnabled()) {
                        atMostOne = true;
                        fluentWebElement.text(value);
                    }
                }
            }
            if (!atMostOne) {
                throw new NoSuchElementException("No element is displayed or enabled. Can't set a new value.");
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList<E> clearAll() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.clear();
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.clear();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList<E> submit() {
        for (E fluentWebElement : this) {
            if (fluentWebElement.isEnabled()) {
                fluentWebElement.submit();
            }
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getValues() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getValue();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getIds() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getId();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAttributes(final String attribute) {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getAttribute(attribute);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getNames() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getName();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTextContents() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getTextContent();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getTexts() {
        return Lists.transform(this, new Function<E, String>() {
            public String apply(E webElement) {
                return webElement.getText();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        if (this.size() > 0) {
            return this.get(0).getValue();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        if (this.size() > 0) {
            return this.get(0).getId();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAttribute(final String attribute) {
        if (this.size() > 0) {
            return this.get(0).getAttribute(attribute);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        if (this.size() > 0) {
            return this.get(0).getName();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        if (this.size() > 0) {
            return this.get(0).getText();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList<E> find(String name, Filter... filters) {
        List<E> finds = new ArrayList<E>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(name, filters));
        }
        return new FluentListImpl<E>(finds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList<E> find(By locator, Filter... filters) {
        List<E> finds = new ArrayList<E>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(locator, filters));
        }
        return new FluentListImpl<E>(finds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FluentList<E> find(Filter... filters) {
        List<E> finds = new ArrayList<E>();
        for (FluentWebElement e : this) {
            finds.addAll((Collection<E>) e.find(filters));
        }
        return new FluentListImpl<E>(finds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E find(String name, Integer number, Filter... filters) {
        FluentList<E> fluentList = find(name, filters);
        if (number >= fluentList.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + number + ". Number of elements available: " + fluentList.size()
                            + ". Selector: " + name + ".");
        }
        return fluentList.get(number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E find(By locator, Integer number, Filter... filters) {
        FluentList<E> fluentList = find(locator, filters);
        if (number >= fluentList.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + number + ". Number of elements available: " + fluentList
                            .size());
        }
        return fluentList.get(number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E find(Integer number, Filter... filters) {
        FluentList<E> fluentList = find(filters);
        if (number >= fluentList.size()) {
            throw new NoSuchElementException(
                    "No such element with position: " + number + ". Number of elements available: " + fluentList.size()
                            + ".");
        }
        return fluentList.get(number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findFirst(By locator, Filter... filters) {
        return find(locator, 0, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findFirst(String name, Filter... filters) {
        return find(name, 0, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findFirst(Filter... filters) {
        return find(0, filters);
    }

    @Override
    public <T extends FluentWebElement> FluentList<T> as(Class<T> componentClass) {
        List<T> elements = new ArrayList<>();

        for (E e : this) {
            elements.add(e.as(componentClass));
        }

        return new FluentListImpl<>(elements);
    }
}

