package io;

//: io/Worm.java
// Demonstrates object serialization.
import java.io.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Data implements Serializable {
	private int n;

	public Data(int n) {
		this.n = n;
	}

	public String toString() {
		return Integer.toString(n);
	}
}

public class Worm implements Serializable {
	private static Random rand = new Random(47);
	private Data[] d = { new Data(rand.nextInt(10)), new Data(rand.nextInt(10)), new Data(rand.nextInt(10)) };
	private Worm next;
	private char c;

	// Value of i == number of segments
	public Worm(int i, char x) {
		print("Worm constructor: " + i);
		c = x;
		if (--i > 0)
			next = new Worm(i, (char) (x + 1));
	}

	public Worm() {
		print("Default constructor");
	}

	public String toString() {
		StringBuilder result = new StringBuilder(":");
		result.append(c);
		result.append("(");
		for (Data dat : d)
			result.append(dat);
		result.append(")");
		if (next != null)
			result.append(next);
		return result.toString();
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Worm w = new Worm(6, 'a');
		print("w = " + w);
		/*************** 通过文件存取序列化对象（持久化）start ****************/
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ioTest/worm.out"));
		//多个序列化对象写入文件时，如同数组的方式存放
		out.writeObject("Worm storage\n");
		out.writeObject("Worm storage2\n");
		out.writeObject(w);
		out.close(); // Also flushes output
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("ioTest/worm.out"));
		//读取多个序列化对象文件时，按照存放的顺序取；注意强转类型也一定要与存入的时候一致，否则报错
		String s = (String) in.readObject();
		String s2 = (String) in.readObject();
		Worm w2 = (Worm) in.readObject();
		print(s + s2 + "w2 = " + w2);
		/*************** 通过文件存取序列化对象end ****************/
		
		/*************** 通过字节数组流存取序列化对象（不会持久化）start ****************/
		//把对象放入字节数组流
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out2 = new ObjectOutputStream(bout);
		out2.writeObject("Worm storage\n");
		out2.writeObject(w);
		out2.flush();
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
		s = (String) in2.readObject();
		Worm w3 = (Worm) in2.readObject();
		print(s + "w3 = " + w3);
		/*************** 通过字节数组流存取序列化对象end ****************/
	}
} /*
 * Output: Worm constructor: 6 Worm constructor: 5 Worm constructor: 4 Worm
 * constructor: 3 Worm constructor: 2 Worm constructor: 1 w =
 * :a(853):b(119):c(802):d(788):e(199):f(881) Worm storage w2 =
 * :a(853):b(119):c(802):d(788):e(199):f(881) Worm storage w3 =
 * :a(853):b(119):c(802):d(788):e(199):f(881)
 */// :~
