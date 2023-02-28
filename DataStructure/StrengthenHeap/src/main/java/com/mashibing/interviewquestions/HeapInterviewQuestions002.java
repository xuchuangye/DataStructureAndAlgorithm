package com.mashibing.interviewquestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 堆的题目：
 * 给定很多线段，每个线段都有两个数[start, end]，表示线段开始位置和结束位置，左右都是闭区间
 * 要求：
 * 1）线段开始位置和结束位置一定都是整数
 * 2）线段重合长度必须>=1，也就是>0
 * 返回线段最多重合区域中，包含几条线段
 * <p>
 * 基本思路：
 * 1、将所有线段的开始位置按照从小到大的顺序进行排序
 * 2、创建小根堆，遍历每一个线段，在小根堆中将小于等于线段的开始位置的值全部弹出，表示没有穿过该线段，也就不会存在重合区域
 * 3、将线段的结束位置的值添加到小根堆中，此时小根堆中值的个数就是穿过当前线段的条数
 * 4、选择最多的条数
 *
 * @author xcy
 * @date 2022/4/20 - 8:49
 */
public class HeapInterviewQuestions002 {
	public static void main(String[] args) {
		//测试时间
		/*int[] arr = SortCommonUtils.getArray();

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("堆排序前的时间：\n%s\n", start);

		System.out.println();
		heapSort(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("堆排序后的时间：\n%s\n", end);*/

		//测试错误
		/*//测试次数
		int testTime = 1000000;
		//数组长度
		int length = 100;
		//数组元素的值的范围
		int value = 100;
		//是否测试成功，默认测试是成功的
		boolean success = true;
		for (int i = 1; i <= testTime; i++) {
			//生成随机长度值也随机的数组
			int[] array = SortCommonUtils.generateRandomArray(length, value);
			//拷贝数组
			int[] arr = SortCommonUtils.copyArray(array);

			//自己实现的排序方式
			heapSort(array, 2);
			//系统提供的排序方式
			SortCommonUtils.sort(arr);

			//判断array和arr数组中的所有元素是否都相等
			if (!SortCommonUtils.isEqual(array, arr)) {
				//如果不相等，则标记success为false
				success = false;
				SortCommonUtils.printArray(array);
				SortCommonUtils.printArray(arr);
				break;
			}

		}
		System.out.println(success ? "测试成功" : "测试失败");*/

		int[] arr = {3, 1, 4, 2, 5};
	}

	/**
	 * 比较差的方法
	 *
	 * @param link 线段的数组集合，例如：{{1,4}, {2,5}, {3,5}}
	 * @return
	 */
	public static int maxCover1(int[][] link) {
		int min = Integer.MIN_VALUE;
		int max = Integer.MAX_VALUE;

		for (int i = 0; i < link.length; i++) {
			min = Math.min(min, link[i][0]);
			max = Math.max(max, link[i][1]);
		}

		int cover = 0;
		for (double p = min + 0.5; p < max; p++) {
			int cur = 0;
			for (int i = 0; i < link.length; i++) {
				if (link[i][0] < p && link[i][1] > p) {
					cur++;
				}
			}
			cover = Math.max(cover, cur);
		}
		return cover;
	}

	/**
	 * 比较好的方法
	 * <p>
	 * 时间复杂度：O(N * logN)
	 * 小根堆中线段的结束位置只会进入和弹出一次，2N次，时间复杂度O(N)
	 * 小根堆的时间复杂度是O(logN)
	 * 最终时间复杂度O(N * logN)
	 *
	 * @param link 线段的数组集合，例如：{{1,4}, {2,5}, {3,5}}
	 * @return
	 */
	public static int maxCover2(int[][] link) {
		Line[] lines = new Line[link.length];
		for (int i = 0; i < link.length; i++) {
			lines[i] = new Line(link[i][0], link[i][1]);
		}
		Arrays.sort(lines, new StartComparator());
		//创建小根堆，系统默认的就是小根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		int max = 0;
		//遍历线段类数组的每一个线段类
		for (int i = 0; i < lines.length; i++) {
			//判断小根堆是否为空，并且查看小根堆中是否有小于等于线段的开始位置的值
			//如果小根堆为空，退出循环
			//如果小根堆中没有小于等于线段开始位置的值，则退出循环
			while (heap.isEmpty() && heap.peek() <= lines[i].start) {
				//如果小根堆不为空则弹出小根堆中小于等于线段的开始位置的值
				heap.poll();
			}
			//在小根堆中添加大于线段结束位置的值
			heap.add(lines[i].end);
			//
			max = Math.max(max, heap.size());
		}
		return max;
	}

	/**
	 * 线段类
	 * 开始位置：start
	 * 结束位置：end
	 */
	public static class Line {
		public int start;
		public int end;

		public Line(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	/**
	 * 按照线段的开始位置start进行升序遍历
	 */
	public static class StartComparator implements Comparator<Line> {
		@Override
		public int compare(Line o1, Line o2) {
			return o1.start - o2.start;
		}
	}
}
