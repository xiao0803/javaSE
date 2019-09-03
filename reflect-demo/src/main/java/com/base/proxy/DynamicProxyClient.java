package com.base.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

//抽象主题类A ，JDK的动态代理是基于接口的，所以一定要是interface  
interface SubjectA {
	public abstract void request();
}

// 抽象主题类B
interface SubjectB {
	public abstract void doSomething();
}

// 真实主题类A，即被代理类
class RealSubjectA implements SubjectA {
	public void request() {
		System.out.println("RealA request() ...");
	}
}

// 真实主题类B，即被代理类
class RealSubjectB implements SubjectB {
	public void doSomething() {
		System.out.println("RealB doSomething() ...");
	}
}

// 动态代理类，实现InvocationHandler接口
class DynamicProxy implements InvocationHandler {
	Object obj = null;

	/**
	 * @param obj
	 */
	public DynamicProxy(Object obj) {
		this.obj = obj;
	}

	/**
	 * 覆盖InvocationHandler接口中的invoke()方法
	 * 
	 * 更重要的是，动态代理模式可以使得我们在不改变原来已有的代码结构 的情况下，对原来的“真实方法”进行扩展、增强其功能，并且可以达到
	 * 控制被代理对象的行为，下面的before、after就是我们可以进行特殊 代码切入的扩展点了。
	 * 
	 * @param proxy
	 *            ，表示执行这个方法的代理对象；
	 * 
	 * @param method
	 *            ，表示真实对象实际需要执行的方法（关于Method类参见Java的反射机制）；
	 * 
	 * @param args
	 *            ，表示真实对象实际执行方法时所需的参数。
	 * 
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/*
		 * before
		 */
		//doSomething();
		
		Object result = method.invoke(this.obj, args);

		/*
		 * after
		 */
		//doSomething();
		
		return result;
	}
}

/**
 * @author LENOVO
 * 测试类
 *
 */
public class DynamicProxyClient {

	public static void main(String[] args) {

		// 被代理类的实例
		SubjectA realSubjectA = new RealSubjectA();
		// loader，表示类加载器，对于不同来源（系统库或网络等）的类需要不同的类加载器来加载，这是Java安全模型的一部分。
		// 可以使用null来使用默认的加载器；
		ClassLoader loader = realSubjectA.getClass().getClassLoader();
		// interfaces，表示接口或对象的数组，它就是前述代理对象和真实对象都必须共有的父类或者接口；
		Class<?>[] interfaces = realSubjectA.getClass().getInterfaces();
		// handler，表示调用处理器，它必须是实现了InvocationHandler接口的对象，其作用是定义代理对象中需要执行的具体操作。
		InvocationHandler handler = new DynamicProxy(realSubjectA);

		// 获得代理的实例 A
		SubjectA proxyA = (SubjectA) Proxy.newProxyInstance(loader, interfaces, handler);
		proxyA.request();

		// 获得代理的实例 B
		RealSubjectB realSubjectB = new RealSubjectB();
		SubjectB proxyB = (SubjectB) Proxy.newProxyInstance(realSubjectB.getClass().getClassLoader(), realSubjectB.getClass().getInterfaces(), new DynamicProxy(realSubjectB));

		proxyB.doSomething();

		// 打印生成代理类的类名
		System.out.println(proxyA.getClass().getSimpleName());
		System.out.println(proxyB.getClass().getSimpleName());

		System.out.println(DynamicProxyClient.class.getSimpleName());
		//生成代理类字节码文件，并输出保存
		createProxyClassFile();

	}

	/**
	 * 生成代理类字节码文件，并输出保存到本地磁盘
	 */
	@SuppressWarnings("restriction")
	private static void createProxyClassFile() {
		byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", RealSubjectA.class.getInterfaces());
		//String path = "G:/javacode/javase/Test/bin/proxy/StuProxy.class";
		String path = "F:/test/fff/SubjectAProxy.class";
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(classFile);
			fos.flush();
			System.out.println("代理类class文件写入成功");
		} catch (Exception e) {
			System.out.println("写文件错误");
		}
	}

}