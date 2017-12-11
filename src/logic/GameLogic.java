package logic;

import java.util.ArrayList;
import java.util.List;

import drawing.Bar;
import logic.GameLogic.STATE;
import sharedObject.RenderableHolder;

public class GameLogic {
	
	private List<Entity> gameObjectContainer;
	private Spawn spawn;
	private Field field;
	private FieldOption fieldOption;
	private long now;
	private int nextStage;
	private boolean reset;
	private boolean isPause;
	private int time;
	private int wave;
	private int money;
	private int stage;
	private int health;
	private boolean gameOver;
	
	public enum STATE{
		Menu,PickStage,Game,Loading,LoadingStage,End,GameOver,Help,GameEnd
	};
	public static STATE gameState = STATE.Loading;
	
	public GameLogic() {
		this.gameObjectContainer = new ArrayList<Entity>();
		this.field = new Field();
		this.spawn = new Spawn(this,field);
		this.fieldOption = new FieldOption(this);
		this.nextStage = 0;
		RenderableHolder.getInstance().add(field);
		RenderableHolder.getInstance().add(fieldOption);
		this.reset = false;
		this.isPause = false;
		this.health = 20;
		this.wave = 1;
	}
	
	public void logicUpdate() {
		
		if(gameState == STATE.Game) {
			if(!isPause) {
				for(int i = gameObjectContainer.size()-1 ; i >= 0 ; i--) {
					if(!gameObjectContainer.get(i).isDestroyed()) {
						gameObjectContainer.get(i).tick();
					}else{
						gameObjectContainer.remove(i);
					}
				}
			}
		}
	}
	
	protected void addNewObject(Entity entity) {
		gameObjectContainer.add(entity);
		RenderableHolder.getInstance().add(entity);
	}
	
	public List<Entity> getGameObjectContainer() {
		return gameObjectContainer;
	}

	public Field getField() {
		return field;
	}

	public FieldOption getFieldOption() {
		return fieldOption;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public Spawn getSpawn() {
		return spawn;
	}
	
	public int getNextStage() {
		return nextStage;
	}

	public boolean isReset() {
		return reset;
	}
	
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	
	public void setLoadStage(int nextStage) {
		gameState = STATE.LoadingStage;
		this.nextStage = nextStage;
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	
}
