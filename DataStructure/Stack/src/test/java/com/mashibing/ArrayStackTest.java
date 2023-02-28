package com.mashibing;

import com.mashibing.stack.ArrayStack;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xcy
 * @date 2022/3/19 - 12:10
 */
@SpringBootTest
public class ArrayStackTest {
	@Test
	public void test() {
		ArrayStack arrayStack = new ArrayStack(10);
		arrayStack.push(1);
		arrayStack.push(2);
		arrayStack.push(3);
		arrayStack.push(4);
		arrayStack.push(5);

		System.out.println("初始状态的Stack");
		arrayStack.list();

		arrayStack.reverse(arrayStack);
		System.out.println("栈中元素逆转之后的Stack");
		arrayStack.list();
	}
}
