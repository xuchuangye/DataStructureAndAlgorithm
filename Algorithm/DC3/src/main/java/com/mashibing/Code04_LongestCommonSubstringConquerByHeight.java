package com.mashibing;

/**
 * 题目四：
 * 1.最长公共子串问题是面试常见题目之一
 * <p>
 * 2.假设str1长度N，str2长度M，一般在面试场上回答出O(N*M)的解法已经是比较优秀了，因为得到O(N*M)的解法，就已经需要用到动态规划了
 * 但其实这个问题的最优解是O(N+M)，需要用到后缀数组+height数组
 * <p>
 * 3.但其实这个问题的最优解是O(N+M)，为了达到这个复杂度可是不容易
 * 1)首先需要用到DC3算法得到后缀数组(sa)
 * 2)进而用sa数组去生成height数组
 * 3)而且在生成的时候，还有一个不回退的优化，都非常不容易理解
 * <p>
 * 4.这就是后缀数组在面试算法中的地位 : 德高望重的噩梦
 *
 * 思路分析：
 * 1.使用动态规划的方式，时间复杂度：O(N * M) ----------------------------> 不是最优解
 * 创建dp[][]，dp[i][j]表示必须以str1的i位置结尾和必须以str2的j位置结尾的情况下，最长公共子串是多长
 * 2.举例：
 * 假设
 * str1 =    "1abc2", str2 = "1aabc3"
 * index =    01234           012345
 * dp[][]
 * -  0  1  2  3  4  5  -> str2的j位置
 * -0 1  0  0  0  0  0
 * -1 0  2
 * -2 0
 * -3 0
 * -4 0
 * -str1的i位置
 * str1中以i位置结尾的子串，str2中以j位置结尾的子串
 * 判断两个子串的是否相等，分为两种情况：
 * (1).str1[i] != str2[j]，dp[i][j] == 0
 * (2).str1[i] == str2[j], dp[i][j] == dp[i - 1][j - 1] + 1
 * 最终的答案：str1和str2的最长公共子串就是dp[][]整张表中出现的max
 * 3.使用DC3算法中的Height[]，时间复杂度：O(N + M)  ----------------------------> 是最优解
 * String arr = {"a", "a", "b", "a", "a", "b", "b"}
 * index =        0    1    2    3    4    5    6
 * 第0名：以0位置开始的后缀 -> "aabaabb"
 * 第1名：以3位置开始的后缀 -> "aabb"
 * 第2名：以1位置开始的后缀 -> "abaabb"
 * 第3名：以4位置开始的后缀 -> "abb"
 * 第4名：以6位置开始的后缀 -> "b"
 * 第5名：以2位置开始的后缀 -> "baabb"
 * 第6名：以5位置开始的后缀 -> "bb"
 * sa[] = {0,3,1,4,6,2,5}  -> 以第几个索引位置开始的后缀
 * index = 0 1 2 3 4 5 6   -> 排名
 * rank = {0,2,5,1,3,6,4}  -> 排名
 * index = 0 1 2 3 4 5 6   -> 以第几个索引位置开始的后缀
 * 4.求Height[]之前，先求出h[]
 * h[]  = {0,1,1,3,2,1,0} -> 表示当前以第i索引位置开始的后缀的排名为x，与排名为x - 1的后缀j的最长公有前缀的长度是多少
 * index = 0 1 2 3 4 5 6  -> 表示以第几索引位置开始的后缀
 * 举例：
 * 以0索引位置开始的后缀："aabaabb"，排名第0名，没有第-1名，所以最长公有前缀为0，h[i] = 0
 * 以1索引位置开始的后缀："abaabb"，排名第2名，有第1名，第1名的后缀："aabb"，所以最长公有前缀为："a"，长度为1，h[i] = 1
 * 以2索引位置开始的后缀："baabb"，排名第5名，有第4名，第4名的后缀："b"，所以最长公有前缀为："b"，长度为1，h[i] = 1
 * 以3索引位置开始的后缀："aabb"，排名第1名，有第0名，第0名的后缀："aabaabb"，所以最长公有前缀为："aab"，长度为3，h[i] = 3
 * 以4索引位置开始的后缀："abb"，排名第3名，有第2名，第2名的后缀："abaabb"，所以最长公有前缀为："ab"，长度为2，h[i] = 2
 * 以5索引位置开始的后缀："bb"，排名第6名，有第5名，第5名的后缀："baabb"，所以最长公有前缀为："b"，长度为1，h[i] = 1
 * 以6索引位置开始的后缀："b"，排名第4名，有第3名，第3名的后缀："abb"，所以没有最长公有前缀，长度为0，h[i] = 0
 * 所以h[i]表示当前以第i索引位置开始的后缀的排名为x，与排名为x - 1的后缀j的最长公有前缀的长度是多少
 * 以i位置开始的后缀和以j位置开始的后缀进行匹配，时间复杂度：O(N * N) -> O(N²)
 * 5.将O(N²)降为O(N)是关键：h[i - 1]的值能够指导h[i]的值
 * 结论：h[i - 1] - 1 <= h[i]，如果h[i - 1]的答案不比h[i]差，那么就相当于不回退
 * 举例：
 * arr[] = {... "a", "a", "a", "b", "c" ... "a", "a", "a", "d" ...}
 * index =       17   18   19   20   21      27   28   29   30
 * 以17位置开始的后缀："aaabc"，排名第5名，假设第4名是以27位置开始的后缀："aaad"，最长公有前缀是"aaa"，长度是3
 * 那么h[17] = 3
 * h[18]的值一定是 <= 3 - 1，并且值不会回退，例如：计算h[i + 1]的值，只会往左错一个格子h[i]，最多错N个格子，整体是不回退的
 * 所以整体的h[]时间复杂度：O(N)
 * 5.Height[]，height[i]表示第i名的对应的原始位置index，和i - 1名的后缀串的最长公有前缀的长度是多少
 * 举例：
 * h[]  =     {0,1,1,3,2,1,0} -> 表示当前以第i索引位置开始的后缀的排名为x，与排名为x - 1的后缀j的最长公有前缀的长度是多少
 * index =     0 1 2 3 4 5 6  -> 表示以第几索引位置开始的后缀
 * height[] = {0,3,1,2,0,1,1} -> 表示第i名的对应的后缀串，和i - 1名的后缀串的最长公有前缀的长度是多少
 * index =     0 1 2 3 4 5 6  -> 排名
 * height[i] = sa[i]名的开始位置x的后缀串和sa[i - 1]名的开始位置y的后缀串的最长公有前缀的长度是多少
 * sa[i] = x, sa[i - 1] = y
 * 假设：
 * str1 = "12abcd34"
 * str2 = "4567abcd89"
 * 合并str1和str2，并加上ASCII码的最小值进行隔断 = "12abcd34 | 4567abcd89"
 * 那么x的位置坐落在左侧，y的位置坐落在右侧才是达标的，x和y位置不坐落在两侧就是不达标的
 * 在所有达标的里面求出最大值，就是题目的结果 -> str1和str2的最长公有子串
 * @author xcy
 * @date 2022/6/16 - 8:58
 */
public class Code04_LongestCommonSubstringConquerByHeight {


	public static void main(String[] args) {
		int len = 30;
		int range = 5;
		int testTime = 100000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int N1 = (int) (Math.random() * len);
			int N2 = (int) (Math.random() * len);
			String str1 = randomNumberString(N1, range);
			String str2 = randomNumberString(N2, range);
			int ans1 = lcs1(str1, str2);
			int ans2 = lcs2(str1, str2);
			if (ans1 != ans2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("功能测试结束");
		System.out.println("==========");

		System.out.println("性能测试开始");
		len = 80000;
		range = 26;
		long start;
		long end;

		String str1 = randomNumberString(len, range);
		String str2 = randomNumberString(len, range);

		start = System.currentTimeMillis();
		int ans1 = lcs1(str1, str2);
		end = System.currentTimeMillis();
		System.out.println("方法1结果 : " + ans1 + " , 运行时间 : " + (end - start) + " ms");

		start = System.currentTimeMillis();
		int ans2 = lcs2(str1, str2);
		end = System.currentTimeMillis();
		System.out.println("方法2结果 : " + ans2 + " , 运行时间 : " + (end - start) + " ms");

		System.out.println("性能测试结束");

	}

	public static int lcs1(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int row = 0;
		int col = str2.length - 1;
		int max = 0;
		while (row < str1.length) {
			int i = row;
			int j = col;
			int len = 0;
			while (i < str1.length && j < str2.length) {
				if (str1[i] != str2[j]) {
					len = 0;
				} else {
					len++;
				}
				if (len > max) {
					max = len;
				}
				i++;
				j++;
			}
			if (col > 0) {
				col--;
			} else {
				row++;
			}
		}
		return max;
	}

	public static int lcs2(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length;
		int M = str2.length;
		int min = str1[0];
		int max = str1[0];
		for (int i = 1; i < N; i++) {
			min = Math.min(min, str1[i]);
			max = Math.max(max, str1[i]);
		}
		for (int i = 0; i < M; i++) {
			min = Math.min(min, str2[i]);
			max = Math.max(max, str2[i]);
		}
		//合并数组，合并数组的长度为数组1的长度 + 数组2的长度 + 1，1这个长度表示隔断
		//举例：
		//假设str1 = "aab"，str2 = "ab"
		//如果直接合并，得到新的字符串"aabab"，并不好，会出现问题，因为str1的最后一个字符'b'后面没有东西了
		//所以"aab"和"ab"中间需要ASCII码的最小值，而DC3算法要求数值必须要 >= 1，所以隔断"aab"和"ab"的ASCII码最小值是1
		//也就是"aab1ab"，才能够知道str1和str2的谁的字典序更好
		int[] mergeArray = new int[N + M + 1];
		int index = 0;
		//因为隔断两个字符串数组的ASCII码为1，而所有的arr数组中的数值范围是[0 ~ 9]，所以arr数组中的所有元素值 + 2，为什么，+ 1的话会跟隔断的ASCII码值1搞混淆
		//所以左侧数组的值都加上2
		for (int i = 0; i < N; i++) {
			mergeArray[index++] = str1[i] - min + 2;
		}
		//中间是隔断的ASCII码的最小值，并且由于DC3算法的限制，值 >= 1，所以中间的隔断值是1
		mergeArray[index++] = 1;
		//右侧数组的值都加上2
		for (int i = 0; i < M; i++) {
			mergeArray[index++] = str2[i] - min + 2;
		}
		DC3 dc3 = new DC3(mergeArray, max - min + 2);
		int n = mergeArray.length;
		int[] sa = dc3.sa;
		int[] height = dc3.height;
		int ans = 0;
		for (int i = 1; i < n; i++) {
			int Y = sa[i - 1];
			int X = sa[i];
			//如果X和Y分别来自左右两侧
			//Math.min(X, Y) < N表示左侧
			//Math.max(X, Y) > N表示右侧
			if (Math.min(X, Y) < N && Math.max(X, Y) > N) {
				//条件达标，记录全局最大值
				ans = Math.max(ans, height[i]);
			}
		}
		return ans;
	}

	public static class DC3 {

		public int[] sa;

		public int[] rank;

		public int[] height;

		/**
		 * 创建sa[],rank[],height[]的时间复杂度：O(N)
		 * @param nums
		 * @param max
		 */
		public DC3(int[] nums, int max) {
			sa = sa(nums, max);
			rank = rank();
			height = height(nums);
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

		/**
		 * 生成height[]
		 * @param s
		 * @return
		 */
		private int[] height(int[] s) {
			int n = s.length;
			int[] ans = new int[n];
			// 依次求h[i] , 一开始的最长公有前缀的长度k = 0，k表示上一个h[i]的值
			for (int i = 0, k = 0; i < n; ++i) {
				//表示只要不是第0名
				if (rank[i] != 0) {
					if (k > 0) {
						--k;
					}
					//如果k == 0，k就不--
					int j = sa[rank[i] - 1];
					//枚举的过程
					//相等就++k，不相等就不++k
					while (i + k < n && j + k < n && s[i + k] == s[j + k]) {
						++k;
					}
					//k表示此时h[i]的值，所以不需要创建h[]，直接将值赋值给height[]即可
					//rank[i]表示i索引是第几名
					ans[rank[i]] = k;
				}
			}
			return ans;
		}

	}

	// for test
	public static String randomNumberString(int len, int range) {
		char[] str = new char[len];
		for (int i = 0; i < len; i++) {
			str[i] = (char) ((int) (Math.random() * range) + 'a');
		}
		return String.valueOf(str);
	}
}
