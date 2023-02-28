package com.mashibing.data_volume;

import com.mashibing.common.SkillUtils;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 题目二：
 * 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]，背包容量为w。
 * 牛牛想知道在总体积不超过背包容量的情况下,
 * 一共有多少种零食放法，体积为0也算一种放法
 * 1 <= n <= 30, 1 <= w <= 2 * 10^9
 * v[i] (0 <= v[i] <= 10^9）
 * <p>
 * 牛客测试链接：
 * https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
 *
 * @author xcy
 * @date 2022/6/5 - 15:22
 */
public class BackpackSnackPlacement {
	public static void main(String[] args) {
		/*Scanner sc = new Scanner(System.in);
		System.out.println("输入arr数组的长度：");
		int N = sc.nextInt();
		System.out.println("输入背包容量：");
		int bag = sc.nextInt();
		int[] arr = new int[N];
		for (int i = 0; i < arr.length; i++) {
			System.out.println("输入arr数组的值(零食的体积)：");
			arr[i] = sc.nextInt();
		}
		long ways = ways(arr, bag);
		System.out.println("一共有" + ways + "种放法");
		sc.close();*/
		int len = 100;
		int value = 100;
		int bag = 76;
		int testTime = 50000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int[] arr = SkillUtils.generateRandomArray(len, value);
			long ans1 = backpackSnackPlacement(arr, bag);
			long ans2 = ways(arr, bag);
			if (ans1 != ans2) {
				System.out.println("测试失败！");
				break;
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 使用分治
	 *
	 * @param arr 数组的值范围比较大
	 * @param bag 背包容量比较大
	 * @return
	 */
	public static long backpackSnackPlacement(int[] arr, int bag) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			//arr[0] <= bag算1种放法，0也算1种放法
			//满足arr[0] <= bag条件就是2种算法，不满足就是1种算法
			return arr[0] <= bag ? 2 : 1;
		}
		//中位数
		int mid = (arr.length - 1) / 2;
		//总的放法数
		long ways = 0L;
		//将原始数组分成左侧和右侧，以mid为划分界限
		TreeMap<Long, Long> leftMap = new TreeMap<>();
		//0 ~ mid
		ways += coreLogicCode(arr, 0, mid, 0, bag, leftMap);
		TreeMap<Long, Long> rightMap = new TreeMap<>();
		//mid ~ arr.length - 1
		ways += coreLogicCode(arr, mid + 1, arr.length - 1, 0, bag, rightMap);

		//右边的前缀和表：rightPreSumMap
		TreeMap<Long, Long> rightPreSumMap = new TreeMap<>();
		//前缀和
		long pre = 0L;
		for (Map.Entry<Long, Long> entry : rightMap.entrySet()) {
			//右边的rightMap
			//1 : 7
			//2 : 6
			//6 : 3
			//8 : 10
			//右边的前缀和表：rightPreSumMap
			//1 : 7
			//1, 2 : 13
			//1, 2, 6 : 16
			//1, 2, 6, 8 : 26
			//前缀和累加右侧表的value值
			pre += entry.getValue();
			rightPreSumMap.put(entry.getKey(), pre);
		}

		//严格
		for (Map.Entry<Long, Long> entry : leftMap.entrySet()) {
			//左边自由选择的累加和
			long leftSum = entry.getKey();
			//左边的放法数
			long lWays = entry.getValue();
			//右边自由选择的累加和
			Long rightSum = rightPreSumMap.floorKey(bag - leftSum);
			if (rightSum != null) {
				//右边的放法数
				long rWays = rightPreSumMap.get(rightSum);
				//总的放法数 累加 左边放法数 * 右边放法数
				ways += lWays * rWays;
			}
		}
		//左侧没有进行自由选择，以及右侧也没有自由选择，那么累加和就是0，0也是一种放法
		return ways + 1;
	}

	/**
	 * [3,3,3,3] bag = 6
	 * 0 1 2 3
	 * - - - -  累加和：0 -> （0 : 1）
	 * - - - $  累加和：3 -> （0 : 1）(3, 1)
	 * - - $ -  累加和：3 -> （0 : 1）(3, 2)
	 *
	 * @param arr   零食体积的数组
	 * @param index 从index开始自由选择
	 * @param end   到end截止自由选择
	 * @param sum   之前自由选择组成的累加和
	 * @param bag   背包容量
	 * @param map   零食体积数组arr[index ... end]自由选择，组成的所有累加和，不能超过bag背包容量，每一种累加和对应的
	 *              放法数，填写在map中，并且最后不能什么零食都没选
	 * @return 返回零食体积数组arr[index ... end]自由选择，组成的所有累加和，每一种累加和对应的放法数的总放法数
	 */
	public static long coreLogicCode(int[] arr, int index, int end, long sum, long bag, TreeMap<Long, Long> map) {
		//之前的累加和已经大于背包容量，没有必要继续放入零食了
		if (sum > bag) {
			return 0L;
		}
		//sum <= bag
		//表示所有的零食都已经自由选择放入完了
		if (index > end) {
			if (sum != 0) {
				if (!map.containsKey(sum)) {
					map.put(sum, 1L);
				} else {
					map.put(sum, map.get(sum) + 1L);
				}
				return 1L;
			}
			//sum == 0，表示没有放入任何零食
			else {
				return 0L;
			}
		}
		//sum <= bag && index <= end
		//背包不放人当前index位置的零食
		long methodCount = coreLogicCode(arr, index + 1, end, sum, bag, map);
		//背包放入当前index位置的零食
		methodCount += coreLogicCode(arr, index + 1, end, sum + arr[index], bag, map);
		return methodCount;
	}

	public static long ways(int[] arr, int bag) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] <= bag ? 2 : 1;
		}
		int mid = (arr.length - 1) >> 1;
		TreeMap<Long, Long> lmap = new TreeMap<>();
		long ways = func(arr, 0, mid, 0, bag, lmap);

		TreeMap<Long, Long> rmap = new TreeMap<>();
		ways += func(arr, mid + 1, arr.length - 1, 0, bag, rmap);

		TreeMap<Long, Long> rpre = new TreeMap<>();
		long pre = 0;
		for (Map.Entry<Long, Long> entry : rmap.entrySet()) {
			pre += entry.getValue();
			rpre.put(entry.getKey(), pre);
		}
		for (Map.Entry<Long, Long> entry : lmap.entrySet()) {
			long lweight = entry.getKey();
			long lways = entry.getValue();
			Long floor = rpre.floorKey(bag - lweight);
			if (floor != null) {
				long rways = rpre.get(floor);
				ways += lways * rways;
			}
		}
		return ways + 1;
	}


	// arr 30
	// func(arr, 0, 14, 0, bag, map)

	// func(arr, 15, 29, 0, bag, map)

	// 从index出发，到end结束
	// 之前的选择，已经形成的累加和sum
	// 零食[index....end]自由选择，出来的所有累加和，不能超过bag，每一种累加和对应的方法数，填在map里
	// 最后不能什么货都没选
	// [3,3,3,3] bag = 6
	// 0 1 2 3
	// - - - - 0 -> （0 : 1）
	// - - - $ 3 -> （0 : 1）(3, 1)
	// - - $ - 3 -> （0 : 1）(3, 2)
	public static long func(int[] arr, int index, int end, long sum, long bag, TreeMap<Long, Long> map) {
		if (sum > bag) {
			return 0;
		}
		// sum <= bag
		if (index > end) { // 所有商品自由选择完了！
			// sum
			if (sum != 0) {
				if (!map.containsKey(sum)) {
					map.put(sum, 1L);
				} else {
					map.put(sum, map.get(sum) + 1);
				}
				return 1;
			} else {
				return 0;
			}
		}
		// sum <= bag 并且 index <= end(还有货)
		// 1) 不要当前index位置的货
		long ways = func(arr, index + 1, end, sum, bag, map);

		// 2) 要当前index位置的货
		ways += func(arr, index + 1, end, sum + arr[index], bag, map);
		return ways;
	}

}
