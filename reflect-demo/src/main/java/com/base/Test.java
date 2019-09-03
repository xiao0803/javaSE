package com.base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

	LinkedList<Object> aa = new LinkedList<Object>();
	
	public static void main(String[] args) {
		System.out.println("1".hashCode());
		System.out.println(Test.hashTest("1"));
		
		char val[] = new char[2];
		val[0] = 'a';
		val[1] = 'b';
		
		System.out.println(val[0]);
		System.out.println(10 + val[0]);
		
		
	}
	
    static final int hashTest(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
	
}
