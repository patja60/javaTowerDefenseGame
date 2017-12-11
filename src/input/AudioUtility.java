package input;

import javafx.scene.media.AudioClip;
import sharedObject.RenderableHolder;

public class AudioUtility {

	public static void playSound(String sound) {
		if (sound == "clickSound") {
			RenderableHolder.soundClick.play();
		}else if (sound == "backGroundMusic") {
			RenderableHolder.soundBackground.play();
		}else if (sound == "gameOver") {
			RenderableHolder.soundgameOver.play();
		}else if (sound == "selected") {
			RenderableHolder.soundSelecte.play();
		}else if (sound == "coin") {
			RenderableHolder.soundCoin.play();
		}
	}
	public static void stopSound(String sound) {
		if (sound == "backGroundMusic") {
			RenderableHolder.soundBackground.stop();;
		}
	}
}
