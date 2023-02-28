package com.mashibing;

/**
 * 蓄水池算法的应用：游戏抽奖系统
 * 做一个游戏的抽奖系统，游戏服务器在全球范围内都有，所有人只有第一次登录游戏服务器的才会有抽奖的资格
 * 游戏的抽奖时间20xx年1月1日00时00分到开奖时间20xx年1月2日00时00分
 * 中奖人数共100人，该范围内的所有人都等概率中奖
 * <p>
 * 思路分析：
 * 1.如果不使用蓄水池算法，需要将登录服务器的所有用户名单拿到手，抽100个人
 * 获取登录用户名单非常繁琐，并且需要20xx年1月1日23时00分都可能还在收集登录用户名单
 * 并且到了开奖时间20xx年1月2日00时00分还无法直接公布中奖名单
 * 2.如果使用蓄水池算法，只需要全球所有服务器只跟一台服务器沟通，上线一些数据即可
 * 1)用户登录是否是第一次，如果不是第一次，之后登录的次数没有抽奖资格
 * 2)如果知道用户在全球中是第i个登录的，该用户进入奖池的概率是100 / i，如果100 / i的概率没有进入奖池，那么再也进入不了奖池
 * 但如果100 / i的概率进入奖池，那么就在100个的奖池中随机剔除掉一个
 * 3)在开奖时间20xx年1月2日00时00分直接公布中奖名单，绝对全球所有登录的用户等概率获奖，而且代码的部署量少得多
 *
 * @author xcy
 * @date 2022/5/23 - 9:14
 */
public class GameLotterySystem {
	public static final int Number = 100000;
	public static void main(String[] args) {

	}

	public static void gameLotterySystem(int number) {
		int testTimes = 1000000;

		int[] count = new int[number + 1];
		for (int i = 0; i < testTimes; i++) {
			//准备奖池
			int[] jackpot = new int[100];
			//奖池的索引
			int index = 0;
			//用户是否是第一次登录
			boolean userLogin = false;
			//全球所有用户
			for (int num = 1; num <= number; num++) {
				if (num <= 100) {
					jackpot[index++] = num;
				}else {
					if (random(num) <= 100) {
						index = (int) (Math.random() * 100);
						jackpot[index] = num;
					}
				}
			}
			for (int num : jackpot) {
				count[num]++;
			}
		}

		for (int num = 0; num <= number; num++) {
			System.out.println(count[num]);
		}
	}

	/**
	 *
	 * @param i
	 * @return 返回1 - i中任意一个数
	 */
	public static int random(int i) {
		return (int) (Math.random() * i + 1);
	}
}
