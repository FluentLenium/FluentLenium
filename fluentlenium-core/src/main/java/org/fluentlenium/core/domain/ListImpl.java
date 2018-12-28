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

    public Iterator<T> iterator() {
        return getList().iterator();
    }

}
