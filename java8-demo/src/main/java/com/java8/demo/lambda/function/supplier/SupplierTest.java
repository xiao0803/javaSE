package com.java8.demo.lambda.function.supplier;

import java.util.function.Supplier;

import com.java8.demo.lambda.function.Employee;

public class SupplierTest {
	public static void main(String[] args) {
	    // 生成固定工资的员工
	    //Supplier<Employee> supplier = () -> new Employee();
	    Supplier<Employee> supplier = Employee::new;
	    Employee employee1 = supplier.get();
	    employee1.setName("test1");
	    Employee employee2 = supplier.get();
	    employee2.setName("test2");
	    System.out.println("employee1:" + employee1);
	    System.out.println("employee2:" + employee2);
	}
}
