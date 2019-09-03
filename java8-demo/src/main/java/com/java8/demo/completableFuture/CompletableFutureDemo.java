package com.java8.demo.completableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CompletableFutureDemo {
	private static Random rand = new Random();
	private static long t = System.currentTimeMillis();

	/*static int getMoreData() {
		System.out.println("begin to start compute");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
		return rand.nextInt(1000);
	}*/

	public static void main(String[] args) throws Exception {
		
		//complete（主动完成计算）方法测试
		/*final CompletableFuture<Integer> f = BasicMain.compute();
		new Client("Client1", f).start();
		new Client("Client2", f).start();
		System.out.println("waiting");
		f.complete(100);
		// f.completeExceptionally(new Exception());
		System.in.read();*/
		
		//whenComplete（计算结果完成时的处理）方法测试
		/*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(WhenCompleteMethodTest::getMoreData);
		Future<Integer> f = future.whenComplete((v, e) -> {
			System.out.println(v);
			System.out.println(e);
		});
		System.out.println(f.get());
		System.in.read();*/

		//thenApplyAsync（转换）方法测试
		/*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			return 100;
		});
		CompletableFuture<String> f = future.thenApplyAsync(i -> i * 10).thenApply(i -> i.toString());
		System.out.println(f.get());*/
		// 输出"1000"
		
		
		//thenAccept（纯消费）方法测试
		/*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			return 100;
		});
		CompletableFuture<Void> f = future.thenAccept(System.out::println);
		System.out.println(f.get());*/
		
		//thenCompose（组合）方法测试
		/*CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			return 110;
		});
		CompletableFuture<String> f = future.thenCompose(i -> {
			return CompletableFuture.supplyAsync(() -> {
				return (i * 10) + "";
			});
		});
		System.out.println(f.get());*/ 
		// 输出"1100"
		
		//Either相关方法测试
		/**
		 * 这个例子有时会输出100,有时候会输出200,哪个Future先完成就会根据它的结果计算。
		 */
		/*Random rand = new Random();
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000 + rand.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 100;
		});
		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10000 + rand.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 200;
		});
		CompletableFuture<String> f = future.applyToEither(future2, i -> i.toString());
		System.out.println(f.get());*/
		
		//allOf和anyOf方法测试
		/**
		 * 下面的代码运行结果有时是100,有时是"abc"。
		 * 但是anyOf和applyToEither不同。anyOf接受任意多的CompletableFuture但是applyToEither只是判断两个CompletableFuture，
		 * anyOf返回值的计算结果是参数中其中一个CompletableFuture的计算结果，applyToEither返回值的计算结果却是要经过fn处理的。
		 * 当然还有静态方法的区别，线程池的选择等。
		 */
		/*Random rand = new Random();
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10000 + rand.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 100;
		});
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10000 + rand.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "abc";
		});
		// CompletableFuture<Void> f = CompletableFuture.allOf(future1,future2);
		CompletableFuture<Object> f = CompletableFuture.anyOf(future1, future2);
		System.out.println(f.get());*/

	}
}

class Client extends Thread {
	CompletableFuture<Integer> f;

	Client(String threadName, CompletableFuture<Integer> f) {
		super(threadName);
		this.f = f;
	}

	@Override
	public void run() {
		try {
			System.out.println(this.getName() + ": " + f.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}

