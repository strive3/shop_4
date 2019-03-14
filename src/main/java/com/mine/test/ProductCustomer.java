package com.mine.test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2019-01-24 10:32
 */
public class ProductCustomer {

    public static void main(String[] args) {
        Stack stack = new Stack();
        new Thread(new Product(stack)).start();
        new Thread(new Customer(stack)).start();
    }
}

class Stack{
    List<Phone> products = new LinkedList<>();
    private int index;
    public synchronized void push(Phone phone){
        while (products.size() > 20){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        products.add(phone);
        index ++;
    }

    public synchronized Phone pull(){
        while (products.size() <= 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        index--;
        return products.remove(index);

    }
}
class Product implements Runnable{
    Stack stack;
    public Product(Stack stack){
        this.stack = stack;
    }
    @Override
    public void run() {
        for (int i  = 0;i <= 20;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("生产了第"+(i)+"个产品");
            stack.push(new Phone());
        }
    }
}
class Customer implements Runnable{
    Stack stack;
    public Customer(Stack stack){
        this.stack = stack;
    }
    @Override
    public void run() {
        for (int i  = 0;i <= 20;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费了第"+(i)+"个产品");
            stack.pull();
        }
    }
}
class Phone{
    public Phone(){}
}

