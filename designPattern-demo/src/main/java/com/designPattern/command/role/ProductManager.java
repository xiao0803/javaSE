package com.designPattern.command.role;

import java.util.ArrayList;
import java.util.List;

import com.designPattern.command.task.Task;

//项目经理（调度者）
public class ProductManager {

	private List<Task> taskList;

	public ProductManager() {
		super();
		taskList = new ArrayList<Task>();
	}

	// 接受一个任务
	public void receive(Task task) {
		taskList.add(task);
	}

	// 分配给程序猿任务，督促程序猿完成
	public void assign() {
		for (int i = 0; i < taskList.size(); i++) {
			taskList.get(i).handle();
		}
	}

}
