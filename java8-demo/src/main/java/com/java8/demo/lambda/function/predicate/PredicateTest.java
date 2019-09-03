package com.java8.demo.lambda.function.predicate;

import java.util.ArrayList;

import com.java8.demo.lambda.function.Employee;

public class PredicateTest {

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        String[] prefix = {"A", "B"};
        for (int i = 1; i <= 10; i++)
            employees.add(new Employee(prefix[i % 2] + i, i * 1000));
        
        System.out.println("输出所有员工信息：" + employees);
        
        //删除不需要交税的员工（小于等于4000员工）
        employees.removeIf((Employee employee) -> {
        	return employee.getSalary() <= 4000;
        });
       // employees.removeIf(employee -> employee.getSalary() <= 4000);
        
        System.out.println("输出需要交税员工的信息：" + employees);

    }

    
}
