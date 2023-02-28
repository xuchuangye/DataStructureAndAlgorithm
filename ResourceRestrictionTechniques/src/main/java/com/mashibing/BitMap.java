package com.mashibing;

/**
 * 题目二：
 * 32位无符号整数的范围是0~4,294,967,295，
 * 现在有一个正好包含40亿个无符号整数的文件，
 * 所以在整个范围中必然存在没出现过的数。
 * 可以使用最多1GB的内存，怎么找到所有未出现过的数？
 *
 * 思路分析：
 * 使用表示位数的数组，数组的索引存储每一个整数，每个整数的32位依次拼接表示整个32位无符号整数的范围0~2的32次方 - 1，一共2的32次方个数
 * 准备一个2的32次方的位图，8位表示1个字节，2的32次方的位图一共占用2的32次方 / 8的字节 = 2的29次方个字节 = 536870912 / 1000000000
 * 不超过600M，没有超出题目的限制内存1G ，并且位图上的值如果是0就表示没有出现过，1表示出现过
 *
 * 536870912 / 32 = 16777216就是要申请的int[]
 * @author xcy
 * @date 2022/5/28 - 14:36
 */
public class BitMap {
	public static void main(String[] args) {
		System.out.println((int)(Math.pow(2, 32) / Math.pow(2, 3)) / 4);
	}
	public final int[] bits;

	public BitMap() {
		//32位无符号整数的范围0 ~ 2的32次方 - 1
		//8位表示1个字节
		//4个字节组成一个int类型
		//所以位图的申请空间
		bits = new int[(int)(Math.pow(2, 32) / Math.pow(2, 3)) / 4];
	}
	/**
	 * 在位图中添加指定数值的标记
	 * @param number 指定数值
	 */
	public void add(int number) {
		if (number < 0) {
			System.out.println("题目要求：无符号整数");
			return;
		}
		//bits[number >> 5]表示number / 32，获取到bits数组中哪一个元素存储的number
		//举例：
		//假设number = 170，那么170 / 32就表示在位图中，哪一个索引有记录这个number
		//number = 170，170 / 32 = 5，那么再bits[]数组中，索引5位置上的整数记录了这个number

		//number & 31和number % 32的结果是一样的
		//number & 31描述在位图中，存储number这个元素在索引number / 32上的这个整数的第几位
		//举例：
		//假设number = 170，那么170 % 32就表示在位图中，在位图中，存储number的这个number / 32整数的第几位
		//也就是bits[5]这个数的第几位，170 % 32 = 10，那么就是bits[5]这个数的第10位上存储了这个number

		//1L << (number & 31)表示，在bits[5]这个数的第10位上存储了这个number，那么1L就需要左移10位
		//然后10位上的1与存储number的bits[5]这个整数上的0进行|运算，将1标记到该整数bits[5]的10位上
		//最终|运算记录到bits[5]这个整数的10位上，位图中就记录了这个number
		bits[number >> 5] |= (1 << (number & 31));
		//bits[number / 32] = bits[number / 32] | (1 << (number % 32));
	}

	/**
	 * 在位图中删除指定数值的标记
	 * @param number 指定数值
	 */
	public void delete(int number) {
		if (number < 0) {
			System.out.println("题目要求：无符号整数");
			return;
		}
		//bits[number >> 5]表示定位到位图中第几个整数存储了number标记

		//1 << (number & 31)表示定位到位图中第number / 32个整数的第几位存储了number标记
		//~(1L << (number & 63))表示取反
		//举例：
		//索引：9876543210
		//假设在1011011011第3位上的存储了number标记，那么将第3位上的1抹掉即可删除指定数值number的标记，如何抹掉
		//     1111110111，只在第3位上是0，那么0000001000就是1左移3位之后的结果，然后再取反，
		//取反后1111110111
		//1011011011
		//&
		//1111110111
		//=
		//1011010011

		//将位图中第number / 32个整数的第几位上存储number标记的1置为0就表示删除了该number存储的标记，也就删除了number
		bits[number >> 5] &= ~(1 << (number & 31));
		//bits[number / 32] = bits[number / 32] & ~(1 << (number % 32));
	}

	/**
	 * 在位图中查找指定数值的标记
	 * @param number 指定数值
	 * @return 如果查找到，返回true，否则返回false
	 */
	public boolean contains(int number) {
		if (number < 0) {
			System.out.println("题目要求：无符号整数");
			return false;
		}
		//bits[number >> 5]表示现在存储number的bits[i]元素的第j位上是否存储了number的标记，也就是是否标记了1
		//1L << (number & 31)表示1左移(number % 32)位之后上的1
		//原来位图中是否记录了1 & 现在1左移(number % 32)位之后上的1
		//如果 != 0，说明位图中已经存储了number的标记，否则表示在位图中没有存储过number的标记，也就表示number在位图中不存在
		return (bits[number >> 5] & (1 << (number & 31))) != 0;
		//return (bits[number / 32] & (1 << (number % 32))) != 0;
	}
}
