package com.mashibing;

/**
 * @author xcy
 * @date 2022/8/3 - 8:14
 */
public class InterviewQuestions2 {
	public static void main(String[] args) {

	}

	public static boolean isMatch(String s1, String s2) {
		if (s1 == null && s2 != null || s1 != null && s2 == null) {
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
		if (!isSameCharSameCount(str1, str2)) {
			return false;
		}
		return process(str1, 0, str1.length - 1, str2, 0, str2.length - 1);
	}

	public static boolean process(char[] str1, int L1, int R1, char[] str2, int L2, int R2) {
		if (L1 == R1) {
			return str1[L1] == str2[L2];
		}
		for (int leftEnd = L1; leftEnd < R1; leftEnd++) {
			//str1 = ""
			//L1      leftEnd      R1
			//3         9
			//str2 = ""
			//L2                   R2
			//14        20
			boolean situation1 = process(str1, L1, leftEnd, str2, L2, L2 + (leftEnd - L1))
					&& process(str1, leftEnd + 1, R1, str2, L2 + (leftEnd - L1) + 1, R2);
			//str1 = ""
			//L1      leftEnd      R1
			//3         9
			//str2 = ""
			//L2                   R2
			//           14        20
			boolean situation2 = process(str1, L1, leftEnd, str2, R2 - (leftEnd - L1), R2)
					&& process(str1, leftEnd + 1, R1, str2, L2, R2 - (leftEnd - L1) - 1);
			if (situation1 || situation2) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMatchWithCache(String s1, String s2) {
		if (s1 == null && s2 != null || s1 != null && s2 == null) {
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
		if (!isSameCharSameCount(str1, str2)) {
			return false;
		}
		int N = str1.length;
		int[][][] dp = new int[N][N][N + 1];
		return process2(str1, str2, 0, 0, str2.length - 1, dp);
	}

	public static boolean process2(char[] str1, char[] str2, int L1, int L2, int N, int[][][] dp) {
		if (dp[L1][L2][N] != 0) {
			return dp[L1][L2][N] == 1;
		}
		boolean ans = false;
		if (N == 1) {
			ans = str1[L1] == str2[L2];
		} else {
			for (int leftEnd = 1; leftEnd < N; leftEnd++) {
				//str1 = ""
				//L1      leftEnd      R1
				//3         9
				//str2 = ""
				//L2                   R2
				//14        20
				boolean situation1 = process2(str1, str2, L1, L2, leftEnd, dp)
						&& process2(str1, str2, leftEnd + L1, leftEnd + L2, N - leftEnd, dp);
				//str1 = ""
				//L1      leftEnd      R1
				//3         9
				//str2 = ""
				//L2                   R2
				//           14        20
				boolean situation2 = process2(str1, str2, L1, L2 + (N - leftEnd), leftEnd, dp)
						&& process2(str1, str2, leftEnd + L1, L2, N - leftEnd, dp);
				if (situation1 || situation2) {
					ans = true;
					break;
				}
			}
		}
		dp[L1][L2][N] = ans ? 1 : -1;
		return ans;
	}

	public static boolean isSameCharSameCount(char[] str1, char[] str2) {
		if (str1.length != str2.length) {
			return false;
		}
		int[] count = new int[256];
		for (char c : str1) {
			count[c]++;
		}
		for (char c : str2) {
			count[c]--;
		}
		for (int i : count) {
			if (i != 0) {
				return false;
			}
		}
		return true;
	}
}
