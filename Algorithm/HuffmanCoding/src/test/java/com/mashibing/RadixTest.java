package com.mashibing;

/**
 * @author xcy
 * @date 2022/3/27 - 17:14
 */
public class RadixTest {
	public static void main(String[] args) {
		String code = "10101000";
		//补码 -> 反码
		//10101000 - 1 => 10100111
		//反码 -> 原码 符号位不变，其余位取反
		//10100111 => 11011000 转换为十进制 => -88
		/*byte b = (byte) Integer.parseInt(code, 2);//-88
		System.out.println(b);

		String s = Integer.toBinaryString(b);
		System.out.println(s.substring(s.length() - 8));*/


		boolean isEnoughSub = true;
		String s1 = byteToBitString(isEnoughSub, (byte) -88);
		System.out.println(s1);
	}

	/**
	 * 将一个byte转成一个二进制的字符串并返回
	 * @param isEnoughSub 是否足够补高位，true表示需要补码，false表示不需要补码，如果是最后一位，就不需要补高位
	 * @param b byte类型的整数
	 * @return 需要按照补码进行返回
	 */
	public static String byteToBitString(boolean isEnoughSub, byte b) {
		int temp = b;
		//如果是正数，还需要补高位
		if (isEnoughSub) {
			temp |= 256; //256 -> 1 0000 0000 | 0000 0001 => 1 0000 0001
		}
		String str = Integer.toBinaryString(temp);
		//System.out.println(str);
		//如果需要补码
		if (isEnoughSub || temp < 0) {
			//那么才截取最后8位
			return str.substring(str.length() - 8);
		} else {
			//如果不需要补码，直接返回即可
			return str;
		}
	}
}
