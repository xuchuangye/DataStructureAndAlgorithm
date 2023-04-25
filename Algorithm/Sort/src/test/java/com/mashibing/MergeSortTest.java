package com.mashibing;

import com.mashibing.common.SortCommonUtils;

/**
 * @author xcy
 * @date 2022/3/21 - 17:47
 */
public class MergeSortTest {

    public static void main(String[] args) {
        //int[] arr = {7, 4, 2, 1, 3, 5, 8, 6};
        //mergeSort(arr, 0, arr.length - 1, temp);
        //System.out.println(Arrays.toString(arr));

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

            //int[] temp = new int[arr.length];
            //自己实现的排序方式
            mergeSortRecursion(array, 0, array.length - 1);
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
        System.out.println(success ? "测试成功" : "测试失败");
    }

    /**
     * 归并排序 -> 递归的方式
     * <p>
     * 时间复杂度：O(NlogN) -> 线性对数阶
     * 使用Master公式计算时间复杂度
     * T(N) = 2 * T(N / 2) + O(N) log以b为底数a为真数 == d，所以时间复杂度为：N ^ d * logN
     *
     * @param arr   原始无序数组
     * @param left  原始无序数组的左边界
     * @param right 原始无序数组的右边界
     */
    public static void mergeSortRecursion(int[] arr, int left, int right) {
        //没有比较的必要
        if (arr == null || arr.length < 2) {
            return;
        }
        //防止出现栈溢出的异常
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        //数组分为左边数组和右边数组两组，左边数组和右边数组都已经有序
        process(arr, left, right);
        //让两个数组合并
        merge(arr, left, mid, right);
    }

    /**
     * 归并排序 -> 非递归的方式
     *
     * @param arr   原始无序数组
     * @param left  原始无序数组的左边界
     * @param right 原始无序数组的右边界
     */
    public static void mergeSortNoRecursion(int[] arr, int left, int right) {
        if (arr == null || arr.length < 2) {
            return;
        }

        //合并元素的步长，起始是1，每次乘以2
        int mergeStep = 1;

        int N = arr.length;
        while (mergeStep < N) {
            //当前每次合并的左边数组的左边界索引，从0开始
            int L = 0;
            while (L < N) {
                //当前每次合并的左边数组的右边界索引
                //当前合并的左边数组的右边界索引M = 当前合并的左边数组的左边界 + 步长 - 1
                int M = L + mergeStep - 1;
                //如果M超出数组的最后一个元素，表示越界，直接返回
                if (M >= N) {
                    break;
                }
                //当前每次合并的右边数组的右边界索引
                //当前合并的右边数组的右边界索引R，如果能够划分右边数组，R = M + mergeStep
                //如果不够划分右边数组，R = arr.length - 1
                int R = Math.min(M + mergeStep, N - 1);
                merge(arr, L, M, R);
                //下一个 合并的左边数组的左边界是当前合并的右边数组的右边界的下一个索引
                L = R + 1;
            }

            //防止数据溢出
            if (mergeStep > N / 2) {
                break;
            }
            //每次循环合并元素的步长 * 2
            mergeStep <<= 1;
        }
    }

    /**
     * 数组分为左边数组和右边数组，递归使其两个数组有序
     *
     * @param arr   原始无序数组
     * @param left  原始无序数组的左边界
     * @param right 原始无序数组的右边界
     */
    public static void process(int[] arr, int left, int right) {
        //防止出现栈溢出的异常
        if (left >= right) {
            return;
        }
        //取出中间索引
        int mid = left + (right - left) / 2;
        //数组分为左边数组和右边数组两组
        //1、递归让左边数组有序
        process(arr, left, mid);
        //2、递归让右边数组有序
        process(arr, mid + 1, right);
        merge(arr, 0, mid, arr.length - 1);
    }

    /**
     * 数组分为左边数组和右边数组，将两个数组进行合并
     *
     * @param arr   原始无序数组
     * @param left  原始无序数组的左边界
     * @param right 原始无序数组的右边界
     */
    public static void merge(int[] arr, int left, int mid, int right) {
        //拷贝数组temp
        int[] temp = new int[right - left + 1];
        //让cur1指向左边有序数组
        int cur1 = left;
        //让cur2指向右边有序数组
        int cur2 = mid + 1;
        //循环指向temp数组的索引
        int index = 0;
        //cur1不能超出mid，cur2不能超出right，边界问题
        while (cur1 <= mid && cur2 <= right) {
            temp[index++] = arr[cur1] <= arr[cur2] ? arr[cur1++] : arr[cur2++];
        }

        //上边的循环结束之后，左边有序数组有剩余或者右边有序数组有剩余
        while (cur1 <= mid) {
            temp[index++] = arr[cur1++];
        }

        while (cur2 <= right) {
            temp[index++] = arr[cur2++];
        }

        //最终将temp数组赋值给arr
        //System.arraycopy(temp, 0, arr, 0, temp.length);
        for (int i = 0; i < temp.length; i++) {
            arr[left + i] = temp[i];
        }
    }
}
