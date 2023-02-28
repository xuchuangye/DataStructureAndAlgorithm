package com.mashibing.day19;

/**
 * 给定一个正数N，比如N = 13，在纸上把所有数都列出来如下：
 * 1 2 3 4 5 6 7 8 9 10 11 12 13
 * 可以数出1这个字符出现了6次，给定一个正数N，如果把1~N都列出来，返回1这个字符出现的多少次
 * <p>
 * 解题思路：
 * 动态规划的模型：数位DP
 * <p>
 * 假设：
 * N = 1364
 * 那么使用迭代求出365 ~ 1364上1字符出现的次数，递归求出1 ~ 364上1字符出现的次数
 * 1.
 * 如何求出1字符在千位上出现的次数？
 * 使用x表示千位上的数
 * 如果x == 1，那么剩余百位数十位数个位数这个三位数 + 1就是千位上1字符出现的次数
 * 如果x > 1，那么千位上1字符出现的次数 = 1999 - 1000 + 1
 * 2.
 * 如何求出1字符在百位上出现的次数？
 * 使用y表示百位上的数
 * 如果y == 1，那么十位上的数和个位上的数随意变换，千位数自动根据是否超出范围进行修正
 * 所以百位上1字符出现的次数 = (十位上范围0 ~ 9)10 * (个位上范围0 ~ 9)10
 * 3.
 * 如何求出1字符在十位上出现的次数？
 * 使用z表示十位上的数
 * 如果z == 1，那么百位上的数和个位上的数随意变换，千位数自动根据是否超出范围进行修正
 * 所以十位上1字符出现的次数 = (百位上范围0 ~ 9)10 * (个位上范围0 ~ 9)10
 * 4.
 * 如何求出1字符在个位上出现的次数？
 * 使用w表示个位上的数
 * 如果w == 1，那么百位上的数和十位上的数随意变换，千位数自动根据是否超出范围进行修正
 * 所以个位上1字符出现的次数 = (百位上范围0 ~ 9)10 * (十位上范围0 ~ 9)10
 * <p>
 * 总结：
 * 正数N的位数 == K，除去最高位，剩余的数值rest，迭代范围rest + 1 ~ 正数N
 * 1.
 * 如果最高位 == 1，那么最高位出现1字符的次数为rest + 1，剩余位数K - 1位，每一位出现1字符的次数为10的K - 2次方
 * 那么正数N中出现1字符的次数 = (rest + 1) + (K - 1) * 10的K - 2次方
 * 举例：
 * N = 1364, K = 4, rest = 364, rest + 1 = 365
 * 最高位出现1字符的次数为365，剩余位数3位，每一位出现1字符的次数为10的2次方
 * 2.
 * 如果最高位 != 1，那么最高位出现1字符的次数为10的K - 1次方，剩余位数K - 1位，能够平均分配的范围x，
 * 每一位在x个范围内出现1字符的次数为10的K - 2次方
 * 那么正数N中出现1字符的次数 = (10的K - 1次方) + (K - 1) * x * (10的K - 2次方)
 * 举例：
 * N = 5379, K = 4, rest = 379, rest + 1 = 380
 * 最高位出现1字符的次数为1000，剩余位数3位，能够平均分配的范围5，
 * 380 ~ 1379, 1380 ~ 2379, 2380 ~ 3379, 3380 ~ 4379, 4380 ~ 5379
 * 每一位在这5个范围内出现1字符的次数都为10的2次方
 *
 * <p>
 * 为什么出这道题目？
 * 这道题在剑指Offer里面跟各种面试题高频
 * <p>
 * 大厂为什么不考这道题？
 * 因为太难，毕竟不是ACM比赛
 *
 * @author xcy
 * @date 2022/8/10 - 16:31
 */
public class Code03_OneNumber {
	public static void main(String[] args) {
		int num = 50000000;
		long start1 = System.currentTimeMillis();
		System.out.println(solution1(num));
		long end1 = System.currentTimeMillis();
		System.out.println("cost time: " + (end1 - start1) + " ms");

		long start2 = System.currentTimeMillis();
		System.out.println(solution2(num));
		long end2 = System.currentTimeMillis();
		System.out.println("cost time: " + (end2 - start2) + " ms");

		long start3 = System.currentTimeMillis();
		System.out.println(solution(num));
		long end3 = System.currentTimeMillis();
		System.out.println("cost time: " + (end2 - start2) + " ms");
	}

	/**
	 * 暴力解
	 * <p>
	 * 时间复杂度：O(N * logN)
	 *
	 * @param num 正数N
	 * @return 返回正数N中1字符出现的次数
	 */
	public static int solution1(int num) {
		if (num < 1) {
			return 0;
		}
		int count = 0;
		for (int i = 1; i != num + 1; i++) {
			count += get1Nums(i);
		}
		return count;
	}

	public static int get1Nums(int num) {
		int res = 0;
		while (num != 0) {
			if (num % 10 == 1) {
				res++;
			}
			num /= 10;
		}
		return res;
	}

	/**
	 * 老师讲的版本
	 * 1 ~ num 这个范围上，画了几道1
	 *
	 * @param num
	 * @return
	 */
	public static int solution2(int num) {
		if (num < 1) {
			return 0;
		}
		// num -> 13625
		// len = 5位数
		int len = getLenOfNum(num);
		if (len == 1) {
			return 1;
		}
		// num  13625
		// tmp1 10000
		//
		// num  7872328738273
		// tmp1 1000000000000
		int tmp1 = powerBaseOf10(len - 1);
		// num最高位 num / tmp1
		int first = num / tmp1;
		// 最高1 N % tmp1 + 1
		// 最高位first tmp1
		int firstOneNum = first == 1 ? num % tmp1 + 1 : tmp1;
		// 除去最高位之外，剩下1的数量
		// 最高位1 10(k-2次方) * (k-1) * 1
		// 最高位first 10(k-2次方) * (k-1) * first
		int otherOneNum = first * (len - 1) * (tmp1 / 10);
		return firstOneNum + otherOneNum + solution2(num % tmp1);
	}

	public static int getLenOfNum(int num) {
		int len = 0;
		while (num != 0) {
			len++;
			num /= 10;
		}
		return len;
	}

	/**
	 * 最优解
	 * <p>
	 * 时间复杂度：O(log以10为底N为真数)的平方
	 *
	 * @param num 正数N
	 * @return 返回正数N中1字符出现的次数
	 */
	public static int solution(int num) {
		if (num < 1) {
			return 0;
		}
		//获取N的位数
		int K = getNumberDigit(num);
		//如果N的位数只有一位，那么1字符出现的次数为1
		if (K == 1) {
			return 1;
		}
		//3759
		//temp = 1000
		//733242322
		//temp = 100000000
		int temp = powerBaseOf10(K - 1);

		//最高位
		int highest = num / temp;
		//最高位出现1字符的次数
		int firstOne = 0;
		//其余位上出现1字符的次数
		int otherOne = 0;
		//如果最高位是1
		if (highest == 1) {
			//最高位出现1的次数
			//num = 1759
			//temp = 1000
			//firstOne = num - temp = 759
			firstOne = num - temp;
			//其余位上出现1字符的次数
			//num = 1759
			//temp = 1000
			//K = 4
			//otherOne = 3 * 10的2次方 = 300
			otherOne = (K - 1) * powerBaseOf10(K - 2);
		}
		//如果最高位不是1
		else {
			//最高位出现1的次数
			//num = 3759
			//temp = 1000
			//最高位出现1的次数的范围：1000 ~ 1999
			//firstOne = 1999 - 1000 + 1 = temp = 1000
			firstOne = temp;
			//其余位上出现1字符的次数
			//num = 3759
			//temp = 1000
			//K = 4
			//highest = 3
			//其余位上出现1字符的次数的范围：
			//760 ~ 1759
			//1760 ~ 2759
			//2760 ~ 3759
			//otherOne = 3 * 3 * 10的2次方 = 300
			otherOne = (K - 1) * highest * powerBaseOf10(K - 2);
		}
		return firstOne + otherOne + solution(num % temp);
	}

	/**
	 * @param number number
	 * @return 返回number的位数
	 */
	public static int getNumberDigit(int number) {
		int digit = 0;
		while (number != 0) {
			digit++;
			number /= 10;
		}
		return digit;
	}

	/**
	 * @param digit 位数
	 * @return 返回10的digit次方
	 */
	public static int powerBaseOf10(int digit) {
		return (int) Math.pow(10, digit);
	}
}
