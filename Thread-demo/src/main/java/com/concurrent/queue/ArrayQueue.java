package com.concurrent.queue;

import java.util.Arrays;

/**
 * Created by liujinhong on 2017/3/7. 基于素组实现队列
 */
public class ArrayQueue<E> {
	Object[] queue;
	int size;
	
	public ArrayQueue() {
		queue = new Object[10];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E poll() {
		if (isEmpty()){
			return null;
		}
		E data = (E) queue[0];
		
		//参数详解：源素组、截取的起始位置、目标素组、覆盖的起始位置、对源素组截取长度
		System.arraycopy(queue, 1, queue, 0, size - 1);
		size--;
		return data;
	}

	private void ensureCapacity(int size) {
		if (size > queue.length) {
			int len = queue.length + 10;
			queue = Arrays.copyOf(queue, len);
		}
	}

	public void offer(E data) {
		ensureCapacity(size + 1);
		queue[size++] = data;
	}

	public static void main(String[] args) {
		ArrayQueue<Integer> queue = new ArrayQueue<>();

		for (int i = 0; i < 20; i++) {
			queue.offer(i);
		}

		for (int i = 0; i < 20; i++) {
			System.out.println(queue.poll());
		}
	}
}