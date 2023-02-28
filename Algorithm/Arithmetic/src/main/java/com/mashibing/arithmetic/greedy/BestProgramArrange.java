package com.mashibing.arithmetic.greedy;

import com.mashibing.common.ArithmeticUtils;
import com.mashibing.pojo.Program;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心算法的面试题
 * 一些项目需要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲
 * 给定每一个项目的开始时间和结束时间，你来安排宣讲的日程，要求会议室进行的宣讲的场次最多
 * 返回最多的宣讲场次
 *
 * @author xcy
 * @date 2022/5/1 - 16:48
 */
public class BestProgramArrange {
	public static void main(String[] args) {
		int programSize = 12;
		int timeMax = 20;
		int timeTimes = 1000000;
		System.out.println("测试开始！");
		for (int i = 0; i < timeTimes; i++) {
			Program[] programs = ArithmeticUtils.generatePrograms(programSize, timeMax);
			if (bestArrange(programs) != bestProgramArrange(programs)) {
				System.err.println("测试错误!");
			}
		}
		System.out.println("测试结束！");
	}

	/**
	 * 暴力方法
	 *
	 * @param programs 会议
	 * @return
	 */
	public static int bestProgramArrange(Program[] programs) {
		if (programs == null || programs.length == 0) {
			return 0;
		}

		return process(programs, 0, 0);
	}

	/**
	 * 目前会议已经来到安排好之后的时间点timeLine，已经安排了done个会议，剩下未安排的会议programs
	 *
	 * @param programs 剩下未安排的会议
	 * @param done     已经安排好的会议数
	 * @param timeLine 会议目前已经安排到的时间
	 * @return 返回最好的会议安排数
	 */
	public static int process(Program[] programs, int done, int timeLine) {
		//剩下未安排的会议为0，不需要安排，返回已经安排好的会议数即可
		if (programs.length == 0) {
			return done;
		}

		//有剩下未安排的会议，从已经安排好的会议数之后开始
		int surplus = done;
		for (int i = 0; i < programs.length; i++) {
			//会议开始的时间大于等于会议目前已经安排到的时间，表示可以安排会议
			if (programs[i].start >= timeLine) {
				//从未安排的会议中移除已经现在已经安排的会议
				Program[] next = removeCurProgram(programs, i);
				//后续安排的会议，done+1表示目前已经安排的会议数，从当前会议的结束时间继续安排下一个会议
				surplus = Math.max(surplus, process(next, done + 1, programs[i].end));
			}
		}
		return surplus;
	}

	public static Program[] removeCurProgram(Program[] programs, int index) {
		Program[] ans = new Program[programs.length - 1];
		int ansIndex = 0;
		for (int i = 0; i < programs.length; i++) {
			if (i != index) {
				ans[ansIndex++] = programs[i];
			}
		}
		return ans;
	}


	/**
	 * 时间比较器
	 * 根据会议的结束时间进行排序
	 */
	public static class TimeComparator implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			return o1.end - o2.end;
		}
	}

	/**
	 * 最好的安排
	 *
	 * @param programs 会以的数组
	 * @return
	 */
	public static int bestArrange(Program[] programs) {
		if (programs == null) {
			return 0;
		}
		Arrays.sort(programs, new TimeComparator());
		//当前会议的开始时间
		int startTime = 0;
		//安排的会议，目前为止安排了0场
		int result = 0;
		//对每一场会议进行遍历
		for (Program program : programs) {
			//如果会议开始时间比准备安排的会议时间早，证明可以安排这场会议
			if (startTime <= program.start) {
				//准备安排这场会议，result++
				result++;
				//安排的会议来到该会议的结束时间，确定下一场会议开始时间
				startTime = program.end;
			}
		}
		return result;
	}
}
