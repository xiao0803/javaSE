package com.concurrent.callableAndFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 比较ExecutorService与CompletionService区别
 * 
 * @author LENOVO
 *
 *         这两者最主要的区别在于submit的task不一定是按照加入自己维护的list顺序完成的。
 *
 *         ExecutorService的实现：是从list中遍历的每个Future对象并不一定处于完成状态，这时调用get()方法就会被阻塞住，
 *         如果系统是设计成每个线程完成后就能根据其结果继续做后面的事，这样对于处于list后面的但是先完成的线程就会增加了额外的等待时间。
 *
 *         CompletionService的实现：是维护一个保存Future对象的BlockingQueue。
 *         只有当这个Future对象状态是结束的时候，才会加入到这个Queue中，
 *         take()方法其实就是Producer-Consumer中的Consumer
 *         。它会从Queue中取出Future对象，如果Queue是空的，就会阻塞在那里，
 *         直到有完成的Future对象加入到Queue中。因此，先完成的必定先被取出。这样就减少了不必要的等待时间。
 *
 */

public class CallableAndFuture3 {

	static class Task implements Callable<String> {
		private int i;

		public Task(int i) {
			this.i = i;
		}

		@Override
		public String call() throws Exception {
			Thread.sleep(10000);
			return Thread.currentThread().getName() + "执行完任务：" + i;
		}
	}

	public static void main(String[] args) {
		futureOfExecutorService();
		//futureOfCompletionService();
	}

	private static void futureOfExecutorService() {
		int numThread = 5;
		ExecutorService executor = Executors.newFixedThreadPool(numThread);
		List<Future<String>> futureList = new ArrayList<Future<String>>();
		for (int i = 0; i < numThread; i++) {
			Future<String> future = executor.submit(new CallableAndFuture3.Task(i));
			futureList.add(future);
		}

		while (numThread > 0) {
			for (Future<String> future : futureList) {
				String result = null;
				try {
					result = future.get(0, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					// 超时异常直接忽略
				}
				if (null != result) {
					futureList.remove(future);
					numThread--;
					System.out.println(result);
					// 此处必须break，否则会抛出并发修改异常。（也可以通过将futureList声明为CopyOnWriteArrayList类型解决）
					break;
				}
			}
		}
	}

	private static void futureOfCompletionService() {
		int numThread = 5;
		ExecutorService executor = Executors.newFixedThreadPool(numThread);
		CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
		for (int i = 0; i < numThread; i++) {
			completionService.submit(new CallableAndFuture3.Task(i));
		}

		for (int i = 0; i < numThread; i++) {
			try {
				System.out.println(completionService.take().get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
