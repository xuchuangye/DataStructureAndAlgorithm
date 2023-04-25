package com.mashibing.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 归并排序
 * 归并排序（MERGE-SORT）是利用归并的思想实现的排序方法
 * 该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解，
 * 而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。
 * <p>
 * 归并排序的稳定性：
 * 归并排序可以实现稳定性，==时，先将左边数组赋值到拷贝数组中，然后在将右边数组赋值到拷贝数组中
 *
 * @author xcy
 * @date 2022/3/21 - 17:25
 */
public class MergeSorting {
    public static void main(String[] args) {
       /* //------------------测试时长------------------
        Date dateStart = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String start = simpleDateFormat.format(dateStart);
        System.out.printf("归并排序前的时间：\n%s\n", start);

        System.out.println();
        int arrayMaxSize = 1200000000;
        int maxValue = 100000;
        int[] arr = generateRandomArray(arrayMaxSize, maxValue);
        //测试递归版本的归并排序的时长
        //递归版本太消耗资源了，推荐非递归版本
        //mergeSortRecursion(arr);

        //测试非递归版本的归并排序的时长
        mergeSortNoRecursion(arr);

        Date dateEnd = new Date();
        String end = simpleDateFormat.format(dateEnd);
        System.out.printf("归并排序后的时间：\n%s\n", end);*/

        //------------------测试排序算法是否正确------------------
        //测试次数
        int testTime = 1000000;
        //数组长度
        int length = 1000;
        //数组元素的值的范围
        int value = 1000;
        //是否测试成功，默认测试是成功的
        boolean success = true;
        for (int i = 1; i <= testTime; i++) {
            //生成随机长度值也随机的数组
            int[] array = generateRandomArray(length, value);
            //拷贝数组
            int[] arr = copyArray(array);

            //int[] temp = new int[arr.length];
            //自己实现的排序方式
            mergeSortRecursion(array);
            //mergeSortNoRecursion(array);
            //系统提供的排序方式
            sort(arr);

            //判断array和arr数组中的所有元素是否都相等
            if (!isEqual(array, arr)) {
                //如果不相等，则标记success为false
                success = false;
                printArray(array);
                printArray(arr);
                break;
            }

        }
        System.out.println(success ? "测试成功" : "测试失败");
    }

    /**
     * 时间复杂度：O(NlogN) -> 线性对数阶
     * 使用Master公式计算时间复杂度
     * T(N) = 2 * T(N / 2) + O(N) log以b为底数a为真数 == d，所以时间复杂度为：N ^ d * logN
     *
     * @param arr
     */
    public static void mergeSortRecursion(int[] arr) {
        //数组为空或者数组元素只有1个，没有排序的必要
        if (arr == null || arr.length < 2) {
            return;
        }
        //数组左边界
        int left = 0;
        //数组右边界
        int right = arr.length - 1;
        mergeSortRecursion(arr, left, right);
    }

    /**
     * 效率高一点
     * 数组元素的切分和合并 -> 递归的方式
     * <p>
     * 实现思路：
     * 一开始数组的左边界是left == 0，右边界是right == arr.length - 1
     * 根据确定中间索引，划分左边范围和右边范围，效果如下：
     * 左边范围：[left ... mid]右边范围：[mid + 1 ... right]
     * 中间索引：
     * (right + left) / 2，但是数组下标可能会越界，尤其是right无限接近arr.length，所以保险起见：left + ((right - left) / 2)
     * 左边范围：
     * [left ... mid]
     * 右边范围：
     * [mid + 1]
     * 可以看出：
     * 一开始的左边范围的左边界是left，右边界是mid
     * 然后是右边范围的左边界是mid + 1，右边界是right
     * <p>
     * 接着：
     * 继续在左边范围[left ... mid]上确定中间索引，划分左边范围和右边范围
     * 继续在右边范围[mid + 1 ... right]上确定中间索引，划分左边范围和右边范围
     * 当划分到左边范围只有一个数或者右边范围只有一个数时：left == right，一个数本身就有序，最底层的递归进行返回
     * 接着递归上一层的左边范围和右边范围，即使都是无序的，可以通过merge让其有序，merge代码看下面，依次类推，直到整个数组的范围有序
     *
     * @param arr   准备排序的原始数组
     * @param left  左边有序序列的起始位置
     * @param right 右边有序序列的起始位置
     */
    public static void mergeSortRecursion(int[] arr, int left, int right) {
        //left > right属于无效范围
        //left == right表示已经递归到只有一个数的左边范围或者右边范围
        if (left >= right) {
            return;
        }
        //中间索引，防止数组下标越界
        //int mid = (left + right) / 2;
        //int mid = left + ((right - left) / 2);
        int mid = left + ((right - left) >> 1);
        //向左递归进行分，也就是左边范围有序
        mergeSortRecursion(arr, left, mid);
        //向右递归进行分，也就是右边范围有序
        mergeSortRecursion(arr, mid + 1, right);
        //分之后，进行合并
        merge(arr, left, mid, right);
    }


    /**
     * 数组元素治的阶段以及数据拷贝
     * <p>
     * 举例：
     * 左边范围：          右边范围：
     * L         M       M+1       R
     * [4, 2, 3, 7]      [3, 9, 8, 7]
     * p1                p2
     * (1)
     * 拷贝数组temp的元素个数 = R - L + 1，temp数组的专属索引tempIndex
     * int[] temp = new int[R - L + 1]
     * (2)
     * p1指向左边范围，p1的有效边界L ~ M，p1从L开始
     * p2指向右边范围，p2的有效边界M+1 ~ R，p2从M+1开始
     * 所以while(p1 <= M && p2 <= R) {
     * 比较arr[p1]和arr[p2]的值，谁的值就拷贝到当前的temp数组中，如果相等默认拷贝arr[p1]的值
     * 1.
     * 当arr[p1]的值小于等于arr[p2]的值时
     * 将arr[p1]的值拷贝到temp的数组中，p1++继续指向左边范围的下一个位置，tempIndex++继续指向拷贝数组的下一个位置
     * 2.
     * 当arr[p2]的值小于arr[p1]的值时
     * 将arr[p2]的值拷贝到temp的数组中，p2++继续指向右边范围的下一个位置，tempIndex++继续指向拷贝数组的下一个位置
     * }
     * 3.
     * 当退出上述while循环时，肯定只有一个范围还有元素，所以两个while循环只会进入一个，将当前范围剩余的元素全部拷贝到temp数组中
     * while(p1 <= M) {
     * temp[tempIndex++] = arr[p1++];
     * }
     * 或者
     * while(p2 <= R) {
     * temp[tempIndex++] = arr[p2++];
     * }
     * 最后将temp的(R - L + 1)个元素覆盖到arr[L ... R]范围上的元素即可实现L ... R范围上的有序
     *
     * @param arr 准备排序的原始数组
     * @param L   左边范围的起始位置
     * @param M   划分左边范围和右边范围的中间索引
     * @param R   右边范围的起始位置
     */
    public static void merge(int[] arr, int L, int M, int R) {
        //额外空间，用于拷贝数据，长度与当前递归的范围元素个数相同
        int[] temp = new int[R - L + 1];
        //记录左边范围的起始位置的索引
        int p1 = L;
        //记录右边范围的起始位置的索引
        int p2 = M + 1;
        //记录temp数组的当前索引
        int tempIndex = 0;

        //左边有序序列从p1 == left到mid，右边有序序列从p2 == mid + 1到right
        //当左边范围的p1索引越界或者当右边范围的p2索引越界时，退出循环
        while (p1 <= M && p2 <= R) {
            //如果左边范围的当前元素 小于等于 右边范围的当前元素
            if (arr[p1] <= arr[p2]) {
                //那么就将左边范围的当前元素填充到temp数组中
                temp[tempIndex] = arr[p1];
                //当前索引tempIndex已经有元素了，++继续指向temp拷贝数组的下一个位置
                tempIndex++;
                //左边范围的当前p1位置的元素已经填充到temp数组中了，++继续指向左边范围的下一个位置
                p1++;
            }
            //如果右边范围的当前元素 小于 左边范围的当前元素
            else {
                //反之，将右边范围的当前元素填充到temp数组中
                temp[tempIndex] = arr[p2];
                //当前索引tempIndex已经有元素了，++继续指向temp拷贝数组的下一个位置
                tempIndex++;
                //右边范围的当前p2位置的元素已经填充到temp数组中了，++继续指向右边 范围的下一个位置
                p2++;
            }
        }

        //当上述while循环结果后，要么左边范围还有剩余元素，要么右边范围还有剩余元素，下面两个while循环只会进一个
        //如果左边范围有剩余的元素，就将剩余的元素全部填充到temp数组中
        while (p1 <= M) {
            //那么就将左边范围的当前元素填充到temp数组中
            temp[tempIndex] = arr[p1];
            //当前索引tempIndex已经有元素了，++继续指向temp拷贝数组的下一个位置
            tempIndex++;
            //左边范围的当前p1位置的元素已经填充到temp数组中了，++继续指向下一个索引
            p1++;
        }

        //反之，如果右边范围还有剩余的元素，就将剩余的元素全部填充到temp数组中
        while (p2 <= R) {
            //那么就将右边范围的当前元素填充到temp数组中
            temp[tempIndex] = arr[p2];
            //当前索引tempIndex已经有元素了，++继续指向temp拷贝数组的下一个位置
            tempIndex++;
            //右边范围的当前p2位置的元素已经填充到temp数组中了，++继续指向下一个索引
            p2++;
        }

        //填充完毕之后，就将temp数组中元素拷贝到原始数组arr中
        //i 表示temp 拷贝数组的个数
        for (int i = 0; i < temp.length; i++) {
            //为什么要从left开始拷贝，因为当前merge就是从left开始的，到right时merge结束
            //一共有多少个元素需要拷贝，也就是temp拷贝数组的长度，实际上就是right - left + 1个
            //举例：
            //[left ... right]
            //left = 3, right = 10
            //那么当前左边范围和右边范围的总共个数就是right - left + 1 = 8个，所以arr[left + i]
            arr[L + i] = temp[i];
        }
    }


    /**
     * 测试代码专用对数器
     * <p>
     * 生成随机长度的随机值的数组
     *
     * @param arrayMaxSize 随机长度
     * @param maxValue     随机值
     * @return 返回随机长度的元素都是随机值的数组
     */
    public static int[] generateRandomArray(int arrayMaxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * arrayMaxSize)];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    /**
     * 测试代码专用对数器
     * <p>
     * 拷贝数组
     *
     * @param arr 准备拷贝的数组
     * @return 拷贝之后的新数组
     */
    public static int[] copyArray(int[] arr) {
        int[] result = new int[arr.length];

		/*for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}*/
        System.arraycopy(arr, 0, result, 0, arr.length);
        return result;
    }

    /**
     * 测试代码专用对数器
     * <p>
     * 系统比较器 --> 系统提供的排序方法
     *
     * @param arr 准备
     */
    public static void sort(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * 测试代码专用对数器
     * <p>
     * 判断数组arr1和数组arr2中的所有元素是否都相等
     *
     * @param arr1 数组arr1
     * @param arr2 数组arr2
     * @return 如果数组arr1和arr2的所有元素都相等则返回true，否则返回false
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        //数组arr1和arr2数组的长度必须一致
        if (arr1.length != arr2.length) {
            return false;
        }
        //数组arr1和arr2数组中的元素只要有一个不相等就返回false
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 测试专用对数器
     * <p>
     * 打印输出数组
     *
     * @param arr 数组
     */
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    /**
     * TODO：测试用的，不要介意
     * 效率还行
     * 合并并排序左侧数组和右侧数组，最终合并排序数组 -> 非递归的方式
     * <p>
     * 时间复杂度：O(NlogN)
     *
     * @param arr 原始数组
     */
   /* public static void mergeSortNoRecursion(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        //步长，从1开始，每次乘2
        int step = 1;
        //数组的长度
        int N = arr.length;

        while (step < N) {
            //第一个步长为step的左边的数组起始索引
            int L = 0;
            //第一个步长为step的左边的数组结束索引
            int mid = 0;
            //第一个步长为step的右边的数组起始索引
            int right = 0;
            while (L < N) {
                //从left开始，到length - 1结束，能否满足step个元素进行合并排序
                //length - 1 - left + 1   --> length - left
                if (N - L >= step) {
                    //如果满足上述 条件，那么第一个步长为step的左边的数组结束索引就已经找到
                    mid = L + step - 1;
                } else {
                    mid = N - 1;
                }
                //mid == length - 1，表示后续已经没有元素可以进行合并排序了，直接退出循环
                if (mid == N - 1) {
                    break;
                }

                //length - 1 - mid + 1 - 1 --> length - 1 - mid
                //从mid + 1开始，到length - 1结束，能否满足step个元素进行合并排序
                if (N - 1 - mid >= step) {
                    //如果满足上述 条件，那么第一个步长为step的右边的数组起始索引就已经找到
                    right = mid + 1 + step - 1;
                } else {
                    right = N - 1;
                }
                //合并排序
                merge(arr, L, mid, right);
                //如果right已经是最后一个元素，那么就表示后续已经没有元素可以合并排序，直接退出循环
                if (right == N - 1) {
                    break;
                } else {
                    //否则right + 1就作为第二个左边数组的起始索引
                    L = right + 1;
                }
            }

            //防止step * 2 超出Integer.MAX_VALUE，所以先判断step能否继续乘以2
            //注意length >> 1可能存在向下取整的问题，所以step == (length / 2)会导致最后一组没有进行合并交换
            //if (step > (length / 2)) {
            if (step > (N >> 1)) {
                break;
            } else {
                //step *= 2;
                step <<= 1;
            }
        }
    }*/

    /**
     * 归并排序 -> 非递归的方式
     *
     * @param arr 原始无序数组
     */
    public static void mergeSortNoRecursion(int[] arr) {
        //如果数组为null或者数组元素个数 < 2，没有排序的必要
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

}
