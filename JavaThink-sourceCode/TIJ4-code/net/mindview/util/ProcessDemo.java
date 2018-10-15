package net.mindview.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * 
 * @author xiao
 * @version
 */
public class ProcessDemo {
	public static void main(String[] args) {
		
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		
		// 执行CMD命令，能够运行本地应用程序QQ
		/*String path = "D:\\腾讯QQ\\Bin\\QQScLauncher.exe";
		try {
			p = rt.exec(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// 调用浏览器，可以打开指定的网站
		//String path = "C:\\Users\\jrs521wlh\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";
		//String chrome = "www.google.com";
		//String[] cmd = { path, chrome };
		/*String path = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
		String firefox = "www.baidu.com";//不起作用
		String[] cmd = { path, firefox };
		try {
			p = rt.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//查询系统里面的进程
		printProcess();
		System.out.println("开始查找进程");
		if (findProcess("QQ.exe")) {
			System.out.println("查找进程存在");

		} else {
			System.out.println("查找进程不存在");

		}

	}

	// 打印所有的进程信息
	public static void printProcess() {
		BufferedReader buff = null;

		Process p = null;

		try {
			// 打印当前系统所有进程的信息
			p = Runtime.getRuntime().exec("tasklist");
			// 用流读出来
			buff = new BufferedReader(new InputStreamReader(p.getInputStream()));

			System.out.println("打印系统进程信息");

			String temp = null;
			// 遍历
			while ((temp = buff.readLine()) != null) {
				System.out.println(buff.readLine());

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 查看进程是否运行
	 * 
	 * @Title: findProcess
	 * @data:2013-4-17上午11:09:26
	 * @author:jrs521wlh
	 *
	 * @return
	 */
	public static boolean findProcess(String processName) {

		BufferedReader buff = null;

		try {
			// 下面这句是列出含有processName的所有进程图形名字
			Process p = Runtime.getRuntime().exec("tasklist");
			buff = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String temp = null;
			while ((temp = buff.readLine()) != null) {
				System.out.println(temp);
				if (temp.contains(processName)) {
					return true;

				}

			}
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}

