package jdbc.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class BaseTest {

    private ResourceBundle resource;
    private Logger logger = Logger.getLogger(getClass());
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    private String validationQuery = "SELECT 1";
    private String url = "jdbc:mysql://mysql.ad-test.jpushoa.com:20000/ssp_test?serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
    private String user = "ssp_test_user";
    private String password = "HuT12smswHuT1";
    private int initialPoolSize = 3;
    private int minPoolSize = 3;
    private int maxPoolSize = 10;
    private int maxStatements = 30;
    private int maxIdleTime = 25000;
    private int idleConnectionTestPeriod = 18000;
    private int connectionLonger = 3600000;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BaseTest baseTest = new BaseTest();
		Class.forName(baseTest.driverClassName);
		Connection connection = DriverManager.getConnection(baseTest.url, baseTest.user, baseTest.password);
		Statement st = connection.createStatement();
		ResultSet resultSet = st.executeQuery("SELECT * FROM t_mock_dsp_material_meta a WHERE a.adslot_template_id = 8");
		System.out.println(resultSet.getString(0));
		
	}

}
