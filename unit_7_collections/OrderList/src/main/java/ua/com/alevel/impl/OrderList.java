package ua.com.alevel.impl;

import ua.com.alevel.config.OrderListConfigData;
import ua.com.alevel.util.ListUtil;

import java.util.*;


public class OrderList<Type extends Comparable<? super Type>> implements List<Type> {
    private int size;
    transient Object[] elementData;

    public OrderList() {
        this.elementData = OrderListConfigData.DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
        this.size = 0;
    }

    public OrderList(Object[] array) {
        this.elementData = array;
        this.size = array.length;
    }

    public OrderList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = OrderListConfigData.DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf((Type) o) >= 0;
    }

    @Override
    public Iterator<Type> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && elementData[currentIndex] != null;
            }

            @Override
            public Type next() {
                return (Type) elementData[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        int size = size();
        if (elementData.length >= size) {
            return elementData;
        } else {
            return ListUtil.copySubArray(elementData, 0, elementData.length - 1);
        }

    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return ListUtil.copySubArray(a, 0, a.length);
        } else {
            a[size] = null;
            return a;
        }
    }

    @Override
    public boolean add(Type t) {
        if (elementData.length == 0) {
            add(t, elementData, size);
            return true;
        }

        if (((Type) elementData[size - 1]).compareTo(t) < 0) {
            add(t, elementData, size);
            return true;
        } else {
            add(t, elementData, size);
            ListUtil.sort(elementData, size);
            return true;
        }

    }

    private void add(Type type, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = type;
        size = s + 1;
    }

    @Override
    public boolean remove(Object o) {

        for (int i = 0; i < size; i++) {
            if (((Type) elementData[i]).compareTo((Type) o) == 0) {
                if (i != 0 && i != size - 1) {
                    Object[] firstPart = ListUtil.copySubArray(elementData, 0, i);
                    Object[] secondPart = ListUtil.copySubArray(elementData, i + 1, size);
                    elementData = ListUtil.concatArray(firstPart, secondPart);
                    size--;
                    break;
                } else if (i == 0) {
                    elementData = ListUtil.copySubArray(elementData, i + 1, size);
                    size--;
                    break;
                } else if (i == (size - 1)) {
                    elementData = ListUtil.copySubArray(elementData, 0, i);
                    size--;
                    break;
                }

            }
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (Objects.isNull(c)) {
            return false;
        }
        Object[] array = c.toArray();
        int counter = array.length;
        for (Object elementDatum : elementData) {
            for (Object o : array) {
                if (Objects.isNull(elementDatum) || Objects.isNull(o)) {
                    continue;
                }
                if (((Type) elementDatum).compareTo((Type) o) == 0) {
                    counter--;
                }
            }
        }
        return counter == 0;
    }

    @Override
    public boolean addAll(Collection<? extends Type> c) {
        Object[] array = c.toArray();
        int arrayLength = array.length;
        if (arrayLength == 0)
            return false;
        Object[] elementData;
        final int s;
        if (arrayLength > (elementData = this.elementData).length - (s = size))
            elementData = grow(s + arrayLength);
        this.elementData = ListUtil.concatArray(elementData, array);
        this.size = s + arrayLength;
        ListUtil.sort(this.elementData, size);
        return true;
    }

    @Override
    @Deprecated
    public boolean addAll(int index, Collection<? extends Type> c) {
        if (index > 0 && index < size - 1) {

            Object[] array = c.toArray();
            int arrayLength = array.length;
            if (arrayLength == 0)
                return false;
            Object[] elementData;
            final int s;
            if (arrayLength > (elementData = this.elementData).length - (s = size))
                elementData = grow(s + arrayLength);

            int numMoved = s - index;
            if (numMoved > 0)
                System.arraycopy(elementData, index,
                        elementData, index + arrayLength,
                        numMoved);
            System.arraycopy(array, 0, elementData, index, arrayLength);
            size = s + arrayLength;
            ListUtil.sort(this.elementData, size);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (Objects.isNull(c)) {
            return false;
        }
        final Object[] array = elementData;
        int counter;
        for (counter = 0; ; counter++) {
            if (counter == size)
                return false;
            if (c.contains(array[counter]))
                break;
        }
        int w = counter++;
        try {
            for (Object e; counter < size; counter++)
                if (!c.contains(e = array[counter]))
                    array[w++] = e;
        } catch (Throwable ex) {

            System.arraycopy(array, counter, array, w, size - counter);
            w += size - counter;
            throw ex;
        } finally {
            shiftTailOverGap(array, w, size);
        }
        return true;
    }

    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, size - hi);
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        final Object[] es = elementData;
        int r;
        for (r = 0; ; r++) {
            if (r == size)
                return false;
            if (!c.contains(es[r]))
                break;
        }
        int w = r++;
        try {
            for (Object e; r < size; r++)
                if (c.contains(e = es[r]))
                    es[w++] = e;
        } catch (Throwable ex) {
            System.arraycopy(es, r, es, w, size - r);
            w += size - r;
            throw ex;
        } finally {
            shiftTailOverGap(es, w, size);
        }
        return true;
    }

    @Override
    public void clear() {
        final Object[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    @Override
    public Type get(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("index out of bound");
        }
        return (Type) this.elementData[index];
    }

    @Override
    public Type set(int index, Type element) {
        checkIndex(index);
        Type oldValue = (Type) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    @Deprecated
    public void add(int index, Type element) {
        add(element);
    }

    @Override
    public Type remove(int index) {
        checkIndex(index);
        Type oldValue = (Type) elementData[index];
        Object[] beforeRemove = ListUtil.copySubArray(elementData, 0, index);
        Object[] afterRemove = ListUtil.copySubArray(elementData, index + 1, size);
        elementData = ListUtil.concatArray(beforeRemove, afterRemove);
        size--;
        ListUtil.sort(elementData, size);
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<Type> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Type> listIterator(int index) {
        return new ListIterator<>() {
            int cursor = index;
            int lastRet = -1;

            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @Override
            public Type next() {
                int i = cursor;
                if (i >= size)
                    throw new NoSuchElementException();
                Object[] array = elementData;
                if (i >= array.length)
                    throw new ConcurrentModificationException();
                cursor = i + 1;
                return (Type) array[(lastRet = i)];
            }

            @Override
            public boolean hasPrevious() {
                return cursor != 0;
            }

            @Override
            public Type previous() {
                int i = cursor - 1;
                if (i < 0)
                    throw new NoSuchElementException();
                Object[] array = elementData;
                if (i >= array.length)
                    throw new ConcurrentModificationException();
                cursor = i;
                return (Type) array[(lastRet = i)];
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                return cursor - 1;
            }

            @Override
            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();

                try {
                    OrderList.this.remove(lastRet);
                    cursor = lastRet;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public void set(Type type) {
                if (lastRet < 0)
                    throw new IllegalStateException();

                try {
                    OrderList.this.set(lastRet, type);
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public void add(Type type) {
                try {
                    int i = cursor;
                    OrderList.this.add(i, type);
                    cursor = i + 1;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

        };
    }

    @Override
    public List<Type> subList(int fromIndex, int toIndex) {
        boolean isIndexesCorrect = ListUtil.subListRangeCheck(fromIndex, toIndex, size);
        if (!isIndexesCorrect) {
            return null;
        }
        Object[] array = ListUtil.copySubArray(elementData, fromIndex, toIndex);
        return new OrderList<>(array);
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 || elementData != OrderListConfigData.DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            int newCapacity = ListUtil.newLength(oldCapacity,
                    minCapacity - oldCapacity,
                    oldCapacity >> 1);

            return elementData = ListUtil.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(OrderListConfigData.DEFAULT_CAPACITY, minCapacity)];
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("index out of bound");
        }
    }

}

