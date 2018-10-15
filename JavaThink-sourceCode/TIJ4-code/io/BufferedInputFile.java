package io;

//: io/BufferedInputFile.java
import java.io.*;

public class BufferedInputFile {
	// Throw exceptions to console:
	public static String read(String filename) throws IOException {
		// Reading input by lines:
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		/*while ((s = in.readLine()) != null){
			sb.append(s + "\n");
		}*/
		
		s = in.readLine(); in.read();
		System.out.print(s);
		
		in.close();
		
		System.out.print("aaaaaaaa");
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		//System.out.print(read("io/BufferedInputFile.java"));
		read("io/BufferedInputFile.java");
	}
} /* (Execute to see output) */// :~
