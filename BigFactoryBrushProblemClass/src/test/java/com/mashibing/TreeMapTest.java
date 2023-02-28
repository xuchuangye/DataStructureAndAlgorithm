package com.mashibing;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author xcy
 * @date 2022/7/28 - 9:46
 */
public class TreeMapTest {
	public static void main(String[] args) {
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
		treeMap.put("ABC", 5);
		treeMap.put("BCD", 5);
		treeMap.put("CKC", 6);

		//有序表修改数据之后，不会重新进行排序，没有反向索引表，不知道从哪里开始调整，依然是原来数据的排列
		System.out.println(treeMap);
		treeMap.remove("ABC", 5);
		treeMap.put("ABC", 6);
		System.out.println(treeMap);
	}


	public static class MyComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}
	}
}
