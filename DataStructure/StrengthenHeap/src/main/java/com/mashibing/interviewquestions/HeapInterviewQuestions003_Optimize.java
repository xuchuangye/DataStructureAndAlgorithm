package com.mashibing.interviewquestions;

import com.mashibing.heap.HeapGreator;

import java.util.*;

/**
 * 使用加强堆进行优化的方法
 * 时间复杂度：O(N * (logN + logK + K))
 * 堆的题目：
 * 给定一个整型数组：int[] arr和一个布尔类型数组：boolean[] op
 * 两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作
 * int[] arr =    {3,3,1,2,1,2,5}
 * boolean[] op = {T,T,T,T,F,T,F}
 * 依次表示：
 * 用户3购买了一件商品，用户3购买了一件商品，用户1购买了 一件商品，用户2购买了一件商品，
 * 用户1退货了一件商品，用户2购买了一件商品，用户5退货了一件商品
 * 一对arr[i]和op[i]就代表一个事件：
 * 用户号为arr[i]，op[i] == T 就代表这个用户购买了一件商品
 * op[i] == F 就代表这个用户退货了一件商品
 * 现在你作为电商平台负责人，你想在每一件事件到来的时候，都给购买次数最多的前K名用户颁奖
 * 所以每个事件发生后，你都需要一个得奖名单(得奖区)
 * <p>
 * 得奖系统的规则：
 * 1、如果某个用户购买商品数为0，但是发生了退货事件，则认为该事件无效，得奖名单和上一个事件发生后一致，比如例子中的5用户
 * 2、某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
 * 3、每次都是最多K个用户得奖，K也为传入的参数，K是正整数
 * 如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
 * 4、得奖系统分为得奖区和候选区，任何用户只要购买数>0，一定在这两个区域中的一个
 * 5、购买数最大的前K名用户进入得奖区，在最初时如果得奖区没有达到K个用户，那么新来的用户直接进入得奖区
 * 6、如果购买数不足以进入得奖区的用户，进入候选区
 * 7、如果候选区购买数最多的用户，已经足以进入得奖区
 * 该用户就会替换得奖区中购买数最少的用户(大于才能替换)，
 * 如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
 * 如果候选区中购买数最多的用户有多个，让最早进入候选区的用户进入得奖区
 * 8、候选区和得奖区是两套时间
 * 因用户只会在一个区域中，所以只会有一个区域的时间，另一个没有
 * 从得奖区出来进入候选区的用户，得奖区的时间删除
 * 进入候选区的时间就是当前事件的时间(可以理解为arr[i]和op[i]中的i)
 * 从候选区出来进入得奖区的用户，候选区的时间删除
 * 进入得奖区的时间就是当前事件的时间(可以理解为arr[i]和op[i]中的i)
 * <p>
 * 9、如果某用户购买数==0，不管在哪个区域中都离开，区域时间删除，离开是指彻底离开，哪个区域也不会找到该用户
 * 如果下次该用户又发生购买事件，产生>0的购买数，会再次根据之前的规则回到某一个区域中，进入区域的事件重记
 *
 * @author xcy
 * @date 2022/4/20 - 8:49
 */
public class HeapInterviewQuestions003_Optimize {
	public static void main(String[] args) {
		//测试时间
		/*int[] arr = SortCommonUtils.getArray();

		Date dateStart = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = simpleDateFormat.format(dateStart);
		System.out.printf("堆排序前的时间：\n%s\n", start);

		System.out.println();
		heapSort(arr);

		Date dateEnd = new Date();
		String end = simpleDateFormat.format(dateEnd);
		System.out.printf("堆排序后的时间：\n%s\n", end);*/

		//测试错误
		/*//测试次数
		int testTime = 1000000;
		//数组长度
		int length = 100;
		//数组元素的值的范围
		int value = 100;
		//是否测试成功，默认测试是成功的
		boolean success = true;
		for (int i = 1; i <= testTime; i++) {
			//生成随机长度值也随机的数组
			int[] array = SortCommonUtils.generateRandomArray(length, value);
			//拷贝数组
			int[] arr = SortCommonUtils.copyArray(array);

			//自己实现的排序方式
			heapSort(array, 2);
			//系统提供的排序方式
			SortCommonUtils.sort(arr);

			//判断array和arr数组中的所有元素是否都相等
			if (!SortCommonUtils.isEqual(array, arr)) {
				//如果不相等，则标记success为false
				success = false;
				SortCommonUtils.printArray(array);
				SortCommonUtils.printArray(arr);
				break;
			}

		}
		System.out.println(success ? "测试成功" : "测试失败");*/

		int[] arr = {3, 1, 4, 2, 5};
	}

	/**
	 * 用户类
	 */
	public static class Consumer {
		/**
		 * 用户id
		 */
		public int id;
		/**
		 * 用户购买数
		 */
		public int buy;
		/**
		 * 用户进入区域的时间
		 */
		public int enterTime;

		public Consumer(int id, int buy, int enterTime) {
			this.id = id;
			this.buy = buy;
			this.enterTime = enterTime;
		}
	}

	/**
	 * 得奖区，小根堆的排序方式
	 */
	public static class DaddyComparator implements Comparator<Consumer> {

		@Override
		public int compare(Consumer o1, Consumer o2) {
			//购买数少的放前面，因为少的需要和候选区购买数多的用户进行交换
			//如果购买数相同，谁进入区域时间早的，需要和候选区购买数多的用户进行交换
			return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
		}
	}

	/**
	 * 候选区，大根堆的排序方式
	 */
	public static class CandidateComparator implements Comparator<Consumer> {

		@Override
		public int compare(Consumer o1, Consumer o2) {
			//购买数多的放前面，因为更有资格和得奖区的用户进行交换
			//如果购买数相同，谁进入区域时间早的，更有资格和得奖区的用户进行交换
			return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
		}
	}

	/**
	 * 得奖名单类
	 */
	public static class DaddyList {
		//用户表
		HashMap<Integer, Consumer> consumers;
		//候选区，大根堆
		HeapGreator<Consumer> candsHeap;
		//得奖区，小根堆
		HeapGreator<Consumer> daddyHeap;
		//得奖名单的个数
		final int daddyLimit;

		public DaddyList(int limit) {
			consumers = new HashMap<>();
			candsHeap = new HeapGreator<>(new CandidateComparator());
			daddyHeap = new HeapGreator<>(new DaddyComparator());
			daddyLimit = limit;
		}

		/**
		 * @param time        用户进入区域的时间
		 * @param id          用户id
		 * @param butOrRefund 用户购买或者退货
		 */
		public void operate(int time, int id, boolean butOrRefund) {
			//用户购买数为0并且出现退货事件，就当没发生，直接退出
			if (!butOrRefund && !consumers.containsKey(id)) {
				return;
			}
			//如果没有记录当前用户，那么就创建该用户
			if (!consumers.containsKey(id)) {
				consumers.put(id, new Consumer(id, 0, 0));
			}
			//取出该用户
			Consumer consumer = consumers.get(id);
			//判断是购买还是退货
			if (butOrRefund) {
				//购买buy++
				consumer.buy++;
			} else {
				//退货buy--
				consumer.buy--;
			}
			//该用户购买数为0，清除
			if (consumer.buy == 0) {
				consumers.remove(id);
			}
			//用户既不在候选区，也不在得奖区
			if (!candsHeap.contains(consumer) && !daddyHeap.contains(consumer)) {
				//新来的用户
				//如果得奖区的用户小于得奖名单的个数
				if (daddyHeap.size() < daddyLimit) {
					//记录进入得奖区域的时间
					consumer.enterTime = time;
					//将该用户添加到得奖区
					daddyHeap.push(consumer);
				} else {
					//记录进入候选区域的时间
					consumer.enterTime = time;
					//将该用户添加到候选区
					candsHeap.push(consumer);
				}
			}
			//如果该用户已经在候选区
			else if (candsHeap.contains(consumer)) {
				//判断该用户 的购买数是否==0，如果购买数为0则直接删除该用户
				if (consumer.buy == 0) {
					candsHeap.remove(consumer);
				} else {
					//否则添加到大根堆中，进行堆的调整
					candsHeap.resign(consumer);
				}
			}
			//如果该用户已经在得奖区
			else {
				//判断该用户的购买数是否==0，如果购买数为0则直接删除该用户
				if (consumer.buy == 0) {
					daddyHeap.remove(consumer);
				} else {
					//否则添加到小根堆中，进行堆的调整
					daddyHeap.resign(consumer);
				}
			}
			//候选区购买数最多的和得奖区购买数最少的用户进行交换
			daddyMove(time);
		}

		/**
		 * 候选区购买数最多的和得奖区购买数最少的用户进行交换
		 *
		 * @param time 候选区购买数最多的和得奖区购买数最少的用户进行交换的时间
		 */
		public void daddyMove(int time) {
			//候选区为空，无法进行交换
			if (candsHeap.isEmpty()) {
				return;
			}
			//候选区不为空，得奖区没有满
			if (daddyHeap.size() < daddyLimit) {
				//因为候选区是大根堆，所以堆顶元素肯定是购买数最多的用户
				Consumer consumer = candsHeap.pop();
				//更新进入得奖区域的时间
				consumer.enterTime = time;
				//将当前候选区中购买数最多的用户添加到得奖区中
				daddyHeap.push(consumer);
			} else {
				//候选区不为空，并且得奖区满了
				//判断候选区购买数最多的用户购买数 > 得奖区购买数最少的用户的购买数，就进行交换
				if (candsHeap.pop().buy > daddyHeap.pop().buy) {
					Consumer candConsumer = candsHeap.pop();
					Consumer daddyConsumer = daddyHeap.pop();
					candConsumer.enterTime = time;
					daddyConsumer.enterTime = time;
					candsHeap.push(daddyConsumer);
					daddyHeap.push(candConsumer);
				}
			}
		}

		/**
		 * 获取得奖名单
		 * @return
		 */
		public List<Integer> getAllDaddise() {
			List<Consumer> allElements = daddyHeap.getAllElements();
			List<Integer> ans = new ArrayList<>();
			for (Consumer allElement : allElements) {
				ans.add(allElement.id);
			}
			return ans;
		}
	}

	/**
	 * 返回所有事件发生之后的得奖名单的集合
	 * @param arr 用户数组
	 * @param k 得奖名单的个数
	 * @return 每次事件的发生，产生一个得奖名单，将所有的得奖名单集合并返回
	 */
	public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
		List<List<Integer>> ans = new ArrayList<>();
		DaddyList daddyList = new DaddyList(k);
		for (int i = 0; i < arr.length; i++) {
			daddyList.operate(i, arr[i], op[i]);
			ans.add(daddyList.getAllDaddise());
		}
		return ans;
	}
}
