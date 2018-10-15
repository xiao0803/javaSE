package io;

//: io/DirList.java
// Display a directory listing using regular expressions.
// {Args: "D.*\.java"}
import java.util.regex.*;
import java.io.*;
import java.util.*;

public class DirList {
	public static void main(String[] args) {
		//File path = new File(".");
		
		File path = new File(".\\io");// “.\\io”表示该根目录（/TIJ4-code）下的io目录
		args = new String[] { "\\w+\\.xml" };//筛选条件
		
		String[] list;
		if (args.length == 0)
			list = path.list();//返回io目录中所有文件和文件夹名称
		else
			list = path.list(new DirFilter(args[0]));//返回io目录中的满足筛选条件的文件和文件夹名称
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list)
			System.out.println(dirItem);
	}
}

class DirFilter implements FilenameFilter {
	private Pattern pattern;

	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}
} /*
 * Output: DirectoryDemo.java DirList.java DirList2.java DirList3.java
 */// :~
