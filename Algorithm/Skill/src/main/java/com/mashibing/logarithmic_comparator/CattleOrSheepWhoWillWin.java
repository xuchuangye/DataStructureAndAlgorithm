package com.mashibing.logarithmic_comparator;

/**
 * 题目二：
 * 给定一个正整数N，表示有N份青草统一堆放在仓库里
 * 有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
 * 不管是牛还是羊，每一轮能吃的草量必须是：
 * 1，4，16，64…(4的某次方)
 * 谁最先把草吃完，让对方没有草可以吃，谁就获胜
 * 假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定
 * 根据唯一的参数N，返回谁会赢
 * <p>
 * 思路分析：
 * 1.没有选择，因为先手会算出所有的后招，使用对自己最有利的去做，所以在博弈论的情况下是无选择的，一定会有一个唯一的胜利者
 *
 * @author xcy
 * @date 2022/6/2 - 17:28
 */
public class CattleOrSheepWhoWillWin {
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			System.out.println(cattleOrSheepWhoWillWinOptimal(i));
		}
	}

	/**
	 * @param N N份草
	 * @return 如果先手赢，返回字符串"先手"，如果后手赢，返回字符串"后手"
	 */
	public static String cattleOrSheepWhoWillWin(int N) {
		//草的数量：0 1 2 4 5
		//先手->牛:X √ X √ √
		//后手->羊:√ X √ X X
		if (N < 5) {
			return N == 2 || N == 0 ? "后手" : "先手";
		}

		//先手先从4的1次方 份草开始拿
		int want = 1;
		while (want <= N) {
			//如果后续操作的后手，也就是当前操作的先手赢了，那么就是先手赢了！
			//因为后续操作的后手就是当前操作的先手，这两个是同一个操作对象
			if ("后手".equals(cattleOrSheepWhoWillWin(N - want))) {
				return "先手";
			}
			if (want > (N >>> 2)) {
				break;
			} else {
				want <<= 2;
			}
		}
		return "后手";
	}

	/**
	 * 最优解
	 * <p>
	 * 时间复杂度：O(1)
	 * <p>
	 * 后手
	 * 先手
	 * 后手
	 * 先手
	 * 先手
	 * <p>
	 * 后手
	 * 先手
	 * 后手
	 * 先手
	 * 先手
	 * <p>
	 * 后手
	 * 先手
	 * 后手
	 * 先手
	 * 先手
	 *
	 * @param N
	 * @return
	 */
	public static String cattleOrSheepWhoWillWinOptimal(int N) {
		if (N % 5 == 0 || N % 5 == 2) {
			return "后手";
		} else {
			return "先手";
		}
	}

}
