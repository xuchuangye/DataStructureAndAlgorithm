package com.mashibing.learn;

import java.util.LinkedList;

/**
 * @author xcy
 * @date 2022/2/18 - 21:10
 */
public class TimeTest {
	private static final LinkedList<Integer> list = new LinkedList<>();

	static {
		for (Integer i = 1; i <= 10000000; i++) {
			list.add(i);
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		//所以总的时间复杂度为O(N²)
		//循环N次，时间复杂度为O(N)
		for (int i = 0; i < list.size(); i++) {
			//每次从list链表中获取元素时，都会重新从0开始查找，时间复杂度为O(N)
			Integer integer = list.get(i);
			System.out.println(integer);
		}
		long end = System.currentTimeMillis();
		System.out.println("查找链表中第200w的元素时，花费的时间：" + (end - start));
	}
}
