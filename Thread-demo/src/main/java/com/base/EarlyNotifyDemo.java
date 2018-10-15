package com.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * “过程通知”问题的demo；
 * 
 * 程序出现了数组下标越界的错误，简单计算一下，3个RemoveThread的等待时间之和是300毫秒，
 * 而AddThread需要600毫秒之后才会执行，所以在600毫秒之前，所有的RemoveThread都因为等待条件list为空陷入等待，
 * 进入等待队列中。当执行到600毫秒的时候，唤醒全部的RemoveThread，从wait返回的RemoveThread不会重新判断list的等待条件，
 * 这样造成的后果就是三个RemoveThread同时删除list中的一个元素，自然就会出现下标越界错误了；
 * 也正是3个RemoveThread在被唤醒到重新获得锁的期间等待条件被修改了，导致出现了错误的结果。
 * 
 */
public class EarlyNotifyDemo {

	// 元素列表
	private List<String> list;
	// 日期格式器
	private static final DateFormat format = new SimpleDateFormat("HH:mm:ss");
	// 计数器
	private AtomicLong number = new AtomicLong();

	public EarlyNotifyDemo() {
		list = new ArrayList<>();
	}

	// 对list执行删除的元素
	public void remove() throws InterruptedException {
		synchronized (list) {
			if (list.isEmpty()) {
				// 只要list为空，那么调用此方法的线程必须等待
				list.wait();
			}
			// 如果执行到这里，说明list已经不为空了
			// 这样执行元素的删除操作才不会出错
			String item = list.remove(0);
			System.out.println(Thread.currentThread().getName() + ": remove element " + item + "! " + format.format(new Date()));
		}
	}

	// 对list执行添加操作
	public void add() {
		synchronized (list) {
			// 添加元素不要进行判断
			list.add("" + number.incrementAndGet());
			System.out.println(Thread.currentThread().getName() + ": add item " + number.get() + " " + format.format(new Date()));
			list.notifyAll();
		}
	}

	static class AddThread implements Runnable {
		private EarlyNotifyDemo es;

		public AddThread(EarlyNotifyDemo es) {
			this.es = es;
		}

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(600);
				es.add();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class RemoveThread implements Runnable {
		private EarlyNotifyDemo es;

		public RemoveThread(EarlyNotifyDemo es) {
			this.es = es;
		}

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
				es.remove();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		EarlyNotifyDemo es = new EarlyNotifyDemo();
		for (int i = 0; i < 3; i++) {
			new Thread(new RemoveThread(es), "RemoveThread" + i).start();
		}
		new Thread(new AddThread(es), "AddThread").start();
	}
}
