package com.java8.demo.lambda.function.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.java8.demo.lambda.function.Employee;

public class FunctionTest {

	public static void main(String[] args) {
		ArrayList<Employee> employees = new ArrayList<>();
		String[] prefix = { "B", "A" };
		for (int i = 1; i <= 10; i++)
			employees.add(new Employee(prefix[i % 2] + i, i * 1000));
		
		// 公司对单个员工的支出数组
		int[] expenses = FunctionTest.listToArray(employees, (Employee employee) -> {
			/**
			 * 计算公司支出；假设公司公积金和社保为工资的20%。
			 */
			return Double.valueOf(employee.getSalary() * (1 + 0.2)).intValue();			
        });
		// 单个员工的收入数组
		int[] incomes = FunctionTest.listToArray(employees, (Employee employee) -> {
			/**
			 * 员工实际到手工资；假设员工薪水 * 80% 为到手工资。
			 */
			return Double.valueOf(employee.getSalary() * (1 - 0.2)).intValue();				
        }); 
		System.out.println("社保+公积金+税=" + (FunctionTest.sum(expenses) - FunctionTest.sum(incomes)) + "元");
	}

	private static int[] listToArray(List<Employee> list, Function<Employee, Integer> function) {
		int[] ints = new int[list.size()];
		for (int i = 0; i < ints.length; i++)
			ints[i] = function.apply(list.get(i));
		return ints;
	}

	private static int sum(int[] salarys) {
		int sum = 0;
		for (int i = 0; i < salarys.length; i++)
			sum += salarys[i];
		return sum;
	}

}
