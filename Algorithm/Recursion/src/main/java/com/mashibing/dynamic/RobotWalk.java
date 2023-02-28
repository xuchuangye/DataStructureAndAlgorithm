package com.mashibing.dynamic;

/**
 * 机器人问题 --> 使用递归的方式实现
 * <p>
 * 假设有排成一行的N个位置，记为1-N，N一定大于或等于2
 * 开始时机器人在其中的M位置上(M一定是1-N中的一个)
 * 如果机器人走到1的位置，下一步只能往右来到2的位置
 * 如果机器人走到N的位置，下一步只能往左来到N-1的位置
 * 如果机器人来到中间的位置，下一步既可以往左走也可以往右走
 * 规定机器人必须走K步，最终来到P的位置(P也是1-N中的一个)的方法数有多少
 * 规定四个参数：N、M、K、P，返回方法数
 * <p>
 * 基本规则：
 * 1、当机器人走到0的位置，下一步必须走1的位置
 * 2、当机器人走到N的位置，下一步必须走N - 1的位置
 * 3、当机器人走到中间的位置，下一步既可以往左走，也可以往右走
 *
 * @author xcy
 * @date 2022/5/7 - 8:03
 */
public class RobotWalk {
	public static void main(String[] args) {
		int count = robotWalk(5, 2, 4, 6);
		System.out.println("方法数：" + count);
		int i = robotWalkToCache(5, 2, 4, 6);
		System.out.println("方法数：" + i);
		int sum = robotWalkToTable(5, 2, 4, 6);
		System.out.println("方法数：" + sum);
	}


	/**
	 * 返回机器人不管怎么走，从start位置出发，走完K步之后到达目标target的多少种走法
	 *
	 * @param N      总共N个位置
	 * @param start  机器人的起点位置
	 * @param target 目标所在的位置
	 * @param K      机器人需要走的步数
	 * @return
	 */
	public static int robotWalk(int N, int start, int target, int K) {
		if (N < 2 || start < 1 || start > N || target < 1 || target > N || K < 1) {
			return -1;
		}
		return process(start, K, target, N);
	}

	/**
	 * @param cur  当前机器人所在的位置
	 * @param rest 机器人还需要走的步数
	 * @param aim  目标的位置
	 * @param N    有哪些位置1-N
	 * @return 机器人从cur位置出发，走过rest步数之后，到达目标位置aim的方法数有多少？
	 */
	public static int process(int cur, int rest, int aim, int N) {
		//判断剩余步数是否为0
		if (rest == 0) {
			//判断cur是否在目标的位置，如果是，方法数为1，否则为0
			return cur == aim ? 1 : 0;
		}
		//此时证明rest步数不为0
		//判断当前位置是否来到1，下一步必须去cur + 1的位置，步数rest - 1
		if (cur == 1) {
			return process(cur + 1, rest - 1, aim, N);
		}
		//判断当前位置是否来到N，下一步必须去N - 1的位置，步数rest - 1
		if (cur == N) {
			return process(N - 1, rest - 1, aim, N);
		}
		//当前位置在中间，下一步既可以往左走cur - 1，也可以往右走cur + 1，步数rest - 1
		return process(cur - 1, rest - 1, aim, N) + process(cur + 1, rest - 1, aim, N);
	}

	/**
	 * 返回机器人不管怎么走，从start位置出发，走完K步之后到达目标target的多少种走法 --> 带缓存
	 *
	 * @param N      有哪些位置1-N
	 * @param start  机器人的起点位置
	 * @param target 机器人到达的目标位置
	 * @param K      机器人必须要走的步数
	 * @return 返回机器人不管怎么走，从start位置出发，走完K步之后到达目标target的多少种走法
	 */
	public static int robotWalkToCache(int N, int start, int target, int K) {
		int[][] cache = new int[N + 1][K + 1];
		for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < K + 1; j++) {
				cache[i][j] = -1;
			}
		}
		return coreLogic(N, start, target, K, cache);
	}

	/**
	 * 动态规划 --> 从顶向下的动态规划(记忆化搜索)
	 *
	 * @param N     有哪些位置1-N
	 * @param cur   机器人当前的位置
	 * @param aim   机器人到达的目标位置
	 * @param rest  机器人还剩下要走的步数
	 * @param cache 缓存所有的元素初始化为-1，如果不为-1，表示已经计算过，直接返回计算的结果即可，
	 *              作为缓存并不关心返回值的依赖cur和aim，有结果就返回结果
	 * @return
	 */
	public static int coreLogic(int N, int cur, int aim, int rest, int[][] cache) {
		//cache[cur][rest]上的索引不为-1，表示已经计算过，直接返回计算过的结果即可
		if (cache[cur][rest] != -1) {
			return cache[cur][rest];
		}
		//否则就是没有计算过
		int ans = 0;
		//判断步数是否为0，如果为0，证明已经走完了
		if (rest == 0) {
			//查看当前机器人所在的位置是否是要到达的目标的位置
			ans = cur == aim ? 1 : 0;
		}
		//判断机器人所在的位置cur是否是1
		else if (cur == 1) {
			//如果是1，那么下一步必须要走cur+1的位置，目标aim不变，步数rest-1，缓存不变
			ans = coreLogic(N, cur + 1, aim, rest - 1, cache);
		}
		//判断机器人所在的位置cur是否是N
		else if (cur == N) {
			//如果是N，那么下一步必须要走N-1的位置，目标aim不变，步数rest-1，缓存不变
			ans = coreLogic(N, N - 1, aim, rest - 1, cache);
		}
		//机器人所在的位置在中间，既可以往左右cur-1，也可以往右走cur+1，步数rest-1
		else {
			ans = coreLogic(N, cur - 1, aim, rest - 1, cache)
					+ coreLogic(N, cur + 1, aim, rest - 1, cache);
		}
		//将计算的结果记录到缓存中，方便用的时候直接取
		cache[cur][rest] = ans;
		return ans;
	}


	/**
	 * 返回机器人不管怎么走，从start位置出发，走完K步之后到达目标target的多少种走法 --> 使用最终动态规划的版本
	 * @param N 有哪些位置1-N
	 * @param start 机器人当前的位置
	 * @param target 机器人到达的目标位置
	 * @param K 机器人必须要走的步数
	 * @return
	 */
	public static int robotWalkToTable(int N, int start, int target, int K) {
		int[][] table = new int[N + 1][K + 1];
		//第target行第0列的值是1
		table[target][0] = 1;

		for (int rest = 1; rest <= K; rest++) {
			//第
			table[1][rest] = table[2][rest - 1];
			for (int cur = 2; cur <= N - 1; cur++) {
				table[cur][rest] = table[cur - 1][rest - 1] + table[cur + 1][rest - 1];
			}
			table[N][rest] = table[N - 1][rest - 1];
		}
		/*for (int i = 0; i < N + 1; i++) {
			for (int j = 0; j < K + 1; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}*/
		return table[start][K];
	}
}
