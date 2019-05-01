package cn.com.lbn.lambda;

public class LamdaTestFuncCaller {
	public void testCall(LamdaTestFuncInfc funcInfc) {
		String str = "Lamda";
		funcInfc.testFunction(str);
	}
}
