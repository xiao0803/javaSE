package com.base;

public class Test {
	
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
