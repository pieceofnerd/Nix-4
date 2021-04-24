package ua.com.alevel;


import ua.com.alevel.config.MathSetConfig;
import ua.com.alevel.util.*;

public class MathSet<number extends Number & Comparable<number>> {

    private Number[] array;
    private int size;

    public MathSet() {
        array = MathSetConfig.DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
        size = 0;
    }

    public MathSet(int capacity) {
        if (capacity > 0) {
            this.array = new Number[capacity];
        } else if (capacity == 0) {
            this.array = MathSetConfig.DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    capacity);
        }
        size = 0;
    }

    public MathSet(Number[] numbers) {
        this.array = uniqueElementsInArray(numbers);
        this.size = array.length;
    }

    public MathSet(Number[]... numbers) {
        int size = 0;
        for (Number[] number : numbers) {
            size += number.length;
        }
        Number[] array = new Number[size];
        int counter = 0;
        for (Number[] number : numbers) {
            for (Number value : number) {
                array[counter] = value;
                counter++;
            }
        }
        this.array = uniqueElementsInArray(array);
        this.size = this.array.length;
    }

    MathSet(MathSet numbers) {
        this.array = numbers.array;
        this.size = numbers.size;
    }

    public void add(number n) {
        add(n, array, size);
    }

    @SafeVarargs
    public final void add(number... n) {
        for (number number : n) {
            add(number, array, size);
        }
    }

    public void join(MathSet ms) {
        for (int i = 0; i < ms.array.length; i++) {
            add((number) ms.array[i]);
        }
    }

    public void join(MathSet... ms) {
        for (MathSet m : ms) {
            for (int j = 0; j < m.array.length; j++) {
                add((number) m.array[j]);
            }
        }
    }

    public int getSize() {
        return array.length;
    }

    public void sortDesc() {

        this.array = MathSetUtil.sort(array, size, 0, array.length, true);
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        this.array = MathSetUtil.sort(array, size, firstIndex, lastIndex, true);
    }

    void sortDesc(int value) {
        this.array = MathSetUtil.sort(array, size, 0, value, true);
    }

    public void sortAsc() {
        this.array = MathSetUtil.sort(array, size, 0, array.length, false);
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        this.array = MathSetUtil.sort(array, size, firstIndex, lastIndex, false);
    }

    public void sortAsc(int value) {
        this.array = MathSetUtil.sort(array, size, 0, value, true);
    }

    public number get(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("index out of bound");
        }
        return (number) this.array[index];
    }

    public number getMax() {
        return MathSetUtil.getMax(this.array);
    }

    public number getMin() {
        return MathSetUtil.getMin(this.array);
    }

    public number getAverage() {
        return (number) MathSetUtil.getAverage(this.array);
    }

    public number getMedian() {
        Number[] sortArray = MathSetUtil.sort(array, size, 0, array.length, false);
        if (sortArray.length % 2 == 0) {
            Number[] averageValuesMedian = new Number[2];
            averageValuesMedian[0] = this.array[sortArray.length / 2];
            averageValuesMedian[1] = this.array[sortArray.length / 2 - 1];
            return (number) MathSetUtil.getAverage(averageValuesMedian);
        }
        return (number) this.array[sortArray.length / 2];
    }

    public MathSet squash(int firstIndex, int lastIndex) {
        Number[] squashArrayFirstPart = MathSetUtil.copySubArray(array, 0, firstIndex);
        Number[] squashArraySecondPart = MathSetUtil.copySubArray(array, lastIndex + 1, array.length);
        return new MathSet(squashArrayFirstPart, squashArraySecondPart);
    }

    public Number[] toArray() {
        return array;
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        return MathSetUtil.copySubArray(array, firstIndex, lastIndex);
    }

    public void clear() {
        final Number[] es = array;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    public void clear(Number[] numbers) {
        final Number[] es = MathSetUtil.copyOf(array, array.length);
        int s = array.length;
        for (int to = s, i = s = 0; i < to; i++)
            for (Number number : numbers) {
                if (array[i].equals(number)) {
                    es[i] = null;
                    break;
                }
            }
        array = es;
    }

    private void add(number element, Number[] array, int size) {
        if (size >= array.length)
            array = grow();
        array[size] = element;
        this.array = uniqueElementsInArray(array);
        this.size = size + 1;
    }

    private Number[] grow() {
        return grow(size + 1);
    }

    private Number[] grow(int minCapacity) {
        int oldCapacity = array.length;
        if (oldCapacity > 0 || array != MathSetConfig.DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            int newCapacity = MathSetUtil.newLength(oldCapacity,
                    minCapacity - oldCapacity,
                    oldCapacity >> 1);


            array = MathSetUtil.copyOf(array, newCapacity);
            return array;
        } else {
            return array = new Number[Math.max(MathSetConfig.DEFAULT_CAPACITY, minCapacity)];
        }
    }

    private Number[] uniqueElementsInArray(Number[] array) {
        Number[] uniqueElements = new Number[array.length];
        int counter = 0;

        for (int i = 0; i < array.length; i++) {
            boolean isElementUnique = true;
            for (int j = 0; j < uniqueElements.length; j++) {
                if (array[i] == null || uniqueElements[j] == null) {
                    break;
                }
                if (i != j && array[i].equals(uniqueElements[j])) {
                    isElementUnique = false;
                    break;
                }
            }
            if (isElementUnique) {
                uniqueElements[counter] = array[i];
                counter++;
            }
        }

        counter = 0;
        Number[] uniqueElementsWithoutNull = new Number[countSize(uniqueElements)];
        for (Number uniqueElement : uniqueElements) {
            if (uniqueElement != null) {
                uniqueElementsWithoutNull[counter] = uniqueElement;
                counter++;
            }
        }

        return uniqueElementsWithoutNull;
    }

    private int countSize(Number[] array) {
        int sum = 0;
        for (Number number : array) {
            if (number != null) {
                sum++;
            }
        }
        return sum;
    }

}
