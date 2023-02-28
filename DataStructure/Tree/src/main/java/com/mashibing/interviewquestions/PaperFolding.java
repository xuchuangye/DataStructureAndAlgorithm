package com.mashibing.interviewquestions;

/**
 * 二叉树的面试题
 * 请把一张纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开，此时折痕是凹下去的。
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕。从上往下依次是凹痕、凹痕、凸痕
 * <p>
 * 给定一个参数N，代表纸条都从下边向上边连续对折N次，请从上往下依次打印出所有折痕的方向
 * <p>
 * 举例：
 * 将一张纸条折痕3次，从上往下的折痕依次是：
 * 第3次：凹
 * 第2次：凹
 * 第3次：凸
 * 第1次：凹
 * 第3次：凹
 * 第2次：凸
 * 第3次：凸
 * <p>
 * 基本思路：
 * 折痕问题类似于二叉树
 * 第一次：                     1凹
 * 第二次：       2凹                         2凸
 * 第三次：3凹           3凸           3凹           3凸
 * <p>
 * 总结折痕的规律：
 * 1、二叉树的根节点的折痕一定为凹痕
 * 2、所有的左子节点的折痕一定为凹痕
 * 3、所有的右子节点的折痕一定为凸痕
 *
 * @author xcy
 * @date 2022/4/28 - 15:49
 */
public class PaperFolding {
	public static void main(String[] args) {
		int totalLevel = 3;
		printAllFolds(totalLevel);
	}

	/**
	 * 打印所有折叠，例如：1凹，2凹，2凸
	 *
	 * @param totalLevel 整棵树的总层数，也就是折痕的总次数
	 */
	public static void printAllFolds(int totalLevel) {
		process(1, totalLevel, true);
	}

	/**
	 * 时间复杂度：O(N)，N表示总层数
	 * 折叠痕迹 --> 中序遍历输出当前节点为根节点的整棵树
	 *
	 * @param curLevel   当前节点所在的层数
	 * @param totalLevel 整棵树的总层数，也就是折痕的总次数
	 * @param down       true表示当前折叠痕迹是“凹”痕，
	 *                   false表示当前折叠痕迹是“凸”痕
	 */
	private static void process(int curLevel, int totalLevel, boolean down) {
		//当前节点所在的层数大于总层数，直接返回
		if (curLevel > totalLevel) {
			return;
		}
		//每次折叠的痕迹，都是上边出现凹痕，表示当前节点的左子节点
		process(curLevel + 1, totalLevel, true);
		System.out.println(down ? "凹" : "凸");
		//每次折叠的痕迹，都是下边出现凸痕，表示当前节点的右子节点
		process(curLevel + 1, totalLevel, false);
	}
}
