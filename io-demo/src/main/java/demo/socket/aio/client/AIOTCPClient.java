package demo.socket.aio.client;

import demo.socket.base.Base;

public class AIOTCPClient extends Base {
	public static void main(String[] args) {
		new Thread(new AsyncClientHandler(), "nio-client-reactor-001").start();
	}
}
