package com.mashibing.dynamic;

/**
 * 规定：1对应A，2对应B，3对应C，依此类推，26对应Z
 * 那么一个数字字符串：比如111就可以转化为AAA KA Ak
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 *
 * @author xcy
 * @date 2022/5/8 - 11:13
 */
public class ConvertToLetterString {
	public static void main(String[] args) {
		String string = "1473441241213";
		int count1 = convertStringMode(string);
		int count2 = convertStringModeWithCache(string);
		int count3 = coreLogicWithTable(string);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
	}

	/**
	 * 将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串，返回转换的方式 --> 使用暴力递归的方式
	 * 举例："111" -> "AAA" 或者 "KA" 或者 "AK"，那么转换的方式有3种
	 *
	 * @param string 都是int类型字符组成的字符串
	 * @return 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方式的个数
	 */
	public static int convertStringMode(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		return process(string.toCharArray(), 0);
	}

	/**
	 * 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方式的个数
	 *
	 * @param chars 字符串转换的字符数组
	 * @param i     对应的索引
	 * @return 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方法的个数
	 */
	public static int process(char[] chars, int i) {
		//如果i越界了，那么返回空串""，也是一种方式
		if (i == chars.length) {
			return 1;
		}
		//如果当前字符是'0'，因为字符0无法转换为英文字符，所以返回0种方法
		if (chars[i] == '0') {
			return 0;
		}
		//当前字符不是'0'
		//情况一：按照一个字符进行转换
		int situation = process(chars, i + 1);
		//情况二：按照两个字符进行转换
		//如果还有第二个字符，并且第一个字符 * 10 + 第二个字符的和 < 27
		if (i + 1 < chars.length && (chars[i] - '0') * 10 + (chars[i + 1] - '0') < 27) {
			situation += process(chars, i + 2);
		}
		return situation;
	}


	/**
	 * 将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串，返回转换的方式 --> 使用缓存的方式
	 * 举例："111" -> "AAA" 或者 "KA" 或者 "AK"，那么转换的方式有3种
	 *
	 * @param string 都是int类型字符组成的字符串
	 * @return 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方式的个数
	 */
	public static int convertStringModeWithCache(String string) {
		if (string == null || string.length() == 0) {
			return 0;
		}
		char[] chars = string.toCharArray();
		int[] cache = new int[chars.length + 1];
		return coreLogicWithCache(chars, 0, cache);
	}

	/**
	 * 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方式的个数
	 *
	 * @param chars 字符串转换的字符数组
	 * @param index 对应的索引
	 * @return 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方法的个数
	 */
	public static int coreLogicWithCache(char[] chars, int index, int[] cache) {
		if (cache[index] != 0) {
			return cache[index];
		}
		int ans = 0;
		//如果i越界了，那么返回空串""，也是一种方式
		if (index == chars.length) {
			ans = 1;
		}
		//如果当前字符是'0'，因为字符0无法转换为英文字符，所以返回0种方法
		else if (chars[index] != '0') {
			//当前字符不是'0'
			//情况一：按照一个字符进行转换
			int situation = coreLogicWithCache(chars, index + 1, cache);
			//情况二：按照两个字符进行转换
			//如果还有第二个字符，并且第一个字符 * 10 + 第二个字符的和 < 27
			if (index + 1 < chars.length && (chars[index] - '0') * 10 + (chars[index + 1] - '0') < 27) {
				situation += coreLogicWithCache(chars, index + 2, cache);
			}
			ans = situation;
			cache[index] = ans;
		}
		return ans;
	}

	/**
	 * 从右往左进行动态规划
	 * 返回将一个都是int类型字符的字符串转换为一个都是英文字符组成并且连续的字符串的方式的个数 --> 使用动态规划的方式
	 * @param str str只含有数字字符0~9
	 * @return 返回多少种转化方案
	 */
	public static int coreLogicWithTable(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] chars = str.toCharArray();
		int[] table = new int[chars.length + 1];
		//if (i == chars.length) {
		//	return 1;
		//}
		table[chars.length] = 1;
		//为什么从右往左遍历，因为i位置上依赖i+1和i+2位置上的结果
		for (int i = chars.length - 1; i >= 0; i--) {
			//int ways = process1(chars, i + 1);
			int ways = table[i + 1];
			if (i + 1 < chars.length && (chars[i] - '0') * 10 + chars[i + 1] - '0' < 27) {
				//ways += process(chars, i + 2);
				ways += table[ i + 2];
			}
			//return ways;
			table[i] = ways;
		}

		//return process1(str.toCharArray(), 0);
		return table[0];
	}


}
