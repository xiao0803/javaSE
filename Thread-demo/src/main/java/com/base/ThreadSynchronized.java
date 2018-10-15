package com.base;

/*
 验证1：Synchronized保证代码块程序执行的一致性
 验证2：执行Synchronized修饰的代码块里面的程序时，同样遵循线程之间的相互竞争机制
 */
public class ThreadSynchronized {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ThreadSynchronized().init();
		System.out.println("主线程结束");
		System.out.println(Thread.currentThread().getName());
	}

	private void init() {
		// 此方法同时启动两个线程，去调用同一个方法的打印名字
		final Outputer outputer = new Outputer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				/*while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output("ABCDEFGHIJK");
				}*/
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				/*int i = 10;
				while (i > 0) {
					i--;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output("123456789");// 验证1
					//System.out.print("111");//验证2
				}*/

			}
		}).start();

	}

	class Outputer {
	//static class Outputer {
		public void output(String name) {
			int len = name.length();
			synchronized (Object.class) {
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}

	}
}