package demo.socket.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base {
	protected final static Logger logger = LoggerFactory.getLogger(Base.class);
	protected static String ipAddress = "127.0.0.1";
	protected static int port = 8081;
	protected static String TIMEQUERY = "query time";
	protected final static int BUFFER_SIZE = 1024;
}
