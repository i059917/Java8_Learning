package cn.com.lbn.stream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WikiTitleCountTest {
	
	private static final String FOLDER = "/Users/i059917/Downloads/tmp/";
	private static final String FILE_NAME = FOLDER + "enwiki-20170220-pages-articles-multistream-index.txt";
	
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
	public void testCount() throws IOException {
		long countBySequantial = Files.lines(Paths.get(FILE_NAME)).count();
		long countByParallel = Files.lines(Paths.get(FILE_NAME)).parallel().count();
		
		assertThat(countBySequantial, equalTo(countByParallel));
		
		System.out.println("countBySequantial: " + countBySequantial);
		System.out.println("countByParallel:   " + countByParallel);
	}

}
