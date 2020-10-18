package demo.io.file;

import java.io.*;

/**
 * 格式化的内存输入
 * @author LENOVO
 *
 */
public class FormattedMemoryInput {
	public static void main(String[] args) throws IOException {
		String fileUrl = "F:\\myProject\\javaSE\\io-demo\\src\\main\\java\\com\\io\\demo\\FormattedMemoryInput.java";
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(BufferedInputFile.read(fileUrl).getBytes());
			DataInputStream in = new DataInputStream(byteArrayInputStream);
			while (true)
				System.out.print((char) in.readByte());
		} catch (EOFException e) {
			System.err.println("End of stream");
		}
	}
}
