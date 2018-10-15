package com.concurrent.queue;

/**
 * Created by liujinhong on 2017/3/7. 基于链表实现队列
 */
public class LinkedQueue<E> {
	class Node<E> {
		Node<E> next = null;
		E data;

		public Node(E data) {
			this.data = data;
		}
	}

	private Node<E> head = null;
	private Node<E> tail = null;

	public boolean isEmpty() {
		return head == null;
	}

	public void offer(E e) {
		Node<E> node = new Node<E>(e);
		if (isEmpty()) {
			head = node;
			tail = node;
			return;
		}
		tail.next = node;
		tail = node;
	}

	public E poll() {
		if (isEmpty()) {
			return null;
		}
		E data = head.data;
		head = head.next;
		return data;
	}

	public int size() {
		Node<E> temp = head;
		int len = 0;
		while (temp != null) {
			len++;
			temp = temp.next;
		}
		return len;
	}

	public static void main(String[] args) {
		LinkedQueue<String> queue = new LinkedQueue<>();
		queue.offer("a");
		queue.offer("b");

		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}
}
