package ua.com.alevel.impl;

import ua.com.alevel.MathSet;
import ua.com.alevel.service.ShowLibraryService;

public class ShowLibraryServiceImpl<number extends Number & Comparable<number>> implements ShowLibraryService<number> {
    private MathSet<number> mathSet = new MathSet<>();

    @Override
    public void add(number n) {
        mathSet.add(n);
    }

    @SafeVarargs
    @Override
    public final void add(number... n) {
        mathSet.add(n);
    }

    @Override
    public void join(MathSet<number> ms) {
        mathSet.join(ms);
    }

    @Override
    public number get(int index) {
        return mathSet.get(index);
    }

    @Override
    public void sortDesc() {
        mathSet.sortDesc();
    }

    @Override
    public void sortAsc() {
        mathSet.sortAsc();
    }

    @Override
    public number getMax() {
        return mathSet.getMax();
    }

    @Override
    public number getMin() {
        return mathSet.getMin();
    }

    @Override
    public number getAverage() {
        return mathSet.getAverage();
    }

    @Override
    public number getMedian() {
        return mathSet.getMedian();
    }

    @Override
    public MathSet<number> getMathSet() {
        return mathSet;
    }

    @Override
    public int getSize() {
        return mathSet.getSize();
    }
}
