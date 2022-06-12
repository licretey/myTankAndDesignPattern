package com.licretey.jvm;

public class classloader {
    public static void main(String[] args) {
        System.out.println(P.count); //2
        System.out.println(T.count); //3
    }
}

class T {
    public static int count = 2;
    public static T t = new T();

    private T(){
        count++;
    }
}

class P {
    public static P p = new P();
    public static int count = 2;

    private P(){
        count++;
    }
}
