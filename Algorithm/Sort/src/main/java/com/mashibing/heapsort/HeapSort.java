package com.mashibing.heapsort;

import com.mashibing.common.SortCommonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 堆排序
 *
 * @author xcy
 * @date 2022/4/19 - 17:08
 */
public class HeapSort {
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
        //测试次数
        int testTime = 1000000;
        //数组长度
        int length = 100;
        //数组元素的值的范围
        int value = 100000;
        //是否测试成功，默认测试是成功的
        boolean success = true;
        for (int i = 1; i <= testTime; i++) {
            //生成随机长度值也随机的数组
            int[] array = SortCommonUtils.generateRandomArray(length, value);
            //拷贝数组
            int[] arr = SortCommonUtils.copyArray(array);

            //自己实现的排序方式
            BigRootHeap.heapSort(array);
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
}
