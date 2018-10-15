package jdbc.connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接池
 * 数据源最高级别接口,定义了数据源的基本功能
 * 
 * @author mjorcen
 * @email mjorcen@gmail.com
 * @dateTime Oct 5, 2014 3:20:21 PM
 * @version 1
 */
public interface PooledDataSource {
    /**
     * 获取链接
     * 
     * @return
     * 
     * @author 
     * @email 
     * @dateTime 
     * @version 
     * @throws SQLException
     */
    Connection getConnection() throws Exception;

    /**
     * 销毁
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Oct 5, 2014 3:26:00 PM
     * @version 1
     */
    void destroy() throws Exception;

    /**
     * 释放
     * 
     * @param connection
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Oct 5, 2014 3:27:09 PM
     * @version 1
     */
    void release(Connection connection) throws Exception;

    /**
     * 数据源是否可用
     * 
     * @return
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Oct 5, 2014 3:28:15 PM
     * @version 1
     */
    boolean isAvailable();

}
