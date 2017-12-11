package logic;

import input.AudioUtility;
import logic.GameLogic.STATE;

public class MyException extends Exception{
	public MyException(STATE state) {
		if(state == STATE.GameOver) {
			AudioUtility.stopSound("backGroundMusic");
		}
	}
	public String message() {
		return "game over";
	}
}
