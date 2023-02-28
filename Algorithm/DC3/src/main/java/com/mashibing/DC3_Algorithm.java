package com.mashibing;

/**
 * DC3算法
 *
 * @author xcy
 * @date 2022/6/15 - 11:27
 */
public class DC3_Algorithm {
	/**
	 * sa[]表示以索引位置开头的后缀排第几名
	 * index:排第几名
	 * value:后缀是以第几索引位置开头的
	 * 举例：
	 * String arr = {"a", "a", "b", "a"}
	 * sa[] = {3, 0, 1, 2}
	 * index = 0  1  2  3
	 */
	public int[] sa;

	/**
	 * rank[]表示排名是以第几索引位置开头的后缀
	 * index:后缀是以第几索引位置开头的
	 * value:排第几名
	 * 举例：
	 * String arr = {"a", "a", "b", "a"}
	 * rank[] = {1, 2, 3, 0}
	 * index =   0  1  2  3
	 */
	public int[] rank;

	public int[] height;

	/**
	 * 根据String[]加工出rank[]的时间复杂度：O(N)
	 * <p>
	 * 构造方法的约定:
	 *
	 * @param nums nums[]表示如果是字符串类型，请转成整型数组nums，
	 *             数组中，最小值 >=1 ，因为内部有补零的情况，所以数组中元素值不能是负数
	 *             如果不满足，处理成满足的，也不会影响使用
	 * @param max  max表示nums里面的最大值是多少，因为根据max的值准备桶的数量，要使用基数排序
	 */
	public DC3_Algorithm(int[] nums, int max) {
		sa = sa(nums, max);
		rank = rank();
		height = height(nums);
	}

	/**
	 * 创建sa[]
	 *
	 * @param nums 原始数组
	 * @param max  最大值
	 * @return
	 */
	private int[] sa(int[] nums, int max) {
		int n = nums.length;
		//n + 3表示可能会补零
		int[] arr = new int[n + 3];
		for (int i = 0; i < n; i++) {
			arr[i] = nums[i];
		}
		return skew(arr, n, max);
	}

	private int[] skew(int[] nums, int n, int K) {
		//n0 = n + 2 / 3表示S0类
		int n0 = (n + 2) / 3;
		//n1 = (n + 1) / 3表示S1类
		int n1 = (n + 1) / 3;
		//n2 = n / 3表示S2类
		int n2 = n / 3;
		int n02 = n0 + n2;
		//S12类组成的数组
		int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
		for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
			if (0 != i % 3) {
				//只要S1S2类，不要S0类
				s12[j++] = i;
			}
		}
		//三维数据，三次基数排序
		radixPass(nums, s12, sa12, 2, n02, K);
		radixPass(nums, sa12, s12, 1, n02, K);
		radixPass(nums, s12, sa12, 0, n02, K);
		//如果直接分出排名
		int name = 0, c0 = -1, c1 = -1, c2 = -1;
		for (int i = 0; i < n02; ++i) {
			if (c0 != nums[sa12[i]] || c1 != nums[sa12[i] + 1] || c2 != nums[sa12[i] + 2]) {
				name++;
				c0 = nums[sa12[i]];
				c1 = nums[sa12[i] + 1];
				c2 = nums[sa12[i] + 2];
			}
			if (1 == sa12[i] % 3) {
				s12[sa12[i] / 3] = name;
			} else {
				s12[sa12[i] / 3 + n0] = name;
			}
		}
		//如果没有直接分出排名，递归调用
		if (name < n02) {
			sa12 = skew(s12, n02, name);
			for (int i = 0; i < n02; i++) {
				s12[sa12[i]] = i + 1;
			}
		} else {
			//如果分出排名，得到S12类排名的sa12数组
			for (int i = 0; i < n02; i++) {
				sa12[s12[i] - 1] = i;
			}
		}
		int[] s0 = new int[n0], sa0 = new int[n0];
		//解决S0内部的排名
		for (int i = 0, j = 0; i < n02; i++) {
			if (sa12[i] < n0) {
				s0[j++] = 3 * sa12[i];
			}
		}
		radixPass(nums, s0, sa0, 0, n0, K);
		int[] sa = new int[n];
		//解决S0和S12类merge的问题
		for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
			int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
			int j = sa0[p];
			if (sa12[t] < n0 ? leq(nums[i], s12[sa12[t] + n0], nums[j], s12[j / 3])
					: leq(nums[i], nums[i + 1], s12[sa12[t] - n0 + 1], nums[j], nums[j + 1], s12[j / 3 + n0])) {
				sa[k] = i;
				t++;
				if (t == n02) {
					for (k++; p < n0; p++, k++) {
						sa[k] = sa0[p];
					}
				}
			} else {
				sa[k] = j;
				p++;
				if (p == n0) {
					for (k++; t < n02; t++, k++) {
						sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
					}
				}
			}
		}
		return sa;
	}

	private void radixPass(int[] nums, int[] input, int[] output, int offset, int n, int k) {
		int[] cnt = new int[k + 1];
		for (int i = 0; i < n; ++i) {
			cnt[nums[input[i] + offset]]++;
		}
		for (int i = 0, sum = 0; i < cnt.length; ++i) {
			int t = cnt[i];
			cnt[i] = sum;
			sum += t;
		}
		for (int i = 0; i < n; ++i) {
			output[cnt[nums[input[i] + offset]]++] = input[i];
		}
	}

	private boolean leq(int a1, int a2, int b1, int b2) {
		return a1 < b1 || (a1 == b1 && a2 <= b2);
	}

	private boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
		return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
	}

	private int[] rank() {
		int n = sa.length;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[sa[i]] = i;
		}
		return ans;
	}

	private int[] height(int[] s) {
		int n = s.length;
		int[] ans = new int[n];
		for (int i = 0, k = 0; i < n; ++i) {
			if (rank[i] != 0) {
				if (k > 0) {
					--k;
				}
				int j = sa[rank[i] - 1];
				while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
					++k;
				}
				ans[rank[i]] = k;
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int maxValue) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * maxValue) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		//从1000000 -> 10000000，运行时间开始变慢，出发JVM的垃圾回收释放机制
		int len = 10000000;
		int maxValue = 100;
		long start = System.currentTimeMillis();
		new DC3_Algorithm(randomArray(len, maxValue), maxValue);
		long end = System.currentTimeMillis();
		System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
	}

}
