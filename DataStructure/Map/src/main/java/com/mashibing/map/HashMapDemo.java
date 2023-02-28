package com.mashibing.map;

import java.util.HashMap;

/**
 * HashMap哈希表
 * @author xcy
 * @date 2022/4/8 - 15:46
 */
public class HashMapDemo {
	public static void main(String[] args) {
		//HashMap的key对于基本的包装类以及String类型都是按值传递
		HashMap<String, String> map = new HashMap<>();
		String s1 = "xuchuangye";
		String s2 = "xuchuangye";
		map.put("xuchuangye","我回来了");
		System.out.println(map.get(s1));
		System.out.println(map.get(s2));

		HashMap<Integer, String> hashMap = new HashMap<>();
		Integer a = 1234567;
		Integer b = 1234567;
		hashMap.put(1234567, "我又回来了");
		System.out.println(hashMap.get(a));
		System.out.println(hashMap.get(b));
	}
}
