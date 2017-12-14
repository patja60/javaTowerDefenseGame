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

public class Menu extends javafx.scene.canvas.Canvas{
	private GameLogic gameLogic;
	private boolean soundPlayButton;
	private boolean soundHelpButton;
	
	public Menu(double width, double height,GameLogic gameLogic) {
		super(width,height);
		this.setVisible(true);
		this.gameLogic = gameLogic;
		this.soundPlayButton = false;
		this.soundHelpButton = false;
		addListerner();
	}
	
	public void tick(){
		if(InputUtility.mouseOnMenu) {
			if(InputUtility.isLeftClickRelease() && onPlayButton()) {
				gameLogic.setGameState(STATE.Game);
			}
			if(InputUtility.isLeftClickRelease() && onHelpButton()) {
				gameLogic.setGameState(STATE.Help);
				AudioUtility.playSound("clickSound");
			}
			InputUtility.updateInputState();
		}
		
		if(InputUtility.mouseOnMenu && onPlayButton() && !soundPlayButton) {
			AudioUtility.playSound("selected");
			soundPlayButton = true;
		}
		
		if(InputUtility.mouseOnMenu && onHelpButton() && !soundHelpButton) {
			AudioUtility.playSound("selected");
			soundHelpButton = true;
		}
		
		if(!onPlayButton()) {
			soundPlayButton = false;
		}
		
		if(!onHelpButton()) {
			soundHelpButton = false;
		}
	}

	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage(RenderableHolder.openBg, 0, 0);
		if(InputUtility.mouseOnMenu && onPlayButton()) {
			gc.drawImage(RenderableHolder.clickPlayButton, 28, 380, 292, 97);
		}else {
			gc.drawImage(RenderableHolder.playButton, 28, 380, 292, 97);
		}
		
		if(InputUtility.mouseOnMenu && onHelpButton()) {
			gc.drawImage(RenderableHolder.clickHelpButton, 28, 480, 292, 97);
		}else {
			gc.drawImage(RenderableHolder.helpButton, 28, 480, 292, 97);
		}
		
		Font theFont = Font.font("Times New Roman", FontWeight.LIGHT, 15);
		gc.setFont(theFont);
		gc.setFill(Color.WHITE);
		gc.fillText("BY THANAPAT JUTHAVANTANA", 30, 600);
	}
	
	public boolean onPlayButton() {
		return (InputUtility.mouseX > 28 && InputUtility.mouseX < 320)&&(InputUtility.mouseY > 380 && InputUtility.mouseY < 477);
	}
	
	public boolean onHelpButton() {
		return (InputUtility.mouseX > 28 && InputUtility.mouseX < 320)&&(InputUtility.mouseY > 480 && InputUtility.mouseY < 577);
	}
	
	public void addListerner() {
		this.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				System.out.println("Click");
				InputUtility.mouseLeftDown();
			}else if (event.getButton() == MouseButton.SECONDARY) {
				System.out.println("Click");
				InputUtility.mouseRightDown();
			}
		});

		this.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				System.out.println("Release");
				InputUtility.mouseLeftRelease();
			}else if (event.getButton() == MouseButton.SECONDARY) {
				System.out.println("Release");
				InputUtility.mouseRightRelease();
			}
		});

		this.setOnMouseEntered((MouseEvent event) -> {
			InputUtility.mouseOnMenu = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnMenu = false;
		});

		this.setOnMouseMoved((MouseEvent event) -> {
			if (InputUtility.mouseOnMenu) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			if (InputUtility.mouseOnMenu) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});
	}

}
