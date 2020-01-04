package com.thread;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Depot {
    //初始仓库为0，最大为10，超过10生产者停止生产
    private int size;
    private int maxSize=10;

    private Condition prodCondition;
    private Condition consumCondition;

    private Lock lock;
    public Depot(){

        this.size=0;
        this.lock=new ReentrantLock();
        //可以看出Condition对象依赖于Lock锁
        this.prodCondition=this.lock.newCondition();
        this.consumCondition=this.lock.newCondition();
    }

    /*
     * 生产者生产方法
     */
    public void prod(){

        lock.lock();
        try{
            //如果生产超过max值，则生产者进入等待
            while(size+1>maxSize){
                try {
                    System.out.println(Thread.currentThread().getName()+"生产者进入等待状态");
                    prodCondition.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            size+=1;
            System.out.println(Thread.currentThread().getName()+" 生产了一个 "+1+" 总共还有 "+size);

            prodCondition.signal();

        }finally {
            lock.unlock();
        }
    }

    /*
     * 消费者消费方法
     */
    public void consum(){

        lock.lock();
        try{
            //如果当前大小减去要消费的值，如果小于0的话，则进入等待
            while(size-1<0){
                try {
                    System.out.println(Thread.currentThread().getName()+" 消费者进入等待状态");
                    consumCondition.await();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            size-=1;
            System.out.println(Thread.currentThread().getName()+" 消费者消费了 "+1+" 个，总共还有 "+size);
            //唤醒生产者线程
            prodCondition.signal();
        }finally {
            lock.unlock();
        }
    }
}