package com.mashibing.arithmetic.greedy;

/**
 * 贪心算法的面试题
 * 给定一个字符串str，只由'X'和'.'两种 字符组成
 * 'X'表示墙，不能放灯，也不需要点亮
 * '.'表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 *
 * @author xcy
 * @date 2022/5/2 - 11:12
 */
public class LightUpQuestion {
	public static void main(String[] args) {

	}

	public static int severalLights(String str) {
		char[] chars = str.toCharArray();
		//点灯的数量
		int lights = 0;
		//字符串的索引
		int index = 0;
		while (index < str.length()) {
			//如果该字符是墙，直接下一个索引
			if (chars[index] == 'X') {
				index++;
			}else {
				//不管是在index，还是index + 1，还是index + 2都需要点灯，所以light++
				lights++;
				//索引已经来到字符串的最后一个字符，直接退出循环
				if (index == str.length() - 1) {
					break;
				}else {
					//判断如果index + 1的字符是X，灯加1,继续判断index + 2的字符
					if (chars[index + 1] == 'X') {
						index = index + 2;
					}
					//判断如果index + 1的字符是'.'
					//不管index + 2的字符是'X'还是'.'，都是灯 + 1，index + 3
					else {
						index = index + 3;
					}
				}
			}
		}
		return lights;
	}
}
