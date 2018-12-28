package org.fluentlenium.core.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class ListImpl<T> implements List<T> {

    public ListImpl() {
        super();
    }

    public abstract List<T> getList();

    public void clear() {
        getList().clear();
    }

    public boolean isEmpty() {
        return getList().isEmpty();
    }

    public T set(int index, T element) {
        return getList().set(index, element);
    }

    public boolean containsAll(Collection<?> collection) {
        return getList().containsAll(collection);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return getList().subList(fromIndex, toIndex);
    }

    public boolean add(T element) {
        return getList().add(element);
    }

    public boolean remove(Object obj) {
        return getList().remove(obj);
    }

    public int size() {
        return getList().size();
    }

    public ListIterator<T> listIterator() {
        return getList().listIterator();
    }

    public boolean contains(Object obj) {
        return getList().contains(obj);
    }

    public Object[] toArray() {
        return getList().toArray();
    }

    public boolean retainAll(Collection<?> collection) {
        return getList().retainAll(collection);
    }

    public int lastIndexOf(Object obj) {
        return getList().lastIndexOf(obj);
    }

    public <T> T[] toArray(T[] elements) {
        return getList().toArray(elements);
    }

    public boolean removeAll(Collection<?> collection) {
        return getList().removeAll(collection);
    }

    public T remove(int index) {
        return getList().remove(index);
    }

    public boolean addAll(Collection<? extends T> collection) {
        return getList().addAll(collection);
    }

    public int indexOf(Object obj) {
        return getList().indexOf(obj);
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

    public boolean addAll(int index, Collection<? extends T> collection) {
        return getList().addAll(index, collection);
    }

    public Iterator<T> iterator() {
        return getList().iterator();
    }

}
