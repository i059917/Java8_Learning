package cn.com.lbn.lambda;

public class ClosureTest {

	public static void main(String[] args) {
		repeatMessage("Hello Lamda!", 10);;
	}

	public static void repeatMessage(String text, int count) {
		Runnable r1 = () -> {
			for(int i = 0; i < count; i++) {
				System.out.println(text +  " " + i + " r1" );
			}
		};
		new Thread(r1).start();
		
		Runnable r2 = new Runnable() {
			public void run() {
				for(int i = 0; i < count; i++) {
					System.out.println(text +  " " + i + " r2");
				}
			}
		};
		new Thread(r2).start();
	}
}
