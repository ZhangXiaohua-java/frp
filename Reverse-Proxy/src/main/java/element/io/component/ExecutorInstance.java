package element.io.component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 张晓华
 * @date 2023-3-22
 */
public class ExecutorInstance {


	public static int coreSize = 3;

	public static int maxSize = 10;

	public static int idleTime = 30;

	public static int queueCapacity = 10;


	public static Executor getExecutor() {
		return Instance.executor;
	}


	public static class Instance {
		private static final Executor executor;

		static {
			executor = new ThreadPoolExecutor(coreSize, maxSize, idleTime, TimeUnit.MILLISECONDS,
					new ArrayBlockingQueue<>(10), new ThreadFactory() {
				AtomicInteger atomicInteger = new AtomicInteger();

				@Override
				public Thread newThread(Runnable runnable) {
					Thread thread = new Thread(runnable);
					thread.setName("pool-" + atomicInteger.getAndIncrement());
					return thread;
				}
			}, new ThreadPoolExecutor.AbortPolicy()
			);
		}
	}


}
