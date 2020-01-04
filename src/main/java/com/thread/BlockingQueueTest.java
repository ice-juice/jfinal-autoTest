package com.thread;
import	java.util.Random;

import java.util.concurrent.*;

public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);

        //new了两个生产者和一个消费者，同时他们共用一个queue缓存队列
        Producer producer1 = new Producer(queue,"测试1");
        Producer producer2 = new Producer(queue,"测试2");
//        Consumer consumer = new Consumer(queue);

        // 通过线程池启动线程
        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(producer1);
        service.execute(producer2);

        String data=((Math.random() * 10 + 1) * 1000)+"abc";
        //设定的等待时间为2s，如果超过2s还没加进去返回false
        while (!queue.offer(data, 2, TimeUnit.SECONDS)) {
            System.out.println(Thread.currentThread().getName()+" 放入数据失败：" + data);
            Thread.sleep(1000);
        }
        Producer producer3= new Producer(queue,"测试3");
        service.execute(producer3);
        // 执行5s
        Thread.sleep(5 * 1000);
        producer1.stop();
        producer2.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }
}
