package jdbc.connectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * connection 的包装类, 因为mysql 一个连接连接8小时就会被mysql干掉;所以出此下策
 * 
 * @author 
 * @email 
 * @dateTime 
 * @version 
 */
public class WarpConnection {
    private Logger logger = Logger.getLogger(getClass());
    static private AtomicInteger atomicInteger = new AtomicInteger(0);
    private String name;
    private long connectionTime;
    private long lastWorkTime;
    private Connection connection;

    public long getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(long connectionTime) {
        this.connectionTime = connectionTime;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static WarpConnection warp(Connection connection) {
        WarpConnection warpConnection = new WarpConnection();
        warpConnection.setConnection(connection);
        warpConnection.setConnectionTime(System.currentTimeMillis());//设置连接开始时间
        warpConnection.setName("name" + atomicInteger.getAndAdd(1));
        return warpConnection;
    }
    
    /**
     * 检查是否连接超时；time=3600000，即1小时为限
     * 
     * @param
     * @author 
     * @email 
     * @dateTime 
     * @version 1
     */
    public boolean isTimeOut(long time) {
        boolean flag = System.currentTimeMillis() - this.connectionTime >= time;
        System.out.println("name is " + this.name + " ,connectionTime is "
                + connectionTime + ", flag is " + flag + " ,time is "+time);
        return flag;
    }

    public long getLastWorkTime() {
        return lastWorkTime;
    }

    public void setLastWorkTime(long lastWorkTime) {
        this.lastWorkTime = lastWorkTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((connection == null) ? 0 : connection.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WarpConnection other = (WarpConnection) obj;
        if (connection == null) {
            if (other.connection != null)
                return false;
        } else if (!connection.equals(other.connection))
            return false;
        return true;
    }

    /**
     * 查看链接是否有效
     * 
     * @param connectionLonger
     *            连接最大时间
     * @return
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Oct 5, 2014 5:21:07 PM
     * @version 1600000
     * @throws SQLException
     */
    public boolean veryfiConnection(int connectionLonger) {
        try {

            if (this.connection == null || this.connection.isClosed()
                    || isTimeOut(connectionLonger)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}