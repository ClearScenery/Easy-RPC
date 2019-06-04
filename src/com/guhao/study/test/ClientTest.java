package com.guhao.study.test;

import java.net.InetSocketAddress;

import com.guhao.study.client.Client;
import com.guhao.study.service.HelloService;

public class ClientTest {
	public static void main(String[] args) throws ClassNotFoundException {
		Client client = new Client();
		HelloService helloService = client.getRemoteProcedureCall(Class.forName("com.guhao.study.service.HelloService"), new InetSocketAddress("127.0.0.1", 9999));
		System.out.println(helloService.sayHello("guhao"));
	}
}
