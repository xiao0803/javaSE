package com.java8.demo.completableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(10);
		Future<Integer> f = es.submit(() -> {
			// 长时间的异步计算
			// ……
			// 然后返回结果
			return 100;
			});
		// while(!f.isDone())
		f.get();
	}
}
