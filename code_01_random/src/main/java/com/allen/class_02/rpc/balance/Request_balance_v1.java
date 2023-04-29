package com.allen.class_02.rpc.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 1、随机负载
 * 2、轮询（顺序）
 * 3、加权轮询
 * 4、加权随机
 * 5、hash ip
 * 6、最少连接
 * @author allen
 *
 */
public class Request_balance_v1 {
	
	
	
	
	/**
	 * 服务器
	 * @author allen
	 *
	 */
	private static class Server{
		String ip;
		int weight;
		
		public Server(String ip, int weight) {
			super();
			this.ip = ip;
			this.weight = weight;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
		@Override
		public String toString() {
			return "Server [ip=" + ip + ", weight=" + weight + "]";
		}
		
		
	}
	
	
	
	
	/**
	 * 负载均衡类
	 * @author allen
	 *
	 */
	static class Balance{
		
		//目前的后台集群服务器
		List<Server> list = new ArrayList<>();
		//最少链接数
		Map<Server, Integer> leastConMap = new HashMap<>();
		//随机函数
		private  Random r = new Random();
		
		//当前服务器
		private Integer count = 0;
		
		/**
		 * 添加机器
		 * @param server
		 */
		public void add(Server server) {
			list.add(server);
			leastConMap.put(server, 0);
		}
		
		
		/**
		 * 清除所有机器
		 */
		public void clear() {
			count = 0;
			list.clear();
		}
		
		
		/**
		 * 链接
		 * @param server
		 */
		public synchronized Server connect() {
			
		  
		  Set<Server> ipS =  leastConMap.keySet();	
	
		  Server leastCountServer = null;
		  int count = 0;
		  for (Server ser : ipS) {
			if(leastCountServer == null) 
				leastCountServer = ser;
			else if(leastConMap.get(ser) < count) 
				leastCountServer = ser;
			count = leastConMap.get(leastCountServer);
		  }
			
		  leastConMap.put(leastCountServer, ++count);
		  
		  return leastCountServer;
		  
		}
		
		
		/*
		 * 随机
		 */
		public Server random() {
			return list.get(r.nextInt(list.size()));
		}
		
		
		/*
		 * 随机
		 */
		public Server randomWeight() {
			List<Server> tmpList = new ArrayList<>();
			
			for (Server server : list) {
				for(int i=0; i< server.weight; i++){
					tmpList.add(server);
				}
			}
			return tmpList.get(r.nextInt(tmpList.size()));
		}
		
		
		/*
		 * 顺序
		 */
		public Server sequence() {
			synchronized (count) {
				return list.get(count++);
			}
		}
		
		/*
		 * 顺序
		 */
		public Server sequenceWeight() {
			
			List<Server> tmpList = new ArrayList<>();
			
			for (Server server : list) {
				for(int i=0; i< server.weight; i++){
					tmpList.add(server);
				}
			}
			
			synchronized (count) {
				return tmpList.get(count++);
			}
		}
		
		
		/**
		 * hash ip
		 */
		
		public Server hashIp(String ip) {
			int pos = Math.abs(ip.hashCode())%list.size();
			return list.get(pos);
		}
		
		
		
		
	}
	
	static Balance b,b2 = null;
	
	public static void main(String[] args) {
		
		b = new Balance();
		System.out.println("===========初始化==========");
		for(int i=0; i<10; i++){
			b.add(new Server("192.168.1.10" + i, 1)); 
		}
		
		System.out.println("==========随机=========");
		Server tmp  = null;
		for(int i=0; i<5; i++){
		   	tmp = b.random();
			System.out.println(tmp);
		}
		
		System.out.println("==========顺序=========");
		for(int i=0; i<5; i++){
		   	tmp = b.sequence();
		   	System.out.println(tmp);
		}
		
		
		b.clear();
		for(int i=0; i<10; i++){
			b.add(new Server("192.168.1.10" + i, 1)); 
		}
		
		System.out.println("==========随机权重=========");
		for(int i=0; i<5; i++){
		   	tmp = b.randomWeight();
		   	System.out.println(tmp);
		}
		
		System.out.println("==========顺序权重=========");
		for(int i=0; i<10; i++){
		   	tmp = b.sequenceWeight();
		   	System.out.println(tmp);
		}
		
		
		System.out.println("==========hash ip=========");
		for(int i=0; i<5; i++){
		   	tmp = b.hashIp("192.168.1.102");
		   	System.out.println(tmp);
		}
		
		
		Balance b2 = new Balance();
		for(int i=0; i<5; i++){
			b2.add(new Server("192.168.1.10" + i, b.r.nextInt(5) + 1)); 
		}
		
		
		b.clear();
		System.out.println(b.list.size());
		
		b = b2;
		
		System.out.println("==========least connect=========");
//		for(int i=0; i<12; i++){
//			new Thread(()->{
//				String name = Thread.currentThread().getName();
//				Server stmp = b.connect();
//				System.out.println(name + ":" + stmp);
//				try {
//					TimeUnit.MILLISECONDS.sleep(1000);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			},"t" + (i+1)).start();
//			
//		}
//		
		
		
		
		new Thread(()->{
			String name = Thread.currentThread().getName();
			for(int i=0; i<12; i++){
				Server stmp = b.connect();
				System.out.println(name + ":" + stmp);
				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		},"t-one").start();
	
		
	
		
		
		
	}

}
