package io;

//: io/LargeMappedFiles.java
// Creating a very large file using mapping.
// {RunByHand}
import java.nio.*;
import java.nio.channels.*;
import java.io.*;
import static net.mindview.util.Print.*;

public class LargeMappedFiles {
	// static int length = 0x8FFFFFF; // 128 MB
	static int length = 1024 * 1024 * 1; // 2 MB
	public static void main(String[] args) throws Exception {
		MappedByteBuffer out = new RandomAccessFile("ioTest/test2.txt", "rw")
		.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
		/*
		 * test.txt从头开始写入y字节，如果test.txt大于2M，那么前2M文本区都会被y字节覆盖；
		 * 如果test.txt小于2M，那么文件将被扩大，2M文本区都会被y字节填满
		*/
		for (int i = 0; i < length; i++) {
			out.put((byte) 'x');
		}
		print("Finished writing");
		//打印出文件最后的10个字节
		for (int i = length-10; i < length; i++) {
			printnb((char) out.get(i));
		}
	}
} // /:~
