package com.tanke.strategy;

import com.tanke.strategy.myInterface.Comparator;

public class Sorter<T> {

    public void sort(T[] arr, Comparator<T> comparator) {
        for(int i=0; i<arr.length - 1; i++) {
            int minPos = i;

            for(int j=i+1; j<arr.length; j++) {
                minPos = comparator.compare(arr[j],arr[minPos])==-1 ? j : minPos; // 根据比较器确定比较策略，更加灵活
            }
            swap(arr, i, minPos);
        }
    }

    void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }







    // T类型的数组，指定比较器
    public void sort(Comparable[] arr) {
        for(int i=0; i<arr.length - 1; i++) {
            int minPos = i;

            for(int j=i+1; j<arr.length; j++) {
                minPos = arr[i].compareTo(arr[minPos])==-1 ? j : minPos; // 调用对象重写的compareTo方法比较（策略内嵌，不够灵活）
            }
            swap(arr, i, minPos);
        }
    }

    void swap(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
