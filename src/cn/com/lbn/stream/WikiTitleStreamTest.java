package cn.com.lbn.stream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WikiTitleStreamTest {
	
	private static final String fileName = "/Users/i059917/Downloads/tmp/enwiki-20170220-pages-articles-multistream-index.txt";
	private static List<String> resultSequential = null;
	private static List<String> resultParallel = null;
	private static Predicate<String> filterByInvalidLetter = null;
	private static Function<String, String> mapToArticleTitle = null;
	private static Predicate<String> filterBySameFirst4Char = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Initialize the functions which will be used in stream processing.
		filterByInvalidLetter = (element) -> {
			if(element == null || element.length() == 0 ) {
				return false;
			}
			if(!element.contains(":")) {
				return false;
			}
			if(element.split(":").length != 3) {
				return false;
			}

			int lastSplitIndex = element.lastIndexOf(":");
			for(int i = 0; i < lastSplitIndex; i++) {
				char c = element.charAt(i);
				if(Character.isDigit(c) || c == ':') {
					continue;
				} else {
					return false;
				}
			}
			for(int i = lastSplitIndex + 1; i < element.length(); i++) {
				char c = element.charAt(i);
				if(((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		};
		
		mapToArticleTitle = (element) -> {
			String title = element.substring(element.lastIndexOf(":") + 1, element.length());
			return title;
		};
		
		filterBySameFirst4Char = (element) -> {
			if(element.length() >= 4) {
				if(element.charAt(0) == element.charAt(1) && element.charAt(1) == element.charAt(2)
						&& element.charAt(2) == element.charAt(3)) {
					
					return true;
				}
			}
			return false;
		};
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTitleStream() throws IOException {
		processSequentially();
		processParralelly();
		
		Collections.sort(resultSequential);
		Collections.sort(resultParallel);
		
		System.out.println();
		System.out.println("Sequential result count: " + resultSequential.size());
		System.out.println("Parallel result count: " + resultParallel.size());
		System.out.println();
		System.out.println("Sequential result: " + resultSequential);
		System.out.println("Parallel result: " + resultParallel);
		
		assertThat(resultSequential, equalTo(resultParallel));
	}
	
	private void processSequentially() throws IOException {
		long start = System.currentTimeMillis();
		resultSequential = Files.lines(Paths.get(fileName)).filter(filterByInvalidLetter)
				.map(mapToArticleTitle).filter(filterBySameFirst4Char).collect(Collectors.toList());
		System.out.println("Cost time in sequantial: " + (System.currentTimeMillis() - start));
	}
	
	private void processParralelly() throws IOException {
		long start = System.currentTimeMillis();
		resultParallel = Files.lines(Paths.get(fileName)).parallel().filter(filterByInvalidLetter)
				.map(mapToArticleTitle).filter(filterBySameFirst4Char).collect(Collectors.toList());
		System.out.println("Cost time in parallel: " + (System.currentTimeMillis() - start));
	}
}
