package com.allen.class_02.rpc.v1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	
	
	public Server() {
		init();
	}
	
	
	public void init() {
		ServerSocket server = null;
		InputStream in = null;
		InputStreamReader ins = null;
		Socket socket  = null;
		
		try {
			server = new ServerSocket(8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				socket = server.accept();
				in = socket.getInputStream();
				ins =  new InputStreamReader(in);
				char[] bytes = new char[1024];
				while(ins.read(bytes, 0, bytes.length) != -1) {
					System.out.println(new String(bytes));
				}
			} catch (UnknownHostException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}finally {
				
				try {
					ins.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
		}
	}
	
	
	
	public static void main(String[] args) {
		
		Server s = new Server();
		
		
	}

}
