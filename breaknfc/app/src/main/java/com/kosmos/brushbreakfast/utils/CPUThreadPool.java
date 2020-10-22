package com.kosmos.brushbreakfast.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 静态内部类单例，懒加载
 */
public class CPUThreadPool {

    private ThreadPoolExecutor executor;

    /**
     * CPU密集型线程池
     * corePoolSize：常驻线程，为CPU线程数
     * maximumPoolSize：最大线程数，为corePoolSize的两倍，阻塞队列满了扩容会使用
     */
    private CPUThreadPool() {
        /**
         * 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
         */
        //核心常驻线程池的数量，同时能够执行的线程数量
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        //最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
        int maximumPoolSize = corePoolSize * 2;
        //非常驻线程存活时间
        long keepAliveTime = 1L;
        //非常驻线程存活时间单位
        TimeUnit unit = TimeUnit.MINUTES;
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new SynchronousQueue<Runnable>(),//这里阻塞队列长度为1，由于不能设置为0，所以可能存在1个任务被阻塞的隐患，但测试中没有出现过
//                new LinkedBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                new NewThreadForRejectHandler()
        );

    }

    private static class ThreadPoolHolder {
        private static CPUThreadPool mInstance = new CPUThreadPool();
    }

    public static CPUThreadPool getInstance() {
        return ThreadPoolHolder.mInstance;
    }

    //自定义拒绝策略:被拒绝的任务在一个新的线程中执行
    private class NewThreadForRejectHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            new Thread(runnable, "new thread for reject").start();
            System.out.println(("新线程中的任务被拒绝：Task " + runnable.toString() + "rejected from " + executor.toString()));
//            LogUtils.e("新线程中的任务被拒绝：Task " + runnable.toString() + "rejected from " + executor.toString());
        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null || executor == null) return;
        executor.execute(runnable);
    }

    /**
     * 从线程池中移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null || executor == null) return;
        executor.remove(runnable);
    }

    /**
     * 关闭线程池
     * 不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
     */
    public void shutdown() {
        if (executor == null) return;
        executor.shutdown();
    }

    /**
     * 线程池中任务是否执行完
     *
     * @return
     */
    public boolean isTerminated() {
        if (executor == null) return false;
        return executor.isTerminated();
    }

    public int getActiveThreadCount() {
        return executor.getActiveCount();
    }
}
