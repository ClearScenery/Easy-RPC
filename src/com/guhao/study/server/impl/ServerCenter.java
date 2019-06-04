package com.guhao.study.server.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.guhao.study.server.Server;

public class ServerCenter implements Server {

	private Map<String, Class<?>> serverMap = new HashMap<>();
	private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	private int port;
	@SuppressWarnings("unused")
	private static boolean isRunning = false;

	public ServerCenter(int port) {
		this.port = port;
	}

	@Override
	public void start() {
		System.out.println("server start...");
		isRunning = true;
		ServerSocket server = null;
		try {
			server = new ServerSocket();
			server.bind(new InetSocketAddress(port));
			while (true) {
				Socket socket = server.accept();
				executor.execute(new ServerTask(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		executor.shutdown();
		isRunning = false;
	}

	@Override
	public void register(Class<?> serviceInter, Class<?> serviceImpl) {
		serverMap.put(serviceInter.getName(), serviceImpl);
	}

	public class ServerTask implements Runnable {
		private Socket socket;

		public ServerTask() {
		}

		public ServerTask(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			ObjectInputStream input = null;
			ObjectOutputStream output = null;
			try {
				System.out.println("accept a socket...");
				input = new ObjectInputStream(socket.getInputStream());
				String seviecName = input.readUTF();
				String methodName = input.readUTF();
				Class<?>[] argsTypes = (Class<?>[]) input.readObject();
				Object[] args = (Object[]) input.readObject();
				Class<?> clazz = serverMap.get(seviecName);
				Method method = clazz.getDeclaredMethod(methodName, argsTypes);
				Object result = method.invoke(clazz.newInstance(), args);

				output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					if (input != null) {
						input.close();
					}
					
					if (output != null) {
						output.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
