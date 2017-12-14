package drawing;

import input.AudioUtility;
import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.GameLogic;
import logic.GameLogic.STATE;
import logic.MyException2;
import sharedObject.RenderableHolder;

public class Bar extends Canvas{
	private GameLogic gameLogic;
	private boolean backHome;
	
	
	public Bar(double width, double height, GameLogic gameLogic) {
		super(width,height);
		addListerner();
		this.setVisible(true);
		this.backHome = false;
		this.gameLogic = gameLogic;
	}
	
	public void tick(){
		if(gameLogic.getHealth()<=0) {
			gameLogic.setPause(true);
			gameLogic.setGameState(STATE.GameOver);
			gameLogic.setGameOver(true);
			AudioUtility.playSound("gameOver");
		}
		if(InputUtility.mouseOnBar) {
			if(InputUtility.isLeftClickTriggered() && onPauseButton()) {
				AudioUtility.playSound("clickSound");
				gameLogic.setPause(!gameLogic.isPause());
			}
			if(InputUtility.isLeftClickTriggered() && onHomeButton()) {
				AudioUtility.playSound("clickSound");
				backHome = true;
			}
			InputUtility.updateInputState();
		}
	}
	
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage(RenderableHolder.bar, 0, 0);
		
		Font theFont = Font.font("Times New Roman", FontWeight.LIGHT, 30);
		gc.setFont(theFont);
		gc.setFill(Color.ORANGE);
		if(gameLogic.getTime()<0) {
			gc.fillText("0", 100, 73);
		}else {
			gc.fillText(gameLogic.getTime()+"", 100, 73);
		}
		if(gameLogic.getHealth()<0) {
			gc.fillText(Integer.toString(0), 500, 73);
		}else {
			gc.fillText(Integer.toString(gameLogic.getHealth()), 440, 73);
		}
		gc.fillText(""+gameLogic.getMoney(), 265, 73);
		
		try {
			paintWave(gameLogic.getWave());
		} catch (MyException2 e) {
			System.out.println(e.message());
			gc.fillText(""+(gameLogic.getWave()-1), 670, 70);
		}
		
		gc.fillText(Integer.toString(gameLogic.getStage()+1), 880, 70);
		
	}
	
	public void paintWave(int wave) throws MyException2 {
		GraphicsContext gc = this.getGraphicsContext2D();
		if(wave > 4) {
			throw new MyException2(wave);
		}
		gc.fillText(""+wave, 670, 70);
	}
	
	public boolean onPauseButton() {
		return (Math.abs(InputUtility.mouseX-(920+35))<=35 && Math.abs(InputUtility.mouseY-(26+35))<=35);
	}
	
	public boolean onHomeButton() {
		return (Math.abs(InputUtility.mouseX-(1005+35))<=35 && Math.abs(InputUtility.mouseY-(26+35))<=35);
	}

	public boolean isBackHome() {
		return backHome;
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
			InputUtility.mouseOnBar = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnBar = false;
		});

		this.setOnMouseMoved((MouseEvent event) -> {
			if (InputUtility.mouseOnBar) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			if (InputUtility.mouseOnBar) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});
	}

}
