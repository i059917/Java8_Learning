package cn.com.lbn.lambda;

public class LamdaFuncInfcTest {
	public static void main(String args[]) {
		LamdaTestFuncCaller caller = new LamdaTestFuncCaller();
		
		// 1. Function interface expression.
		caller.testCall((output) -> System.out.println("Hello " + output + "!"));
		
		// 2. Function interface instance.
		LamdaTestFuncInfc funcInfc = (output) -> System.out.println("Hello " + output + "!");
		caller.testCall(funcInfc);
		
		// 3. Method Reference
		//LamdaTestMethodReference methodRef = new LamdaTestMethodReference();
		caller.testCall(LamdaTestMethodReference::printHello);
	}
}
