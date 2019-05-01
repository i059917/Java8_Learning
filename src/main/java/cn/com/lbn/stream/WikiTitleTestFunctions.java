package cn.com.lbn.stream;

import java.util.function.Function;
import java.util.function.Predicate;

public interface WikiTitleTestFunctions {
	public static final Predicate<String> filterByInvalidLetter = (element) -> {
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
	
	public static final Function<String, String> mapToArticleTitle = (element) -> {
		String title = element.substring(element.lastIndexOf(":") + 1, element.length());
		return title;
	};
	
	public static final Predicate<String> filterBySameFirst4Char = (element) -> {
		if(element.length() >= 4) {
			if(element.charAt(0) == element.charAt(1) && element.charAt(1) == element.charAt(2)
					&& element.charAt(2) == element.charAt(3)) {				
				return true;
			}
		}
		return false;
	};
}
