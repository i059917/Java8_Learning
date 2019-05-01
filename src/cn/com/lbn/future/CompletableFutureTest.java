package cn.com.lbn.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
			}
			return 1;
		});
		
		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
			}
			return 2;
		});
		
		CompletableFuture<Integer> resultFuture = future1.thenCombine(future2, CompletableFutureTest::add);
		try {
			System.out.println(resultFuture.get());
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Time cost: " + (end - start));
	}
	
	public static Integer add(Integer a, Integer b) {
		return a + b;
	}
}
