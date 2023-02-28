package com.mashibing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表达式的计算器
 * @author xcy
 * @date 2022/3/17 - 16:15
 */
public class InversePolishExpression {
	public static void main(String[] args) {
		//中缀表达式：(3 + 4) * 5 - 6  -> 后缀表达式 3 4 + 5 * 6 -
		//String suffixExpression = "3 4 + 5 * 6 -";
		//中缀表达式：20 - 3 * 8 + 50 * 2 - 10 + 100 / 10  -> 后缀表达式 20 3 8 * - 50 2 * + 10 - 100 10 / +
		String suffixExpression = "20 3 8 * - 50 2 * + 10 - 100 10 / +";//96
		List<String> stringList = getStringList(suffixExpression);
		//System.out.println(stringList);

		int result = suffixExpressionResult(stringList);
		System.out.printf("后缀表达式的计算结果是：%d\n", result);
	}

	/**
	 * 返回将后缀表达式按照空格分隔的数组存放在List集合中
	 *
	 * @param suffixExpression 后缀表达式
	 * @return
	 */
	public static List<String> getStringList(String suffixExpression) {
		String[] split = suffixExpression.split(" ");
		return new ArrayList<>(Arrays.asList(split));
	}

	/**
	 * 1) 从左至右扫描，将3和4压入堆栈；
	 * 2) 遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
	 * 3) 将5入栈；
	 * 4) 接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
	 * 5) 将6入栈；
	 * 6) 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
	 *
	 * @param list
	 * @return
	 */
	public static int suffixExpressionResult(List<String> list) {
		Stack<String> stack = new Stack<>();
		for (String item : list) {
			//使用正则表达式进行匹配，匹配多位数
			if (item.matches("\\d+")) {
				stack.push(item);
			} else {
				//取出栈顶元素
				int num2 = Integer.parseInt(stack.pop());
				//取出次顶元素
				int num1 = Integer.parseInt(stack.pop());
				int result = 0;
				if ("+".equals(item)) {
					result = num1 + num2;
				} else if ("-".equals(item)) {
					result = num1 - num2;
				} else if ("*".equals(item)) {
					result = num1 * num2;
				} else if ("/".equals(item)) {
					result = num1 / num2;
				} else {
					throw new RuntimeException("运算符有误");
				}
				//计算结果入栈
				stack.push(String.valueOf(result));
			}
		}
		//最终栈中只剩一个元素，也就是计算的结果
		return Integer.parseInt(stack.pop());
	}
}

