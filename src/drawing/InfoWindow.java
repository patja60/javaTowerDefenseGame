package drawing;

import input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameLogic;
import logic.IceTower;
import logic.LaserTower;
import logic.NormalTower;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class InfoWindow extends Canvas{
	
	private GameLogic gameLogic;
	private Image image;

	public InfoWindow(int width, int height, GameLogic gameLogic) {
		super(width,height);
		this.gameLogic = gameLogic;
		
	}
	
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		for(int i = 0 ; i < 300 ; i += 50) {
			for(int j = 0 ; j < 500 ; j += 50) {
				gc.drawImage(RenderableHolder.grass, i, j);
			}
		}
		gc.setFill(Color.BLACK);
		Font SCORE_TIME_FONT = new Font("Monospace", 20);
		gc.setFont(SCORE_TIME_FONT);
		try {
			for(int i = 0 ; i < gameLogic.getFieldOption().getTowerClick().towerData().size() ; i++) {
				gc.fillText(gameLogic.getFieldOption().getTowerClick().towerData().get(i).toString(), 20, 40*i+260);
			}
			if(gameLogic.getFieldOption().getTowerClick() instanceof NormalTower) {
				image = RenderableHolder.infoNormalTower;
			}else if(gameLogic.getFieldOption().getTowerClick() instanceof IceTower) {
				image = RenderableHolder.infoIceTower;
			}else if(gameLogic.getFieldOption().getTowerClick() instanceof LaserTower) {
				image = RenderableHolder.infoLaserTower;
			}
		}catch(NullPointerException e) {
			try {
				for(int i = 0 ; i < gameLogic.getSpawn().getCurrentCreep().creepData().size() ; i++) {
					gc.fillText(gameLogic.getSpawn().getCurrentCreep().creepData().get(i).toString(), 20, 40*i+260);
				}
				if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Deacoon : war front type")) {
					image = RenderableHolder.deacoon;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Morph : speed type")) {
					image = RenderableHolder.morph;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Theontic  : tough skin type")) {
					image = RenderableHolder.Theontic;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("E23-6 : Fast Robot")) {
					image = RenderableHolder.E23;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("E24-0 : armor robot")) {
					image = RenderableHolder.E24;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Luna-44 : killer aircraft")) {
					image = RenderableHolder.luna;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Andromeda-47 : speed aircraft")) {
					image = RenderableHolder.andromeda;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Quasar-C : mega aircraft")) {
					image = RenderableHolder.quasar;
				}else if(gameLogic.getSpawn().getCurrentCreep().creepData().get(0).equals("Supernova-E12 : ultimate aircraft")) {
					image = RenderableHolder.supernova;
				}
				
			}catch(NullPointerException e1) {
				gc.fillText("-", 50, 300);
			}
		}
		gc.drawImage(image, 20, 20);
		gc.setLineWidth(5);
		gc.strokeRect(20, 20, 260, 200);
	}
	
	public void tick(){
		
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
			InputUtility.mouseOnInfoWindow = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnInfoWindow = false;
		});

		this.setOnMouseMoved((MouseEvent event) -> {
			if (InputUtility.mouseOnGameScreen) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			if (InputUtility.mouseOnGameScreen) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});
	}
}
