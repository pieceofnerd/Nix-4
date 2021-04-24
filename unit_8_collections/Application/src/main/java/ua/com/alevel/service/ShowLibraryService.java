package ua.com.alevel.service;

import ua.com.alevel.MathSet;

public interface ShowLibraryService<number extends Number & Comparable<number>> {
    void add(number n);

    void add(number... n);

    void join(MathSet<number> ms);

    number get(int index);

    void sortDesc();

    void sortAsc();

    number getMax();

    number getMin();

    number getAverage();

    number getMedian();

    MathSet<number> getMathSet();

    int getSize();

}
