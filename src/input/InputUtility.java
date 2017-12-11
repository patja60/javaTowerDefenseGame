package input;

public class InputUtility {
	public static double mouseX,mouseY;
	public static boolean mouseOnGameScreen;
	public static boolean mouseOnBar;
	public static boolean mouseOnMenu;
	public static boolean mouseOnHelp;
	public static boolean mouseOnInfoWindow;
	private static boolean isLeftDown = false;
	private static boolean isLeftClickedLastTick = false;
	private static boolean isLeftRelease = false;
	private static boolean isRightDown = false;
	private static boolean isRightClickedLastTick = false;
	private static boolean isRightRelease = false;
	
	public static void mouseLeftDown(){
		isLeftDown = true;
		isLeftClickedLastTick = true;
	}
	
	public static void mouseLeftRelease(){
		isLeftRelease = true;
		isLeftDown = false;
	}
	
	public static boolean isLeftClickTriggered(){
		return isLeftClickedLastTick;
	}
	
	public static boolean isLeftClickRelease(){
		return isLeftRelease;
	}
	
	public static void mouseRightDown(){
		isRightDown = true;
		isRightClickedLastTick = true;
	}
	
	public static void mouseRightRelease(){
		isRightDown = false;
		isRightRelease = true;
	}
	
	public static boolean isRightClickTriggered(){
		return isRightClickedLastTick;
	}
	
	public static boolean isRightClickRelease(){
		return isRightRelease;
	}
	
	public static void updateInputState(){
		isLeftClickedLastTick = false;
		isRightClickedLastTick = false;
		isLeftRelease = false;
		isRightRelease = false;
	}
}
