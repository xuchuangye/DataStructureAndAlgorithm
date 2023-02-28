package com.mashibing.logarithmic_comparator;

/**
 * 题目一：
 * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
 * 1）能装下6个苹果的袋子
 * 2）能装下8个苹果的袋子
 * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
 * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
 *
 * @author xcy
 * @date 2022/6/2 - 15:55
 */
public class PackFullAppleMinBagNum {
	public static void main(String[] args) {
		/*
		规律分析：
		苹果数量：18，袋子数量：3
        苹果数量：19，袋子数量：-1
        苹果数量：20，袋子数量：3
        苹果数量：21，袋子数量：-1
        苹果数量：22，袋子数量：3
        苹果数量：23，袋子数量：-1
        苹果数量：24，袋子数量：3
        苹果数量：25，袋子数量：-1

        偶数返回3，奇数返回-1

        苹果数量：26，袋子数量：4
		苹果数量：27，袋子数量：-1
		苹果数量：28，袋子数量：4
		苹果数量：29，袋子数量：-1
		苹果数量：30，袋子数量：4
		苹果数量：31，袋子数量：-1
		苹果数量：32，袋子数量：4
		苹果数量：33，袋子数量：-1

		偶数返回4，奇数返回-1

		苹果数量：34，袋子数量：5
		苹果数量：35，袋子数量：-1
		苹果数量：36，袋子数量：5
		苹果数量：37，袋子数量：-1
		苹果数量：38，袋子数量：5
		苹果数量：39，袋子数量：-1
		苹果数量：40，袋子数量：5
		苹果数量：41，袋子数量：-1

		偶数返回5，奇数返回-1

		1 - 17 没有规律，从18开始，8个为一组，第0组：18 - 25，第1组：26 - 33，第2组：34 - 41
		那么返回的袋子数量：第0组：0 + 3，第1组：1 + 3，第2组：2 + 3
		苹果数量在第0组，苹果数量为偶数返回袋子数量3，奇数返回-1
		苹果数量在第1组，苹果数量为偶数返回袋子数量4，奇数返回-1
		苹果数量在第2组，苹果数量为偶数返回袋子数量5，奇数返回-1
        */
		for (int i = 1; i < 200; i++) {
			int numberOfBag = packFullAppleMinBagNumOptimal(i);
			System.out.println("苹果数量：" + i + "，袋子数量：" + numberOfBag);
		}
	}

	/**
	 * @param numberOfApple
	 * @return
	 */
	public static int packFullAppleMinBagNum1(int numberOfApple) {
		//装6个苹果的袋子
		int pack6 = -1;
		//装8个苹果的袋子
		int pack8 = numberOfApple / 8;
		//剩余的苹果数量 = 总的苹果数量 - 8 * 装8个苹果的袋子数量
		int rest = numberOfApple - 8 * pack8;
		while (pack8 >= 0) {
			//剩余苹果数量能否被装6个苹果的袋子装满
			int canItBe_Pack6Bag_PackFull = remainingQuantityCanItBeDividedBy6(rest);
			//如果剩余苹果数量能被装6个苹果的袋子装满
			if (canItBe_Pack6Bag_PackFull != -1) {
				pack6 = canItBe_Pack6Bag_PackFull;
				break;
			}
			rest = numberOfApple - 8 * (pack8 - 1);
			pack8--;
		}
		return pack6 != -1 ? pack6 + pack8 : -1;
	}

	/**
	 * @param rest 剩余需要装的苹果数量
	 * @return 剩余需要装的苹果数量能否被6整除，如果能，返回苹果数量除以6的商，如果不能，返回-1，表示使用装6个的袋子装不下
	 */
	public static int remainingQuantityCanItBeDividedBy6(int rest) {
		return (rest % 6) == 0 ? rest / 6 : -1;
		//return rest % 6 == 0 ? rest / 6 : -1;
	}

	public static int packFullAppleMinBagNum2(int numberOfApple) {
		if (numberOfApple < 0) {
			return -1;
		}
		int pack8 = numberOfApple >> 3;
		int rest = numberOfApple - (pack8 << 3);
		while (pack8 >= 0) {
			if (rest % 6 == 0) {
				return pack8 + (rest / 6);
			} else {
				pack8--;
				rest += 8;
			}
		}
		return -1;
	}

	/**
	 * 最优解，时间复杂度：O(1)
	 * 规律分析：
	 * 苹果数量：18，袋子数量：3
	 * 苹果数量：19，袋子数量：-1
	 * 苹果数量：20，袋子数量：3
	 * 苹果数量：21，袋子数量：-1
	 * 苹果数量：22，袋子数量：3
	 * 苹果数量：23，袋子数量：-1
	 * 苹果数量：24，袋子数量：3
	 * 苹果数量：25，袋子数量：-1
	 * <p>
	 * 偶数返回组数0 + 3 = 3，奇数返回-1
	 * <p>
	 * 苹果数量：26，袋子数量：4
	 * 苹果数量：27，袋子数量：-1
	 * 苹果数量：28，袋子数量：4
	 * 苹果数量：29，袋子数量：-1
	 * 苹果数量：30，袋子数量：4
	 * 苹果数量：31，袋子数量：-1
	 * 苹果数量：32，袋子数量：4
	 * 苹果数量：33，袋子数量：-1
	 * <p>
	 * 偶数返回组数1 + 3 = 4，奇数返回-1
	 * <p>
	 * 苹果数量：34，袋子数量：5
	 * 苹果数量：35，袋子数量：-1
	 * 苹果数量：36，袋子数量：5
	 * 苹果数量：37，袋子数量：-1
	 * 苹果数量：38，袋子数量：5
	 * 苹果数量：39，袋子数量：-1
	 * 苹果数量：40，袋子数量：5
	 * 苹果数量：41，袋子数量：-1
	 * <p>
	 * 偶数返回组数2 + 3 = 5，奇数返回-1
	 * <p>
	 * 1 - 17 没有规律，
	 * 从18开始，8个为一组，第0组：18 - 25，第1组：26 - 33，第2组：34 - 41
	 * 那么返回的袋子数量：第0组：0 + 3，第1组：1 + 3，第2组：2 + 3
	 * 苹果数量在第0组，苹果数量为偶数返回袋子数量3，奇数返回-1
	 * 苹果数量在第1组，苹果数量为偶数返回袋子数量4，奇数返回-1
	 * 苹果数量在第2组，苹果数量为偶数返回袋子数量5，奇数返回-1
	 *
	 * @param numberOfApple
	 * @return
	 */
	public static int packFullAppleMinBagNumOptimal(int numberOfApple) {
		//如果是奇数，返回-1
		//if ((numberOfApple % 2) 1= 0)
		if ((numberOfApple & 1) != 0) {
			return -1;
		}
		//1 - 17的规律
		if (numberOfApple < 18) {
			/*if (numberOfApple == 0) {
				return 0;
			} else if (numberOfApple == 6 || numberOfApple == 8) {
				return 1;
			} else if (numberOfApple == 12 || numberOfApple == 14 || numberOfApple == 16) {
				return 2;
			}*/
			return numberOfApple == 0 ? 0 :
					(numberOfApple == 6 || numberOfApple == 8 ? 1 :
							(numberOfApple == 12 || numberOfApple == 14 || numberOfApple == 16 ? 2 : -1));
		}
		//18以后的规律
		else {
			//numberOfApple - 1表示从18开始
			//(numberOfApple - 18) / 8表示分组，从第0组开始
			//第0组返回0 + 3个袋子，第1组返回1 + 3个袋子，第2组返回2 + 3个袋子，依此类推
			return ((numberOfApple - 18) >> 3) + 3;
			//return ((numberOfApple - 18) / 8) + 3;
		}
	}
}
