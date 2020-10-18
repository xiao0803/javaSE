package demo.nio.socket.server;

import demo.base.Base;

public class NIOServer extends Base {
	public static void main(String[] args) {
		new Thread(new ReactorServerHandler(), "nio-server-reactor-001").start();
	}
}
