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

    @Override
    public void clear() {
        getList().clear();
    }

    @Override
    public boolean isEmpty() {
        return getList().isEmpty();
    }

    @Override
    public T set(int index, T element) {
        return getList().set(index, element);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return getList().containsAll(collection);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return getList().subList(fromIndex, toIndex);
    }

    @Override
    public boolean add(T element) {
        return getList().add(element);
    }

    @Override
    public boolean remove(Object obj) {
        return getList().remove(obj);
    }

    @Override
    public int size() {
        return getList().size();
    }

    @Override
    public ListIterator<T> listIterator() {
        return getList().listIterator();
    }

    @Override
    public boolean contains(Object obj) {
        return getList().contains(obj);
    }

    @Override
    public Object[] toArray() {
        return getList().toArray();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return getList().retainAll(collection);
    }

    @Override
    public int lastIndexOf(Object obj) {
        return getList().lastIndexOf(obj);
    }

    @Override
    public <T> T[] toArray(T[] elements) {
        return getList().toArray(elements);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return getList().removeAll(collection);
    }

    @Override
    public T remove(int index) {
        return getList().remove(index);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return getList().addAll(collection);
    }

    @Override
    public int indexOf(Object obj) {
        return getList().indexOf(obj);
    }

    @Override
    public void add(int index, T element) {
        getList().add(index, element);
    }

    @Override
    public T get(int index) {
        return getList().get(index);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return getList().listIterator(index);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        return getList().addAll(index, collection);
    }

    @Override
    public Iterator<T> iterator() {
        return getList().iterator();
    }

}
