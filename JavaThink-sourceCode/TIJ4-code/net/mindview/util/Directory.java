//: net/mindview/util/Directory.java
// Produce a sequence of File objects that match a
// regular expression in either a local directory,
// or by walking a directory tree.
package net.mindview.util;

import java.util.regex.*;
import java.io.*;
import java.util.*;

public final class Directory {
	// 列出目录下符合筛选条件的文件名
	public static File[] local(File dir, final String regex) {
		return dir.listFiles(new FilenameFilter() {
			private Pattern pattern = Pattern.compile(regex);

			public boolean accept(File dir, String name) {
				return pattern.matcher(new File(name).getName()).matches();
			}
		});
	}

	// 重载方法；作用同上
	public static File[] local(String path, final String regex) {
		return local(new File(path), regex);
	}

	// 代表文件树信息的静态内部类
	public static class TreeInfo implements Iterable<File> {
		public List<File> files = new ArrayList<File>();
		public List<File> dirs = new ArrayList<File>();

		// 默认的迭代器方法，跌倒文件树元素对象
		public Iterator<File> iterator() {
			return files.iterator();
		}

		void addAll(TreeInfo other) {
			files.addAll(other.files);
			dirs.addAll(other.dirs);
		}

		public String toString() {
			return "dirs: " + PPrint.pformat(dirs) + "\n\nfiles: " + PPrint.pformat(files);
		}
	}

	// 从指定的文件/目录开始遍历符合筛选条件的文件夹和文件
	public static TreeInfo walk(String start, String regex) {
		return recurseDirs(new File(start), regex);
	}

	// 重载方法；作用同上
	public static TreeInfo walk(File start, String regex) {
		return recurseDirs(start, regex);
	}

	// 默认查找指定文件/目录下所有节点的任何文件夹和文件
	public static TreeInfo walk(File start) { // Everything
		return recurseDirs(start, ".*");
	}

	// 重载方法；作用同上
	public static TreeInfo walk(String start) {
		return recurseDirs(new File(start), ".*");
	}

	// 从指定的文件/目录开始遍历，查找符合条件的文件名
	static TreeInfo recurseDirs(File startDir, String regex) {
		TreeInfo result = new TreeInfo();
		for (File item : startDir.listFiles()) {
			// 如果遍历的文件是目录
			if (item.isDirectory()) {
				result.dirs.add(item);
				// 迭代子目录
				result.addAll(recurseDirs(item, regex));
			}
			// 如果遍历的的文件是普通文件
			else {
				if (item.getName().matches(regex)) {
					result.files.add(item);
				}
			}
		}
		return result;
	}

	//测试
	public static void main(String[] args) {
		
		args = new String[] { "\\w+\\.xml" };// 筛选条件
		if (args.length == 0){
			// 测试walk方法
			System.out.println(walk(".\\io"));// “.\\io”表示该根目录（/TIJ4-code）下的io目录
		}else{
			for (String arg : args){
				//测试local方法
				File[] files = local(".\\io", arg);
				for (File file : files) {
					System.out.println(file);
				}
			}
		}
	}
} // /:~
