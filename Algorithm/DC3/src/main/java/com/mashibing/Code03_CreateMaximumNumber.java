package com.mashibing;

/**
 * 题目三：
 * 给两个长度分别为M和N的整型数组nums1和nums2，其中每个值都不大于9，再给定一个正数K。
 * 你可以在nums1和nums2中挑选数字，要求一共挑选K个，并且要从左到右挑。返回所有可能的结果中，代表最大数字的结果。
 * <p>
 * 给定长度分别为M和N的两个数组nums1和nums2，其元素由0-9构成，表示两个自然数各位上的数字。
 * 现在从这两个数组中选出K(K <= M + N)个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
 * 求出满足该条件的最大数。结果返回一个表示该最大数的长度为k的数组。
 * 说明: 请尽可能地优化你算法的时间和空间复杂度。
 * <p>
 * 示例1：
 * 输入:
 * nums1 = [3, 4, 6, 5]
 * nums2 = [9, 1, 2, 5, 8, 3]
 * k = 5
 * 输出:
 * [9, 8, 6, 5, 3]
 * 示例2：
 * 输入:
 * nums1 = [6, 7]
 * nums2 = [6, 0, 4]
 * k = 5
 * 输出:
 * [6, 7, 6, 0, 4]
 * 示例3：
 * 输入:
 * nums1 = [3, 9]
 * nums2 = [8, 9]
 * k = 3
 * 输出:
 * [9, 8, 9]
 * <p>
 * <p>
 * 思路分析：
 * arr1[] = {}, arr2[] == {}
 * 假设K = 5
 * 那么大流程分为几种情况：
 * (1).从arr1[]中挑选5个，arr2[]中不挑选
 * (2).从arr1[]中挑选4个，arr2[]中挑选1个
 * (3).从arr1[]中挑选3个，arr2[]中挑选2个
 * (4).从arr1[]中挑选2个，arr2[]中挑选3个
 * (5).从arr1[]中挑选1个，arr2[]中挑选4个
 * (6).从arr1[]中不挑选，arr2[]中挑选5个
 * 1.需要解决两个问题：
 * 第一：解决从1个数组中，从左往右挑选，挑选出M个数，并且是尽量大的
 * 第二：从两个数组中挑选K个数，合并之后是尽量大的
 * 2.需要解决批量查询做预处理结构的问题
 * 1)：不管查询1个数组挑选几个，都能够很快的知道答案
 * 举例：
 * arr1[]中挑选5个
 * arr1[]中挑选4个
 * arr1[]中挑选3个
 * arr1[]中挑选M个
 * 2)：使用dp[][]表示预处理结构
 * 假设arr[] = {6, 9, 4, 9, 2}
 * index =     0  1  2  3  4
 * -  0  1  2  3  4  5  ->j
 * -0 X  1  1  1  1  0
 * -1 X  1  1  1  1  X
 * -2 X  3  3  2  X  X
 * -3 X  3  3  X  X  X
 * -4 X  4  X  X  X  X
 * -i
 * X：表示不需要填写
 * (1).j == 0时表示挑选0个，没有意义，所以j == 0这一列都不需要填写
 * (2).当i == 4时表示从索引4开始及其以后的位置可以挑选，只能挑选1个，所以j == 2,3,4,5都不需要填写
 * 当i == 3时表示从索引3开始及其以后的位置可以挑选，只能挑选2个，所以j == 3,4,5都不需要填写
 * 当i == 2时表示从索引2开始及其以后的位置可以挑选，只能挑选3个，所以j == 4,5都不需要填写
 * 当i == 1时表示从索引1开始及其以后的位置可以挑选，只能挑选4个，所以j == 5都不需要填写
 * (3).当i == 4时表示从索引4开始及其以后的位置可以挑选，只能挑选1个，所以j == 1时，dp[i][j] == 4
 * 当i == 3时表示从索引3开始及其以后的位置可以挑选，只能挑选2个，所以j == 2时，dp[i][j] == 3
 * 当i == 2时表示从索引2开始及其以后的位置可以挑选，只能挑选3个，所以j == 3时，dp[i][j] == 2
 * 当i == 1时表示从索引1开始及其以后的位置可以挑选，只能挑选4个，所以j == 4时，dp[i][j] == 1
 * 当i == 0时表示从索引0开始及其以后的位置可以挑选，只能挑选5个，所以j == 5时，dp[i][j] == 0
 * arr[] = {6, 9, 4, 9, 2}
 * index    0  1  2  3  4
 * dp[i][j]表示只能从索引i开始及其往后的位置可以挑选，并且挑选j个，最大的结果的开始位置在哪里？
 * dp[2][1] = 3表示从索引2开始及其往后的位置可以挑选，并且挑选1个，最大的结果是9，所以最大结果的开始位置是3
 * dp[][]预处理结构非常有用，能够迅速得到挑选的最大结果
 * 举例：
 * arr[] = {6, 9, 4, 9, 2}
 * 假设从arr数组中，挑选2个
 * (1)先从索引0及其往后的位置开始，挑选1个，最大结果起始位置是索引1，值是9
 * (2)那么只需要从索引2及其往后的位置开始，挑选1个，最大结果的起始位置是索引3，值是9
 * (3)最终挑选出的最大结果是99
 * 假设从arr数组中，挑选6个
 * 从索引0开始及其往后的位置，挑选6个，假设最大结果的起始位置是索引7
 * 从索引8开始及其往后的位置，挑选5个，假设最大结果的起始位置是索引13
 * 从索引13开始及其往后的位置，挑选4个...以此类推如果从arr数组中，挑选M个，那么时间复杂度：O(M)
 * 3.需要解决从两个数组中挑选K个数，合并之后是尽量大的问题
 * 假设从arr1[]和arr2[]中挑选8个
 * arr1 = {9, 9, 9, 3, 2}
 * -         /\
 * -         |
 * -指针1
 * arr2 = {9, 9, 4}
 * -      /\
 * -      |
 * -指针2
 * 指针1是arr1[]的指针，指针2是arr2[]中的指针，指针1和指针2同时指向各自数组的0索引
 * 第一步：
 * 指针1指向的值是arr1[]中0索引上的9，指针2指向的值是arr2[]中0索引上的9，相等就需要同时往右滑动，
 * 指针1指向的值是arr1[]中1索引上的9，指针2指向的值是arr2[]中1索引上的9，相等就需要同时往右滑动，
 * 指针1指向的值是arr1[]中2索引上的9，指针2指向的值是arr2[]中2索引上的4，不相等，9>4，所以挑选出第1个值9，arr1[]0索引上的9，指针1往右滑动
 * 第一步：
 * 指针1指向的值是arr1[]中1索引上的9，指针2指向的值是arr2[]中0索引上的9，相等就需要同时往右滑动，
 * 指针1指向的值是arr1[]中2索引上的9，指针2指向的值是arr2[]中1索引上的9，相等就需要同时往右滑动，
 * 指针1指向的值是arr1[]中3索引上的9，指针2指向的值是arr2[]中2索引上的4，不相等，4>3，所以挑选出第2个值9，arr2[]0索引上的9，指针2往右滑动
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/create-maximum-number
 *
 * @author xcy
 * @date 2022/6/16 - 8:53
 */
public class Code03_CreateMaximumNumber {
	public static void main(String[] args) {

	}

	/**
	 * @param arr1 数组1
	 * @param arr2 数组2
	 * @param k    挑选k个数
	 * @return 返回同时在arr1[]和arr2[]中从左到右挑选k个数，并且结果是最大的值组成的数组
	 */
	public static int[] maxNumber1(int[] arr1, int[] arr2, int k) {
		//len1表示arr1[]的长度
		int len1 = arr1.length;
		//len2表示arr2[]的长度
		int len2 = arr2.length;
		if (k < 0 || k > len1 + len2) {
			return null;
		}
		int[] res = new int[k];
		// 生成dp1这个表，以后从nums1中，只要固定拿N个数，
		int[][] dp1 = getdp(arr1);
		int[][] dp2 = getdp(arr2);
		//从arr1[]中挑选的下限是：
		//如果arr2[]的长度够k个，也就是k - (arr2[]的长度)len2 <= 0，那么arr1[]挑选的下限就可以从0开始
		//如果arr2[]的长度不够k个，也就是k - (arr2[]的长度)len2 > 0，那么arr1[]挑选的下限就可以从k - (arr2[]的长度)len2开始
		//从arr1[]中挑选的上限是：
		//如果arr1[]的长度够k个，那么arr1[]挑选的上限就可以从k开始
		//如果arr1[]的长度不够k个，那么arr1[]挑选的上限就可以从arr1[]的长度len1开始
		//getArr1表示从arr1[]中挑选的个数
		//那么K - getArr1表示从arr2[]中挑选的个数
		for (int getArr1 = Math.max(0, k - len2); getArr1 <= Math.min(k, len1); getArr1++) {
			// arr1挑选getArr1个，怎么得到一个最优结果
			int[] pick1 = maxPick(arr1, dp1, getArr1);
			int[] pick2 = maxPick(arr2, dp2, k - getArr1);
			int[] merge = merge(pick1, pick2);
			res = preMoreThanLast(res, 0, merge, 0) ? res : merge;
		}
		return res;
	}

	/**
	 * 没有使用优化的合并两个数组的方法
	 *
	 * @param nums1
	 * @param nums2
	 * @return
	 */
	public static int[] merge(int[] nums1, int[] nums2) {
		int k = nums1.length + nums2.length;
		int[] ans = new int[k];
		for (int i = 0, j = 0, r = 0; r < k; ++r) {
			ans[r] = preMoreThanLast(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
		}
		return ans;
	}

	public static boolean preMoreThanLast(int[] nums1, int i, int[] nums2, int j) {
		while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
			i++;
			j++;
		}
		return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
	}

	/**
	 * 使用DC3算法的方式
	 * @param arr1
	 * @param arr2
	 * @param k
	 * @return
	 */
	public static int[] maxNumber2(int[] arr1, int[] arr2, int k) {
		int len1 = arr1.length;
		int len2 = arr2.length;
		if (k < 0 || k > len1 + len2) {
			return null;
		}
		int[] res = new int[k];
		int[][] dp1 = getdp(arr1);
		int[][] dp2 = getdp(arr2);
		//从arr1[]中挑选的下限是：
		//如果arr2[]的长度够k个，也就是k - (arr2[]的长度)len2 <= 0，那么arr1[]挑选的下限就可以从0开始
		//如果arr2[]的长度不够k个，也就是k - (arr2[]的长度)len2 > 0，那么arr1[]挑选的下限就可以从k - (arr2[]的长度)len2开始
		//从arr1[]中挑选的上限是：
		//如果arr1[]的长度够k个，那么arr1[]挑选的上限就可以从k开始
		//如果arr1[]的长度不够k个，那么arr1[]挑选的上限就可以从arr1[]的长度len1开始
		//getArr1表示从arr1[]中挑选的个数
		//那么K - getArr1表示从arr2[]中挑选的个数
		for (int getArr1 = Math.max(0, k - len2); getArr1 <= Math.min(k, len1); getArr1++) {
			//arr1挑选getArr1个，怎么得到一个最优结果
			int[] pick1 = maxPick(arr1, dp1, getArr1);
			int[] pick2 = maxPick(arr2, dp2, k - getArr1);
			int[] merge = mergeBySuffixArray(pick1, pick2);
			res = moreThan(res, merge) ? res : merge;
		}
		return res;
	}

	public static boolean moreThan(int[] pre, int[] last) {
		int i = 0;
		int j = 0;
		while (i < pre.length && j < last.length && pre[i] == last[j]) {
			i++;
			j++;
		}
		return j == last.length || (i < pre.length && pre[i] > last[j]);
	}

	/**
	 * 使用优化的合并两个数组的方法
	 *
	 * @param arr1 数组1
	 * @param arr2 数组2
	 * @return
	 */
	public static int[] mergeBySuffixArray(int[] arr1, int[] arr2) {
		int size1 = arr1.length;
		int size2 = arr2.length;
		//合并数组，合并数组的长度为数组1的长度 + 数组2的长度 + 1，1这个长度表示隔断
		//举例：
		//假设str1 = "aab"，str2 = "ab"
		//如果直接合并，得到新的字符串"aabab"，并不好，会出现问题，因为str1的最后一个字符'b'后面没有东西了
		//所以"aab"和"ab"中间需要ASCII码的最小值，而DC3算法要求数值必须要 >= 1，所以隔断"aab"和"ab"的ASCII码最小值是1
		//也就是"aab1ab"，才能够知道str1和str2的谁的字典序更好
		int[] mergeArray = new int[size1 + 1 + size2];

		//因为隔断两个字符串数组的ASCII码为1，而所有的arr数组中的数值范围是[0 ~ 9]，所以arr数组中的所有元素值 + 2，为什么，+ 1的话会跟隔断的ASCII码值1搞混淆
		//所以左侧数组的值都加上2
		for (int i = 0; i < size1; i++) {
			mergeArray[i] = arr1[i] + 2;
		}
		//中间是隔断的ASCII码的最小值，并且由于DC3算法的限制，值 >= 1，所以中间的隔断值是1
		mergeArray[size1] = 1;
		//右侧数组的值都加上2
		for (int j = 0; j < size2; j++) {
			mergeArray[j + size1 + 1] = arr2[j] + 2;
		}
		DC3 dc3 = new DC3(mergeArray, 11);
		int[] rank = dc3.rank;
		int[] ans = new int[size1 + size2];
		int i = 0;
		int j = 0;
		int r = 0;
		while (i < size1 && j < size2) {
			//rank[]直接告诉谁的字典序更大
			ans[r++] = rank[i] > rank[j + size1 + 1] ? arr1[i++] : arr2[j++];
		}
		while (i < size1) {
			ans[r++] = arr1[i++];
		}
		while (j < size2) {
			ans[r++] = arr2[j++];
		}
		return ans;
	}

	public static class DC3 {

		public int[] sa;

		public int[] rank;

		public DC3(int[] nums, int max) {
			sa = sa(nums, max);
			rank = rank();
		}

		private int[] sa(int[] nums, int max) {
			int n = nums.length;
			int[] arr = new int[n + 3];
			for (int i = 0; i < n; i++) {
				arr[i] = nums[i];
			}
			return skew(arr, n, max);
		}

		private int[] skew(int[] nums, int n, int K) {
			int n0 = (n + 2) / 3, n1 = (n + 1) / 3, n2 = n / 3, n02 = n0 + n2;
			int[] s12 = new int[n02 + 3], sa12 = new int[n02 + 3];
			for (int i = 0, j = 0; i < n + (n0 - n1); ++i) {
				if (0 != i % 3) {
					s12[j++] = i;
				}
			}
			radixPass(nums, s12, sa12, 2, n02, K);
			radixPass(nums, sa12, s12, 1, n02, K);
			radixPass(nums, s12, sa12, 0, n02, K);
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
			if (name < n02) {
				sa12 = skew(s12, n02, name);
				for (int i = 0; i < n02; i++) {
					s12[sa12[i]] = i + 1;
				}
			} else {
				for (int i = 0; i < n02; i++) {
					sa12[s12[i] - 1] = i;
				}
			}
			int[] s0 = new int[n0], sa0 = new int[n0];
			for (int i = 0, j = 0; i < n02; i++) {
				if (sa12[i] < n0) {
					s0[j++] = 3 * sa12[i];
				}
			}
			radixPass(nums, s0, sa0, 0, n0, K);
			int[] sa = new int[n];
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

	}

	public static int[][] getdp(int[] arr) {
		int size = arr.length; // 0~N-1
		int pick = arr.length + 1; // 1 ~ N
		int[][] dp = new int[size][pick];
		// get 不从0开始，因为拿0个无意义
		for (int get = 1; get < pick; get++) { // 1 ~ N
			int maxIndex = size - get;
			// i~N-1
			for (int i = size - get; i >= 0; i--) {
				if (arr[i] >= arr[maxIndex]) {
					maxIndex = i;
				}
				dp[i][get] = maxIndex;
			}
		}
		return dp;
	}

	public static int[] maxPick(int[] arr, int[][] dp, int pick) {
		int[] res = new int[pick];
		for (int resIndex = 0, dpRow = 0; pick > 0; pick--, resIndex++) {
			res[resIndex] = arr[dp[dpRow][pick]];
			dpRow = dp[dpRow][pick] + 1;
		}
		return res;
	}

}
