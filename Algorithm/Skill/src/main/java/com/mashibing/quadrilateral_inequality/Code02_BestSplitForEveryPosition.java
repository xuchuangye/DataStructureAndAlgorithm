package com.mashibing.quadrilateral_inequality;

import com.mashibing.common.SkillUtils;

/**
 * 题目二：
 * 把题目一中提到的，
 * min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说：
 * S(N-1)：在arr[0…N-1]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 现在要求返回一个长度为N的s数组，
 * s[i] =在arr[0…i]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 得到整个s数组的过程，做到时间复杂度O(N)
 * <p>
 * 思路分析：
 * 只需要证明左右分界线不需要回退即可，也就是0 ~ i的最优解在0 ~ i + 1的时候不需要回退
 * 举例：
 * 1.假设0 ~ i中，划分出左右两个区域，并且左边是瓶颈值
 * -  左   |    右
 * 0 ... 9 | 10 ... 17   18
 * -       |
 * 1) 0 ~ 17，左边是瓶颈值，分界线不需要往左滑动，也就是不回退
 * <p>
 * 2.假设0 ~ i中，划分出左右两个区域，并且右边是瓶颈值
 * -  左   |    右
 * 0 ... 9 | 10 ... 17   18
 * -       |
 * 1) 0 ~ 17，右边是瓶颈值，当18位置的数值进来之后，右边不再是是瓶颈值，而左边是瓶颈值，并且成为0 ~ i + 1的最优解，
 * 分界线不需要往左滑动，也就是不回退
 * 举例：
 * -     |
 * [3, 5,|2, 4] 6
 * -     |
 * 0 ~ 3的最优解：左边累加和是8，右边累加和是6，那么最小值6是整个数组中的最优解
 * 此时4位置的6加入进来，0 ~ 4的最优解：左边累加和是8，右边累加和是12，8成为了最优解，分界线不需要往左滑动，也就是不回退
 * 2) 0 ~ 17，右边是瓶颈值，当18位置的数值进来之后，右边依然是瓶颈值，分界线不需要往左滑动，也就是不回退，因为如果此时
 * 分界线能够往左滑动，那么之前就已经能够往左滑动了
 * 举例：
 * -       |
 * [3, 100,|8, 9] 10
 * -       |
 * 0 ~ 3的最优解：左边累加和是103，右边累加和是17，那么最小值17是整个数组中的最优解
 * 此时4位置的10加入进来，0 ~ 4的最优解：左边累加和是103，右边累加和是27，27成为了最优解，分界线不需要往左滑动，也就是
 * 不回退
 * <p>
 * 3.总结：只要确定0 ~ i的最优解，也就是最优的分界线，那么确定0 ~ i + 1的最优解时，分界线不需要往左滑动，也就是不回退
 * -    |
 * 0 ———|——— i
 * -    | -> |
 * -         |
 * 0 ————————|—— i + 1
 * -         | -> |
 * -              |
 * 0 —————————————|—— i + 2
 * -              |
 *
 * @author xcy
 * @date 2022/6/12 - 8:37
 */
public class Code02_BestSplitForEveryPosition {
	public static void main(String[] args) {
		int N = 20;
		int max = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N);
			int[] arr = SkillUtils.randomArray(len, max);
			int[] ans1 = logarithmic_Comparator1(arr);
			int[] ans2 = logarithmic_Comparator2(arr);
			int[] ans3 = bestSplitForEveryPosition(arr);
			if (!SkillUtils.isSameArray(ans1, ans2) || !SkillUtils.isSameArray(ans1, ans3)) {
				System.out.println("测试错误!");
			}
		}
		System.out.println("测试结束");
	}

	/**
	 * 对数器
	 * 时间复杂度：O(N³)
	 *
	 * @param arr
	 * @return
	 */
	public static int[] logarithmic_Comparator1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		int[] ans = new int[N];
		ans[0] = 0;
		for (int range = 1; range < N; range++) {
			for (int s = 0; s < range; s++) {
				int sumL = 0;
				for (int L = 0; L <= s; L++) {
					sumL += arr[L];
				}
				int sumR = 0;
				for (int R = s + 1; R <= range; R++) {
					sumR += arr[R];
				}
				ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
			}
		}
		return ans;
	}

	/**
	 * 对数器
	 * 时间复杂度：O(N²)
	 *
	 * @param arr
	 * @return
	 */
	public static int[] logarithmic_Comparator2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		int[] ans = new int[N];
		ans[0] = 0;
		int[] sum = new int[N + 1];
		for (int i = 0; i < N; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		for (int range = 1; range < N; range++) {
			for (int s = 0; s < range; s++) {
				int sumL = sum(sum, 0, s);
				int sumR = sum(sum, s + 1, range);
				ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
			}
		}
		return ans;
	}

	/**
	 *
	 * @param arr
	 * @return 返回arr[]每个位置上的最优划分所得到的min{左部分累加和，右部分累加和}的最大值的新的数组
	 */
	public static int[] bestSplitForEveryPosition(int[] arr) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}

		//arr =    {3, 1, 2, 2, 3}
		//          0  1  2  3  4
		//sum = {0, 3, 4, 6, 8, 11}
		//       0  1  2  3  4  5
		//arr[0 ~ 2]的累加和 = sum[2 + 1] - sum[0]
		//arr[0 ~ 2]的累加和  = 6
		//sum[2 + 1] - sum[0] = 6 - 0 = 6
		//sum[]多补充一个0，那么就不需要再处理边界的问题了
		int[] sum = new int[arr.length + 1];
		for (int i = 0; i < arr.length; i++) {
			sum[i + 1] = sum[i] + arr[i];
		}
		int[] ans = new int[arr.length];
		ans[0] = 0;
		//range范围：1 ~ arr.length - 1
		//arr数组分成左右两部分，左边部分[0 ~ best]，右边部分[best + 1 ~ range - 1]
		//best表示最优划分，如果best + 1 == range，表示已经没有办法进行划分了
		int best = 0;
		for (int range = 1; range < arr.length; range++) {
			//best + 1 == range表示
			while (best + 1 < range) {
				//分界线往右滑动之前
				int before = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
				//分界线往右滑动之后
				int after = Math.min(sum(sum, 0, best + 1), sum(sum, best + 2, range));

				//如果分界线滑动之后的累加和的最小值大于分界线滑动之前的累加和的最小值
				//表示分界线滑动之后的累加和的最小值是最优解，那么就继续往右滑动
				//举例：
				//arr[] = {3, 2, 4, 2, 5}
				//分界线 ->   |
				//左边累加和3，右边累加和13，最小值3
				//分界线 ->      |
				//左边累加和5，右边累加和11，最小值5
				//分界线 ->         |
				//左边累加和9，右边累加和7，最小值7
				//分界线 ->            |
				//左边累加和11，右边累加和5，最小值5，最小值变小了，就不能继续往右滑动，退出循环
				if (after >= before) {
					best++;
				}
				//否则分界线滑动之后的累加和的最小值不是最优解，分界线往右滑动之前才是最优解，直接退出循环
				else {
					break;
				}
			}
			//退出while循环时，累加和的最小值已经是最优解了
			ans[range] = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
		}

		return ans;
	}

	/**
	 * @param sum 累加和数组
	 * @param L   左边界
	 * @param R   右边界
	 * @return 返回arr[]中，L - R范围的累加和
	 */
	public static int sum(int[] sum, int L, int R) {
		return sum[R + 1] - sum[L];
	}
}
