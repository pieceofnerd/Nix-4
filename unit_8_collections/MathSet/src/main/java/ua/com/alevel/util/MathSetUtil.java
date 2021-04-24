package ua.com.alevel.util;

import ua.com.alevel.config.MathSetConfig;

public class MathSetUtil {
    public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        int newLength = Math.max(minGrowth, prefGrowth) + oldLength;
        if (newLength - MathSetConfig.MAX_ARRAY_LENGTH <= 0) {
            return newLength;
        }
        return hugeLength(oldLength, minGrowth);
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError("Required array length too large");
        }
        if (minLength <= MathSetConfig.MAX_ARRAY_LENGTH) {
            return MathSetConfig.MAX_ARRAY_LENGTH;
        }
        return Integer.MAX_VALUE;
    }

    public static Number[] copyOf(Number[] fromArray, int capacity) {
        Number[] toArray = new Number[capacity];
        for (int i = 0; i < fromArray.length; i++) {
            toArray[i] = fromArray[i];
        }
        return toArray;
    }

    private static <number extends Number & Comparable<number>> int partition(Number[] array, int begin, int end) {
        int pivot = end - 1;
        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (((number) array[i]).compareTo((number) array[pivot]) < 0) {
                number temp = (number) array[counter];
                array[counter] = array[i];
                array[i] = (Number) temp;
                counter++;
            }
        }
        number temp = (number) array[pivot];
        array[pivot] = array[counter];
        array[counter] = (Number) temp;
        return counter;
    }

    private static void quickSort(Number[] array, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(array, begin, end);
        quickSort(array, begin, pivot - 1);
        quickSort(array, pivot + 1, end);
    }

    public static<number extends Number & Comparable<number>>  Number[] sort(Number[] array, int size, int firstIndex, int secondIndex, boolean isDesk) {
        quickSort(array, firstIndex, secondIndex);

        boolean isSorted = false;
        Number buf;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length-1; i++) {
                if(((number) array[i]).compareTo((number) array[i+1]) > 0){
                    isSorted = false;

                    buf = array[i];
                    array[i] = array[i+1];
                    array[i+1] = buf;
                }
            }
        }

        if (isDesk) {
            Number[] tempArray = new Number[size];
            for (int i = firstIndex; i < secondIndex; i++) {
                tempArray[secondIndex - 1 - i] = array[i];
            }
            int counter = 0;
            for (int i = 0; i < array.length; i++) {
                if (i >= firstIndex && i <= secondIndex + 1) {
                    array[i] = tempArray[counter];
                    counter++;
                }
            }
        }
        return array;
    }

    public static <number extends Number & Comparable<number>> number getMax(Number[] array) {
        number max = (number) array[0];
        for (Number number : array) {
            if (max.compareTo((number) number) < 0) {
                max = (number) number;
            }
        }
        return max;
    }

    public static <number extends Number & Comparable<number>> number getMin(Number[] array) {
        number min = (number) array[0];
        for (int i = 0; i < array.length; i++) {
            if (min.compareTo((number) array[i]) > 0) {
                min = (number) array[i];
            }
        }
        return min;
    }

    public static Number[] copySubArray(Number[] fromArray, int fromIndex, int toIndex) {
        Number[] toArray = new Number[toIndex - fromIndex + 1];
        int counter = 0;
        for (int i = fromIndex; i < toIndex; i++) {
            toArray[counter] = fromArray[i];
            counter++;
        }
        return toArray;
    }

    public static <number extends Number & Comparable<? extends number>> Number getAverage(Number[] array) {
        double average = 0;
        for (Number number : array) {
            average += Double.parseDouble(number.toString());
        }
        average = average / array.length;
        return average;

    }
}
