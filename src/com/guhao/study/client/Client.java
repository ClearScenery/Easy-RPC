package com.guhao.study.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	@SuppressWarnings("unchecked")
	public <T> T getRemoteProcedureCall(Class<?> serviceInter,InetSocketAddress address) {
		return (T) Proxy.newProxyInstance(serviceInter.getClassLoader(), new Class<?>[] {serviceInter}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				@SuppressWarnings("resource")
				Socket socket = new Socket();
				socket.connect(address);
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				output.writeUTF(serviceInter.getName());
				output.writeUTF(method.getName());
				output.writeObject(method.getParameterTypes());
				output.writeObject(args);
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				return input.readObject();
			}
		});
	}
}
