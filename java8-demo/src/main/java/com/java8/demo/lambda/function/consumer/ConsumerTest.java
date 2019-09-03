package com.java8.demo.lambda.function.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.java8.demo.lambda.function.Employee;

public class ConsumerTest {

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        String[] prefix = {"A", "B"};
        for (int i = 1; i <= 10; i++)
            employees.add(new Employee(prefix[i % 2] + i, i * 1000));
        //输出需要交税的员工（大于4000的员工）
        //employees.forEach(new SalaryConsumer());
        employees.forEach((Employee employee) -> {
        	if (employee.getSalary() > 4000) {
                System.out.println(employee.getName() + "要交税了.");
            }
        });
        // 输出需要名字前缀是‘A’的员工信息
        //employees.forEach(new NameConsumer());
        employees.forEach(employee -> {
        	if (employee.getName().startsWith("A")) {
                System.out.println(employee.getName() + " salary is " + employee.getSalary());
            }
        });
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.forEach((key, val) -> {
        	System.out.println("key:" + key);
        	System.out.println("val:" + val);
        });
    }

/*    
    // 输出需要交税的员工
    static class SalaryConsumer implements Consumer<Employee> {

        @Override
        public void accept(Employee employee) {
            if (employee.getSalary() > 2000) {
                System.out.println(employee.getName() + "要交税了.");
            }
        }
        
    }
    
    // 输出需要名字前缀是‘A’的员工信息
    static class NameConsumer implements Consumer<Employee> {

        @Override
        public void accept(Employee employee) {
            if (employee.getName().startsWith("A")) {
                System.out.println(employee.getName() + " salary is " + employee.getSalary());
            }
        }

    }
    */
    
}
