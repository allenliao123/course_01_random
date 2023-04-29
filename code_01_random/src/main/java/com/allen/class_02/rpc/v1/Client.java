package com.allen.class_02.rpc.v1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client {
	
	public Client() {
		init();
	}
	
	/**
	 * 客户端初始化
	 */
	public void init() {
		Socket client = null;
		OutputStream os = null;
		OutputStreamWriter ow  = null;
		try {
			client = new Socket("localhost", 8080);
			os = client.getOutputStream();
			ow = new OutputStreamWriter(os);
			while(true) {
				ow.write("你好!");
				ow.flush();
				System.out.println("客户端说：你好");
				TimeUnit.MILLISECONDS.sleep(1000);
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			try {
				ow.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		Client client = new Client();
		
		
		
	}

}
