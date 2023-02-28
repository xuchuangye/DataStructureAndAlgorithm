package com.mashibing.day02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * 题目一：
 * 给定数组hard和money，长度都为N
 * hard[i]表示i号工作的难度， money[i]表示i号工作的收入
 * 给定数组ability，长度都为M，ability[j]表示j号人的能力
 * 每一号工作，都可以提供无数的岗位，难度和收入都一样
 * 但是人的能力必须 >= 这份工作的难度，才能上班。
 * 返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
 *
 * 解题思路：
 * 1.使用有序表TreeMap
 * 2.按照难度进行从小到大的排序，收入从大到小进行排序，并且只保留相同难度中收入最高的，从而选出每个难度中收入最高的
 * 因为根据贪心算法，肯定是做在难度相同的情况下，收入最高的工作
 * 3.在这个难度排序并且选出每个难度中收入最高的过程中，如果下一个难度的最高收入比上一个难度的最高收入还要低，那么直接
 * 删除下一个难度的最高收入，因为根据贪心算法，肯定是选择难度较低并且收入较高的工作
 * 4.最终保留的难度和收入一定成正比，处于递增状态
 * 5.最后使用有序表，根据人的能力大于等于工作的难度，直接选择最大的收入
 *
 * @author xcy
 * @date 2022/7/11 - 9:08
 */
public class Code01_ChooseWork {
	public static void main(String[] args) {

	}

	/**
	 * 将工作的难度和工作的收入进行封装，封装为工作类
	 */
	public static class Job {
		/**
		 * 工作的难度
		 */
		public int hard;
		/**
		 * 工作的收入
		 */
		public int money;

		public Job(int hard, int money) {
			this.hard = hard;
			this.money = money;
		}
	}

	/**
	 * 按照工作的难度和工作的收入生成工作类比较器
	 */
	public static class JobComparator implements Comparator<Job> {
		@Override
		public int compare(Job o1, Job o2) {
			//根据贪心算法：肯定是选择工作的难度小，工作的收入高的工作
			//按照工作的难度进行从小到大的排序
			//如果工作的难度相等，按照工作的收入进行从大到小的排序
			return o1.hard != o2.hard ? o1.hard - o2.hard : o2.money - o1.money;
		}
	}

	/**
	 * 二分查找和有序表的时间复杂度：O(logN)
	 * @param jobs 工作类
	 * @param ability 人的能力组
	 * @return
	 */
	public static int[] getMoney(Job[] jobs, int[] ability) {
		//先按照工作类的数组进行排序
		Arrays.sort(jobs, new JobComparator());
		//key: 工作的难度
		//value: 工作的收入
		TreeMap<Integer, Integer> treeMap = new TreeMap<>();
		treeMap.put(jobs[0].hard, jobs[0].money);
		//上一个工作
		Job pre = jobs[0];
		for (Job job : jobs) {
			//所有删除掉的工作不进入这个表
			//当前这份工作的难度和上一份工作难度不一样
			//并且当前这份工作的收入大于上一份工作的收入
			//上一份工作就来到当前这份工作，并且将当前这份工作记录到表中
			if (job.hard != pre.hard && job.money > pre.money) {
				pre = job;
				treeMap.put(pre.hard, pre.money);
			}
		}
		//根据人数创建每个人对应的最大收入的数组
		int[] ans = new int[ability.length];
		for (int i = 0; i < ability.length; i++) {
			//treeMap.floorKey(ability[i])表示 <= ability[i]的key是多少
			Integer abilities = treeMap.floorKey(ability[i]);
			//根据人的能力选择合适的工作，也有可能人的能力不能胜任任何工作，有可能为null
			ans[i] = abilities != null ? treeMap.get(abilities) : 0;
		}
		return ans;
	}
}
