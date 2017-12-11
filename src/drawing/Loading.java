package drawing;

import java.awt.Button;
import java.awt.Canvas;

import input.InputUtility;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logic.Creep;
import logic.GameLogic;
import logic.GameLogic.STATE;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class Loading extends javafx.scene.canvas.Canvas{
	private GameLogic gameLogic;
	private int deg1 = 5;
	private int deg2 = 4;
	private int deg3 = 3;
	private int count;
	private long currntTime;
	private long loadingTime;
	
	public Loading(double width, double height,GameLogic gameLogic) {
		super(width,height);
		this.setVisible(true);
		this.gameLogic = gameLogic;
		addListerner();
		currntTime = -1;
		loadingTime = -1;
		count = 1;
	}
	
	public void tick() {
		if(gameLogic.getNextStage() != 0) {
			if(loadingTime < 0) {
				loadingTime = gameLogic.getNow();
			}
			if((gameLogic.getNow()-loadingTime)/1000000000>2) {
				for(IRenderable x : RenderableHolder.getInstance().getEntities()) {
					if(x instanceof Creep) {
						Creep o = (Creep)x;
						o.setDestroy();
					}
				}
				System.out.println("done");
				gameLogic.gameState = STATE.Game;
				loadingTime = -1;
				currntTime = -1;
				RenderableHolder.state = "dead";
			}
		}
	}

	
	public void paintComponent() {
		if(currntTime < 0) {
			currntTime = gameLogic.getNow();
		}
		paintLoadingScreen();
		paintText(gameLogic.getNextStage());
	}
	
	public void paintLoadingScreen() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.translate(this.getWidth()/2, this.getHeight()/2);
		gc.rotate(deg1);
		gc.drawImage(RenderableHolder.loadingCircle, -100, -100);
		gc.rotate(-deg1);
		gc.translate(-this.getWidth()/2, -this.getHeight()/2);
		
		gc.translate(this.getWidth()/2 - 110, this.getHeight()/2 - 130);
		gc.rotate(deg2);
		gc.drawImage(RenderableHolder.loadingCircle, -50, -50, 100, 100);
		gc.rotate(-deg2);
		gc.translate(-(this.getWidth()/2 - 110), -(this.getHeight()/2 - 130));
		
		gc.translate(this.getWidth()/2 - 35 , this.getHeight()/2 - 110);
		gc.rotate(deg3);
		gc.drawImage(RenderableHolder.loadingCircle, -30, -30, 60, 60);
		gc.rotate(-deg3);
		gc.translate(-(this.getWidth()/2 - 35 ), -(this.getHeight()/2 - 110));
		
		deg1 += 5;
		deg2 += 8;
		deg3 -= 5;
	}
	
	public void paintText(int textCase) {
		GraphicsContext gc = this.getGraphicsContext2D();
		Font theFont = Font.font("Times New Roman", FontWeight.LIGHT, 30);
		gc.setFont(theFont);
		gc.setFill(Color.WHITE);
		if((gameLogic.getNow()-currntTime)/1000000000>0.5) {
			count++;
			if(count > 3) {
				count = 1;
			}
			currntTime = gameLogic.getNow();
		}
		
		switch (count) {
		case 1:
			if(textCase == 0) {
				gc.fillText("Loading.", this.getWidth()/2-80, 500);
			}else {
				gc.fillText("Stage "+textCase+".", this.getWidth()/2-80, 500);
			}
			break;

		case 2:
			if(textCase == 0) {
				gc.fillText("Loading..", this.getWidth()/2-80, 500);
			}else {
				gc.fillText("Stage "+textCase+"..", this.getWidth()/2-80, 500);
			}
			break;
			
		case 3:
			if(textCase == 0) {
				gc.fillText("Loading...", this.getWidth()/2-80, 500);
			}else {
				gc.fillText("Stage "+textCase+"...", this.getWidth()/2-80, 500);
			}
			break;
		}
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
