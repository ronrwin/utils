package com.audionote.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorUtils {
	// 线程池分配
	public static final int DEFAULT_CORE_POOL_SIZE = 5;
	public static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
	public static final int DEFAULT_KEEP_ALIVE = 1;

	/** 使用JDK1.5以后的线程池技术, 提高线程操作性能 */
	private static ExecutorService executor = null;
	private static final Object LOCK = new Object();
	// 线程队列
	private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue<Runnable>(
			10);
	// 线程工厂
	private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
		private final AtomicInteger counter = new AtomicInteger(0);

		public Thread newThread(Runnable runnable) {
			return new Thread(runnable, "WinAndroid"
					+ counter.incrementAndGet());
		}
	};

	/**
	 * 用法:<br>
	 * ExecutorUtils.getExecutorService().execute(Runnable);
	 * 
	 * @return
	 */
	public static ExecutorService getExecutorService() {
		synchronized (LOCK) {
			if (executor == null) {
				executor = new ThreadPoolExecutor(
						DEFAULT_CORE_POOL_SIZE,
						DEFAULT_MAXIMUM_POOL_SIZE,
						DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS,
						DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
			}
		}
		return executor;
	}

	/**
	 * 关闭线程池
	 */
	public static void releaseExecutorService() {
		synchronized (LOCK) {
			if (executor != null) {
				executor.shutdown();
				executor = null;
			}
		}
	}
}
