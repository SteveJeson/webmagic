package netProgramDemos;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ThreadPool extends ThreadGroup{

    private boolean isColosed = false;

    private LinkedList<Runnable> workQueue;

    private static int threadPoolID;

    private int threadID;

    public ThreadPool (int poolSize) {
        super("ThreadPool-"+(threadPoolID++));
        setDaemon(true);
        workQueue = new LinkedList<Runnable>();
        for (int i=0;i<poolSize;i++) {
            new WorkThread().start();
        }
    }

    /**向工作队列中加入一个新任务，由工作线程去执行该任务*/
    public synchronized void execute (Runnable task) {
        if (isColosed) {
            throw new IllegalStateException();
        }
        if (workQueue != null) {
            workQueue.add(task);
            notify();
        }
    }

    /**从工作队列中取出一个任务，工作线程会执行该方法*/
    protected synchronized Runnable getTask () throws InterruptedException{
        while (workQueue.size() == 0) {
            if(isColosed) return null;
            wait();
        }
        return workQueue.removeFirst();
    }

    /**关闭线程池*/
    public synchronized void close () {
        if (!isColosed) {
            isColosed = true;
            workQueue.clear();
            interrupt();        //中断所有线程类，该方法继承自ThreadGoup类
        }
    }

    /**等待工作线程把所有任务执行完*/
    public void join () {
        synchronized (this) {
            isColosed = true;
            notifyAll();            //唤醒还在getTask方法中等待任务的工作线程
        }
        Thread[] threads = new Thread[activeCount()];
        //enumerate()方法继承自ThreadGoup类，获取线程组当中所有活着的工作线程
        int count = enumerate(threads);
        for (int i=0;i<count;i++) {     //等待所有工作线程运行结束
            try {
                threads[i].join();          //等待工作线程运行结束
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class WorkThread extends Thread {

    }
}
