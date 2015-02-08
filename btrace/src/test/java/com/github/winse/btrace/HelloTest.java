package com.github.winse.btrace;

import org.junit.Test;

public class HelloTest {

	@Test
	public void test() throws Exception {
		testTarget();
	}

	public void testNewStringWithBytes() {
		byte[] value = new byte[] { 'a', 'b', 'c' };
		System.out.println(new String(value));
	}

	public void testNewString() {
		System.out.println(new String());
	}
	
	public void testToString() {
		System.out.println(new StringBuilder().append(1234).append(456).toString());
	}
	
	public void testTarget() {
		testNewStringWithBytes();
	}

	public void testNewThread() throws InterruptedException {
		Thread.sleep(20 * 1000);  // 最佳方式就是使用Scanner，手动输入一个操作后执行后面的操作。scanner.nextLine()

		for (int i = 0; i < 100; i++) {
			final int index = i;
			new Thread(//
					new Runnable() {
						public void run() {
							System.out.println("my order: " + index);
						}
					} //
			).start();
		}
	}

}
