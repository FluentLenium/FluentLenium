package org.fluentlenium.core.components;

import org.fluentlenium.core.domain.WrapsElements;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A list of component that lazy initialize from it's related list of elements.
 *
 * @param <T> type of component.
 */
public class LazyComponentList<T> implements List<T>, WrapsElements, LazyComponents<T> {
    private final ComponentInstantiator instantiator;
    private final Class<T> componentClass;

    private final List<WebElement> elements;

    private final List<LazyComponentsListener<T>> lazyComponentsListeners = new ArrayList<>();

    private final AtomicReference<java.lang.Object> list = new AtomicReference<>();

    /**
     * Creates a new lazy component list.
     *
     * @param instantiator   component instantiator
     * @param componentClass component class
     * @param elements       underlying element list
     */
    public LazyComponentList(ComponentInstantiator instantiator, Class<T> componentClass, List<WebElement> elements) {
        this.componentClass = componentClass;
        this.instantiator = instantiator;
        this.elements = elements;
    }

    public List<T> getList() {
        Object value = this.list.get();
        if (value == null) {
            synchronized (this.list) {
                value = this.list.get();
                if (value == null) {
                    final List<T> actualValue = transformList();
                    value = actualValue == null ? this.list : actualValue;
                    this.list.set(value);
                }
            }
        }
        return (List<T>) (value == this.list ? null : value);
    }

    /**
     * Transform the actual list into components.
     *
     * @return transformed list
     */
    protected List<T> transformList() {
        List<T> components = new ArrayList<>();
        Map<WebElement, T> componentMap = new LinkedHashMap<>();
        for (WebElement element : elements) {
            T component = instantiator.newComponent(componentClass, element);
            components.add(component);
            componentMap.put(element, component);
        }
        fireLazyComponentsInitialized(componentMap);
        return components;
    }

    /**
     * First lazy components initialized event.
     *
     * @param componentMap components
     */
    protected void fireLazyComponentsInitialized(Map<WebElement, T> componentMap) {
        for (LazyComponentsListener<T> listener : lazyComponentsListeners) {
            listener.lazyComponentsInitialized(componentMap);
        }
    }

    @Override
    public boolean addLazyComponentsListener(LazyComponentsListener<T> listener) {
        return lazyComponentsListeners.add(listener);
    }

    @Override
    public boolean removeLazyComponentsListener(LazyComponentsListener<T> listener) {
        return lazyComponentsListeners.remove(listener);
    }

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    public boolean isLazyInitialized() {
        return list == null;
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return elements;
    }

    @Override
    public String toString() {
        return isLazyInitialized() ? getList().toString() : elements.toString();
    }

    public void clear() {
        getList().clear();
    }

    public void forEach(Consumer<? super T> action) {
        getList().forEach(action);
    }

    public <T> T[] toArray(IntFunction<T[]> generator) {
        return getList().toArray(generator);
    }

    public boolean isEmpty() {
        return getList().isEmpty();
    }

    public boolean removeIf(Predicate<? super T> filter) {
        return getList().removeIf(filter);
    }

    public Spliterator<T> spliterator() {
        return getList().spliterator();
    }

    public T set(int index, T element) {
        return getList().set(index, element);
    }

    public boolean containsAll(Collection<?> c) {
        return getList().containsAll(c);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return getList().subList(fromIndex, toIndex);
    }

    public boolean add(T e) {
        return getList().add(e);
    }

    public boolean remove(Object o) {
        return getList().remove(o);
    }

    public int size() {
        return getList().size();
    }

    public ListIterator<T> listIterator() {
        return getList().listIterator();
    }

    public boolean contains(Object o) {
        return getList().contains(o);
    }

    public Object[] toArray() {
        return getList().toArray();
    }

    public void sort(Comparator<? super T> c) {
        getList().sort(c);
    }

    public boolean retainAll(Collection<?> c) {
        return getList().retainAll(c);
    }

    public int lastIndexOf(Object o) {
        return getList().lastIndexOf(o);
    }

    public <T> T[] toArray(T[] a) {
        return getList().toArray(a);
    }

    public boolean removeAll(Collection<?> c) {
        return getList().removeAll(c);
    }

    public T remove(int index) {
        return getList().remove(index);
    }

    public Stream<T> parallelStream() {
        return getList().parallelStream();
    }

    public boolean addAll(Collection<? extends T> c) {
        return getList().addAll(c);
    }

    public int indexOf(Object o) {
        return getList().indexOf(o);
    }

    public void add(int index, T element) {
        getList().add(index, element);
    }

    public T get(int index) {
        return getList().get(index);
    }

    public ListIterator<T> listIterator(int index) {
        return getList().listIterator(index);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return getList().addAll(index, c);
    }

    public Stream<T> stream() {
        return getList().stream();
    }

    public Iterator<T> iterator() {
        return getList().iterator();
    }

    public void replaceAll(UnaryOperator<T> operator) {
        getList().replaceAll(operator);
    }
}
