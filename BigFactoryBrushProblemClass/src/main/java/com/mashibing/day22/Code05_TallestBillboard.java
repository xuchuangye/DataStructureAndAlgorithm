package com.mashibing.day22;

import java.util.HashMap;

/**
 * 你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
 * 你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
 * 返回广告牌的最大可能安装高度。如果没法安装广告牌，请返回 0。
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/tallest-billboard/
 *
 * @author xcy
 * @date 2022/8/15 - 8:19
 */
public class Code05_TallestBillboard {
	public static void main(String[] args) {
		int[] rods = {1, 2, 3, 5};
		tallestBillboard(rods);
	}

	public static int tallestBillboard(int[] rods) {
		if (rods == null || rods.length == 0) {
			return 0;
		}
		HashMap<Integer, Integer> dp = new HashMap<>(), cur;
		//空集和空集
		dp.put(0, 0);
		for (int num : rods) {
			if (num != 0) {
				//新的Map拷贝老的Map
				cur = new HashMap<>(dp);
				//对新的Map进行遍历
				//老的Map推新的Map
				for (Integer d : cur.keySet()) {
					//差值中最好的基线
					int diffMore = cur.get(d);
					//d + rod的累加和, d + rod累加和对应的基线0
					dp.put(d + num, Math.max(diffMore, dp.getOrDefault(num + d, 0)));

					//基线
					int diffXD = dp.getOrDefault(Math.abs(num - d), 0);
					if (d >= num) {
						dp.put(d - num, Math.max(diffMore + num, diffXD));
					} else {
						dp.put(num - d, Math.max(diffMore + d, diffXD));
					}
				}
			}
		}
		//返回差值为0的最好基线
		return dp.get(0);
	}
}
