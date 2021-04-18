package ua.com.alevel.service;

import java.util.Collection;
import java.util.List;

public interface ShowLibraryService<Type extends Comparable<? super Type>> {
    void add(Type obj);

    int size();

    boolean addAll(Collection<? extends Type> col);

    Type get(int index);

    int indexOf(Object obj);

    int lastIndexOf(Object obj);

    boolean remove(Type type);

    Type set(int index, Type obj);

    List<Type> subList(int start, int end);

    void clean();

}
