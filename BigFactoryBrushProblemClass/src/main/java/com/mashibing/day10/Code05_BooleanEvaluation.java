package com.mashibing.day10;

/**
 * 题目五：
 * 给定一个布尔表达式和一个期望的布尔结果result，
 * 布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。
 * 实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。
 * <p>
 * 解题思路：
 * 1.字符串的长度一定为奇数
 * 2.偶数位置上都是0或者1
 * 3.奇数位置上都是符号
 * 4.不需要检查
 * <p>
 * Leetcode测试链接：
 * https://leetcode.cn/problems/boolean-evaluation-lcci/
 *
 * @author xcy
 * @date 2022/7/27 - 15:24
 */
public class Code05_BooleanEvaluation {
	public static void main(String[] args) {
		String str = "1^0|0|1";
		int result = 0;
		int count1 = countEval(str, result);
		int count2 = countEvalWithCache(str, result);
		int count3 = countEvalWithDynamicProgramming(str, result);
		System.out.println(count1);
		System.out.println(count2);
		System.out.println(count3);
	}

	public static class Info {
		/**
		 * 结果为true的方法数
		 */
		public int true_;
		/**
		 * 结果为false的方法数
		 */
		public int false_;

		public Info(int _true, int _false) {
			this.true_ = _true;
			this.false_ = _false;
		}
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param s
	 * @param result
	 * @return
	 */
	public static int countEval(String s, int result) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		return result == 0 ? process(str, 0, str.length - 1).false_ : process(str, 0, str.length - 1).true_;
	}

	public static Info process(char[] str, int L, int R) {
		int true_ = 0;
		int false_ = 0;
		//如果字符串只有一个字符
		if (L == R) {
			//注意点：一定要 == '1'
			true_ = str[L] == '1' ? 1 : 0;
			false_ = str[L] == '0' ? 1 : 0;
		}
		//否则字符串的长度必定大于等于3，因为至少要有一个逻辑符号
		else {
			//split表示逻辑符号
			for (int split = L + 1; split < R; split += 2) {
				Info leftInfo = process(str, L, split - 1);
				Info rightInfo = process(str, split + 1, R);
				int a = leftInfo.true_;//1
				int b = leftInfo.false_;//0
				int c = rightInfo.true_;//1
				int d = rightInfo.false_;//0

				//注意点：所有的可能性进行累加
				switch (str[split]) {
					case '&':
						//与：两个数只要有一个为0，结果即为0，都为1，结果才为1
						true_ += a * c;
						false_ += a * d + b * c + b * d;
						break;
					case '|':
						//或：两个数只要有一个为1，结果即为1，都为0，结果才为0
						true_ += a * c + a * d + b * c;
						false_ += b * d;
						break;
					case '^':
						//异或：两个数相同为0，不同为1
						true_ += a * d + b * c;
						false_ += a * c + b * d;
						break;
				}
			}
		}

		return new Info(true_, false_);
	}

	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param s
	 * @param result
	 * @return
	 */
	public static int countEvalWithCache(String s, int result) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		Info[][] dp = new Info[N][N];
		Info allInfo = processWithCache(str, 0, N - 1, dp);
		return result == 0 ? allInfo.false_ : allInfo.true_;
	}

	public static Info processWithCache(char[] str, int L, int R, Info[][] dp) {
		if (dp[L][R] != null) {
			return dp[L][R];
		}
		int true_ = 0;
		int false_ = 0;
		//如果字符串只有一个字符
		if (L == R) {
			//
			true_ = str[L] == '1' ? 1 : 0;
			false_ = str[L] == '0' ? 1 : 0;
		}
		//否则字符串的长度必定大于等于3，因为至少要有一个逻辑符号
		else {
			//split表示逻辑符号
			for (int split = L + 1; split < R; split += 2) {
				Info leftInfo = processWithCache(str, L, split - 1, dp);
				Info rightInfo = processWithCache(str, split + 1, R, dp);
				int a = leftInfo.true_;//1
				int b = leftInfo.false_;//0
				int c = rightInfo.true_;//1
				int d = rightInfo.false_;//0

				switch (str[split]) {
					case '&':
						//与：两个数只要有一个为0，结果即为0，都为1，结果才为1
						true_ += a * c;
						false_ += a * d + b * c + b * d;
						break;
					case '|':
						//或：两个数只要有一个为1，结果即为1，都为0，结果才为0
						true_ += a * c + a * d + b * c;
						false_ += b * d;
						break;
					case '^':
						//异或：两个数相同为0，不同为1
						true_ += a * d + b * c;
						false_ += a * c + b * d;
						break;
				}
			}
		}
		dp[L][R] = new Info(true_, false_);
		return dp[L][R];
	}

	/**
	 * 使用暴力递归 -> 动态规划的方式
	 *
	 * //TODO 待定
	 *
	 * @param s
	 * @param result
	 * @return
	 */
	public static int countEvalWithDynamicProgramming(String s, int result) {
		if (s == null || "".equals(s)) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		Info[][] dp = new Info[N][N];

		int true_ = 0;
		int false_ = 0;
		for (int L = 0; L < N; L++) {
			true_ = str[L] == '1' ? 1 : 0;
			false_ = str[L] == '0' ? 1 : 0;
			dp[L][L] = new Info(true_, false_);
		}
		for (int L = 0; L < N; L++) {
			for (int R = 0; R < N; R++) {
				//split表示逻辑符号
				for (int split = L + 1; split < R; split += 2) {
					Info leftInfo = dp[L][split - 1];
					Info rightInfo = dp[split + 1][R];
					int a = leftInfo.true_;//1
					int b = leftInfo.false_;//0
					int c = rightInfo.true_;//1
					int d = rightInfo.false_;//0

					//注意点：所有的可能性进行累加
					switch (str[split]) {
						case '&':
							//与：两个数只要有一个为0，结果即为0，都为1，结果才为1
							true_ += a * c;
							false_ += a * d + b * c + b * d;
							break;
						case '|':
							//或：两个数只要有一个为1，结果即为1，都为0，结果才为0
							true_ += a * c + a * d + b * c;
							false_ += b * d;
							break;
						case '^':
							//异或：两个数相同为0，不同为1
							true_ += a * d + b * c;
							false_ += a * c + b * d;
							break;
					}
				}
				dp[L][R] = new Info(true_, false_);
			}
		}
		return result == 0 ? dp[0][N - 1].false_ : dp[0][N - 1].true_;
	}
}
