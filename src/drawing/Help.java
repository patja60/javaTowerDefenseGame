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

public class Help extends javafx.scene.canvas.Canvas{
	private GameLogic gameLogic;
	private boolean soundBackButton;
	
	public Help(double width, double height,GameLogic gameLogic) {
		super(width,height);
		this.setVisible(true);
		this.gameLogic = gameLogic;
		this.soundBackButton = false;
		addListerner();
	}
	
	public void tick(){
		if(InputUtility.mouseOnHelp) {
			if(InputUtility.isLeftClickRelease() && onBackButton()) {
				gameLogic.gameState = STATE.Menu;
				AudioUtility.playSound("clickSound");
			}
			InputUtility.updateInputState();
		}
		
		if(InputUtility.mouseOnHelp && onBackButton() && !soundBackButton) {
			AudioUtility.playSound("selected");
			soundBackButton = true;
		}
		
		if(!onBackButton()) {
			soundBackButton = false;
		}
		
	}

	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage(RenderableHolder.help, 0, 0);
		if(InputUtility.mouseOnHelp && onBackButton()) {
			gc.drawImage(RenderableHolder.clickbackButton, 820, 530, 256, 85);
		}else {
			gc.drawImage(RenderableHolder.backButton, 820, 530, 256, 85);
		}
	}
	
	public boolean onBackButton() {
		return (InputUtility.mouseX > 820 && InputUtility.mouseX < 1076)&&(InputUtility.mouseY > 530 && InputUtility.mouseY < 615);
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
			InputUtility.mouseOnHelp = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnHelp = false;
		});

		this.setOnMouseMoved((MouseEvent event) -> {
			if (InputUtility.mouseOnHelp) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			if (InputUtility.mouseOnHelp) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});
	}

}
