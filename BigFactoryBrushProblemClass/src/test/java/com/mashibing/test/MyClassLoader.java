package com.mashibing.test;


/**
 * @author xcy
 * @date 2022/10/6 - 9:52
 */
public class MyClassLoader {

	/*
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException
	{
		synchronized (getClassLoadingLock(name)) {
			//1.类加载机制之缓存机制
			// First, check if the class has already been loaded
			Class<?> c = findLoadedClass(name);
			if (c == null) {
				long t0 = System.nanoTime();
				try {
					//private final ClassLoader parent;
					//只要不是Bootstrap，那么parent就不为空
					if (parent != null) {
						c = parent.loadClass(name, false);
					} else {
						c = findBootstrapClassOrNull(name);
					}
				} catch (ClassNotFoundException e) {
					// ClassNotFoundException thrown if class not found
					// from the non-null parent class loader
				}
				if (c == null) {
					// If still not found, then invoke findClass in order
					// to find the class.
					long t1 = System.nanoTime();
					c = findClass(name);
					// this is the defining class loader; record the stats
					PerfCounter.getParentDelegationTime().addTime(t1 - t0);
					PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
					PerfCounter.getFindClasses().increment();
				}
			}
			if (resolve) {
				resolveClass(c);
			}
			return c;
		}
	}
	*/
}
