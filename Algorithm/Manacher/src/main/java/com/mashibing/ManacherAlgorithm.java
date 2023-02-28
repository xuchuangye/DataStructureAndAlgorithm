package com.mashibing;

import com.mashibing.common.ManacherUtils;

import java.util.Arrays;

/**
 * Manacher算法
 * <p>
 * 假设字符串str长度为N，想返回最长回文子串的长度
 * <p>
 * 要求：
 * 时间复杂度O(N)
 * <p>
 * Manacher算法的核心
 * 1）理解回文半径数组
 * 2）理解所有中心的回文最右边界R，和取得R时的中心点C
 * 3）理解   L…(i`)…C…(i)…R  的结构，以及根据i’回文长度进行的状况划分
 * 4）每一种情况划分，都可以加速求解i回文半径的过程
 *
 * @author xcy
 * @date 2022/5/21 - 8:53
 */
public class ManacherAlgorithm {
	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int testTimes = 5000000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			String str = ManacherUtils.getRandomString(possibilities, strSize);
			if (manacher(str) != ManacherUtils.right(str)) {
				System.err.println("测试出错！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * Manacher算法
	 * <p>
	 * Manacher算法的时间复杂度：O(N)
	 * 1.对于每一个位置为中心往两边扩，扩失败的次数必然会是1次，或者边界或者两边字符不相等，N个位置，失败N次
	 * 2.对于每一个位置为中心往两边扩，必然会更新最右回文边界R，R值增加，扩成功的次数
	 * 情况1的时间复杂度：O(1)
	 * 以i为回文中心的回文不需要往两边扩，所以时间复杂度：O(1)
	 * 情况2的时间复杂度：O(1)
	 * 以i为回文中心的回文不需要往两边扩，所以时间复杂度：O(1)
	 * 情况3的时间复杂度：O(N)
	 * x[i - R]y，i - R 范围上不需要分析，从R的范围外开始分析，也就是y，扩失败就1次，扩成功R值增加
	 * R的范围0 - N，所以时间复杂度：O(N)
	 * <p>
	 * 两种大的思路分析：
	 * 一.当前索引i没有被最右回文边界R扩住了
	 * 暴力扩，没有优化
	 * <p>
	 * 二.当前索引i被最右回文边界R扩住了
	 * i表示当前索引
	 * i'表示与当前索引i对称的索引
	 * R表示最右回文边界
	 * C表示以最右回文边界R的回文的回文中心
	 * L表示与最右回文边界R对称的索引
	 * [                  ]
	 * L   i'   C    i    R
	 * <p>
	 * 第二种思路分析的3种情况：
	 * 情况1)
	 * 以i'为回文中心的回文范围在L - R的范围内 ---> O(1)
	 * [   (     )       (     )   ]
	 * L      i'     C      i      R
	 * 举例：
	 * 带特殊字符#的字符串：
	 * a            b           x            y
	 * [a, b, (c, d, c) k, s, t, s, k, (c, d, c) b, a]
	 * L          i'             C         i         R
	 * 证明：
	 * 1)i'回文的左边a字符必然和右边b字符不相等
	 * 2)x字符和b字符对称，y字符和a字符对称
	 * 3)所以以i为回文中心的回文必然和以i'为回文中心的回文一样
	 * 总结：以i为回文中心的回文范围就是以i'为回文中心的回文
	 * <p>
	 * 情况2)
	 * 以i'为回文中心的回文范围有一部分在L - R的范围内 ---> O(1)
	 * ( [           )              ]
	 * L       i'     C      i      R
	 * 举例：
	 * 带特殊字符#的字符串：
	 * (a, b, [c, d, e, d, c, b, a) t, s, t, a, b,(c, d, e, d, c)], f
	 * L      i'                C                 i      R
	 * 总结：以i为回文中心的回文范围就是回文半径为i - R的回文
	 * <p>
	 * 情况3)以i'为回文中心的回文的左边界和L在同一个位置，也就是压线
	 * [            )              ]
	 * L      i'     C      i      R
	 * 举例：
	 * 带特殊字符#的字符串：
	 * a                b     x                 y
	 * .[a, b, c, b, a) s, t, s,(a, b, c, b, a] .
	 * L      i'          C           i      R
	 * 证明：需要判断索引x上的字符是否等于索引y上的字符，如果相等，以i为回文中心的回文还能继续往两边扩
	 * 总结：以i为回文中心的回文范围至少是i'为回文中心的回文这么大，但是可能更大
	 *
	 * @param string 原始字符串
	 * @return 返回字符串中最长回文子串的长度
	 */
	public static int manacher(String string) {
		//1.判断边界问题
		if (string == null || string.length() == 0) {
			return 0;
		}
		//2.处理字符串，也就是在第0个字符左边和 第string.length个字符右边添加特殊字符，在剩余字符左右两边添加特殊字符
		char[] str = manacherCharArray(string);
		//3.准备回文半径数组、最右回文边界的下一个位置、回文中心
		int[] pArr = new int[str.length];
		//分析时，R表示最右回文边界，在代码中，R表示最右回文边界的下一个位置，因为可以省去很多的判断条件
		int R = -1;
		//表示回文的回文中心
		int C = -1;
		//最大回文子串长度
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < str.length; i++) {
			//R > i表示i在R的内部，此时的R表示最右回文边界的下一个位置
			//所以R == i就表示以i为回文中心的回文范围在R的外部了

			//pArr[2 * C - i]表示以i'为回文中心的回文，也就是在i对称的位置的回文
			//举例:
			//索引1 2 3  4 5 6  7 8 9
			//       i'   C   i
			//i' = 2 * C - i --> 2 * 5 - 7 = 3;
			//Math.min(2 * C - i, R - i)表示i'的回文半径和R - i的距离谁的值小，谁就是不需要验证回文半径的区域
			//如果i'的回文半径大，说明i'的回文范围已经超出了L - R的范围
			//如果R - i的距离大，说明i'的回文范围在L - R的范围内
			//如果两个值一样，说明i'的回文左边界在L上，也就是压线

			//1表示i在R的外部，那么以i为回文中心的回文长度就只有索引i自己，所以回文半径是1
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

			//i + pArr[i] < str.length && i - pArr[i] > -1表示以i为回文中心的回文既能够往左扩，也能够往右扩
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				//以i为回文中心的回文往左扩的字符 == 以i为回文中心的回文往右扩的字符
				//说明回文扩成功了，当前索引i的回文半径++
				if (str[i + pArr[i]] == str[i - pArr[i]]) {
					pArr[i]++;
				}
				//其余任何情况都退出循环
				else {
					break;
				}
			}
			//i + 回文半径的长度大于R，也就是超过了R，更新最右回文边界R和回文中心C
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			max = Math.max(pArr[i], max);
		}
		//
		return max - 1;
	}

	/**
	 * @param string 原始字符串
	 * @return 返回处理之后的字符串对应的字符数组
	 */
	public static char[] manacherCharArray(String string) {
		/*char[] chars = string.toCharArray();
		char[] res = new char[string.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : chars[index++];
		}
		System.out.println(Arrays.toString(res));
		return res;*/
		char[] chars = string.toCharArray();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			if (i != 0) {
				stringBuilder.append(chars[i]);
			}
			stringBuilder.append("#");
		}
		return stringBuilder.toString().toCharArray();
	}

	/**
	 * @param string 原始字符串
	 * @return 返回主字符串中的最长回文子串
	 */
	public static String manacherReturnLongestPalindromicSubstring(String string) {
		//1.判断边界问题
		if (string == null || string.length() == 0) {
			return null;
		}
		//2.处理字符串，也就是在第0个字符左边和 第string.length个字符右边添加特殊字符，在剩余字符左右两边添加特殊字符
		char[] str = manacherCharArray(string);
		//3.准备回文半径数组、最右回文边界的下一个位置、回文中心
		int[] pArr = new int[string.length()];
		//分析时，R表示最右回文边界，在代码中，R表示最右回文边界的下一个位置，因为可以省去很多的判断条件
		int R = -1;
		//表示回文的回文中心
		int C = -1;
		//最大回文子串长度
		int max = Integer.MIN_VALUE;
		//最大回文子串的结束位置
		int end = -1;
		for (int i = 0; i < str.length; i++) {
			//R > i表示i在R的内部，此时的R表示最右回文边界的下一个位置
			//所以R == i就表示以i为回文中心的回文范围在R的外部了

			//pArr[2 * C - i]表示以i'为回文中心的回文，也就是在i对称的位置的回文
			//举例:
			//索引1 2 3  4 5 6  7 8 9
			//       i'   C   i
			//i' = 2 * C - i --> 2 * 5 - 7 = 3;
			//Math.min(2 * C - i, R - i)表示i'的回文半径和R - i的距离谁的值小，谁就是不需要验证回文半径的区域
			//如果i'的回文半径大，说明i'的回文范围已经超出了L - R的范围
			//如果R - i的距离大，说明i'的回文范围在L - R的范围内
			//如果两个值一样，说明i'的回文左边界在L上，也就是压线

			//1表示i在R的外部，那么以i为回文中心的回文长度就只有索引i自己，所以回文半径是1
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

			//i + pArr[i] < str.length && i - pArr[i] > -1表示以i为回文中心的回文既能够往左扩，也能够往右扩
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				//以i为回文中心的回文往左扩的字符 == 以i为回文中心的回文往右扩的字符
				//说明回文扩成功了，当前索引i的回文半径++
				if (str[i + pArr[i]] == str[i - pArr[i]]) {
					pArr[i]++;
				}
				//其余任何情况都退出循环
				else {
					break;
				}
			}
			//i + 回文半径的长度大于R，也就是超过了R，更新最右回文边界R和回文中心C
			if (i + pArr[i] > R) {
				end = R - 1;
				R = i + pArr[i];
				C = i;
			}
			max = Math.max(pArr[i], max);
			end = Math.max(pArr[i], end);
		}
		//举例：
		//1.原始字符串的字符个数为偶数
		//原始字符串：   1221  特殊字符的字符串：   # 1 # 2 # 2 # 1 #
		//原始字符串索引：0123 特殊字符的字符串索引：0 1 2 3 4 5 6 7 8
		//最大回文子串的结束位置 = (特殊字符串的结束位置 - 1) / 2
		//原始字符串也就是最大回文子串的结束位置 = （8 - 1）- 2 = 3
		//2.原始字符串的字符个数为奇数
		//原始字符串：   12321  特殊字符的字符串：   # 1 # 2 # 3 # 2 # 1 #
		//原始字符串索引：01234 特殊字符的字符串索引：0 1 2 3 4 5 6 7 8 9 10
		//最大回文子串的结束位置 = (特殊字符串的结束位置 - 1) / 2
		//原始字符串也就是最大回文子串的结束位置 = （10 - 1）- 2 = 4


		//最大回文子串的起始位置 = 最大回文子串的结束位置 - 最大回文子串的长度
		int startIndex = (end - 1) / 2 - (max - 1);
		return string.substring(startIndex, (end - 1) / 2);
	}
}
