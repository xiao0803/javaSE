package demo.aio.socket.server;

import demo.base.Base;

public class AIOServer extends Base {
	public static void main(String[] args) {
		new Thread(new AsyncServerHandler(), "nio-server-reactor-001").start();
	}
}
