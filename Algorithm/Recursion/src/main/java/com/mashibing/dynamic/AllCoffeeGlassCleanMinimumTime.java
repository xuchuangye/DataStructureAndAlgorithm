package com.mashibing.dynamic;

import com.mashibing.common.DynamicUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 咖啡机的问题
 *
 * 给定一个数组arr，arr[i]表示第i号咖啡机泡一杯咖啡的时间，每一台咖啡机只能串行的泡咖啡
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，只能使用咖啡机泡咖啡
 * 认为每个人喝咖啡的时间非常短，咖啡机刚泡好的咖啡就能喝完
 * 只有一台洗咖啡机的机器，一次只能洗一个杯子，时间耗费为a，洗完才能洗下一个杯子
 * 每一个咖啡杯子也可以自己挥发干净，时间耗费为b，挥发干净咖啡杯可以并行
 * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程的最短时间
 * 参数：int[] arr, int N, int a, int b
 * @author xcy
 * @date 2022/5/10 - 9:15
 */
public class AllCoffeeGlassCleanMinimumTime {
	public static void main(String[] args) {
		int len = 10;
		int max = 10;
		int testTime = 10;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = DynamicUtils.randomArray(len, max);
			int n = (int) (Math.random() * 7) + 1;
			int a = (int) (Math.random() * 7) + 1;
			int b = (int) (Math.random() * 10) + 1;
			int ans1 = right(arr, n, a, b);
			int ans2 = minTime(arr, n, a, b);
			int ans3 = minTimeWithTable(arr, n, a, b);
			if (ans1 != ans2 || ans2 != ans3) {
				DynamicUtils.printArray(arr);
				System.out.println("n : " + n);
				System.out.println("a : " + a);
				System.out.println("b : " + b);
				System.out.println(ans1 + " , " + ans2 + " , " + ans3);
				System.out.println("===============");
				break;
			}
		}
		System.out.println("测试结束");

	}

	// 验证的方法
	// 彻底的暴力
	// 很慢但是绝对正确
	public static int right(int[] arr, int n, int a, int b) {
		int[] times = new int[arr.length];
		int[] drink = new int[n];
		return forceMake(arr, times, 0, drink, n, a, b);
	}

	// 每个人暴力尝试用每一个咖啡机给自己做咖啡
	public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
		if (kth == n) {
			int[] drinkSorted = Arrays.copyOf(drink, kth);
			Arrays.sort(drinkSorted);
			return forceWash(drinkSorted, a, b, 0, 0, 0);
		}
		int time = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			int work = arr[i];
			int pre = times[i];
			drink[kth] = pre + work;
			times[i] = pre + work;
			time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
			drink[kth] = 0;
			times[i] = pre;
		}
		return time;
	}

	public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
		if (index == drinks.length) {
			return time;
		}
		// 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
		int wash = Math.max(drinks[index], washLine) + a;
		int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

		// 选择二：当前index号咖啡杯，选择自然挥发
		int dry = drinks[index] + b;
		int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
		return Math.min(ans1, ans2);
	}

	public static class Machine {
		/**
		 * 什么时间点能醒来，继续给别人提供服务  --> 咖啡机可以再次使用的时间
		 */
		public int timePoint;
		/**
		 * 泡咖啡的时间 --> 咖啡机泡咖啡需要多久的时间
		 */
		public int workTime;
		public Machine(int t ,int w) {
			timePoint = t;
			workTime = w;
		}
	}

	/**
	 * 小根堆根据咖啡机可以再次使用的时间 + 咖啡机泡咖啡的时间的总时间进行升序排序的比较器
	 */
	public static class MaChineComparator implements Comparator<Machine> {
		@Override
		public int compare(Machine o1, Machine o2) {
			return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
		}
	}

	/**
	 * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程的最短时间 --> 使用暴力递归的方式
	 * @param arr 每台咖啡机泡咖啡的时间
	 * @param n N个人喝咖啡
	 * @param a 洗干净杯子花费的时间
	 * @param b 挥发干净杯子花费的时间
	 * @return 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程的最短时间
	 */
	public static int minTime(int[] arr, int n, int a, int b) {
		//创建小根堆
		PriorityQueue<Machine> queue = new PriorityQueue<>(new MaChineComparator());
		//一开始，每台咖啡机可以再次使用的时间是0，每台机器泡咖啡的时间是各自咖啡机泡咖啡的时间
		for (int makeCoffeeTime : arr) {
			queue.add(new Machine(0, makeCoffeeTime));
		}
		//每个人能够喝到咖啡的时间数组
		int[] drinks = new int[n];
		for (int i = 0; i < n; i++) {
			Machine cur = queue.poll();
			//每个人喝完咖啡的时间 = 咖啡机可以再次使用的时间 + 咖啡机泡咖啡的时间
			cur.timePoint += cur.workTime;
			drinks[i] = cur.timePoint;
			queue.add(cur);
		}
		return coreLogic(drinks, a, b, 0, 0);
	}

	/**
	 * 核心逻辑
	 *
	 * 当前杯子洗干净的情况：
	 * 1、要么喝完咖啡之后，等着洗咖啡杯子的机器去洗
	 * 2、要么就是洗咖啡杯子的机器等人喝完，才能去洗
	 * 当前杯子洗干净的时间 = Math.max(刚喝完咖啡的时间, 轮到当前杯子洗的时间) + 洗完杯子的时间
	 * 下一个杯子洗干净的时间 = process(drinks, a, b, index + 1, (洗咖啡杯的机器可以再次使用的时间)上一个杯子洗完的时间)
	 *
	 * 当前杯子挥发干净的情况：
	 * 1、喝完咖啡之后，直接去挥发
	 * 当前杯子挥发干净的时间 = 刚喝完咖啡的时间 + 挥发干净杯子的时间
	 * 下一个杯子挥发干净的时间 = process(drinks, a, b, index + 1, washLine)
	 * @param drinks N个人喝完咖啡的时间，也就是N个人开始洗咖啡杯的时间
	 * @param wash 咖啡机洗一个杯子洗干净的时间，只能串行
	 * @param air 咖啡杯子自然挥发干净的时间，可以并行
	 * @param index 当前第index个人开始洗咖啡杯 --> 可变参数
	 * @param free 咖啡机可以再次使用的时间 --> 可变参数
	 * @return drinks[]从index开始，所有杯子都变干净的最短时间
	 */
	public static int coreLogic(int[] drinks, int wash, int air, int index, int free) {
		//所有杯子变干净的最短时间是需要等所有的杯子都干净之后决定的
		//所以即使是最后一个杯子干净的时间返回0又如何
		if (index == drinks.length) {
			return 0;
		}

		//当前的咖啡杯子决定使用洗咖啡杯子的机器洗干净
		//当前杯子洗干净的时间
		//当前杯子洗干净的时间 = Math.max(刚喝完咖啡的时间, 轮到当前杯子洗的时间) + 洗完杯子的时间
		int curGlassCleanTime = Math.max(drinks[index], free) + wash;
		//下一个杯子洗干净的时间
		//下一个杯子洗干净的时间 = process(drinks, a, b, index + 1, (洗咖啡杯的机器可以再次使用的时间)上一个杯子洗完的时间)
		int nextGlassCleanTime = coreLogic(drinks, wash, air, index + 1, curGlassCleanTime);
		//情况1的最大时间
		int situation1 = Math.max(curGlassCleanTime, nextGlassCleanTime);

		//当前的咖啡杯子决定自然挥发干净
		//当前杯子挥发干净的时间
		//当前杯子挥发干净的时间 = 刚喝完咖啡的时间 + 挥发干净杯子的时间
		int curGlassVolatilizeTime = drinks[index] + air;
		//下一个杯子挥发干净的时间
		//下一个杯子挥发干净的时间 = process(drinks, a, b, index + 1, washLine)
		int nextGlassVolatilizeTime = coreLogic(drinks, wash, air, index + 1, free);
		//情况2的最大时间
		int situation2 = Math.max(curGlassVolatilizeTime, nextGlassVolatilizeTime);

		//返回所有杯子干净的最短时间
		return Math.min(situation1, situation2);
	}

	/**
	 * 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程的最短时间 --> 使用动态规划的方式
	 * @param arr 每台咖啡机泡咖啡的时间
	 * @param n N个人喝咖啡
	 * @param a 洗干净杯子的时间
	 * @param b 挥发干净杯子的时间
	 * @return 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程的最短时间
	 */
	public static int minTimeWithTable(int[] arr, int n, int a, int b) {
		//创建小根堆
		PriorityQueue<Machine> queue = new PriorityQueue<>(new MaChineComparator());
		for (int coffeeTime : arr) {
			queue.add(new Machine(0, coffeeTime));
		}
		//N个人喝完咖啡的时间
		int[] drinks = new int[n];
		for (int i = 0; i < n; i++) {
			Machine cur = queue.poll();
			cur.timePoint += cur.workTime;
			drinks[i] = cur.timePoint;
			queue.add(cur);
		}
		return coreLogicWithTable(drinks, a, b);
	}

	/**
	 * 核心逻辑
	 * @param drinks 所有客户喝完咖啡的时间
	 * @param wash 洗干净杯子的时间
	 * @param air 挥发干净杯子的时间
	 * @return 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程的最短时间
	 */
	public static int coreLogicWithTable(int[] drinks, int wash, int air) {
		//所有杯子洗干净的时间
		int maxFree = 0;
		for (int drink : drinks) {
			maxFree = Math.max(maxFree, drink) + wash;
		}
		
		int[][] table = new int[drinks.length + 1][maxFree + 1];
		//if (index == drinks.length) {
		//	return 0;
		//}
		//table[drinks.length][...] = 0;

		for (int index = drinks.length - 1; index >= 0; index--) {
			for (int free = 0; free <= maxFree; free++) {
				//当前的咖啡杯子决定使用洗咖啡杯子的机器洗干净
				//当前杯子洗干净的时间
				//当前杯子洗干净的时间 = Math.max(刚喝完咖啡的时间, 轮到当前杯子洗的时间) + 洗完杯子的时间
				//int curGlassCleanTime = Math.max(drinks[index], washLine) + wash;
				int curGlassCleanTime = Math.max(drinks[index], free) + wash;
				//如果curGlassCleanTime比所有杯子洗干净的时间还要长，说明暴力递归一定没有调用，直接退出当前层循环
				if (curGlassCleanTime > maxFree) {
					break;
				}
				//下一个杯子洗干净的时间
				//下一个杯子洗干净的时间 = process(drinks, wash, air, index + 1, (洗咖啡杯的机器可以再次使用的时间)上一个杯子洗完的时间)
				//int nextGlassCleanTime = coreLogic(drinks, wash, air, index + 1, curGlassCleanTime);
				int nextGlassCleanTime = table[index + 1][curGlassCleanTime];
				//情况1的最大时间
				//int situation1 = Math.max(curGlassCleanTime, nextGlassCleanTime);
				int situation1 = Math.max(curGlassCleanTime, nextGlassCleanTime);
				//当前的咖啡杯子决定自然挥发干净
				//当前杯子挥发干净的时间
				//当前杯子挥发干净的时间 = 刚喝完咖啡的时间 + 挥发干净杯子的时间
				//int curGlassVolatilizeTime = drinks[index] + air;
				int curGlassVolatilizeTime = drinks[index] + air;
				//下一个杯子挥发干净的时间
				//下一个杯子挥发干净的时间 = process(drinks, wash, air, index + 1, washLine)
				//int nextGlassVolatilizeTime = coreLogic(drinks, wash, air, index + 1, washLine);
				int nextGlassVolatilizeTime = table[index + 1][free];
				//情况2的最大时间
				//int situation2 = Math.max(curGlassVolatilizeTime, nextGlassVolatilizeTime);
				int situation2 = Math.max(curGlassVolatilizeTime, nextGlassVolatilizeTime);
				//返回所有杯子干净的最短时间
				//return Math.min(situation1, situation2);
				table[index][free] = Math.min(situation1, situation2);
			}
		}
		
		//return coreLogicWithTable(drinks, a, b, 0, 0);
		return table[0][0];
	}
}
