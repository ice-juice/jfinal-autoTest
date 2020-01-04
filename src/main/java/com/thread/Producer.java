package com.thread;

import com.jfinal.kit.Prop;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable  {
    private volatile boolean  isRunning = true;//是否在运行标志
    private BlockingQueue<String> queue;//阻塞队列
    private String name;
    private static AtomicInteger count = new AtomicInteger();//自动更新的值

    //构造函数
    public Producer(BlockingQueue<String> queue,String name) {
        this.queue = queue;
        this.name=name;
    }

    public void run() {
        String data = null;
        System.out.println(Thread.currentThread().getName()+" 启动生产者线程！");
        try {
            while (isRunning) {
                Thread.sleep(1000);

                //以原子方式将count当前值加1
                data = "" + count.incrementAndGet();
                System.out.println(Thread.currentThread().getName()+this.name+" 将生产数据：" + data + "放入队列中");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName()+" 退出生产者线程！");
        }
    }

    public void stop() {
        isRunning = false;
    }
}
