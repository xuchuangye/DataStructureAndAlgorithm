package com.mashibing.day13;

/**
 * 假设有 n 台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的
 * 在每一步操作中，你可以选择任意 m （1 ≤ m ≤ n） 台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机
 * 给定一个非负整数数组代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的最少的操作步数
 * 如果不能使每台洗衣机中衣物的数量相等，则返回-1
 * <p>
 * 解题思路：
 * 1.利用单点思维
 * 2.分为几种情况：
 * 已经计算过每一台洗衣机平均需要洗衣服的数量，i位置有多少衣服不知道
 * (1)i位置左侧整体相对于平均值少15件，i位置右侧整体相对于平均值多10件
 * i位置至少需要送几轮衣服，i就不需要再送了，15轮，也就是Math.max(15, 10)
 * i位置左侧整体相对于平均值少15件，i位置右侧整体相对于平均值多20件
 * i位置至少需要送几轮衣服，i就不需要再送了，20轮，也就是Math.max(15, 20)
 * (2).i位置左侧整体相对于平均值多15件，i位置右侧整体相对于平均值多10件
 * i位置至少需要送几轮衣服，i就不需要再送了，15轮，也就是Math.max(15, 10)
 * (3).i位置左侧整体相对于平均值少15件，i位置右侧整体相对于平均值少10件
 * i位置至少需要送几轮衣服，i就不需要再送了，25轮，也就是15 + 10 = 25
 * 3.每个位置至少需要的轮数最大的就是整体的瓶颈，因为只有该位置解决了，其他的位置就都解决了
 * 4.洗衣机问题，不会想整体，就想单点答案是什么，通过单点答案得到整体答案的办法会成为我们的思想传统
 *
 * <p>
 * Leetcode题目：https://leetcode.cn/problems/super-washing-machines/
 *
 * @author xcy
 * @date 2022/8/1 - 15:02
 */
public class Code02_SuperWashingMachines {
	public static void main(String[] args) {

	}

	public static int findMinMoves(int[] machines) {
		if (machines == null || machines.length == 0) {
			return 0;
		}
		//机器数量
		int size = machines.length;
		//所有机器中的衣服数量
		int sum = 0;
		for (int machine : machines) {
			sum += machine;
		}
		//如果所有机器中的衣服数量 % 机器数量 != 0，那么根本没有任何办法进行机器之间衣服的传送
		if (sum % size != 0) {
			return -1;
		}
		//每台机器能够洗衣服的数量的平均值
		int avg = sum / size;
		//左部分的累加和
		int leftSum = 0;
		int ans = 0;
		for (int i = 0; i < machines.length; i++) {
			int leftRest = leftSum - i * avg;
			//sum - leftSum - machines[i]表示右部分的累加和，也就是右部分所有机器中的衣服数量
			//sum整体的累加和
			//leftSum左部分的累加和
			//machines[i]表示当前机器中衣服的数量
			//(size - i - 1) * avg表示右部分机器相对应平均值需要的衣服数量
			//(sum - leftSum - machines[i]) - (size - i - 1) * avg表示
			//如果多出衣服数量，那么就往外送，如果少出衣服数量，那么就往里送
			int rightRest = (sum - leftSum - machines[i]) - (size - i - 1) * avg;
			//(1)i位置左侧整体相对于平均值-a件，i位置右侧整体相对于平均值b件
			//i位置至少需要送几轮衣服，i就不需要再送了，也就是Math.max(Math.abs(a), b)
			//(2).i位置左侧整体相对于平均值a件，i位置右侧整体相对于平均值b件
			//i位置至少需要送几轮衣服，i就不需要再送了，也就是Math.max(a, b)
			//(3).i位置左侧整体相对于平均值-a件，i位置右侧整体相对于平均值-b件
			//i位置至少需要送几轮衣服，i就不需要再送了，也就是Math.abs(a) + Math.abs(b)
			if (leftRest < 0 && rightRest < 0) {
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else {
				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}
			leftSum += machines[i];
		}
		return ans;
	}
}
