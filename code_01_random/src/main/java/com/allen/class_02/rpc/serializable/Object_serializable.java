package com.allen.class_02.rpc.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class Object_serializable {
	
	
	static class  Person implements Serializable{
		
		
		
		String name;
		int age;
		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}
		
		
	}
	
	
	/**
	 * JDK 自己的序列化
	 */
	public static void serializable_v1() {
		
		try {
			Person p1 = null;
			Person  p2 = null;
			long s = System.currentTimeMillis();
			for(int i=0; i<1000; i++){
				p1 = new Person("王麻子" + i, 20);
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bout);
				out.writeObject(p1);
				
				byte[] bytes = bout.toByteArray();
				ByteArrayInputStream ban = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(ban);
				 p2	=  (Person)  ois.readObject();
				
//				System.out.println(p2);
			}
			
			long e = System.currentTimeMillis();
		
			System.out.println("time:" + (e-s));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Hessian 自己的序列化
	 */
	public static void serializable_v2() {
		
		try {
			Person p1 = null;
			Person  p2 = null;
			long s = System.currentTimeMillis();
			for(int i=0; i<1000; i++){
				p1 = new Person("王麻子" + i, 20);
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				HessianOutput out = new HessianOutput(bout);
				out.writeObject(p1);
				
				byte[] bytes = bout.toByteArray();
				ByteArrayInputStream ban = new ByteArrayInputStream(bytes);
				HessianInput ois = new HessianInput(ban);
				 p2	=  (Person)  ois.readObject();
				
//				System.out.println(p2);
			}
			
			long e = System.currentTimeMillis();
		
			System.out.println("time:" + (e-s));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		serializable_v1();
		System.out.println("========");
		serializable_v2();
	}

}
