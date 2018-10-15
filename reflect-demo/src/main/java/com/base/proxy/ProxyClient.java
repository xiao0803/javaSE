package com.base.proxy;

interface Subject {
	public void doSomething();
}

// 被代理类
class RealSub implements Subject {
	public void doSomething() {
		System.out.println("RealSub call doSomething()");
	}
}

// 代理类
class SubjectProxy implements Subject {
	Subject subimpl = new RealSub();

	public void doSomething() {
		subimpl.doSomething();
	}
}

//客户端类
public class ProxyClient {
	public static void main(String[] args) {
		Subject sub = new SubjectProxy();
		sub.doSomething();
	}

}
