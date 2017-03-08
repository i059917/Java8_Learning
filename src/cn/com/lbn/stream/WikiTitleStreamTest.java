package cn.com.lbn.stream;

import static cn.com.lbn.stream.WikiTitleTestFunctions.filterByInvalidLetter;
import static cn.com.lbn.stream.WikiTitleTestFunctions.filterBySameFirst4Char;
import static cn.com.lbn.stream.WikiTitleTestFunctions.mapToArticleTitle;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
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
	private static long timeCostSquential;
	private static long timeCostParallel;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		assertThat(resultSequential, equalTo(resultParallel));
		
		printOutSummary();
	}
	
	private void processSequentially() throws IOException {
		long start = System.currentTimeMillis();
		
		resultSequential = Files.lines(Paths.get(fileName)).filter(filterByInvalidLetter)
				.map(mapToArticleTitle).filter(filterBySameFirst4Char).collect(Collectors.toList());
		
		timeCostSquential = System.currentTimeMillis() - start;
		System.out.println("Cost time in sequantial: " + timeCostSquential);
	}
	
	private void processParralelly() throws IOException {
		long start = System.currentTimeMillis();
		
		resultParallel = Files.lines(Paths.get(fileName)).parallel().filter(filterByInvalidLetter)
				.map(mapToArticleTitle).filter(filterBySameFirst4Char).collect(Collectors.toList());
		
		timeCostParallel = System.currentTimeMillis() - start;
		System.out.println("Cost time in parallel: " + timeCostParallel);
	}
	
	private void printOutSummary() {
		System.out.println();
		System.out.println("Sequential result count: " + resultSequential.size());
		System.out.println("Parallel result count: " + resultParallel.size());
		System.out.println();
		System.out.println("Sequential result: " + resultSequential);
		System.out.println("Parallel result: " + resultParallel);
		int fasterRate = Math.round(((timeCostSquential - timeCostParallel)/(float)timeCostSquential) * 100);
		System.out.println();
		System.out.println("Parallel is " + fasterRate + "% faster than sequential.");
	}
}
