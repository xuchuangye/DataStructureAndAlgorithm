package com.mashibing;

import org.junit.jupiter.api.Test;

/**
 * @author xcy
 * @date 2022/2/19 - 21:54
 */
public class TimeTest {

	@Test
	public void testTime() {
		long start = System.currentTimeMillis();

		for (int i = 1; i <= 10000000; i++) {
			int num = Integer.MAX_VALUE ^ i;
			System.out.println("num = " + num);
		}

		long end = System.currentTimeMillis();
		System.out.println("花费时间：" + (end - start));
	}
}
