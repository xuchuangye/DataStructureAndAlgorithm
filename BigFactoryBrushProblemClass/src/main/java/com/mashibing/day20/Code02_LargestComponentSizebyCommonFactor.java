package com.mashibing.day20;

import java.util.HashMap;

/**
 * 给定一个由不同正整数的组成的非空数组A，考虑下面的图：有A.length个节点，按从A[0]到A[A.length - 1]标记；
 * 只有当 A[i] 和 A[j] 共用一个大于 1 的公因数时，A[i] 和 A[j] 之间才有一条边。返回图中最大连通集合的大小
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/largest-component-size-by-common-factor/
 * <p>
 * 解题思路：
 * 并查集
 *
 * @author xcy
 * @date 2022/8/13 - 8:33
 */
public class Code02_LargestComponentSizebyCommonFactor {
	public static void main(String[] args) {
		int gcd = gcd(60, 50);
		System.out.println(gcd);
	}

	public static int largestComponentSize1(int[] arr) {
		int N = arr.length;
		UnionFind set = new UnionFind(N);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (gcd(arr[i], arr[j]) != 1) {
					set.union(i, j);
				}
			}
		}
		return set.maxSize();
	}

	public static class UnionFind {
		public int[] parents;
		public int[] sizes;
		public int[] help;

		public UnionFind(int N) {
			parents = new int[N];
			sizes = new int[N];
			help = new int[N];
			//每一个位置的父节点都是自己
			//每一个位置的集合长度都是1
			for (int i = 0; i < N; i++) {
				parents[i] = i;
				sizes[i] = 1;
			}
		}

		public int find(int index) {
			int helpIndex = 0;
			while (index != parents[index]) {
				help[helpIndex++] = index;
				index = parents[index];
			}

			for (helpIndex--; helpIndex >= 0; helpIndex--) {
				parents[help[helpIndex]] = index;
			}
			return index;
		}

		public void union(int i, int j) {
			int father1 = find(i);
			int father2 = find(j);
			if (father1 != father2) {
				int longSize = sizes[father1] >= sizes[father2] ? father1 : father2;
				int shortSize = longSize == father1 ? father2 : father1;
				parents[shortSize] = longSize;
				sizes[longSize] = sizes[father1] + sizes[father2];
			}
		}

		public int maxSize() {
			int ans = 0;
			for (int size : sizes) {
				ans = Math.max(ans, size);
			}
			return ans;
		}
	}

	/**
	 * 为什么需要在数组每个位置都找到该位置元素的因子表，因为可以以O(根号x)的时间复杂度拿下
	 * 举例：
	 * 假设x = 20，根号20的值在4 ~ 5之间，那么求1 ~ 根号20的因子表，也就是1 ~ 4
	 * 1能被20整除，所以因子表记录1,20
	 * 2能被20整除，所以因子表记录2,10
	 * 4能被20整除，所以因子表记录4,5
	 * 除去1不添加到因子表
	 * {2, 0位置的20拥有},{4, 0位置的20拥有},{5, 0位置的20拥有},{10, 0位置的20拥有},{20, 0位置的20拥有}
	 * 这就是20的因子表
	 * 只要下一个位置的因子有不同的因子，就往并查集中添加自己的因子表
	 * 只要下一个位置的因子拥有前一个位置的因子表中的因子，两个位置的数就合并
	 * 时间复杂度：O(N的2次方) -> O(N * 根号x)
	 * 如果数组长度比较长，选择时间复杂度为O(N * 根号x)的方法，如果数组中元素值比较大，选择时间复杂度为O(N的2次方)的方法
	 *
	 * @param arr
	 * @return
	 */
	public static int largestComponentSize2(int[] arr) {
		int N = arr.length;
		//创建并查集
		UnionFind unionFind = new UnionFind(N);
		//创建因子表
		//key：表示每一个位置上的因子，value：表示该因子在什么位置
		HashMap<Integer, Integer> fatorsMap = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			int num = arr[i];
			//求出num的1 ~ 根号num的因子表
			int limit = (int) Math.sqrt(num);
			//1 ~ 根号num
			for (int j = 1; j <= limit; j++) {
				//表示j是num的因子
				if (num % j == 0) {
					//忽略1这个因子
					if (j != 1) {
						//如果当前i位置上的因子表中不包含该因子
						if (!fatorsMap.containsKey(j)) {
							//将当前因子j记录到因子表中，同时记录因子出现的位置i，表示哪个位置拥有该因子
							fatorsMap.put(j, i);
						}
						//否则如果当前i位置上的因子表中包含该因子
						else {
							//将前一个位置的因子和当前位置的因子进行合并
							unionFind.union(fatorsMap.get(j), i);
						}
					}
					//另一个因子
					int other = num / j;
					//忽略1这个因子
					if (other != 1) {
						if (!fatorsMap.containsKey(other)) {
							fatorsMap.put(other, i);
						} else {
							unionFind.union(fatorsMap.get(other), i);
						}
					}
				}
			}
		}
		return unionFind.maxSize();
	}

	/**
	 * 非常重要
	 * gcd()求两个数的最大公约数
	 * <p>
	 * 时间复杂度：O(1)
	 *
	 * @param a 是正整数，不能为0
	 * @param b 是正整数，不能为0
	 * @return 返回两个数的最大公约数
	 */
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
