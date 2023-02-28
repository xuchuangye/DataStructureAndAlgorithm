package com.mashibing.day04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 题目一：
 * 数组为{3, 2, 2, 3, 1}，查询数组为(0, 3, 2)，意思是在数组里下标0~3这个范围上，有几个？答案返回2
 * 假设给你一个数组arr，对这个数组的查询非常频繁，也就是查询数组非常多，且都给了查询组，请返回所有查询的结果
 * <p>
 * 解题思路：
 * 1.生成预处理结构，单次代价时间复杂度很低
 * 假设：
 * arr[] = {3, 2, 1, 3, 2, 1, 1, 3, 2}
 * index =  0  1  2  3  4  5  6  7  8
 * 1: {2 5 6} 2: {1 4 8} 3: {0 3 7}
 * 2.使用二分查找
 *
 * @author xcy
 * @date 2022/7/15 - 11:37
 */
public class Code01_QueryHobby {
	public static void main(String[] args) {
		System.out.println(1 / 2);
	}

	public static class QueryBox {
		private final HashMap<Integer, ArrayList<Integer>> hashMap;

		/**
		 * 根据arr[]创建arr[]中不同的元素值的索引列表
		 * @param arr
		 */
		public QueryBox(int[] arr) {
			hashMap = new HashMap<>();

			for (int value : arr) {
				if (!hashMap.containsKey(value)) {
					hashMap.put(value, new ArrayList<>());
				}
				hashMap.get(value).add(value);
			}
		}

		public int query(int L, int R, int value) {
			if (!hashMap.containsKey(value)) {
				return 0;
			}

			ArrayList<Integer> indexList = hashMap.get(value);

			//3:   {2,  5,  7, 10, 13}
			//index:0   1   2   3   4
			//查询返回6 ~ 12
			//返回a == 2
			int a = countLess(indexList, L);
			//返回b == 4
			int b = countLess(indexList, R + 1);
			//4 - 2 == 2
			return b - a;
		}

		/**
		 * 对于列表，使用二分查找找出 < limit的数有多少个
		 * @param arr
		 * @param limit
		 * @return
		 */
		public int countLess(ArrayList<Integer> arr, int limit) {
			int L = 0;
			int R = arr.size() - 1;
			int mostRight = -1;
			while (L <= R) {
				int mid = L + ((R - L) >> 1);
				if (arr.get(mid) < limit) {
					mostRight = mid;
					L = mid + 1;
				} else {
					R = mid - 1;
				}
			}
			return mostRight + 1;
		}
	}
}
