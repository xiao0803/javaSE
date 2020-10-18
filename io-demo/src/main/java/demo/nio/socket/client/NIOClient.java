package demo.nio.socket.client;

import demo.base.Base;

public class NIOClient extends Base {
	public static void main(String[] args) {
		new Thread(new ReactorClientHandler(), "nio-client-reactor-001").start();
	}
}
