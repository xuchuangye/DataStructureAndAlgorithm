package com.mashibing.heapsort;

/**
 * 大根堆
 * <p>
 * 1）堆结构就是用数组实现的完全二叉树结构
 * 2）完全二叉树中如果每棵子树的最大值都在顶部就是大根堆
 * 3）完全二叉树中如果每棵子树的最小值都在顶部就是小根堆
 * 4）堆结构的heapInsert与heapify操作
 * 5）堆结构的增大和减少
 * 6）优先级队列结构，就是堆结构
 * <p>
 * 1，先让整个数组都变成大根堆结构，建立堆的过程:
 * 1)从上到下的方法，时间复杂度为O(N*logN)
 * 2)从下到上的方法，时间复杂度为O(N)
 * 2，把堆的最大值和堆末尾的值交换，然后减少堆的大小之后，再去调整堆，一直周而复始，时间复杂度为O(N*logN)
 * 3，堆的大小减小成0之后，排序完成
 *
 * @author xcy
 * @date 2022/4/19 - 16:14
 */
public class BigRootHeap {
    public static void main(String[] args) {

    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //方式一：循环遍历依次插入数组的每一个元素，建立大根堆
        //往上浮，O(N * logN)
        //大量的数是高度比较深的往上浮，比较和交换的次数较多，少量的数是高度比较低的往上浮，比较和交换的次数较少
        //因此时间复杂度比较差
        /*for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }*/
        //方式二：直接给一组数据，建立大根堆
        //往下沉，O(N)
        //大量的数是高度比较低的往下沉，比较和交换的次数较少，少量的数是高度比较深的往下沉，比较和交换的次数较多
        //因此时间复杂度比较好
        for (int i = arr.length; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        //大根堆的节点个数
        int heapSize = arr.length;
        //此时数组第一个元素一定是最大的，跟数组的最后一个元素进行交换
        //所以heapSize需要先--，也就是逻辑删除最后一个元素，让最后一个元素脱离整棵完全二叉树
        swap(arr, 0, --heapSize);
        //O(N * logN)
        while (heapSize > 0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    /**
     * 用户在堆结构中插入节点
     * 该方法判断当前子节点和父节点的关系根据大根堆的规则能否进行交换
     * <p>
     * 时间复杂度：O(logN)
     * 两个限制条件：
     * 1、如果当前节点已经是根节点，退出while循环
     * 2、如果当前节点不是根节点，但是当前节点的权值比父节点的权值小，退出while循环
     *
     * @param arr   数组
     * @param index 当前节点的索引，也就是新插入进来的数所在的位置，请依次往上和父节点的值进行比较
     *              如果大于父节点的值，就和父节点的值进行交换，并且来到父节点的位置
     *              如果小于父节点的值或者当前节点的值已经来到了根节点的位置，循环结束
     */
    public static void heapInsert(int[] arr, int index) {
        //arr[index]表示当前节点的值，arr[(index - 1) / 2]表示当前节点的父节点的值
        //该while循环包含两个条件
        //1、判断当前节点是否是根节点，如果是，退出while循环
        //arr[0] 是否大于 arr[(0 - 1) / 2]，肯定不大于，根节点和自己比较值，不可能出现大于的情况，所以退出循环
        //2、判断当前节点的值是否大于父节点的值，如果当前节点的值小于父节点的值退出while循环
        //当前节点的值没有来到根节点，但是当前节点的值确实小于父节点的值，也会退出循环
        while (arr[index] > arr[(index - 1) / 2]) {
            //当前节点的值与父节点的值进行交换
            swap(arr, index, (index - 1) / 2);
            //如果当前节点的值大于父节点的值，当前节点来到父节点的位置
            index = (index - 1) / 2;
        }
    }

    /**
     * 从index开始，往下看，不断的下沉
     * <p>
     * 用户能pop出大根堆中的最大值，并且依然能够维持大根堆的结构和规则
     * 该方法判断当前父节点和子节点的关系根据大根堆的规则能否进行交换
     * <p>
     * 时间复杂度：O(logN)
     *
     * @param arr      数组
     * @param index    当前节点的索引
     * @param heapSize 堆结构中节点的个数
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        //首先确定当前节点的左子节点
        int left = 2 * index + 1;
        //如果左孩子节点越界，那么右孩子节点一定会越界，因为左孩子节点的索引小于右孩子节点
        //看看左孩子节点是否越界，因为heapSize管着整个完全二叉树的长度
        while (left < heapSize) {
            //如果有左孩子节点，那么可能有也可能没有右孩子节点
            //判断当前节点的左子节点和右子节点的权值大小，返回权值较大的索引
            //情况一：
            //1、left + 1就是右孩子节点，left + 1 < heapSize表示没有越界
            //2、arr[left + 1] > arr[left]表示右孩子节点的值大于左孩子节点的值，
            //  那么较大值就是右孩子节点，较大值的位置就是右孩子节点的位置
            //  否则较大值就是左孩子节点，较大值的位置就是左孩子节点的位置
            //情况二：
            //没有右孩子节点，较大值就是左孩子节点，较大值的位置就是左孩子节点的位置
            //或者右孩子节点的值小于左孩子节点的值，较大值就是左孩子节点，较大值的位置就是左孩子节点的位置
            int maxIndex = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            //取出值较大的子节点，再和父节点的值进行比较，返回值较大的索引
            int largest = arr[maxIndex] > arr[index] ? maxIndex : index;
            //判断最终获取的较大值是否是当前节点的索引，如果是，不需要往下沉了，直接break
            if (largest == index) {
                break;
            }
            //如果不是，证明当前节点不是整棵子树中的值最大的节点，当前节点索引需要和整棵子树的最大值索引进行交换
            //index   ->       7
            //               /   \
            //              9     1
            //             /       \
            //            3         5
            //7不是整棵子树中值最大的节点，那么7需要和整棵子树中值最大的节点9进行交换
            swap(arr, largest, index);
            //当前节点来到当前节点所在的整棵子树中的值最大的节点的位置
            index = largest;
            //继续进行下一层的判断，从左子节点开始
            //                 9
            //               /   \
            //index   ->    7     1
            //             /       \
            //            3         5
            left = 2 * index + 1;
        }
    }

    /**
     * 数组两个元素进行交换
     *
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
