package demo.file.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * 
 * @author LENOVO
 *
 */
public class FileChannelDemo {
	// 使用通道读文件
	public void readData(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			FileChannel fc = fileInputStream.getChannel();
			// 创建缓冲区
			ByteBuffer buf = ByteBuffer.allocate(16);
			while (fc.read(buf) != -1) {// 从通道读取数据到缓冲区
				// 反转缓冲区(limit设置为position,position设置为0,mark设置为-1)
				buf.flip();
				while (buf.hasRemaining()) {// 就是判断position和limit之间是否有元素
					// 按照字节的格式获取数据
					System.out.print((char) buf.get());
				}
				// 读完将缓冲区还原(position设置为0,limit设置为capacity,mark设置为-1)
				buf.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 使用通道写文件
	public void writeData(File file, String data) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			FileChannel fc = fos.getChannel();
			// 创建缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			// 将数据装入缓冲区
			buf.put(data.getBytes());
			// 反转缓冲区(limit设置为position,position设置为0,mark设置为-1)
			buf.flip();
			// 将缓冲区中的数据写入到通道
			fc.write(buf);
			// 写完将缓冲区还原(position设置为0,limit设置为capacity,mark设置为-1)
			buf.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		final FileChannelDemo test = new FileChannelDemo();
		String fileUrl = "src/main/resources/testFile/test1.txt";
		File file = new File(fileUrl);
		if (file.exists()) {
			System.out.println("文件存在");
		} else {
			System.out.println("文件不存在");
			return;
		}
		test.readData(file);
		//test.writeData(file, "123456789");
	}

}
