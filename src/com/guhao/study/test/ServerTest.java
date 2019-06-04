package com.guhao.study.test;

import com.guhao.study.server.Server;
import com.guhao.study.server.impl.ServerCenter;
import com.guhao.study.service.HelloService;
import com.guhao.study.service.impl.HelloServiceImpl;

public class ServerTest {
	public static void main(String[] args) {
		Server serverCenter = new ServerCenter(9999);
		serverCenter.register(HelloService.class, HelloServiceImpl.class);
		try {
			serverCenter.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
