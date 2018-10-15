package jdbc.connectionPool;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 
 * @author 
 * @email 
 * @dateTime 
 * @version 
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration("product_db");
        final AbstractPooledDataSource dataSource = new AbstractPooledDataSource(
                conf);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable r = new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        Connection connection = dataSource.getConnection();
                        System.out.println(Thread.currentThread().getName()
                                + " : " + connection);
                        Thread.sleep(3000);
                        dataSource.release(connection);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 10; i++) {

            executor.execute(r);
        }
        // Connection connection = dataSource.getConnection();
        // connection = dataSource.getConnection();
        // System.out.println(connection);
        // dataSource.release(connection);
    }
}
