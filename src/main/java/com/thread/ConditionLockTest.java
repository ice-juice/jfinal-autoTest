package com.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLockTest {
    public static void main(String[] args){

        //相当于仓库
        Depot depot=new Depot();

        //创建两个生产者一个消费者
        Produce producer1=new Produce(depot);
        Produce producer2=new Produce(depot);
        Consumer consumer1=new Consumer(depot);

        //采用线程池方式
        Executor executors= Executors.newFixedThreadPool(5);
        executors.execute(producer1);
        executors.execute(producer2);
        executors.execute(consumer1);
    }
}
//生产者
class Produce implements  Runnable {

    Depot depot;
    public Produce(Depot depot){
        this.depot=depot;
    }
    public void  run(){
        while(true){
            depot.prod();
        }
    }
}

//消费者
class Consumer implements  Runnable{

    Depot depot;
    public Consumer(Depot depot){
        this.depot=depot;
    }
    public void run(){
        while(true){
            depot.consum();
        }
    }
}
