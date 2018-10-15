package jdbc.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单的线程池实现
 * 
 * @author 
 * @email 
 * @dateTime 
 * @version 
 */
public class AbstractPooledDataSource implements PooledDataSource {

    protected ConcurrentLinkedQueue<WarpConnection> idleQueue;   //空闲队列（没有被使用的队列）
    protected ConcurrentLinkedQueue<WarpConnection> busyQueue;   //繁忙队列（正在被使用的队列）
    protected ThreadLocal<Connection> threadLocal;
    protected AtomicInteger totalSize;
    protected AtomicInteger currentSize;
    protected boolean available;
    protected Configuration configuration;
    final Lock lock = new ReentrantLock();// 锁
    // final Condition notFull = lock.newCondition(); // 实例化两个condition
    final Condition notEmpty = lock.newCondition();

    public AbstractPooledDataSource(Configuration configuration)
            throws Exception {
        super();
        this.configuration = configuration;
        idleQueue = new ConcurrentLinkedQueue<WarpConnection>();
        busyQueue = new ConcurrentLinkedQueue<WarpConnection>();
        threadLocal = new ThreadLocal<Connection>();
        totalSize = new AtomicInteger(0);
        currentSize = new AtomicInteger(0);
        init();
    }

    /**
     * 初始化
     * 
     * @author 
     * @email 
     * @dateTime 
     * @version 
     * @throws ClassNotFoundException
     */
    private void init() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        for (int i = 0; i < this.configuration.getInitialPoolSize(); i++) {
            idleQueue.add(WarpConnection.warp(openConnection()));
        }
        this.totalSize.set(this.configuration.getInitialPoolSize());
        available = true;
    }
    
    /**
     * 与DB建立连接
     * 
     * @author 
     * @email 
     * @dateTime 
     * @version 
     * @throws 
     */
    protected Connection openConnection() throws SQLException {
        return DriverManager.getConnection(configuration.getUrl(),
                configuration.getUser(), configuration.getPassword());
    }
    
    /**
     * 获取连接对象；idleQueue里面移除一个连接对象，busyQueue里面添加一个连接对象；
     * catch语句块里面的作用：idleQueue里面的空闲“包装连接对象”用完了，向idleQueue添加“包装连接对象”，直到个数等于设置的最大值为止
     * @author 
     * @email 
     * @dateTime 
     * @version 1
     * @param Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null) {
            return connection;
        }
        try {
            lock.lock();
            WarpConnection warpConnection = null;
            try {
                warpConnection = this.idleQueue.remove();
            } catch (NoSuchElementException e) {//idleQueue里面的空闲“包装连接对象”用完了，向idleQueue添加“包装连接对象”
                warpConnection = getWarpConnection();
            }
            veryfiConnection(warpConnection);
            warpConnection.setLastWorkTime(System.currentTimeMillis());
            this.busyQueue.add(warpConnection);
            threadLocal.set(warpConnection.getConnection());
            return warpConnection.getConnection();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 检查连接是否有效，如果无效就创建一个连接对象
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Oct 5, 2014 5:17:06 PM
     * @version 1
     * @param warpConnection
     * @throws SQLException
     */
    private void veryfiConnection(WarpConnection warpConnection)
            throws SQLException {
        if (warpConnection.veryfiConnection(this.configuration
                .getConnectionLonger())) {
            warpConnection.setConnection(openConnection());
            warpConnection.setConnectionTime(System.currentTimeMillis());
        }
    }

    /**idleQueue里面没有“包装连接对象”的时候获取一个“包装连接对象”；即this.idleQueue.remove()报错的时候调用
     * 
     * @return WarpConnection
     * 
     * @author 
     * @email
     * @dateTime 
     * @version 1
     * @throws SQLException
     */
    private WarpConnection getWarpConnection() throws SQLException {
        WarpConnection warpConnection = null;

        if (this.totalSize.get() < configuration.getMaxPoolSize()) {
            warpConnection = WarpConnection.warp(openConnection());
            this.totalSize.addAndGet(1);//idleQueue里面连接对象的总数量+1
            return warpConnection;
        }
        while (true) {
            try {
                warpConnection = this.idleQueue.remove();
                return warpConnection;
            } catch (NoSuchElementException e) {
                try {
                    this.notEmpty.wait();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    /**清空idleQueue和busyQueue，关闭idleQueue里面所有的连接对象
     * 
     * @author 
     * @email 
     * @dateTime 
     * @version 1
     */
    public void destroy() {
        this.available = false;
        ConcurrentLinkedQueue<WarpConnection> _idleQueue = this.idleQueue;
        ConcurrentLinkedQueue<WarpConnection> _busyQueue = this.busyQueue;
        this.idleQueue = null;
        this.busyQueue = null;
        this.threadLocal = null;
        for (WarpConnection connection : _idleQueue) {
            closeQuiet(connection.getConnection());
        }
    }

    /**释放连接对象；即对象用完后就移除busyQueue，然后放入idleQueue
     * 
     * @author 
     * @email 
     * @dateTime 
     * @version 1
     */
    public void release(Connection connection) throws Exception {
        try {
            lock.lock();
            if (this.available) {
                WarpConnection warpConnection = null;
                for (WarpConnection element : this.busyQueue) {
                    if (element.getConnection().equals(connection)) {
                        warpConnection = element;
                        break;
                    }
                }
                this.busyQueue.remove(warpConnection);
                this.idleQueue.add(warpConnection);
                // System.out.println("busyQueue = " + busyQueue.size());
                // System.out.println("idleQueue = " + idleQueue.size());
                threadLocal.set(null);
                notEmpty.signal();// 一旦插入就唤醒取数据线程
            } else {
                closeQuiet(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**关闭DB连接对象
     * 
     * @author 
     * @email 
     * @dateTime 
     * @version 1
     */
    private void closeQuiet(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean isAvailable() {
        return available;
    }

}
