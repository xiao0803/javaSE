package demo.socket.aio.server;

import demo.socket.base.Base;

public class AIOTCPServer extends Base {
	public static void main(String[] args) {
		new Thread(new AsyncServerHandler(), "nio-server-reactor-001").start();
	}
}
