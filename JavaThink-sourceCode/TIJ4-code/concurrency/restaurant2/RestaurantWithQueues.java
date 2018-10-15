//: concurrency/restaurant2/RestaurantWithQueues.java
// {Args: 5}
package concurrency.restaurant2;
import enumerated.menu.*;

import java.util.concurrent.*;
import java.util.*;

import com.example.Chef;
import com.example.Course;
import com.example.Customer;
import com.example.Order;
import com.example.Plate;
import com.example.Restaurant;
import com.example.WaitPerson;

import static net.mindview.util.Print.*;

class Course{
    private static Random rand = new Random();
    public static String[]  food={"food1","food2","food3","food4"};
    public static String randomSelection(){
        return food[rand.nextInt(food.length)];
    }
}

//封装的订单类
class Order{
    private static int counter=0;
    private final int id = counter++; //订单唯一的编号
    private final Customer customer; //订单对应的顾客
    private final WaitPerson waitPerson; //负责该订单的服务员
    private final String food; //订单对应的食物

    public Order(Customer cust,WaitPerson wait,String food){
        this.customer = cust;
        this.waitPerson = wait;
        this.food = food;
    }
    //返回订单中的食物
    public String item(){
        return food;
    }

    public Customer getCustomer(){
        return customer;
    }

    public WaitPerson getWaitPerson(){
        return waitPerson;
    }

    public String toString(){
        return "Order: "+id+"item: "+food+" for: "+customer+" served by: "+waitPerson;
    }
}

//装好食物的碟子类
class Plate{
    private final Order order; //该碟子对应的订单
    private  final String food; //该碟子盛放的食物
    public Plate(Order order , String food){
        this.order = order;
        this.food = food;
    }

    public Order getOrder(){
        return order;
    }
    public String getFood(){
        return food;
    }

    public String toString(){
        return food;
    }
}

//顾客类
class Customer implements Runnable{
    private static int counter = 0; 
    private final int id = counter++; //顾客id
    private final WaitPerson waitPerson ;//服务该顾客的侍者

    //表示顾客面前的盘子，在我们的仿真中顾客只会消费一种食物，所以我们使用了
    //容量为１的阻塞队列SynchronousQueue来表示其前面的盘子，这个队列每个put()操作
    //后面都必须跟一个take()操作，否则就会阻塞。
    private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<Plate>();

    public Customer(WaitPerson wait){
        this.waitPerson = wait;
    }
    //将制作完成的食物提交给顾客，如果前面已经put()过并且
    //用户还没有take()则会阻塞
    public void deliver(Plate p) throws InterruptedException{
        placeSetting.put(p);
    }

    public void run(){
        for(String food: Course.food){

            //每次用户都会从菜单中随机选择一种食物
            food =Course.randomSelection();
            try{
                //waitPerson提交用户的订单
                waitPerson.placeOrder(this,food);

                //表示用户吃掉食物，如果食物还没做好，则take()操作会阻塞
                System.out.println(this+" eating "+placeSetting.take());
            }catch(InterruptedException ex){
                System.out.println("Interrupted");
                break;
            }
        }
        System.out.println(this+"finished meal,leaving");
    }

    public String toString(){
        return "Customer "+id+" ";
    }
}

//封装的侍者类
class WaitPerson implements Runnable{
    private static int counter = 0;
    private final int id = counter++; //侍者编号
    private final Restaurant restaurant;//侍者所属的饭店

    //无界的阻塞队列，用来存放厨师已经完成的食物
    //侍者需要将这些食物送到对应的顾客手上
    LinkedBlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<Plate>();

    public WaitPerson(Restaurant rest){
        this.restaurant = rest;
    }

    //当用户点了食物以后，侍者提交订单
    public void placeOrder(Customer cust, String food){
        try{
            //向餐馆的订单队列中提交一个新订单
            restaurant.orders.put(new Order(cust,this,food));
        }catch(InterruptedException ex){
            System.out.println("Intrrupted");
        }
    }

    //侍者线程的主要作用是不断的从filledOrders中取出已完成的食物
    //提交给对应的顾客
    public void run(){
        try{
            while(!Thread.interrupted()){
                //如果队列为空，则会阻塞
                Plate plate = filledOrders.take();
                System.out.println(this+"received "+plate+" delivering to "+plate.getOrder().getCustomer());
                //将提取的plate提交给对应的顾客
                plate.getOrder().getCustomer().deliver(plate);
            }
        }catch(InterruptedException ex){
            System.out.println(this +"Interrupted");
        }
    }
    public String toString(){
        return "waitPerson "+id+" ";
    }
}

//厨师类
class Chef implements Runnable{
    private static int counter = 0;
    private final int id = counter++;//厨师编号
    private final Restaurant restaurant ;//厨师对应的餐馆
    private  Random rand = new Random(47);
    public Chef(Restaurant rest){
        restaurant = rest;
    }

    //厨师线程的主要任务是从饭店的订单队列提取订单，然后完成其中的食物
    //再将完成以后的plate提交给对应的侍者的filledOrders队列
    public void run(){
        try{
            while(!Thread.interrupted()){
                //从订单队列中取出订单，如果没有订单则会阻塞
                Order order = restaurant.orders.take(); 
                String food = order.item();//取得该订单所需的食物
                //模拟准备这种食物所需的时间
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order,food);
                //将完成的plate交给对应的waitPerson
                order.getWaitPerson().filledOrders.put(plate);
            }
        }catch(InterruptedException ex){
            System.out.println(this+"Interrupted");
        }
        System.out.println(this +"off duty");
    }
    public String toString(){
        return "Chef "+id+" ";
    }
}

//饭店类
class Restaurant implements Runnable{
    //饭店的侍者队列
    private ArrayList<WaitPerson> waitPersons = new ArrayList<WaitPerson>();
    //饭店的厨师队列
    private ArrayList<Chef> chefs = new ArrayList<Chef>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private static Random rand = new Random(47);
    //饭店的订单队列
     BlockingQueue<Order> orders = new LinkedBlockingQueue<Order>();

    public Restaurant(ExecutorService exe,int nWaitPerson,int nChef){
        exec = exe;
        //预先为饭店分配好侍者和厨师
        for(int i=0;i<nWaitPerson;i++){
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }
        for(int i=0;i<nChef;i++){
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    //饭店任务主要是隔一段时间就产生一个顾客,并为这个顾客分配一个服务的侍者
   public void run(){
       try{
           while(!Thread.interrupted()){
               WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
               Customer c = new Customer(wp);
               exec.execute(c);
               TimeUnit.MILLISECONDS.sleep(100);
           }
       }catch(InterruptedException ex){
           System.out.println(this+"Interrupted");
       }
       System.out.println("Restaurant closing");
   }
}

public class RestaurantWithQueues {

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();

        //指定一个五个侍者，２个厨师的饭店
        Restaurant restaurant = new Restaurant(exec,5,2);
        exec.execute(restaurant);
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        exec.shutdownNow();
    }
}

/* Output: (Sample)
WaitPerson 0 received SPRING_ROLLS delivering to Customer 1
Customer 1 eating SPRING_ROLLS
WaitPerson 3 received SPRING_ROLLS delivering to Customer 0
Customer 0 eating SPRING_ROLLS
WaitPerson 0 received BURRITO delivering to Customer 1
Customer 1 eating BURRITO
WaitPerson 3 received SPRING_ROLLS delivering to Customer 2
Customer 2 eating SPRING_ROLLS
WaitPerson 1 received SOUP delivering to Customer 3
Customer 3 eating SOUP
WaitPerson 3 received VINDALOO delivering to Customer 0
Customer 0 eating VINDALOO
WaitPerson 0 received FRUIT delivering to Customer 1
...
*///:~
