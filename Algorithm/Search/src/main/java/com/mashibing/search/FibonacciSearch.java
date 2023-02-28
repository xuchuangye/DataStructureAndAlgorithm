package com.mashibing.search;

import java.util.Arrays;

/**
 * 斐波那契查找
 *
 * 斐波那契查找的原理
 * 斐波那契查找原理与前两种相似，仅仅改变了中间结点（mid）的位置，
 * mid不再是中间或插值得到，而是位于黄金分割点附近，即mid=low+F(k-1)-1（F代表斐波那契数列）
 *
 * 对F(k-1)-1的理解：
 * 由斐波那契数列 F[k]=F[k-1]+F[k-2] 的性质，可以得到（F[k]-1）=（F[k-1]-1）+（F[k-2]-1）+1。
 *
 * 示意图--------------------------------------------
 *                  F[k - 1]
 *
 * low|             |mid|            |high
 *
 *     F[k - 1] - 1      F[k - 2] - 1
 * 示意图--------------------------------------------
 *
 * 该式说明：只要顺序表的长度为F[k]-1，则可以将该表分成长度为F[k-1]-1和F[k-2]-1的两段，即如上图所示。
 * 从而中间位置为mid=low+F(k-1)-1
 * 类似的，每一子段也可以用相同的方式分割
 * 但顺序表长度n不一定刚好等于F[k]-1，所以需要将原来的顺序表长度n增加至F[k]-1。
 * 这里的k值只要能使得F[k]-1恰好大于或等于n即可，由以下代码得到,
 *
 * while(n > F[k] - 1) {
 *     k++;
 * }
 *
 * 顺序表长度增加后，新增的位置（从n+1到F[k]-1位置），都赋为n位置的值即可。
 *
 * 斐波那契查找的注意事项
 * 一定要在一个有序数组中进行使用
 *
 * @author xcy
 * @date 2022/3/23 - 15:27
 */
public class FibonacciSearch {
	public static final int ARRAY_LENGTH = 20;

	public static void main(String[] args) {
		int[] arr = {20, 30, 40, 50, 60, 100, 1024};
		System.out.println("index = " + fibonacciSearch(arr, 10245));
	}

	/**
	 * 获取斐波那契数列
	 *
	 * @return 斐波那契数列
	 */
	public static int[] fibonacci() {
		int[] array = new int[ARRAY_LENGTH];
		array[0] = 1;
		array[1] = 1;
		for (int i = 2; i < ARRAY_LENGTH; i++) {
			array[i] = array[i - 1] + array[i - 2];
		}
		return array;
	}

	/**
	 * 斐波那契查找
	 *
	 * @param arr 数组
	 * @param key 查找的值
	 * @return 返回查找的值的索引
	 */
	public static int fibonacciSearch(int[] arr, int key) {
		//左边的索引
		int low = 0;
		//右边的索引
		int high = arr.length - 1;
		//
		int mid = 0;
		//获取斐波那契数列的黄金分割点对应的索引
		int k = 0;
		//获取斐波那契数列
		int[] fibonacci = fibonacci();

		//获取到斐波那契的黄金分割点对应的索引，fibonacci[k]对应的值表示最少需要多少个元素才能使用斐波那契
		while (high > fibonacci[k] - 1) {
			k++;
		}
		//因为数组fibonacci[k]的值表示如果需要使用斐波那契，最少需要多少个元素才能完成
		//所以如果数组fibonacci的元素个数大于查找数组arr的元素个数，就需要进行扩容
		//使用Arrays.copyOf构造一个新的数组temp，并且将arr数组中的元素全部拷贝进去
		//除去arr数组中的元素，剩余索引上的值使用0进行填充
		int[] temp = Arrays.copyOf(arr, fibonacci[k]);

		//0肯定是不合适的，所以直接使用arr的最高位high的值将所有的0都进行覆盖
		//所以循环遍历从high + 1开始，到temp数组的最后一个元素截止，都使用arr[high]将0进行覆盖
		//比如：temp[] = {20, 30, 40, 50, 60, 100, 1024, 0} --> temp[] = {20, 30, 40, 50, 60, 100, 1024, 1024}
		for (int i = high + 1; i < temp.length; i++) {
			temp[i] = arr[high];
		}

		//使用循环找到需要查找的索引key
		while (low <= high) {
			//斐波那契数列的中间位置对应的索引
			mid = low + fibonacci[k - 1] - 1;
			//如果查找的值key小于temp数组中间索引mid对应的值时，继续向斐波那契数列的左边查找
			if (key < temp[mid]) {
				//将斐波那契数列的查找范围从 low 到 high 缩小到从 low 到 high = mid - 1
				high = mid - 1;
				//mid = low + fibonacci[k - 1] - 1
				//fibonacci[k - 1] = fibonacci[k - 2] + fibonacci[k - 3]
				k--;
			}
			//如果查找的值key大于temp数组中间索引mid对应的值时，继续向斐波那契数列的右边查找
			else if (key > temp[mid]) {
				//将斐波那契数列的查找范围从 low 到 high 缩小到从 low = mid + 1 到 high
				low = mid + 1;
				k -= 2;
			} else {
				//需要确定的是返回哪个索引
				if (mid <= high) {
					return mid;
				} else {
					return high;
				}
			}
		}
		return -1;
	}
}
