package com.mashibing.bitmap;

import java.util.HashSet;

/**
 * 位图
 * 位图就是拿每一位Bit作为位图
 * <p>
 * 位图的功能
 * 极大的节省内存空间
 * <p>
 * 位图的优点：
 * 位图能够节省大量的内存空间
 * <p>
 * 注意事项：
 * 在Java默认的情况下，整型的数一般都是int类型
 * 而int类型是4个字节byte，每个字节8位，一共是32位的
 * 所以32位的int型数值是无法做到向左移动超出32位的位数
 *
 * @author xcy
 * @date 2022/4/9 - 17:08
 */
public class BitMap {
    /**
     * 位图
     */
    private int[] bits = null;

    public static void main(String[] args) {
        System.out.println("测试开始");
        int max = 10000;
        int testTime = 10000000;
        BitMap longBitMap = new BitMap(max);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < testTime; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                longBitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                longBitMap.delete(num);
                set.remove(num);
            } else {
                if (longBitMap.contains(num) != set.contains(num)) {
                    System.err.println("出现错误");
                    break;
                }
            }

        }
        for (int num = 0; num < max; num++) {
            if (longBitMap.contains(num) != set.contains(num)) {
                System.err.println("出现错误");
                break;
            }
        }
        System.out.println("测试结束");
    }

    public BitMap(int max) {
        //int类型可以表示(max + 32) / 32
        //如果max的范围在0~31内，只需要长度为1的int类型数组
        //如果max的范围在32~63内，只需要长度为2的int类型数组
        //依次类推
        //bits = new int[(int) ((max + 32L) >> 5)];
        bits = new int[(int) ((max + 32) / 32)];
    }

    /**
     * 在位图中添加数值标记
     *
     * @param number
     */
    public void add(int number) {
        //bits[number >> 5]表示number / 32，获取到bits数组中哪一个元素存储的number
        //举例：
        //假设number = 170，那么170 / 64就表示在位图中，哪一个索引有记录这个number
        //number = 170，170 / 64 = 2，那么再bits[]数组中，索引2位置上记录了这个number

        //number & 63和number % 64的结果是一样的
        //number & 63描述存储number的元素的第几位
        //举例：
        //假设number = 170，那么170 % 64就表示在位图中，存储number的这个整数的第几位
        //也就是bits[2]这个数的第几位，170 % 64 = 42，那么就是bits[2]这个数的第42位上存储了这个number

        //1L << (number & 63)表示，在bits[2]这个数的第42位上存储了这个number，那么1L就需要左移42位
        //然后42位上的1与存储number的bits[2]这个整数上的0进行|运算，将1标记到该整数bits[2]的42位上
        //最终|运算记录到bits[2]这个整数的42位上，位图中就记录了这个number
        //bits[number >> 5] |= (1L << (number & 31));
        bits[number / 32] = bits[number / 32] | (1 << (number  % 32));
    }

    /**
     * 在位图汇总删除数值标记
     *
     * @param number
     */
    public void delete(int number) {
        //bits[number >> 6]表示定位到第几个元素进行存储number
        //1L << (number & 63)表示定位到该元素的第几位进行存储
        //~(1L << (number & 63))表示取反
        //举例：
        //索引：9876543210
        //假设在1011011011第3位上的存储了number，那么将第3位上的1抹掉即可，如何抹掉
        //     1111110111，只在第3位上是0，那么0000001000就是1L左移3位之后的结果，然后再取反，
        //取反后1111110111
        //1011011011
        //&
        //1111110111
        //=
        //1011010011

        //将原来的数组中第几个元素的第几位上存储number的1置为0就表示删除了该number存储的标记，也就删除了number
        //所以将原来数组中存储第几个元素的第几位上的1 与上 11110111中的0，那么该位就置为0，也就删除了number
        //bits[number >> 5] &= ~(1L << (number & 31));
        bits[number / 32] = bits[number / 32] & ~(1 << (number % 32));
    }

    /**
     * 查看number在位图中是否存在
     *
     * @param number
     * @return
     */
    public boolean contains(int number) {
        //bits[number >> 6]表示现在存储number的bits[i]元素的第j位上是否存储了number，也就是是否标记了1
        //1L << (number & 63)表示1左移(number % 64)位之后上的1
        //原来是否记录了1 & 现在1左移(number % 64)位之后上的1
        //如果 != 0，说明位图中已经存储了number，否则表示在位图中没有存储过number
        //return ((bits[number >> 5]) & (1L << (number & 31))) != 0;
        return ((bits[number / 32]) & (1 << (number & 31))) != 0;
    }
}
