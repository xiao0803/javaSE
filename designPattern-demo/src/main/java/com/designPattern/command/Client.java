package com.designPattern.command;

import com.designPattern.command.role.ProductManager;
import com.designPattern.command.role.Programmer;
import com.designPattern.command.role.Salesman;

public class Client {

	public static void main(String[] args) {
		Programmer xiaozuo = new Programmer("小左");
		ProductManager productManager = new ProductManager();

		Salesman salesmanA = new Salesman("A");
		Salesman salesmanB = new Salesman("B");
		Salesman salesmanC = new Salesman("C");
		Salesman salesmanD = new Salesman("D");

		salesmanA.putDemand(productManager, xiaozuo);
		salesmanB.putBug(productManager, xiaozuo);
		salesmanC.putProblem(productManager, xiaozuo);
		salesmanD.putDemand(productManager, xiaozuo);

		System.out.println("产品经理分配任务");
		productManager.assign();
	}

}
