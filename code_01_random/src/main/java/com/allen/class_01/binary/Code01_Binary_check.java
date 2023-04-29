package com.allen.class_01.binary;

import java.util.Scanner;

/**
 * 二进制相关
 * @author allen
 * 
 */
public class Code01_Binary_check {

	
	public static String  toBinaryStr(int num) {
		
		StringBuilder s = new StringBuilder();
		for(int i = 31; i>= 0;i--) {
			int cur = (num >> i & 1);
			s.append(cur);
			if((i & 7) == 0 && i != 0) {
				s.append("_");
			}
		}
		return s.toString();
	}
	
	
	/**
	 * 二进制转换
	 */
	public static void toBinaryFromRead() {
		
		Scanner s = new Scanner(System.in);
		System.out.print("请输入数据：");
		while(s.hasNext()) {
			
			Integer in = s.nextInt();
			if(in == 0) {
				break;
			}
			String str = Integer.toBinaryString(in);
			System.out.printf("%32s \n",str);
//			System.out.println(toBinaryStr(in));
			System.out.print("请输入数据：");
		}
		
	}
	
	
	public static void main(String[] args) {
		
	  toBinaryFromRead();
		
	}
	
}
