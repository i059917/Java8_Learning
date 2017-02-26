package cn.com.lbn.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileStreamTest {
	
	private static final String fileName = "/Users/i059917/Downloads/tmp/enwiki-20170220-pages-articles-multistream-index.txt";
	//private static Map<String, List<String>> keyWordMap = new LinkedHashMap<String, List<String>>();
	private static List<String> resultList = Collections.synchronizedList(new ArrayList<String>());
	
	public static void main(String[] args) throws Exception {
		//initArrayListForEachKey();	

		//System.out.println(Files.lines(Paths.get(fileName)).count());
		
		long start = System.currentTimeMillis();
		Predicate<String> filterByInvalidLetter = (element) -> {
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
				if(Character.isLetter(c)) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		};
		
		Function<String, String> mapToArticleTitle = (element) -> {
			String title = element.substring(element.lastIndexOf(":") + 1, element.length());
			return title;
		};
		
		Predicate<String> filterBySameFirst4Char = (element) -> {
			if(element.length() >= 4) {
				if(element.charAt(0) == element.charAt(1) && element.charAt(1) == element.charAt(2)
						&& element.charAt(2) == element.charAt(3)) {
					
					return true;
				}
			}
			return false;
		};
		
		resultList = Files.lines(Paths.get(fileName)).parallel().filter(filterByInvalidLetter)
				.map(mapToArticleTitle).filter(filterBySameFirst4Char).collect(Collectors.toList());
		Collections.sort(resultList);
		
		System.out.println("Result count: " + resultList.size());
		System.out.println("Cost time: " + (System.currentTimeMillis() - start));
		System.out.println(resultList);
		
		//for(String key : keyWordMap.keySet()) {
		//	System.out.println(key + " - " + keyWordMap.get(key).size());
		//}
	}
	
//	private static void initArrayListForEachKey() {
//		keyWordMap.put("A", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("B", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("C", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("D", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("E", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("F", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("G", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("H", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("I", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("J", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("K", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("L", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("M", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("N", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("O", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("P", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("Q", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("R", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("S", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("T", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("U", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("V", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("W", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("X", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("Y", Collections.synchronizedList(new ArrayList<String>()));
//		keyWordMap.put("Z", Collections.synchronizedList(new ArrayList<String>()));
//	}
}
