package com.mashibing.day02;

/**
 * 题目二：
 * 贩卖机只支持硬币支付，且收退都只支持10 ，50，100三种面额
 * 一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则
 * 需要购买的可乐数量是m，其中手头拥有的10、50、100的数量分别为a、b、c，可乐的价格是x(x是10的倍数)
 * 请计算出需要投入硬币次数
 * <p>
 * 举例：
 * 假设100的7张 50的3张 10的100张，可乐的价值230，可乐的数量是3
 * 1.买第一瓶可乐，塞入100的3张，找零70，1张50和2张10  -->  投币次数3次
 * 此时100的4张，50的4张，10的102张
 * 2.买第二瓶可乐，塞入100的3张，找零70,1张50和2张10  -->  投币次数3次
 * 此时100的1张，50的5张，10的104张
 * 3.买第三瓶可乐，塞入100的1张，50的3张，找零20，2张10  -->  投币次数4次
 * 此时100的0张，50的2张，10的106张
 * <p>
 * 解题思路：
 * 1.这道题不管是投钱还是找零都是使用优先大面值原则
 * 2.这道题是跟面值的数量有关的，时间复杂度：O(K) 当K值极小时，O(1)
 *
 * @author xcy
 * @date 2022/7/11 - 9:09
 */
public class Code02_Cola {
	public static void main(String[] args) {
		//向上取整
		//System.out.println((2101 + (700 - 1)) / 700);

		int testTime = 1000;
		int zhangMax = 10;
		int colaMax = 10;
		int priceMax = 20;
		System.out.println("如果错误会打印错误数据，否则就是正确");
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int m = (int) (Math.random() * colaMax);
			int a = (int) (Math.random() * zhangMax);
			int b = (int) (Math.random() * zhangMax);
			int c = (int) (Math.random() * zhangMax);
			int x = ((int) (Math.random() * priceMax) + 1) * 10;
			int ans1 = numberOfCoinTimes1(m, a, b, c, x);
			int ans2 = numberOfCoinTimes2(m, a, b, c, x);
			if (ans1 != ans2) {
				System.out.println("int m = " + m + ";");
				System.out.println("int a = " + a + ";");
				System.out.println("int b = " + b + ";");
				System.out.println("int c = " + c + ";");
				System.out.println("int x = " + x + ";");
				break;
			}
		}
		System.out.println("测试结束！");
	}


	/**
	 * 暴力尝试，为了验证正式方法而已
	 *
	 * @param m
	 * @param a
	 * @param b
	 * @param c
	 * @param x
	 * @return
	 */
	public static int numberOfCoinTimes1(int m, int a, int b, int c, int x) {
		int[] qian = {100, 50, 10};
		int[] zhang = {c, b, a};
		int puts = 0;
		while (m != 0) {
			int cur = buy(qian, zhang, x);
			if (cur == -1) {
				return -1;
			}
			puts += cur;
			m--;
		}
		return puts;
	}

	public static int buy(int[] qian, int[] zhang, int rest) {
		int first = -1;
		for (int i = 0; i < 3; i++) {
			if (zhang[i] != 0) {
				first = i;
				break;
			}
		}
		if (first == -1) {
			return -1;
		}
		if (qian[first] >= rest) {
			zhang[first]--;
			giveChange(qian, zhang, first + 1, qian[first] - rest, 1);
			return 1;
		} else {
			zhang[first]--;
			int next = buy(qian, zhang, rest - qian[first]);
			if (next == -1) {
				return -1;
			}
			return 1 + next;
		}
	}

	/**
	 * 时间复杂度：O(1)
	 *
	 * @param m 表示需要购买可乐的瓶数
	 * @param a 表示100面值的张数
	 * @param b 表示50面值的张数
	 * @param c 表示10面值的张数
	 * @param x 表示单瓶可乐的价值，是10的整数倍
	 * @return 根据投币和找零的优先使用大面值的原则，返回购买m瓶可乐至少投币多少次
	 */
	public static int numberOfCoinTimes2(int m, int a, int b, int c, int x) {
		//购买至少1瓶可乐，可乐的价值至少10元
		if (m < 1 || x < 1 || x % 10 != 0) {
			return 0;
		}

		//面值
		int[] faceValue = {100, 50, 10};
		//张数
		int[] numberOfSheets = {c, b, a};
		//投币的次数
		int numberOfCoinTimes = 0;

		//之前剩余的总面值
		int preRestFaceValue = 0;
		//之前剩余的总张数
		int preRestNumberOfSheets = 0;

		//3表示面值的种数，本来是i = 1，i <= 3，但是因为数组索引是从0开始，为了代码的健壮性和边界问题，i从1开始，i < 3
		for (int i = 0; i < 3 && m != 0; i++) {
			//首次使用当前面试购买可乐的张数 = (单瓶可乐的价值 - 之前剩余的总面值 + 当前面值 - 1) / 当前面值
			int curFirstBuyColaNumberOfSheets = (x - preRestFaceValue + faceValue[i] - 1) / faceValue[i];
			//如果当前面值的张数能够搞定首次购买可乐需要的张数
			if (numberOfSheets[i] >= curFirstBuyColaNumberOfSheets) {
				//需要找零，将找零的钱分配给之后面值
				//找零的钱数 = (之前剩余的总面值 - 当前面值 * 当前面值首次购买可乐的张数) - 购买单瓶可乐的价值
				giveChange(faceValue, numberOfSheets, i + 1, (preRestFaceValue + faceValue[i] * curFirstBuyColaNumberOfSheets) - x, 1);

				//投币次数 += 首次购买可乐的张数 + 之前剩余的总张数
				numberOfCoinTimes += curFirstBuyColaNumberOfSheets + preRestNumberOfSheets;
				//当前面值的张数减去首次购买可乐需要的张数
				numberOfSheets[i] -= curFirstBuyColaNumberOfSheets;
				//购买可乐的瓶数 - 1
				m--;
			}
			//否则当前面值的张数不能够搞定首次购买可乐需要的张数
			else {
				//打不过就加入
				preRestFaceValue += numberOfSheets[i] * faceValue[i];
				preRestNumberOfSheets += numberOfSheets[i];
				continue;
			}

			//之后每次使用当前面值购买单瓶可乐的张数 = (单瓶可乐的价值 + 当前面值 - 1) / 当前面值
			int curEveryPurchaseOneColaNumberOfSheets = (x + faceValue[i] - 1) / faceValue[i];
			//当前面值的张数 / 购买单瓶可乐的张数 = 能够购买的可乐的数量
			//能够购买多少瓶可乐 = (能够购买的可乐的数量, 需要购买可乐的数量)中取最小值
			int canBuyColaCount = Math.min(numberOfSheets[i] / curEveryPurchaseOneColaNumberOfSheets, m);
			//使用当前面值购买单瓶可乐之后剩余的找零钱数 = 购买单瓶可乐的张数 * 当前面值 - 单瓶可乐的价值
			int curBuyOneColaRestMoney = curEveryPurchaseOneColaNumberOfSheets * faceValue[i] - x;

			//需要找零，将找零的钱分配给之后面值
			giveChange(faceValue, numberOfSheets, i + 1, curBuyOneColaRestMoney, canBuyColaCount);

			//投币次数 += 每次购买单瓶可乐的张数 * 能够购买多少瓶可乐
			numberOfCoinTimes += curEveryPurchaseOneColaNumberOfSheets * canBuyColaCount;
			//需要购买可乐的瓶数 - 能够购买多少瓶可乐
			m -= canBuyColaCount;
			//当前面值的张数 -= 每次购买单瓶可乐的张数 * 能够购买多少瓶可乐
			numberOfSheets[i] -= curEveryPurchaseOneColaNumberOfSheets * canBuyColaCount;

			//出现新的历史：之前剩余的总面值以及之前剩余的总张数
			//之前剩余的总面值 = 当前面值 * 当前面值剩余的张数
			preRestFaceValue = faceValue[i] * numberOfSheets[i];
			//之前剩余的总张数 = 当前面值剩余的张数
			preRestNumberOfSheets = numberOfSheets[i];
		}

		//剩余需要购买可乐的瓶数是否为0，为0时返回投币次数，否则返回-1
		return m == 0 ? numberOfCoinTimes : -1;
	}

	/**
	 * 购买单瓶可乐需要找零
	 *
	 * @param faceVale               当前面值
	 * @param numberOfSheets         当前面值的张数
	 * @param i                      当前面值的索引
	 * @param curBuyOneColaRestMoney 当前面值购买单瓶可乐剩余钱数
	 * @param times                  当前购买可乐之后找零的次数
	 */
	public static void giveChange(int[] faceVale, int[] numberOfSheets, int i, int curBuyOneColaRestMoney, int times) {
		for (; i < 3; i++) {
			numberOfSheets[i] += (curBuyOneColaRestMoney / faceVale[i]) * times;
			curBuyOneColaRestMoney %= faceVale[i];
		}
	}

	/**
	 * 老师写的参考版
	 *
	 * @param m
	 * @param a
	 * @param b
	 * @param c
	 * @param x
	 * @return
	 */
	public static int putTimes(int m, int a, int b, int c, int x) {
		//              0    1   2
		int[] qian = {100, 50, 10};
		int[] zhang = {c, b, a};
		// 总共需要多少次投币
		int puts = 0;
		// 之前面值的钱还剩下多少总钱数
		int preQianRest = 0;
		// 之前面值的钱还剩下多少总张数
		int preQianZhang = 0;
		for (int i = 0; i < 3 && m != 0; i++) {
			// 要用之前剩下的钱、当前面值的钱，共同买第一瓶可乐
			// 之前的面值剩下多少钱，是preQianRest
			// 之前的面值剩下多少张，是preQianZhang
			// 之所以之前的面值会剩下来，一定是剩下的钱，一直攒不出一瓶可乐的单价
			// 当前的面值付出一些钱+之前剩下的钱，此时有可能凑出一瓶可乐来
			// 那么当前面值参与搞定第一瓶可乐，需要掏出多少张呢？就是curQianFirstBuyZhang
			int curQianFirstBuyZhang = (x - preQianRest + qian[i] - 1) / qian[i];
			if (zhang[i] >= curQianFirstBuyZhang) { // 如果之前的钱和当前面值的钱，能凑出第一瓶可乐
				// 凑出来了一瓶可乐也可能存在找钱的情况，
				giveRest(qian, zhang, i + 1, (preQianRest + qian[i] * curQianFirstBuyZhang) - x, 1);
				puts += curQianFirstBuyZhang + preQianZhang;
				zhang[i] -= curQianFirstBuyZhang;
				m--;
			} else { // 如果之前的钱和当前面值的钱，不能凑出第一瓶可乐
				preQianRest += qian[i] * zhang[i];
				preQianZhang += zhang[i];
				continue;
			}
			// 凑出第一瓶可乐之后，当前的面值有可能能继续买更多的可乐
			// 以下过程就是后续的可乐怎么用当前面值的钱来买
			// 用当前面值的钱，买一瓶可乐需要几张
			int curQianBuyOneColaZhang = (x + qian[i] - 1) / qian[i];
			// 用当前面值的钱，一共可以搞定几瓶可乐
			int curQianBuyColas = Math.min(zhang[i] / curQianBuyOneColaZhang, m);
			// 用当前面值的钱，每搞定一瓶可乐，收货机会吐出多少零钱
			int oneTimeRest = qian[i] * curQianBuyOneColaZhang - x;
			// 每次买一瓶可乐，吐出的找零总钱数是oneTimeRest
			// 一共买的可乐数是curQianBuyColas，所以把零钱去提升后面几种面值的硬币数，
			// 就是giveRest的含义
			giveRest(qian, zhang, i + 1, oneTimeRest, curQianBuyColas);
			// 当前面值去搞定可乐这件事，一共投了几次币
			puts += curQianBuyOneColaZhang * curQianBuyColas;
			// 还剩下多少瓶可乐需要去搞定，继续用后面的面值搞定去吧
			m -= curQianBuyColas;
			// 当前面值可能剩下若干张，要参与到后续买可乐的过程中去，
			// 所以要更新preQianRest和preQianZhang
			zhang[i] -= curQianBuyOneColaZhang * curQianBuyColas;
			//产生新的历史
			preQianRest = qian[i] * zhang[i];
			preQianZhang = zhang[i];
		}
		return m == 0 ? puts : -1;
	}

	public static void giveRest(int[] qian, int[] zhang, int i, int oneTimeRest, int times) {
		for (; i < 3; i++) {
			zhang[i] += (oneTimeRest / qian[i]) * times;
			oneTimeRest %= qian[i];
		}
	}
}
