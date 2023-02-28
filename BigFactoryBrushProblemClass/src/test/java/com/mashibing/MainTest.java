package com.mashibing;

/**
 * @author xcy
 * @date 2022/7/22 - 8:48
 */
public class MainTest {
	public static void main(String[] args) {
		String xor = xor(-5);
		String xor1 = xor(-100);
		System.out.println(xor);
		System.out.println(xor1);
	}

	public static String xor(int num) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int move = 31; move >= 0; move--) {
			int digit = (num >> move) & 1;
			if (digit == 0) {
				stringBuilder.append("0");
			}else {
				stringBuilder.append("1");
			}
		}
		return stringBuilder.toString();
	}
}
