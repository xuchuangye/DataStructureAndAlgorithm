package com.mashibing.interviewquestions;

import java.util.*;

/**
 * 比较暴力的方法
 * 时间复杂度：O(N² * logN)
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
public class HeapInterviewQuestions003_NoOptimize {
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
	 * 得奖区排序方式
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
	 * 候选区排序方式
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
	 * @param arr 用户数组，i表示时间点
	 * @param op  购买或者退货的记录
	 * @param k   得奖区的K个用户
	 * @return 返回每次购买或者退货事件发生后，得到一个得奖名单
	 */
	public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
		//用户表
		HashMap<Integer, Consumer> consumerHashMap = new HashMap<>();
		//候选区
		ArrayList<Consumer> cands = new ArrayList<>();
		//得奖区
		ArrayList<Consumer> daddy = new ArrayList<>();
		//得奖名单
		List<List<Integer>> ans = new LinkedList<>();
		//i作为时间点
		for (int i = 0; i < arr.length; i++) {
			//用户id
			int id = arr[i];
			//用户是否购买或者退货，true表示购买，false表示退货
			boolean butOrRefund = op[i];
			//如果用户购买数为0，并且用户发生了退货事件，得奖名单和和上一个事件发生后一致
			if (!butOrRefund && !consumerHashMap.containsKey(id)) {
				ans.add(getCurAns(daddy));
				continue;
			}
			//没有发生用户购买数为0，用户退货事件
			//1、用户购买数=0，此时购买事件
			//2、用户购买数>0，此时购买事件
			//3、用户购买数>0，此时退货事件

			//如果没有记录用户，说明该用户新来的，创建该用户
			if (!consumerHashMap.containsKey(id)) {
				consumerHashMap.put(id, new Consumer(id, 0, 0));
			}

			//取出该用户
			Consumer consumer = consumerHashMap.get(id);
			//判断该用户是否购买或者退货
			if (butOrRefund) {
				//如果是购买,buy++
				consumer.buy++;
			} else {
				//如果是退货,buy--
				consumer.buy--;
			}

			//如果购买数 == 0，说明该用户没有购买任何商品，移除该用户
			if (consumer.buy == 0) {
				consumerHashMap.remove(id);
			}
			//接着在候选区或者得奖区也要将该用户删除
			if (!cands.contains(id) && !daddy.contains(id)) {
				//如果该用户是新来的
				//如果得奖区中存在空闲区域，也就是得奖区的用户数<K个，该用户进入得奖区，记录时间
				if (daddy.size() < k) {
					consumer.enterTime = i;
					daddy.add(consumer);
				}
				//否则该用户进入候选区，记录时间
				else {
					consumer.enterTime = i;
					cands.add(consumer);
				}
			}
			//将得奖区和候选区所有购买数为0的用户清除cleanBuyZero
			cleanBuyZero(daddy);
			cleanBuyZero(cands);
			//候选区排序
			cands.sort(new CandidateComparator());
			//得奖区排序
			daddy.sort(new DaddyComparator());
			//得奖区和候选区的用户需要交换move
			move(cands, daddy, k, i);
			ans.add(getCurAns(daddy));
		}
		return ans;
	}

	private static void cleanBuyZero(ArrayList<Consumer> list) {
		//创建一个存储够购买数不为0的用户的集合
		ArrayList<Consumer> noBuyZero = new ArrayList<>();
		//遍历原始的集合
		for (Consumer consumer : list) {
			//将所有购买数不为0的用户添加到noBuyZero集合中
			if (consumer.buy != 0) {
				noBuyZero.add(consumer);
			}
		}
		//将原始的集合清空
		list.clear();
		//将存储购买数不为0的用户集合的所有用户添加到原始的集合中
		list.addAll(noBuyZero);
	}

	/**
	 * 如果出现用户购买数为0并且退货的事件，需要忽略该事件，并且恢复到上一次事件发生之后时的得奖名单
	 *
	 * @param daddy 得奖区
	 * @return 得奖名单
	 */
	private static List<Integer> getCurAns(ArrayList<Consumer> daddy) {
		List<Integer> ans = new ArrayList<>();
		for (Consumer consumer : daddy) {
			ans.add(consumer.id);
		}
		return ans;
	}

	/**
	 * 候选区购买数最多的用户和得奖区购买数最少的用户进行交换
	 *
	 * @param cands 候选区
	 * @param daddy 得奖区
	 * @param k     K个用户
	 * @param time  候选区购买数最多的用户和得奖区购买数最少的用户进行交换的时间
	 */
	public static void move(ArrayList<Consumer> cands, ArrayList<Consumer> daddy, int k, int time) {
		//候选区为空，直接返回
		if (!cands.isEmpty()) {
			return;
		}
		//候选区不为空，但是得奖区有空闲
		if (daddy.size() < k) {
			//1.先取出候选区购买数最多的用户
			Consumer consumer = cands.get(0);
			//2.修改该用户进入区域的时间enterTime
			consumer.enterTime = time;
			//3.将当前候选区中购买数最多的用户添加到得奖区中
			cands.remove(consumer);
			//4.最后再将当前候选区中购买数最多的用户删除
			daddy.add(consumer);
		} else {
			//候选区不为空，但是得奖区没有空闲
			//1.判断候选区中购买数最多的大于得奖区购买数最少的才会进行交换
			if (cands.get(0).buy > daddy.get(0).buy) {
				//2.取出候选区的购买数最多的用户，在候选区中删除该用户
				Consumer oldConsumer = cands.get(0);
				//3.取出得奖区的购买数最少的用户，在得奖区中删除该用户
				Consumer newConsumer = daddy.get(0);
				//4.更新时间
				oldConsumer.enterTime = time;
				newConsumer.enterTime = time;
				//5.将候选区的购买数最多的用户添加到得奖区，将得奖区的购买数最少的用户添加到候选区
				cands.add(newConsumer);
				daddy.add(oldConsumer);
			}
		}
	}
}
