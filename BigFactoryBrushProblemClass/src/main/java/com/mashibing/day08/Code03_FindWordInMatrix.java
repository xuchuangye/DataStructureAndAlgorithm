package com.mashibing.day08;

/**
 * 题目三：
 * 给定一个char[][] matrix，也就是char类型的二维数组，再给定一个字符串word，
 * 可以从任何一个某个位置出发，可以走上、下、左、右，能不能找到word？
 * char[][] m = {
 * -    { 'a', 'b', 'z' },
 * -    { 'c', 'd', 'o' },
 * -    { 'f', 'e', 'o' }
 * }
 * 设定1：可以走重复路的情况下，返回能不能找到
 * 比如，word = "zoooz"，是可以找到的，z -> o -> o -> o -> z，因为允许走一条路径中已经走过的字符
 * 设定2：不可以走重复路的情况下，返回能不能找到
 * 比如，word = "zoooz"，是不可以找到的，因为允许走一条路径中已经走过的字符不能重复走
 *
 * @author xcy
 * @date 2022/7/24 - 9:30
 */
public class Code03_FindWordInMatrix {
	public static void main(String[] args) {
		char[][] matrix = {
				{'a', 'b', 'z'},
				{'c', 'd', 'o'},
				{'f', 'e', 'o'}
		};
		String word = "zoooz";
		boolean canFindWord = isCanFindWord_CanRepeat_Recursion(matrix, word);
		boolean canFindWord2 = isCanFindWord_CanRepeat_Cache(matrix, word);
		boolean canFindWord3 = isCanFindWord_CanRepeat_DynamicProgramming(matrix, word);
		System.out.println(canFindWord);
		System.out.println(canFindWord2);
		System.out.println(canFindWord3);
		boolean canFindWord_noRepeat = isCanFindWord_NoRepeat_Recursion(matrix, word);
		System.out.println(canFindWord_noRepeat);
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param matrix 地图
	 * @param word   被找的字符串
	 * @return 返回地图上是否能够找到word
	 */
	public static boolean isCanFindWord_CanRepeat_Recursion(char[][] matrix, String word) {
		if (word == null || "".equals(word)) {
			return true;
		}
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		char[] str = word.toCharArray();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (canRepeat_Recursion(matrix, i, j, str, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param matrix 地图
	 * @param i      横坐标
	 * @param j      纵坐标
	 * @param str    需要被找到的字符串的字符数组
	 * @param k      字符数组的起始位置
	 * @return 返回在(i, j)位置出发，能否找到str[k ...]的字符串
	 */
	public static boolean canRepeat_Recursion(char[][] matrix, int i, int j, char[] str, int k) {
		//表示已经找到了所有的字符
		if (k == str.length) {
			return true;
		}
		//越界问题，以及matrix[i][j] != str[k]表示当前 (i, j)的起始位置就无法拼接原始字符串
		if (i == -1 || i == matrix.length || j == -1 || j == matrix[0].length || matrix[i][j] != str[k]) {
			return false;
		}

		boolean ans = false;
		if (canRepeat_Recursion(matrix, i + 1, j, str, k + 1) || canRepeat_Recursion(matrix, i - 1, j, str, k + 1)
				|| canRepeat_Recursion(matrix, i, j + 1, str, k + 1) || canRepeat_Recursion(matrix, i, j - 1, str, k + 1)) {
			ans = true;
		}
		return ans;
	}

	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param matrix 地图
	 * @param word   被找的字符串
	 * @return 返回地图上是否能够找到word
	 */
	public static boolean isCanFindWord_CanRepeat_Cache(char[][] matrix, String word) {
		if (word == null || "".equals(word)) {
			return true;
		}
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		char[] str = word.toCharArray();
		int P = str.length;
		boolean[][][] dp = new boolean[N][M][P];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (canRepeat_Cache(matrix, i, j, str, 0, dp)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param matrix 地图
	 * @param i      横坐标
	 * @param j      纵坐标
	 * @param str    需要被找到的字符串的字符数组
	 * @param k      字符数组的起始位置
	 * @param dp     傻缓存
	 * @return 返回在(i, j)位置出发，能否找到str[k ...]的字符串
	 */
	public static boolean canRepeat_Cache(char[][] matrix, int i, int j, char[] str, int k, boolean[][][] dp) {
		if (dp[i][j][k]) {
			return dp[i][j][k];
		}
		//表示已经找到了所有的字符
		if (k == str.length) {
			return true;
		}
		//越界问题，以及matrix[i][j] != str[k]表示当前 (i, j)的起始位置就无法拼接原始字符串
		if (i > matrix.length || j > matrix[0].length || matrix[i][j] != str[k]) {
			return false;
		}

		boolean ans = false;
		if (canRepeat_Recursion(matrix, i + 1, j, str, k + 1) || canRepeat_Recursion(matrix, i - 1, j, str, k + 1)
				|| canRepeat_Recursion(matrix, i, j + 1, str, k + 1) || canRepeat_Recursion(matrix, i, j - 1, str, k + 1)) {
			ans = true;
		}
		dp[i][j][k] = ans;
		return ans;
	}

	/**
	 * 使用暴力递归的方式
	 * //TODO: 待定
	 *
	 * @param matrix 地图
	 * @param word   被找的字符串
	 * @return 返回地图上是否能够找到word
	 */
	public static boolean isCanFindWord_CanRepeat_DynamicProgramming(char[][] matrix, String word) {
		if (word == null || "".equals(word)) {
			return true;
		}
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int N = matrix.length;
		int M = matrix[0].length;
		char[] str = word.toCharArray();
		int P = str.length;

		boolean[][][] dp = new boolean[N][M][P + 1];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				dp[i][j][P] = true;
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				for (int k = P - 1; k >= 0; k--) {
					boolean ans = false;
					if (dp[i + 1][j][k + 1] || dp[i][j + 1][k + 1]) {
						ans = true;
					}
					if (i > 0 && dp[i - 1][j][k + 1]) {
						ans = true;
					}
					if (j > 0 && dp[i][j - 1][k + 1]) {
						ans = true;
					}
					dp[i][j][k] = ans;
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (dp[i][j][0]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param matrix 地图
	 * @param word   需要被找到的字符串
	 * @return 返回在地图中，能否找到字符串
	 */
	public static boolean isCanFindWord_NoRepeat_Recursion(char[][] matrix, String word) {
		if (word == null || "".equals(word)) {
			return true;
		}
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}

		int N = matrix.length;
		int M = matrix[0].length;
		char[] str = word.toCharArray();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (noRepeat(matrix, i, j, str, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 不能使用动态规划，因为有四个参数，并且其中一个参数的参数类型是char[][]二维数组
	 *
	 * @param matrix 地图
	 * @param i      横坐标
	 * @param j      纵坐标
	 * @param str    需要被找到的字符串
	 * @param k      需要被找到的字符串的索引
	 * @return 返回在地图中，能否找到字符串
	 */
	public static boolean noRepeat(char[][] matrix, int i, int j, char[] str, int k) {
		if (k == str.length) {
			return true;
		}

		if (i == -1 || i == matrix.length || j == -1 || j == matrix[0].length || matrix[i][j] == 0 || matrix[i][j] != str[k]) {
			return false;
		}
		//记录当前i, j位置的字符
		char aChar = matrix[i][j];
		//将当前字符置为0
		matrix[i][j] = 0;

		boolean ans = false;
		if (noRepeat(matrix, i + 1, j, str, k + 1) || noRepeat(matrix, i - 1, j, str, k + 1)
				|| noRepeat(matrix, i, j + 1, str, k + 1) || noRepeat(matrix, i, j - 1, str, k + 1)) {
			ans = true;
		}
		//如果没有找到需要的字符，重新退回原来的位置继续进行递归
		matrix[i][j] = aChar;
		return ans;
	}

	/**
	 * 对noRepeat()的代码优化
	 *
	 * @param matrix 地图
	 * @param i      横坐标
	 * @param j      纵坐标
	 * @param str    需要被找到的字符串
	 * @param k      需要被找到的字符串的索引
	 * @return 返回在地图中，能否找到字符串
	 */
	public static boolean noRepeatOptimal(char[][] matrix, int i, int j, char[] str, int k) {
		if (k == str.length) {
			return true;
		}

		if (i == -1 || i == matrix.length || j == -1 || j == matrix[0].length || matrix[i][j] != str[k]) {
			return false;
		}
		//记录当前i, j位置的字符
		//将当前字符置为0
		matrix[i][j] = 0;

		boolean ans = false;
		if (noRepeatOptimal(matrix, i + 1, j, str, k + 1) || noRepeatOptimal(matrix, i - 1, j, str, k + 1)
				|| noRepeatOptimal(matrix, i, j + 1, str, k + 1) || noRepeatOptimal(matrix, i, j - 1, str, k + 1)) {
			ans = true;
		}
		//如果没有找到需要的字符，重新退回原来的位置继续进行递归
		matrix[i][j] = str[k];
		return ans;
	}
}
