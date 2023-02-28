package com.mashibing.day03;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 题目七：
 * 电子游戏“辐射4”中，任务“通向自由”要求玩家到达名为“Freedom Trail Ring”的金属表盘，并使用表盘拼写特定关键词才能开门
 * 给定一个字符串 ring，表示刻在外环上的编码；给定另一个字符串 key，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数
 * 最初，ring 的第一个字符与12:00方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，以此逐个拼写完 key 中的所有字符
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 * 您可以将 ring 顺时针或逆时针旋转一个位置，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 * <p>
 * Leetcode测试链接：
 * https://leetcode.com/problems/freedom-trail/
 *
 * @author xcy
 * @date 2022/7/13 - 8:34
 */
public class Code07_FreedomTrail {
	public static void main(String[] args) {
		String ring = "afaxfcfaxcfacf";
		String key = "afc";
		int cost1 = findRotateSteps(ring, key);
		int cost2 = findRotateStepsWithCache(ring, key);
		System.out.println(cost1);
		System.out.println(cost2);
	}

	/**
	 * 使用暴力递归的方式
	 *
	 * @param ring 主字符串，也就是电话
	 * @param key  需要拼接的字符串，需要在电话中拨(旋转)按钮和确认按钮
	 * @return
	 */
	public static int findRotateSteps(String ring, String key) {
		if (ring == null || key == null || ring.length() == 0 || key.length() < 1) {
			return 0;
		}
		//记录字符串的每一个字符出现的位置信息
		HashMap<Character, ArrayList<Integer>> hashMap = new HashMap<>();
		char[] str = ring.toCharArray();
		for (int i = 0; i < ring.length(); i++) {
			if (!hashMap.containsKey(str[i])) {
				hashMap.put(str[i], new ArrayList<>());
			}
			hashMap.get(str[i]).add(i);
		}
		char[] keyChars = key.toCharArray();
		return coreLogic(0, 0, keyChars, hashMap, ring.length());
	}

	/**
	 * @param preButton 在电话中，当前指针指着的按钮的上一个按钮
	 * @param index     在目标中，当前需要确认的字符的索引
	 * @param str       需要按键拼接的字符串的字符数组，参数固定
	 * @param hashMap   电话本，记录主字符串每个字符出现的所有位置，参数固定
	 * @param N         电话的按键个数，参数固定
	 * @return
	 */
	public static int coreLogic(int preButton, int index, char[] str, HashMap<Character, ArrayList<Integer>> hashMap, int N) {
		//已经来到需要按键拼接的字符串之后的位置，表示需要拼接的字符串已经使用按键拼接完了，不需要继续操作，返回0
		if (index == str.length) {
			return 0;
		}
		//需要拼接的字符串还没有拼接完
		//当前字符
		char cur = str[index];
		//当前字符的所有可选位置，根据位置决定是顺序播，还是逆序播
		ArrayList<Integer> nextPositions = hashMap.get(cur);

		int ans = Integer.MAX_VALUE;
		for (Integer next : nextPositions) {
			//当前字符使用电话按钮拨(旋转)的代价和使用电话按钮确认的代价 + 下一个字符使用电话按钮拨和使用电话按钮确认的代价
			//1. dialThePhone(preButton, next, N)表示当前字符使用电话按钮拨(旋转)的代价
			//2. +1表示电话按钮按下去的代价
			//3. coreLogic(next, index + 1, str, hashMap, N)表示下一个字符使用电话按钮的代价
			int cost = dialThePhone(preButton, next, N) + 1 + coreLogic(next, index + 1, str, hashMap, N);
			ans = Math.min(ans, cost);
		}
		return ans;
	}


	/**
	 * 使用暴力递归 + 傻缓存的方式
	 *
	 * @param ring 主字符串，也就是电话
	 * @param key  需要拼接的字符串，需要在电话中拨(旋转)按钮和确认按钮
	 * @return
	 */
	public static int findRotateStepsWithCache(String ring, String key) {
		if (ring == null || key == null || ring.length() == 0 || key.length() < 1) {
			return 0;
		}
		//记录字符串的每一个字符出现的位置信息
		HashMap<Character, ArrayList<Integer>> hashMap = new HashMap<>();
		char[] string = ring.toCharArray();
		//电话的按钮个数
		int N = string.length;
		//循环遍历主字符串的每一个字符
		for (int i = 0; i < N; i++) {
			//如果该字符的位置信息没有创建，创建位置信息列表
			if (!hashMap.containsKey(string[i])) {
				hashMap.put(string[i], new ArrayList<>());
			}
			//如果该字符的位置信息创建过了，将当前字符的位置信息添加到列表中
			hashMap.get(string[i]).add(i);
		}
		char[] str = key.toCharArray();
		int M = str.length;

		int[][] dp = new int[N + 1][M + 1];

		//如果暴力递归的BaseCase返回值是0，最好先初始化一下dp[][]
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//M = str.length时,return 0，所以 j == M这一行可以不需要初始化
				dp[i][j] = -1;
			}
		}
		return coreLogicWithCache(0, 0, str, hashMap, N, dp);
	}

	/**
	 * @param preButton 在电话中，当前指针指着的按钮的上一个按钮
	 * @param index     在目标中，当前需要确认的字符的索引
	 * @param str       需要按键拼接的字符串的字符数组，参数固定
	 * @param hashMap   电话本，记录主字符串每个字符出现的所有位置，参数固定
	 * @param N         电话的按键个数，参数固定
	 * @return
	 */
	public static int coreLogicWithCache(int preButton, int index, char[] str, HashMap<Character, ArrayList<Integer>> hashMap, int N, int[][] dp) {
		//缓存命中
		if (dp[preButton][index] != -1) {
			return dp[preButton][index];
		}
		int ans = Integer.MAX_VALUE;
		//已经来到需要按键拼接的字符串之后的位置，表示需要拼接的字符串已经使用按键拼接完了，不需要继续操作，返回0
		if (index == str.length) {
			ans = 0;
		}
		//需要拼接的字符串还没有拼接完
		//当前字符
		char cur = str[index];
		//当前字符的所有可选位置，根据位置决定是顺序播，还是逆序播
		ArrayList<Integer> nextPositions = hashMap.get(cur);

		for (Integer next : nextPositions) {
			//当前字符使用电话按钮拨(旋转)的代价和使用电话按钮确认的代价 + 下一个字符使用电话按钮拨和使用电话按钮确认的代价
			//1. dialThePhone(preButton, next, N)表示当前字符使用电话按钮拨(旋转)的代价
			//2. +1表示电话按钮按下去的代价
			//3. coreLogic(next, index + 1, str, hashMap, N)表示下一个字符使用电话按钮的代价
			int cost = dialThePhone(preButton, next, N) + 1 + coreLogicWithCache(next, index + 1, str, hashMap, N, dp);
			ans = Math.min(ans, cost);
		}
		dp[preButton][index] = ans;
		return ans;
	}

	/**
	 * 拨电话的代价
	 *
	 * @param position1
	 * @param position2
	 * @param size
	 * @return
	 */
	public static int dialThePhone(int position1, int position2, int size) {
		return Math.min(Math.abs(position1 - position2), Math.min(position1, position2) + size - Math.max(position1, position2));
	}
}
