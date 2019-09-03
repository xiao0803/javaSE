package com.java8.demo.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StreamTest {
	public static void main(String[] args) {
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		
		
		//Filter 过滤测试
		System.out.println("Filter 过滤测试打印");
		stringCollection
	    .stream()
	    .filter((s) -> s.startsWith("a"))
	    .forEach(System.out::println);
		
		//Sort 排序测试
		/**
		 * 排序是一个中间操作，返回的是排序好后的Stream。如果你不指定一个自定义的Comparator则会使用默认排序。
		 * 需要注意的是，排序只创建了一个排列好后的Stream，而不会影响原有的数据源，排序之后原数据stringCollection是不会被修改的。
		 */
		System.out.println("Sort 排序测试打印");
		stringCollection
	    .stream()
	    .sorted()
	    .forEach(System.out::println);
		
		//Map 映射测试
		System.out.println("Map 映射测试打印");
		stringCollection
	    .stream()
	    .map(String::toUpperCase)
	    .forEach(System.out::println);
		
		//Match 匹配测试
		System.out.println("Match 匹配测试打印");
		boolean anyStartsWithA = stringCollection
		        .stream()
		        .anyMatch((s) -> s.startsWith("a"));
		System.out.println("anyMatch:" + anyStartsWithA);// true
			    
			
		boolean allStartsWithA = stringCollection
		        .stream()
		        .allMatch((s) -> s.startsWith("a"));
		System.out.println("allMatch:" + allStartsWithA);// false
			
		boolean noneStartsWithZ = 
		    stringCollection
		        .stream()
		        .noneMatch((s) -> s.startsWith("z"));
		System.out.println("noneMatch:" + noneStartsWithZ);// true
		
		//Count 计数测试
		System.out.println("Count 计数测试打印");
		long startsWithB = stringCollection
		        .stream()
		        .filter((s) -> s.startsWith("b"))
		        .count();  
		System.out.println(startsWithB);// 3
		
		//Reduce 规约测试
		System.out.println("Reduce 规约测试打印");
		Optional<String> reduced = stringCollection
		        .stream()
		        .sorted()
		        .reduce((s1, s2) -> s1 + "#" + s2);
		reduced.ifPresent(System.out::println);// "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"

		//并行Streams
		System.out.println("并行Streams测试打印");
		StreamTest.parallel();
	}
	
	//并行Streams
	private static void parallel() {
		int max = 100000;
		List<String> values = new ArrayList<>(max);
		for (int i = 0; i < max; i++) {
		    UUID uuid = UUID.randomUUID();
		    values.add(uuid.toString());
		}
		//串行排序耗时;
		long sequentialT0 = System.nanoTime();
		long sequentialCount = values.stream().sorted().count();
		System.out.println("串行排序元素数:" + sequentialCount);
		long sequentialT1 = System.nanoTime();
		long sequentialMillis = TimeUnit.NANOSECONDS.toMillis(sequentialT1 - sequentialT0);
		System.out.println(String.format("串行排序耗时: %d ms", sequentialMillis));
		//并行排序耗时
		long parallelT0 = System.nanoTime();
		long parallelCount = values.parallelStream().sorted().count();
		System.out.println("并行排序元素数:" + parallelCount);
		long parallelT1 = System.nanoTime();
		long parallelMillis = TimeUnit.NANOSECONDS.toMillis(parallelT1 - parallelT0);
		System.out.println(String.format("并行排序耗时: %d ms", parallelMillis));
	}
	
}
