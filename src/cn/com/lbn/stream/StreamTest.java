package cn.com.lbn.stream;

import java.util.stream.Stream;

public class StreamTest {

	public static void main(String[] args) {
		// It distinct all the duplicated elements, only keeps one which the place is the first time founded.
		Stream<String> strStream = Stream.of("Apple", "Apple", "Orange", "Apple").distinct();
		
		Object[] objArray = strStream.toArray();
		for(Object str : objArray) {
			System.out.println(str);
		}
	}
}
