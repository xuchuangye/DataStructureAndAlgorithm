package com.mashibing.day16;

import com.mashibing.common.TestUtils;

/**
 * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
 * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
 * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
 * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
 * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
 * 接下来根据reverse对arr进行调整：
 * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数为一组，然后每个小组内部逆序，那么arr变成[3,1,4,2]，此时有3个逆序对。
 * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数为一组，然后每个小组内部逆序，那么arr变成[1,3,2,4]，此时有1个逆序对
 * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数为一组，然后每个小组内部逆序，那么arr变成[1,3,2,4]，此时有1个逆序对。
 * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数为一组，然后每个小组内部逆序，那么arr变成[4,2,3,1]，此时有5个逆序对。
 * 所以返回[3,1,1,5]，表示每次调整之后的逆序对数量。
 * 输入数据状况：
 * power的范围[0,20]
 * arr长度范围[1,10的7次方]
 * reverse长度范围[1,10的6次方]
 * <p>
 * 解题思路：
 * 1.arr[]表示原始数组，长度一定为2的power次方
 * 2.reverse[]表示原始数组如何进行逆序，元素值表示原始数组以2的reverse[i]次方划分为一组，然后进行逆序，最后收集逆序对
 * reverse[i]值的范围在0 ~ power之间
 * <p>
 * 解题过程：
 * 1.
 * 假设arr[] = {6, 4, 1, 3, 2, 5, 0, 7}
 * index =  0  1  2  3  4  5  6  7
 * 以0位置开始的逆序对，{6,4},{6,1},{6,3},{6,2},{6,5},{6,0}一共6对
 * 以1位置开始的逆序对，{4,1},{4,3},{4,2},{4,0}一共4对
 * 以2位置开始的逆序对，{1,0}一共1对
 * 以3位置开始的逆序对，{3,2},{1,0}一共2对
 * 以4位置开始的逆序对，{2,0}一共1对
 * 以5位置开始的逆序对，{5,0}一共1对
 * 总共15对逆序对
 * 2.
 * 现在按照2的某次方划分为一组
 * 以2的1次方划分为一组，{6,4}一共1对
 * 以2的2次方划分为一组，{6,1},{6,3},{4,1},{4,3},{2,0},{5,0}一共6对
 * 以2的3次方划分为一组，{6,2},{6,5},{6,0},{4,2},{4,0},{1,0},{3,2},{3,0}一共8对
 * 总共15对逆序对
 * 3.
 * 假设reverse[i] = 2，表示原始数组以2的2次方划分为一组，查看有多少个逆序对
 * 2的1次方，逆序对为a，正序对为b
 * 2的2次方，逆序对为c，正序对为d
 * 2的3次方，逆序对为e，正序对为f
 * 那么受影响的只有2的1次方的逆序对和正序对，以及2的2次方的逆序对和正序对
 * 2的3次方的逆序对和正序对不会受到影响
 * 4.
 * 因此原始数组根据长度进行2的1次方划分为一组，2的2次方划分为一组，直到2的power次方划分为一组
 * 求出2的1次方划分为一组的逆序对
 * 2的2次方划分为一组的逆序对
 * ...
 * 2的power次方划分为一组的逆序对
 * 进行累加就是最终答案
 *
 * @author xcy
 * @date 2022/8/5 - 14:32
 */
public class Code04_MergeRecord {
	public static void main(String[] args) {
		int powerMax = 8;
		int msizeMax = 10;
		int value = 30;
		int testTime = 50000;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int power = (int) (Math.random() * powerMax) + 1;
			int msize = (int) (Math.random() * msizeMax) + 1;
			int[] originArr = TestUtils.generateRandomOriginArray(power, value);
			int[] originArr1 = copyArray(originArr);
			int[] originArr2 = copyArray(originArr);
			int[] reverseArr = TestUtils.generateRandomReverseArray(msize, power);
			int[] reverseArr1 = copyArray(reverseArr);
			int[] reverseArr2 = copyArray(reverseArr);
			int[] ans1 = reversePair1(originArr1, reverseArr1, power);
			int[] ans2 = reversePair2(originArr2, reverseArr2, power);
			if (!TestUtils.isEqual(ans1, ans2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

		powerMax = 20;
		msizeMax = 1000000;
		value = 1000;
		int[] originArr = TestUtils.generateRandomOriginArray(powerMax, value);
		int[] reverseArr = TestUtils.generateRandomReverseArray(msizeMax, powerMax);
		// int[] ans1 = reversePair1(originArr1, reverseArr1, powerMax);
		long start = System.currentTimeMillis();
		reversePair2(originArr, reverseArr, powerMax);
		long end = System.currentTimeMillis();
		System.out.println("run time : " + (end - start) + " ms");
	}

	/*
	 * 腾讯原题
	 *
	 * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
	 * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
	 * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
	 * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
	 * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
	 * 接下来根据reverse对arr进行调整：
	 * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [3,1,4,2]，此时有3个逆序对。
	 * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [1,3,2,4]，此时有1个逆序对
	 * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [1,3,2,4]，此时有1个逆序对。
	 * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数一组，然后每个小组内部逆序，那么arr变成
	 * [4,2,3,1]，此时有5个逆序对。
	 * 所以返回[3,1,1,5]，表示每次调整之后的逆序对数量。
	 *
	 * 输入数据状况：
	 * power的范围[0,20]
	 * arr长度范围[1,10的7次方]
	 * reverse长度范围[1,10的6次方]
	 *
	 * */

	public static int[] reversePair1(int[] originArr, int[] reverseArr, int power) {
		int[] ans = new int[reverseArr.length];
		for (int i = 0; i < reverseArr.length; i++) {
			reverseArray(originArr, 1 << (reverseArr[i]));
			ans[i] = countReversePair(originArr);
		}
		return ans;
	}

	public static void reverseArray(int[] originArr, int teamSize) {
		if (teamSize < 2) {
			return;
		}
		for (int i = 0; i < originArr.length; i += teamSize) {
			reversePart(originArr, i, i + teamSize - 1);
		}
	}

	public static void reversePart(int[] arr, int L, int R) {
		while (L < R) {
			int tmp = arr[L];
			arr[L++] = arr[R];
			arr[R--] = tmp;
		}
	}

	public static int countReversePair(int[] originArr) {
		int ans = 0;
		for (int i = 0; i < originArr.length; i++) {
			for (int j = i + 1; j < originArr.length; j++) {
				if (originArr[i] > originArr[j]) {
					ans++;
				}
			}
		}
		return ans;
	}

	public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
		//逆序数组
		int[] reverse = copyArray(originArr);
		reverseArray(reverse, 0, reverse.length - 1);
		//逆序对数组
		int[] reverseOrder = new int[power + 1];
		//正序对数组
		int[] positiveOrder = new int[power + 1];

		process(originArr, 0, originArr.length - 1, power, reverseOrder);
		process(reverse, 0, reverse.length - 1, power, positiveOrder);

		int[] ans = new int[reverseArr.length];
		for (int i = 0; i < reverseArr.length; i++) {
			int curPower = reverseArr[i];
			for (int pow = 1; pow <= curPower; pow++) {
				int temp = reverseOrder[pow];
				reverseOrder[pow] = positiveOrder[pow];
				positiveOrder[pow] = temp;
			}
			for (int pow = 1; pow <= power; pow++) {
				ans[i] += reverseOrder[pow];
			}
		}
		return ans;
	}

	/**
	 * 拷贝数组
	 *
	 * @param arr 原始数组
	 * @return 返回拷贝的数组
	 */
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	/**
	 * 对原始数组的L ~ R范围内进行逆序
	 *
	 * @param arr 原始数组
	 * @param L   L
	 * @param R   R
	 */
	public static void reverseArray(int[] arr, int L, int R) {
		while (L < R) {
			int temp = arr[L];
			arr[L++] = arr[R];
			arr[R--] = temp;
		}
	}

	/**
	 * 该方法完成对arr[]的L ~ R范围内进行排序
	 *
	 * @param arr    数组
	 * @param L      L
	 * @param R      R
	 * @param power  L ~ R范围上的power次方
	 * @param record arr[]的左部分和右部分记录在该数组中
	 */
	public static void process(int[] arr, int L, int R, int power, int[] record) {
		if (L >= R) {
			return;
		}
		int mid = L + ((R - L) >> 1);
		//左边部分，L ~ mid，每次调用递归，power - 1
		process(arr, L, mid, power - 1, record);
		//右边部分，mid + 1 ~ R
		process(arr, mid + 1, R, power - 1, record);
		//+= merge(arr, L, mid, R)表示有多种次方的逆序对
		//按照2的1次方划分为一组的逆序对
		//按照2的2次方划分为一组的逆序对
		//按照2的3次方划分为一组的逆序对
		//...
		//按照2的power次方划分为一组的逆序对
		//并且按照2的1次方划分为一组时，相邻的一组的逆序对也需要累加
		record[power] += merge(arr, L, mid, R);
	}

	/**
	 * @param arr
	 * @param L
	 * @param Mid
	 * @param R
	 * @return
	 */
	public static int merge(int[] arr, int L, int Mid, int R) {
		int p1 = L;
		int p2 = Mid + 1;
		int[] help = new int[R - L + 1];
		int index = 0;
		//收集逆序对
		int ans = 0;
		while (p1 <= Mid && p2 <= R) {
			ans += arr[p1] > arr[p2] ? (Mid - p1 + 1) : 0;
			help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= Mid) {
			help[index++] = arr[p1++];
		}
		while (p2 <= R) {
			help[index++] = arr[p2++];
		}
		/*for (int i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}*/
		if (help.length >= 0) System.arraycopy(help, 0, arr, L, help.length);
		return ans;
	}
}
