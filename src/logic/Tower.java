package logic;

import java.util.List;

public abstract class Tower extends Entity {
	
	protected GameLogic gameLogic;
	protected int amount;
	protected int attackLevel;
	protected double speed;
	public static final int MAX_AMOUNT = 3;
	public static final int MAX_ATTACK_LEVEL = 3;
	public Tower(int x, int y,GameLogic gameLogic, double speed, int amount, int attackLevel) {
		this.x = x;
		this.y = y;
		this.amount = amount;
		this.attackLevel = attackLevel;
		this.speed = speed;
		this.gameLogic = gameLogic;
	}
	
	public abstract List towerData();
	public abstract void destroyTower();
}
