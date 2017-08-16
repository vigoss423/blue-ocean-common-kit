/**
 * @Copyright:Copyright (c) 2009 - 2014
 * @Company:SITECH
 * @Author: Dable
 * @Since:  2015年7月30日
 */
package com.blueocean.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;


/**
 * @Title:
 * @Description:
 * @Author: Dable
 * @Since:  2015年7月30日
 * @Version:1.0
 */
public class UUIDGen {
	public static String getUUID(){
		return 	UUID.randomUUID().toString().replaceAll("-", "");
		
	}
	
	public static String getNumID(){
		 SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		 return sdf.format(new Date())+getRandomString(4);
		
	}
	
	
	 public static String getRandomString(int length){
	     String str="0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(10);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
	 
	 public static void main(String[] args) {
		/* HashMap<String,String> res=new HashMap<String, String>();
		int j=0;
		long c=System.currentTimeMillis();
 		for (int i = 0; i <10000; i++) {
			 String uid=UUIDGen.getNumID();
			 res.put("a"+String.valueOf(i),uid);
			 if(!res.containsValue(uid)){
				System.out.println(uid);
			 }else{
				 ++j;
			 }
		}
 		System.out.println(j);
 		System.out.println(System.currentTimeMillis()-c);*/
// 		long d=System.currentTimeMillis();
// 		List<String> s =new ArrayList<String>();
//		for(int i=0;i<10;i++){
//			String re = UUIDGen.getNumID();
//			if(!s.contains(re)){
//				System.out.println(re);
//				s.add(re);
//			}else{
//			 	System.out.println(re);
//			}
//		}
//		System.out.println(s.size());
//		System.out.println(System.currentTimeMillis()-d);
// 		
 		
 		System.out.println(UUIDGen.getNumID());
 		
	}
}
