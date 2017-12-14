package application;

import drawing.Bar;
import drawing.End;
import drawing.GameScreen;
import drawing.Help;
import drawing.InfoWindow;
import drawing.Loading;
import drawing.Menu;
import input.AudioUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.GameLogic;
import logic.GameLogic.STATE;
import logic.MyException;
import sharedObject.RenderableHolder;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private long gap=0;
	private long currentTime=0;
	private boolean fromPause = false;
	private GameLogic gameLogic;
	private Bar bar;
	private Loading loading;
	private GameScreen gameScreen;
	private Help help;
	private InfoWindow infoWindow;
	private HBox body;
	private VBox game;
	private Menu menu;
	private End end;
	private StackPane loadingScreen;
	private StackPane mainMenu;
	private StackPane helpScreen;
	private StackPane endScreen;
	private double delayTime = -1;
	private Thread sound;
	
	@Override
	public void start(Stage stage) {
		gameLogic = new GameLogic();
		bar = new Bar(1100, 120, gameLogic);
			
		game = new VBox();
		Scene scene2 = new Scene(game);
		gameScreen = new GameScreen(800, 500,gameLogic);
		infoWindow = new InfoWindow(300, 500,gameLogic);
		body = new HBox();
		body.getChildren().addAll(gameScreen,infoWindow);
		game.getChildren().addAll(bar,body);
		
		menu = new Menu(1100, 620, gameLogic);
		mainMenu = new StackPane();
		mainMenu.getChildren().add(menu);
		Scene scene1 = new Scene(mainMenu);
		
		loading = new Loading(1100, 620, gameLogic);
		loadingScreen = new StackPane();
		loadingScreen.getChildren().add(loading);
		Scene scene3 = new Scene(loadingScreen);
		
		help = new Help(1100, 620, gameLogic);
		helpScreen = new StackPane();
		helpScreen.getChildren().add(help);
		Scene scene4 = new Scene(helpScreen);
		
		end = new End(1100, 620, gameLogic);
		endScreen = new StackPane();
		endScreen.getChildren().add(end);
		Scene scene5 = new Scene(endScreen);
		
		stage.setTitle("TD");
		stage.setScene(scene3);
		stage.show();
		stage.setResizable(false);
		
		sound = new Thread(()->{
			while(gameLogic.getGameState() != STATE.End) {
				if(RenderableHolder.state != "loading" && (gameLogic.getGameState() != STATE.GameOver && gameLogic.getGameState() != STATE.GameEnd)) {
					AudioUtility.playSound("backGroundMusic");
					try {
						Thread.sleep(158000);
					} catch (InterruptedException e) {
					}
				}else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			}
		});
		sound.start();

		AnimationTimer animation = new AnimationTimer() {
			public void handle(long now) {
				if(gameLogic.isReset()) {
					
					RenderableHolder.getInstance().resetGame();
					gameLogic = new GameLogic();
					bar = new Bar(1100, 120, gameLogic);
					gameScreen = new GameScreen(800, 500,gameLogic);
					infoWindow = new InfoWindow(300, 500,gameLogic);
					body = new HBox();
					body.getChildren().addAll(gameScreen,infoWindow);
					game = new VBox();
					game.getChildren().addAll(bar,body);
					scene2.setRoot(game);
					menu = new Menu(1100, 620, gameLogic);
					mainMenu.getChildren().add(menu);
					scene1.setRoot(mainMenu);
					
					loading = new Loading(1100, 620, gameLogic);
					loadingScreen.getChildren().add(loading);
					scene3.setRoot(loadingScreen);
					
					help = new Help(1100, 620, gameLogic);
					helpScreen.getChildren().add(help);
					scene4.setRoot(helpScreen);
					
					end = new End(1100, 620, gameLogic);
					endScreen.getChildren().add(end);
					scene5.setRoot(endScreen);
					
					gameLogic.setReset(false);
				}
				if(gameLogic.isGameOver()){
					sound.interrupt();

					try {
						throw new MyException(gameLogic.getGameState());
					} catch (MyException e) {
						System.out.println(e.message());
					}
					
					if(delayTime<0) {
						delayTime = now;
					}
					if((now-delayTime)/1000000000>5) {
						delayTime = -1;
						gameLogic.setReset(true);
						gameLogic.setGameState(STATE.Menu);
					}
				}
				if(bar.isBackHome()){
					gameLogic.setReset(true);
					gameLogic.setGameState(STATE.Menu);
				}
				 
				if(RenderableHolder.state == "loading") {
					stage.setScene(scene3);
					loading.tick();
					loading.paintComponent();
				}else if(RenderableHolder.state == "finish"){
					gameLogic.setGameState(STATE.Menu);
					RenderableHolder.state = "dead";
				}
				
				if(gameLogic.getGameState() == STATE.LoadingStage) {
					stage.setScene(scene3);
					loading.paintComponent();
				}
				
				if(gameLogic.isPause()) {
					fromPause = true;
				}else {
					if(fromPause) {
						gap = gap + now - currentTime;
						fromPause = false;
					}
					currentTime = now;
					gameLogic.setNow(now-gap);
				}
				if(gameLogic.getGameState() == STATE.Menu) {
					stage.setScene(scene1);
					menu.tick();
					menu.paintComponent();
				}else if(gameLogic.getGameState() == STATE.Game){
					stage.setScene(scene2);
					bar.tick();
					bar.paintComponent();
					gameScreen.paintComponent();
					infoWindow.paintComponent();
					gameLogic.getSpawn().tick();
					gameLogic.getFieldOption().tick();
				}else if(gameLogic.getGameState() == STATE.Help){
					stage.setScene(scene4);
					help.tick();
					help.paintComponent();
				}else if(gameLogic.getGameState() == STATE.GameEnd){
					sound.interrupt();
					AudioUtility.stopSound("backGroundMusic");
					stage.setScene(scene5);
					end.tick();
					end.paintComponent();
				}
				gameLogic.logicUpdate();
				RenderableHolder.getInstance().update();
			}
		};
		animation.start();
		
	}
	
	@Override
	public void stop() {
		gameLogic.setGameState(STATE.End);
		AudioUtility.stopSound("backGroundMusic");
		sound.interrupt();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
