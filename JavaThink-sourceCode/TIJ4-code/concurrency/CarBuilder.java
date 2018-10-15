package concurrency;
//: concurrency/CarBuilder.java
// A complex example of tasks working together.
import java.util.concurrent.*;
import java.util.*;

/*import com.example.Assembler;
import com.example.Car1;
import com.example.CarQueue;
import com.example.ChassisBuilder;
import com.example.DriveTrainRobot;
import com.example.EngineRobot;
import com.example.Reporter;
import com.example.Robot;
import com.example.RobotPool;
import com.example.WheelsRobot;*/

import static net.mindview.util.Print.*;

//封装Car类表示汽车
class Car1 {
	private final int id;// 汽车编号
	// 表示开始时汽车各部分都还没组装好
	private boolean engine = false, driveTrain = false, wheels = false;

	public Car1(int id) {
		this.id = id;
	}

	public Car1() {
		id = -1;
	}

	public synchronized int getId() {
		return id;
	}

	// 以下是组装汽车的步骤
	// 这里通过设定指定的标记为true，表示完成了相应的步骤
	public synchronized void addEngine() {
		engine = true;
	}

	public synchronized void addDriveTrain() {
		driveTrain = true;
	}

	public synchronized void addWheels() {
		wheels = true;
	}

	public synchronized String toString() {
		return "Car " + id + " [" + " engine: " + engine + " driveTrain: " + driveTrain + " wheels: " + wheels + " ]";
	}
}

// 封装的汽车队列，是一个阻塞队列
class CarQueue extends LinkedBlockingQueue<Car1> {
};

// 建造底盘的类
// 建好底盘以后就将放入相应的阻塞队列中，供后面的线程使用
class ChassisBuilder implements Runnable {
	private CarQueue carQueue; // 存放建好底盘的汽车
	private int counter = 0;

	public ChassisBuilder(CarQueue queue) {
		carQueue = queue;
	}

	// 线程的主要任务就是生成汽车底盘,放入阻塞队列中
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(400);
				Car1 c = new Car1(counter++);
				System.out.println("ChassisBuilder created " + c);
				carQueue.put(c);
			}
		} catch (InterruptedException ex) {
			System.out.println("ChassisBuilder interrpted");
		}
		System.out.println("ChassisBuilder off");
	}
}

// 组装类，通过调用机器人在建好的底盘上组装其它部分
class Assembler implements Runnable {
	// 分配记录装好底盘的Car和已经完成组装号的Car
	private CarQueue chassisQueue, finishedQueue;

	private Car1 car; // 正在组装的Car
	private CyclicBarrier barrier = new CyclicBarrier(4);
	private RobotPool robotPool;

	public Assembler(CarQueue cq, CarQueue fq, RobotPool rt) {
		chassisQueue = cq;
		finishedQueue = fq;
		robotPool = rt;
	}

	public Car1 getCar() {
		return car;
	}

	public CyclicBarrier getBarrier() {
		return barrier;
	}

	// 线程的主要任务就是负责调用机器人来组装Car
	// 注意这里使用了CyclicBarrier来一辆车完成装好以后才能继续组装下一辆
	public void run() {

		try {
			while (!Thread.interrupted()) {
				// 如果底盘还没有生成则会阻塞
				car = chassisQueue.take();
				// 下面会雇佣各个类型的robot去组装这辆汽车

				robotPool.hire(EngineRobot.class, this);
				// System.out.println("test");
				robotPool.hire(DriveTrainRobot.class, this);
				robotPool.hire(WheelsRobot.class, this);

				barrier.await(); // 如果上面的组装还没完成，则会阻塞在这里;这样可以保证一辆车组装完以后再组装下一辆车
				finishedQueue.put(car); // 将组装完成的车加入队列
			}
		} catch (Exception ex) {
			System.out.println("Assemble Interrupted");
		}
		System.out.println("Assemble off");
	}
}

// 将组装好的汽车输出进行检查
class Reporter implements Runnable {
	private CarQueue carQueue;

	public Reporter(CarQueue carQueue) {
		this.carQueue = carQueue;
	}

	// 线程的主要任务是将组装完成的汽车打印出来
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println(carQueue.take());
			}
		} catch (InterruptedException ex) {
			System.out.println("reporter interrupted");
		}
	}
}

// 负责组装工作的机器人类，是一个抽象类
// 下面会有各种机器人的具体实现
abstract class Robot implements Runnable {
	private RobotPool robotPool;

	public Robot(RobotPool pool) {
		robotPool = pool;
		robotPool.add(this); // 将自己加入管理池中去
		// robotPool.pool.add(this);
	}

	protected Assembler assembler; // 该机器人服务的组装线

	// 关联到指定的组装线
	public Robot assignAssembler(Assembler am) {
		assembler = am;
		return this;
	}

	private boolean engage = false; // 是否在干活

	// 让机器人干活
	public synchronized void engage() {
		engage = true;
		notifyAll();
	}

	// 由子类实现的抽象方法，每个子类的行为都不一样
	abstract protected void performService();

	public void run() {
		try {
			powerDown(); // 如果没有组装线雇佣这个机器人，则线程在此阻塞
			while (!Thread.interrupted()) {
				performService();// 干活
				assembler.getBarrier().await(); // 表示自己的活已经干完
				powerDown();
			}
		} catch (Exception ex) {
			System.out.println("Exception");
		}
	}

	private synchronized void powerDown() throws Exception {
		engage = false;
		assembler = null;// 解除和装配线的联系
		robotPool.release(this);
		while (engage == false) {// 没有活干时挂起
			wait();
		}
	}

	public String toString() {
		return getClass().getName();
	}
}

// 装配发动机的机器人
class EngineRobot extends Robot {
	public EngineRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing engine");
		assembler.getCar().addEngine();
	}
}

// 装配传动系统的机器人
class DriveTrainRobot extends Robot {
	public DriveTrainRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing driveTrain");
		assembler.getCar().addDriveTrain();
		;
	}
}

// 装配轮子的机器人
class WheelsRobot extends Robot {
	public WheelsRobot(RobotPool pool) {
		super(pool);
	}

	protected void performService() {
		System.out.println(this + " installing Wheels");
		assembler.getCar().addWheels();
	}
}

// 集中管理所有的机器人
class RobotPool {
	public HashSet<Robot> pool = new HashSet<>();

	public synchronized void add(Robot r) {
		pool.add(r);
		notifyAll();
	}

	public synchronized void hire(Class<? extends Robot> robotType, Assembler d) throws Exception {
		for (Robot r : pool) {// 找到合适品种的机器人，如果找不到则等待再递归寻找
			if (r.getClass().equals(robotType)) {
				pool.remove(r);
				r.assignAssembler(d);// 关联生产线
				r.engage();// 让机器人干活
				return;
			}
		}
		wait();// 当前没有多余的机器人则等待直到有空闲的再递归搜索
		hire(robotType, d);// 递归
	}

	public synchronized void release(Robot r) {
		add(r);
	}
}

public class CarBuilder {
	public static void main(String[] args) throws Exception {
		CarQueue chassisQueue = new CarQueue(), finishedQueue = new CarQueue();
		ExecutorService exec = Executors.newCachedThreadPool();

		// 依次启动各个机器人，生产线
		RobotPool robotPool = new RobotPool();
		exec.execute(new EngineRobot(robotPool));
		exec.execute(new DriveTrainRobot(robotPool));
		exec.execute(new WheelsRobot(robotPool));
		exec.execute(new Assembler(chassisQueue, finishedQueue, robotPool));
		exec.execute(new Reporter(finishedQueue));
		exec.execute(new ChassisBuilder(chassisQueue));
		TimeUnit.SECONDS.sleep(7);
		exec.shutdownNow();
	}
}
