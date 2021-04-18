package ua.com.alevel.util;

import ua.com.alevel.config.OrderListConfigData;

public class ListUtil {

    public static boolean subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            return false;
        if (toIndex > size)
            return false;
        return fromIndex <= toIndex;
    }

    public static <Type> Type[] copySubArray(Type[] fromArray, int fromIndex, int toIndex) {
        Type[] toArray = (Type[]) new Object[toIndex - fromIndex + 1];
        int counter = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            toArray[counter] = fromArray[i];
            counter++;
        }
        return toArray;
    }

    public static <Type> Type[] copyOf(Type[] fromArray, int capacity) {
        Type[] toArray = (Type[]) new Object[capacity];
        for (int i = 0; i < fromArray.length; i++) {
            toArray[i] = fromArray[i];
        }
        return toArray;
    }

    public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        int newLength = Math.max(minGrowth, prefGrowth) + oldLength;
        if (newLength - OrderListConfigData.MAX_ARRAY_LENGTH <= 0) {
            return newLength;
        }
        return hugeLength(oldLength, minGrowth);
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError("Required array length too large");
        }
        if (minLength <= OrderListConfigData.MAX_ARRAY_LENGTH) {
            return OrderListConfigData.MAX_ARRAY_LENGTH;
        }
        return Integer.MAX_VALUE;
    }

    public static void sort(Object[] array, int size) {
        int begin = 0;
        quickSort(array, begin, size);
    }

    public static Object[] concatArray(Object[] firstArray, Object[] secondArray) {
        int size = firstArray.length + secondArray.length;
        Object[] generalArray = new Object[size];
        int counter = -1;
        for (Object o : firstArray) {
            if (o == null) {
                break;
            }
            counter++;
            generalArray[counter] = o;

        }
        for (Object o : secondArray) {
            counter++;
            generalArray[counter] = o;
        }
        return generalArray;
    }

    private static <T extends Comparable<? super T>> int partition(Object[] array, int begin, int end) {
        int pivot = end - 1;
        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (((T) array[i]).compareTo((T) array[pivot]) < 0) {
                T temp = (T) array[counter];
                array[counter] = array[i];
                array[i] = temp;
                counter++;
            }
        }
        T temp = (T) array[pivot];
        array[pivot] = array[counter];
        array[counter] = temp;
        return counter;
    }

    private static void quickSort(Object[] array, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot - 1);
        quickSort(array, pivot + 1, end);
    }


}
