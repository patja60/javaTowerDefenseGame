package logic;

public class MyException2 extends Exception{
	private int wave;
	public MyException2(int wave) {
		this.wave = wave;
	}
	
	public String message() {
		return "maximum wave is "+(wave-1);
	}
}
