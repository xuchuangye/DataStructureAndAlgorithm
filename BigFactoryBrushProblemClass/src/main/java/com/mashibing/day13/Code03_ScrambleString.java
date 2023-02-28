package com.mashibing.day13;

/**
 * 旋变字符串
 * 使用下面描述的算法可以扰乱字符串s得到字符串t：
 * 如果字符串的长度为1，算法停止
 * 如果字符串的长度 > 1，执行下述步骤：
 * 在一个随机下标处将字符串分割成两个非空的子字符串。
 * 即，如果已知字符串s，则可以将其分成两个子字符串x和y，且满足s = x + y 。
 * 随机决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。
 * 即，在执行这一步骤之后，s可能是s = x + y或者s = y + x 。
 * 在x和y这两个子字符串上继续从步骤1开始递归执行此算法。
 * 给你两个长度相等的字符串s1和s2，判断s2是否是s1的扰乱字符串。如果是，返回true ；否则，返回false。
 * <p>
 * 数据规模：
 * s1.length == s2.length
 * 1 <= s1.length <= 30
 * s1 和 s2 由小写英文字母组成
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/scramble-string/
 *
 * @author xcy
 * @date 2022/8/1 - 15:02
 */
public class Code03_ScrambleString {
	public static void main(String[] args) {
		String s1 = "abafacde";
		String s2 = "cdbafaae";
		boolean scrambleString = isScramble(s1, s2);
		boolean scrambleWithCache = isScrambleWithCache(s1, s2);
		System.out.println(scrambleString);
		System.out.println(scrambleWithCache);
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param s1 s1的字符串
	 * @param s2 s2的字符串
	 * @return 判断s2是否是s1的扰乱字符串，如果是返回true，否则返回false
	 */
	public static boolean isScramble(String s1, String s2) {
		if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
			return false;
		}
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1.equals(s2)) {
			return true;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		if (isSameCharSameCount(str1, str2)) {
			return true;
		}
		return process(str1, 0, str1.length - 1, str2, 0, str2.length - 1);
	}

	/**
	 * @param str1 str1字符串
	 * @param L1   L1
	 * @param R1   R1
	 * @param str2 str2字符串
	 * @param L2   L2
	 * @param R2   R2
	 * @return 返回str1[]L1 ~ R1范围内的字符串对应str2[]L2 ~ R2范围内的字符串是否是扰乱字符串
	 */
	public static boolean process(char[] str1, int L1, int R1, char[] str2, int L2, int R2) {
		//如果str1和str2各自只有一个字符
		if (L1 == R1) {
			return str1[L1] == str2[L2];
		}
		//如果str1和str2各自字符大于1
		for (int leftEnd = L1; leftEnd < R1; leftEnd++) {
			//情况1：str1的左部分对应str2的左部分，str1的右部分对应str2的右部分
			//str1左部分：L1  ~ leftEnd
			//对应
			//str2左部分：L2 ~ L2 + (leftEnd - L1)
			//str1右部分：leftEnd + 1 ~ R1
			//对应
			//str2右部分：L2 + (leftEnd - L1) + 1 ~ R2
			boolean situation1 = process(str1, L1, leftEnd, str2, L2, L2 + (leftEnd - L1))
					&& process(str1, leftEnd + 1, R1, str2, L2 + (leftEnd - L1) + 1, R2);
			//情况2：str1的左部分对应str2的右部分，str1的右部分对应str2的左部分
			//str1左部分：L1  ~ leftEnd
			//对应
			//str2右部分：R2 - (leftEnd - L1) ~ R2
			//str1右部分：leftEnd + 1 ~ R1
			//对应
			//str2左部分：L2 ~ R2 - (leftEnd - L1) - 1
			boolean situation2 = process(str1, L1, leftEnd, str2, R2 - (leftEnd - L1), R2)
					&& process(str1, leftEnd + 1, R1, str2, L2, R2 - (leftEnd - L1) - 1);
			//只要满足其中一种情况，直接返回true
			if (situation1 || situation2) {
				return true;
			}
		}
		//上述情况都不满足，返回false
		return false;
	}

	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param s1 s1的字符串
	 * @param s2 s2的字符串
	 * @return 判断s2是否是s1的扰乱字符串，如果是返回true，否则返回false
	 */
	public static boolean isScrambleWithCache(String s1, String s2) {
		if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
			return false;
		}
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1.equals(s2)) {
			return true;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		if (isSameCharSameCount(str1, str2)) {
			return true;
		}
		int N = str1.length;
		int[][][] dp = new int[N][N][N + 1];
		return process1(str1, str2, 0, 0, N, dp);
	}

	/**
	 * @param str1 str1字符串
	 * @param str2 str2字符串
	 * @param L1   L1
	 * @param L2   L2
	 * @param N    N的长度
	 * @param dp   傻缓存
	 * @return
	 */
	public static boolean process1(char[] str1, char[] str2, int L1, int L2, int N, int[][][] dp) {
		if (dp[L1][L2][N] != 0) {
			return dp[L1][L2][N] == 1;
		}
		boolean ans = false;
		//str1和str2的字符串长度为1
		if (N == 1) {
			ans = str1[L1] == str2[L2];
		}
		////str1和str2的字符串长度不为1
		else {
			for (int leftEnd = 1; leftEnd < N; leftEnd++) {
				//str1的左部分对应str2的左部分
				//str1从L1开始，str2从L2开始，长度为leftEnd
				boolean situation1 = process1(str1, str2, L1, L2, leftEnd, dp)
						//str1的右部分对应str2的右部分
						//str1从leftEnd + L1开始，str2从leftEnd + L2开始，长度为N - leftEnd
						&& process1(str1, str2, leftEnd + L1, leftEnd + L2, N - leftEnd, dp);
				//str1的左部分对应str2的右部分
				//str1从L1开始，str2从L2 + (N - leftEnd)开始，长度为leftEnd
				boolean situation2 = process1(str1, str2, L1, L2 + (N - leftEnd), leftEnd, dp)
						//str1的右部分对应str2的左部分
						//str1从L1 + leftEnd开始，str2从L2开始，长度为N - leftEnd
						&& process1(str1, str2, leftEnd + L1, L2, N - leftEnd, dp);
				if (situation1 || situation2) {
					ans = true;
					break;
				}
			}
		}
		dp[L1][L2][N] = ans ? 1 : -1;
		return ans;
	}

	/**
	 * 校验两个字符串的字符种类以及字符个数是否都相等
	 *
	 * @param str1 str1字符串
	 * @param str2 str2字符串
	 * @return 如果都相等，直接返回true，否则返回false
	 */
	public static boolean isSameCharSameCount(char[] str1, char[] str2) {
		//长度必须一样，否则返回false
		if (str1.length != str2.length) {
			return false;
		}
		//ASCII码
		int[] count = new int[256];
		//统计str1的字符种类以及字符个数
		for (char c : str1) {
			//++
			count[c]++;
		}
		//统计str2的字符种类以及字符个数
		for (char c : str2) {
			//--
			count[c]--;
		}
		//只要有一个字符种类或者字符个数不一样，直接返回false
		for (int i : count) {
			if (i != 0) {
				return false;
			}
		}
		return true;
	}
}
