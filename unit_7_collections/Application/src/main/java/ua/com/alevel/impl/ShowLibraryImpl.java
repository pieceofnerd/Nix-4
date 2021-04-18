package ua.com.alevel.impl;

import lombok.Getter;
import ua.com.alevel.service.ShowLibraryService;

import java.util.Collection;
import java.util.List;

public class ShowLibraryImpl<Type extends Comparable<? super Type>> implements ShowLibraryService<Type> {
    @Getter
    private OrderList<Type> orderList = new OrderList<>();

    @Override
    public void add(Type obj) {
        orderList.add(obj);
    }

    @Override
    public int size() {
        return orderList.size();
    }

    @Override
    public boolean addAll(Collection<? extends Type> col) {
        return orderList.addAll(col);
    }

    @Override
    public Type get(int index) {
        return orderList.get(index);
    }

    @Override
    public int indexOf(Object obj) {
        return orderList.indexOf(obj);
    }

    @Override
    public int lastIndexOf(Object obj) {
        return orderList.lastIndexOf(obj);
    }

    @Override
    public boolean remove(Type type) {
        return orderList.remove(type);
    }

    @Override
    public Type set(int index, Type obj) {
        return orderList.set(index, obj);
    }

    @Override
    public List<Type> subList(int start, int end) {
        return orderList.subList(start, end);
    }

    @Override
    public void clean() {
        orderList.clear();
    }
}
