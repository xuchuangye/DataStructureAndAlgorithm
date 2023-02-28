package com.mashibing.greedy;


import com.mashibing.pojo.Project;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 贪心算法的面试题
 * 输入：正数数组costs、正数数组profits、正数K、正数M
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱（也就是利润）
 * K表示只能串行的最多做K个项目
 * M表示初始的资金
 * 说明：每做完一个项目，马上获得的收益，可以支持你做下一个项目，不能并行的做项目
 * 输出：你最后获得的最大钱数
 *
 * 基本思路：
 * 1、costs[i]表示i号项目的花费
 * 2、profits[i]表示i号项目的利润
 * 3、M初始的资金
 * 4、K表示串行最多做K个项目
 * 5、最后获得的钱数
 *
 *
 * 基本思路：
 * 1、创建小根堆和大根堆
 * 2、将所有的项目添加到小根堆，在小根堆中按照项目的花费进行从小到大的排序
 * 3、将小根堆所有小于等于初始资金的项目弹出，依次添加到大根堆，在大根堆中按照项目的利润进行降序排序
 * 4、从大根堆中挑选最大利润的项目开始做，初始资金累加该项目的利润，循环往复，直到超过最多做多少个项目数为止，结束
 * @author xcy
 * @date 2022/5/2 - 9:42
 */
public class IPO {
	public static void main(String[] args) {

	}

	/**
	 * 按照项目的花费进行升序排序
	 */
	public static class CostComparator implements Comparator<Project> {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.cost - o2.cost;
		}
	}

	/**
	 * 按照项目的利润进行降序排序
	 */
	public static class ProfitComparator implements Comparator<Project> {
		@Override
		public int compare(Project o1, Project o2) {
			return o2.profit - o1.profit;
		}
	}

	/**
	 * 初始资金M，返回做K个项目之后挣到利润累加和的最大资金
	 * @param K 最多做K个项目
	 * @param M 初始资金
	 * @param profits 每一个项目的利润
	 * @param costs 每一个项目的花费
	 * @return 返回做K个项目之后挣到利润累加和的最大资金
	 */
	public static int findMaximizedCapital(int K, int M, int[] profits, int[] costs) {
		//创建小根堆，按照项目的花费进行升序排序
		PriorityQueue<Project> costsQueue = new PriorityQueue<>(new CostComparator());
		//创建大根堆，按照项目的利润进行降序排序
		PriorityQueue<Project> profitsQueue = new PriorityQueue<>(new ProfitComparator());

		for (int i = 0; i < profits.length; i++) {
			costsQueue.add(new Project(profits[i], costs[i]));
		}
		//最多做K个项目
		for (int i = 0; i < K; i++) {
			//如果小根堆不为空，并且查看小根堆的堆顶的项目元素，该项目的花费大于等于初始资金
			while (!costsQueue.isEmpty() && costsQueue.peek().cost <= M) {
				//说明可以做该项目，将该项目从小根堆弹出，放入到大根堆中
				profitsQueue.add(costsQueue.poll());

			}
			//如果大根堆为空，直接返回资金M
			/*
			几种大根堆为空的情况：
			1.小根堆是空的，那么大根堆必然也是空的
			2.初始资金不够，也就是花费不够，无法做小根堆中的项目
			3.最多做K个项目，但是小根堆中根本就没有K个项目，需要提前结束
			 */
			if (profitsQueue.isEmpty()) {
				return M;
			}
			//如果大根堆不为空，取出堆顶利润最大的项目，做完该项目之后将该项目的利润累加到初始资金当中
			M += profitsQueue.poll().profit;
		}
		return M;
	}
}
