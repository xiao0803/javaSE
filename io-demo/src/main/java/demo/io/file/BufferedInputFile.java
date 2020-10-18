package demo.io.file;

import java.io.*;

/**
 * 从内存输入
 * @author LENOVO
 *
 */
public class BufferedInputFile {
	public static String read(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		
//		s = in.readLine(); 
//		in.read(); 
//		System.out.print(s);
		
		in.close();
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		String s = BufferedInputFile.read("F:\\myProject\\javaSE\\io-demo\\src\\main\\java\\com\\io\\demo\\BufferedInputFile.java");
		System.out.print(s);

	}
}
