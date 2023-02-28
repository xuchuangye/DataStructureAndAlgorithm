package com.mashibing.test;

import java.lang.ref.SoftReference;

/**
 * @author xcy
 * @date 2022/10/5 - 15:53
 */
public class SoftReferenceDemo {
	public static class Worker {

	}
	public static void main(String[] args) {
		Worker worker = new Worker();
		SoftReference<Worker>  softReference = new SoftReference<>(worker);

		worker = null;

		//通知JVM进行gc()
		System.gc();

		if (softReference != null) {
			Worker workerSoft = softReference.get();
			System.out.println(workerSoft);
		}else {
			worker = new Worker();
			softReference = new SoftReference<>(worker);
		}
		System.out.println(worker);
		System.out.println(softReference);
	}
}
