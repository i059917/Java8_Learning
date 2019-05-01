package cn.com.lbn.lamda;

public class LamdaTestFuncCaller {
	public void testCall(LamdaTestFuncInfc funcInfc) {
		String str = "Lamda";
		funcInfc.testFunction(str);
	}
}
