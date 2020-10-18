package demo.aio.socket.client;

import demo.base.Base;

public class AIOClient extends Base {
	public static void main(String[] args) {
		new Thread(new AsyncClientHandler(), "nio-client-reactor-001").start();
	}
}
