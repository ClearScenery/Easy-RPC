package com.guhao.study.server;

public interface Server {
	public void start() throws Exception ;
	
	public void stop();
	
	public void register(Class<?> serviceInter,Class<?> serviceImpl);
}
