package com.mine.test;

/**
 * @author 杜晓鹏
 * @create 2019-01-21 11:40
 */
public class Singleton {
    private static volatile Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null)
                    singleton = new Singleton();
            }
        }
        return singleton;
    }
}
