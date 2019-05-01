package cn.com.lbn.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileStreamTest {
	private static Map<String, List<String>> keyWordMap = new LinkedHashMap<String, List<String>>();
	
	public static void main(String[] args) throws Exception {
		initArrayListForEachKey();
		
		long start = System.currentTimeMillis();
		
		String fileName = "/Users/li_beining/Downloads/tmp/enwiki-20170220-pages-articles-multistream-index.txt";
		
		//System.out.println(Files.lines(Paths.get(fileName)).count());
		Files.lines(Paths.get(fileName)).limit(5000000).parallel().forEach((element) -> {
				if(element != null && element.indexOf(":") != -1) {
					String[] strArr = element.split(":");
					if(strArr != null && strArr.length > 0) {
						String title = strArr[strArr.length - 1];
						if(title != null && title.length() > 0) {
							char firstChar = title.charAt(0);
							if((firstChar >= 'A' && firstChar <= 'Z') || (firstChar >= 'a' && firstChar <= 'z')) {
								List<String> strList = keyWordMap.get(Character.toString(firstChar).toUpperCase());
								strList.add(element);
							}
						}
					}
				}
			}
		);
		
		System.out.println(System.currentTimeMillis() - start);
		
		for(String key : keyWordMap.keySet()) {
			System.out.println(key + " - " + keyWordMap.get(key).size());
		}
	}
	
	private static void initArrayListForEachKey() {
		keyWordMap.put("A", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("B", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("C", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("D", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("E", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("F", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("G", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("H", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("I", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("J", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("K", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("L", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("M", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("N", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("O", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("P", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("Q", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("R", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("S", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("T", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("U", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("V", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("W", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("X", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("Y", Collections.synchronizedList(new ArrayList<String>()));
		keyWordMap.put("Z", Collections.synchronizedList(new ArrayList<String>()));
	}
}
