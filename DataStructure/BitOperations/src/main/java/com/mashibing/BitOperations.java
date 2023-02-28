package com.mashibing;

/**
 * 位运算
 *
 * @author xcy
 * @date 2022/4/10 - 9:02
 */
public class BitOperations {
	public static void main(String[] args) {
		int a = Integer.MIN_VALUE;
		int b = Integer.MAX_VALUE;
		System.out.println(a);
		System.out.println(b);
		System.out.println("测试位运算相加");
		System.out.println(a + b);
		System.out.println((a ^ b) + ((a & b) << 1));
		System.out.println(add(a, b));

		System.out.println("测试位运算相减");
		System.out.println(a - b);
		System.out.println(sub(a, b));

		System.out.println("测试位运算相乘");
		System.out.println(a * b);
		System.out.println(mul(a, b));

		System.out.println("测试位运算相除");
		System.out.println(a / b);
		System.out.println(div(a, b));

		System.out.println(Math.abs(Integer.MIN_VALUE));
		System.out.println(Math.abs(Integer.MAX_VALUE));

		System.out.println(Integer.MIN_VALUE / -1);
		System.out.println(divide(Integer.MIN_VALUE, 2));
	}

	/**
	 * 使用位运算进行相加
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static int add(int a, int b) {
		int num = a;
		while (b != 0) {
			num = a ^ b; //无进位相加 -> num
			b = ((a & b) << 1); //进位信息 b -> b'
			a = num;//a -> a'无进位相加
		}
		return num;
	}

	/**
	 * 数值取反： ~number + 1
	 *
	 * @param number
	 * @return
	 */
	public static int reverseNumber(int number) {
		return add(~number, 1);
	}

	/**
	 * 使用位运算进行相减
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static int sub(int a, int b) {
		return add(a, reverseNumber(b));
	}

	/**
	 * 使用位运算进行相乘
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static int mul(int a, int b) {
		int num = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				num = add(num, a);
			}
			a <<= 1;
			b >>>= 1;
		}
		return num;
	}

	/**
	 * 判断是否是负数
	 *
	 * @param number
	 * @return
	 */
	public static boolean isNegative(int number) {
		return number < 0;
	}

	/**
	 * 使用位运算进行相除
	 * @param a
	 * @param b
	 * @return
	 */
	public static int div(int a, int b) {
		//判断a是否是负数，如果是负数就进行取反
		int x = isNegative(a) ? reverseNumber(a) : a;
		//判断b是否是负数，如果是负数就进行取反
		int y = isNegative(b) ? reverseNumber(b) : b;

		int ans = 0;
		//每次循环i减去1，因为不能出现-减号
		for (int i = 30; i >= 0; i = sub(i, 1)) {
			//判断x右移i位，如果右移i位之后x的值大于等于y，说明该位上有1
			if ((x >> i) >= y) {
				//那么在该位上就标记到ans中
				ans |= (1 << i);
				//然后x 减去 y 左移 i位之后的数值
				x = sub(x, y << i);
			}
		}
		//判断a和b的符号是否一样，如果不一样，那么需要对结果进行取反，如果一样，那么直接返回结果
		return isNegative(a) ^ isNegative(b) ? reverseNumber(ans) : ans;
	}

	/**
	 * 解决系统最小值无法转换成绝对值的问题
	 * 1、Integer.MIN_VALUE不能转换成绝对值
	 * 因为0的存在，所以只有最小-2147483648，没有最大的2147483648，只有2147483647
	 * 2、所以在除法中，Integer.MIN_VALUE作为除数无法转换成绝对值进行计算
	 * 3、因此需要想办法绕开直接转换成绝对值的问题
	 * 4、LeetCode约定：系统最小值 / -1 时，返回系统最大值即可
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static int divide(int a, int b) {
		//1、a和b都是系统最小值
		if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
			return 1;
		}
		//2、a不是系统最小值，b是系统最小值
		else if (b == Integer.MIN_VALUE) {
			return 0;
		}
		//3、a是系统最小值，b不是系统最小值
		else if (a == Integer.MIN_VALUE) {
			//判断b是否是负数
			//如果是负数，并且被除数b是-1，那么直接返回系统最大值
			if (b == reverseNumber(1)) {
				return Integer.MAX_VALUE;
			} else {
				//a没有办法直接除以b
				//所以a需要加1，然后再除以b，得到商quotient
				int quotient = div(add(a, 1), b);
				//a减去商quotient乘以b的和，得到差difference
				int difference = sub(a, mul(quotient, b));
				//得到的商quotient + 得到的差difference再除以b的商数 的和，就是a除以b的结果
				return add(quotient, div(difference, b));
			}
		}
		//4、a和b都不是系统最小值
		else {
			return div(a, b);
		}
	}
}
