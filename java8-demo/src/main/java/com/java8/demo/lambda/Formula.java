package com.java8.demo.lambda;

/**
 * lambda表达式的使用例子;
 * 只允许有一个未实现的方法。
 */
public interface Formula {
	
	double calculate(int a);

	/**
	 * Java 8允许我们给接口添加多个非抽象的方法实现.
	 * @param a
	 * @return
	 */
	default double sqrt(int a) {
		return Math.sqrt(a);//求平方
	}
	
	default String test(String a) {
		return a;
	}
	
}
