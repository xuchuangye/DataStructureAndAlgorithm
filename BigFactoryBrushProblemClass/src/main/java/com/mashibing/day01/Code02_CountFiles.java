package com.mashibing.day01;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 题目二：
 * 给定一个文件目录的路径，写一个函数统计这个目录下所有的文件数量并返回
 * 隐藏文件也算，但是文件夹不算
 *
 * 解题思路：
 * 1.使用图的宽度度优先遍历
 * 使用一个全局变量：count，记录总的文件个数
 * 使用一个全局变量：队列，所有的文件夹都放入队列中
 * 每次弹出队列中的文件夹，判断内部是文件还是文件夹，是文件，count++，是文件夹，直接入队列
 * 2.使用图的深度度优先遍历
 * 使用一个全局变量：count，记录总的文件个数
 * 使用一个全局变量：栈，所有的文件夹都放入栈中
 * 每次弹出队列中的文件夹，判断内部是文件还是文件夹，是文件，count++，是文件夹，直接入栈
 *
 * @author xcy
 * @date 2022/7/4 - 10:10
 */
public class Code02_CountFiles {
	public static void main(String[] args) {
		String folderPath = "D:\\Desktop";
		int fileNumberWithQueue = getFileNumberWithQueue(folderPath);
		int fileNumberWithStack = getFileNumberWithStack(folderPath);
		System.out.println(fileNumberWithQueue);
		System.out.println(fileNumberWithStack);
	}

	/**
	 * 使用迭代的方式：队列 -> 宽度优先遍历
	 * 注意这个函数也会统计隐藏文件
	 * @param folderPath 文件/文件夹路径
	 * @return
	 */
	public static int getFileNumberWithQueue(String folderPath) {
		if (folderPath == null || folderPath.length() == 0) {
			return 0;
		}
		//根据路径创建文件对象
		File file = new File(folderPath);
		//如果文件对象既不是文件夹也不是文件，那么直接返回0
		if (!file.isDirectory() && !file.isFile()) {
			return 0;
		}
		//如果文件对象是文件，那么返回文件数1
		if (file.isFile()) {
			return 1;
		}
		//全局变量：文件个数
		int files = 0;
		//队列
		Queue<File> queue = new LinkedList<>();
		//表示此时的file文件对象是文件夹，直接入队列
		queue.add(file);
		//如果队列不为空
		while (!queue.isEmpty()) {
			//取出队列的顶部元素
			File cur = queue.poll();
			File[] fileArray = cur.listFiles();
			if (fileArray != null) {
				for (File listFile : fileArray) {
					//如果是文件，统计文件个数 + 1
					if (listFile.isFile()) {
						files++;
					}
					//如果是文件夹，直接入队列
					if (listFile.isDirectory()) {
						queue.add(listFile);
					}
				}
			}
		}
		//返回文件个数
		return files;
	}

	/**
	 * 使用迭代的方式：栈 -> 深度优先遍历
	 * 注意这个函数也会统计隐藏文件
	 * @param folderPath 文件/文件夹路径
	 * @return
	 */
	public static int getFileNumberWithStack(String folderPath) {
		if (folderPath == null || folderPath.length() == 0) {
			return 0;
		}
		//根据路径创建文件对象
		File file = new File(folderPath);
		//如果文件对象既不是文件夹也不是文件，那么直接返回0
		if (!file.isDirectory() && !file.isFile()) {
			return 0;
		}
		//如果文件对象是文件，那么返回文件数1
		if (file.isFile()) {
			return 1;
		}
		//全局变量：文件个数
		int files = 0;
		//栈
		Stack<File> stack = new Stack<>();
		//表示此时的file文件对象是文件夹，直接入栈
		stack.push(file);
		//如果队列不为空
		while (!stack.isEmpty()) {
			//取出队列的顶部元素
			File cur = stack.pop();
			File[] fileArray = cur.listFiles();
			if (fileArray != null) {
				for (File listFile : fileArray) {
					//如果是文件，统计文件个数 + 1
					if (listFile.isFile()) {
						files++;
					}
					//如果是文件夹，直接入栈
					if (listFile.isDirectory()) {
						stack.push(listFile);
					}
				}
			}
		}
		//返回文件个数
		return files;
	}
}
