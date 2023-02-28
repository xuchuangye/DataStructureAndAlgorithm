package com.mashibing;

import com.mashibing.common.KMPUtils;

/**
 * KMP算法
 *
 * 假设字符串str长度为N，字符串match长度为M，M <= N
 * 想确定str中是否有某个子串是等于match的。
 *
 * 要求：
 * 时间复杂度O(N)
 * @author xcy
 * @date 2022/5/20 - 16:36
 */
public class KMPAlgorithm {
	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 50000000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			String str = KMPUtils.getRandomString(possibilities, strSize);
			String match = KMPUtils.getRandomString(possibilities, matchSize);
			if (KMP(str, match) != str.indexOf(match)) {
				System.out.println("测试出错!");
			}
		}
		System.out.println("测试结束！");

		System.out.println("---------------");

		System.out.println("测试violenceMatch()开始");
		long start = System.currentTimeMillis();
		for (int i = 0; i < testTimes; i++) {
			String str = KMPUtils.getRandomString(possibilities, strSize);
			String match = KMPUtils.getRandomString(possibilities, matchSize);
			violenceMatch(str, match);
		}
		long end = System.currentTimeMillis();
		System.out.println("测试violenceMatch()结束");
		System.out.println("violenceMatch()测试共花费了" + (end - start));

		System.out.println("测试KMP()开始");
		start = System.currentTimeMillis();
		for (int i = 0; i < testTimes; i++) {
			String str = KMPUtils.getRandomString(possibilities, strSize);
			String match = KMPUtils.getRandomString(possibilities, matchSize);
			KMP(str, match);
		}
		end = System.currentTimeMillis();
		System.out.println("测试KMP()结束");
		System.out.println("KMP()测试共花费了" + (end - start));
	}

	/**
	 * 该方法的时间复杂度：O(N)
	 * 获取next[]数组的时间复杂度：O(M) M <= N
	 * KMP算法 --> while循环的时间复杂度：O(N)
	 * 所以该方法的总的时间复杂度：O(N)
	 *
	 * 因为在while循环 中，3种情况只会进其中一种
	 * 所以时间复杂度跟循环次数有关，所以时间复杂度为：O(N)，N表示主字符串的长度
	 * //x 范围最大到N，y 范围最大到M，x - y的范围最大到N
	 * while (x < str1.length && y < str2.length) {
	 *     //情况1：
	 *     //x的值增加，y的值增加，x - y的值不变
	 *     if (str1[x] == str2[y]) {
	 *     	   x++;
	 *         y++;
	 *     }
	 *     //情况2：
	 *     //x值值增加，范围最大到N，x - y的值在减小，范围最大到N
	 *     else if (next[y] == -1) {
	 *     	   x++;
	 *     }
	 *     //情况3：
	 *     //x 值不变，范围最大到N，x - y的值在增加，范围最大到N
	 *     else {
	 *     	   y = next[y];
	 *     }*
	 * }
	 * @param str 主字符串
	 * @param match 准备匹配的字符串
	 * @return 返回主字符串中是否能够匹配到准备匹配的字符串
	 * 如果能够匹配到，返回第一次匹配到的索引
	 * 如果不能够匹配到，返回-1，表示没有匹配到
	 */
	public static int KMP(String str, String match) {
		if (str == null || match == null || match.length() == 0 || str.length() < match.length()) {
			return -1;
		}
		//字符串的字符数组
		char[] str1 = str.toCharArray();
		char[] str2 = match.toCharArray();
		//str1字符串的真正进行比较的位置
		int x = 0;
		//str2字符串的真正进行比较的位置
		int y = 0;
		//next数组
		int[] next = getNextArray(str2);

		//举例：
		//str1 = "a  b  c  a  b  c  t  a  b  c  a  b  c  z"
		//索引     0  1  2  3  4  5  6  7  8  9 10 11 12 13
		//str2 = "a  b  c  a  b  c  z"
		//索引     0  1  2  3  4  5  6
		//第一次匹配时，"t" != "z"，并且"z"的前缀和后缀共有字符串的最大长度为3
		//str1 = "a  b  c  a  b  c  t  a  b  c  a  b  c  z"
		//索引     0  1  2  3  4  5  6  7  8  9 10 11 12 13
		//str2 =          "a  b  c  a  b  c  z"
		//索引             0  1  2  3  4  5  6
		//第二次匹配时，"t" 和 索引3的"a"进行比较，并且索引3的"a"的前缀和后缀共有字符串的最大长度为0
		//此时索引2的"c"的的前缀和后缀共有字符串的最大长度为-1，表示str2字符串已经无法继续往后推了
		//str1继续下一个字符，也就是"t"的下一个字符继续让str2进行匹配
		//第三次匹配时，
		//str1 = "a  b  c  a  b  c  t  a  b  c  a  b  c  z"
		//索引     0  1  2  3  4  5  6  7  8  9 10 11 12 13
		//str2 =                      "a  b  c  a  b  c  z"
		//索引                          0  1  2  3  4  5  6
		while (x < str1.length && y < str2.length) {
			//情况1：只有str2的第一个字符匹配到str1的第一个字符时，同时移动到下一个字符
			if (str1[x] == str2[y]) {
				x++;
				y++;
			}
			//情况2：
			//如果next[]数组，也就是前缀和后缀共有字符串的最大长度为-1时，表示str2已经无法继续往前推了
			//需要str1字符串继续下一个字符重新进行匹配
			else if (next[y] == -1) {
				x++;
			}
			//情况3：
			//此时str2的字符没有匹配到str1的字符，str2也可以继续往后推，y来到下一个可以继续往前推的位置
			else {
				y = next[y];
			}
		}
		//退出while循环时，有两种情况：
		//1)x越界了，如果x越界，说明y没有越界，从头到尾str1字符串都没有完全匹配到str2字符串
		//2)y越界了，如果y越界，说明str1字符串完全匹配上str2字符串，x - y就是str1匹配到str2的起始位置
		return y == str2.length ? x - y : -1;
	}

	/**
	 * 时间复杂度：O(M)
	 *
	 * 因为在while循环 中，3种情况只会进其中一种
	 * 所以时间复杂度跟循环次数有关，所以时间复杂度为：O(M)，M表示准备匹配的字符串的长度
	 *
	 * //index的范围最大到M， index - cn的 范围最大到M
	 * while (index < next.length) {
	 *     //index的值增加，index-cn的值不变
	 * 	   if (str[index - 1] == str[cn]) {
	 * 	   	   next[index++] = ++cn;
	 *     }
	 *     //index的值不变，index-cn的值增加
	 * 	   else if (cn > 0) {
	 * 	   	   cn = next[cn];
	 *     }
	 *     //index的值增加，index-cn的值增加
	 * 	   else {
	 * 	   	   next[index++] = 0;
	 *     }
	 * }
	 * @param str 字符串
	 * @return 返回字符串的next数组
	 */
	private static int[] getNextArray(char[] str) {
		if (str.length == 1) {
			return  new int[] {-1};
		}
		//创建next数组
		int[] next = new int[str.length];
		//next数组中0位置的前缀和后缀最大共有字符串长度为-1
		next[0] = -1;
		//next数组中0位置的前缀和后缀最大共有字符串长度为0
		next[1] = 0;
		//对应next数组的索引，索引0和1的值已经确定了，从 2开始
		int index = 2;
		//cn是索引，表示和index - 1索引上的值进行比较，默认是0，因为此时index = 2，只有索引0和1进行比较
		//cn在索引0的位置上
		int cn = 0;
		//举例：
		//next[]数组
		//索引：[0 ~ 6]  7(cn) [12 ~ 18] 19(index - 1) 20(index)
		//值：                                7
		//cn索引上的字符和index-1索引上的字符进行比较，判断是否相等
		//1.如果相等，index索引上的值：前缀和后缀最大共有字符串的长度就是cn + 1
		//2.如果不相等，并且cn大于0，也就是cn没有越界，cn就继续往前推
		//3.如果cn不能继续往前推了，并且cn索引上的字符和index-1索引上的字符不相等
		//那么index索引上的值：前缀和后缀最大共有字符串的长度就是0
		while (index < next.length) {
			//cn索引上的值和index - 1索引上的值进行比较，如果相等，index = cn + 1
			if (str[index - 1] == str[cn]) {
				// next[index++] = cn + 1;
				// cn++;
				next[index++] = ++cn;
			}
			//如果cn索引上的值和index - 1索引上的值不相等，并且cn大于0，继续往前推
			else if (cn > 0) {
				cn = next[cn];
			}
			//如果上述两个条件都不成立，证明cn不能继续往前推了，当前index == 0就表示前置和后缀最大共有字符串的长度为0
			else {
				next[index++] = 0;
			}
		}
		return next;
	}



	/**
	 * 暴力匹配算法
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int violenceMatch(String str1, String str2) {
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();

		int s1Length = s1.length;
		int s2Length = s2.length;

		int i = 0;//指向s1
		int j = 0;//指向s2

		//防止越界
		while (i < s1Length && j < s2Length) {
			//每匹配到一个字符就继续往后遍历
			if (s1[i] == s2[j]) {
				i++;
				j++;
			} else {
				//没有匹配到，i就回退到原来的位置
				i = i - (j - 1);
				//j回退到起始位置
				j = 0;
			}
		}

		//说明两个字符串完全遍历完毕
		if (j == s2Length) {
			return i - j;
		}else {
			return -1;
		}
	}
}
