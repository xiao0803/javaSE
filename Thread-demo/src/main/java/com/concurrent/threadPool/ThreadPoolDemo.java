package com.concurrent.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 固定线程池大小
		// ExecutorService threadPool = Executors.newFixedThreadPool(3);
		// 可缓存的线程池
		// ExecutorService threadPool = Executors.newCachedThreadPool();
		// 单线程的线程池
		ExecutorService threadPool = Executors.newSingleThreadExecutor();

		// 向线程池中放入10个任务，每次只能为一个任务服务（单线程的线程池）
		for (int i = 1; i <= 10; i++) {
			final int task = i;
			// 执行任务（可以与new Thread（new Runnable(){}）相比，相当于10个任务创建10个线程，而此时只有一个线程）
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j <= 10; j++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for  task of " + task);
					}
				}
			});
		}
		// 10个任务放入线程池后，才会执行下面的代码，打印出此提示信息，说明已经放入了10个任务
		System.out.println("all of 10 tasks have committed! ");

		// 当线程池内存在线程时，是不会停止的，想要停止使用如下语句
		// threadPool.shutdownNow();

		// 定时执行任务
		// Executors.newScheduledThreadPool(3).scheduleAtFixedRate(
		// new Runnable(){
		// @Override
		// public void run() {
		// System.out.println("bombing!");
		//
		// }},
		// 6,
		// 2,
		// TimeUnit.SECONDS);
	}

}