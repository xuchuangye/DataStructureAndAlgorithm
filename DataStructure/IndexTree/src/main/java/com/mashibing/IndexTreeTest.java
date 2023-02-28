package com.mashibing;

/**
 * @author xcy
 * @date 2022/5/29 - 9:04
 */
public class IndexTreeTest {
	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTime = 2000000;
		IndexTree tree = new IndexTree(N);
		Right test = new Right(N);
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			int index = (int) (Math.random() * N) + 1;
			if (Math.random() <= 0.5) {
				int add = (int) (Math.random() * V);
				tree.add(index, add);
				test.add(index, add);
			} else {
				if (tree.sum(index) != test.sum(index)) {
					System.err.println("测试错误！");
				}
			}
		}
		System.out.println("测试结束！");
	}
}
