package demo.socket.nio.server;

import demo.socket.base.Base;

public class NIOTCPServer extends Base {
	public static void main(String[] args) {
		new Thread(new ReactorServerHandler(), "nio-server-reactor-001").start();
	}
}
