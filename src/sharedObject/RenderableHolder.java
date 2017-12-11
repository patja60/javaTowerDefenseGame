package sharedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import logic.GameLogic;
import logic.GameLogic.STATE;

public class RenderableHolder {
	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;
	public static String state;
	
	public static Image loadingCircle;
	public static Image infoWindow;
	
	public static Image infoTowerImage;
	public static Image infoNormalTower;
	public static Image infoIceTower;
	public static Image infoLaserTower;
	
	public static Image infoCreepImage;
	public static Image deacoon;
	public static Image morph;
	public static Image Theontic;
	public static Image E23;
	public static Image E24;
	public static Image luna;
	public static Image andromeda;
	public static Image quasar;
	public static Image supernova;
	
	public static Image bar;
	
	public static Image openBg;
	public static Image help;
	public static Image button;
	public static Image playButton;
	public static Image clickPlayButton;
	public static Image helpButton;
	public static Image clickHelpButton;
	public static Image backButton;
	public static Image clickbackButton;
	
	public static Image portal;
	public static Image terrain;
	public static Image orbitEntity;
	public static Image laser;
	public static Image laserTower;
	public static Image icon;
	
	public static Image tree;
	public static Image grass;
	public static Image path;
	public static Image rock;
	
	public static Image normalTower;
	public static Image iceTower;
	
	public static Image normalCreep1;
	public static Image normalCreep2;
	public static Image normalCreep3;
	public static Image normalCreep4;
	
	public static Image aircraft;
	public static Image aircraft1;
	public static Image aircraft2;
	public static Image aircraft3;
	public static Image aircraft4;
	
	public static Image normalOrbitEntity;
	
	public static Image gameStart;
	public static Image gameOver;
	public static Image stageClear;
	public static Image counting;
	public static Image one;
	public static Image two;
	public static Image three;
	
	public static Image headLaserR;
	public static Image midLaserR;
	public static Image tailLaserR;
	public static Image headLaserL;
	public static Image midLaserL;
	public static Image tailLaserL;
	public static Image laserTowerR;
	public static Image laserTowerL;
	
	public static Image normalTowerIcon;
	public static Image normalUpgradeAttackIcon;
	public static Image normalUpgradeAmountIcon;
	public static Image iceTowerIcon;
	public static Image iceUpgradeSlowIcon;
	public static Image iceUpgradeAttackIcon;
	public static Image laserTowerIcon;
	public static Image laserUpgradeIcon;
	
	public static Image end;
	
	
	public static AudioClip soundClick;
	public static AudioClip soundBackground;
	public static AudioClip soundgameOver;
	public static AudioClip soundSelecte;
	public static AudioClip soundCoin;
	
	private static final RenderableHolder instance = new RenderableHolder();
	
	static {
		Thread thread1 = new Thread(() -> {
			//loadingCircle = new Image(ClassLoader.getSystemResource("loadingCircle.png").toString());
			loadingCircle = new Image(ClassLoader.getSystemResource("loadingCircle.png").toString(), 200, 200, false, false);
			state = "loading";
			for(int i = 0 ; i < 100000 ; i++) {
				//System.out.println(i);
			}
			loadResource();
			state = "finish";
		});
		thread1.start();
	}
	
	public static void loadResource() {
		
		help = new Image(ClassLoader.getSystemResource("help.png").toString());
		infoTowerImage = new Image(ClassLoader.getSystemResource("infoTowerImage.png").toString());
		infoNormalTower = new WritableImage(infoTowerImage.getPixelReader(), 0, 0, 260, 200);
		infoIceTower = new WritableImage(infoTowerImage.getPixelReader(), 260, 0, 260, 200);
		infoLaserTower = new WritableImage(infoTowerImage.getPixelReader(), 520, 0, 260, 200);
		
		infoCreepImage = new Image(ClassLoader.getSystemResource("infoCreepImage.png").toString());
		deacoon = new WritableImage(infoCreepImage.getPixelReader(), 0, 0, 260, 200);
		morph = new WritableImage(infoCreepImage.getPixelReader(), 260, 0, 260, 200);
		Theontic = new WritableImage(infoCreepImage.getPixelReader(), 780, 0, 260, 200);
		E23 = new WritableImage(infoCreepImage.getPixelReader(), 520, 0, 260, 200);
		E24 = new WritableImage(infoCreepImage.getPixelReader(), 0, 400, 260, 200);
		luna = new WritableImage(infoCreepImage.getPixelReader(), 0, 200, 260, 200);
		andromeda = new WritableImage(infoCreepImage.getPixelReader(), 260, 200, 260, 200);
		quasar = new WritableImage(infoCreepImage.getPixelReader(), 520, 200, 260, 200);
		supernova = new WritableImage(infoCreepImage.getPixelReader(), 780, 200, 260, 200);
		
		openBg = new Image(ClassLoader.getSystemResource("openBg.png").toString());
		button = new Image(ClassLoader.getSystemResource("button.png").toString());
		playButton = new WritableImage(button.getPixelReader(), 0, 0, 512, 170);
		clickPlayButton = new WritableImage(button.getPixelReader(), 512, 0, 512, 170);
		helpButton = new WritableImage(button.getPixelReader(), 0, 170, 512, 170);
		clickHelpButton = new WritableImage(button.getPixelReader(), 512, 170, 512, 170);
		backButton = new WritableImage(button.getPixelReader(), 0, 340, 512, 170);
		clickbackButton = new WritableImage(button.getPixelReader(), 512, 340, 512, 170);
		
		bar = new Image(ClassLoader.getSystemResource("bar.png").toString());
		
		portal = new Image(ClassLoader.getSystemResource("portal.png").toString());
		terrain = new Image(ClassLoader.getSystemResource("terrain.png").toString());
		grass = new WritableImage(terrain.getPixelReader(), 300, 50, 50, 50);
		path = new WritableImage(terrain.getPixelReader(), 50, 200, 50, 50);
		tree = new WritableImage(terrain.getPixelReader(), 900, 250+1, 50, 50);
		rock = new WritableImage(terrain.getPixelReader(), 1050, 250, 50, 50);
		
		normalTower = new WritableImage(terrain.getPixelReader(), 950, 350+1, 50, 50);
		
		normalCreep1 = new WritableImage(terrain.getPixelReader(), 750, 500+2, 50, 50);
		normalCreep2 = new WritableImage(terrain.getPixelReader(), 800, 500+2, 50, 50);
		normalCreep3 = new WritableImage(terrain.getPixelReader(), 850, 500+2, 50, 50);
		normalCreep4 = new WritableImage(terrain.getPixelReader(), 900, 500+2, 50, 50);
		
		aircraft = new Image(ClassLoader.getSystemResource("aircraft.png").toString());
		aircraft1 = new WritableImage(aircraft.getPixelReader(), 0, 0, 50, 50); 
		aircraft2 = new WritableImage(aircraft.getPixelReader(), 0, 50, 50, 50); 
		aircraft3 = new WritableImage(aircraft.getPixelReader(), 0, 50, 50, 50); 
		aircraft4 = new WritableImage(aircraft.getPixelReader(), 50, 50, 50, 50); 
		
		gameStart = new Image(ClassLoader.getSystemResource("gameStart.png").toString());
		gameOver = new Image(ClassLoader.getSystemResource("gameOver.png").toString());
		stageClear = new Image(ClassLoader.getSystemResource("stageClear.png").toString());
		counting = new Image(ClassLoader.getSystemResource("1-2-3.png").toString());
		one = new WritableImage(counting.getPixelReader(), 0, 0, 150, 150);
		two = new WritableImage(counting.getPixelReader(), 150, 0, 150, 150);
		three = new WritableImage(counting.getPixelReader(), 300, 0, 150, 150);
		
		orbitEntity = new Image(ClassLoader.getSystemResource("orbitEntity.png").toString());
		normalOrbitEntity = new WritableImage(orbitEntity.getPixelReader(), 0, 0, 20, 20);
		
		laser = new Image(ClassLoader.getSystemResource("laser.png").toString());
		headLaserR = new WritableImage(laser.getPixelReader(), 52, 0, 10, 25);
		midLaserR = new WritableImage(laser.getPixelReader(), 15, 0, 1, 25);
		tailLaserR = new WritableImage(laser.getPixelReader(), 0, 0, 10, 25);
		headLaserL = new WritableImage(laser.getPixelReader(), 0, 25, 10, 25);
		midLaserL = new WritableImage(laser.getPixelReader(), 17, 25, 1, 25);
		tailLaserL = new WritableImage(laser.getPixelReader(), 52, 25, 10, 25);
		
		laserTower = new Image(ClassLoader.getSystemResource("laserTower.png").toString());
		laserTowerL = new WritableImage(laserTower.getPixelReader(), 0, 0, 50, 50);
		laserTowerR = new WritableImage(laserTower.getPixelReader(), 50, 0, 50, 50);
		
		iceTower = new Image(ClassLoader.getSystemResource("froz.png").toString());
		
		icon = new Image(ClassLoader.getSystemResource("towerIcon.png").toString());
		normalTowerIcon = new WritableImage(icon.getPixelReader(), 0, 0, 50, 50);
		normalUpgradeAttackIcon = new WritableImage(icon.getPixelReader(), 0, 50, 50, 50);
		normalUpgradeAmountIcon = new WritableImage(icon.getPixelReader(), 50, 50, 50, 50);
		iceTowerIcon = new WritableImage(icon.getPixelReader(), 50, 0, 50, 50);
		iceUpgradeAttackIcon = new WritableImage(icon.getPixelReader(), 50, 100, 50, 50);
		iceUpgradeSlowIcon = new WritableImage(icon.getPixelReader(), 0, 100, 50, 50);
		laserTowerIcon = new WritableImage(icon.getPixelReader(), 100, 0, 50, 50);
		laserUpgradeIcon = new WritableImage(icon.getPixelReader(), 100, 100, 50, 50);
		
		end = new Image(ClassLoader.getSystemResource("theEnd.png").toString());
		
		soundClick = new AudioClip(ClassLoader.getSystemResource("clickSound.mp4").toString());
		soundBackground = new AudioClip(ClassLoader.getSystemResource("backgroundMusic.mp4").toString());
		soundgameOver = new AudioClip(ClassLoader.getSystemResource("gameOver.mp4").toString());
		soundSelecte = new AudioClip(ClassLoader.getSystemResource("selecteSound.wav").toString());
		soundCoin = new AudioClip(ClassLoader.getSystemResource("coinSound.wav").toString());
	}
	
	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1,IRenderable o2) -> {
			if(o1.getZ() > o2.getZ()) return 1;
			return -1;
		};
	}
	
	public void add(IRenderable entity) {
		entities.add(entity);
		Collections.sort(entities,comparator);
	}
	
	public void update() {
		for(int i = 0 ; i < entities.size() ; i++) {
			if(entities.get(i).isDestroyed()) {
				entities.remove(i);
			}
		}
	}
	
	public static RenderableHolder getInstance() {
		return instance;
	}
	
	public List<IRenderable> getEntities(){
		return entities;
	}
	
	public void resetGame() {
		this.entities = new ArrayList();
	}
	
}
