package com.mine.test;

/**
 * @author 杜晓鹏
 * @create 2019-01-21 11:44
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {0, 3, 5, 9, 10, 56};
        int key = 9;
        Integer result1 = binarySearch(array, 0, array.length - 1, key);
        Integer result2 = whileSearch(array, key);
        System.out.println(result1);
        System.out.println(result2);
    }

    public static Integer binarySearch(int[] array, int start, int end, int key) {
        int mid = (end + start) / 2;
        if (start > end || array[end] < key || array[start] > key) {
            return -1;
        }


        if (array[mid] > key)
            return binarySearch(array, start, mid - 1, key);
        else if (array[mid] < key)
            return binarySearch(array, mid + 1, end, key);
        else
            return mid;

    }

    public static Integer whileSearch(int[] array, int key) {
        int start = 0;
        int end = array.length - 1;
        int mid = 0;

        if (start > end || array[end] < key || array[start] > key) {
            return -1;
        }
        while (start <= end) {
            mid = (end + start) / 2;
            if (array[mid] > key)
                end = mid - 1;
            else if (array[mid] < key)
                start = mid + 1;
            else
                return mid;
        }
        return -1;
    }
}
