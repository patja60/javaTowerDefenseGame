package drawing;

import java.awt.Button;
import java.awt.Canvas;

import input.AudioUtility;
import input.InputUtility;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.GameLogic;
import logic.GameLogic.STATE;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class End extends javafx.scene.canvas.Canvas{
	private GameLogic gameLogic;
	private double delay;
	
	public  End(double width, double height,GameLogic gameLogic) {
		super(width,height);
		this.setVisible(true);
		this.gameLogic = gameLogic;
		this.delay = -1;
	}
	
	public void tick(){
		if(delay < 0) {
			delay = gameLogic.getNow();
		}
		if((gameLogic.getNow()-delay)/1000000000>10) {
			gameLogic.setReset(true);
			gameLogic.setGameState(STATE.Menu);
		}
	}

	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage(RenderableHolder.end, 0, 0);
	}

}
