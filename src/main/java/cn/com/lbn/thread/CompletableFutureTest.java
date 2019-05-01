package cn.com.lbn.thread;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.junit.Test;

public class CompletableFutureTest {

	@Test
	public void shouldCreateCF() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
		assertTrue(cf.isDone());
		assertEquals("message", cf.getNow(null));
	}
	
	@Test
	public void shouldCreateRunnableCF() {
		CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
			assertTrue(Thread.currentThread().isDaemon());
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		assertFalse(cf.isDone());
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(cf.isDone());
	}
	
	@Test
	public void shouldRunInThenApply() {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
			System.out.println("Run in thenApply()");
			return s;
		});
	}
	
	@Test
	public void shouldApplyToResult() {
		System.out.println("Main thread id: " + Thread.currentThread().getId());
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
			System.out.println("thenApply thread id: " + Thread.currentThread().getId());
			assertFalse(Thread.currentThread().isDaemon());
			return s.toUpperCase();
		});
		
		assertEquals("message".toUpperCase(), cf.getNow(null));
	}
	
	@Test
	public void shouldApplyAsyncToResult() {
		System.out.println("Main thread id: " + Thread.currentThread().getId());
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
			System.out.println("thenApply thread id: " + Thread.currentThread().getId());
			assertTrue(Thread.currentThread().isDaemon());
			return s.toUpperCase();
		});
		
		assertEquals("message".toUpperCase(), cf.join());
	}
	
	@Test
	public void shouldConsumeResult() {
		CompletableFuture<Void> cf = CompletableFuture.completedFuture("message").thenAccept(s -> {
			assertEquals("message", s);
		});
	}
	
//	@Test
//	public void shouldApplyExceptionToResult() {
//		CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase, 
//				CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
//	}
	
	@Test
	public void shouldGetEitherResult() {
		final int ONE_HUNDRED = 100;
		CompletableFuture<Integer> cf1 = CompletableFuture.completedFuture(ONE_HUNDRED).thenApplyAsync(i -> {
			int sum = 0;
			for(int e = 1; e <= i; e++) {
				sum = sum + e;
			}
			try {
				Thread.sleep(new Random().nextInt(90));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Return result in cf1");
			return sum;
		});
		
		CompletableFuture<Integer> cf2 = cf1.applyToEither(CompletableFuture.completedFuture(ONE_HUNDRED).thenApplyAsync(i -> {
			int sum = 0;
			for(int e = i; e >= 1; e--) {
				sum = sum + e;
			}
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Return result in cf2");
			return sum;
		}), result-> {
			System.out.println("result= " + result);
			return result;
		});
		
		assertEquals(cf2.join(), new Integer(5050));
	}
}
