package com.guhao.study.service.impl;

import com.guhao.study.service.HelloService;

public class HelloServiceImpl implements HelloService{

	@Override
	public String sayHello(String name) {
		return "hello,"+name;
	}

}
