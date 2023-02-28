package com.mashibing.dynamic;

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每一张牌
 * 规定：
 * 玩家A先拿玩家B后拿，但每个玩家每次只能拿最左或者最右的纸牌
 * 玩家A和玩家B都绝顶聪明，请返回最后获胜者的分数
 *
 * @author xcy
 * @date 2022/5/7 - 14:47
 */
public class PokerCardsInWinner {
	public static void main(String[] args) {
		int[] arr = {5, 7, 10, 8, 9, 11, 1};
		int result1 = returnWinnerFraction(arr);
		int result2 = returnWinnerFractionWithCache(arr);
		int result3 = returnWinnerFractionWithTable(arr);
		System.out.println(result1);
		System.out.println(result2);
		System.out.println(result3);
	}

	/**
	 * 返回获胜者的分数
	 *
	 * @param arr
	 * @return
	 */
	public static int returnWinnerFraction(int[] arr) {
		if (arr == null || arr.length == 0) {
			//表示没有获胜者
			return -1;
		}
		int firstFraction = firstHand(arr, 0, arr.length - 1);
		int backFraction = backHand(arr, 0, arr.length - 1);
		return Math.max(firstFraction, backFraction);
	}

	/**
	 * 在arr数组中，从left到right范围上，先手拿牌获得的最好分数
	 *
	 * @param arr
	 * @param left
	 * @param right
	 * @return 返回在arr数组中，从left到right范围上，先手拿牌获得的最好分数
	 */
	public static int firstHand(int[] arr, int left, int right) {
		//如果只剩下一张牌，而且作为先手，返回arr[left]和arr[right]都一样
		if (left == right) {
			return arr[left];
		}
		//如果先手先拿left上的牌，后手拿牌的范围就是left + 1到right
		int situation1 = arr[left] + backHand(arr, left + 1, right);
		//如果先手先拿right上的牌，后手拿牌的范围就是left到right - 1
		int situation2 = arr[right] + backHand(arr, left, right - 1);
		//先手肯定想要获胜，两种情况选择最大值即可获胜
		return Math.max(situation1, situation2);
	}

	/**
	 * 在arr数组中，从left到right范围上，后手拿牌获得的最好分数
	 *
	 * @param arr
	 * @param left
	 * @param right
	 * @return 返回在arr数组中，从left到right范围上，后手拿牌获得的最好分数
	 */
	public static int backHand(int[] arr, int left, int right) {
		//如果只剩下一张牌，而且作为后手，肯定拿不到牌，直接返回0
		if (left == right) {
			return 0;
		}
		//先手拿完arr[left]上的牌之后，此时后手作为先手，拿arr[left+1,right]范围上的牌
		int situation1 = firstHand(arr, left + 1, right);
		//先手拿完arr[right]上的牌之后，此时后手作为先手，拿arr[left,right-1]范围上的牌
		int situation2 = firstHand(arr, left, right - 1);
		//先手肯定会拿最优解的牌，尽可能的会给后手决定相对来说比较差的选择，所以是Math.min
		return Math.min(situation1, situation2);
	}

	/**
	 * 返回获胜者的分数
	 *
	 * @param arr
	 * @return
	 */
	public static int returnWinnerFractionWithCache(int[] arr) {
		if (arr == null || arr.length == 0) {
			//表示没有获胜者
			return -1;
		}
		int[][] firstCache = new int[arr.length][arr.length];
		int[][] backCache = new int[arr.length][arr.length];

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				firstCache[i][j] = -1;
				backCache[i][j] = -1;
			}
		}
		int firstFraction = firstHandWithCache(arr, 0, arr.length - 1, firstCache, backCache);
		int backFraction = backHandWithCache(arr, 0, arr.length - 1, firstCache, backCache);
		return Math.max(firstFraction, backFraction);
	}

	/**
	 * 在arr数组中，从left到right范围上，先手拿牌获得的最好分数
	 *
	 * @param arr
	 * @param left
	 * @param right
	 * @param firstCache
	 * @param backCache
	 * @return 返回在arr数组中，从left到right范围上，先手拿牌获得的最好分数
	 */
	public static int firstHandWithCache(int[] arr, int left, int right, int[][] firstCache, int[][] backCache) {
		if (firstCache[left][right] != -1) {
			return firstCache[left][right];
		}
		int ans = 0;
		//如果只剩下一张牌，而且作为先手，返回arr[left]和arr[right]都一样
		if (left == right) {
			ans = arr[left];
		} else {
			//如果先手先拿left上的牌，后手拿牌的范围就是left + 1到right
			int situation1 = arr[left] + backHandWithCache(arr, left + 1, right, firstCache, backCache);
			//如果先手先拿right上的牌，后手拿牌的范围就是left到right - 1
			int situation2 = arr[right] + backHandWithCache(arr, left, right - 1, firstCache, backCache);
			//先手肯定想要获胜，两种情况选择最大值即有可能获胜
			ans = Math.max(situation1, situation2);
		}
		firstCache[left][right] = ans;
		return ans;
	}

	/**
	 * 在arr数组中，从left到right范围上，后手拿牌获得的最好分数
	 *
	 * @param arr
	 * @param left
	 * @param right
	 * @param firstCache
	 * @param backCache
	 * @return 返回在arr数组中，从left到right范围上，后手拿牌获得的最好分数
	 */
	public static int backHandWithCache(int[] arr, int left, int right, int[][] firstCache, int[][] backCache) {
		if (backCache[left][right] != -1) {
			return backCache[left][right];
		}
		int ans = 0;
		//如果只剩下一张牌，而且作为后手，肯定拿不到牌，直接返回0
		if (left != right) {
			//先手拿完arr[left]上的牌之后，此时后手作为先手，拿arr[left+1,right]范围上的牌
			int situation1 = firstHandWithCache(arr, left + 1, right, firstCache, backCache);
			//先手拿完arr[right]上的牌之后，此时后手作为先手，拿arr[left,right-1]范围上的牌
			int situation2 = firstHandWithCache(arr, left, right - 1, firstCache, backCache);
			//先手肯定会拿最优解的牌，尽可能的会给后手决定相对来说比较差的选择，所以是Math.min
			ans = Math.min(situation1, situation2);
		}
		backCache[left][right] = ans;
		return ans;
	}

	/**
	 * @param arr
	 * @return
	 */
	public static int returnWinnerFractionWithTable(int[] arr) {
		int[][] firstTable = new int[arr.length][arr.length];
		int[][] backTable = new int[arr.length][arr.length];

		/*for (int i = 0; i < arr.length; i++) {
			firstTable[i][i] = arr[i];
			for (int startCol = 1; startCol < arr.length; startCol++) {
				int left = 0;
				int right = startCol;
				while (right < arr.length) {
					firstTable[left][right] = Math.max(arr[left] + backTable[left + 1][right], arr[right] + backTable[left][right - 1]);
					backTable[left][right] = Math.min(firstTable[left + 1][right], firstTable[left][right - 1]);
					left++;
					right++;
				}
			}
		}*/
		for (int startCol = 1; startCol < arr.length; startCol++) {
			int left = 0;
			for (int right = startCol; right < arr.length; right++) {
				firstTable[left][right] = Math.max(arr[left] + backTable[left + 1][right], arr[right] + backTable[left][right - 1]);
				backTable[left][right] = Math.min(firstTable[left + 1][right], firstTable[left][right - 1]);
				left++;
			}
		}

		/*for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.printf("%3d", firstTable[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.printf("%3d", backTable[i][j]);
			}
			System.out.println();
		}*/
		return Math.max(firstTable[0][arr.length - 1], backTable[0][arr.length - 1]);
	}
}