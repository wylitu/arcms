package com.suniusoft.common.threadPool;

import java.util.concurrent.*;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: <p>
 *                 线程池
 *                </p>
 *  @author litu  litu@shufensoft.com
 *  @date 2015/6/14  
 */
public class ThreadPoolExecutor extends java.util.concurrent.ThreadPoolExecutor {

    /**
     *  默认保留的线程池大小。
     */
    private static int DEFAULT_CORE_POOLSIZE = 0;

    /**
     * 默认线程池的最大大小(CPU密集型任务配置尽可能小的线程，如配置Ncpu+1个线程的线程池。
     * IO密集型任务则由于线程并不是一直在执行任务，则配置尽可能多的线程，如2*Ncpu)
     */
    private static int DEFAULT_MAXIMUM_POOLSIZE = Runtime.getRuntime().availableProcessors()*2;

    /**
     * 默认空闲线程结束的超时时间。
     */
    private static long DEFAULT_KEEP_ALIVE_TIME = 60L;

    /**
     *  默认队列大小
     */
    private static int DEFAULT_ARRAY_BLOCKING_QUEUE_SIZE = 1000;


    public ThreadPoolExecutor(){

        this(DEFAULT_CORE_POOLSIZE, DEFAULT_MAXIMUM_POOLSIZE, DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue(DEFAULT_ARRAY_BLOCKING_QUEUE_SIZE),new CallerRunsPolicy());

    }


    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

}
