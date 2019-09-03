package com.java8.demo.lambda;

import java.util.Objects;
import java.util.function.Predicate;

import com.java8.demo.lambda.Aaa;
import com.java8.demo.lambda.Formula;

/**
 * @author 
 * @email 
 * @dateTime 
 * @version 
 */
public class Client {
	
    public static void main(String[] args) throws Exception {
    	//lambda表达式的使用例子测试
    	/*Formula formula = (a) -> {
    		return a + 1.1;
    	};
    	System.out.println(formula.calculate(5));*/

    	//lambda之方法引用例子测试
    	/*Formula formula = Aaa::test1;
    	System.out.println("出参：" + formula.calculate(5));*/
    	
    	Predicate<String> predicate = (s) -> s.length() > 0;
    	predicate.test("foo");              // true
    	predicate.negate().test("foo");     // false
    	Predicate<Boolean> nonNull = Objects::nonNull;
    	Predicate<Boolean> isNull = Objects::isNull;
    	Predicate<String> isEmpty = String::isEmpty;
    	Predicate<String> isNotEmpty = isEmpty.negate();
    	
    }
    
}
