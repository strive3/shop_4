package com.mine.test;

/**
 * @author 杜晓鹏
 * @create 2019-01-24 9:22
 *
 * 死锁的例子
 */
public class SynchronizedDouble {



    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = new Object();
        new Thread(new Runnable(){

            @Override
            public void run() {
                synchronized (object1){
                    System.out.println(object1.toString());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object2){
                        System.out.println(object1.hashCode());
                    }
                }
            }
        }).start();

        new Thread(()-> {
            synchronized (object2) {
                System.out.println(object2.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1) {
                    System.out.println(object2.hashCode());
                }
            }
        }).start();

    }
}
