package demo.io.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author LENOVO
 *
 */
public class BufferedInputStreamDemo {
	public static String read(String filename) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filename);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		
		byte[] buffer = new byte[1024];
		StringBuilder sb = new StringBuilder();
		
		int bytesRead = 0;
		//in.read();
		while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {//从文件中按字节读取内容，到文件尾部时read方法将返回-1
			String chunk = new String(buffer, 0, bytesRead);
			sb.append(chunk + "\n");
		}
		bufferedInputStream.close();
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String s = BufferedInputStreamDemo.read("F:\\myProject\\javaSE\\io-demo\\src\\main\\java\\com\\io\\demo\\BufferedInputStreamDemo.java");
		System.out.print(s);

	}
}
