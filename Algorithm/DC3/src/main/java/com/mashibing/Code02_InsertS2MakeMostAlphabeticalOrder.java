package com.mashibing;

/**
 * 题目二：
 * 给定两个字符串str1和str2，想要把str2整体插入到str1中的某个位置，形成最大的字典序
 * 返回字典序最大的结果
 * <p>
 * 思路分析：
 * 1.最优解是DC3算法
 * 2.暴力解的时间复杂度：O(N * (N + M))  ->  O(N²)
 * 1).str1长度为N，str2长度为M
 * 2).str2插入str1，插入位置从0到N，长度为N + M，插入的时间复杂度：O(N)
 * 3).每次比较的时间复杂度：O(N + M)
 * 4)暴力解的时间复杂度为：O(N * (N + M))，如果N远远大于M的话，那么时间复杂度：O(N²)
 * 3.最终方案的时间复杂度：O(N + M) + O(M²)
 * <p>
 * 解题思路：
 * 1.对str1的每一个位置上的字符进行遍历，如果str1上所有的位置的字典序都大于str2的字典序，直接str1 + str2就是最好的结果  --> DC3算法
 * 从0 ~ i的位置，时间复杂度：O(N)
 * 举例：
 * 如果0位置的字典序大于str2的字典序，那么str2没有必要插入到str1的0位置之前，因为str2插入str1之后，字典序会变小
 * 如果1位置的字典序大于str2的字典序，那么str2没有必要插入到str1的1位置之前，因为str2插入str1之后，字典序会变小
 * 如果i位置的字典序大于str2的字典序，那么str2没有必要插入到str1的i位置之前，因为str2插入str1之后，字典序会变小
 * 2.如果i位置的字典序小于str2的字典序，那么i位置与i - 1位置之间的位置不可忽略，确定最左的位置以及最右的位置，时间复杂度：O(M)
 * 举例：
 * i的字典序：993 str2的字典序：994，那么插入99和3的中间是最优解，插入之后的字典序：99 994 3
 * i的字典序：431 str2的字典序：994，那么插入431之前是最优解，插入之后的字典序：994 431
 * 所以i位置最左的可能性，也就是i - 1与i位置之间的位置是不可忽略的
 * 所以i位置最右的可能性，也就是i位置之后的位置的字典序与str2的字典序进行比较可以取得
 * 3.根据最左和最右位置，截取字符串，每个字符串进行比较字典序，时间复杂度的估计
 * 假设str1 = 9999 993 299
 * 索引index =0123 456 789
 * str2 = 994
 * i位置来到索引4的位置，最左的可能性的索引是i - 1，最右的可能性是99 和 3的中间的位置，也就是索引i + 1和i + 2的中间
 * 假设str2的长度为M，那么先将最左到最右的字符串截取出来，从字符串的最左到最右插入str2之后的最大长度为2M，也就是M，
 * 从字符串的最左位置到最右位置长度为M的字符串互相之间进行比较字典序，时间复杂度：O(M²)
 * 4.总的时间复杂度：O(N + M) + O(M²)
 *
 * @author xcy
 * @date 2022/6/16 - 8:52
 */
public class Code02_InsertS2MakeMostAlphabeticalOrder {
	public static void main(String[] args) {

	}

	/**
	 * 暴力解
	 * 时间复杂度：O(N * (N + M))  ->  O(N²)
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String returnLexicographicOrderMaximumResults(String str1, String str2) {
		if (str1 == null || str1.length() == 0) {
			return str2;
		}
		if (str2 == null || str2.length() == 0) {
			return str1;
		}
		String p1 = str1 + str2;
		String p2 = str2 + str1;
		//str1 + str2 的字典序大于 str2 + str1的字典序，表示str1的字典序比str2的字典序大
		String ans = p1.compareTo(p2) > 0 ? p1 : p2;
		for (int end = 1; end < str1.length(); end++) {
			//从0 ~ end 依次截取的字符串，从end到str1字符串的结尾截取的字符串
			//然后str2插入到中间
			String cur = str1.substring(0, end) + str2 + str1.substring(end);
			//依次进行字典序的比较，字典序大的作为答案
			if (cur.compareTo(ans) > 0) {
				ans = cur;
			}
		}
		//整个for循环之后，返回最终的答案
		return ans;
	}




	/**
	 * 最优解的方法
	 * 时间复杂度：O(N+M) + O(M²)
	 * N : s1长度
	 * M : s2长度
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String returnLexicographicOrderMaximumResultsWithOptimalSolution(String s1, String s2) {
		if (s1 == null || s1.length() == 0) {
			return s2;
		}
		if (s2 == null || s2.length() == 0) {
			return s1;
		}
		char[] s1ToCharArray = s1.toCharArray();
		char[] stToCharArray = s2.toCharArray();
		int N = s1ToCharArray.length;
		int M = stToCharArray.length;
		int min = s1ToCharArray[0];
		int max = s1ToCharArray[0];
		//根据最大值和最小值，调整怎样使用DC3算法
		for (int i = 1; i < N; i++) {
			min = Math.min(min, s1ToCharArray[i]);
			max = Math.max(max, s1ToCharArray[i]);
		}
		for (int i = 0; i < M; i++) {
			min = Math.min(min, stToCharArray[i]);
			max = Math.max(max, stToCharArray[i]);
		}
		//N + M + 1，其中的1表示使用ASCII码最小值做隔断
		int[] all = new int[N + M + 1];
		int index = 0;
		//左边是s1做调整
		for (int i = 0; i < N; i++) {
			all[index++] = s1ToCharArray[i] - min + 2;
		}
		//中间做隔断
		all[index++] = 1;
		//右边是s2做调整
		for (int i = 0; i < M; i++) {
			all[index++] = stToCharArray[i] - min + 2;
		}
		DC3 dc3 = new DC3(all, max - min + 2);
		int[] rank = dc3.rank;
		int comp = N + 1;
		for (int i = 0; i < N; i++) {
			//在s1字符串中，找到了字典序小于s2的字典序的位置
			if (rank[i] < rank[comp]) {
				//根据最左和最右，选择一个最优的位置
				int best = bestSplit(s1, s2, i);
				return s1.substring(0, best) + s2 + s1.substring(best);
			}
		}
		//如果在s1中没有找到字典序小于s2字典序的位置，s1 + s2就是最终答案
		return s1 + s2;
	}

	public static int bestSplit(String s1, String s2, int first) {
		int N = s1.length();
		int M = s2.length();
		int end = N;
		for (int i = first, j = 0; i < N && j < M; i++, j++) {
			if (s1.charAt(i) < s2.charAt(j)) {
				end = i;
				break;
			}
		}
		String bestPrefix = s2;
		int bestSplit = first;
		for (int i = first + 1, j = M - 1; i <= end; i++, j--) {
			String curPrefix = s1.substring(first, i) + s2.substring(0, j);
			if (curPrefix.compareTo(bestPrefix) >= 0) {
				bestPrefix = curPrefix;
				bestSplit = i;
			}
		}
		return bestSplit;
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
}
