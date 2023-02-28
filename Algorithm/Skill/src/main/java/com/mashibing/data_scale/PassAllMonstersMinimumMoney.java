package com.mashibing.data_scale;

import com.mashibing.common.SkillUtils;

/**
 * 题目四：
 * int[] d，d[i]：i号怪兽的能力
 * int[] p，p[i]：i号怪兽要求的钱
 * 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
 * 1.如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上；
 * 2.如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，
 * 然后怪兽就会加入你，他的能力直接累加到你的能力上。
 * <p>
 * 返回通过所有的怪兽，需要花的最小钱数。
 *
 * @author xcy
 * @date 2022/6/4 - 10:17
 */
public class PassAllMonstersMinimumMoney {
	public static void main(String[] args) {
		int len = 10;
		int value = 20;
		int testTimes = 1000000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTimes; i++) {
			int[][] arrs = SkillUtils.generateTwoRandomArray(len, value);
			int[] d = arrs[0];
			int[] p = arrs[1];
			long ans1 = passAllMonstersMinimumMoney_ModeOfThinking1(d, p);
			long ans2 = passAllMonstersMinimumMoney_ModeOfThinking2(d, p);
			long ans3 = passAllMonstersMinimumMoney_ModeOfThinking1DynamicPlanning(d, p);
			long ans4 = passAllMonstersMinimumMoney_ModeOfThinking2DynamicPlanning(d, p);
			if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
				System.out.println("测试失败！");
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 使用思维模式1的方式 --> 暴力递归
	 * <p>
	 * 适用类型：
	 * 贿赂怪兽花费的钱数范围：10的6次方
	 * 怪兽的能力值范围： 1 ~ 10
	 *
	 * dp[i][j]
	 * i表示怪兽，j当前所具有的能力值
	 * 表示来到i号怪兽时，当前所具有的能力值j，既要通过i号怪兽，又要通过i号之后所有的怪兽花费最小的钱数
	 * @param d
	 * @param p
	 * @return
	 */
	public static long passAllMonstersMinimumMoney_ModeOfThinking1(int[] d, int[] p) {
		if (d == null || d.length == 0 || p == null || p.length == 0) {
			return 0L;
		}
		//一开始，当前所具有的能力值ability是0，并且从第0个怪兽开始通过
		return coreLogic_ModeOfThinking1(d, p, 0, 0);
	}

	/**
	 * 核心逻辑
	 *
	 * @param d       怪兽能力的数组
	 * @param p       贿赂怪兽的钱数
	 * @param index   当前第几号怪兽
	 * @param ability 当前所具有的能力值
	 * @return 当前所具有的能力值是ability，来到了index号怪兽，如果需要通过所有的怪兽，返回需要花的最小钱数
	 */
	public static long coreLogic_ModeOfThinking1(int[] d, int[] p, int index, int ability) {
		//已经来到怪兽的长度，表示已经通过所有的怪兽
		if (index == d.length) {
			return 0L;
		}
		//如果当前所具有的能力值 小于 怪兽的能力值，必须要花钱贿赂
		if (ability < d[index]) {
			//p[index]：表示当前怪兽贿赂的钱
			//ability + d[index]：表示贿赂怪兽之后，当前所具有的能力值 + 怪兽所具有的能力值
			//index + 1：表示继续通过下一个怪兽
			return p[index] + coreLogic_ModeOfThinking1(d, p, index + 1, ability + d[index]);
		}
		//如果当前所具有的能力值 大于等于 怪兽的能力值，既可以选择花钱贿赂，也可以选择不花钱直接通过，返回通过所有怪兽的最小钱数
		else {
			return Math.min(p[index] + coreLogic_ModeOfThinking1(d, p, index + 1, ability + d[index])
					, coreLogic_ModeOfThinking1(d, p, index + 1, ability));
		}
	}

	/**
	 * 使用思维模式1的方式 --> 动态规划
	 * <p>
	 * 适用类型：
	 * 贿赂怪兽花费的钱数范围：10的6次方
	 * 怪兽的能力值范围： 1 ~ 10
	 *
	 * dp[i][j]
	 * i表示怪兽，j当前所具有的能力值
	 * 表示来到i号怪兽时，当前所具有的能力值j，既要通过i号怪兽，又要通过i号之后所有的怪兽花费最小的钱数
	 * @param d
	 * @param p
	 * @return
	 */
	public static long passAllMonstersMinimumMoney_ModeOfThinking1DynamicPlanning(int[] d, int[] p) {
		if (d == null || d.length == 0 || p == null || p.length == 0) {
			return 0L;
		}
		int allAbility = 0;
		//累加怪物的能力值
		for (int amount : d) {
			allAbility += amount;
		}
		//要求，通过0 ~ i的所有怪兽，所以0 ~d.length，需要扩充 + 1，
		int[][] dp = new int[d.length + 1][allAbility + 1];

		for (int ability = 0; ability <= allAbility; ability++) {
			dp[0][ability] = 0;
		}

		for (int index = d.length - 1; index >= 0; index--) {
			for (int ability = 0; ability <= allAbility; ability++) {
				// 如果这种情况发生，那么这个ability必然是递归过程中不会出现的状态
				// 既然动态规划是尝试过程的优化，尝试过程碰不到的状态，不必计算
				if (ability + d[index] > allAbility) {
					continue;
				}
				//if (ability < d[index]) {
				//	//p[index]：表示当前怪兽贿赂的钱
				//	//ability + d[index]：表示贿赂怪兽之后，当前所具有的能力值 + 怪兽所具有的能力值
				//	//index + 1：表示继续通过下一个怪兽
				//	return p[index] + coreLogic_ModeOfThinking1(d, p, index + 1, ability + d[index]);
				//}
				//如果当前所具有的能力值 小于 怪兽的能力值，必须要花钱贿赂
				if (ability < d[index]) {
					//p[index]：表示当前怪兽贿赂的钱
					//index + 1：表示继续通过下一个怪兽
					//ability + d[index]：表示贿赂怪兽之后，当前所具有的能力值 + 怪兽所具有的能力值
					dp[index][ability] = p[index] + dp[index + 1][ability + d[index]];
				}
				//else {
				//	return Math.min(p[index] + coreLogic_ModeOfThinking1(d, p, index + 1, ability + d[index])
				//			, coreLogic_ModeOfThinking1(d, p, index + 1, ability));
				//}
				//如果当前所具有的能力值 大于等于 怪兽的能力值
				else {
					//既可以选择花钱贿赂，也可以选择不花钱直接通过，选择通过所有怪兽的最小钱数
					dp[index][ability] = Math.min(p[index] + dp[index + 1][ability + d[index]], dp[index + 1][ability]);
				}
			}
		}
		//return coreLogic_ModeOfThinking1(d, p, 0, 0);
		return dp[0][0];
	}

	/**
	 * 使用思维模式2的方式 --> 暴力递归
	 * <p>
	 * 适用类型：
	 * 贿赂怪兽花费的钱数范围：1 ~ 10
	 * 怪兽的能力值范围： 10的6次方
	 * <p>
	 * dp[i][j]
	 * 1.i表示怪兽的编号，j表示必须严格花的钱数
	 * 2.如果从0 ~ i花的钱数是j，dp[i][j]表示能够通过i号怪兽时达到的最大能力值
	 * 3.如果是通过0 ~ i号怪兽但是没有正好花够j的钱数或者正好花够j的钱数但是没有通过0 ~ i号怪兽，都不通过，dp[i][j] = -1
	 * <p>
	 * 情况一：不贿赂当前i号怪兽
	 * 1）dp[i - 1][j] == -1，表示虽然花够了j的钱数，但是不能通过0 ~ i - 1号怪兽
	 * 或者虽然通过了0 ~ i - 1号怪兽，但是没花够j的钱数，那么当前怪兽不管怎么样都不会通过，dp[i][j] = -1
	 * 所以当前怪兽通过的前提条件，dp[i - 1][j] != -1
	 * 2）dp[i - 1][j] >= i号怪兽的能力，必须保证前一个怪兽能够通过并且能力值大于等于当前i号怪兽的能力，当前怪兽才能通过，
	 * 并且不需要贿赂当前怪兽
	 * <p>
	 * 情况二：贿赂当前i号怪兽
	 * money表示i号怪兽贿赂需要的钱数
	 * 1）dp[i - 1][j - money]，表示贿赂i号怪兽之后花够j的钱数之前，通过0 ~ i - 1怪兽已经花够j - money的钱数
	 * 所以当前怪兽贿赂的前提条件，dp[i - 1][j - p[index]] != -1
	 *
	 * @param d
	 * @param p
	 * @return
	 */
	public static long passAllMonstersMinimumMoney_ModeOfThinking2(int[] d, int[] p) {
		if (d == null || d.length == 0 || p == null || p.length == 0) {
			return 0L;
		}
		//贿赂所有怪兽需要花费的钱数
		int allMoney = 0;
		for (int amount : p) {
			allMoney += amount;
		}
		int N = d.length;
		//从0开始，到N - 1号怪兽，如果能够通过所有的怪兽，并且组成最小钱数，直接返回最小钱数
		for (int money = 0; money <= allMoney; money++) {
			if (coreLogic_ModeOfThinking2(d, p, N - 1, money) != -1) {
				return money;
			}
		}
		return allMoney;
	}

	/**
	 * 核心逻辑
	 *
	 * @param d     怪兽能力的数组
	 * @param p     贿赂怪兽的钱数
	 * @param index 当前第几号怪兽
	 * @param money 当前怪兽需要贿赂的钱数
	 * @return 当前所具有的能力值是ability，来到了index号怪兽，如果需要通过所有的怪兽，返回需要花的最小钱数
	 */
	public static long coreLogic_ModeOfThinking2(int[] d, int[] p, int index, int money) {
		//没有遇到怪兽的情况下
		if (index == -1) {
			//如果花费0的钱数
			return money == 0 ? 0L : -1L;
		}
		//情况1：直接通过当前index号怪兽，那么0 ~ index - 1的怪兽必须已经花够了money的钱数
		//表示既通过了0 ~ index - 1号怪兽，又花够了money的钱数
		long ability1 = coreLogic_ModeOfThinking2(d, p, index - 1, money);
		//情况1表示在通过index号怪兽的情况下，当前的最大能力值
		long situation1 = -1;
		//表示0 ~ index - 1号怪兽都通过了，并且之前通过的能力值 大于 当前怪兽的能力值
		if (ability1 != -1 && ability1 >= d[index]) {
			//情况1的最大能力值
			situation1 = ability1;
		}

		//情况2：直接贿赂当前index号怪兽，那么0 ~ index - 1的怪兽必须已经花够了money - p[index]的钱数
		//表示通过了0 ~ index - 1号怪兽
		long ability2 = coreLogic_ModeOfThinking2(d, p, index - 1, money - p[index]);
		//情况2表示在贿赂index号怪兽的情况下，当前的最大能力值
		long situation2 = -1;
		//表示0 ~ index - 1号怪兽都通过了，贿赂怪兽时不需要考虑怪兽的能力
		if (ability2 != -1) {
			//情况2的最大能力值 = 贿赂怪兽的钱数 + 0 ~ index - 1的怪兽已经花费的钱数
			situation2 = d[index] + ability2;
		}
		//返回最大钱数
		return Math.max(situation1, situation2);
	}

	/**
	 * 使用思维模式2的方式 --> 动态规划
	 * <p>
	 * 适用类型：
	 * 贿赂怪兽花费的钱数范围：1 ~ 10
	 * 怪兽的能力值范围： 10的6次方
	 * <p>
	 * dp[i][j]
	 * 1.i表示怪兽的编号，j表示必须严格花的钱数
	 * 2.如果从0 ~ i花的钱数是j，dp[i][j]表示能够通过i号怪兽时达到的最大能力值
	 * 3.如果是通过0 ~ i号怪兽但是没有正好花够j的钱数或者正好花够j的钱数但是没有通过0 ~ i号怪兽，都不通过，dp[i][j] = -1
	 * <p>
	 * 情况一：不贿赂当前i号怪兽
	 * 1）dp[i - 1][j] == -1，表示虽然花够了j的钱数，但是不能通过0 ~ i - 1号怪兽
	 * 或者虽然通过了0 ~ i - 1号怪兽，但是没花够j的钱数，那么当前怪兽不管怎么样都不会通过，dp[i][j] = -1
	 * 所以当前怪兽通过的前提条件，dp[i - 1][j] != -1
	 * 2）dp[i - 1][j] >= i号怪兽的能力，必须保证前一个怪兽能够通过并且能力值大于等于当前i号怪兽的能力，当前怪兽才能通过，
	 * 并且不需要贿赂当前怪兽
	 * <p>
	 * 情况二：贿赂当前i号怪兽
	 * money表示i号怪兽贿赂需要的钱数
	 * 1）dp[i - 1][j - money]，表示贿赂i号怪兽之后花够j的钱数之前，通过0 ~ i - 1怪兽已经花够j - money的钱数
	 * 所以当前怪兽贿赂的前提条件，dp[i - 1][j - p[index]] != -1
	 *
	 * @param d
	 * @param p
	 * @return
	 */
	public static long passAllMonstersMinimumMoney_ModeOfThinking2DynamicPlanning(int[] d, int[] p) {
		if (d == null || d.length == 0 || p == null || p.length == 0) {
			return 0L;
		}
		int allMoney = 0;
		//累加贿赂怪兽的钱数
		for (int amount : p) {
			allMoney += amount;
		}
		long[][] dp = new long[d.length][allMoney + 1];

		// dp[i][j]含义：
		// 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
		// 如果dp[i][j] == -1，表示经过0～i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
		for (int index = 0; index < d.length; index++) {
			for (int money = 0; money <= allMoney; money++) {
				dp[index][money] = -1;
			}
		}
		// 经过0～i的怪兽，花钱数一定为p[0]，达到武力值d[0]的地步。其他第0行的状态一律是无效的
		dp[0][p[0]] = d[0];

		for (int index = 1; index < d.length; index++) {
			for (int money = 0; money <= allMoney; money++) {
				// 可能性一，选择贿赂当前怪兽
				// 存在条件：
				// j - p[i]不越界，并且在钱数为j - p[i]时，要能通过0 ~ i - 1的怪兽，并且钱数组合是有效的
				if (money >= p[index] && (dp[index - 1][money - p[index]] != -1)) {
					//情况2的最大能力值 = 贿赂怪兽的钱数 + 0 ~ index - 1的怪兽已经花费的钱数
					dp[index][money] = d[index] + dp[index - 1][money - p[index]];
				}
				// 可能性二，不选择贿赂当前怪兽
				// 存在条件：
				// 0 ~ i - 1怪兽在花钱为j的情况下，能保证通过当前i位置的怪兽
				if (dp[index - 1][money] >= d[index]) {
					//情况1的最大能力值
					dp[index][money] = Math.max(dp[index][money], dp[index - 1][money]);
				}
			}
		}
		int ans = 0;
		// dp表最后一行上，dp[N-1][j]代表：
		// 能经过0～N-1的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
		// 那么最后一行上，最左侧的不为-1的列数(j)，就是答案
		for (int money = 0; money <= allMoney; money++) {
			if (dp[d.length - 1][money] != -1) {
				ans = money;
				break;
			}
		}
		return ans;
	}
}
