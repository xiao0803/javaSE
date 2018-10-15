package jdbc.connectionPool;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 
 * 配置信息
 * 
 * @author 
 * @email 
 * @dateTime 
 * @version 
 */
public class Configuration {
    private ResourceBundle resource;
    private Logger logger = Logger.getLogger(getClass());
    private String driverClassName = "com.mysql.jdbc.Driver";
    private String validationQuery = "SELECT 1";
    private String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    private String user = "root";
    private String password = "";
    private int initialPoolSize = 3;
    private int minPoolSize = 3;
    private int maxPoolSize = 10;
    private int maxStatements = 30;
    private int maxIdleTime = 25000;
    private int idleConnectionTestPeriod = 18000;
    private int connectionLonger = 3600000;

    public Configuration() {
        super();

    }

    public Configuration(String _properties) {
        super();
        init(_properties);
    }

    /**
     * 
     * @param _properties
     * 
     * @author mjorcen
     * @email mjorcen@gmail.com
     * @dateTime Oct 5, 2014 3:08:54 PM
     * @version 1
     */
    private void init(String _properties) {
        resource = ResourceBundle.getBundle(_properties);
        try {
            String tmp = "";
            setDriverClassName(resource.getString("driverClassName"));
            setValidationQuery(resource.getString("validationQuery"));
            setUrl(resource.getString("jdbc_url"));
            setUser(resource.getString("jdbc_username"));
            setPassword(resource.getString("jdbc_password"));

            tmp = resource.getString("initialPoolSize");
            if (tmp != null) {
                setInitialPoolSize(Integer.parseInt(tmp));
            }
            tmp = resource.getString("minPoolSize");
            if (tmp != null) {
                setMinPoolSize(Integer.parseInt(tmp));
            }
            tmp = resource.getString("maxPoolSize");
            if (tmp != null) {
                setMaxPoolSize(Integer.parseInt(tmp));
            }
            tmp = resource.getString("maxStatements");
            if (tmp != null) {
                setMaxStatements(Integer.parseInt(tmp));
            }
            tmp = resource.getString("maxIdleTime");
            if (tmp != null) {
                setMaxIdleTime(Integer.parseInt(tmp));
            }
            tmp = resource.getString("idleConnectionTestPeriod");
            if (tmp != null) {
                setIdleConnectionTestPeriod(Integer.parseInt(tmp));
            }
            tmp = resource.getString("connectionLonger");
            if (tmp != null) {
                setConnectionLonger(Integer.parseInt(tmp));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

    }

    public ResourceBundle getResource() {
        return resource;
    }

    public void setResource(ResourceBundle resource) {
        this.resource = resource;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getIdleConnectionTestPeriod() {
        return idleConnectionTestPeriod;
    }

    public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
        this.idleConnectionTestPeriod = idleConnectionTestPeriod;
    }

    public int getConnectionLonger() {
        return connectionLonger;
    }

    public void setConnectionLonger(int connectionLonger) {
        this.connectionLonger = connectionLonger;
    }

}